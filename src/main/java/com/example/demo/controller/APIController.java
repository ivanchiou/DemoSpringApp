package com.example.demo.controller;

import java.util.stream.Collectors;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.UserModelRequestEntity;
import com.example.demo.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@Tag(name = "API", description = "Operations for api controller")
@RequestMapping("/api")
public class APIController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Swagger!";
    }

    @Operation(summary = "Find a user name", description = "Find a user name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account has been found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @GetMapping("/users/{name}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Map<String, Boolean> getUserByName(@PathVariable String name) {
        boolean isSuccess = this.usersService.findUserByUsername(name);
        return Map.of("success", isSuccess);
    }

    @Operation(summary = "Update a user's password", description = "Update a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the password successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PatchMapping("/admin/users/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Map<String, Boolean> setUserPasswordByName(@PathVariable String name, @RequestParam String password) {
        boolean isSuccess = this.usersService.updatePassword(name, password);
        return Map.of("success", isSuccess);
    }

    @Operation(summary = "User login", description = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User login successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute UserModelRequestEntity modelBody,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                                            .stream()
                                            .map(ObjectError::getDefaultMessage)
                                            .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(Map.of("errors", errorMessages));
        }

        String username = modelBody.getUsername();
        String password = modelBody.getPassword();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username can't be null or empty"));
        }

        try {
            // 驗證用戶
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password); // 編碼後密碼
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, encodedPassword);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
