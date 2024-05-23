package com.example.demo.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;


public class SqlManager {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String shopID = "";

    @PostConstruct
    public void init() {
        System.out.println("MySQL Connection Success \n");
    }

     // 註冊商店
    public void enroll(String shopName, String password) {
        String[][] result = checkShopName(shopName);
        if (result != null && result.length > 0) {
            System.out.println("店面名稱已存在，請選擇其他名稱。");
            return;
        }

        String columns = "`shopName`, `shopPassword`";
        String data = String.format("'%s', '%s'", shopName, password);
        addData("shop", columns, data);
        System.out.println("註冊成功！");
    }

    // 檢查登入
    public boolean checkSign(String shopName, String password) {
        boolean correct = false;

        String[][] result = checkShopName(shopName);
        if (result == null || result.length == 0) {
            return false;
        }

        String[][] passwordResult = search("`shopPassword`", "shop", String.format("shopName = '%s'", shopName));
        if (passwordResult != null && passwordResult.length > 0 && passwordResult[0].length > 0 && passwordResult[0][0] != null) {
            String standard = passwordResult[0][0];

            if (standard.equals(password)) {
                correct = true;
                this.shopID = result[0][0];
            }
        }

        return correct;
    }

    // 修改密碼
    public void changePassword(String newPassword, String shopName) {
        String query = String.format("UPDATE shop SET shopPassword = '%s' WHERE shopName = '%s';", newPassword, shopName);
        jdbcTemplate.update(query);
    }

    // 檢查店家名稱是否存在
    public String[][] checkShopName(String shopName) {
        String query = String.format("SELECT shopName FROM shop WHERE shopName = '%s'", shopName);
        return executeQuery(query);
    }

    // 根據月份查詢帳單
    public String[][] getBillsByMonth(int month) {
        String query = "SELECT b.shopID, b.billID, bi.billName, bi.occurDate, bi.cost " +
                "FROM bill b " +
                "LEFT JOIN bill_info bi ON b.billID = bi.billID " +
                "WHERE MONTH(bi.occurDate) = " + month;
        return executeQuery(query);
    }

	/*這個是bill的取出方式
	 *  String[][] result = sql.getBillsByMonth(5);
        for (String[] row : result) {
            for (String column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
        output:
        1	1001	Bill 1	2024-05-01	500	
		1	1002	Bill 2	2024-05-02	700	
		2	2001	Bill 3	2024-05-03	300	
	*/

	//帳單添加成功
	public void addBill(String billID, String billName, String occurDate, double cost) {
		// Add entry to the 'bill' table
		String billColumns = "shopID, billID";
		String billData = String.format("'%s', '%s'", this.shopID, billID);
		addData("bill", billColumns, billData);
	
		// Add entry to the 'bill_info' table
		String billInfoColumns = "billID, billName, occurDate, cost";
		String billInfoData = String.format("'%s', '%s', '%s', %f", billID, billName, occurDate, cost);
		addData("bill_info", billInfoColumns, billInfoData);
	
		System.out.println("帳單添加成功！");
	}
	
    // 新增進口
    public void addImport(String itemID, int importAmount, String importDate) {
        String table = "import";
        String columns = "shopID, itemID, importAmount, importDate";
        String data = String.format("%s, %s, %d, '%s'", this.shopID, itemID, importAmount, importDate);
        addData(table, columns, data);
    }

    // 新增商品
    public void addItem(String itemID, String itemName, int unitPerBox, int importPrice, int sellingPricePerUnit, int alertAmount) {
        String table = "item";
        String columns = "shopID, itemID, itemName, unitPerBox, importPrice, sellingPricePerUnit, alertAmount";
        String data = String.format("'%s', '%s', '%s', %d, %d, %d, %d", this.shopID, itemID, itemName, unitPerBox, importPrice, sellingPricePerUnit, alertAmount);
        addData(table, columns, data);
    }

