package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.DemoModel;
import com.example.demo.service.DemoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Authentication", description = "Operations for authentication")
@RequestMapping("/api")
public class APIController {

    @Autowired
    private DemoService demoService;

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
}
