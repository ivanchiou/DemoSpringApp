package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.component.JwtTokenUtil;
import com.example.demo.model.DemoModel;
import com.example.demo.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpHeaders;
import java.util.Map;
import java.util.List;

@WebMvcTest(APIController.class)
@WithMockUser(username = "ivan", roles = {"USER"})
class APIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DemoService demoService;

    @MockBean
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    private String token;

    @BeforeEach
    void setup() {
        // 初始化必要的 MockBean
        // 生成測試用 JWT Token
        token = jwtTokenUtil.generateToken("ivan", List.of("ROLE_USER"));
    }

    @Test
    void testSayHello() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Swagger!"));
    }

    @Test
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
    void testGetUserByID_NotFound() throws Exception {
        Mockito.when(demoService.getUserByID(100)).thenReturn(null);

        mockMvc.perform(get("/api/users/100")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isInternalServerError()); // 根據程式的邏輯，會拋出 RuntimeException
    }

    @Test
    void testSetUserNameByID() throws Exception {
        Mockito.when(demoService.updateUserName(1, "NewName")).thenReturn(true);

        mockMvc.perform(patch("/api/admin/users/1")
                .param("name", "NewName")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testLogin_Success() throws Exception {
        Mockito.doNothing().when(authenticationManager).authenticate(Mockito.any());

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "testuser")
                .param("password", "password123")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void testLogin_InvalidInput() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Username can't be null or empty"));
    }

    @Test
    void testLogin_Unauthorized() throws Exception {
        Mockito.doThrow(AuthenticationException.class).when(authenticationManager).authenticate(Mockito.any());

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "testuser")
                .param("password", "wrongpassword")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
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
