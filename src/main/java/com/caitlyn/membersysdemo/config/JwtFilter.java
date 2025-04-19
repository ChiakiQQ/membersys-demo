package com.caitlyn.membersysdemo.config;

import com.caitlyn.membersysdemo.util.JwtUtil;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 取得 HTTP 請求與回應
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        // 嘗試從 Cookie 中取得名為 jwt 的 token
        // 若未取得則導向登入頁
        String token = null;
        Cookie[] cookies = httpReq.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        // 若未找到 token，轉導回登入頁
        if (token == null) {
            httpResp.sendRedirect("/admin/login");
            return;
        }

        // 嘗試解析 JWT，取得使用者名稱
        // 取得目前 Session 與應用層 ServletContext
        try {
            String username = JwtUtil.getSubject(token);
            HttpSession session = httpReq.getSession();
            ServletContext app = session.getServletContext();

            // 檢查是否已有其他 Session 註冊該使用者
            // 若有且不同於目前 Session，則導向 /admin/locked 錯誤頁
            String key = "active_" + username;
            String currentSessionId = session.getId();
            String existingSessionId = (String) app.getAttribute(key);

            if (existingSessionId == null || existingSessionId.equals(currentSessionId)) {
                // 沒有其他活躍 session 或就是自己，正常放行
                app.setAttribute(key, currentSessionId);
                httpReq.setAttribute("jwtUser", username);
                chain.doFilter(request, response);
            } else {
                // 若有其他 session，強制取代為目前的 sessionId
                app.setAttribute(key, currentSessionId);
                httpReq.setAttribute("jwtUser", username);
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            httpResp.sendRedirect("/admin/login");
        }
    }
}