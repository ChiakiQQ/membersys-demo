package com.caitlyn.membersysdemo.event;
import com.caitlyn.membersysdemo.model.Member;
import org.springframework.context.ApplicationEvent;

/**
 * 會員註冊事件（Spring 事件用於觸發如：發送歡迎信、記錄註冊日誌等）
 */
public class MemberRegisteredEvent extends ApplicationEvent {

    private final Member member;

    public MemberRegisteredEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
