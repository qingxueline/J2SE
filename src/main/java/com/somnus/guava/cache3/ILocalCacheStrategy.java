package com.somnus.guava.cache3;

/**
 * @author lyl
 * @version V1.0
 * @project monitor-api
 * @package com.gsta.monitor.common.cache
 * @date 2018/05/25 1:29
 * @description 本地缓存接口，如果要通过策略设计模式获取缓存，那么需要实现该接口
 */
public interface ILocalCacheStrategy<K, V> {
    /**
     * 策略方法。从缓存中获取数据
     *
     * @param key .
     * @return value
     */
    public V get(K key);
}