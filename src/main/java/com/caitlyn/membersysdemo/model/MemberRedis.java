package com.caitlyn.membersysdemo.model;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class MemberRedis {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Random random = new Random();

    public MemberRedis(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



    // 快取會員列表，附加隨機過期時間以防止緩存雪崩
    public void cacheMemberList(String key, List<Member> memberList) {
        int baseSeconds = 300; // 基本快取時間 5 分鐘
        int randomSeconds = random.nextInt(60); // 額外隨機 0~59 秒
        Duration ttl = Duration.ofSeconds(baseSeconds + randomSeconds);
        redisTemplate.opsForValue().set(key, memberList, ttl);
    }

    @SuppressWarnings("unchecked")
    public List<Member> getCachedMemberList(String key) {
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached instanceof List<?>) {
            return (List<Member>) cached;
        }
        return null;
    }

    /**
     * 嘗試加鎖：使用 Redis SETNX 實作分散式鎖
     * @param lockKey 鎖定的 key
     * @param lockValue 可使用 UUID 或 sessionId 作為識別
     * @param baseSeconds 鎖定基礎秒數
     * @param retryTimes 嘗試重試次數
     * @param retryDelay 每次重試的間隔（毫秒）
     * @return 是否加鎖成功
     */
    public boolean tryLock(String lockKey, String lockValue, int baseSeconds, int retryTimes, long retryDelay) {
        for (int i = 0; i < retryTimes; i++) {
            int randomSeconds = random.nextInt(60); // 額外隨機 0~59 秒
            Duration ttl = Duration.ofSeconds(baseSeconds + randomSeconds); //防止雪崩加上隨機時間
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, ttl); //setNx取得key+自動失效TTL避免死鎖
            if (Boolean.TRUE.equals(locked)) {
                return true;
            }

            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * 解鎖：只有當 value 一致時才刪除（防止誤刪他人鎖）
     * @param lockKey 鎖定的 key
     * @param lockValue 當前持有鎖的 value
     */
    public void unlock(String lockKey, String lockValue) {
        Object currentValue = redisTemplate.opsForValue().get(lockKey);
        if (lockValue.equals(currentValue)) {
            redisTemplate.delete(lockKey);
        }
    }

    /**
     * 快取會員分頁結果，用於加速前端列表頁查詢
     * @param page 當前頁碼
     * @param limit 每頁筆數
     * @param memberList 會員列表資料
     */
    public void cachePagedMemberList(int page, int limit, List<Member> memberList) {
        String key = "member_list:page=" + page + ":limit=" + limit;
        cacheMemberList(key, memberList);
    }

    /**
     * 取得會員分頁快取資料
     * @param page 當前頁碼
     * @param limit 每頁筆數
     * @return 快取中的會員列表，或 null 表示無快取
     */
    public List<Member> getCachedPagedMemberList(int page, int limit) {
        String key = "member_list:page=" + page + ":limit=" + limit;
        return getCachedMemberList(key);
    }

    /**
     * 刪除指定分頁的會員快取資料
     * @param page 當前頁碼
     * @param limit 每頁筆數
     */
    public void deleteCacheForPage(int page, int limit) {
        String key = "member_list:page=" + page + ":limit=" + limit;
        redisTemplate.delete(key);
    }

    /**
     * 清除所有會員列表相關的快取
     */
    public void deleteAllMemberListCache() {
        var keys = redisTemplate.keys("member_list:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

}
