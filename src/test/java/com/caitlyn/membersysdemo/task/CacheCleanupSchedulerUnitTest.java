package com.caitlyn.membersysdemo.task;

import com.caitlyn.membersysdemo.service.CacheService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 單元測試：測試 CacheCleanupScheduler 類別是否正確呼叫 CacheService 的 deleteAllMemberCache() 方法。
 * 此測試不啟動 Spring Boot 應用，只使用 Mockito 進行模擬與驗證。
 */
public class CacheCleanupSchedulerUnitTest {

    /**
     * 測試 clearMemberCache() 方法是否有正確調用 mock 的 deleteAllMemberCache()
     */
    @Test
    void testClearMemberCache_shouldInvokeDeleteAllMemberCache() {
        // 建立 mock 的 CacheService 物件
        CacheService mockCacheService = Mockito.mock(CacheService.class);

        // 建立要測試的 CacheCleanupScheduler 實例
        CacheCleanupScheduler scheduler = new CacheCleanupScheduler();

        // 透過反射將 mock 注入 scheduler 內部的私有欄位
        var field = CacheCleanupScheduler.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(scheduler, mockCacheService);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // 執行要測試的方法
        scheduler.clearMemberCache();

        // 驗證 mock 的 deleteAllMemberCache() 是否被呼叫一次
        Mockito.verify(mockCacheService, Mockito.times(1)).deleteAllMemberCache();
    }
}
