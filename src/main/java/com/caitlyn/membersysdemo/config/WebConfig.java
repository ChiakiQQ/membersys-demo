package com.caitlyn.membersysdemo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtFilter());

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