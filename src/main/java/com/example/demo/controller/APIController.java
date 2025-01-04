package com.example.demo.controller;

import java.util.stream.Collectors;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.DemoModel;
import com.example.demo.model.UserModelRequestEntity;
import com.example.demo.service.DemoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@Tag(name = "Authentication", description = "Operations for authentication")
@RequestMapping("/api")
public class APIController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Swagger!";
    }

    @Operation(summary = "Register a new account", description = "Register a new account to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @GetMapping("/users/{id}")
    public DemoModel getUserByID(@PathVariable int id) {
        DemoModel demoModel = this.demoService.getUserByID(id);
        if(demoModel == null) {
            throw new RuntimeException("demoModel can't be null");
        }
        return demoModel;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(
        @Valid @ModelAttribute UserModelRequestEntity modelBody,
        BindingResult result,
        HttpServletResponse response) {
        // 檢查資料驗證結果
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                                                .stream()
                                                .map(ObjectError::getDefaultMessage)
                                                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(Map.of("errors", errorMessages));
        }

        // 確保 username 欄位存在且有效
        String username = modelBody.getUsername();
        String password = modelBody.getPassword();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name can't be null or empty"));
        }
        System.out.println(username);

        try {
            // 呼叫服務層進行登入操作
            DemoModel model = this.demoService.getUserByName(username);
            if(model == null) {
                throw new RuntimeException("demoModel can't be null");
            }

            try {
                    // 認證用戶
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, password);

                    // 使用 Spring Security 的 AuthenticationManager 進行認證
                    Authentication authentication = authenticationManager.authenticate(authenticationToken);

                    // 將認證結果存入 SecurityContext 中
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        System.out.println("Authenticated user: " + authentication.getName());
                    }                    

                    // 登入成功返回用戶資訊
                    String redirectUrl = "/api/hello";
                    response.sendRedirect(redirectUrl);
                    return null;
                } catch (AuthenticationException e) {
                    // 認證失敗返回錯誤
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(Map.of("error", "Invalid username or password"));
                }
        } catch (Exception e) {
            // 捕捉潛在異常，提供更友好的錯誤訊息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }
}
