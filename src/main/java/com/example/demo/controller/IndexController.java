package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("")
public class IndexController {
    
    @PostMapping
    public ResponseEntity<String> handleLogin(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        // 这里添加登录逻辑
        return ResponseEntity.ok("Login successful");
    }
}
