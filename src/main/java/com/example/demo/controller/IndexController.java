package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/index.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleFormSubmission(@RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, String> response = new HashMap<>();
        
        // 假设这里的判断是如果用户名为 "admin" 且密码为 "password" 则成功，否则失败
        if ("admin".equals(username) && "password".equals(password)) {
            response.put("success", "true");
            response.put("message", "登入成功！");
        } else {
            response.put("success", "false");
            response.put("message", "帳號或密碼錯誤！");
        }
        
        return ResponseEntity.ok(response);
    }
    /*@PostMapping("/")
    @ResponseBody
    public String handleFormSubmission(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 处理从表单中获取到的数据
        String userData = "登入Username: " + username + ", Password: " + password;
        System.out.println(userData);

        // 返回响应（可选）
        return "Received form data: " + userData;
    }*/

    
    }



