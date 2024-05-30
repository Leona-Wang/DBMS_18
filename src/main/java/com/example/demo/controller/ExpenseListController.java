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
public class ExpenseListController {
    
    @GetMapping(value = "/expenseList", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/expenseList.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/showExpenseList")
    public List<OtherExpense> showInventoryList() {
        
        /*在這裡放從DB撈出來的產品清單 */
        /*顯示的資料可以從Product新增其他屬性 */
        /*這裡改完inventory.html顯示的標題也要改，inventoryControll.js也要改 */
        /*可以的話全部的值都用String塞，這樣回傳比較不會出問題 */
        /*html跟js不會改的話再跟我說，我去改 */
        List<OtherExpense> expenses = new ArrayList<>();
        // String [][] billData = getBillData();
        // for(int i = 0 ; i<billData.length ; i++){
        //     OtherExpense OtherExpense = new OtherExpense();  //等getBillData()寫好再編輯這裡
        //     expenses.add(e);
        // }

        OtherExpense e1=new OtherExpense("0","2024-01-01","e1","500");
        OtherExpense e2=new OtherExpense("1","2024-01-02","e2","400");
        expenses.add(e1);
        expenses.add(e2);
        return expenses;
    }
    
}




