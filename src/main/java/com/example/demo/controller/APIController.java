package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.DemoModel;
import com.example.demo.service.DemoService;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Tag(name = "API", description = "Operations for APIController")
@RequestMapping("/api")
public class APIController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private AuthenticationManager authenticaitonManager;

    @Operation(summary = "Test API hello", description = "Test API hello")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Swagger!";
    }

    @Operation(summary = "Login API", description = "Login API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User login successfully"),
        @ApiResponse(responseCode = "400", description = "username or password is null or empty") // bad request
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if(username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username or password can't be null or empty"));
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticaitonManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(Map.of("success", "Login successful"));
    }

    @Operation(summary = "Get user model by ID", description = "Get user model by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user model successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided") // bad request
    })
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public DemoModel getUserByID(@PathVariable int id) {
        DemoModel demoModel = this.demoService.getUserByID(id);
        if(demoModel == null) {
            throw new RuntimeException("demoModel can't be null");
        }
        return demoModel;
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Map<String, Boolean> setUserNameByID(@PathVariable int id, @RequestParam String name) {
        boolean isSuccess = this.demoService.updateUserName(id, name);
        return Map.of("success", isSuccess);
    }
}
