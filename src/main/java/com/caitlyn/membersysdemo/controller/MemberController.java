package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.event.MemberRegisteredEvent;
import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.caitlyn.membersysdemo.util.PasswordUtil;
import com.caitlyn.membersysdemo.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

@Controller
public class MemberController {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String sessionId = session.getId();
            String lockKey = "member_session:" + request.getParameter("username");
            redisUtil.renewIfMatch(lockKey, sessionId, 3600);
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");
        String password =  PasswordUtil.md5(rawPassword);

        Member member = new Member(
                null,
                username,
                email,
                password,
                System.currentTimeMillis()
        );

        memberRepo.insert(member);

        // 發送會員註冊事件（將由 Listener 接收）
        eventPublisher.publishEvent(new MemberRegisteredEvent(this, member));

        return "redirect:/register?success";
    }
}