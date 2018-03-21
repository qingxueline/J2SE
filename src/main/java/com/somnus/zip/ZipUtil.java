package com.somnus.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/** 
 * @Title: ZipUtil.java 
 * @Package com.somnus.zip 
 * @Description: TODO
 * @author Somnus
 * @date 2015年5月27日 下午5:37:09 
 * @version V1.0 
 */
public class ZipUtil {
    public static byte[] zip() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zos.setEncoding("gbk");
        try {
            zos.putNextEntry(new ZipEntry("文件1.txt"));
            zos.write("文件111111111".getBytes(/*"UTF-8"*/));
            
            zos.putNextEntry(new ZipEntry("文件2.txt"));
            zos.write("文件222222222".getBytes(/*"UTF-8"*/));
            
            zos.flush();
            zos.finish();
        } finally{
            zos.close();
            baos.close();
        }
        return baos.toByteArray();
    }
    
    public static void main(String[] args) throws IOException {
        OutputStream os = new FileOutputStream(new File("target/classes/测试.zip"));
        os.write(zip());
        os.close();
    }
}
