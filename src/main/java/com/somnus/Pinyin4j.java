package com.somnus;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @Title: Pinyin4j.java 
 * @Package com.somnus 
 * @Description: TODO
 * @author Somnus
 * @date 2015年6月8日 上午11:34:20 
 * @version V1.0
 */
public class Pinyin4j {
    
	public static void main(String[] args) {
		/*
		 * String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('单');
		 * for (int i = 0; i < pinyinArray.length; ++i) {
		 * System.out.println(pinyinArray[i]); }
		 */
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		String[] pinyinArray = null;
		try {
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray('单', format);
		} catch (BadHanyuPinyinOutputFormatCombination e){
			e.printStackTrace();
		}
		for (int i = 0; i < pinyinArray.length; ++i) {
			System.out.println(pinyinArray[i]);
		}
		
		
		HanYu hanyu = new HanYu();
         // 中英文混合的一段文字
         String str = "少壮不努力自挂东南枝，study hard hard,day day up";
         String strPinyin = hanyu.getStringPinYin(str);
         System.out.println(strPinyin);
	}

}

class HanYu {
	private HanyuPinyinOutputFormat format = null;
	private String[] pinyin;
	public HanYu(){
		format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		pinyin = null;
	}
	// 转换单个字符
	public String getCharacterPinYin(char c){
		try{
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		}
		catch (BadHanyuPinyinOutputFormatCombination e){
			e.printStackTrace();
		}
		// 如果c不是汉字，toHanyuPinyinStringArray会返回null
		if (pinyin == null)
			return null;
		// 只取一个发音，如果是多音字，仅取第一个发音
		return pinyin[0];
	}

	// 转换一个字符串
	public String getStringPinYin(String str){
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i){
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null){
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			}
			else{
				sb.append(tempPinyin+" ");
			}
		}
		return sb.toString();
	}
}