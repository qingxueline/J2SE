package com.somnus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/** 
 * @description: TODO 
 * @author Somnus
 * date 2015年4月2日 下午2:17:26  
 */
public class ClientTcpSend {
    public static void main(String[] args) {
        
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 33456),10 * 1000);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            File file = new File("src/main/resources/luoli.jpg");
            fis = new FileInputStream(file);
            byte[] buff = new byte[1024];
            dos.writeUTF(file.getName());
            System.out.println("发送文件名之后的响应:"+dis.readUTF());
            int len = 0;
            while((len = fis.read(buff, 0, buff.length))!=-1){
                dos.write(buff,0,len);
                dos.flush();
                System.out.println("成功一次就响应一次:"+dis.readUTF());
            }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                try {
                    if (dos != null)
                        dos.close();
                    if (dis != null)
                        dis.close();
                    if (fis != null)
                        fis.close();
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
