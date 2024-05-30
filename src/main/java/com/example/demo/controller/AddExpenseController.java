package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
public class AddExpenseController {
    
    @GetMapping(value = "/addExpense", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/addExpense.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostMapping("/addExpenseList")
    public void addProductList(@RequestParam("expenseType") List<String> expenseType,
    @RequestParam("expenseCost") List<String> expenseCost,
    @RequestParam("expenseDate") List<String> expenseDate) {
        
        /*把這裡取到的資料丟進去DB費用表*/
        addBill(UUID.randomUUID().toString(), expenseType.get(0),  expenseDate.get(0), expenseCost.get(0));
        

        System.out.println("expenseType: " + expenseType + 
        "\nexpenseCost: " + expenseCost+
        "\nexpenseDate: " + expenseDate);

        System.out.println("good");

        
    }
    
}



