package com.somnus.async;

/**
 * @Description: 蛋糕师傅
 * @author Somnus
 * @date 2016年3月9日 下午7:22:52
 * @version 1.0
 */
public class CakeBaker implements Cake {
	private final String cake;

	public CakeBaker(int count, char c) {
		System.out.println("making cake(" + count + ", " + c + ") BEGIN");
		char[] buffer = new char[count];
		for (int i = 0; i < count; i++) {
			buffer[i] = c;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("making cake(" + count + ", " + c + ") END");
		this.cake = new String(buffer);
	}

	public String getCake() {
		return cake;
	}

}
