package com.caitlyn.membersysdemo.event;

import com.caitlyn.membersysdemo.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MemberEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MemberEventListener.class);

    /**
     * 當會員註冊成功後觸發的監聽方法。
     * 可在此擴充，例如發送簡訊、email 等非同步操作。
     */
    @EventListener
    public void handleMemberRegistered(MemberRegisteredEvent event) {
        Member member = event.getMember();
        logger.info("會員註冊事件接收成功，username={}, email={}", member.getUsername(), member.getEmail());

        // 模擬後續處理，如：發送驗證碼 email
        // TODO: 發送 email 或簡訊等非同步任務
    }
}
