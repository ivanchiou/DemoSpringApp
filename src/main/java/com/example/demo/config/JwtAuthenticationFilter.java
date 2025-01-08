package com.example.demo.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getRequestURI().startsWith("/api/")) { // 只處理 /api/ 路徑的請求
            filterChain.doFilter(request, response);
            return;
        }

        // 檢查 Authorization Header
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            SecurityContextHolder.clearContext(); // 確保未攜帶 JWT 的請求無法使用 Session
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = header.substring(7);
        try {
            // 解碼並驗證 Token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes()) // 設置簽名密鑰
                    .build()
                    .parseClaimsJws(token) // 驗證並解碼 JWT
                    .getBody(); // 獲取 Claimss

            // 提取用戶名和角色
            String username = claims.getSubject();
            List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get("roles")).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // 設置認證上下文
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            // Token 無效，設置 HTTP 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
