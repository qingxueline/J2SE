package com.somnus.string;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * Charsets静态
 *
 * @author lyl
 * @version 2020/4/23 0023 12:36:44
 */
public class Charsets {
    @Test
    public void CharsetsTest() {
        System.out.println("UTF-8:" + StandardCharsets.UTF_8);
        System.out.println("ISO_8859_1:" + StandardCharsets.ISO_8859_1);
        System.out.println("UTF_16:" + StandardCharsets.UTF_16);
        System.out.println("UTF_16BE:" + StandardCharsets.UTF_16BE);
        System.out.println("UTF_16LE:" + StandardCharsets.UTF_16LE);
    }
}
