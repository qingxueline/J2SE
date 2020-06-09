package com.somnus.shell;

import com.google.common.collect.Lists;
import com.jcraft.jsch.*;
import com.somnus.shell.entity.Remote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.somnus.socket.utils.HttpProxyConfig.CONNECT_TIMEOUT;

/**
 * @author lyl
 * @version 2020/5/31 0031 23:37:22
 */
@Slf4j
public class SSHClientHelper {

    public static Session getSession() throws JSchException {
        return connect(Remote.builder()
                .host("192.168.98.101")
                .port(22)
                .user("root")
                .password("root")
                .build());
    }

    public static Session getSession(Remote remote) throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(remote.getUser(), remote.getHost(), remote.getPort());
        session.setPassword(remote.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        return session;
    }

    public static Session connect(Remote remote) throws JSchException {
        /**
         * 创建连接后这个session是一直可用的，所以不需要关闭.由Session中open的Channel在使用后应该关闭.
         * jsch是以多线程方式运行的，所以代码在connect后如果不disconnect Channel和Session，以及相关的Stream，程序会一直等待，直到关闭.
         */
        Session session = getSession(remote);
        session.connect(CONNECT_TIMEOUT);
        if (session.isConnected()) {
            log.info("Host({}) connected.", remote.getHost());
        }
        return session;
    }

