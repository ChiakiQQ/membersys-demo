package com.caitlyn.membersysdemo.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系統啟動時自動執行的多執行緒 demo 任務。
 * 每條 thread 持續嘗試搶佔隨機 key 並寫入自己的名稱。
 */
@Component
public class MultiThreadDemoService {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadDemoService.class);
    /**
     * 固定大小的執行緒池，用來同時啟動最多 5 條執行緒。
     * Executors.newFixedThreadPool(n) 會建立一個最多 n 條執行緒的池，
     * 超過的任務會排隊等待空閒的執行緒處理，適合負載可預測的情境。
     */
    private final ExecutorService worker = Executors.newFixedThreadPool(5);
    private final Boolean isRetry = false; //搶完是否重啟
    private final RedisTemplate<String, Object> redisTemplate;
    private final Random random = new Random();


    public MultiThreadDemoService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 啟動後自動執行的方法，會在 Spring 完成所有依賴注入後呼叫。
     * 用來初始化並啟動 5 條執行緒模擬 Redis 併發搶 key 寫入測試。
     * 每條 thread 持續嘗試搶佔隨機 key 並寫入自己的名稱。
     */
    @PostConstruct
    public void runDemo() {
        logger.info("啟動多執行緒 Redis 搶佔寫入測試");
        redisTemplate.delete("thread_total_success");
        redisTemplate.delete("finished_thread_count");

        startThreads();
    }

    private void startThreads() {
        for (int i = 1; i <= 5; i++) {
            int threadId = i;

            // 每個執行緒持續執行嘗試搶佔 Redis key 的任務直到成功 5 次後退出。
            // 使用無限迴圈模擬常駐背景任務，透過 Thread.sleep 控制頻率。
            Runnable task = () -> {
                String threadName = "Thread-" + threadId;
                int successCount = 0;
                while (true) {
                    int keyNum = random.nextInt(5) + 1;
                    String key = "thread_key:" + keyNum;
                    String value = threadName + " got!";

                    Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(10));
                    if (Boolean.TRUE.equals(success)) {
                        logger.info("[{}] 搶到 key={} 並寫入 '{}'  (累計成功 {} 次), TTL=10s", threadName, key, value, successCount);

                        Long totalSuccess = redisTemplate.opsForValue().increment("thread_total_success", 1);
                        if (totalSuccess == null) {
                            totalSuccess = 1L; // 預設值或錯誤處理
                        }
                        logger.info("[{}] 累計所有執行緒成功搶到 {} 次", threadName, totalSuccess);

                        successCount++;
                        if (successCount >= 5) {
                            logger.info("[{}] 成功搶到 5 次，進入休息狀態", threadName);

                            synchronized (MultiThreadDemoService.class) {
                                Long finished = redisTemplate.opsForValue().increment("finished_thread_count", 1);
                                if (finished != null && finished >= 5) {
                                    logger.info("---------------------------");
                                    redisTemplate.delete("finished_thread_count");

                                    logger.info("全部執行緒已完成，{}", isRetry ? "重啟所有執行緒" : "休息");
                                    if (isRetry) {
                                        runDemo();
                                    }
                                }
                            }
                            return;
                        }
                    } else {
                        Object existing = redisTemplate.opsForValue().get(key);
                        logger.info("[{}] 寫入失敗，key={} 已被占用，現有值為 {}", threadName, key, existing);
                    }

                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            };

            // 將任務提交給執行緒池執行，會由 pool 中空閒的執行緒接手執行
            worker.submit(task);
        }
    }
}