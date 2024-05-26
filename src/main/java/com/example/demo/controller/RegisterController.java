package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends SqlManager{

    public RegisterController() {
        super();
        //TODO Auto-generated constructor stub
    }

    @GetMapping(value = "/register", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/register.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        
    }  

    @PostMapping("/register")
    @ResponseBody
    public String getRegister(@RequestParam("username") String username, @RequestParam("password") String password) {
        
        /*這裡放判斷帳號有沒有重複辦過的method(帳號是primary key)，沒有重複申辦就放到DB裡*/
        /*username是申辦帳號，password是申辦密碼*/
        /*密碼可以用加密或其他神奇的魔法處理後再放到DB裡，這樣看起來比較專業*/
        /*現在預設是送出後會直接進系統，可以改 */
        
        String userData = "註冊Username: " + username + ", Password: " + password;
       //幫我填parameter
        //enroll(username,password);
        System.out.println(userData);

        
        return "Received form data: " + userData;
    }
}
