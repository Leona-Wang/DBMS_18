package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.convert.ReadingConverter;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.x.protobuf.MysqlxCrud.DataModel;



@RestController
public class IndexController {
    
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/index.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
    /*@GetMapping("/")
    public String index() {
        return "/";
    }*/

    /*@GetMapping("/")
    public String sayHello(Model model) {
        model.addAttribute("name", "World");
        return "/";
    }*/

    @PostMapping("/")
    public String receiveData(@RequestBody DataModel data) {
        System.out.println("Received data: " + data.getData());
        return "Received: " + data.getData();
    }

    static class DataModel {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
