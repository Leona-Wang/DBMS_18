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

    public String shopID = "";

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

	public void addBill(String shopID, String billName, String occurDate, String cost) {
		// 在 bill 表格中插入帳單資訊
		String billInsertQuery = "INSERT INTO bill (shopID) VALUES (?)";
		jdbcTemplate.update(billInsertQuery, shopID);
	
		// 獲取剛插入的帳單的 billID
		String getBillIDQuery = "SELECT LAST_INSERT_ID()";
		@SuppressWarnings("null")
		int billID = jdbcTemplate.queryForObject(getBillIDQuery, Integer.class);
	
		// 在 bill_info 表格中插入帳單資訊
		String billInfoInsertQuery = "INSERT INTO bill_info (billID, billName, occurDate, cost) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(billInfoInsertQuery, billID, billName, occurDate, cost);
	
		System.out.println("帳單添加成功！");
	}
	
	// 新增進口
	public void addImport(String itemID, String importAmount, String importDate) {
		String table = "import";
		String columns = "shopID, itemID, importAmount, importDate";
		String data = String.format("%s, %s, %d, '%s'", this.shopID, itemID, importAmount, importDate);
		addData(table, columns, data);
	}
	
	// 新增商品
	public void addItem(String shopID, String itemName, String unitPerBox, String importPrice, String sellingPricePerUnit, String alertAmount) {
		String table = "item";
		String columns = "shopID, itemName, unitPerBox, importPrice, sellingPricePerUnit, alertAmount";
		String data = String.format("'%s','%s', %d, %d, %d, %d", shopID, itemName, Integer.parseInt(unitPerBox), Integer.parseInt(importPrice), Integer.parseInt(sellingPricePerUnit), Integer.parseInt(alertAmount));
		addData(table, columns, data);
	}
	
	// 根據商品名稱獲取商品ID
	public String getItemIDByName(String itemName) {
		String query = String.format("SELECT itemID FROM item WHERE itemName = '%s'", itemName);
		List<String> results = jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("itemID"));
		return results.isEmpty() ? null : results.get(0);
	}
	
	// 新增出口
	public void addExport(String shopID, String itemID, String exportAmount, String exportDate) {
		String table = "export";
		String columns = "shopID, itemID, exportAmount, exportDate";
		String data = String.format("'%s', '%s', %d, '%s'", shopID, itemID, Integer.parseInt(exportAmount), exportDate);
		addData(table, columns, data);
	}
	
	// 新增庫存
	public void addInventory(String shopID, String itemID, String remainAmount) {
		String table = "inventory";
		String columns = "shopID, itemID, remainAmount";
		String data = String.format("'%s', '%s', %d", shopID, itemID, Integer.parseInt(remainAmount));
		addData(table, columns, data);
	}
	
	// 更新庫存
	public void updateInventory(String shopID, String itemID, String newAmount) {
		String query = String.format("UPDATE inventory SET remainAmount = %d WHERE shopID = '%s' AND itemID = '%s'", Integer.parseInt(newAmount), shopID, itemID);
		jdbcTemplate.update(query);
	}
	
	// 獲取庫存量
	public int getInventoryAmount(String shopID, String itemID) {
		String[][] result = search("remainAmount", "inventory", String.format("shopID = '%s' AND itemID = '%s'", shopID, itemID));
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