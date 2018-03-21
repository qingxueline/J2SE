package com.somnus.uuid;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author Somnus
 * @version V1.0
 * @Title: Identities.java
 * @Package com.somnus.uuid
 * @Description: TODO
 * @date 2015年5月28日 下午5:17:11
 */
public class Identities {

    private static SecureRandom random = new SecureRandom();

    private static final String RANDOM_STRING_RANGE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String RANDOM_NUMBER_RANGE = "0123456789";

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割
     * (128Bits)
     */
    public static String get32LenUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割
     */
    public static String get36LenUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 生成指定位数的随机字符串
     *
     * @param length
     * @param isNumber
     * @return
     */
    public static String randomString(int length, boolean isNumber) {
        StringBuilder builder = new StringBuilder(isNumber ? RANDOM_NUMBER_RANGE : RANDOM_STRING_RANGE);
        StringBuilder resultBuilder = new StringBuilder();
        Random r = new Random();
        int range = builder.length();
        for (int i = 0; i < length; i++) {
            resultBuilder.append(builder.charAt(r.nextInt(range)));
        }
        return resultBuilder.toString();
    }

    /**
     * 生成流水号
     *
     * @return String
     */
    public static String get28LenNonce() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }

        /**
         * 字符串格式化说明，更多参考String类的格式字符串语法
         * <p>
         * 0 代表前面填充0
         * 14 代表长度为14
         * d 代表参数为正数
         * <p/>
         */
        return df.format(date) + String.format("%014d", hashCodeV);
    }

    public static void main(String[] args) {
        System.out.println("28LenNonce:" + Identities.get28LenNonce());
        System.out.println("32LenUUID:" + Identities.get32LenUUID());
        System.out.println("36LenUUID:" + Identities.get36LenUUID());
        System.out.println("randomString:" + Identities.randomString(10, true));
        System.out.println("randomLong:" + Identities.randomLong());
    }

}
