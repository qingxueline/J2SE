package com.somnus.Listener;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * description:通过Swing认识事件<br/>
 * <p>事件三要素
 * 1、事件源。（如果是在web领域，事件源只有3个ServletContext、HttpSession、ServletRequest）
 * 2、事件。
 * 3、时间监听器和处理方法
 * <p>
 * User: LiangYongLong
 * Date: 2016/9/9 15:53
 */
public class JFrame {
    public static void main(String[] args) {
        Frame frame = new Frame("我的窗体");
        //设置窗体的大小
        frame.setSize(200, 300);
        //设置窗体位置
        frame.setLocation(150, 200);
        //将不可见的窗口显示
        frame.setVisible(true);
        //为窗体注册窗体监听器
        frame.addWindowListener(new MyHandler());
    }

    //监听器
    static class MyHandler extends WindowAdapter {//WindowAdapter是一个适配器

        //事件处理
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("窗口关闭了");
            //获取事件源
            Frame frame = (Frame) e.getSource();
            //异常窗体
            frame.setVisible(false);
            //关闭jvm
            System.exit(1);

        }
    }
}
