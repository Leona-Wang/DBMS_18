package com.example.demo.controller;

import java.sql.*;


public class SqlManager {
	Connection conn;
	Statement stat;
	private String shopID = "";


	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/shop?useSSL=false";

    // 用户名和密码
    static final String USER = "root";
    static final String PASS = "password";

	public SqlManager () throws SQLException{
		
		try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find driver");
            e.printStackTrace();
        }
        try {
				conn =  DriverManager.getConnection(DB_URL, USER,PASS);
				System.out.println("MySQL Connection Success \n");			   
			} catch (SQLException e) {
				e.printStackTrace();
			}
		stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	// 註冊商店
	public void enroll(String shopName, String password) {
		// 檢查店家名稱是否已存在
		String[][] result = checkShopName(shopName);
		if (result != null && result.length > 0) {
			System.out.println("店面名稱已存在，請選擇其他名稱。");
			return;
		}

		// 如果店家名稱不存在，則新增商店資訊
		String columns = "`shopName`, `shopPassword`";
		String data = String.format("'%s', '%s'", shopName, password);
		addData("shop", columns, data);
		System.out.println("註冊成功！");
   }

   //檢查登入
   public boolean checkSign(String shopName, String password) {
	   boolean correct = false;

	   // 檢查店鋪名稱是否存在
	   String[][] result = checkShopName(shopName);
	   if (result == null || result.length == 0) {
		   return false; // 店鋪名稱不存在，返回 false
	   }

	   // 獲取存儲的密碼
	   String[][] passwordResult = search("`shopPassword`","shop", String.format("shopName = '%s'", shopName));
	   if (passwordResult != null && passwordResult.length > 0 && passwordResult[0].length > 0 && passwordResult[0][0] != null) {
		   String standard = passwordResult[0][0];

		   // 比較密碼
		   if (standard.equals(password)) {
			   correct = true;
			   this.shopID = result[0][0]; // 登入成功，設置商店ID
		   }
	   }

	   return correct;
   }

   
   //修改密碼
   public void changePassword(String newPassword,String shopName) {
	   String query;
	   query = String.format("UPDATE Shop SET shopPassword = '%s' WHERE shopName = '%s';", newPassword, shopName);
	   
	   try {
		   stat.execute(query);
	   } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
   }
   
   
   // 檢查店家名稱是否存在
   public String[][] checkShopName(String shopName) {
	   String[][] resultArray = null;
	   String query = String.format("SELECT shopName FROM shop WHERE shopName = '%s'", shopName);

	   try {
		   ResultSet result = stat.executeQuery(query);
		   result.setFetchSize(0); // 或者嘗試設置為一個大於 0 的值
		   resultArray = showResultSet(result);
		   result.close();
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }

	   return resultArray;
   }


