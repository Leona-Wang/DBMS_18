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
public class AddProductController extends SqlManager{
    
    @GetMapping(value = "/addProduct", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/addProduct.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @PostMapping("/addProductList")
    public void addProductList(@RequestParam("productName[]") List<String> productName,
    @RequestParam("productImportPrice[]") List<String> productImportPrice,
    @RequestParam("productUnitPerBox[]") List<String> productUnitPerBox,
    @RequestParam("productExportUnitPrice[]") List<String> productExportUnitPrice,
    @RequestParam("productAlertNumber[]") List<String> productAlertNumber) {
        
        /*把這裡取到的資料丟進去DB費用表*/
        String newProductName = productName.get(0).replace("[", "").replace("]", "");
        String newProductUnitPerBox = productUnitPerBox.get(0).replace("[", "").replace("]", "");
        String newProductImportPrice = productImportPrice.get(0).replace("[", "").replace("]", "");
        String newProductExportUnitPrice = productExportUnitPrice.get(0).replace("[", "").replace("]", "");
        String newProductAlertNumber = productAlertNumber.get(0).replace("[", "").replace("]", "");
        addItem(newProductName, newProductUnitPerBox, newProductImportPrice, newProductExportUnitPrice, newProductAlertNumber);

        System.out.println("productName: " + productName + 
        "\nproductImportPrice: " + productImportPrice+
        "\nproductUnitPerBox: " + productUnitPerBox+
        "\nproductExportUnitPrice: " + productExportUnitPrice+
        "\nproductAlertNumber: " + productAlertNumber);

        System.out.println("good");

        
    }
    
}



