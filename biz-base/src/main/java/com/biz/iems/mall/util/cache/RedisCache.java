package com.biz.iems.mall.util.cache;

public interface RedisCache {
    /**
     * set存数据
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Object value);

    /**
     * set存数据
     * @param key
     * @param value
     * @param timeOut
     * @return
     */
    boolean set(String key, Object value, int timeOut);

    /**
     * get获取数据
     * @param key
     * @return
     */
    <T> T get(String key);

    /**
     * 移除数据
     * @param key
     * @return
     */
    boolean remove(String key);
}
