package com.example.demo.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;


public class SqlManager {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String shopID = "";

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
				String[][] id=search("`shopID`","shop",String.format("shopName = '%s'", shopName));
                SqlManager.shopID = id[0][0];
				System.out.println(shopID);
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

	// 根據指定日期查詢帳單
	public String[][] getBillsBetweenDates(String startDate, String endDate) {
        String query = String.format(
            "SELECT b.shopID, b.billID, bi.billName, bi.occurDate, bi.cost " +
            "FROM bill b " +
            "LEFT JOIN bill_info bi ON b.billID = bi.billID " +
            "WHERE bi.occurDate BETWEEN '%s' AND '%s'", 
            startDate,
            endDate
        );
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

	public void addBill(String billName, String occurDate, String cost) {
		// 在 bill 表格中插入帳單資訊
		String billInsertQuery = "INSERT INTO bill (shopID) VALUES (?)";
		jdbcTemplate.update(billInsertQuery, SqlManager.shopID);
	
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
		String data = String.format("%d, %d, %d, '%s'", Integer.parseInt(SqlManager.shopID), Integer.parseInt(itemID), Integer.parseInt(importAmount), importDate);
		//String data = String.format("%s, %s, %d, '%s'", SqlManager.shopID, itemID, importAmount, importDate);
		addData(table, columns, data);
	}
	
	// 新增商品
	public void addItem(String itemName, String unitPerBox, String importPrice, String sellingPricePerUnit, String alertAmount) {
		String table = "item";
		String columns = "shopID, itemName, unitPerBox, importPrice, sellingPricePerUnit, alertAmount";
		String data = String.format("'%s','%s', %d, %d, %d, %d", SqlManager.shopID, itemName, Integer.parseInt(unitPerBox), Integer.parseInt(importPrice), Integer.parseInt(sellingPricePerUnit), Integer.parseInt(alertAmount));
		addData(table, columns, data);
	}
	
	// 根據商品名稱獲取商品ID
	public String getItemIDByName(String itemName) {
		String query = String.format("SELECT itemID FROM item WHERE itemName = '%s'", itemName);
		List<String> results = jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("itemID"));
		return results.isEmpty() ? null : results.get(0);
	}

	 // 獲取指定日期範圍內的出口總量
    public String getTotalAmountOfExportBetweenDates(String startDate, String endDate) {
        String query = String.format(
            "SELECT shopID, SUM(exportAmount) AS totalExportAmount " +
            "FROM export " +
            "WHERE shopID = '%s' AND exportDate BETWEEN '%s' AND '%s' " +
            "GROUP BY shopID", SqlManager.shopID, startDate, endDate
        );
        return convertToString(executeQuery(query));
    }

	// 根據日期範圍計算出口的總成本
	public String getTotalCostOfExportBetweenDates(String startDate, String endDate) {
		String query = String.format(
			"SELECT SUM(it.sellingPricePerUnit * e.exportAmount) AS totalCost " +
			"FROM export e " +
			"LEFT JOIN item it ON e.itemID = it.itemID " +
			"WHERE e.shopID = '%s' AND e.exportDate BETWEEN '%s' AND '%s'",
			SqlManager.shopID, startDate, endDate
		);
		String[][] result = executeQuery(query);
		return result.length > 0 ? result[0][0] : "0";
	}

	// 根據日期範圍計算進口的總收入
	public String getTotalRevenueOfImportBetweenDates(String startDate, String endDate) {
		String query = String.format(
			"SELECT SUM(it.importPrice * im.importAmount) AS totalRevenue " +
			"FROM import im " +
			"LEFT JOIN item it ON im.itemID = it.itemID " +
			"WHERE im.shopID = '%s' AND im.importDate BETWEEN '%s' AND '%s'",
			SqlManager.shopID, startDate, endDate
		);
		String[][] result = executeQuery(query);
		return result.length > 0 ? result[0][0] : "0";
	}


    // 將查詢結果轉換為字符串
    private String convertToString(String[][] queryResults) {
        return queryResults != null ? 
               Arrays.stream(queryResults)
                     .map(row -> String.join(",", row))
                     .collect(Collectors.joining("\n")) : "";
    }

	// 新增出口
	public void addExport(String itemID, String exportAmount, String exportDate) {
		String table = "export";
		String columns = "shopID, itemID, exportAmount, exportDate";
		String data = String.format("'%s', '%s', %d, '%s'", SqlManager.shopID, itemID, Integer.parseInt(exportAmount), exportDate);
		addData(table, columns, data);
	}
	
