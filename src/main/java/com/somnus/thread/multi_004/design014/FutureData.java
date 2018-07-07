package com.somnus.thread.multi_004.design014;

/**
 * @author lyl
 */
public class FutureData implements Data{

	private RealData realData ;

	private boolean isReady = false;

	public synchronized void setRealData(RealData realData) {
		//如果已经装载完毕了，就直接返回
		if(isReady){
			return;
		}
		//如果没装载，进行装载真实对象
		this.realData = realData;
		isReady = true;
		//唤醒等待的程序，但是并没有把锁释放，notify方法是不释放锁的。
		this.notify();
	}

	@Override
	public synchronized String getRequest() {
		//如果没装载好 程序就一直处于阻塞状态
		while(!isReady){
			try {
				//等待唤醒
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//装载好直接获取数据即可
		return this.realData.getRequest();
	}

}
