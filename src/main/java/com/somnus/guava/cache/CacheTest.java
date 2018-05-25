package com.somnus.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 缓存在很多场景下都是相当有用的。例如，计算或检索一个值的代价很高，并且对同样的输入需要不止一次获取值的时候，就应当考虑使用缓存。
 * <p>
 * Guava Cache与ConcurrentMap很相似，但也不完全一样。最基本的区别是ConcurrentMap会一直保存所有添加的元素，直到显式地移除。相对地，Guava Cache为了限制内存占用，通常都设定为自动回收元素。在某些场景下，尽管LoadingCache 不回收元素，它也是很有用的，因为它会自动加载缓存。
 * <p>
 * Guava Cache是一个全内存的本地缓存实现，它提供了线程安全的实现机制。
 * <p>
 * 通常来说，Guava Cache适用于：
 * <p>
 * •你愿意消耗一些内存空间来提升速度。
 * <p>
 * <p>
 * •你预料到某些键会被查询一次以上。
 * <p>
 * <p>
 * •缓存中存放的数据总量不会超出内存容量。（Guava Cache是单个应用运行时的本地缓存。它不把数据存放到文件或外部服务器。
 * <p>
 * <p>
 * 如果这不符合你的需求，请尝试Memcached这类工具）
 * <p>
 * Guava Cache有两种创建方式：
 * •cacheLoader
 * •callable callback
 * <p>
 * Created by lyl on 2018/3/22 0022.
 */
public class CacheTest {

    @Test
    public void LoadingCacheDemoTest() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //最大缓存数目
                .maximumSize(100)
                //设置时间对象没有被读/写访问，则对象1秒后从内存中删除
                .expireAfterAccess(1, TimeUnit.SECONDS)
                //设置时间对象没有被写访问则，对象1秒从内存中删除
                .expireAfterWrite(1, TimeUnit.SECONDS)
                //CacheLoader类 实现自动加载
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        //缓存过期从SQL或者NoSql 获取对象。此处我们之间返回Key.
                        return key;
                    }
                });
        cache.put("j", "java");
        cache.put("c", "cpp");
        cache.put("s", "scala");
        cache.put("g", "go");
        try {
            System.out.println(cache.get("j"));
            TimeUnit.SECONDS.sleep(2);
            System.out.println(cache.get("s")); //缓存过期后，返回key。会输出s
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void CallbackDemoTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        try {
            String result = cache.get("java", () -> "hello java");
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * refresh机制：
     * - LoadingCache.refresh(K) 在生成新的value的时候，旧的value依然会被使用。
     * - CacheLoader.reload(K, V) 生成新的value过程中允许使用旧的value
     * - CacheBuilder.refreshAfterWrite(long, TimeUnit) 自动刷新cache
     */
}