	// 新增庫存
	public void addInventory(String itemID, String remainAmount) {
		String table = "inventory";
		String columns = "shopID, itemID, remainAmount";
		//search(String selectColumns, String table, String condition)
		//String.format("shopID = '%s' AND itemID = '%s'", SqlManager.shopID, itemID)
		//search("remainAmount", "inventory", String.format("shopID = '%s' AND itemID = '%s'", SqlManager.shopID, itemID))
		String[][] unitPerBox=search("unitPerBox","item",String.format("shopID = %d AND itemID = %d",Integer.parseInt(SqlManager.shopID) , Integer.parseInt(itemID)));
		String unitRemainAmount=(Integer.parseInt(remainAmount)*Integer.parseInt(unitPerBox[0][0]))+"";
		if (getInventoryAmount(itemID)>0){
			String amount=(getInventoryAmount(itemID)+Integer.parseInt(unitRemainAmount))+"";
			System.out.println("Amount: "+amount);
			updateInventory(itemID, amount);
		}
		else{
			String data = String.format("%d, %d, %d", Integer.parseInt(SqlManager.shopID),Integer.parseInt(itemID), Integer.parseInt(unitRemainAmount));
			System.out.println(data);
			addData(table, columns, data);
		}
		

		
	}
	
	// 更新庫存
	public void updateInventory(String itemID, String newAmount) {
		String query = String.format("UPDATE inventory SET remainAmount = %d WHERE shopID = %d AND itemID = %d", Integer.parseInt(newAmount), Integer.parseInt(SqlManager.shopID), Integer.parseInt(itemID));
		System.out.println(query);
		jdbcTemplate.update(query);
	}
	
	// 獲取庫存量
	public int getInventoryAmount(String itemID) {
		String[][] result = search("remainAmount", "inventory", String.format("shopID = '%s' AND itemID = '%s'", SqlManager.shopID, itemID));
		return (result != null && result.length > 0 && result[0].length > 0) ? Integer.parseInt(result[0][0]) : 0;
	}

	// 獲取指定商店的所有帳單數據
    public String[][] getBillData() {
        String query = String.format(
            "SELECT b.shopID, b.billID, bi.billName, bi.occurDate, bi.cost " +
            "FROM bill b " +
            "LEFT JOIN bill_info bi ON b.billID = bi.billID " +
            "WHERE b.shopID = '%s'", SqlManager.shopID
        );
        return executeQuery(query);
    }
	
	// 根據 itemID 查詢商品名稱
	public String getItemNameById(String itemID) {
		String query = String.format(
			"SELECT itemName " +
			"FROM item " +
			"WHERE shopID = %d AND itemID = %d", 
			Integer.parseInt(SqlManager.shopID), Integer.parseInt(itemID)
		);
		return convertToString(executeQuery(query));
	}


	// 更新商品表格的指定列
	public void updateItemColumn(String itemID, String columnName, String newValue) {
		String query = String.format("UPDATE item SET %s = '%s' WHERE itemID = '%s'", columnName, newValue, itemID);
		jdbcTemplate.update(query);
	}
	
	// 更新店鋪表格的指定列
	public void updateShopColumn(String columnName, String newValue) {
		String query = String.format("UPDATE shop SET %s = '%s' WHERE shopID = '%s'", columnName, newValue, SqlManager.shopID);
		jdbcTemplate.update(query);
	}
	
	// 更新帳單表格的指定列
	public void updateBillColumn(String billID, String columnName, String newValue) {
		String query = String.format("UPDATE bill SET %s = '%s' WHERE shopID = '%s' AND billID = '%s'", columnName, newValue, SqlManager.shopID, billID);
		jdbcTemplate.update(query);
	}
	
	// 更新帳單資訊表格的指定列
	public void updateBillInfoColumn(String billID, String columnName, String newValue) {
		String query = String.format("UPDATE bill_info SET %s = '%s' WHERE billID = '%s'", columnName, newValue, billID);
		jdbcTemplate.update(query);
	}

	// 更新出口表格的出口數量
	public void updateExportAmount(String itemID, int newExportAmount) {
		String query = String.format("UPDATE export SET exportAmount = %d WHERE shopID = '%s' AND itemID = '%s'", newExportAmount, SqlManager.shopID, itemID);
		jdbcTemplate.update(query);
	}

	// 更新進口表格的進口數量
	public void updateImportAmount(String itemID, int newImportAmount) {
		String query = String.format("UPDATE import SET importAmount = %d WHERE shopID = '%s' AND itemID = '%s'", newImportAmount, SqlManager.shopID, itemID);
		jdbcTemplate.update(query);
	}

