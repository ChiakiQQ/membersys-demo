package com.caitlyn.membersysdemo.service.impl;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.model.MemberRedis;
import com.caitlyn.membersysdemo.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private MemberRedis memberRedis;

    @Override
    public List<Member> getCachedMembers(int page, int limit) {
        return memberRedis.getCachedPagedMemberList(page, limit);
    }

    @Override
    public void cacheMembers(int page, int limit, List<Member> members) {
        memberRedis.cachePagedMemberList(page, limit, members);
    }

    @Override
    public void deleteMemberPageCache(int page, int limit) {
        memberRedis.deleteCacheForPage(page, limit);
    }

    @Override
    public void deleteAllMemberCache() {
        memberRedis.deleteAllMemberListCache();
    }
}
