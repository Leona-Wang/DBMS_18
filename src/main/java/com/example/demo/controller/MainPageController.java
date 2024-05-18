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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MainPageController {

    User u=new User();

    

    @GetMapping(value = "/mainPage", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String serveHomePage() throws IOException {
        String username = u.username;
        ClassPathResource resource = new ClassPathResource("static/mainPage.html");
        String htmlContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return htmlContent.replace("{username}", username); // 将 {username} 替换为实际的用户名
        
    } 

    @GetMapping("/mainPage")
    public String getUsername() {
        String username = "John Doe"; // 從數據庫或其他來源獲取用戶名
        return username;
    }


}
