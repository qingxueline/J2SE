package com.somnus.guava.cache;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * 抽象缓存类、缓存模板。
 * 子类需要实现fetchData从数据库或其他数据源中获取数据。
 *
 * @param <K> key type
 * @param <V> value type
 * @author Xu
 */
public abstract class MyAbstractLoadingCache<K, V> {
    //缺省的初始化参数
    private static int maximumSize = 100;                    //最大缓存条数
    private static int refreshAfterWriteDuration = 2;    //刷新数据时间
    private static TimeUnit timeUnit = TimeUnit.SECONDS;    //缺省时间单位（秒）

    LoadingCache<K, V> cache;

    public LoadingCache<K, V> getCache() {
        return cache;
    }

    public MyAbstractLoadingCache() {
        this(maximumSize, refreshAfterWriteDuration, timeUnit);
    }

    public MyAbstractLoadingCache(int maximumSize, int refreshAfterWriteDuration, TimeUnit timeUnit) {
        //缓存数据的最大条目
        cache = CacheBuilder.newBuilder().maximumSize(maximumSize)
                //记录被创建多少时间后被更新
                //.refreshAfterWrite(refreshAfterWriteDuration, timeUnit)
                //记录被创建多少时间后被移除
                .expireAfterWrite(10, timeUnit)
                .recordStats()
                .removalListener(new RemovalListener<K, V>() {
                    @Override
                    public void onRemoval(RemovalNotification<K, V> rn) {
                        System.out.println(rn.getKey() + "被移除");

                    }
                }).build(new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) throws Exception {
                        return fetchData(key);
                    }
                });
    }

    /**
     * 从数据库或其他数据源中获取一个key-value，并被加载到缓存中。
     *
     * @param key
     * @return 连同key一起被加载到缓存中的。
     * @author Xu
     */
    protected abstract V fetchData(K key);
}
