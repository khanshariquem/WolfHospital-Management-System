import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class for role - Registraion staff
 *
 */
public class RegistrationStaff {
		
	/**
	 * Method to display the menu for Registration Staff
	 * @param input
	 */
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
            System.out.println("7. Update Checkin details/Checkout");
            System.out.println("8. Update Billing Record");
			System.out.println("9. Delete a staff");
			System.out.println("10. Delete a ward");
			System.out.println("11. Delete a patient");
			System.out.println("12. Check bed availability");
			System.out.println("13. Assign a bed to patient");
			System.out.println("14. Reserve a bed for patient");
			System.out.println("15. Release a bed");
			System.out.println("16. Create Billing Record");
			System.out.println("17. Get ward Usage percentage");
			System.out.println("18. Get current patients for doctor ");
			System.out.println("19. Get all staff information grouped by role");
            System.out.println("20. Get Patient Medical Record by MM/YYY");
            System.out.println("21. Get Patient Medical Record between start and end period");
            System.out.println("22. Create Check-In");
            System.out.println("23. Update Medical Record");
            System.out.println("24. Get bed usage");
			System.out.println("25. Get patients count");
			System.out.println("26. Sign Out");
			System.out.println("27. Exit");

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
                    updateStaffDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
				break;
			case 5:
                try {
                    updateWardDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidID ex) {
                    System.out.println(ex.getMessage());
                } catch (InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
				break;
			case 6:
                try {
                    updatePatientDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidID ex) {
                    System.out.println(ex.getMessage());
                } catch (InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
				break;
            case 7:
                try {
                    updateCheckInDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidID ex) {
                    System.out.println(ex.getMessage());
                } catch (InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 8:
                try {
                    updateBRDetails(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidID ex) {
                    System.out.println(ex.getMessage());
                } catch (InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
                break;
			case 9:
				deleteStaff(input);
				break;
			case 10:
				deleteWard(input);
				break;
			case 11:
				deletePatient(input);
				break;
			case 12:
				checkBedAvailability(input);
				break;
			case 13:
				try {
					assignBed(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 14:
				reserveBed(input);
				break;
			case 15:
				releaseBed(input);
				break;
			case 16:
				try {
					createBillingRecord(input);
				} catch (SQLException e) {
					System.out.println("Error occured while checking the bed availability, try again");
				}
				break;
			case 17:
				getWardUsagePercentage();
				break;
			case 18:
				getActivePatientForDoctor(input);
				break;
			case 19:
				getAllStaffs(input);
				break;
			case 20:
                try {
                    getMedicalRecordForPatient(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 21:
                try {
                    getMedicalRecordForPatientBetween(input);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 22:
                try {
                    check_in(input);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 23:
                    Doctor.updateMedicalRecord(input);
                break;
            case 24:
				getBedUsage();
				break;
			case 25:
				try {
					getPatientCount(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 26:
				User.name = null;
				Index.homePage(input);
				break;
			case 27:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}

	
	/**
	 * Allows the registration staff to update staff details such as Name, Address, Date of Birth etc.
	 * @param input
	 * @throws SQLException
	 * @throws InvalidChoice
	 */
	private static void updateStaffDetails(Scanner input) throws SQLException, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Staff Id for updation:");
        String id = input.next(); // Takes the staff id on which updation is to be performed
        Validation val = new Validation("Staff", "StaffId", id);
        if (val.validatePresence()) {  // Validates if the staff exists
            try {
                Connector.setAutoCommit(false); //Auto commit disabled to implement update of all fields in one transaction
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Name  2.Address  3.DOB  4.ProfTitle  5.Phone  6.Gender  7.JobTitle  8.Dept");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            System.out.println("Enter Name");
                            update.put("Name", input.nextLine());
                            break;
                        case "2":
                            System.out.println("Enter Address");
                            update.put("Address", input.nextLine());
                            break;
                        case "3":
                            System.out.println("Enter DOB (YYYY-MM-DD)");
                            update.put("DOB", input.nextLine());
                            break;
                        case "4":
                            System.out.println("Enter ProfTitle");
                            update.put("ProfTitle", input.nextLine());
                            break;
                        case "5":
                            System.out.println("Enter Phone");
                            update.put("Phone", input.nextLine());
                            break;
                        case "6":
                            System.out.println("Enter Gender (M/F)");
                            update.put("Gender", input.nextLine().toUpperCase());
                            break;
                        case "7":
                            System.out.println("Enter JobTitle");
                            update.put("JobTitle", input.nextLine());
                            break;
                        case "8":
                            System.out.println("Enter Dept");
                            update.put("Dept", input.nextLine());
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");   // Throws a custom exception if user chooses invalid menu option
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) { // All update key, values are concatenated
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateStaffDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();     // Executes update of all user selected fields
                System.out.println("Details updated Successfully");
                Connector.commit(); // Commits the transaction
            } catch (SQLException e) {
                Connector.rollback(); //In case of error, the transaction is rollbacked
                System.out.println("Error occured while updating Staff details" + e.getMessage());
                //e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such user exists");
        }
        Connector.setAutoCommit(true); // Auto commit enabled post transaction
    }

    
    /**
     * Allows the registration staff to update patient details such as Name, Address, Date of Birth, SSN etc.
     * @param input
     * @throws SQLException
     * @throws InvalidID
     * @throws InvalidChoice
     */
    private static void updatePatientDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Patient Id for updation:");
        String id = input.next(); // Takes the patient id on which updation is to be performed
        Validation val = new Validation("Patient", "PatientId", id);
        if (val.validatePresence()) { // Validates if the patient exists
            try {
                Connector.setAutoCommit(false); //Auto commit disabled to implement update of all fields in one transaction
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Name  2.Address  3.DOB  4.Phone  5.Gender  6.SSN   7.Processing Treatment Plan  8.Completing Treatment");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            System.out.println("Enter Name");
                            update.put("Name", input.nextLine());
                            break;
                        case "2":
                            System.out.println("Enter Address");
                            update.put("Address", input.nextLine());
                            break;
                        case "3":
                            System.out.println("Enter DOB (YYYY-MM-DD)");
                            update.put("DOB", input.nextLine());
                            break;
                        case "4":
                            System.out.println("Enter Phone");
                            update.put("Phone", input.nextLine());
                            break;
                        case "5":
                            System.out.println("Enter Gender (M/F)");
                            update.put("Gender", input.nextLine().toUpperCase());
                            break;
                        case "6":
                            System.out.println("Enter SSN");
                            update.put("SSN", input.nextLine());
                            break;
                        case "7":
                            System.out.println("Enter Processing_Treatment_Plan");
                            update.put("Processing_Treatment_Plan", input.nextLine());
                            break;
                        case "8":
                            System.out.println("Enter Completing_Treatment (Yes/No)");
                            update.put("Completing_Treatment", input.nextLine());
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");   // Throws a custom exception if user chooses invalid menu option
                    }
                }
                update.put("StaffID", User.id);
                String updateQuery = "";
                for (String key : update.keySet()) { // All update key, values are concatenated
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updatePatientDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();  // Executes update of all user selected fields
                System.out.println("Details updated Successfully");
                Connector.commit();  // Commits the transaction
            } catch (SQLException e) {
                Connector.rollback();  //In case of error, the transaction is rollbacked
                System.out.println("Error occured while updating Patient details" + e.getMessage());
                //e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such patient exists");
        }
        Connector.setAutoCommit(true);  // Auto commit enabled post transaction
    }

    
    /**
     * Allows the registration staff to update ward details such as charges, capacity, responsible staff etc.
     * @param input
     * @throws SQLException
     * @throws InvalidID
     * @throws InvalidChoice
     */
    private static void updateWardDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Ward No for updation:");
        String id = input.next();   // Takes the ward no on which updation is to be performed
        Validation val = new Validation("Ward", "WardNo", id);
        if (val.validatePresence()) {  // Validates if the ward exists
            try {
                Connector.setAutoCommit(false);  //Auto commit disabled to implement update of all fields in one transaction
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Charges  2.Capacity  3.StaffID");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            System.out.println("Enter Charges");
                            update.put("Charges", input.nextLine());
                            break;
                        case "2":
                            System.out.println("Enter Capacity");
                            update.put("Capacity", input.nextLine());
                            break;
                        case "3":
                            System.out.println("Enter StaffID"); // Validating if StaffID exists in Staff table; following referrential integrity constraint
                            String staffId = input.nextLine();
                            Validation staffVal = new Validation("Staff", "StaffID", staffId);
                            if(staffVal.validatePresence()) {
                                update.put("StaffID", staffId);
                            } else {
                                throw new InvalidID("Invalid StaffId, transaction aborted");  //Throws a custom excepttion if StaffId is invalid, and aborts the transaction
                            }
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");  // Throws a custom exception if user chooses invalid menu option
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) {  // All update key, values are concatenated
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateWardDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();   // Executes update of all user selected fields
                System.out.println("Details updated Successfully");
                Connector.commit();   // Commits the transaction
            } catch (SQLException e) {
                Connector.rollback();   //In case of error, the transaction is rollbacked
                System.out.println("Error occured while updating Ward details" + e.getMessage());
                //e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such ward exists");
        }
        Connector.setAutoCommit(true);    // Auto commit enabled post transaction
    }

    
    /**
     * // Allows the registration staff to update ward details of an admitted patient and checkout a patient from the hospital
     * @param input
     * @throws SQLException
     * @throws InvalidID
     * @throws InvalidChoice
     */
    private static void updateCheckInDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Patient Id for updation:");
        String id = input.next();  // Takes the patient id for which updation is to be performed
        String selectQuery = "select * from CheckIn where PatientId = ? and EndDate is NULL"; // Validates if the patient has not already been checked out using EndDate
        Connector.createPreparedStatement(selectQuery);
        Connector.setPreparedStatementString(1, id);
        ResultSet resultSet = Connector.executePreparedQuery();
        if(resultSet.next()) {
            try {
                Connector.setAutoCommit(false);   //Auto commit disabled to implement update of all fields in one transaction
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.WardNo & BedID  2.End Date");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            String oldbedDataQuery = "select * from CheckIn where PatientId = ? and EndDate is NULL";
                            Connector.createPreparedStatement(oldbedDataQuery);
                            Connector.setPreparedStatementString(1, id);
                            ResultSet oldrs = Connector.executePreparedQuery();
                            int oldWardNo = 0;
                            String oldBedId = "";
                            if(oldrs.next()) {  // Stores the existing Bed Id and Ward No of the patient to release the bed in DB
                                oldWardNo = oldrs.getInt("WardNo");
                                oldBedId = oldrs.getString("BedId");
                            }
                            System.out.println("Enter WardNo");
                            String wardNo = input.nextLine();
                            System.out.println("Enter BedID");
                            String bedId = input.nextLine();
                            String bedAvailQuery = "select * from Bed where WardNo = ? and BedId = ?";
                            Connector.createPreparedStatement(bedAvailQuery);
                            Connector.setPreparedStatementString(1, wardNo);
                            Connector.setPreparedStatementString(2, bedId);
                            Connector.executeUpdatePreparedQuery();
                            ResultSet rs = Connector.executePreparedQuery();
                            if(rs.next()) {
                                if(rs.getString("Status").charAt(0) == 'Y') { // If the requested bed & ward combination exists at the hospital and is available, then it is alloted
                                    update.put("WardNo", wardNo);
                                    update.put("BedID", bedId);

                                    //Releasing existing bed occupied by the patient
                                    Connector.createPreparedStatement(Constants.releaseBed);
                                    Connector.setPreparedStatementInt(1, oldWardNo);
                                    Connector.setPreparedStatementString(2, oldBedId);
                                    Connector.executeUpdatePreparedQuery();

                                    //Reserving new bed and marking it unavailable in DB
                                    Connector.createPreparedStatement(Constants.reserveBed);
                                    Connector.setPreparedStatementInt(1, Integer.parseInt(wardNo));
                                    Connector.setPreparedStatementString(2, bedId.toUpperCase());
                                    Connector.executeUpdatePreparedQuery();

                                } else {
                                    throw new InvalidID("Bed unavailable, transaction aborted"); // Aborts the transaction if the bed is unavailable
                                }
                            } else {
                                throw new InvalidID("No such ward & bed exists, transaction aborted"); // Aborts the transaction if the ward & bed doesn't exist
                            }
                            break;
                        case "2":
                            System.out.println("Enter End Date (YYYY-MM-DD)");
                            update.put("EndDate", input.nextLine());
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");   // Throws a custom exception if user chooses invalid menu option
                    }
                }
                update.put("StaffID", User.id);
                String updateQuery = "";
                for (String key : update.keySet()) {  // All update key, values are concatenated
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateCheckInDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();    // Executes update of all user selected fields
                System.out.println("Details updated Successfully");
                Connector.commit();   // Commits the transaction
            } catch (SQLException e) {
                Connector.rollback();   //In case of error, the transaction is rollbacked
                System.out.println("Error occured while updating details" + e.getMessage());
                //e.printStackTrace(System.out);
            }
        } else {
            System.out.println("Invalid choice");
        }
        Connector.setAutoCommit(true);    // Auto commit enabled post transaction
    }

    
    /**
     * // Allows the registration staff to update Billing details of an admitted patient such as Visit Date, Payment Method, Card Number etc.
     * @param input
     * @throws SQLException
     * @throws InvalidID
     * @throws InvalidChoice
     */
    private static void updateBRDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Billing Record Id for updation:");
        String id = input.next();   // Takes the billing record id for which updation is to be performed
        Validation val = new Validation("BillingRecord", "BillingRecordID", id);
        if (val.validatePresence()) {   // Validates if the billing record id exists
            try {
                Connector.setAutoCommit(false);   //Auto commit disabled to implement update of all fields in one transaction
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Visit Date  2.Payment Method  3.Card Number  4.Fees  5.PayeeSSN  6.BillingAddress  7.PatientId  8.MedicalRecordId");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            System.out.println("Enter Visit Date (YYYY-MM-DD)");
                            update.put("VisitDate", input.nextLine());
                            break;
                        case "2": // If the user enters card as the payment method, then he is also prompted to enter the card number
                            System.out.println("Enter Payment Method");
                            String paymentMethod = input.nextLine();
                            if(paymentMethod.toLowerCase().equals("card")) {
                                System.out.println("Enter Card Number");
                                update.put("CardNumber", input.nextLine());
                            }
                            update.put("PaymentMethod", paymentMethod);
                            break;
                        case "3":
                            System.out.println("Enter Card Number");
                            update.put("CardNumber", input.nextLine());
                            break;
                        case "4":
                            System.out.println("Enter Fees");
                            update.put("Fees", input.nextLine());
                            break;
                        case "5":
                            System.out.println("Enter PayeeSSN");
                            update.put("PayeeSSN", input.nextLine());
                            break;
                        case "6":
                            System.out.println("Enter BillingAddress");
                            update.put("BillingAddress", input.nextLine());
                            break;
                        case "7":
                            System.out.println("Enter Patient ID");
                            String patientId = input.nextLine();
                            Validation patVal = new Validation("Patient", "PatientID", patientId);
                            if(patVal.validatePresence()) {
                                update.put("PatientID", patientId);
                            } else {
                                throw new InvalidID("Invalid PatientID, transaction aborted");
                            }
                            break;
                        case "8":
                            System.out.println("Enter Medical Record ID");
                            String MRId = input.nextLine();
                            Validation MRVal = new Validation("MedicalRecord", "MedicalRecordID", MRId);
                            if(MRVal.validatePresence()) {
                                update.put("MedicalRecordID", MRId);
                            } else {
                                throw new InvalidID("Invalid MedicalRecordID, transaction aborted");
                            }
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");
                    }
                }
                update.put("StaffID", User.id);
                String updateQuery = "";
                for (String key : update.keySet()) {
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateBRDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();
                System.out.println("Details updated Successfully");
                Connector.commit();
            } catch (SQLException e) {
                Connector.rollback();
                System.out.println("Error occured while updating details" + e.getMessage());
                //e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such Billing Record exists");
        }
        Connector.setAutoCommit(true);
    }
	
	/**
	 * Method to display all the staff details grouped by role
	 * @param input
	 */
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
	
	/**
	 * Method to get the patients a doctor
	 * @param input
	 */
	private static void getActivePatientForDoctor(Scanner input) {
			int temp;
			System.out.println("Enter Doctor ID:");
			temp = input.nextInt();
			getActivePatientForGivenDoctor(temp);
	}
	
	/**
	 * Method will display the patient list for given doctor ID 
	 * @param doctorID
	 */
	public static void getActivePatientForGivenDoctor(int doctorID) {
		try {
			Connector.createPreparedStatement(Constants.getActivePatientForDoctor);
			Connector.setPreparedStatementInt(1, doctorID);
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
	
	
	/**
	 * method to get the usage details for a given ward
	 */
	private static void getBedUsage() {
		try {
			Connector.createStatement();
			// get available/occupancy details for a given ward
			ResultSet rs = Connector.executeQuery(Constants.getBedUsage);
			String leftAlignFormat = "|       %-10s |    %-10s |    %-10s |     %-10s|%n";
			// display ward no, capacity, total count of available beds and total count of occupied beds 
			System.out.format("+------------------+---------------+---------------+---------------+%n");
			System.out.format("| Ward No          |  Capacity     |    Available  |  Occupied     |%n");
			System.out.format("+------------------+---------------+---------------+---------------+%n");

			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4));

			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
		}
	}
	
	/**
	 * Method to get the ward usage percentage at any given time
	 */
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
	
	/**
	 * Method to get the ward usage percentage at any given time
	 * @return ward usage percentage for resue
	 */
	private static float returnWardUsagePercentage() {
		try {
			Connector.createStatement();
			ResultSet rs = Connector.executeQuery(Constants.getWardUsagePercentage);
			if(rs.next()) {
				return rs.getFloat(1)*100;
			}
			else {
				return (float) -1.0;
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again." + e.getMessage());
			return (float) -1.0;
		}
	}
	
	
	/**
	 * Method to check the bed availablity based on bed type or ward number
	 * @param input
	 */
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
	/**
	 * Method to check the bed availablity based on bed type
	 * @param input
	 */
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
	
	/**
	 * Method to check the bed availablity based on ward number
	 * @param input
	 */
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
	
	
	
	/**
	 * method to reserve a requested bed in the requested ward 
	 * @param input
	 */
	private static void reserveBed(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();		
			// validate ward id
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	// reserve bed
            	Connector.createPreparedStatement(Constants.reserveBed);
            	Connector.setPreparedStatementInt(1, temp);
    			Connector.setPreparedStatementString(2, bedId.toUpperCase());
                if(Connector.executeUpdatePreparedQuery() == 1)
                	System.out.println("Bed reserved Successfully");
                else {
                	// fails if bed id is invalid
                	System.out.println("Error occured, invalid Bed Id! try again");
                }
            } else {
                System.out.println("Given ward doesn't exist");
            }	  
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}	
	}
	
	
	
	/**
	 * // method to create billing record
	 * @param input
	 * @throws SQLException
	 */
	// performed in transaction - medical record is created internally
	private static void createBillingRecord(Scanner input) throws SQLException {
		// check if they want to admit the patient
		System.out.println("Do you want to admit the patient(Y/N)?:");
		String admit = input.next();
		boolean bedAvailable = true;
		// if patient needs to be admitted
		if(admit.equalsIgnoreCase("Y")) {
			float result = returnWardUsagePercentage();
			if(result < 0.0) {
				// if it fails during DB operation
				throw new SQLException();
			} else if (result < 100.0){
				// if beds are available
				bedAvailable = true;
			} else 
				// if no beds are available
				bedAvailable = false;
		} 
		// if no beds are available, return saying no beds available
		if(!bedAvailable) {
			System.out.println("No empty bed available to check in the patient");
		// else create billing record and medical record
		} else {
			try {
				// read patient id
				System.out.println("Enter Patient ID:");
				int patientId = input.nextInt();
				// read responsible doctor id
				System.out.println("Enter the Id of the Responsible Doctor for this treatment:");
				int docId = input.nextInt();
				// validate patient id
				Connector.createPreparedStatement(Constants.validatePatient);
				Connector.setPreparedStatementInt(1, patientId);
	            ResultSet patRes =  Connector.executePreparedQuery();
	            // validate doctor id
	            Connector.createPreparedStatement(Constants.validateDoctor);
				Connector.setPreparedStatementInt(1, docId);
	            ResultSet docRes =  Connector.executePreparedQuery();
	            // if both patient id and doctor id are valid
	            if(patRes.next() && docRes.next()) {
	            	// set auto commit to false
	            	Connector.setAutoCommit(false);
	            	// create medical record
	     			Connector.createPreparedStatement(Constants.createMedicalRecord);
	     			Connector.setPreparedStatementInt(1, docId);
	     			Connector.setPreparedStatementInt(2, patientId);
	     			Connector.executeUpdatePreparedQuery();
	     			ResultSet rs = Connector.getGeneratedKeys();
	     			int medId = 0;
	     			// if medical record creation is successful
	     			if(rs.next())
	     				medId = rs.getInt(1);
	     			else
	     				throw new SQLException();
	     			// create billing record
	     			Connector.createPreparedStatement(Constants.createBillingRecord);
	     			Connector.setPreparedStatementInt(1, Integer.parseInt(User.id));
	     			Connector.setPreparedStatementInt(2, patientId);
	     			Connector.setPreparedStatementInt(3, medId);
	     			System.out.println("Enter Payment Method(Card/Cash/Insurance):");
	     			String paymentMethod = input.next();
	     			Connector.setPreparedStatementString(4, paymentMethod);
	     			if(!paymentMethod.equalsIgnoreCase("cash")) {
	     				// read card details and billing address if payment method is not cash
	     				System.out.println("Enter card/insurance details:");
	     				String temp = input.next();
	     				Connector.setPreparedStatementString(5, temp);
	     				System.out.println("Enter billing address:");
	     				temp = input.next();
	     				Connector.setPreparedStatementString(8, temp);
	     			} else {
	     				Connector.setPreparedStatementString(5, null);
	     				Connector.setPreparedStatementString(8, null);
	     			}
	     		
	     			System.out.println("Enter Fees:");
	     			float fees = input.nextFloat();
	     			Connector.setPreparedStatementFloat(6, fees);
	     			// check if payeeSSN is to be added
	     			System.out.println("Do you want to enter PayeeSSN(optional)? (Y/N):");
	     			String temp = input.next();
	     			if(temp.equals("Y")) {
	     				System.out.println("Enter PayeeSSN:");
	     				temp = input.next();
	     				Connector.setPreparedStatementString(7, temp);
	     			} else {
	     				Connector.setPreparedStatementString(7, null);
	     			}		
	     			Connector.executeUpdatePreparedQuery();
	     			// commit everything finally
	     			Connector.commit();
	     			// set auto commit to true
	     			Connector.setAutoCommit(true);
	     			System.out.println("Billing record created successfully");
	     			
	     		} else {
	     			// return with a message when patient id or responsible doctor id are valid
	                System.out.println("Given patient ID or Responsible doctor ID doesn't exist, try again!");
	            }  
			}
	        catch(SQLException e) {
	        	// handle exceptions - perform rollback and set auto commit to true
	 			Connector.rollback();
	 			Connector.setAutoCommit(true);
	 			System.out.println("Error occured while creating entries, please check your input data " + e.getMessage());
	 		}
		}
	}
	
	/**
	 * Method to assign a bed to a patient
	 * @param input
	 * @throws SQLException
	 */
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
	
	
	/**
	 *  method to release a requested bed in the requested ward 
	 * @param input
	 */
	private static void releaseBed(Scanner input) {
		try {
			int temp;
			// read ward id
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			String bedId = null;
			// read bed id
			System.out.println("Enter Bed ID:");
			bedId = input.next();			
			// valdiate ward id
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	// release bed
            	Connector.createPreparedStatement(Constants.releaseBed);
            	Connector.setPreparedStatementInt(1, temp);
    			Connector.setPreparedStatementString(2, bedId.toUpperCase());
                if(Connector.executeUpdatePreparedQuery() == 1)
                	System.out.println("Bed released Successfully");
                else {
                	// failed due to invalid bed id
                	System.out.println("Error occured, invalid Bed Id! try again");
                }
            } else {
                System.out.println("Given ward doesn't exist");
            }	  
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again "+e.getMessage());
		}	
	}
	
	
	/**
	 * method to delete a staff
	 * @param input
	 */
	private static void deleteStaff(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Staff ID:");
			temp = input.nextInt();
			if(temp == Integer.parseInt(User.id)) {
				System.out.println("You cannot delete your own account, try again");
				RegistrationStaff.menu(input);
			}
			// check if the passed staff id exists
			Connector.createPreparedStatement(Constants.checkStaff);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	// delete if the id exists
            	Connector.createPreparedStatement(Constants.deleteStaff);
                Connector.setPreparedStatementInt(1, temp);
                if(Connector.executeUpdatePreparedQuery() == 1)
                    System.out.println("Staff deleted Successfully");
                else {
                    System.out.println("Error deleting staff, try again");
                }
            } else {
                System.out.println("Given staff doesn't exist");
            }
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again "+e.getMessage());
		}	
	}
	
	
	/**
	 * method to delete a patient
	 * @param input
	 */
	private static void deletePatient(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Patient ID:");
			temp = input.nextInt();
			// check if the passed patient id exists
			Connector.createPreparedStatement(Constants.validatePatient);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	// delete if the id exists
            	Connector.createPreparedStatement(Constants.deletePatient);
                Connector.setPreparedStatementInt(1, temp);
                if(Connector.executeUpdatePreparedQuery() == 1)
                    System.out.println("Patient deleted Successfully");
                else {
                    System.out.println("Error deleting patient, try again");
                }
            } else {
                System.out.println("Given patient doesn't exist");
            }	  
		} catch(SQLException e) {
			System.out.println("Error occured, try again "+e.getMessage());
		}	
	}
	
	
	
	/**
	 * method to delete a ward
	 *  handled in transaction - ward and all its beds are deleted
	 * @param input
	 */
	private static void deleteWard(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Ward Number:");
			temp = input.nextInt();
			// validate ward id
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	// make sure no beds of this ward are assigned to any patient
            	Connector.createPreparedStatement(Constants.checkBeds);
    			Connector.setPreparedStatementInt(1, temp);
                ResultSet res =  Connector.executePreparedQuery();
                if(!res.next()) {
                	try {
                		// set auto commit to false - to handle transaction
                		Connector.setAutoCommit(false);
                		// delete beds
                    	Connector.createPreparedStatement(Constants.deleteBeds);
                    	Connector.setPreparedStatementInt(1, temp);
                    	if(Connector.executeUpdatePreparedQuery() == 0)
                    		throw new SQLException();
                    	// delete ward
                    	Connector.createPreparedStatement(Constants.deleteWard);
                    	Connector.setPreparedStatementInt(1, temp);
                    	if(Connector.executeUpdatePreparedQuery() != 1)
                    		throw new SQLException();
                    	// commit everything at once finally
                    	Connector.commit();
                    	// set auto commit to true
                    	Connector.setAutoCommit(true);
                    	System.out.println("Ward deleted successfully");
                	} catch(SQLException e){
                		// handle exception
                		// rollback  everything and set auto commit to true
                		Connector.rollback();
                		Connector.setAutoCommit(true);
                		System.out.println("Error occured while deleting the ward, try again ");
                	}
                } else {
                	// do not delete ward
                	System.out.println("One or more beds of this ward are already assigned to patients, ward delete not allowed!");
                }
            } else {
            	System.out.println("Given ward doesn't exist, try again!");
            }	  
		} catch(SQLException e) {
			System.out.println("Error occured, try again "+e.getMessage());
		}	
	}
	
	
	/**
	 * Method to create a new Staff based on user inputs
	 * @param input
	 */
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
			System.out.println("Error occured while processing the data "+e.getMessage());
		}	
	}
	
	/**
	 * Method to create a new Patient based on user input.
	 * @param input
	 */
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
			int plan;
			System.out.println("Enter Patient Address:");
			plan = input.nextInt();
			Connector.setPreparedStatementInt(7, plan);
			Connector.setPreparedStatementString(8, "Yes");
			Connector.setPreparedStatementInt(9, Integer.parseInt(User.id));
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
	
	/**
	 * Method will validate if a nurse is present in the database
	 * @param id
	 * @return
	 */
	public static boolean validateNurse(int id) {
		boolean res = false;
		try {
			Connector.createPreparedStatement(Constants.validateNurse);
			Connector.setPreparedStatementInt(1, id);
			ResultSet result = Connector.executePreparedQuery();
			if(result.next()) {
				res = true;
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
		return res;
	}
	
	/**
	 * This method will create a new Ward based on user inputs
	 * The method will also create the required beds and assign them to ward
	 * This is handled in a transaction
	 * @param input
	 * @throws SQLException
	 */
	private static void createNewWard(Scanner input) throws SQLException {
		try {
			Connector.setAutoCommit(false);
		    int capacity = 0, charges = 0;
		    System.out.println("Enter Ward Capacity:");
		    capacity = input.nextInt();
			System.out.println("Enter Ward Charges:");
			charges = input.nextInt();
			while(true) {
				System.out.println("Enter Responsible Nurse:");
				int staffID = input.nextInt();
				if(validateNurse(staffID)) {
					Connector.createPreparedStatement(Constants.createWard);
					Connector.setPreparedStatementInt(1, capacity);
					Connector.setPreparedStatementInt(2, charges);
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
    
     /**
     * Check if a given Ward-bed is free
     * @param wardNo
     * @param bedid
     * @return
     */
    static boolean checkBedAvail(int wardNo,String bedid){
	    try {
            String bedAvailQuery = "select * from Bed where WardNo = ? and BedId = ?";
            Connector.createPreparedStatement(bedAvailQuery);
            Connector.setPreparedStatementInt(1, wardNo);
            Connector.setPreparedStatementString(2, bedid);
            Connector.executeUpdatePreparedQuery();
            ResultSet rs = Connector.executePreparedQuery();
            if (rs.next()) {
                if(rs.getString("Status").charAt(0) == 'Y')
                    return true;
            }
        }
        catch(Exception e){
            System.out.println("error occurred while check bed status");
        }
        return false;

    }
    
    /**
     * Display the Ward and bed status information
     */
    static void displayBedAvail(){
        try {
            String bedAvailQuery = "select * from Bed; ";
            Connector.createPreparedStatement(bedAvailQuery);
            Connector.executeUpdatePreparedQuery();
            ResultSet rs = Connector.executePreparedQuery();
            String leftAlignFormat = "|       %-10s |    %-8s |    %-8s |%n";
            System.out.format("+------------------+-------------+-------------+%n");
            System.out.format("| BedID            |  WardNo     |    Status   |%n");
            System.out.format("+------------------+-------------+-------------+%n");

            while(rs.next()) {
                System.out.format(leftAlignFormat,rs.getString(1),rs.getInt(2),rs.getString(3));

            }
        }
        catch(Exception e){
            System.out.println("error occurred while displaying bed availability");
        }


    }
    
	/**
	 * Method to Check-In the patient.
	 * @param input
	 * @throws SQLException
	 * @throws InvalidID
	 */
	private static void check_in(Scanner input) throws SQLException, InvalidID {
		try {
			Connector.setAutoCommit(false);  //Set auto-commit to false, transaction begins
			Connector.createPreparedStatement(Constants.createCheckIn);
			//int capacity = 0 , charges = 0;
			System.out.println("Enter Patient ID:");
			String id = input.next();
			if(new Validation("Patient","PatientID",id).validatePresence()){
                displayBedAvail();  //display the list of available beds
                boolean bedAlloted=false;

				System.out.println("Enter Ward Number:");
				int wardNo = input.nextInt();
				System.out.println("Enter Bed ID:");
				String bedid = input.next();


					if(checkBedAvail(wardNo,bedid)){
                        Connector.createPreparedStatement(Constants.createCheckIn);

						Connector.setPreparedStatementInt(1, Integer.valueOf(User.id));
						Connector.setPreparedStatementInt(2, Integer.valueOf(id));
						Connector.setPreparedStatementInt(3, wardNo);
						Connector.setPreparedStatementString(4, bedid.toUpperCase());
						Connector.executeUpdatePreparedQuery();  //create checkin for the patient in the alloted bed
						bedAlloted=true;

					}
					else{

                       throw new InvalidID("Bed not available");
                    }

				//Update Bed status to unavailable
				if(bedAlloted){

						Connector.createPreparedStatement(Constants.reserveBed);
						Connector.setPreparedStatementInt(1, wardNo);
						Connector.setPreparedStatementString(2, bedid.toUpperCase());
						Connector.executeUpdatePreparedQuery() ; //update the bed to not available

				}

				Connector.commit();  //commit the transaction
				System.out.println("Patient check-in success");
			}
			else{
				System.out.println("Incorrect Patient Id, retry");
			}

		} catch (SQLException e) {
			Connector.rollback();   //rol-back in case of error
			System.out.println("Error occured while processing the data" + e.getMessage());
            //e.printStackTrace(System.out);
		}
		Connector.setAutoCommit(true);

	}
	
	/**
	 * Get Medical Record of a given patient for a given month and year
	 * @param input
	 * @throws SQLException
	 */
	public static void getMedicalRecordForPatient(Scanner input) throws SQLException{
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
	
	
	/**
	 * method to return the total count of patients on a given year and month
	 * @param input
	 * @throws SQLException
	 */
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
			// print result with year, month and count attributes
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

   
	/**
	 * Get Medical Record of a given patient within start and end month in a year
	 * @param input
	 * @throws SQLException
	 */
	public static void getMedicalRecordForPatientBetween(Scanner input) throws SQLException{
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

	/**
	 * Exception class for custom exception
	 *
	 */
	public static class InvalidID extends Exception {
        public InvalidID(String message) {
            super(message);
        }
    }

    /**
     * Exception class for custom exception
     *
     */
    public static class InvalidChoice extends Exception {
        public InvalidChoice(String message) {
            super(message);
        }
    }
}
