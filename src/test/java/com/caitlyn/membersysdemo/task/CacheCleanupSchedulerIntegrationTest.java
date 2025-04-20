package com.caitlyn.membersysdemo.task;

import com.caitlyn.membersysdemo.service.CacheService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * （中文）整合測試：驗證排程快取清除任務是否正確呼叫 CacheService 刪除會員快取。
 * 測試會啟動完整 Spring 應用上下文，並用 @MockBean 模擬 CacheService。
 */
@SpringBootTest
public class CacheCleanupSchedulerIntegrationTest {

    @MockBean
    private CacheService cacheService;

    @Autowired
    private CacheCleanupScheduler scheduler;

    /**
     *
     * 驗證 clearMemberCache() 是否正確觸發，
     * 並確認模擬的 CacheService 有呼叫 deleteAllMemberCache()。
     */
    @Test
    void testClearMemberCache_shouldCallDeleteAllMemberCache() {
        scheduler.clearMemberCache();
        Mockito.verify(cacheService, Mockito.times(1)).deleteAllMemberCache();
    }
}
