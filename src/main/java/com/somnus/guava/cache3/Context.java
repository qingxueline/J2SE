package com.somnus.guava.cache3;

/**
 * @author lyl
 * @version V1.0
 * @project monitor-api
 * @package com.gsta.monitor.common.cache
 * @date 2018/05/25 15:32
 * @description 使用策略设计模式获取缓存
 */
public class Context<K, V> {
    /**
     * 定义一个获取缓存策略
     **/
    private ILocalCacheStrategy<K, V> iLocalCacheStrategy;

    public Context(ILocalCacheStrategy<K, V> iLocalCacheStrategy) {
        this.iLocalCacheStrategy = iLocalCacheStrategy;
    }

    /**
     * 获取缓存
     **/
    public V get(K key) {
        return iLocalCacheStrategy.get(key);
    }
}
