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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckReportController {
    
    public static String[] dates=new String[2]; 

    @GetMapping(value = "/checkReport", produces = MediaType.TEXT_HTML_VALUE)
    public String serveHomePage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/checkReport.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping("/showImportTable")
    public List<Inventory> showImportTable() {
        //查query的時候，日期從dates[]拿，dates[0]是起始點，dates[1]是終點
        //查完之後新建物件，把資料塞到inventory裡，可以用資料比數跑for迴圈之類的
        
        List<Inventory> imports = new ArrayList<>();

        Inventory e1=new Inventory("0","2024-01-01","mouse","5","5000");
        Inventory e2=new Inventory("1","2024-01-02","cow","5","10000");
        imports.add(e1);
        imports.add(e2);
        return imports;
    }

    @GetMapping("/showExportTable")
    public List<Inventory> showExportTable() {
        
        //查query的時候，日期從dates[]拿，dates[0]是起始點，dates[1]是終點
        //查完之後新建物件，把資料塞到inventory裡，可以用資料比數跑for迴圈之類的
        List<Inventory> exports = new ArrayList<>();

        Inventory e1=new Inventory("0","2024-03-15","mouse","3","120");
        Inventory e2=new Inventory("1","2024-03-25","cow","2","400");
        exports.add(e1);
        exports.add(e2);
        return exports;
    }
    
    @GetMapping("/showExpenseTable")
    public List<OtherExpense> showExpenseTable() {
        
        //查query的時候，日期從dates[]拿，dates[0]是起始點，dates[1]是終點
        //查完之後新建物件，把資料塞到otherExpense裡，可以用資料比數跑for迴圈之類的
        List<OtherExpense> expenses = new ArrayList<>();

        OtherExpense e1=new OtherExpense("0","2024-01-01","e1","500");
        OtherExpense e2=new OtherExpense("1","2024-01-02","e2","400");
        expenses.add(e1);
        expenses.add(e2);
        return expenses;
    }

    @PostMapping("/getSelectDate")
    public String[] getSelectDate(@RequestParam("minSelectDate") List<String> minSelectDate,
    @RequestParam("maxSelectDate") List<String> maxSelectDate) {
        
        /*把這裡取到的資料丟進去DB費用表*/

        System.out.println("minSelectDate: " + minSelectDate + 
        "\nmaxSelectDate: " + maxSelectDate);

        String minDate=minSelectDate.get(0);
        String maxDate=maxSelectDate.get(0);

        String[] dates=new String[2];
        dates[0]=minDate;
        dates[1]=maxDate;

        this.dates=dates;

        System.out.println(dates[0]+" "+dates[1]);
        return dates;
    }
    
    @PostMapping("/getImportData")
    public void getImportData(@RequestBody RowData rowData) {
        // 处理接收到的数据
        System.out.println("Received data: " + rowData.toString());
        System.out.println("good");
        // 进行后续操作
    }

    @PostMapping("/deleteImportData")
    public void deleteImportData(@RequestBody RowData rowData) {
        // 处理接收到的数据
        System.out.println("Received data: " + rowData.toString());
        System.out.println("good");
        // 进行后续操作
    }

    @PostMapping("/getExportData")
    public void getExportData(@RequestBody RowData rowData) {
        // 处理接收到的数据
        System.out.println("Received data: " + rowData.toString());
        System.out.println("good");
        // 进行后续操作
    }

    @PostMapping("/deleteExportData")
    public void deleteExportData(@RequestBody RowData rowData) {
        // 处理接收到的数据
        System.out.println("Received data: " + rowData.toString());
        System.out.println("good");
        // 进行后续操作
    }

    @PostMapping("/getExpenseData")
    public void getExpenseData(@RequestBody ExpenseData expenseData) {
        // 处理接收到的数据
        System.out.println("Received data: " + expenseData.toString());
        System.out.println("good");
        // 进行后续操作
    }

    @PostMapping("/deleteExpenseData")
    public void deleteExpenseData(@RequestBody ExpenseData expenseData) {
        // 处理接收到的数据
        System.out.println("Received data: " + expenseData.toString());
        System.out.println("good");
        // 进行后续操作
    }


    static class RowData {
        private String column1;
        private String column2;
        private String column3;
        private String column4;
        private String column5;

        public String getColumn1() {
            return column1;
        }
        public void setColumn1(String column1) {
            this.column1 = column1;
        }
        public String getColumn2() {
            return column2;
        }
        public void setColumn2(String column2) {
            this.column2 = column2;
        }
        public String getColumn3() {
            return column3;
        }
        public String getColumn4() {
            return column4;
        }
        public void setColumn4(String column4) {
            this.column4 = column4;
        }
        public String getColumn5() {
            return column5;
        }
        public void setColumn5(String column5) {
            this.column5 = column5;
        }
        
        @Override
        public String toString() {
            return "RowData:" +
                    "column1='" + column1 + '\'' +
                    ", column2='" + column2 + '\'' +
                    ", column3='" + column3 + '\'' +
                    ", column4='" + column4 + '\''+
                    ", column5='" + column5 + '\'';
        }
    }

    static class ExpenseData{
        private String column1;
        private String column2;
        private String column3;
        private String column4;

        public String getColumn1() {
            return column1;
        }
        public void setColumn1(String column1) {
            this.column1 = column1;
        }
        public String getColumn2() {
            return column2;
        }
        public void setColumn2(String column2) {
            this.column2 = column2;
        }
        public String getColumn3() {
            return column3;
        }
        public String getColumn4() {
            return column4;
        }
        public void setColumn4(String column4) {
            this.column4 = column4;
        }
        
        @Override
        public String toString() {
            return "RowData:" +
                    "column1='" + column1 + '\'' +
                    ", column2='" + column2 + '\'' +
                    ", column3='" + column3 + '\'' +
                    ", column4='" + column4 + '\'';
        }
    }
}








