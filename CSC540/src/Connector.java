import java.sql.*;
public class Connector {
	
	public static Connection con;
	private static final String dbServerURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
	private static final String driverName = "org.mariadb.jdbc.Driver";
	
	public static void createConnection(String dbName , String userName , String password) {
		
		try{ 
			Class.forName(driverName); 
			con = DriverManager.getConnection(dbServerURL+dbName,userName,password);         
			}
		catch(Exception e){
				e.printStackTrace();
			}   
			
	}
		
	public static void closeConnection() {
		
		try{  
			con.close();       
			}
		catch(Exception e){
				e.printStackTrace();
			}   	
	}
	

}
