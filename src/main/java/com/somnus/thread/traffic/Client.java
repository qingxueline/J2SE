package com.somnus.thread.traffic;
/**
 * 交通灯项目需求如下：
1.        要求模拟一个十字路口的交通灯控制系统，具体要求如下：
 
2.        用程序模拟出各个路线上汽车奔跑的过程；
 
3.        只考虑绿色和红色信号灯，忽略黄色信号灯；
 
4.        只考虑左转信号灯，不用考虑右转信号灯；
 
5.        具体信号灯控制逻辑应当与真实交通灯控制逻辑一样，东西向与南北向交替放行；
 
6.        每辆车通过时间为1s；
 
7.        随机生成车辆时间间隔以及红绿灯交替切换时间间隔自定，可以设置；
 
8.        通过Log方式在控制台实现模拟

 */
public class Client{
	public static void main(String[] args){

		/* 产生12个方向的路线 */
		String[] directions = new String[] { "S2N", "S2W", "E2W", "E2S", "N2S",
				"N2E", "W2E", "W2N", "S2E", "E2N", "N2W", "W2S" };
		for (int i = 0; i < directions.length; i++){
			new Road(directions[i]);
		}
		/* 产生整个交通灯系统 */
		new LampController();
	}
}
