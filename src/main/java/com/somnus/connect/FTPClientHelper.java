package com.somnus.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.net.ftp.*;


@Slf4j
public class FTPClientHelper {

    public FTPClientHelper() {
    }

    private String host;
    private String port;
    private String username;
    private String password;
    private FTPClient client;

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public FTPClientHelper(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    /**
     * @Description 连接并登录到FTP
     * @author Somnus
     */
    public void login() {
        client = new FTPClient();
        try {
            client.connect(host, Integer.parseInt(port));
            client.enterLocalPassiveMode();
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                client.disconnect();
                throw new SocketException("connection refused.");
            }
            if (StringUtils.isNotBlank(username)) {
                boolean succeed = client.login(username, password);
                if (!succeed) {
                    client.disconnect();
                    throw new RuntimeException(String.format("incorrect username[%s] or password[%s]", username, password));
                } else {
                    log.info("FTP登陆成功");
                }
            } else {
                throw new RuntimeException("username is required.");
            }
        } catch (SocketException e) {
            log.error("Cannot connect to specified ftp server : {}:{} \n Exception message is: {}", host, port, e.getMessage());
            throw new RuntimeException(e);
        } catch (NumberFormatException | IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    /**
     * @param remoteFile 文件
     * @return
     * @Description 判断文件是否存在
     * @author Somnus
     */
    public boolean isRemoteFileExists(String remoteFile) {
        try {
            FTPFile[] files = client.listFiles(remoteFile);
            return !ArrayUtils.isEmpty(files) && files.length > 0;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * @param remoteDirPath
     * @return
     * @Description 判断目录是否存在, 并进入目录
     * @author Somnus
     */
    public boolean isRemoteDirExists(String remoteDirPath) {
        try {
            return client.changeWorkingDirectory(new String(remoteDirPath.getBytes("GBK"), FTP.DEFAULT_CONTROL_ENCODING));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * @param localFile      本地文件
     * @param remoteFilePath 远程文件全路径
     * @param remoteFileName
     * @return
     * @Description 文件上传
     * @author Somnus
     */
    public boolean upload(File localFile, String remoteFilePath, String remoteFileName) {
        try {
            if (remoteFilePath == null) {
                log.error("remoteFilePath file is required.");
                throw new IOException("remoteFilePath file is required.");
            }
            //set ftp file transfer mode
            client.setFileType(FTP.BINARY_FILE_TYPE);
            //create directory
            client.makeDirectory(new String(remoteFilePath.getBytes(), client.getControlEncoding()));
            //change directory
            client.changeWorkingDirectory(remoteFilePath);
            //construct input stream
            InputStream is = new FileInputStream(localFile);
            //put file to server
            boolean stored = client.storeFile(remoteFileName, is);
            //close stream
            IOUtils.closeQuietly(is);
            log.info("upload {}", stored ? "successful" : "failed");
            return stored;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param remoteFilePath 远程全路径
     * @param localFile      本地文件全路径
     * @return
     * @Description 下载文件
     * @author Somnus
     */
    public boolean download(String remoteFilePath, File localFile) {
        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
            if (!localFile.getParentFile().exists()) {
                boolean dirCreated = localFile.getParentFile().mkdirs();
                log.info("directory created {}", dirCreated ? "successful" : "failed");
            }
            OutputStream os = new FileOutputStream(localFile);
            boolean received = client.retrieveFile(new String(remoteFilePath.getBytes(), client.getControlEncoding()), os);
            log.info("download {}", received ? "successful" : "failed");
            return received;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    /**
     * @Description 登出FTP
     * @author Somnus
     */
    public void logout() {
        try {
            client.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String oui = "ctnet";
        String directory = String.format("/%s/cer/", oui);
        String fileName = "ctnet_SN00001.cer";
        String filePath = directory + fileName;

        FTPClientHelper ftp = new FTPClientHelper("10.17.0.6", "21", "ctnet", "123456");
        ftp.login();
        if (!ftp.isRemoteDirExists(directory)) {
            log.warn("文件目录不存在");
            return;
        }
        if (!ftp.isRemoteFileExists(fileName)) {
            log.warn("文件不存在");
            return;
        }
        ftp.download(filePath, new File("D:/" + fileName));
        ftp.logout();
    }

}
