package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.model.Mng;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import com.caitlyn.membersysdemo.repo.MngRepo;
import com.caitlyn.membersysdemo.service.ExportService;
import com.caitlyn.membersysdemo.util.PasswordUtil; // 新增的匯入
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MngRepo mngRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private ExportService exportService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String handleLogin(HttpServletRequest request) {
        String name = request.getParameter("name");
        String rawPassword = request.getParameter("password");
        String encryptedPassword = PasswordUtil.md5(rawPassword);

        Mng admin = mngRepo.findByNameAndPassword(name, encryptedPassword);

        if (admin != null && admin.getEnable() == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            return "redirect:/admin/list"; // 登入成功跳轉後台
        } else {
            return "redirect:/admin/login?error=true"; // 登入失敗返回登入頁
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/list")
    public String showMemberList(Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("members", memberRepo.findAll());
        return "admin/list";
    }

    @GetMapping("/delete")
    public String deleteMember(@RequestParam("id") long id, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        memberRepo.deleteById(id);
        return "redirect:/admin/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") long id, Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("member", memberRepo.findById(id));
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String updateMember(@ModelAttribute("member") Member member, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            String encryptedPassword = PasswordUtil.md5(member.getPassword());
            member.setPassword(encryptedPassword);
        }

        memberRepo.update(member);
        return "redirect:/admin/list";
    }

    @GetMapping("/export")
    public void exportMembers(HttpServletResponse response, HttpSession session) throws IOException {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            response.sendRedirect("/admin/login");
            return;
        }

        exportService.exportMembers(response);
    }
}