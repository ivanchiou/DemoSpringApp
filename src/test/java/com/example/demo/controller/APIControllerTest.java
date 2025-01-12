package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.component.JwtTokenUtil;
import com.example.demo.model.DemoModel;
import com.example.demo.model.UserModelRequestEntity;
import com.example.demo.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Collection;
import java.util.List;

@WebMvcTest(APIController.class)
//@WithMockUser(username = "ivan", roles = {"USER"})
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DemoService demoService;

    @MockBean
    private AuthenticationManager authenticationManager;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private APIController apiController;
    
    private String token;
    private String admin_token;

    @BeforeEach
    void setup() {
        // 初始化必要的 MockBean
        // 生成測試用 JWT Token
        token = jwtTokenUtil.generateToken("ivan", List.of("ROLE_USER"));
        admin_token = jwtTokenUtil.generateToken("admin", List.of("ROLE_USER", "ROLE_ADMIN"));
    }

    @Test
    @WithMockUser(username = "ivan", roles = {"USER"})
    void testSayHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk()) // status code 200
                .andExpect(content().string("Hello, Swagger!"));
    }

    @Test
    @WithMockUser(username = "ivan", roles = {"USER"})
    void testGetUserByID() throws Exception {
        DemoModel demoModel = new DemoModel(); // 假設 DemoModel 有無參數建構函式
        demoModel.setId(1);
        demoModel.setName("ivan");

        Mockito.when(demoService.getUserByID(1)).thenReturn(demoModel);

        mockMvc.perform(get("/api/users/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)) // 添加 JWT Token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ivan"));
    }

    @Test
    @WithMockUser(username = "ivan", roles = {"USER"})
    void testGetUserByID_NotFound() throws Exception {
        Mockito.when(demoService.getUserByID(100)).thenReturn(null);

        mockMvc.perform(get("/api/users/100")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSetUserNameByID() throws Exception {
        
        Mockito.when(demoService.updateUserName(1, "NewName")).thenReturn(true);

        mockMvc.perform(patch("/api/admin/users/1")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Include JWT Token
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Specify form-data content type
                    .param("name", "NewName")) // Add form-data parameter
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testLogin_Success() throws Exception {
        // 模擬 Authentication 對象
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getName()).thenReturn("ivan");

        // 模擬 AuthenticationManager 行為，返回假用戶
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
            .thenReturn(mockAuthentication);

        // 測試控制器方法
        UserModelRequestEntity user = new UserModelRequestEntity();
        user.setUsername("ivan");
        user.setPassword("password");

        ResponseEntity<?> response = apiController.login(user, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Login successful"));
    }
    

    @Test
    void testLogin_InvalidInput() throws Exception {
        // Create a real UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken("ivan", "password");
    
        // Mock AuthenticationManager to return the token
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(token);
    
        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("username", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username can't be null or empty"));
    }

    @Test
    void testLogin_Unauthorized() throws Exception {
        // Create a mock Authentication object
        // Mock AuthenticationManager to throw an exception for invalid credentials
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
            .thenThrow(new RuntimeException("Invalid credentials")); 

        mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("username", "ivan")
                    .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout successful"));
    }
}
