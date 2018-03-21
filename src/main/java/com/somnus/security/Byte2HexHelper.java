package com.somnus.security;

/** 
 * @Title: Byte2HexHelper.java 
 * @Package com.somnus.cipher 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月5日 下午5:47:26 
 * @version V1.0 
 */
public class Byte2HexHelper {
    
    /**
     * 从字节数组到十六进制字符串转换 
     * String byte2hex = Hex.encodeHexString(buff);
     * @param buff
     * @return
     */
    private static String byte2hex(byte[] buff) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buff.length; ++i) {
            sb.append(Integer.toHexString((buff[i] & 0xFF) | 0x100).substring(1,3));  
        }  
        return sb.toString();  
    }
    
    /**
     * 从字节数组到十六进制字符串转换 
     * String byte2hex = Hex.encodeHexString(buff);
     * @param buff
     * @return
     */
    private static String byte2hex2(byte[] buff) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buff.length; ++i) {
            final String HEX = "0123456789abcdef"; 
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt((buff[i] >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt(buff[i] & 0x0f));
        }
        return sb.toString();
    }
    
    /**
     * 从字节数组到十六进制字符串转换 
     * String byte2hex = Hex.encodeHexString(buff);
     * @param buff
     * @return
     */
    private static String byte2hex3(byte[] buff) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buff.length; i++) {
            String hex = Integer.toHexString(buff[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /**
     * 从十六进制字符串到字节数组转换
     * byte[] hex2byte = Hex.decodeHex(hexstr.toCharArray());
     * @param hexstr
     * @return
     */
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    
}
