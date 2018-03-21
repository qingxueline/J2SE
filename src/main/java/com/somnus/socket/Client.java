package com.somnus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @description: TODO
 * @author Somnus 
 * date 2015年3月23日 下午1:53:14
 */
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            // 获取输出流，用于客户端向服务器端发送数据
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            // 获取输入流，用于接收服务器端发送来的数据
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            // 客户端向服务器端发送数据
            dos.writeUTF("我是客户端，请求连接!");
            dos.writeInt(10);
            // 打印出从服务器端接收到的数据
            System.out.println(dis.readUTF());
            System.out.println(dis.readUTF());
            // 不需要继续使用此连接时，记得关闭哦
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
