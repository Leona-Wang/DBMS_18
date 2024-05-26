package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

@RestController
public class AddInventoryController {
    
    @GetMapping(value = "/addInventory", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/addInventory.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostMapping("/addImport")
    public void addProductList(@RequestParam("importName[]") List<String> importName,
    @RequestParam("importBoxNum[]") List<String> importBoxNum) {
        
        /*把這裡取到的資料丟進去DB費用表*/

        System.out.println("importName: " + importName + 
        "\nimportBoxNum: " +importBoxNum);

        System.out.println("good");

        
    }

    @GetMapping("/inventoryOptions")
    public List<String> getDropdownOptions() {
        
        /*把商品清單項目名稱拉出來，當成新增銷售存貨的選擇項目 */
        return Arrays.asList("mouse", "cow");
    }
    
}





