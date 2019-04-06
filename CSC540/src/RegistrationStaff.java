import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistrationStaff {
		
	public static void menu(Scanner input) {
		while (true) {
			System.out.println("Hi "+User.name+" , Welcome to WolfHospital Management System");
			System.out.println("Menu:");
			System.out.println("1. Add new staff");
			System.out.println("2. Add new Ward");
			System.out.println("3. Add new patient");
			System.out.println("4. Update staff details");
			System.out.println("5. Update ward details");
			System.out.println("6. Update pateint details");
			System.out.println("7. Delete a staff");
			System.out.println("8. Delete a ward");
			System.out.println("9. Delete a pateint");
			System.out.println("10. Check bed availability");
			System.out.println("11. Assign a bed to patient");
			System.out.println("12. Reserve a bed for patient");
			System.out.println("13. Release a bed");
			System.out.println("14. Sign Out");
			System.out.println("15. Exit");
			System.out.print("Enter Choice : ");
			
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				craeteNewStaff(input);
				break;
			case 2:
				try {
					craeteNewWard(input);
				} catch (SQLException e) {
					//todo
				}
				break;
			case 3:
				craeteNewPatient(input);
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			case 13:
				break;
			case 14:
				User.name = null;
				Index.homePage(input);
				break;
			case 15:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}
	
	
	private static void craeteNewStaff(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.createStaff);
			String temp = null;
			System.out.println("Enter Staff Name:");
			temp = input.next();
			Connector.setPreparedStatement(1, temp);
			System.out.println("Enter Staff Address:");
			temp = input.next();
			Connector.setPreparedStatement(2, temp);
			System.out.println("Enter Staff DOB(yyyy-mm-dd):");
			temp = input.next();
			Connector.setPreparedStatement(3, temp);
			System.out.println("Enter Staff Professional Title:");
			temp = input.next();
			Connector.setPreparedStatement(4, temp);
			System.out.println("Enter Staff Phone Number:");
			temp = input.next();
			Connector.setPreparedStatement(5, temp);
			System.out.println("Enter Staff Gender(M/F/NB/U):");
			temp = input.next();
			Connector.setPreparedStatement(6, temp);
			System.out.println("Enter Staff Job Title(RegistrationStaff/Doctor/Nurse):");
			temp = input.next();
			Connector.setPreparedStatement(7, temp);
			System.out.println("Enter Staff Department:");
			temp = input.next();
			Connector.setPreparedStatement(8, temp);
			Connector.executeUpdatePreparedQuery();
			System.out.println("Staff Registered Successfully");
		}
		catch(SQLException e) {
			//todo
		}	
	}
	
	private static void craeteNewPatient(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.createPatient);
			String temp = null;
			System.out.println("Enter Patient Name:");
			temp = input.next();
			Connector.setPreparedStatement(1, temp);
			System.out.println("Enter Patient DOB(yyyy-mm-dd):");
			temp = input.next();
			Connector.setPreparedStatement(2, temp);
			System.out.println("Enter Patient Address:");
			temp = input.next();
			Connector.setPreparedStatement(3, temp);
			System.out.println("Enter Patient Gender(M/F/NB/U):");
			temp = input.next();
			Connector.setPreparedStatement(4, temp);
			System.out.println("Enter Patient SSN(optional):");
			temp = input.next();
			Connector.setPreparedStatement(5, temp);
			Connector.setPreparedStatement(6, "Ongoing");
			Connector.setPreparedStatement(7, User.id );
			Connector.executeUpdatePreparedQuery();
			System.out.println("Patient Registered Successfully");
		}
		catch(SQLException e) {
			//todo
		}			
	}
	
	
	private static void craeteNewWard(Scanner input) throws SQLException {
		try {
			Connector.con.setAutoCommit(false);
		    Connector.createPreparedStatement(Constants.createWard);
		    String temp = null;
		    System.out.println("Enter Ward Capacity:");
		    temp = input.next();
			Connector.setPreparedStatement(1, temp);
			System.out.println("Enter Ward Charges:");
		    temp = input.next();
			Connector.setPreparedStatement(2, temp);
			ResultSet result = Connector.executePreparedQuery();
			char alphabet = 'A';
			for(int i = 0; i < Integer.parseInt(result.getString("Capacity")); i++ ) {
				Connector.createPreparedStatement(Constants.createBed);
				Connector.setPreparedStatement(1, ""+alphabet);
				Connector.setPreparedStatement(2, result.getString("WardNo"));
				Connector.setPreparedStatement(3, "Y");
				Connector.executeUpdatePreparedQuery();
				alphabet++;
			}
			Connector.con.commit();
		} catch (SQLException e) {
			// TODO: handle exception
			Connector.con.rollback();
		}	
		Connector.con.setAutoCommit(true);
	}

}
