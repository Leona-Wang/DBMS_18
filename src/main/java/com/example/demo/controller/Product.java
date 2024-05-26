package com.example.demo.controller;

public class Product {
    
    public String productIndex="";
    public String productName="";
    public String productImportPrice="";
    public String productUnitPerBox="";
    public String productExportUnitPrice="";
    public String productAlertNumber="";
    public String inventoryAmount="0";

    

    
    //放產品清單
    public Product(String productIndex,String productName,String productImportPrice,
    String productUnitPerBox,String productExportUnitPrice,String productAlertNumber){
        
        this.productIndex=productIndex;
        this.productName=productName;
        this.productImportPrice=productImportPrice;
        this.productUnitPerBox=productUnitPerBox;
        this.productExportUnitPrice=productExportUnitPrice;
        this.productAlertNumber=productAlertNumber;
        this.inventoryAmount="0";
    }

    //放存貨清單
    public Product(String productIndex,String productName,String inventoryAmount){
        this.productIndex=productIndex;
        this.productName=productName;
        this.inventoryAmount=inventoryAmount;
    }

    
    
}
