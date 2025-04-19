package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import javax.servlet.http.HttpServletRequest;

import com.caitlyn.membersysdemo.util.PasswordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MemberController {

    @Autowired
    private MemberRepo memberRepo;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(HttpServletRequest request) {
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

        return "redirect:/register?success";
    }
}