    // 根據商品名稱獲取商品ID
    public String getItemIDByName(String itemName) {
        String query = String.format("SELECT itemID FROM item WHERE itemName = '%s'", itemName);
        List<String> results = jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("itemID"));
        return results.isEmpty() ? null : results.get(0);
    }

	
    // 新增出口
    public void addExport(String itemID, int exportAmount, String exportDate) {
        String table = "export";
        String columns = "shopID, itemID, exportAmount, exportDate";
        String data = String.format("'%s', '%s', %d, '%s'", this.shopID, itemID, exportAmount, exportDate);
        addData(table, columns, data);
    }

    // 新增庫存
    public void addInventory(String itemID, int remainAmount) {
        String table = "inventory";
        String columns = "shopID, itemID, remainAmount";
        String data = String.format("'%s', '%s', %d", this.shopID, itemID, remainAmount);
        addData(table, columns, data);
    }

    // 更新庫存
    public void updateInventory(String itemID, int newAmount) {
        String query = String.format("UPDATE inventory SET remainAmount = %d WHERE shopID = '%s' AND itemID = '%s'", newAmount, this.shopID, itemID);
        jdbcTemplate.update(query);
    }

    // 獲取庫存量
    public int getInventoryAmount(String itemID) {
        String[][] result = search("remainAmount", "inventory", String.format("shopID = '%s' AND itemID = '%s'", this.shopID, itemID));
        return (result != null && result.length > 0 && result[0].length > 0) ? Integer.parseInt(result[0][0]) : 0;
    }

    // 更新商品表格的指定列
    public void updateItemColumn(String itemID, String columnName, String newValue) {
        String query = String.format("UPDATE item SET %s = '%s' WHERE itemID = '%s'", columnName, newValue, itemID);
        jdbcTemplate.update(query);
    }

    // 更新店鋪表格的指定列
    public void updateShopColumn(String shopID, String columnName, String newValue) {
        String query = String.format("UPDATE shop SET %s = '%s' WHERE shopID = '%s'", columnName, newValue, shopID);
        jdbcTemplate.update(query);
    }

    // 更新帳單表格的指定列
    public void updateBillColumn(String shopID, String billID, String columnName, String newValue) {
        String query = String.format("UPDATE bill SET %s = '%s' WHERE shopID = '%s' AND billID = '%s'", columnName, newValue, shopID, billID);
        jdbcTemplate.update(query);
    }

    // 更新帳單資訊表格的指定列
    public void updateBillInfoColumn(String billID, String columnName, String newValue) {
        String query = String.format("UPDATE bill_info SET %s = '%s' WHERE billID = '%s'", columnName, newValue, billID);
        jdbcTemplate.update(query);
    }

    // 更新出口表格的指定列
    public void updateExportColumn(String shopID, String itemID, String columnName, String newValue) {
        String query = String.format("UPDATE export SET %s = '%s' WHERE shopID = '%s' AND itemID = '%s'", columnName, newValue, shopID, itemID);
        jdbcTemplate.update(query);
    }

    // 更新進口表格的指定列
    public void updateImportColumn(String shopID, String itemID, String columnName, String newValue) {
        String query = String.format("UPDATE import SET %s = '%s' WHERE shopID = '%s' AND itemID = '%s'", columnName, newValue, shopID, itemID);
        jdbcTemplate.update(query);
    }

	

    // 添加資料到指定表格
    private void addData(String table, String columns, String data) {
        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", table, columns, data);
        jdbcTemplate.update(query);
    }

    // 根據指定條件查詢資料
    public String[][] search(String selectColumns, String table, String condition) {
        String query = String.format("SELECT %s FROM %s WHERE %s;", selectColumns, table, condition);
        return executeQuery(query);
    }

    // 執行查詢並返回結果
    private String[][] executeQuery(String query) {
        List<String[]> results = jdbcTemplate.query(query, (rs, rowNum) -> {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getString(i + 1);
            }
            return row;
        });
        return results.toArray(new String[0][0]);
    }
}