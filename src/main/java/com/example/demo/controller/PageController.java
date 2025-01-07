package com.example.demo.controller;
import java.util.Date;

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

    @GetMapping("/fb_login")
    public String fb_login(
        Model model
    ) {
        return "fb_login";
    }

    @GetMapping("/fb_success")
    public String fb_success(
        @AuthenticationPrincipal OAuth2User principal,
        Model model
    ) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        model.addAttribute("name", name);
        model.addAttribute("email", email);

        String token = Jwts.builder()
            .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
            .setSubject("name")
            .setIssuer("self")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600))
            .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"), SignatureAlgorithm.HS256)
            .compact();

        model.addAttribute("token", token);

        return "fb_success";
    }
    

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
