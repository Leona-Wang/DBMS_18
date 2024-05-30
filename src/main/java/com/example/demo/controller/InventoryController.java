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
public class InventoryController extends SqlManager{
    
    User user=new User();
    
    @GetMapping(value = "/inventory", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/inventory.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/inventoryProductList")
    public List<Product> getAllUsers() {
        
        
        List<Product> products = new ArrayList<>();

        Product mouse=new Product("mouse","123");
        Product cow=new Product("cow","456");
        products.add(mouse);
        products.add(cow);
        return products;
    }

    @GetMapping("/inventoryOptions")
    public List<String> getDropdownOptions() {
        
        
        return Arrays.asList("mouse", "cow");
    }

    @PostMapping("/addProductList")
    @ResponseBody
    public void addProductList(@RequestParam("productName[]") String[] productNames, @RequestParam("productPrice[]") String[] productPrices) {
        
        /*把這裡取到的資料塞到DB商品清單去 */
        
        StringBuilder result = new StringBuilder("Received data:");
        
        
        for (int i = 0; i < productNames.length; i++) {
            result.append("產品名稱: ").append(productNames[i]).append(", 價格: ").append(productPrices[i]);
            
            //addItem(shopID,productNames[i],unitPerBox, importPrice,productPrices[i],alertAmount)
        }
        System.out.println(result.toString());

    }

    @PostMapping("/addInventory")
    public void addInventory(@RequestParam("addInventoryOption[]") List<String> options,
                            @RequestParam("addInventoryBox[]") List<String> boxes) {
        
            //把這裡取到的資料塞到DB的新增存貨裡
            
            for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            String box = boxes.get(i);
            addInventory(option,Integer.parseInt(box));
            System.out.println("Option: " + option + ", Box: " + box);
        }
    }


    @GetMapping("/inventoryInventoryList")
    public List<Product> getInventory() {
        
        
        List<Product> products = new ArrayList<>();

        Product mouse=new Product("mouse","4");
        Product cow=new Product("cow","5");
        products.add(mouse);
        products.add(cow);
        return products;
    }

    @PostMapping("/deductInventory")
    public void deductInventory(@RequestParam("deductInventoryOption[]") List<String> options,
                            @RequestParam("deductInventoryBox[]") List<String> boxes) {
        
            //把這裡的資料調整去DB存貨清單 
                                
            for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            String box = boxes.get(i);
            updateInventory(option, Integer.parseInt(box));
            System.out.println("Option: " + option + ", Pack: " + box);
        }
    }

}



