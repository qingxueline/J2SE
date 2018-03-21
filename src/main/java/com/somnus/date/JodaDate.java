package com.somnus.date;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.junit.Test;

/** 
 * @Description: TODO
 * @author Somnus
 * @date 2015年8月6日 下午4:25:45 
 * @version V1.0 
 */
public class JodaDate {
    
    @Test
    public void now(){
        System.out.println(DateTime.now().toString("yyyy-MM-dd"));
    }
    
    @Test
    public void tostring(){
        DateTime dateTime = new DateTime(2015, 11, 11, 0, 0, 0, 0);
        System.out.println(dateTime.toString("yyyy-MM-dd"));
    }
    
    @Test
    public void plusDays(){
        //加上 9 天
        System.out.println(DateTime.now().plusDays(9).toString("yyyy-MM-dd"));
    }
    
    @Test
    public void dayOfWeek(){
        // 当前周的周一，周日  
        System.out.println(String.format(  
                "min:%s, max:%s",  
                DateTime.now().dayOfWeek().withMinimumValue()  
                        .toString("yyyy-MM-dd"),  
                DateTime.now().dayOfWeek().withMaximumValue()  
                        .toString("yyyy-MM-dd")));  
    }
    
    @Test
    public void dayOfMonth(){
        // 当前月的第一天和最后一天  
        System.out.println(String.format(  
                "min:%s, max:%s",  
                DateTime.now().dayOfMonth().withMinimumValue()  
                        .toString("yyyy-MM-dd"),  
                DateTime.now().dayOfMonth().withMaximumValue()  
                        .toString("yyyy-MM-dd")));  
    }
    
    @Test
    public void dayOfYear(){
     // 当前年的第一天和最后一天  
        System.out.println(String.format(  
                "min:%s, max:%s",  
                DateTime.now().dayOfYear().withMinimumValue()  
                        .toString("yyyy-MM-dd"),  
                DateTime.now().dayOfYear().withMaximumValue()  
                        .toString("yyyy-MM-dd"))); 
    }
    
    @Test
    public void calendar(){
        //Joda 和 JDK 互操作性
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.now().toDate());
        System.out.println(calendar.get(Calendar.YEAR));
    }
    
    @Test
    public void getDayOfMonth(){
        //在当月第几天
        System.out.println(DateTime.now().getDayOfMonth());
    }
    
}
