package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends SqlManager {
    
    User user = new User();
    
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/index.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, String> response = new HashMap<>();
        
        if (checkSign(username, password)) {
            response.put("success", "true");
            response.put("message", "登入成功！");
            user.username = username;
        } else {
            response.put("success", "false");
            response.put("message", "帳號或密碼錯誤！");
        }
        
        return ResponseEntity.ok(response);
    }
}
