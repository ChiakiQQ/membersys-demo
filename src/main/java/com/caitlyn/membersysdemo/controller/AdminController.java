package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Mng;
import com.caitlyn.membersysdemo.repo.MngRepo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private MngRepo mngRepo;

    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String handleLogin(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        Mng mng = mngRepo.findByNameAndPassword(name, password);
        if (mng != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", mng.name());
            return "redirect:/admin/list";
        } else {
            return "redirect:/admin/login?error";
        }
    }
}