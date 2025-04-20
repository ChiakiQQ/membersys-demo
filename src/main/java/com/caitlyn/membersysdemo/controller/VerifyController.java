package com.caitlyn.membersysdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.Random;

@Controller
public class VerifyController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/verify")
    public String showVerifyPage() {
        return "verify/form"; // 輸入 Email 的頁面
    }

    @PostMapping("/verify/code")
    public String generateCode(@RequestParam("email") String email, Model model) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000); // 產生 1000 ~ 9999
        String key = "verify_code:" + email;
        System.out.println("============code is "+code+"============");
        // todo 可配合MQ發送非同步事件寄信

        redisTemplate.opsForValue().set(key, code, Duration.ofSeconds(60)); // 60秒過期
        model.addAttribute("email", email);
        model.addAttribute("code", code); // 傳遞驗證碼
        return "verify/enter"; // 讓使用者輸入驗證碼
    }

    @PostMapping("/verify/submit")
    public String submitCode(@RequestParam("email") String email,
                             @RequestParam("code") String code,
                             Model model) {
        String key = "verify_code:" + email;
        Object realCode = redisTemplate.opsForValue().get(key);

        if (realCode != null && realCode.equals(code)) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        model.addAttribute("email", email);
        return "verify/result";
    }
}
