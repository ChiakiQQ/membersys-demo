package com.caitlyn.membersysdemo.controller;

import com.caitlyn.membersysdemo.util.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping("/redis")
public class RedisController {

    private static final Logger logger = LogManager.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/test/set")
    @ResponseBody
    public String setRedis() {
        redisTemplate.opsForValue().set("hello", "world");
        return "Value set in Redis.";
    }

    @GetMapping("/test/get")
    @ResponseBody
    public String getRedis() {
        Object value = redisTemplate.opsForValue().get("hello");
        return value != null ? "Value from Redis: " + value : "Key not found.";
    }

    @GetMapping("/keys")
    public String showRedisKeys(Model model) {
        Set<String> keys = redisUtil.listAllKeys();
        model.addAttribute("redisKeys", keys);
        return "redis-keys";
    }

    @GetMapping("/value")
    public String showRedisValue(@RequestParam String key, Model model) {
        Object value = redisUtil.getValue(key);
        model.addAttribute("redisKey", key);
        model.addAttribute("redisValue", value);
        return "redis-value";
    }

    @GetMapping("/delete")
    public String deleteRedisKey(@RequestParam String name, HttpServletRequest request) {
        String key = "admin_session:" + name;
        redisUtil.delete(key);
        request.getSession().setAttribute("unlockMessage", "Session key deleted. You may now log in.");
        logger.warn("Session key deleted. You may now log in.");
        return "redirect:/admin/login";
    }

    @GetMapping("/deleteAll")
    public String deleteAllCache(HttpServletRequest request) {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            request.getSession().setAttribute("unlockMessage", "All Redis cache deleted.");
            logger.info("Deleted ALL keys from Redis");
        } else {
            logger.info("No keys found in Redis to delete.");
        }
        return "redirect:/redis/keys";
    }

}
