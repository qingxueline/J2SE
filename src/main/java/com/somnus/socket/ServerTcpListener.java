package com.somnus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * @description: TODO 
 * @author Somnus
 * date 2015年4月2日 下午2:20:46  
 */
public class ServerTcpListener {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            final ServerSocket server = new ServerSocket(33456);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                           System.out.println("开始监听。。。");
                           Socket socket = server.accept();
                           System.out.println("有链接");
                           receiveFile(socket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            th.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void receiveFile(Socket socket){
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileOutputStream fos = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            fos = new FileOutputStream(new File("E:\\"+dis.readUTF()));
            System.out.println("给予接收到文件名响应");
            dos.writeUTF("1111");
            byte[] buff = new byte[1024];
            System.out.println("开始接收数据...");
            int len = 0;
            //成功读一次就响应0000
            while ((len = dis.read(buff, 0, buff.length)) != -1) {
                System.out.println("每次读到的字节数长度："+len);
                fos.write(buff, 0, len);
                fos.flush();
                //System.out.println("给予接收到文件字节响应");
                dos.writeUTF("0000");
            }
            System.out.println("完成接收");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (dos != null)
                    dos.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
