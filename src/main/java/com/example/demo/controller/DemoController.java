package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import java.util.Map;

import com.example.demo.model.DemoModel;
import com.example.demo.model.DemoModelRequestEntity;
import com.example.demo.service.DemoService;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello Ivan!";
    }

    @GetMapping("/model")
    public ResponseEntity<DemoModel> getModel(
            @RequestParam int id, 
            @RequestParam(required = false, defaultValue = "Test") String name) {
        DemoModel model = new DemoModel(id, name);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping("/model/register")
    public ResponseEntity<?> register(
        @Valid @ModelAttribute DemoModelRequestEntity modelBody,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Validation errors: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        String name = modelBody.getName();
        if (name == null) {
            throw new RuntimeException("name can't be null");
        }
        System.out.println(name);
        // Save the model to the database
        DemoModel savedModel = demoService.createUserByName(name);
        return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
    }

    @PostMapping("/model/check-username") 
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean isAvailable = !demoService.checkUserName(username.toLowerCase());
        return Map.of("isAvailable", isAvailable);
    }


    @GetMapping("/model/{name}")
    public DemoModel getUserByName(@PathVariable String name) {
        DemoModel demoModel = demoService.getUserByName(name);
        if(demoModel == null) {
            throw new RuntimeException("demoModel can't be null");
        }
        return demoModel;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        return String.format("error: %s", e.getMessage());
    }
}
