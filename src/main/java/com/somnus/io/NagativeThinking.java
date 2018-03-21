package com.somnus.io;

/** 
 * @Title: NagativeThinking.java 
 * @Package com.somnus.io 
 * @Description: TODO
 * @author Somnus
 * @date 2015年5月20日 下午4:25:27 
 * @version V1.0 
 */
public class NagativeThinking {
    /**
     * @see http://blog.csdn.net/zzycgfans/article/details/6782989
     * @see http://www.linuxidc.com/Linux/2015-02/113863.htm
     * @param args
     */
    public static void main(String[] args) {
        /* Demo     7          -7               -7
         * 原码          00000111    1 0000111     1 000000000000000000000000000111
         * 反码          00000111    1 1111000     1 111111111111111111111111111000
         * 补码          00000111    1 1111001     1 111111111111111111111111111001
         */
        int a = -7;//
        System.out.println(Integer.toBinaryString(a));//1 1111111111111111111111111111001
        System.out.println(Integer.toHexString(a));//fffffff9
        //111111111111111111111111 11111001 都要以补码的形式参与计算(它的原码是1000000000000000000000000000111)
        //             &
        //000000000000000000000000 11111111 都要以补码的形式参与计算（它的原码是自身）
        //000000000000000000000000 11111001
        System.out.println(Integer.toBinaryString(a & 0xff/*255*/));//1 1111001
        System.out.println(Integer.toHexString(a & 0xff/*255*/));//f9
        
        System.out.println("*******************************************************");
        
        int c = 7;
        byte d = 7;
        System.out.println(Integer.toBinaryString(c));//0 0000000000000000000000000000111
        System.out.println(Integer.toBinaryString(d));//0 0000000000000000000000000000111
        System.out.println(Integer.toHexString(d));//7
    }

}
