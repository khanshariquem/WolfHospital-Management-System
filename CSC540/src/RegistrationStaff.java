import java.sql.Connection;
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
			System.out.println("15. Get ward Usage percentage");
			System.out.println("16. Get current patients for doctor ");
			System.out.println("17. Get all staff information grouped by role");
			System.out.println("18. Get bed usage");
			System.out.println("19. Get patients count");
			System.out.println("20. Sign Out");
			System.out.println("21. Exit");
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
                try {
                    updateBRDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
				break;
			case 5:
                try {
                    updateWardDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
				break;
			case 6:
                try {
                    updatePatientDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
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
				checkBedAvailability(input);
				break;
			case 11:
				try {
					assignBed(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
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
					System.out.println(e.getMessage());
				}
				break;
			case 15:
				getWardUsagePercentage();
				break;
			case 16:
				getActivePatientForDoctor(input);
				break;
			case 17:
				getAllStaffs(input);
				break;
			case 18:
				getBedUsage();
				break;
			case 19:
				try {
					getPatientCount(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 20:
				User.name = null;
				Index.homePage(input);
				break;
			case 21:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}

	private static void updateStaffDetails(Scanner input) throws SQLException{
		try {
            System.out.println("Enter StaffID for update:");
			int staffId = input.nextInt();
            Connector.createStatement();
            String checkUser = "select * from Staff where StaffId = " + staffId;
            ResultSet rs =  Connector.executeQuery(checkUser);
            if(rs.next()) {
                Connector.setAutoCommit(false);
                System.out.println("Enter no of columns for updation:");
                int count = input.nextInt();
                for(int i = 0; i < count; i++) {
                    System.out.println("Select the column to update its value:\n 1.Name 2.Address 3.DOB 4.ProfTitle 5.Phone 6.Gender 7.JobTitle 8.Dept");
                    int choice = input.nextInt();
                    String colName = "";
                    System.out.println("Enter new value");
                    String newVal = input.next();
                    switch (choice) {
                        case 1:
                            colName = "Name";
                            break;
                        case 2:
                            colName = "Address";
                            break;
                        case 3:
                            colName = "DOB";
                            break;
                        case 4:
                            colName = "ProfTitle";
                            break;
                        case 5:
                            colName = "Phone";
                            break;
                        case 6:
                            colName = "Gender";
                            break;
                        case 7:
                            colName = "JobTitle";
                            break;
                        case 8:
                            colName = "Dept";
                    }
                    String query = "update Staff set " + colName + " = ? where StaffId = ?";
                    Connector.createPreparedStatement(query);
                    Connector.setPreparedStatementString(1, newVal);
                    Connector.setPreparedStatementInt(2, staffId);
                    Connector.executeUpdatePreparedQuery();
                }
                Connector.commit();
                System.out.println("Details updated succcessfully");
            } else {
                System.out.println("No such user exists");
            }
        }
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
        Connector.setAutoCommit(true);
    }

    private static void updatePatientDetails(Scanner input) throws SQLException{
        try {
            System.out.println("Enter PatientID for update:");
            int patientId = input.nextInt();
            Connector.createStatement();
            String checkUser = "select * from Patient where PatientId = " + patientId;
            ResultSet rs =  Connector.executeQuery(checkUser);
            if(rs.next()) {
                Connector.setAutoCommit(false);
                System.out.println("Enter no of columns for updation:");
                int count = input.nextInt();
                for(int i = 0; i < count; i++) {
                    System.out.println("Select the column to update its value:\n 1.Name 2.Address 3.DOB 4.Phone 5.Gender 6.SSN 7.StaffID 8.Processing Treatment Plan 9.Completing Treatment");
                    int choice = input.nextInt();
                    String colName = "";
                    System.out.println("Enter new value");
                    String newVal = input.next();
                    switch (choice) {
                        case 1:
                            colName = "Name";
                            break;
                        case 2:
                            colName = "Address";
                            break;
                        case 3:
                            colName = "DOB";
                            break;
                        case 4:
                            colName = "Phone";
                            break;
                        case 5:
                            colName = "Gender";
                            break;
                        case 6:
                            colName = "SSN";
                            break;
                        case 7:
                            colName = "StaffID";
                            break;
                        case 8:
                            colName = "Processing_Treatment_Plan";
                            break;
                        case 9:
                            colName = "Completing_Treatment";
                    }
                    String query = "update Patient set " + colName + " = ? where PatientId = ?";
                    Connector.createPreparedStatement(query);
                    Connector.setPreparedStatementString(1, newVal);
                    Connector.setPreparedStatementInt(2, patientId);
                    Connector.executeUpdatePreparedQuery();
                }
                Connector.commit();
                System.out.println("Details updated succcessfully");
            } else {
                System.out.println("No such patient exists");
            }
        }
        catch(SQLException e) {
            System.out.println("Error occured, try again." + e.getMessage());
        }
        Connector.setAutoCommit(true);
    }

    private static void updateWardDetails(Scanner input) throws SQLException{
        try {
            System.out.println("Enter WardNo for update:");
            int wardNo = input.nextInt();
            Connector.createStatement();
            String checkUser = "select * from Ward where WardNo = " + wardNo;
            ResultSet rs =  Connector.executeQuery(checkUser);
            if(rs.next()) {
                Connector.setAutoCommit(false);
                System.out.println("Enter no of columns for updation:");
                int count = input.nextInt();
                for(int i = 0; i < count; i++) {
                    System.out.println("Select the column to update its value:\n 1.Charges 2.Capacity 3.StaffID");
                    int choice = input.nextInt();
                    String colName = "";
                    System.out.println("Enter new value");
                    String newVal = input.next();
                    switch (choice) {
                        case 1:
                            colName = "Charges";
                            break;
                        case 2:
                            colName = "Capacity";
                            break;
                        case 3:
                            colName = "StaffID";
                    }
                    String query = "update Ward set " + colName + " = ? where WardNo = ?";
                    Connector.createPreparedStatement(query);
                    Connector.setPreparedStatementString(1, newVal);
                    Connector.setPreparedStatementInt(2, wardNo);
                    Connector.executeUpdatePreparedQuery();
                }
                Connector.commit();
                System.out.println("Details updated succcessfully");
            } else {
                System.out.println("No such ward exists");
            }
        }
        catch(SQLException e) {
            System.out.println("Error occured, try again." + e.getMessage());
        }
        Connector.setAutoCommit(true);
    }

    private static void updateBRDetails(Scanner input) throws SQLException{
        try {
            System.out.println("Enter BillingRecordID for update:");
            int billingRecordID = input.nextInt();
            Connector.createStatement();
            String checkUser = "select * from BillingRecord where BillingRecordID = " + billingRecordID;
            ResultSet rs =  Connector.executeQuery(checkUser);
            if(rs.next()) {
                Connector.setAutoCommit(false);
                System.out.println("Enter no of columns for updation:");
                int count = input.nextInt();
                for(int i = 0; i < count; i++) {
                    System.out.println("Select the column to update its value:\n 1.Visit Date 2.Payment Method 3.Card Number 4.Fees 5.PayeeSSN 6.BillingAddress 7.StaffID 8.PatientId 9.MedicalRecordId");
                    int choice = input.nextInt();
                    String colName = "";
                    System.out.println("Enter new value");
                    String newVal = input.next();
                    switch (choice) {
                        case 1:
                            colName = "VisitDate";
                            break;
                        case 2:
                            colName = "PaymentMethod";
                            break;
                        case 3:
                            colName = "CardNumber";
                            break;
                        case 4:
                            colName = "Fees";
                            break;
                        case 5:
                            colName = "PayeeSSN";
                            break;
                        case 6:
                            colName = "BillingAddress";
                            break;
                        case 7:
                            colName = "StaffID";
                            Connector.createStatement();
                            String queryStaff = "select * from Staff where StaffId = " + newVal;
                            ResultSet resultSetStaff =  Connector.executeQuery(queryStaff);
                            if(!resultSetStaff.next()) {
                                System.out.println("Invalid StaffId");
                                continue; //break or continue???
                            }
                            break;
                        case 8:
                            colName = "PatientId";
                            Connector.createStatement();
                            String queryPatient = "select * from Patient where PatientId = " + newVal;
                            ResultSet resultSetPatient =  Connector.executeQuery(queryPatient);
                            if(!resultSetPatient.next()) {
                                System.out.println("Invalid PatientId");
                                continue;
                            }
                            break;
                        case 9:
                            colName = "MedicalRecordId";
                            Connector.createStatement();
                            String queryMR = "select * from MedicalRecord where MedicalRecordId = " + newVal;
                            ResultSet resultSetMR =  Connector.executeQuery(queryMR);
                            if(!resultSetMR.next()) {
                                System.out.println("Invalid MedicalRecordId");
                                continue;
                            }
                    }
                    String query = "update BillingRecord set " + colName + " = ? where BillingRecordID = ?";
                    Connector.createPreparedStatement(query);
                    Connector.setPreparedStatementString(1, newVal);
                    Connector.setPreparedStatementInt(2, billingRecordID);
                    Connector.executeUpdatePreparedQuery();
                }
                Connector.commit();
                System.out.println("Details updated succcessfully");
            } else {
                System.out.println("No such ward exists");
            }
        }
        catch(SQLException e) {
            System.out.println("Error occured, try again." + e.getMessage());
        }
        Connector.setAutoCommit(true);
    }
	
	private static void getAllStaffs(Scanner input) {
		
		try {
			Connector.createStatement();
			ResultSet rs = Connector.executeQuery(Constants.getAllStaff);
			
			String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |     %-4s|      %-40s |      %-12s |      %-8s |      %-18s |          %-15s          |  %-18s                |%n";
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");
			System.out.format("| Staff ID  |  Name   |    DOB  |   Age   |   Address |    Phone   |    Gender  |       Professional Title    |   Job Title    |     Department       |%n");
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString(1),rs.getString(2),rs.getString(4),Util.getAge(rs.getDate(4)),rs.getString(3),rs.getString(6),Util.getGender(rs.getString(7)),rs.getString(5),rs.getString(8),rs.getString(9));

			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	
	private static void getActivePatientForDoctor(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.getActivePatientForDoctor);
			int temp;
			System.out.println("Enter Doctor ID:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			ResultSet rs = Connector.executePreparedQuery();
			String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |     %-4s|      %-40s |      %-12s |      %-8s |      %-18s |          %-5s          |  %-8s                |%n";
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");
			System.out.format("| Patient ID  |  Name   |    DOB  |   Age   |   Address |    Phone   |    Gender  |       SSN  |Processing Treatment Plan| Completing Treatment |%n");
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString(1),rs.getString(2),rs.getString(3),Util.getAge(rs.getDate(3)),rs.getString(4),rs.getString(5),Util.getGender(rs.getString(6)),rs.getString(7),rs.getString(8),rs.getString(9));

			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	private static void getBedUsage() {
		try {
			Connector.createStatement();
			ResultSet rs = Connector.executeQuery(Constants.getBedUsage);
			String leftAlignFormat = "|       %-10s |    %-10s |    %-10s |     %-10s|%n";
			System.out.format("+------------------+----------------+--------------+--------------+%n");
			System.out.format("| Ward No          |  Capacity      |    Available |  Occupied    |%n");
			System.out.format("+------------------+----------------+--------------+--------------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4));

			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	
	private static void getWardUsagePercentage() {
		try {
			Connector.createStatement();
			ResultSet rs = Connector.executeQuery(Constants.getWardUsagePercentage);
			if(rs.next()) {
				System.out.println("Current Wards Usage:" + (rs.getFloat(1)*100));
			}
			else {
				System.out.println("Error occured. Try again.");
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	
	private static void checkBedAvailability(Scanner input) {
		System.out.println("Check available beds based on following options:");
		System.out.println("1. Bed Type");
		System.out.println("2. Ward Number");
		System.out.print("Enter Choice : ");
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			checkBedAvailabilityBasedOnBedType(input);
			break;
		case 2:
			checkBedAvailabilityBasedWardNo(input);
			break;
		}
		
	}
	private static void checkBedAvailabilityBasedOnBedType(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.checkBedAvailabilityBasedOnBedType);
			int temp;
			System.out.println("Enter Ward type:1-Bed (1) /2-Bed (2) /3-Bed (3)");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			ResultSet rs = Connector.executePreparedQuery();
			if(rs.next()) {
				System.out.println("Number of Available Beds:" + rs.getInt(1));
			}
			else {
				System.out.println("Error occured. Try again.");
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	
	private static void checkBedAvailabilityBasedWardNo(Scanner input) {
		try {
			Connector.createPreparedStatement(Constants.checkBedAvailabilityBasedWardNo);
			int temp;
			System.out.println("Enter Ward Number:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(1, temp);
			ResultSet rs = Connector.executePreparedQuery();
			if(rs.next()) {
				System.out.println("Number of Available Beds:" + rs.getInt(1));
			}
			else {
				System.out.println("Error occured. Try again.");
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
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
				System.out.println("You cannot delete your own account, try again");
				RegistrationStaff.menu(input);
			}
            Connector.createStatement();
            String checkUser = "select * from Staff where StaffId = " + temp;
            ResultSet rs =  Connector.executeQuery(checkUser);
            if(rs.next()) {
                Connector.setPreparedStatementInt(1, temp);
                if(Connector.executeUpdatePreparedQuery() == 1)
                    System.out.println("Staff deleted Successfully");
                else {
                    System.out.println("Error deleting staff, try again");
                }
            } else {
                System.out.println("User doesn't exist");
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
			System.out.println("Do you want to Enter Staff Professional Title(optional)? (Y/N):");
			temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter Staff Professional Title:");
				temp = input.next();
			}
			else 
				temp = null;
			Connector.setPreparedStatementString(4, temp);
			System.out.println("Enter Staff Phone Number:");
			temp = input.next();
			Connector.setPreparedStatementString(5, temp);
			System.out.println("Enter Staff Gender(M/F/NB/U):");
			temp = input.next();
			Connector.setPreparedStatementString(6, temp);
			System.out.println("Enter Staff Job Title:");
			temp = input.next();
			Connector.setPreparedStatementString(7, temp);
			System.out.println("Enter Staff Department:");
			temp = input.next();
			Connector.setPreparedStatementString(8, temp);
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.getGeneratedKeys();
			int ID = 0;
			if(rs.next())
				ID = rs.getInt(1);
			else
				throw new SQLException();
			System.out.println("Staff Registered Successfully with ID: "+ID);
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
			System.out.println("Enter Patient Phone number:");
			temp = input.next();
			Connector.setPreparedStatementString(5, temp);
			System.out.println("Do you want to enter SSN(optional)? (Y/N):");
			temp = input.next();
			if(temp.equals("Y")) {
				System.out.println("Enter SSN:");
				temp = input.next();
			}
			else
				temp = null;
			Connector.setPreparedStatementString(6, temp);
			Connector.setPreparedStatementString(7, "Yes");
			Connector.setPreparedStatementInt(8, Integer.parseInt(User.id));
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.getGeneratedKeys();
			int ID = 0;
			if(rs.next())
				ID = rs.getInt(1);
			else
				throw new SQLException();
			System.out.println("Patient Registered Successfully with ID : "+ID);
		}
		catch(SQLException e) {
			System.out.println("Error occured while processing the data "+e.getMessage());
		}			
	}
	
	public static boolean validateNurse(int id) {
		try {
			Connector.createPreparedStatement(Constants.validatePatient);
			ResultSet result = Connector.executePreparedQuery();
			if(result.next()) {
				return true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
		return false;
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
			while(true) {
				System.out.println("Enter Responsible Nurse:");
				int staffID = input.nextInt();
				if(validateNurse(staffID)) {
					Connector.setPreparedStatementInt(3, staffID);
					break;
				}
				else
					System.out.println("Nurse does not exist. try again.");
			}
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
			System.out.println("Ward "+ wardNo +" added Successfully");
		} catch (SQLException e) {
			Connector.rollback();
			System.out.println("Error occured while processing the data" + e.getMessage());
		}
		Connector.setAutoCommit(true);
		
	}
	private static void check_in(Scanner input) throws SQLException {
		try {
			Connector.setAutoCommit(false);
			Connector.createPreparedStatement(Constants.createCheckIn);
			//int capacity = 0 , charges = 0;
			System.out.println("Enter Patient ID:");
			String id = input.next();
			if(new Validation("Patient","PatientID",id).validatePresence()){
				System.out.println("Enter Ward Number:");
				int wardNo = input.nextInt();
				System.out.println("Enter Bed ID:");
				String bedid = input.next();
				System.out.println("Enter State Date(yyyy-mm-dd):");
				String temp = input.next();
				try {
					Date sDate = Date.valueOf(temp);
					Connector.setPreparedStatementDate(3, sDate);
				} catch (Exception e) {
					System.out.println("Invalid data, Try agian"+e.getMessage());
					return ;
				}

				boolean bedAlloted=false;
				while(!bedAlloted){
					//check bed availability
					if(true){
						Connector.setPreparedStatementInt(1, Integer.valueOf(User.id));
						Connector.setPreparedStatementInt(2, Integer.valueOf(id));
						Connector.setPreparedStatementInt(4, wardNo);
						Connector.setPreparedStatementString(5, bedid.toUpperCase());
						Connector.executeUpdatePreparedQuery();
						bedAlloted=true;

					}
					else{
						bedAlloted=false;
					}
				}
				//Update Bed status to unavailable
				if(bedAlloted){

						Connector.createPreparedStatement(Constants.reserveBed);
						Connector.setPreparedStatementInt(1, wardNo);
						Connector.setPreparedStatementString(2, bedid.toUpperCase());
						Connector.executeUpdatePreparedQuery() ;

				}

				Connector.commit();
				System.out.println("Ward added Successfully");
			}
			else{
				System.out.println("Incorrect Patient Id, retry");
			}

		} catch (SQLException e) {
			Connector.rollback();
			System.out.println("Error occured while processing the data" + e.getMessage());
		}
		Connector.setAutoCommit(true);

	}
	public static void getMedicalRecordForPatient(Scanner input) throws SQLException{
		System.out.println("Start**");
		try {
			Connector.createPreparedStatement(Constants.getMedicalRecordForPatient);
			int temp;
			System.out.println("Enter Patient ID:");
			temp = input.nextInt();
			Connector.setPreparedStatementInt(3, temp);
			System.out.println("Enter Month:");
			String stMonth=input.next();
			Connector.setPreparedStatementString(1, stMonth);
			System.out.println("Enter Year:");
			stMonth=input.next();
			Connector.setPreparedStatementString(2, stMonth);
			ResultSet rs = Connector.executePreparedQuery();
			String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |     %-8s|      %-8s |      %-12s |      %-18s |      %-18s |          %-20s |  %-8s |%n";
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");
			System.out.format("| Medical Record ID|  Start Date   |    End Date   |  Staff ID   | Patient ID    |    Medicine Name  |          Test Name      |          Test Lab       |          Test Result          | Test Staff|%n");
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));

			}


		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}
	
	
	public static void getPatientCount(Scanner input) throws SQLException{
		try {
			Connector.createPreparedStatement(Constants.getPatientCount);
			System.out.println("Enter Year:");
			String stMonth=input.next();
			Connector.setPreparedStatementString(1, stMonth);
			System.out.println("Enter Month:");
			stMonth=input.next();
			Connector.setPreparedStatementString(2, stMonth);
			ResultSet rs = Connector.executePreparedQuery();
			String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |%n";
			System.out.format("+------------------+-------------+-------------+%n");
			System.out.format("| Year             |  Month      |    Count    |%n");
			System.out.format("+------------------+-------------+-------------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getInt(1),rs.getInt(2),rs.getInt(3));

			}


		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}
	
	
	public static void getMedicalRecordForPatientBetween(Scanner input) throws SQLException{
		System.out.println("Start**");
		try {
			Connector.createPreparedStatement(Constants.getMedicalRecordForPatientBetween);
			int temp;
			System.out.println("Enter Patient ID:");
			temp = input.nextInt();
			System.out.println("Enter Start Month:");
			String stMonth=input.next();
			Connector.setPreparedStatementString(1, stMonth);
			System.out.println("Enter End Month:");
			 stMonth=input.next();
			Connector.setPreparedStatementString(2, stMonth);
			System.out.println("Enter Search Year:");
			 stMonth=input.next();
			Connector.setPreparedStatementString(3, stMonth);
			Connector.setPreparedStatementInt(4, temp);
			ResultSet rs = Connector.executePreparedQuery();
			String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |     %-8s|      %-8s |      %-12s |      %-18s |      %-18s |          %-20s |  %-8s |%n";
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");
			System.out.format("| Medical Record ID|  Start Date   |    End Date   |  Staff ID   | Patient ID    |    Medicine Name  |          Test Name      |          Test Lab       |          Test Result          | Test Staff|%n");
			System.out.format("+------------------+---------------+---------------+-------------+---------------+-------------------+-------------------------+-------------------------+-------------------------------+-----------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));

			}

		} catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}
}