	// 刪除進貨資料
	public void deleteImportData(String itemID, String importDate) {
		String condition = String.format("shopID = '%s' AND itemID = '%s' AND importDate = '%s'",SqlManager.shopID, itemID, importDate);
		delete("import", condition);
	}

	// 刪除出貨資料
	public void deleteExportData(String itemID, String exportDate) {
		String condition = String.format("shopID = '%s' AND itemID = '%s' AND exportDate = '%s'", SqlManager.shopID, itemID, exportDate);
		delete("export", condition);
	}

	// 刪除費用資料
	public void deleteExpenseData(String billID) {
		String condition = String.format("shopID = '%s' AND billID = '%s'", SqlManager.shopID, billID);
		delete("bill", condition);
	}

	// 查詢進口數據
	public String[][] getImportData() {
		String query = String.format(
			"SELECT shopID, itemID, importAmount, importDate FROM import WHERE shopID = '%s'", 
			SqlManager.shopID
		);

		List<String[]> results = jdbcTemplate.query(query, (ResultSet rs) -> {
			List<String[]> rows = new ArrayList<>();
			while (rs.next()) {
				String[] row = new String[4];
				row[0] = rs.getString("shopID");
				row[1] = rs.getString("itemID");
				row[2] = rs.getString("importAmount");
				row[3] = rs.getString("importDate");
				rows.add(row);
			}
			return rows;
		});

		return results.toArray(new String[0][0]);
	}

	// 查詢出口數據
	public String[][] getExportData() {
		String query = String.format(
			"SELECT shopID, itemID, exportAmount, exportDate FROM export WHERE shopID = '%s'", 
			SqlManager.shopID
		);

		List<String[]> results = jdbcTemplate.query(query, (ResultSet rs) -> {
			List<String[]> rows = new ArrayList<>();
			while (rs.next()) {
				String[] row = new String[4];
				row[0] = rs.getString("shopID");
				row[1] = rs.getString("itemID");
				row[2] = rs.getString("exportAmount");
				row[3] = rs.getString("exportDate");
				rows.add(row);
			}
			return rows;
		});

		return results.toArray(new String[0][0]);
	}

	// 根據日期範圍查詢進口數據
	public String[][] getImportByDate(String startDate, String endDate) {
		String query = String.format(
			"SELECT i.shopID, im.importDate, it.itemName, im.importAmount, (it.importPrice * im.importAmount) AS totalCost " +
			"FROM import im " +
			"LEFT JOIN item it ON im.itemID = it.itemID " +
			"WHERE im.shopID = '%s' AND im.importDate BETWEEN '%s' AND '%s'", 
			SqlManager.shopID, startDate, endDate
		);
		return executeQuery(query);
	}

	// 根據日期範圍查詢出口數據
	public String[][] getExportByDate(String startDate, String endDate) {
		String query = String.format(
			"SELECT e.shopID, e.exportDate, it.itemName, e.exportAmount, (it.sellingPricePerUnit * e.exportAmount) AS totalRevenue " +
			"FROM export e " +
			"LEFT JOIN item it ON e.itemID = it.itemID " +
			"WHERE e.shopID = '%s' AND e.exportDate BETWEEN '%s' AND '%s'", 
			SqlManager.shopID, startDate, endDate
		);
		return executeQuery(query);
	}


	// 獲取所有商品名稱
    public List<String> getAllItemNames() {
        String query = String.format("SELECT itemName FROM item WHERE shopID = '%s'", SqlManager.shopID);
        List<String> itemNames = new ArrayList<>();

        jdbcTemplate.query(query, (ResultSet rs) -> {
            while (rs.next()) {
                itemNames.add(rs.getString("itemName"));
            }
            return null; // We don't need to return anything from this lambda function
        });

		
        return itemNames;
		
    }
	
	 // 查詢所有商品
	 public String[][] getItems() {
        String query = "SELECT * FROM item";
        return executeQuery(query);
    }

    // 查詢所有庫存
    public String[][] getInventory() {
        String query = "SELECT * FROM inventory";
        return executeQuery(query);
    }



	// 刪除指定資料
	public void delete(String table, String condition) {
		String query = String.format("DELETE FROM `%s` WHERE %s", table, condition);

		try {
			jdbcTemplate.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    // 添加資料到指定表格
    private void addData(String table, String columns, String data) {
        String query = String.format("INSERT INTO %s (%s) VALUES (%s);", table, columns, data);
		System.out.println(query);
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