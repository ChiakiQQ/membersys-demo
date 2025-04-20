package com.caitlyn.membersysdemo.task;

import com.caitlyn.membersysdemo.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheCleanupScheduler {

    @Autowired
    private CacheService cacheService;

    private static final Logger logger = LoggerFactory.getLogger(CacheCleanupScheduler.class);

    // @Scheduled(cron = "0 0 */2 * * *") // 每 2 小時清除一次會員快取
    @Scheduled(cron = "*/30 * * * * *") // 每 30 秒清除一次會員快取（測試用）
    public void clearMemberCache() {
        logger.info("[Scheduled Task] clearMemberCache() triggered");
        cacheService.deleteAllMemberCache();
    }
}