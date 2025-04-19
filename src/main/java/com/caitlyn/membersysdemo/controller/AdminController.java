package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.model.Mng;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import com.caitlyn.membersysdemo.repo.MngRepo;
import com.caitlyn.membersysdemo.service.ExportService;
import com.caitlyn.membersysdemo.util.CaptchaUtil;
import com.caitlyn.membersysdemo.util.JwtUtil;
import com.caitlyn.membersysdemo.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
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

    @GetMapping("/lock")
    public String showLockedPage() {
        System.out.println("跳轉至 locked 頁面");
        return "admin/lock";
    }

    @PostMapping("/login")
    public String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String rawPassword = request.getParameter("password");
        String inputCaptcha = request.getParameter("captcha");
        String encryptedPassword = PasswordUtil.md5(rawPassword);

        // 檢查驗證碼
        String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(inputCaptcha)) {
            return "redirect:/admin/login?error=captcha";
        }

        Mng admin = mngRepo.findByNameAndPassword(name, encryptedPassword);

        if (admin != null && admin.getEnable() == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);

            String token = JwtUtil.generateToken(admin.getName());
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(86400); // 1 天
            response.addCookie(jwtCookie);

            //避免重複登入（含同瀏覽器） 認session
            ServletContext servletContext = session.getServletContext();
            servletContext.setAttribute("active_" + admin.getName(), session.getId());

            return "redirect:/admin/list"; // 登入成功跳轉後台
        } else {
            return "redirect:/admin/login?error=true"; // 登入失敗返回登入頁
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        response.setHeader("Authorization", ""); // optional: 清空 header
        return "redirect:/admin/login";
    }

    @GetMapping("/list")
    public String showMemberList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            Model model,
            HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }

        int offset = (page - 1) * limit;
        model.addAttribute("members", memberRepo.findPage(offset, limit));

        int totalCount = memberRepo.count();
        int totalPages = (int) Math.ceil((double) totalCount / limit);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("limit", limit);
        model.addAttribute("totalCount", totalCount);
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

    @GetMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        String captchaText = CaptchaUtil.generateCaptchaText(4);
        session.setAttribute("captcha", captchaText);

        byte[] imageBytes = CaptchaUtil.generateCaptchaImage(captchaText);

        response.setContentType("image/png");
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}