   // 根據月份查詢帳單
   public String[][] getBillsByMonth(int month) {
	   String[][] resultArray = null;
	   String query = "SELECT b.shopID, b.billID, bi.billName, bi.occurDate, bi.cost " +
					  "FROM bill b " +
					  "LEFT JOIN bill_info bi ON b.billID = bi.billID " +
					  "WHERE MONTH(bi.occurDate) = " + month;

	   try {
		   ResultSet result = stat.executeQuery(query);
		   if (result != null) {
			   resultArray = showResultSet(result);
			   result.close();
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }

	   return resultArray != null ? resultArray : new String[0][0];
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
	   String itemID = null; // 默認值，如果沒有找到，返回 null
	   String query = String.format("SELECT itemID FROM item WHERE itemName = '%s'", itemName);
	   
	   try {
		   ResultSet result = stat.executeQuery(query);
		   if (result != null && result.next()) {
			   itemID = result.getString("itemID");
		   }
		   result.close();
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return itemID;
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
	   String data = String.format("'%s', '%s', %d", this.shopID,itemID, remainAmount);
	   addData(table, columns, data);
   }

   // 更新庫存
   public void updateInventory(String itemID, int newAmount) {
	   String query = String.format("UPDATE inventory SET remainAmount = %d WHERE shopID = '%s' AND itemID = '%s'", newAmount, this.shopID, itemID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 獲取庫存量
   public int getInventoryAmount(String itemID) {
	   int remainAmount = 0;
	   String[][] result = search("remainAmount", "inventory", String.format("shopID = '%s' AND itemID = '%s'", this.shopID, itemID));
	   
	   if (result != null && result.length > 0 && result[0].length > 0) {
		   remainAmount = Integer.parseInt(result[0][0]);
	   }
	   
	   return remainAmount;
   }

   // 更新商品表格的指定列
   public void updateItemColumn(String itemID, String columnName, String newValue) {
	   String query = String.format("UPDATE item SET %s = '%s' WHERE itemID = '%s'", columnName, newValue, itemID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新店鋪表格的指定列
   public void updateShopColumn(String shopID, String columnName, String newValue) {
	   String query = String.format("UPDATE shop SET %s = '%s' WHERE shopID = '%s'", columnName, newValue, shopID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新帳單表格的指定列
   public void updateBillColumn(String shopID, String billID, String columnName, String newValue) {
	   String query = String.format("UPDATE bill SET %s = '%s' WHERE shopID = '%s' AND billID = '%s'", columnName, newValue, shopID, billID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新帳單資訊表格的指定列
   public void updateBillInfoColumn(String billID, String columnName, String newValue) {
	   String query = String.format("UPDATE bill_info SET %s = '%s' WHERE billID = '%s'", columnName, newValue, billID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新出口表格的指定列
   public void updateExportColumn(String shopID, String itemID, String columnName, String newValue) {
	   String query = String.format("UPDATE export SET %s = '%s' WHERE shopID = '%s' AND itemID = '%s'", columnName, newValue, shopID, itemID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新進口表格的指定列
   public void updateImportColumn(String shopID, String itemID, String columnName, String newValue) {
	   String query = String.format("UPDATE import SET %s = '%s' WHERE shopID = '%s' AND itemID = '%s'", columnName, newValue, shopID, itemID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }

   // 更新庫存表格的指定列
   public void updateInventoryColumn(String shopID, String itemID, String columnName, String newValue) {
	   String query = String.format("UPDATE inventory SET %s = '%s' WHERE shopID = '%s' AND itemID = '%s'", columnName, newValue, shopID, itemID);
	   
	   try {
		   stat.executeUpdate(query);
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
   }


   // 搜尋資料
   public String[][] search(String columns, String table, String condition) {
	   String[][] resultArray = null;
	   String query = String.format("SELECT %s FROM %s WHERE %s", columns, table, condition);

	   try {
		   ResultSet result = stat.executeQuery(query);
		   if (result != null) {
			   resultArray = showResultSet(result);
			   result.close();
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }

	   return resultArray != null ? resultArray : new String[0][0];
   }

   // 新增資料
   public void addData(String table,String columns,String data) {
	   String query;
	   boolean success;
	   query = String.format("INSERT INTO `%s`(%s)VALUES(%s)",table,columns,data);
	   
	   try {
		   stat.execute(query);
	   } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
   }

   // 刪除資料
   public void delete(String table,String condition) {
	   String query;
	   boolean success;
	   query = String.format("DELETE FROM `%s` WHERE %s",table,condition);
	   
	   try {
		   stat.execute(query);
	   } catch (SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
   }

   // 顯示結果集
   public static String[][] showResultSet(ResultSet result) throws SQLException {
	   ResultSetMetaData metaData = result.getMetaData();
	   int columnCount = metaData.getColumnCount();
	   int rowCount = 0;

	   // 獲取結果集行數
	   if (result.last()) {
		   rowCount = result.getRow();
		   result.beforeFirst(); // 將結果集游標移到第一行之前
		   }
	   // 創建二維數組用於存儲結果
	   String[][] output = new String[rowCount][columnCount];

	   int rowIndex = 0;
	   while (result.next()) {
		   for (int i = 1; i <= columnCount; i++) {
			   output[rowIndex][i - 1] = result.getString(i);
		   }
		   rowIndex++;
	   }

	   return output;
   }

   // 可用於顯示錯誤訊息
   class AccountError extends Exception {
	   public AccountError(String Error){
	   super(Error);
	   }
   }
}

