package application;

import java.sql.*;
import java.time.LocalDateTime;

public class SqlManager {
	Connection conn;
	static Statement stat;
	private String account = "account";
	private String columns;
	private String data;
	private String condition;
	private boolean login;
	
	public SqlManager () throws SQLException{
		String server = "";
		String database = ""; // change to your own database
		String url = server + database + "?useSSL=false";
		String username = "root";
		String password = "";
		
		this.conn = DriverManager.getConnection(url, username, password);
		System.out.println("DB connected");
			
		stat = conn.createStatement();
	}
	

	 
	public void enroll(String account,String password) {
		columns = "Account,Password";
		data = String.format("'%s','%s'",account,password);
		addData(this.account,columns,data);
	}
	
	/* 	
	
	public boolean checkSign(String account,String password) {
		boolean correct = false;
		
		if(!checkAccount(account)) {
			correct = false;
		}else {
			String standard = search("`Password`",this.account,String.format("Account =  '%s' ", account));
						
			if(standard.equals(password)) {
				correct = true;
			}else {
				correct = false;
			}
		}
		return correct;
	}
	*/
	/* 
	public void changePassword(String account, String newPassword) {
		String query;
		query = String.format("UPDATE %s\n SET Password = %s\n WHERE Account = '%s';",this.account,newPassword,account);
		
		try {
			stat.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    */
	
	/*
	public void changeAccInfo(String account,String setCol,String changeInfo) {
		String query;
		boolean success;
		query = String.format("UPDATE %s\n SET %s = %s\n WHERE Account = '%s';",this.account,setCol,changeInfo,account);
		
		try {
			stat.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	
	public boolean checkAccount(String account) {
		boolean exist = false;
		String query;
		String output ="";
		boolean success;
		columns = "Account";
		condition = String.format("Account = '%s'",account);
		query = String.format("SELECT %s From %s WHERE %s",columns,this.account,condition);
		
		
		try {
			success = stat.execute(query);
			ResultSet result = stat.getResultSet();
			output = showResultSet(result);
			result.close();
			
			if (output == null) {
				exist = false;
			}else {
				exist = true;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exist;
	}
	

	public  String getAccount(String account) {
		String id = "";
		id = search("Account",this.account,String.format("Account = '%s'", account));
		return id;
	}
	
	
	public static String search(String columns,String table,String condition) {
		String output = "";
		String query;
		boolean success;
		query = String.format("SELECT %s From %s WHERE %s",columns,table,condition);
		
		try {
			success = stat.execute(query);
			if (success) {
				ResultSet result = stat.getResultSet();
				output = showResultSet(result);
				result.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
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

	public static String showResultSet(ResultSet result) throws SQLException {
	    ResultSetMetaData metaData = result.getMetaData();
	    int columnCount = metaData.getColumnCount();
	    String output = "";

	    while (result.next()) {
	        for (int i = 1; i <= columnCount; i++) {
	            output += String.format("%s" + " ", result.getString(i));
	        }
	        output += "\n";
	    }

	    if (output.isEmpty()) {
	        output = null;
	    }else {
	    	output = output.substring(0,output.length()-2);	
	    }
	   
	    return output;
	}
		
}
