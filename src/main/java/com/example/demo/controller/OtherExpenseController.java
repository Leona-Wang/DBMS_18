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
public class OtherExpenseController {
    
    User user=new User();
    
    @GetMapping(value = "/otherExpense", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/otherExpense.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/otherExpenseList")
    public List<OtherExpense> getAllExpense() {

        /*把其他費用那張表拉出來 */
        

        List<OtherExpense> expenses = new ArrayList<>();

        OtherExpense e1=new OtherExpense("2024-01-05","水費", "456");
        OtherExpense e2=new OtherExpense("2024-04-10","電費", "123");   
        expenses.add(e1);
        expenses.add(e2);
        return expenses;
    }

    @PostMapping("/addExpense")
    public void addExpense(@RequestParam("expenseDate[]") List<String> dates,
    @RequestParam("expenseType[]") List<String> types,
                            @RequestParam("expenseCost[]") List<String> costs) {
        
        /*把這裡取到的資料丟進去DB費用表*/
        addBill(shopID, types.get(0), dates.get(0), costs.get(0));

        for (int i = 0; i < dates.size(); i++) {
            String date=dates.get(i);
            String type = types.get(i);
            String cost = costs.get(i);
            System.out.println("date: " + date + ", type: " + type+", cost: " + cost);
        }
    }

    
}




