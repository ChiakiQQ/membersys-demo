package com.caitlyn.membersysdemo.service.impl;

import com.caitlyn.membersysdemo.model.MemberRedis;
import com.caitlyn.membersysdemo.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 基於 Redis 的簡易分散式鎖服務實作。

 * - 使用 SETNX 實作鎖定機制，支援過期時間與重試機制。
 * - 可應用於防止重複提交、單一帳號操作限制、資源互斥控制等場景。
 */
@Service
public class LockServiceImpl implements LockService {

    private final MemberRedis memberRedis;

    @Autowired
    public LockServiceImpl(MemberRedis memberRedis) {
        this.memberRedis = memberRedis;
    }

    @Override
    public boolean tryLock(String key, String value, int baseSeconds, int retryTimes, long retryDelay) {
        return memberRedis.tryLock(key, value, baseSeconds, retryTimes, retryDelay);
    }

    @Override
    public void unlock(String key, String value) {
        memberRedis.unlock(key, value);
    }
}