import java.sql.Date;
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
			System.out.println("6. Update patient details");
			System.out.println("7. Delete a staff");
			System.out.println("8. Delete a ward");
			System.out.println("9. Delete a pateint");
			System.out.println("10. Check bed availability");
			System.out.println("11. Assign a bed to patient");
			System.out.println("12. Reserve a bed for patient");
			System.out.println("13. Release a bed");
			System.out.println("14. Create Billing Record");
			System.out.println("15. Sign Out");
			System.out.println("16. Exit");
			System.out.print("Enter Choice : ");
			
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				createNewStaff(input);
				break;
			case 2:
				try {
					createNewWard(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				createNewPatient(input);
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				deleteStaff(input);
				break;
			case 8:
				break;
			case 9:
				deletePatient(input);
				break;
			case 10:
				break;
			case 11:
				try {
					assignBed(input);
				} catch (SQLException e) {
					//todo
				}
				break;
			case 12:
				reserveBed(input);
				break;
			case 13:
				releaseBed(input);
				break;
			case 14:
				try {
					createBillingRecord(input);
				} catch (SQLException e) {
					//todo
				}
				break;
			case 15:
				User.name = null;
				Index.homePage(input);
				break;
			case 16:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}
	
	private static void reserveBed(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.reserveBed);
			int temp;
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();
			Connector.setPreparedStatementString(2, bedId.toUpperCase());
			
			if(Connector.executeUpdatePreparedQuery() == 1)
				System.out.println("Bed reserved Successfully");
	        else {
	        	System.out.println("Error occured while reserving bed, try again");
	        }
	  
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}	
	}
	
	private static void createBillingRecord(Scanner input) throws SQLException {
		try {
			Connector.setAutoCommit(false);
			System.out.println("Enter Patient ID:");
			int patientId = input.nextInt();
			
			Connector.createPreparedStatementWithKeys(Constants.createMedicalRecord);
			Connector.setPreparedStatementInt(1, Integer.parseInt(User.id));
			Connector.setPreparedStatementInt(2, patientId);
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.getGeneratedKeys();
			int medId = 0;
			if(rs.next())
				medId = rs.getInt(1);
			else
				throw new SQLException();
			Connector.createPreparedStatement(Constants.createBillingRecord);
			Connector.setPreparedStatementInt(1, Integer.parseInt(User.id));
			Connector.setPreparedStatementInt(2, patientId);
			Connector.setPreparedStatementInt(3, medId);
			System.out.println("Enter Payment Method:");
			String paymentMethod = input.next();
			Connector.setPreparedStatementString(4, paymentMethod);
			System.out.println("Do you want to enter card details(if applicable)? (Y/N):");
			String temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter card details:");
				temp = input.next();
				Connector.setPreparedStatementString(5, temp);
			}
			System.out.println("Enter Fees:");
			float fees = input.nextFloat();
			Connector.setPreparedStatementFloat(6, fees);
			System.out.println("Do you want to enter PayeeSSN(optional)? (Y/N):");
			temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter PayeeSSN:");
				temp = input.next();
				Connector.setPreparedStatementString(7, temp);
			}
			System.out.println("Do you want to enter Billing Address(if applicable)? (Y/N):");
			temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter Billing Address:");
				temp = input.next();
				Connector.setPreparedStatementString(8, temp);
			}			
			Connector.executeUpdatePreparedQuery();
			Connector.commit();
			System.out.println("Billing record created successfully");
		}
		catch(SQLException e) {
			Connector.rollback();
			System.out.println("Error occured while creating entries" + e.getMessage());
		}
		Connector.setAutoCommit(true);	
	}
	
	
	
	private static void assignBed(Scanner input) throws SQLException{
		try {
			Connector.setAutoCommit(false);
			Connector.createPreparedStatement(Constants.reserveBed);
			int wardId;
			System.out.println("Enter Ward ID:");
			wardId = input.nextInt();
			Connector.setPreparedStatementInt(1, wardId);
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();
			Connector.setPreparedStatementString(2, bedId.toUpperCase());
			System.out.println("Enter Patient ID:");
			int patientId;
			patientId = input.nextInt();
			Date startDate = null;
			Date endDate = null;
			String temp = null;
			try {
				System.out.println("Enter Start Date(yyyy-mm-dd):");
				temp = input.next();
				startDate = Date.valueOf(temp);
				System.out.println("Enter End Date(yyyy-mm-dd):");
				temp = input.next();
				endDate = Date.valueOf(temp);
			} catch (Exception e) {
				System.out.println("Invalid date, Try agian"+e.getMessage());
				return ;
			}
			
			if(Connector.executeUpdatePreparedQuery() == 1) {
				Connector.createPreparedStatement(Constants.assignBed);
				Connector.setPreparedStatementInt(1, Integer.parseInt(User.id));
				Connector.setPreparedStatementInt(2, patientId);
				Connector.setPreparedStatementDate(3, startDate);
				Connector.setPreparedStatementInt(4, wardId);
				Connector.setPreparedStatementString(5, bedId);
				Connector.setPreparedStatementDate(6, endDate);
				Connector.executeUpdatePreparedQuery();
				Connector.commit();
			}				
	        else {
	        	throw new SQLException();
	        }	
			System.out.println("Bed assigned Successfully");
			
		} catch(SQLException e) {
			Connector.rollback();
			System.out.println("Error occured while processing the data" + e.getMessage());
		}
		Connector.setAutoCommit(true);
		
	}
	
	
	
	
	private static void releaseBed(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.releaseBed);
			int temp;
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();
			Connector.setPreparedStatementString(2, bedId.toUpperCase());
			
			if(Connector.executeUpdatePreparedQuery() == 1)
				System.out.println("Bed released Successfully");
	        else {
	        	System.out.println("Error occured while releasing bed, try again");
	        }
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}	
	}
	
	private static void deleteStaff(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.deleteStaff);
			int temp;
			System.out.println("Enter Staff ID:");
			temp = input.nextInt();
			if(temp == Integer.parseInt(User.id)) {
				System.out.println("you cannot delete your own account, try again");
				RegistrationStaff.menu(input);
			}
			Connector.setPreparedStatementInt(1, temp);
			
			if(Connector.executeUpdatePreparedQuery() == 1)
				System.out.println("Staff deleted Successfully");
	        else {
	        	System.out.println("Error deleting staff, try again");
	        }
	  
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}	
	}
	
	private static void deletePatient(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.deletePatient);
			int temp;
			System.out.println("Enter Patient ID:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			
			if(Connector.executeUpdatePreparedQuery() == 1)
				System.out.println("Patient deleted Successfully");
	        else {
	        	System.out.println("Error deleting patient, try again");
	        }
	  
		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}	
	}
	
	
	
	private static void createNewStaff(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.createStaff);
			String temp = null;
			System.out.println("Enter Staff Name:");
			temp = input.next();
			Connector.setPreparedStatementString(1, temp);
			System.out.println("Enter Staff Address:");
			temp = input.next();
			Connector.setPreparedStatementString(2, temp);
			System.out.println("Enter Staff DOB(yyyy-mm-dd):");
			temp = input.next();
			
			try {
				Date dob = Date.valueOf(temp);
				Connector.setPreparedStatementDate(3, dob);
			} catch (Exception e) {
				System.out.println("Invalid data, Try agian"+e.getMessage());
				return ;
			}
			
			System.out.println("Enter Staff Professional Title:");
			temp = input.next();
			Connector.setPreparedStatementString(4, temp);
			System.out.println("Enter Staff Phone Number:");
			temp = input.next();
			Connector.setPreparedStatementString(5, temp);
			System.out.println("Enter Staff Gender(M/F/NB/U):");
			temp = input.next();
			Connector.setPreparedStatementString(6, temp);
			System.out.println("Enter Staff Job Title(RegistrationStaff/Doctor/Nurse):");
			temp = input.next();
			Connector.setPreparedStatementString(7, temp);
			System.out.println("Enter Staff Department:");
			temp = input.next();
			Connector.setPreparedStatementString(8, temp);
			Connector.executeUpdatePreparedQuery();
			System.out.println("Staff Registered Successfully");
		}
		catch(SQLException e) {
			System.out.println("Error occured while processing the data"+e.getMessage());
		}	
	}
	
	private static void createNewPatient(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.createPatient);
			String temp = null;
			System.out.println("Enter Patient Name:");
			temp = input.next();
			Connector.setPreparedStatementString(1, temp);
			System.out.println("Enter Patient DOB(yyyy-mm-dd):");
			temp = input.next();
			try {
				Date dob = Date.valueOf(temp);
				Connector.setPreparedStatementDate(2, dob);
			} catch (Exception e) {
				System.out.println("Invalid data, Try agian"+e.getMessage());
				return ;
			}
			System.out.println("Enter Patient Address:");
			temp = input.next();
			Connector.setPreparedStatementString(3, temp);
			System.out.println("Enter Patient Gender(M/F/NB/U):");
			temp = input.next();
			Connector.setPreparedStatementString(4, temp);
			System.out.println("Do you want to enter SSN(optional)? (Y/N):");
			temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter SSN:");
				temp = input.next();
				Connector.setPreparedStatementString(5, temp);
			}	
			Connector.setPreparedStatementString(6, "Ongoing");
			Connector.setPreparedStatementInt(7, Integer.parseInt(User.id));
			Connector.executeUpdatePreparedQuery();
			System.out.println("Patient Registered Successfully");
		}
		catch(SQLException e) {
			System.out.println("Error occured while processing the data "+e.getMessage());
		}			
	}
	
	
	private static void createNewWard(Scanner input) throws SQLException {
		try {
			Connector.setAutoCommit(false);
		    Connector.createPreparedStatement(Constants.createWard);
		    int capacity = 0 , charges = 0;
		    System.out.println("Enter Ward Capacity:");
		    capacity = input.nextInt();
			Connector.setPreparedStatementInt(1, capacity);
			System.out.println("Enter Ward Charges:");
			charges = input.nextInt();
			Connector.setPreparedStatementInt(2, charges);
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.getGeneratedKeys();
			int wardNo = 0;
			if(rs.next())
				wardNo = rs.getInt(1);
			else
				throw new SQLException();
			char alphabet = 'A';
			for(int i = 0; i < capacity; i++) {
				Connector.createPreparedStatement(Constants.createBed);
				Connector.setPreparedStatementString(1, ""+alphabet);
				Connector.setPreparedStatementInt(2, wardNo);
				Connector.setPreparedStatementString(3, "Y");
				Connector.executeUpdatePreparedQuery();
				alphabet++;
			}
			Connector.commit();
			System.out.println("Ward added Successfully");
		} catch (SQLException e) {
			Connector.rollback();
			System.out.println("Error occured while processing the data" + e.getMessage());
		}
		Connector.setAutoCommit(true);
		
	}

}
