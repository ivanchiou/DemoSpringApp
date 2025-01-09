package com.example.demo.controller;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/page")
public class PageController {

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

    @GetMapping("/success")
    public String success(
        @AuthenticationPrincipal OAuth2User principal,
        Model model
    ) {
        String name = "";
        String email = "";
        Collection<? extends GrantedAuthority> roles = List.of(() -> "ROLE_USER");

        // for fb OAuth2 login
        if (principal != null ) {
            name = principal.getAttribute("name");
            email = principal.getAttribute("email");
        } else { // for form login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            name = authentication.getName();
            roles = authentication.getAuthorities();
        }

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        List<String> roleNames = roles.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        model.addAttribute("authorities", roleNames);

        String token = Jwts.builder()
            .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
            .setSubject(name)
            .setIssuer("self")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 一個小時
            .claim("roles", roleNames)
            .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"), SignatureAlgorithm.HS256)
            .compact();

        model.addAttribute("token", token);

        return "success";
    }
    

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
