package com.caitlyn.membersysdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

    /**
     * 嘗試續命指定 key 的 TTL。
     * 僅當目前的值與期望值一致時，才更新該 key 的過期時間。
     *
     * @param key 指定的 Redis key
     * @param expectedValue 預期目前的值（例如 sessionId）
     * @param additionalSeconds 欲延長的 TTL 秒數（會覆蓋原本的 TTL）
     * @return 是否成功續命
     */
    public boolean renewIfMatch(String key, String expectedValue, int additionalSeconds) {
        Object currentValue = redisTemplate.opsForValue().get(key);
        if (expectedValue.equals(currentValue)) {
            redisTemplate.expire(key, Duration.ofSeconds(additionalSeconds));
            return true;
        }
        return false;
    }
}