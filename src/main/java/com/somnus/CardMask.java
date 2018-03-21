package com.somnus;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2016年1月15日 下午2:33:37 
 * @version V1.0 
 */
public class CardMask {
    
    @Test
    public void mask(){
        String cardNo = "6226095722249733";
        String mask = StringUtils.rightPad(StringUtils.left(cardNo, 6), 
                cardNo.length()-4, 
                '*')
                .concat(StringUtils.right(cardNo, 4));
        System.out.println(mask);
    }
    
    @Test
    public void mask2(){
        String cardNo = "62260957222333333349733";
        String mask = cardNo.replaceAll("(\\d{6})\\d+(\\d{4})$",
                        "$1"
                        + StringUtils.repeat('*', cardNo.length()-10)
                        +"$2");
        System.out.println(mask);
    }

}
