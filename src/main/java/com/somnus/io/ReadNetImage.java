package com.somnus.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 读取网络图片转换为二进制数据
 * @Title: ReadNetImage.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月9日 下午4:58:35 
 * @version V1.0
 */
public class ReadNetImage {
    /**
     * 
     * @param imageUrl
     *              图片网络地址
     * @return
     * @throws IOException
     */
	public byte[] getImageUrl(String imageUrl) throws IOException {
		byte[] fileData = null;
		InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            is = httpConn.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int len = 0;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, len);
            }
            fileData = bos.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            is.close();
            bos.close();
        }
		return fileData;
	}
}