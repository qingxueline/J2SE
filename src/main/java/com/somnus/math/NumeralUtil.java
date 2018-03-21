package com.somnus.math;

import java.text.DecimalFormat;

public class NumeralUtil {
    public static final String FORMAT_ONE = "###,###.##";
    public static final String FORMAT_TWO = "0.00";
    public static final String FORMAT_THREE = "0.00%";

    public static String numeralToString(double num, String format) {
        DecimalFormat formater = new DecimalFormat(format);

        String str = formater.format(num);

        return str;
    }

    public static String numeralToString(long num, String format) {
        DecimalFormat formater = new DecimalFormat(format);

        String str = formater.format(num);

        return str;
    }

    public static String extractNumeric(String data) {
        String str = "";
        if (data != null && !"".equals(data)) {
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) >= 48 && data.charAt(i) <= 57) {
                    str += data.charAt(i);
                }
            }
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(NumeralUtil.numeralToString(222212.123, NumeralUtil.FORMAT_ONE));
        System.out.println(NumeralUtil.numeralToString(222212, NumeralUtil.FORMAT_ONE));

        System.out.println(NumeralUtil.numeralToString(222212.123, NumeralUtil.FORMAT_TWO));
        System.out.println(NumeralUtil.numeralToString(0, NumeralUtil.FORMAT_TWO));

        System.out.println(NumeralUtil.numeralToString(0.7654, NumeralUtil.FORMAT_TWO));

        System.out.println(NumeralUtil.numeralToString((50.0 / 13113), NumeralUtil.FORMAT_THREE));

        System.out.println(Double.valueOf(NumeralUtil.numeralToString(50, NumeralUtil.FORMAT_TWO)));

        System.out.println(NumeralUtil.extractNumeric("abcd13579xyz24680"));
    }

}
