package com.caitlyn.membersysdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 列出所有 key
    public Set<String> listAllKeys() {
        return redisTemplate.keys("*");
    }

    // 取得指定 key 的 value
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 刪除指定 key
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}