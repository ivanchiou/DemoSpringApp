package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authroize -> authroize
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() 
                .requestMatchers("/api/auth/**").permitAll() 
                .requestMatchers("/api/hello", "/api/users/**", "/api/threads/**").authenticated()
                .anyRequest().permitAll())
            .formLogin(form -> form
                .loginPage("/page/login") // 自定義登入頁面（可選）
                .defaultSuccessUrl("/api/hello", true) // 登入成功後跳轉
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/page/login?logout=true") // 登出後跳轉
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))  // 默認模式
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException)->{
                    System.err.println("401 error");
                    response.sendRedirect("/page/login"); // 導向預設登入頁
                }));
        
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("ivan").password("{noop}password").authorities("read").build());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}   
