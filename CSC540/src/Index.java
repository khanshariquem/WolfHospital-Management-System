import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Index {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("Enter Database User Name");
			String userName = input.next();
			System.out.println("Enter Database Password");
			String password = input.next();
			System.out.println("Enter Database to use");
			String dbName = input.next();
			try {
				Connector.createConnection(dbName, userName, password);
				break;
			} catch (Exception e) {
				System.out.println("Error Occurred.Try again."+e.getMessage());
			}
		}
		
		while(true) {
			System.out.println("1. Initialise data");
			System.out.println("2. Go to home");
			System.out.print("Enter Choice : ");
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				init();
			case 2: 
				homePage(input);
				break;
			default:
				System.out.println("Enter correct choice");
			}
		}
		
		
	}

	 public static char[] hideConsole(String field) {
		 Console console = System.console();

		char[] password = console.readPassword(field);
		return password;
	 }
	
	public static void homePage(Scanner input) {
		while (true) {
			System.out.println("Welcome to WolfHospital Management System");
			System.out.println("Please select your role");
			System.out.println("1. Registration Staff");
			System.out.println("2. Doctor");
			System.out.println("3. Patient");
			System.out.println("4. Exit");
			System.out.print("Enter Choice : ");
			int choice = input.nextInt();
			int id = 0;
			if(choice!= 4) {
				System.out.print("Enter your id : ");
				id = input.nextInt();
			}
			switch (choice) {
			case 1:
				User.validateUser(id, Constants.registrationStaffRole);
				if(User.name == null) {
					System.out.println("Invalid User..... Try Again!!!");
					break;
				}
				RegistrationStaff.menu(input);
				break;
			case 2:
				User.validateUser(id, Constants.doctorRole);
				if(User.name == null) {
					System.out.println("Invalid User..... Try Again!!!");
					break;
				}
				Doctor.menu(input);
				break;
			case 3:
				User.validateUser(id, Constants.patientRole);
				if(User.name == null) {
					System.out.println("Invalid User..... Try Again!!!");
					break;
				}
				Patient.menu(input);
				break;
			case 4:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
				break;
			default:
				System.out.println("Enter correct choice");
			}
		}
	}
	private static void dropTables() throws SQLException{
		try {
			Connector.setAutoCommit(false);
			Connector.createPreparedStatement(Constants.dropTestTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropMedicineTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropBillingRecordTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropMedicalRecordTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropCheckInTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropBedTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropWardTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropPatientTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.dropStaffTable);
			Connector.executeUpdatePreparedQuery();
			Connector.commit();
		}
		catch(SQLException e) {
			Connector.rollback();
			System.out.println("Error occured, try again"+e.getMessage());
			//e.printStackTrace(System.out);
		}finally {
			Connector.setAutoCommit(true);
		}
	}
	
	private static void createTables() throws SQLException {
		try {
			//Create Tables
			Connector.setAutoCommit(false);
			Connector.createPreparedStatement(Constants.createStaffTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createPatientTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createWardTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createBedTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createCheckInTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createMedicalRecordTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createBillingRecordTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createMedicineTable);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.createTestTable);
			Connector.executeUpdatePreparedQuery();
			Connector.commit();
		}
		catch(SQLException e) {
			Connector.rollback();
			System.out.println("Error occured, try again"+e.getMessage());
			//e.printStackTrace(System.out);
		}
		finally {
			Connector.setAutoCommit(true);
		}
	}
	
	private static void insertDemoData() throws SQLException
	{
		try {
			Connector.setAutoCommit(false);
			//Dummy Data Init
			Connector.createPreparedStatement(Constants.insertStaffs);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.insertPatients);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.insertMedicalRecords);
			Connector.executeUpdatePreparedQuery();

			Connector.createPreparedStatement(Constants.insertBillingRecords);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.insertWards);
			Connector.executeUpdatePreparedQuery();
			Connector.createPreparedStatement(Constants.insertBeds);
			Connector.executeUpdatePreparedQuery();

			Connector.createPreparedStatement(Constants.insertCheckIns);
			Connector.executeUpdatePreparedQuery();
			Connector.commit();
		}
		catch(SQLException e) {
			Connector.rollback();
			System.out.println("Error occured, try again"+e.getMessage());
			//e.printStackTrace(System.out);
		}
		finally {
			Connector.setAutoCommit(true);
		}
	}
	private static void init(){
			try {
				dropTables();
				createTables();
				insertDemoData();
			} catch (SQLException e) {
				System.out.println("Error occured, try again"+e.getMessage());
				//e.printStackTrace(System.out);
			}		
	}
	/*
	public  static void  selectMedicalRecord(){
		System.out.println("Medical Record Table");
		try {
			Connector.createPreparedStatement(Constants.selectMedicalRecord);

			ResultSet rs = Connector.executePreparedQuery();

			while(rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print(rs.getString(2));
				System.out.print(rs.getDate(3));
				System.out.print( rs.getString(4));
				System.out.print( rs.getString(5));
				System.out.print( rs.getString(6));
				System.out.println(rs.getString(7));
				System.out.println( rs.getString(8));
				System.out.println( rs.getString(9));

			}


		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());

		}
	}
	public  static void selectPatient(){
		System.out.println("Patient Table");
		try {
			Connector.createPreparedStatement(Constants.selectPatient);

			ResultSet rs = Connector.executePreparedQuery();

			while(rs.next()) {
				System.out.print(rs.getString(1));
				System.out.print(rs.getString(2));
				System.out.print(rs.getDate(3));
				System.out.print( rs.getString(4));
				System.out.print( rs.getString(5));
				System.out.print( rs.getString(6));
				System.out.println(rs.getString(7));
				System.out.println( rs.getString(8));

			}


		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}*/
}
