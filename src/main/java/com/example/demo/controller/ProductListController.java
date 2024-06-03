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
public class ProductListController extends SqlManager{
    
    @GetMapping(value = "/productList", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/productList.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/showProductList")
    public List<Product> showProductList() {
        
        /*在這裡放從DB撈出來的產品清單 */
        /*顯示的資料可以從Product新增其他屬性 */
        /*這裡改完inventory.html顯示的標題也要改，inventoryControll.js也要改 */
        /*可以的話全部的值都用String塞，這樣回傳比較不會出問題 */
        /*html跟js不會改的話再跟我說，我去改 */
        List<Product> products = new ArrayList<>();
        String [][] itemList = getItems();
        Product mouse=new Product("0","mouse","1000","10","150","5");
        Product cow=new Product("0","cow","2000","20","200","10");
        products.add(mouse);
        products.add(cow);
        for (String[] productInfo : itemList) {
            products.add(new Product(productInfo[0], productInfo[1], productInfo[2], productInfo[3], productInfo[4], productInfo[5]));
        }
        return products;
    }
    
}




