package com.somnus.designPatterns.strategy;

public class Client {
    public static void main(String[] args) throws Exception {
        MovieTicket mt = new MovieTicket();
        double originalPrice = 60.0;

        mt.setPrice(originalPrice);
        System.out.println("原始价为：" + originalPrice);
        System.out.println("---------------------------------");

        Discount discount = (Discount) XMLUtil.getBean(); // 读取配置文件并反射生成具体折扣对象
        mt.setDiscount(discount); // 注入折扣对象

        double currentPrice = mt.getPrice();
        System.out.println("折后价为：" + currentPrice);
    }
}
