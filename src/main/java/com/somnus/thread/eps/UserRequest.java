package com.somnus.thread.eps;

/**
 * Created by lyl on 2018/3/20 0020.
 */
public class UserRequest implements Runnable{
    private int id;

    public UserRequest(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(id);
    }
}
