package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.model.Mng;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import com.caitlyn.membersysdemo.repo.MngRepo;
import com.caitlyn.membersysdemo.service.CacheService;
import com.caitlyn.membersysdemo.service.ExportService;
import com.caitlyn.membersysdemo.service.LockService;
import com.caitlyn.membersysdemo.util.CaptchaUtil;
import com.caitlyn.membersysdemo.util.JwtUtil;
import com.caitlyn.membersysdemo.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private MngRepo mngRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private ExportService exportService;
    @Autowired
    private LockService lockService;
    @Autowired
    private CacheService cacheService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/lock")
    public String showLockedPage() {
        logger.info("跳轉至 locked 頁面");
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
            logger.warn("Captcha mismatch for admin login: input={}, expected={}", inputCaptcha, sessionCaptcha);
            return "redirect:/admin/login?error=captcha";
        }

        Mng admin = mngRepo.findByNameAndPassword(name, encryptedPassword);
        if (admin != null && admin.getEnable() == 1) {
            // 改用 LockService 實作 Redis 分散式鎖處理併發，限制同帳號同時登入
            HttpSession session = request.getSession();
            String lockKey = "admin_session:" + admin.getName();
            String lockValue = session.getId(); // 可使用 sessionId 區分鎖持有者
            boolean isNewLogin = lockService.tryLock(lockKey, lockValue, 3600, 3, 100);
            if (!isNewLogin) {
                logger.warn("Admin [{}] login blocked due to existing active session", name);
                return "redirect:/admin/lock";
            }

            // session + jwt token驗證(存在cookie)
            session.setAttribute("admin", admin);

            String token = JwtUtil.generateToken(admin.getName());
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(86400); // 1 天
            response.addCookie(jwtCookie);
            logger.info("Admin [{}] login success , token {}", name, token);

            return "redirect:/admin/list"; // 登入成功跳轉後台
        } else {
            logger.warn("Admin login failed for name={}", name);
            return "redirect:/admin/login?error=true"; // 登入失敗返回登入頁
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        var adminObj = session.getAttribute("admin");
        if (adminObj instanceof Mng) {
            Mng admin = (Mng) adminObj;

            // 新作法：從 Redis 中移除 session 限制
            lockService.unlock("admin_session:" + admin.getName(), session.getId());
        }

        session.invalidate();
        response.setHeader("Authorization", ""); // optional: 清空 header
        return "redirect:/admin/login";
    }

    @GetMapping("/list")
    public String showMemberList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            Model model,
            HttpServletRequest request) {
        if (!JwtUtil.isAuthenticated(request)) {
            logger.warn("Unauthorized access to /admin/list due to invalid or missing JWT");
            return "redirect:/admin/login";
        }

        // 每次列表頁載入前先刪除該分頁快取，避免顯示舊資料
        cacheService.deleteMemberPageCache(page, limit);

        List<Member> members = cacheService.getCachedMembers(page, limit);
        if (members == null) {
            int offset = (page - 1) * limit;
            members = memberRepo.findPage(offset, limit);
            cacheService.cacheMembers(page, limit, members);
        }

        model.addAttribute("members", members);

        int totalCount = memberRepo.count();
        int totalPages = (int) Math.ceil((double) totalCount / limit);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("limit", limit);
        model.addAttribute("totalCount", totalCount);
        return "admin/list";
    }

    @GetMapping("/delete")
    public String deleteMember(@RequestParam("id") long id, HttpServletRequest request) {
        // var admin = session.getAttribute("admin");
        // if (admin == null) {
        if (!JwtUtil.isAuthenticated(request)) {
            logger.warn("Unauthorized delete attempt for member id={}", id);
            return "redirect:/admin/login";
        }

        memberRepo.deleteById(id);
        cacheService.deleteAllMemberCache(); // 清除快取
        return "redirect:/admin/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") long id, Model model, HttpServletRequest request) {
        // var admin = session.getAttribute("admin");
        // if (admin == null) {
        if (!JwtUtil.isAuthenticated(request)) {
            logger.warn("Unauthorized edit access for member id={}", id);
            return "redirect:/admin/login";
        }

        model.addAttribute("member", memberRepo.findById(id));
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String updateMember(@ModelAttribute("member") Member member, HttpServletRequest request) {
        // var admin = session.getAttribute("admin");
        // if (admin == null) {
        if (!JwtUtil.isAuthenticated(request)) {
            return "redirect:/admin/login";
        }

        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            String encryptedPassword = PasswordUtil.md5(member.getPassword());
            member.setPassword(encryptedPassword);
        }

        memberRepo.update(member);
        cacheService.deleteAllMemberCache(); // 清除快取
        return "redirect:/admin/list";
    }

    @GetMapping("/export")
    public void exportMembers(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // var admin = session.getAttribute("admin");
        // if (admin == null) {
        if (!JwtUtil.isAuthenticated(request)) {
            logger.warn("Unauthorized export attempt");
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