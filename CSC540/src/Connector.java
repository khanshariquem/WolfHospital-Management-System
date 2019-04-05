import java.sql.*;
public class Connector {
	
	public static Connection con;
	public static Statement stmt;
	public static PreparedStatement prepStmt;	
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
	
	public static void createStatement() {
		
		try{  
			stmt = con.createStatement();       
			}
		catch(Exception e){
				e.printStackTrace();
			}   	
	}
	
	public static void createPreparedStatement(String query) {
		
		try{  
			prepStmt = con.prepareStatement(query);       
			}
		catch(Exception e){
				e.printStackTrace();
			}   	
	}
	
	public static void setPreparedStatement(int key , String value) {
		
		try{  
			prepStmt.setString(key, value);       
			}
		catch(Exception e){
				e.printStackTrace();
			}   	
	}
	
	public static ResultSet executeQuery(String query) {
		ResultSet result = null;
		try{ 
			 result = stmt.executeQuery(query);
			}
		catch(Exception e){
				e.printStackTrace();
			} 	
		return result;	
	}
	
	public static ResultSet executePreparedQuery() {
		ResultSet result = null;
		try{ 
			 result = prepStmt.executeQuery();
			}
		catch(Exception e){
				e.printStackTrace();
			} 	
		return result;	
	}

}
