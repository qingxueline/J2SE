package com.somnus;

/**
 * 
 * @Title: MaxSameString.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:33:34 
 * @version V1.0
 */
public class MaxSameString {
	/**
	 * 求两个字符拥有最长相同并且相连字符串
	 * @param str1
	 * 					例 ：abcdefg
	 * @param str2
	 * 					例 ：xxxabxxxxcdexxx
	 * @return   
	 * 					返回 cde
	 */
	public String  getResualt(String str1,String str2) {
		String max = "";
		for (int i = 0; i < str2.length(); i++) {
			String temp1 = str2.substring(i);
			for (int y = temp1.length() - 1; y >= 0; y--) {
				String temp2 = temp1.substring(0, y);
				if (str1.indexOf(temp2) != -1 && temp2.length() > max.length())
					max = temp2;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		MaxSameString msss = new MaxSameString();
		System.out.println(msss.getResualt("abcdefg","xxxabxxxxcdexxx"));
	}
}
