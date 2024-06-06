package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public MainPageController() throws SQLException {
        super();
        //TODO Auto-generated constructor stub
    }

    User u=new User();

    

    @GetMapping(value = "/mainPage", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String serveHomePage() throws IOException {
        String username = u.username;
        ClassPathResource resource = new ClassPathResource("static/mainPage.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        
        
    } 
    
    @GetMapping("/random-sentence")
    public String getRandomSentence() {

        String[] sentences = {
            "早安\n平安喜樂",
            "早晨好，如意自在。\n舒心愉快，福慧齊來。",
            "幸福，珍惜眼前所有。\n早安，享受美好當下。",
            "心有所念，苦有回甘。\n願有年年，有錢有顏。",
            "健康是最大的財富，保重身體~\n早睡早起，養成良好的生活習慣~~",
            "早安\n一聲早，再聲好\n開心如意樣樣好。"
        };

        Random random = new Random();
        int index = random.nextInt(sentences.length);
        return sentences[index];
    }

}
