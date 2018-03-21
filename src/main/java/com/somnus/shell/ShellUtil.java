package com.somnus.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年8月5日 上午10:33:42 
 * @version V1.0 
 */
public class ShellUtil {
    /** 
     * 数据加密处理 
     *  
     * @param data 要加密的数据 
     * @param commonKey 加密口令文件名 
     * @return　加密数据 
     */  
    public static String encryption(String data, String key){
        // 加密后的数据定义  
        String encryptionData = "";
        try {
            // 加密命令  
            String encryption = "echo {0} | openssl enc -aes-128-cbc -e -a -k {1}";
            // 替换命令中占位符  
            encryption = MessageFormat.format(encryption, data, key);
            System.out.println(encryption);
            String[] sh = new String[]{"/bin/sh", "-c", encryption};
            // Execute Shell Command
            ProcessBuilder pb = new ProcessBuilder(sh);
            Process p = pb.start();
            encryptionData = getShellOut(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptionData;
    }
    
    /** 
     * 读取输出流数据 
     *  
     * @param p 进程 
     * @return 从输出流中读取的数据 
     * @throws IOException 
     */  
    private static String getShellOut(Process p) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedInputStream in = null;
        BufferedReader br = null;
        try {
            in = new BufferedInputStream(p.getInputStream());
            br = new BufferedReader(new InputStreamReader(in));
            String s;
            while ((s = br.readLine()) != null) {
                // 追加换行符 
                sb.append('\n');
                sb.append(s);
            }
        } catch (IOException e) {
            throw e;  
        } finally {
            br.close();
            in.close();
        }
        return sb.toString();
    }
    
}
