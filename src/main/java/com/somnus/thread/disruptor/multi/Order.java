package com.somnus.thread.disruptor.multi;


import lombok.Data;

//订单（将会被生产的产品）
@Data
public class Order {
    private String id;//ID
    private String name;
    private double price;//金额
}