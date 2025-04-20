package com.caitlyn.membersysdemo.service;

import com.caitlyn.membersysdemo.model.Member;
import java.util.List;

public interface CacheService {
    List<Member> getCachedMembers(int page, int limit);
    void cacheMembers(int page, int limit, List<Member> members);
    void deleteMemberPageCache(int page, int limit);
    void deleteAllMemberCache();
}
