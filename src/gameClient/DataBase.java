package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	
	
	
	
	
	
	private int howManyGame() {
		
		
		int ans = 0;
		final int MY_ID =209313238; 
		String allCustomersQuery = "SELECT * FROM Logs where userID="+MY_ID+";";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
			DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next()) {
				ans++;
			}
			resultSet.close();
			return ans;
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
		
		
		
		
	}
	
	public int currentStage() {
		final int MY_ID =209313238; 
		int currentSt=0;
		String allCustomersQuery = "SELECT * FROM Users where userID="+MY_ID+";";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
			DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			
			while(resultSet.next()) {
				return resultSet.getInt("levelNum");
			}
			resultSet.close();
			return currentSt;
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	
	
	
	
	
}
