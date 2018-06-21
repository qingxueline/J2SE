package com.somnus.thread.multi_001.sync004;

/**
 * 业务整体需要使用完整的synchronized，保持业务的原子性。
 *
 * @author alienware
 */
public class DirtyRead {

    private String username = "bjsxt";
    private String password = "123";

    public synchronized void setValue(String username, String password) {
        this.username = username;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("setValue最终结果：username = " + username + " , password = " + password);
    }

    public /*synchronized*/ void getValue() {
        System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
    }


    public static void main(String[] args) throws Exception {

        final DirtyRead dr = new DirtyRead();

        //启动一个线程去做业务修改
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                dr.setValue("z3", "456");
            }
        });
        t1.start();
        Thread.sleep(1000);

        //主线程在上面的线程中还没执行完毕就可以执行查询操作，导致获取到的结果和我们预期结果不一致。产生了脏读。所以为了保障原子性，getValue()也必须使用同步的方法，这样才能产生预期的结果。
        dr.getValue();
    }


}
