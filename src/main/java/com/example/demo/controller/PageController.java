package com.example.demo.controller;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

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

        String token = Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
                .setSubject(name)
                .setIssuer("self")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"), SignatureAlgorithm.HS256)
                .compact();

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
