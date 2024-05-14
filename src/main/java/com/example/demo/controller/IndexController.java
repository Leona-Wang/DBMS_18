package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    
    @RequestMapping(value = "/")
    public String index() {
        // 你可以在這裡添加任何需要的邏輯
        System.out.println("index");
        return "index";
    }
    /*@PostMapping
    public ResponseEntity<String> handleLogin(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        // 这里添加登录逻辑
        return ResponseEntity.ok("Login successful");
    }*/
}
