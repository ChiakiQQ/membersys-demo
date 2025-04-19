package com.caitlyn.membersysdemo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {

    private static final long EXPIRATION_TIME = 86400000; // 1 天
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String subject) {
    // 1. 建立一個 JWT builder 實例 (Jwts.builder()).
    // 2. 設定 JWT 的主體 (subject) - 通常用來代表使用者的唯一識別.
    // 3. 設定簽發時間 (iat) 為目前時間.
    // 4. 設定過期時間 (exp) 為目前時間加上預設的過期時間.
    // 5. 使用指定的密鑰與 HS256 演算法進行簽名.
    // 6. 將 JWT 壓縮成一個字串並回傳.
    return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
