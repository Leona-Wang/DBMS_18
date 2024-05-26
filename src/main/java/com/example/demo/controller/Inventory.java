package com.example.demo.controller;

public class Inventory {
    public String inventoryIndex;
    public String date;
    public String inventoryName;
    public String number;
    public String totalMoney;

    public Inventory(String inventoryIndex,String date,String inventoryName,
    String number,String totalMoney){
        this.inventoryIndex=inventoryIndex;
        this.date=date;
        this.inventoryName=inventoryName;
        this.number=number;
        this.totalMoney=totalMoney;
    }

}
