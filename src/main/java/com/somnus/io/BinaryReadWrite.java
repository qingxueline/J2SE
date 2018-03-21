package com.somnus.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 数据流读和写数据
 * @Title: BinaryReadWrite.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:55:30 
 * @version V1.0
 */
public class BinaryReadWrite {
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private String s_FilePath = "target/classes/bin.dat";
	private byte[] buff = "{\"name\":\"alen\"}".getBytes();

	public BinaryReadWrite() {
		init();
	}

	private void init() {
		try {
			if (!new File(s_FilePath).exists()) {
				new File(s_FilePath).createNewFile();
			}
			dos = new DataOutputStream(new FileOutputStream(
					new File(s_FilePath)));
			dis = new DataInputStream(new FileInputStream(new File(s_FilePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeBinaryStream() throws IOException {
		if (dos != null) {
			dos.writeBoolean(true);

			char c = 'a';
			dos.writeChar((int) c);

			Double d = 12.567d;
			dos.writeDouble(d);

			Float f = 56.782f;
			dos.writeFloat(f);

			int k = 105;
			dos.writeInt(k);

			long l = 98765l;
			dos.writeLong(l);

			short st = 12;
			dos.writeShort(st);

			dos.writeInt(buff.length);
			dos.write(buff);

			dos.write("中国".getBytes(/*"UTF-8"*/));

			dos.write("中国".getBytes("GB2312"));
			
			dos.writeBytes("abc123;");
			
			dos.flush();
			dos.close();
		}
	}
	
	public void readBinaryStream() throws IOException {
		if (dis != null) {
			while (dis.available() > 0) {
				System.out.println(dis.readBoolean());
				System.out.println((char) dis.readChar());
				System.out.println(dis.readDouble());
				System.out.println(dis.readFloat());
				System.out.println(dis.readInt());
				System.out.println(dis.readLong());
				System.out.println(dis.readShort());
				
				System.out.println("*******************************");
				
				int len = dis.readInt();
				byte[] buffer = new byte[len];
				dis.read(buffer, 0, buffer.length);
				System.out.println(new String(buffer));
				
				System.out.println("*******************************");

				byte[] buffer2 = new byte[6];
				dis.read(buffer2, 0, buffer2.length);
				for(byte b:buffer2){
				    System.out.print(b+"|"+(b & 0xff)+"|"+Integer.toBinaryString(b & 0xff)+"|"+Integer.toHexString(b & 0xff)+"   ");
                }
				System.out.println(new String(buffer2/*, "UTF-8"*/));
				
				System.out.println("*******************************");
				
				byte[] buffer3 = new byte[4];
				dis.read(buffer3, 0, buffer3.length);
				for(byte b:buffer3){
				    System.out.print(b+"|"+(b & 0xff)+"|"+Integer.toBinaryString(b & 0xff)+"|"+Integer.toHexString(b & 0xff)+"   ");
                }
				System.out.println(new String(buffer3, "GB2312"));
				
				System.out.println("*******************************");
				
				byte[] buffer4 = new byte[7];
				dis.read(buffer4, 0, buffer4.length);
				for(byte b:buffer4){
					System.out.println("#"+b);
				}
				System.out.println(new String(buffer4));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BinaryReadWrite bin = new BinaryReadWrite();
		bin.writeBinaryStream();
		bin.readBinaryStream();
	}
}