package me.wuwenbin.items.sso.eurekaclient.context;

import lombok.Getter;

/**
 * created by Wuwenbin on 2018/1/4 at 22:50
 *
 * @author wuwenbin
 */
public class CacheBean<K, V> {

    private final K key;
    private final V value;

    /**
     * 上次访问时间
     */
    @Getter
    private long lastAccess;
    /**
     * 访问次数
     */
    @Getter
    private int accessCount;
    /**
     * 对象存活时长，0表示永久存活
     */
    @Getter
    private long ttl;


    CacheBean(K key, V value, long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
        this.lastAccess = System.currentTimeMillis();
    }


    /**
     * @return 是否过期
     */
    boolean isExpired() {
        return (ttl > 0) && (lastAccess + ttl < System.currentTimeMillis());
    }

    /**
     * @return 获得对象
     */
    V get() {
        lastAccess = System.currentTimeMillis();
        accessCount++;
        return value;
    }

}
