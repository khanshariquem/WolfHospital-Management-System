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
	
	public static void createStatement() throws SQLException { 
			stmt = con.createStatement();        	
	}
	
	public static void createPreparedStatement(String query) throws SQLException {
			prepStmt = con.prepareStatement(query);          	
	}
	
	public static void setPreparedStatement(int key , String value) throws SQLException {
			prepStmt.setString(key, value);         	
	}
	
	public static ResultSet executeQuery(String query) throws SQLException {
		return stmt.executeQuery(query);
	}
	
	public static ResultSet executePreparedQuery() throws SQLException {	
		return prepStmt.executeQuery();	
	}
	
	public static int executeUpdatePreparedQuery() throws SQLException {
			 return prepStmt.executeUpdate();	
	}

}
