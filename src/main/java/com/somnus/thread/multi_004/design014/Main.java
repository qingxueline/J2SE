package com.somnus.thread.multi_004.design014;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		FutureClient fc = new FutureClient();

		//异步去做操作
		Data data = fc.request("请求参数");

		//此处省略做其他事情的操作业务代码。意思就是上面的方法在执行的过程中完全不影响程序在做其他业务
		System.out.println("请求发送成功!");
		System.out.println("做其他的事情...");

		//因为里面代码使用 wait() 和 notify() 实现，如果数据没有封装好，那么就会进行等待，如果已经封装好，那么数据就会直接返回。
		String result = data.getRequest();
		System.out.println(result);

	}
}
