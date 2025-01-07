package com.example.demo.controller;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @GetMapping("/")
    public String index(
        @RequestParam(value = "message", required = false, defaultValue = "您好Java Spring!") String message,
        @RequestParam(value = "description", required = false, defaultValue = "我是描述!") String description,
        Model model
    ) {
        model.addAttribute("message", message);
        model.addAttribute("description", description);
        return "index";
    }

    @GetMapping("/fb_home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        
        // 獲取使用者訊息
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");

        model.addAttribute("name", name);
        model.addAttribute("email", email);

        // 獲取當前的 Authentication 對象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 生成 JWT Claims
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Token 發行者
                .subject(name) // Token 主題
                .issuedAt(Instant.now()) // Token 發行時間
                .expiresAt(Instant.now().plusSeconds(3600)) // Token 過期時間
                .claim("roles", authentication.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList())) // 添加用戶角色信息
                .build();

        // 生成 JWT Token
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        // 返回 JWT Token 給Client端
        model.addAttribute("token", token);

        return "fb_home";
    }

    @GetMapping("/card")
    public String card(
        Model model
    ) {
        return "card";
    }

    @GetMapping("/example")
    public String example(
        Model model
    ) {
        return "example";
    }

    @GetMapping("/jquery")
    public String jquery(
        Model model
    ) {
        return "jquery";
    }

    @GetMapping("/checkusername")
    public String checkusername(
        Model model
    ) {
        return "checkusername";
    }

    @GetMapping("/upload")
    public String upload(
        Model model
    ) {
        return "upload";
    }

    @GetMapping("/login")
    public String login(
        Model model
    ) {
        return "login";
    }

    @GetMapping("/fb_oauth2")
    public String fb_oauth2(
        Model model
    ) {
        return "fb_oauth2";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
