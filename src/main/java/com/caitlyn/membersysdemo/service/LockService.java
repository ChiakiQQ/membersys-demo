package com.caitlyn.membersysdemo.service;

public interface LockService {
    boolean tryLock(String key, String value, int baseSeconds, int retryTimes, long retryDelay);
    void unlock(String key, String value);
    boolean renewLock(String key, String value, int baseSeconds);
}