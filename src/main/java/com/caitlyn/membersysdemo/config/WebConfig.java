package com.caitlyn.membersysdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Autowired
    private JwtFilterProperties jwtFilterProperties;

    @Bean
    public FilterRegistrationBean<javax.servlet.Filter> jwtFilter() {
        FilterRegistrationBean<javax.servlet.Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtFilter(jwtFilterProperties));

        // 只攔截需要驗證的路徑
        registration.addUrlPatterns(
                "/admin/list",
                "/admin/delete",
                "/admin/edit",
                "/admin/export"
        );

        registration.setName("JwtFilter");
        registration.setOrder(1); // 執行順序
        return registration;
    }
}