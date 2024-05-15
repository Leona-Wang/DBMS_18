package com.example.demo.controller;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.x.protobuf.MysqlxCrud.DataModel;



@RestController
public class IndexController {
    
    @PostMapping(value = "/")
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
    /*public String login(@RequestParam(name = "username", required = true) String username, @RequestParam(name = "username", required = true) String password, Model model) {
        // 在這裡可以加入驗證邏輯
        model.addAttribute("username", username);
        System.out.println("aaa"+username);
        return "good";
    }*/
    /*public String index() {
        // 你可以在這裡添加任何需要的邏輯
        System.out.println("index");
        return "index";
    }*/
    /*@PostMapping
    public ResponseEntity<String> handleLogin(@RequestParam String username, @RequestParam String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        // 这里添加登录逻辑
        return ResponseEntity.ok("Login successful");
    }*/
}
