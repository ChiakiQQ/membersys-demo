package com.caitlyn.membersysdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDBController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-db")
    public String testDB() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "資料庫連線成功，結果：" + result;
        } catch (Exception e) {
            return "資料庫連線失敗：" + e.getMessage();
        }
    }
}