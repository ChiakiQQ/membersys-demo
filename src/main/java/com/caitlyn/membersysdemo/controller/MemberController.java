package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import javax.servlet.http.HttpServletRequest;
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
        String password = request.getParameter("password");

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