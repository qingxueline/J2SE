package com.somnus.guava.cache2;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lyl
 * @version V1.0
 * @project J2SE
 * @package com.somnus.guava
 * @date 2018/05/25 15:14
 * @description
 */
public class CacheObject {
    private Object value;
    private Date expireDt;

    /**
     * 永不过期
     * @param key
     * @param value
     */
    public CacheObject(Object value) {
        this.value = value;
        this.expireDt = null;
    }

    /**
     * 过期时间为dt
     * @param key
     * @param obj
     * @param dt
     */
    public CacheObject(Object value, Date dt) {
        this.value = value;
        this.expireDt = dt;
    }

    /**
     * 过期时间为ageInSeconds秒之后
     * @param key
     * @param obj
     * @param ageInSeconds
     */
    public CacheObject(Object value, long ageInSeconds) {
        this.value = value;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + ageInSeconds*1000);
        this.expireDt = calendar.getTime();
    }

    public boolean isAvailable() {
        return expireDt == null || expireDt.getTime() >= System.currentTimeMillis();
    }

    public Date getExpireDt() {
        return expireDt;
    }
    public void setExpireDt(Date expireDt) {
        this.expireDt = expireDt;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CacheObject [value=" + value + ", expireDt=" + expireDt + "]";
    }


}