    /**
     * 执行相关命令
     *
     * @param session session
     * @param command 命令
     * @return 。
     * @throws JSchException
     */
    public static List<String> remoteExecute(Session session, String command) throws JSchException, IOException {
        log.debug("开始执行命令:" + command);

        List<String> resultLines = Lists.newArrayList();
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            //执行的命令
            channel.setCommand(command);
            //读取返回结果
            InputStream input = channel.getInputStream();
            channel.connect();
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
                String inputLine;
                while ((inputLine = inputReader.readLine()) != null) {
                    log.debug("{}", inputLine);
                    resultLines.add(inputLine);
                }
            } finally {
                IOUtils.closeQuietly(input);
            }
        } finally {
            if (channel != null) {
                try {
                    channel.disconnect();
                } catch (Exception e) {
                    log.error("JSch channel disconnect error:", e);
                }
            }
        }
        return resultLines;
    }

    /**
     * 执行多行shell
     *
     * @param command 命令
     * @return
     * @throws Exception
     */
    public static int remoteShell(Session session, String command) throws Exception {
        log.debug("开始执行命令:\r\n" + command);
        int returnCode = -1;
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        InputStream in = channel.getInputStream();
        channel.setPty(true);
        channel.connect();
        OutputStream os = channel.getOutputStream();
        os.write((command + "\r\n").getBytes());
        os.write("exit\r\n".getBytes());
        os.flush();
        //输出执行结果
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                String res = new String(tmp, 0, i);
                log.info("============================================================\r\n{}\r\n============================================================", res);
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                returnCode = channel.getExitStatus();
                log.debug("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
        os.close();
        in.close();
        channel.disconnect();
        return returnCode;
    }


    private static boolean remoteEdit(Session session, String source, Function<List<String>, List<String>> process) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = source;
            int index = source.lastIndexOf('/');
            if (index >= 0) {
                fileName = source.substring(index + 1);
            }
            //backup source
            remoteExecute(session, String.format("cp %s %s", source, source + ".bak." + System.currentTimeMillis()));
            //scp from remote
            String tmpSource = System.getProperty("java.io.tmpdir") + session.getHost() + "-" + fileName;
            scpFrom(session, source, tmpSource);
            in = new FileInputStream(tmpSource);
            //edit file according function process
            String tmpDestination = tmpSource + ".des";
            out = new FileOutputStream(tmpDestination);
            List<String> inputLines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                inputLines.add(inputLine);
            }
            List<String> outputLines = process.apply(inputLines);
            for (String outputLine : outputLines) {
                out.write((outputLine + "\n").getBytes());
                out.flush();
            }
            //scp to remote
            scpTo(tmpDestination, session, source);
            return true;
        } catch (Exception e) {
            log.error("remote edit error, ", e);
            return false;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public static long scpTo(String source, Session session, String destination) {
        FileInputStream fileInputStream = null;
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            String command = "scp";
            command += " -t " + destination;
            channel.setCommand(command);
            channel.connect();
            if (checkAck(in) != 0) {
                return -1;
            }
            File file = new File(source);
            //send "C0644 filesize filename", where filename should not include '/'
            long fileSize = file.length();
            command = "C0644 " + fileSize + " ";
            if (source.lastIndexOf('/') > 0) {
                command += source.substring(source.lastIndexOf('/') + 1);
            } else {
                command += source;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            //send content of file
            fileInputStream = new FileInputStream(source);
            byte[] buf = new byte[1024];
            long sum = 0;
            while (true) {
                int len = fileInputStream.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
                sum += len;
            }
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                return -1;
            }
            return sum;
        } catch (JSchException e) {
            log.error("scp to catched jsch exception, ", e);
        } catch (IOException e) {
            log.error("scp to catched io exception, ", e);
        } catch (Exception e) {
            log.error("scp to error, ", e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
        return -1;
    }

    public static long scpFrom(Session session, String source, String destination) {
        FileOutputStream fileOutputStream = null;
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("scp -f " + source);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] buf = new byte[1024];
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while (true) {
                if (checkAck(in) != 'C') {
                    break;
                }
            }
            //read '644 '
            in.read(buf, 0, 4);
            long fileSize = 0;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    break;
                }
                if (buf[0] == ' ') {
                    break;
                }
                fileSize = fileSize * 10L + (long) (buf[0] - '0');
            }
            String file = null;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            // read a content of lfile
            if (Files.isDirectory(Paths.get(destination))) {
                fileOutputStream = new FileOutputStream(destination + File.separator + file);
            } else {
                fileOutputStream = new FileOutputStream(destination);
            }
            long sum = 0;
            while (true) {
                int len = in.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                sum += len;
                if (len >= fileSize) {
                    fileOutputStream.write(buf, 0, (int) fileSize);
                    break;
                }
                fileOutputStream.write(buf, 0, len);
                fileSize -= len;
            }
            return sum;
        } catch (JSchException e) {
            log.error("scp to catched jsch exception, ", e);
        } catch (IOException e) {
            log.error("scp to catched io exception, ", e);
        } catch (Exception e) {
            log.error("scp to error, ", e);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
        return -1;
    }

    private static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }
        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) {
                // error
                log.debug(sb.toString());
            }
            if (b == 2) {
                // fatal error
                log.debug(sb.toString());
            }
        }
        return b;
    }


    public static void main(String[] args) throws Exception {
        Session session = getSession();
        String username = "root";
        String cmd = "cat /etc/passwd | cut -f1 -d':' | grep -w \"" + username + "\" -c ";
        //执行远程指令
        List<String> str = remoteExecute(session, cmd);
        str.forEach(item -> System.out.println("==========:" + item));
//        remoteExecute(session, "mkdir /home/acc/jsch-demo");
//        remoteExecute(session, "ls /home/acc");
//        remoteExecute(session, "touch /home/acc/jsch-demo/test1; touch /home/acc/jsch-demo/test2");
//        remoteExecute(session, "echo 'It a test file.' > /home/acc/jsch-demo/test-file");
//        remoteExecute(session, "ls -all /home/acc/jsch-demo");
//        remoteExecute(session, "ls -all /home/acc/jsch-demo | grep test");
//        remoteExecute(session, "cat /home/acc/jsch-demo/test-file");


        //scp操作
//        remoteExecute(session, "ls /root/jsch-demo/");
//        scpTo("test.txt", session, "/root/jsch-demo/");
//        remoteExecute(session, "ls /root/jsch-demo/");
//        remoteExecute(session, "echo ' append text.' >> /root/jsch-demo/test.txt");
//        scpFrom(session, "/root/jsch-demo/test.txt", "file-from-remote.txt");


        //远程编辑
//        remoteExecute(session, "echo 'It a test file.' > /root/jsch-demo/test");
//        remoteExecute(session, "cat /root/jsch-demo/test");
//        remoteEdit(session, "/root/jsch-demo/test", (inputLines) -> {
//            List<String> outputLines = new ArrayList<>();
//            for (String inputLine : inputLines) {
//                outputLines.add(inputLine.toUpperCase());
//            }
//            return outputLines;
//        });
//        remoteExecute(session, "cat /root/jsch-demo/test");


        //添加用户,执行多行shell
//        String cmd =
//                "username=(test02)\n" +
//                "password=(123456)\n" +
//                "pass=$(perl -e 'print crypt($ARGV[0], \"password\")' $password)\n" +
//                "useradd -m -p $pass $username\n" +
//                "echo $pass";

//        String shell = "useradd -d /home/ftp/test001 -s /usr/bin/nologin -p $(perl -e 'print crypt($ARGV[0], password)' test01)  -m test01";
//        remoteShell(session, shell);

        session.disconnect();
    }
}
