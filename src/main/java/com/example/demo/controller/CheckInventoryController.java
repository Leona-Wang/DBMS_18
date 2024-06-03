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
public class CheckInventoryController extends SqlManager{
    
    @GetMapping(value = "/checkInventory", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/checkInventory.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/checkInventoryList")
    public List<Product> checkInventoryList() {
        
        /*在這裡放從DB撈出來的產品清單 */
        /*顯示的資料可以從Product新增其他屬性 */
        /*這裡改完inventory.html顯示的標題也要改，inventoryControll.js也要改 */
        /*可以的話全部的值都用String塞，這樣回傳比較不會出問題 */
        /*html跟js不會改的話再跟我說，我去改 */
        List<Product> inventory = new ArrayList<>();
        String [][] invList = getInventory();
        Product mouse=new Product("0","mouse","40");
        Product cow=new Product("1","cow","20");
        inventory.add(mouse);
        inventory.add(cow);
        for (String[] productInfo : invList) {
            inventory.add(new Product(productInfo[0], productInfo[1], productInfo[2]));
        }
        return inventory;
    }
    

    @PostMapping("/adjustInventoryList")
    public void addProductList(@RequestParam("productIndex") List<String> productIndex,
    @RequestParam("productName") List<String> productName,
    @RequestParam("inventoryAmount") List<String> inventoryAmount) {
        
        /*把這裡取到的資料丟進去DB費用表*/

        for (int i=0;i<productIndex.size();i++){
            String newProductIndex = productIndex.get(i).replace("[", "").replace("]", "");
            String newProductName = productName.get(i).replace("[", "").replace("]", "");
            String newInventoryAmount = inventoryAmount.get(i).replace("[", "").replace("]", "");
            updateInventory(newProductIndex, newInventoryAmount);
            System.out.println("index:"+productIndex.get(i)+"\nproductName:"+productName.get(i)+"\ninventoryAmount:"+inventoryAmount.get(i));
        }

        System.out.println("good");

        
    }
}




