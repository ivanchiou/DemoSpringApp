package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.example.demo.model.DemoModel;
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
    public ResponseEntity<DemoModel> register(
        @RequestBody Map<String, Object> jsonBody) {
        // Save the model to the database
        String name = (String) jsonBody.get("name");
        DemoModel savedModel = demoService.createUserByName(name);
        return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
    }

    @GetMapping("/model/{name}")
    public DemoModel getUserByName(@PathVariable String name) {
        return demoService.getUserByName(name);
    }
}
