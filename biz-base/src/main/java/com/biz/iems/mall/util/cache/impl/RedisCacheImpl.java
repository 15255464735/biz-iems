package com.biz.iems.mall.util.cache.impl;

import com.alibaba.fastjson.JSON;
import com.biz.iems.mall.util.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Objects;

@Component
public class RedisCacheImpl implements RedisCache {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean set(String key, Object value) {
        if(Objects.equals(value, null)){
            return false;
        }
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        return true;
    }

    @Override
    public boolean set(String key, Object value, int timeOut) {
        if(Objects.equals(value, null)){
            return false;
        }
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value), timeOut);
        return true;
    }

    @Override
    public <T> T get(String key) {
        try {
            String value = stringRedisTemplate.opsForValue().get(key);
            if(Objects.equals(value, null)){
                return null ;
            }
            return (T)JSON.parse(value);
        }catch (Exception e){
            return null ;
        }
    }

    @Override
    public boolean remove(String key) {
        return stringRedisTemplate.delete(key);
    }
}
