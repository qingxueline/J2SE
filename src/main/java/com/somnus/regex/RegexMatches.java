package com.somnus.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @author Somnus
 * @date 2015年11月2日 下午5:22:45
 * @version V1.0
 */
public class RegexMatches {
    
    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String line = "2016-01-24,PAY000000192303   ,677824749  ,"
                + "310000067575   ,2016-01-24 10:42:19,1  ,\"1,000,000.00\",01 ,   ,,Y ,";
        
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("((\\d{1,3}(,\\d{3})*)|(\\d+))\\.\\d{2}?");

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        
        if (m.find()) {
           System.out.println("Found value: " + m.group() );
        } else {
           System.out.println("NO MATCH");
        }
    }
}
