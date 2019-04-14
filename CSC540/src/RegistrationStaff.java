import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
					System.out.println(e.getMessage());
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
                } catch (SQLException e) {
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

	private static void updateStaffDetails(Scanner input) throws SQLException, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Staff Id for updation:");
        String id = input.next();
        Validation val = new Validation("Staff", "StaffId", id);
        if (val.validatePresence()) {
            try {
                Connector.setAutoCommit(false);
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
                            throw new InvalidChoice("Invalid menu option");
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) {
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateStaffDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();
                System.out.println("Details updated Successfully");
                Connector.commit();
            } catch (SQLException e) {
                Connector.rollback();
                System.out.println("Error occured while updating Staff details" + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such user exists");
        }
        Connector.setAutoCommit(true);
    }

    private static void updatePatientDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Patient Id for updation:");
        String id = input.next();
        Validation val = new Validation("Patient", "PatientId", id);
        if (val.validatePresence()) {
            try {
                Connector.setAutoCommit(false);
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Name  2.Address  3.DOB  4.Phone  5.Gender  6.SSN  7.StaffID  8.Processing Treatment Plan  9.Completing Treatment");
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
                            System.out.println("Enter StaffID");
                            String staffId = input.nextLine();
                            Validation staffVal = new Validation("Staff", "StaffID", staffId);
                            if(staffVal.validatePresence()) {
                                update.put("StaffID", staffId);
                            } else {
                                throw new InvalidID("Invalid StaffId, transaction aborted");
                            }
                            break;
                        case "8":
                            System.out.println("Enter Processing_Treatment_Plan");
                            update.put("Processing_Treatment_Plan", input.nextLine());
                            break;
                        case "9":
                            System.out.println("Enter Completing_Treatment (Yes/No)");
                            update.put("Completing_Treatment", input.nextLine());
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) {
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updatePatientDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();
                System.out.println("Details updated Successfully");
                Connector.commit();
            } catch (SQLException e) {
                Connector.rollback();
                System.out.println("Error occured while updating Patient details" + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such patient exists");
        }
        Connector.setAutoCommit(true);
    }

    private static void updateWardDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Ward No for updation:");
        String id = input.next();
        Validation val = new Validation("Ward", "WardNo", id);
        if (val.validatePresence()) {
            try {
                Connector.setAutoCommit(false);
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
                            System.out.println("Enter StaffID");
                            String staffId = input.nextLine();
                            Validation staffVal = new Validation("Staff", "StaffID", staffId);
                            if(staffVal.validatePresence()) {
                                update.put("StaffID", staffId);
                            } else {
                                throw new InvalidID("Invalid StaffId, transaction aborted");
                            }
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) {
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateWardDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();
                System.out.println("Details updated Successfully");
                Connector.commit();
            } catch (SQLException e) {
                Connector.rollback();
                System.out.println("Error occured while updating Ward details" + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such ward exists");
        }
        Connector.setAutoCommit(true);
    }

    private static void updateCheckInDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Patient Id for updation:");
        String id = input.next();
        String selectQuery="select * from CheckIn where PatientId = ? and EndDate is NULL";
        Connector.createPreparedStatement(selectQuery);
        Connector.setPreparedStatementString(1, id);
        ResultSet resultSet = Connector.executePreparedQuery();
        if(resultSet.next()) {
            try {
                Connector.setAutoCommit(false);
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.WardNo & BedID  2.End Date");
                String choices = input.next();
                input.nextLine();
                String[] choice = choices.split(",");
                HashMap<String, String> update = new HashMap<String, String>();
                for (String c : choice) {
                    switch (c) {
                        case "1":
                            String oldbedDataQuery="select * from CheckIn where PatientId = ? and EndDate is NULL";
                            Connector.createPreparedStatement(oldbedDataQuery);
                            Connector.setPreparedStatementString(1, id);
                            ResultSet oldrs = Connector.executePreparedQuery();
                            int oldWardNo = 0;
                            String oldBedId = "";
                            if(oldrs.next()) {
                                oldWardNo = oldrs.getInt("WardNo");
                                oldBedId = oldrs.getString("BedId");
                            }
                            System.out.println("Enter WardNo");
                            String wardNo = input.nextLine();
                            System.out.println("Enter BedID");
                            String bedId = input.nextLine();
                            String bedAvailQuery="select * from Bed where WardNo = ? and BedId = ?";
                            Connector.createPreparedStatement(bedAvailQuery);
                            Connector.setPreparedStatementString(1, wardNo);
                            Connector.setPreparedStatementString(2, bedId);
                            Connector.executeUpdatePreparedQuery();
                            ResultSet rs = Connector.executePreparedQuery();
                            if(rs.next()) {
                                if(rs.getString("Status").charAt(0) == 'Y') {
                                    update.put("WardNo", wardNo);
                                    update.put("BedID", bedId);

                                    //Release previous bed
                                    Connector.createPreparedStatement(Constants.releaseBed);
                                    Connector.setPreparedStatementInt(1, oldWardNo);
                                    Connector.setPreparedStatementString(2, oldBedId);
                                    Connector.executeUpdatePreparedQuery();

                                    //Assign new bed
                                    Connector.createPreparedStatement(Constants.reserveBed);
                                    Connector.setPreparedStatementInt(1, Integer.parseInt(wardNo));
                                    Connector.setPreparedStatementString(2, bedId.toUpperCase());
                                    Connector.executeUpdatePreparedQuery();

                                } else {
                                    throw new InvalidID("Bed unavailable, transaction aborted");
                                }
                            } else {
                                throw new InvalidID("No such ward & bed exists, transaction aborted");
                            }
                            break;
                        case "2":
                            System.out.println("Enter End Date (YYYY-MM-DD)");
                            update.put("EndDate", input.nextLine());
                            break;
                        default:
                            throw new InvalidChoice("Invalid menu option");
                    }
                }
                String updateQuery = "";
                for (String key : update.keySet()) {
                    updateQuery += key + " = '" + update.get(key) + "',";
                }
                String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateCheckInDetails);
                Connector.createPreparedStatement(temp);
                Connector.executeUpdatePreparedQuery();
                System.out.println("Details updated Successfully");
                Connector.commit();
            } catch (SQLException e) {
                Connector.rollback();
                System.out.println("Error occured while updating details" + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("Invalid choice");
        }
        Connector.setAutoCommit(true);
    }

    private static void updateBRDetails(Scanner input) throws SQLException, InvalidID, InvalidChoice {
        System.out.println("Hi " + User.name + " , Enter Billing Record Id for updation:");
        String id = input.next();
        Validation val = new Validation("BillingRecord", "BillingRecordID", id);
        if (val.validatePresence()) {
            try {
                Connector.setAutoCommit(false);
                System.out.println("Choose fields to update (comma separate if multiple fields)");
                System.out.println("1.Visit Date  2.Payment Method  3.Card Number  4.Fees  5.PayeeSSN  6.BillingAddress  7.StaffID  8.PatientId  9.MedicalRecordId");
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
                        case "2":
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
                            System.out.println("Enter Staff ID");
                            String staffId = input.nextLine();
                            Validation staffVal = new Validation("Staff", "StaffID", staffId);
                            if(staffVal.validatePresence()) {
                                update.put("StaffID", staffId);
                            } else {
                                throw new InvalidID("Invalid StaffId, transaction aborted");
                            }
                            break;
                        case "8":
                            System.out.println("Enter Patient ID");
                            String patientId = input.nextLine();
                            Validation patVal = new Validation("Patient", "PatientID", patientId);
                            if(patVal.validatePresence()) {
                                update.put("PatientID", patientId);
                            } else {
                                throw new InvalidID("Invalid PatientID, transaction aborted");
                            }
                            break;
                        case "9":
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
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("No such Billing Record exists");
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
			int temp;
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();			
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	Connector.createPreparedStatement(Constants.reserveBed);
            	Connector.setPreparedStatementInt(1, temp);
    			Connector.setPreparedStatementString(2, bedId.toUpperCase());
                if(Connector.executeUpdatePreparedQuery() == 1)
                	System.out.println("Bed reserved Successfully");
                else {
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
	
	private static void createBillingRecord(Scanner input) throws SQLException {
		try {
			System.out.println("Enter Patient ID:");
			int patientId = input.nextInt();
			
			Connector.createPreparedStatement(Constants.validatePatient);
			Connector.setPreparedStatementInt(1, patientId);
            ResultSet res =  Connector.executePreparedQuery();
            if(res.next()) {
            	Connector.setAutoCommit(false);
     			Connector.createPreparedStatement(Constants.createMedicalRecord);
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
     			if(!paymentMethod.equalsIgnoreCase("cash")) {
     				
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
     			Connector.commit();
     			Connector.setAutoCommit(true);
     			System.out.println("Billing record created successfully");
     			
     		} else {
                System.out.println("Given patient doesn't exist");
            }  
		}
        catch(SQLException e) {
 			Connector.rollback();
 			Connector.setAutoCommit(true);
 			System.out.println("Error occured while creating entries, please check your input data " + e.getMessage());
 		}
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
			int temp;
			System.out.println("Enter Ward ID:");
			temp = input.nextInt();
			String bedId = null;
			System.out.println("Enter Bed ID:");
			bedId = input.next();			
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	Connector.createPreparedStatement(Constants.releaseBed);
            	Connector.setPreparedStatementInt(1, temp);
    			Connector.setPreparedStatementString(2, bedId.toUpperCase());
                if(Connector.executeUpdatePreparedQuery() == 1)
                	System.out.println("Bed reserved Successfully");
                else {
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
	
	private static void deleteStaff(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Staff ID:");
			temp = input.nextInt();
			if(temp == Integer.parseInt(User.id)) {
				System.out.println("You cannot delete your own account, try again");
				RegistrationStaff.menu(input);
			}
			Connector.createPreparedStatement(Constants.checkStaff);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
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
	
	private static void deletePatient(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Patient ID:");
			temp = input.nextInt();
			
			Connector.createPreparedStatement(Constants.validatePatient);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
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
	
	
	private static void deleteWard(Scanner input) {
		try {
			int temp;
			System.out.println("Enter Ward Number:");
			temp = input.nextInt();
			
			Connector.createPreparedStatement(Constants.validateWard);
			Connector.setPreparedStatementInt(1, temp);
            ResultSet rs =  Connector.executePreparedQuery();
            if(rs.next()) {
            	Connector.createPreparedStatement(Constants.checkBeds);
    			Connector.setPreparedStatementInt(1, temp);
                ResultSet res =  Connector.executePreparedQuery();
                if(!res.next()) {
                	try {
                		Connector.setAutoCommit(false);
                    	Connector.createPreparedStatement(Constants.deleteBeds);
                    	Connector.setPreparedStatementInt(1, temp);
                    	if(Connector.executeUpdatePreparedQuery() == 0)
                    		throw new SQLException();
                    	Connector.createPreparedStatement(Constants.deleteWard);
                    	Connector.setPreparedStatementInt(1, temp);
                    	if(Connector.executeUpdatePreparedQuery() != 1)
                    		throw new SQLException();
                    	Connector.commit();
                    	Connector.setAutoCommit(true);
                    	System.out.println("Ward deleted successfully");
                	} catch(SQLException e){
                		Connector.rollback();
                		Connector.setAutoCommit(true);
                		System.out.println("Error occured while deleting the ward, try again ");
                	}
                } else {
                	System.out.println("One or more beds of this ward are already assigned to patients, ward delete not allowed!");
                }
            } else {
            	System.out.println("Given ward doesn't exist, try again!");
            }	  
		} catch(SQLException e) {
			System.out.println("Error occured, try again "+e.getMessage());
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
			System.out.println("Error occured while processing the data "+e.getMessage());
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
	
	private static void createNewWard(Scanner input) throws SQLException {
		try {
			Connector.setAutoCommit(false);
		    Connector.createPreparedStatement(Constants.createWard);
		    int capacity = 0, charges = 0;
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

	private static class InvalidID extends Exception {
        public InvalidID(String message) {
            super(message);
        }
    }

    private static class InvalidChoice extends Exception {
        public InvalidChoice(String message) {
            super(message);
        }
    }
}
