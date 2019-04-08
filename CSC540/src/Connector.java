import java.sql.*;
public class Connector {
	
	private static Connection con;
	private static Statement stmt;
	private static PreparedStatement prepStmt;	
	private static final String dbServerURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
	private static final String driverName = "org.mariadb.jdbc.Driver";
	
	public static void createConnection(String dbName , String userName , String password) throws ClassNotFoundException, SQLException {
		 
		Class.forName(driverName); 
		con = DriverManager.getConnection(dbServerURL+dbName,userName,password);     
			
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
	
	public static void setAutoCommit(Boolean value) throws SQLException { 
		con.setAutoCommit(value);
	}
	
	public static void commit() throws SQLException { 
		con.commit();
	}
	
	public static void rollback() throws SQLException { 
		con.rollback();
	}

	public static void createPreparedStatement(String query) throws SQLException {
			prepStmt = con.prepareStatement(query);          	
	}
	
	public static void createPreparedStatementWithKeys(String query) throws SQLException {
		prepStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);          	
	}
	
	public static void setPreparedStatementString(int key , String value) throws SQLException {
			prepStmt.setString(key, value);         	
	}
	
	public static void setPreparedStatementInt(int key , int value) throws SQLException {
		prepStmt.setInt(key, value);         	
	}
	
	public static void setPreparedStatementFloat(int key , float value) throws SQLException {
		prepStmt.setFloat(key, value);         	
	}
	
	public static void setPreparedStatementDate(int key , Date value) throws SQLException {
		prepStmt.setDate(key, value);         	
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
	
	public static ResultSet getGeneratedKeys() throws SQLException {
		return prepStmt.getGeneratedKeys();	
	}

}
