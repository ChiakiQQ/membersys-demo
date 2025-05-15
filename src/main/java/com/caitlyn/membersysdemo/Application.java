package com.caitlyn.membersysdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@SpringBootApplication
public class Application {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 清除 Redis 中所有 admin_session:* 開頭的快取鍵。
     * 這可避免在應用程式重新啟動後，因快取鎖定仍存在導致使用者無法重新登入的問題。
     * 該方法會在 Spring Boot 應用啟動完成後自動執行。
     */
    @EventListener(ApplicationReadyEvent.class)
    public void clearSessionKeysOnStartup() {
        try {
            Set<String> keys = redisTemplate.keys("admin_session:*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                System.out.println("Cleared " + keys.size() + " admin session keys from Redis on startup.");
            } else {
                System.out.println("No admin session keys found in Redis.");
            }
        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis 尚未啟動或連線失敗，略過清除快取。原因：" + e.getMessage());
        }
    }
}