package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // 禁用 CSRF（根據需求啟用或禁用）
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/page/**", "/static/css/**", "/static/js/**", "/static/images/**").permitAll()
                .requestMatchers("/api/users/**").authenticated() // /api/users/** 需要驗證
                .anyRequest().authenticated()) // 其他請求需要認證
            .formLogin(form -> form
                .loginPage("/page/login") // 自定義登入頁面
                .defaultSuccessUrl("/page/home", true) // 登入成功後跳轉
                .permitAll()) // 登入相關頁面允許訪問
            .logout(logout -> logout
                .logoutUrl("/page/logout") // 自定義登出 URL
                .logoutSuccessUrl("/page/login?logout") // 登出後跳轉
                .permitAll()) // 登出相關頁面允許訪問
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 返回 401 狀態
                    response.getWriter().write("Unauthorized: Authentication is required");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 返回 403 狀態
                }));
    
        return http.build();    
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密密碼
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

