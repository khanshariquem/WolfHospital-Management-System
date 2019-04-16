import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class for role - Doctor
 *
 */

public class Doctor {
	
	/**
	 * Method to display the menu for Doctor
	 * @param input
	 */
	public static void menu(Scanner input) {
		while (true) {
			System.out.println("Hi "+User.name+" , Welcome to WolfHospital Management System");
			System.out.println("Menu:");
			System.out.println("1. Update Medical Record");
			System.out.println("2. Get My Patients List");
			System.out.println("3. Get Patient Medical Record by MM/YYY");
            System.out.println("4. Get Patient Medical Record between start and end period");
			System.out.println("5. Sign Out");
			System.out.println("6. Exit");
			System.out.print("Enter Choice : ");
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				updateMedicalRecord(input);
				break;
			case 2:
				RegistrationStaff.getActivePatientForGivenDoctor(Integer.parseInt(User.id));
				break;	
			case 3:
				viewPatientMedicalRecord(input);
			case 4:
				try {
					RegistrationStaff.getMedicalRecordForPatientBetween(input);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			case 5:
				User.name = null;
				Index.homePage(input);
				break;	
			case 6:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}
	/**
	 * Method to view the medical record for a patient
	 * @param input
	 */
	public static void viewPatientMedicalRecord(Scanner input) {
		Integer temp;
		System.out.println("Enter Patient ID:");
		temp = input.nextInt();
		Validation val=new Validation("Patient","PatientID",temp.toString());
		if(val.validatePresence())
			Patient.viewPatientMedicalRecord(input, temp);
		else{
			System.out.println("Patient does not exist.Re-try");
			Doctor.menu(input);
		}
	}
	/**
	 * Method to update the medical record of the patient
	 * the user is provided with menu and the update happens based on user inputs. 
	 * the number of fields to update are selected by the user.
	 * @param input
	 */
	public static void updateMedicalRecord(Scanner input){
		{
			boolean tryUpdate=true;
			while(tryUpdate){
				System.out.println("Hi "+User.name+" , Enter Medical Record Id:");
				String id = input.next();
				Validation val=new Validation("MedicalRecord","MedicalRecordID",id);
				if(val.validatePresence()){
					tryUpdate=false;
					while (true) {
						System.out.println("Please select the update type");
						System.out.println("Menu:");
						System.out.println("1. Add Medicine");
						System.out.println("2. Update Medicine");
						System.out.println("3. Add Test");
						System.out.println("4. Update Test");
						System.out.println("5. Update Other Medical Record fields ");
						System.out.println("6. Go Back");
						System.out.print("Enter Choice : ");
						int choice = input.nextInt();
						input.nextLine();
						switch (choice) {
							case 1:
								createMedicine(input,id);
								break;
							case 2:
								updateMedicine(input,id);
								break;
							case 3:
								createTest(input,id);
								break;
							case 4:
								updateTest(input,id);
								break;
							case 5:
								updateMROtherFields(input,id);
								break;
							case 6:
								System.out.println("Re-directing to Home Page");
								Index.homePage(input);
							default:
								break;
						}
					}
				}
				else{
					System.out.println("Medical Record does not exist.Re-try");
					Index.homePage(input);
				}


			}

		}
	}

	/**
	 * Create a new Medicine entry for the Medical record
	 * @param input
	 * @param MRID
	 */
	public static void createMedicine(Scanner input,String MRID){
		{
			try {
				Connector.createPreparedStatement(Constants.createMedicine);
				String temp = null;
				System.out.println("Enter Medicine Name:");
				temp = input.nextLine();
				Connector.setPreparedStatementString(1, temp);
				System.out.println("Enter Medicine Manufacturer:");
				temp = input.nextLine();
				Connector.setPreparedStatementString(2, temp);
				Connector.setPreparedStatementString(3, MRID);

				Connector.executeUpdatePreparedQuery();
				System.out.println("Medicine Added Successfully");
			}
			catch(SQLException e) {
				System.out.println("Error occured while adding medicine data"+e.getMessage());
			}
		}
	}
	
	/**
	 * update theMedicine entry for the  given Medical record
	 * @param input
	 * @param mrid
	 */
	public static void updateMedicine(Scanner input, String mrid){
		try {
			System.out.println("Enter Medicine Id");
			String id = input.next();
			if(checkMedicineID(id,mrid) ) {
			System.out.println("Choose fields to update( comma separate if multiple fields)");

			System.out.println("1.Name");
			System.out.println("2.Cost");
			System.out.println("3.Manufacturer");
			String choices = input.next();
			String[] choice = choices.split(",");
			HashMap<String, String> update = new HashMap<String, String>();
			for (String c : choice) {
				switch (c) {
					case "1":
						System.out.println("Enter Name");
						update.put("Name", input.next());
						break;
					case "2":
						System.out.println("Enter Cost");
						update.put("Cost", input.next());
						break;
					case "3":
						System.out.println("Enter Manufacturer");
						update.put("Manufacturer", input.next());
						break;
					default:
						break;
				}
			}
			String updateQuery = "";
			for (String key : update.keySet()) {
				updateQuery += key + " = '" + update.get(key) + "',";
			}
			String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateMedicine);
			Connector.createPreparedStatement(temp);
			Connector.executeUpdatePreparedQuery();
			System.out.println("Medicine updated Successfully");
		}
		else{
				System.out.println("Medicine ID for the given Medical Record ID doesnt exist ");
			}
		}
		catch(SQLException e) {
			System.out.println("Error occured while updating Medicine data"+e.getMessage());
		}
	}
	
	/**
	 * Create a new test entry for the Medical record
	 * @param input
	 * @param id
	 */
	public static void createTest(Scanner input,String id){
		try {
			String temp = null;
			System.out.println("Enter responsible staff id:");
			temp = input.nextLine();
			if(!(RegistrationStaff.validateNurse(Integer.parseInt(temp)) || RegistrationStaff.validateDoctor(Integer.parseInt(temp)))) {
				throw new RegistrationStaff.InvalidID("Invalid Staff ID");
			}
			Connector.createPreparedStatement(Constants.createTest);
			Connector.setPreparedStatementString(3, temp);
			System.out.println("Enter Test Name:");
			temp = input.nextLine();
			Connector.setPreparedStatementString(1, temp);
			System.out.println("Enter Test Lab:");
			temp = input.nextLine();
			Connector.setPreparedStatementString(2, temp);
			Connector.setPreparedStatementString(4, id);
			Connector.executeUpdatePreparedQuery();
			System.out.println("Test Added Successfully");
		}
		catch(Exception e) {
			System.out.println("Error occurred while adding Test data"+e.getMessage());
		}
	}
	
	/**
	 * Method to check if a given medicine id belongs to the given Medical Record
	 * @param medId
	 * @param mrid
	 * @return
	 */
	static boolean checkMedicineID(String medId, String mrid){
		try {
			Connector.createPreparedStatement(Constants.selectMedicine);
			Connector.setPreparedStatementString(1, medId);
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.executePreparedQuery();
			if (rs.next()) {
				if(mrid.equals(rs.getString("MedicalRecordID")) )
					return true;
			}
		}
		catch(Exception e){
			System.out.println("error occurred while checking Medicine table");
		}
		return false;

	}
	
	/**
	 * Method to check if a given test id belongs to the given Medical Record
	 * @param testId
	 * @param mrid
	 * @return
	 */
	static boolean checkTestID(String testId, String mrid){
		try {
			Connector.createPreparedStatement(Constants.selectTest);
			Connector.setPreparedStatementString(1, testId);
			Connector.executeUpdatePreparedQuery();
			ResultSet rs = Connector.executePreparedQuery();
			if (rs.next()) {
				if(mrid.equals(rs.getString("MedicalRecordID")))
					return true;
			}
		}
		catch(Exception e){
			System.out.println("error occurred while checking Test table");
		}
		return false;

	}
	
	/**
	 * update the test entry for the  given Medical record
	 * @param input
	 * @param mrid
	 */
	public static void updateTest(Scanner input,String mrid) {
		try {
			System.out.println("Enter Test Id");
			String id=input.next();
			if(checkTestID(id,mrid) ) {
				System.out.println("Choose fields to update( comma separate if multiple fields)");
				System.out.println("1.Name");
				System.out.println("2.Cost");
				System.out.println("3.Lab");
				System.out.println("4.Result");
				System.out.println("5.Test Staff ");
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
							System.out.println("Enter Cost");
							update.put("Cost", input.nextLine());
							break;
						case "3":
							System.out.println("Enter Lab");
							update.put("Lab", input.nextLine());
							break;
						case "4":
							System.out.println("Enter Result");
							update.put("Result", input.nextLine());
							break;
						case "5":
							System.out.println("Enter Test Staff");
							String temp = input.nextLine();
							if(RegistrationStaff.validateNurse(Integer.parseInt(temp)) || RegistrationStaff.validateDoctor(Integer.parseInt(temp))) {
								update.put("StaffID", temp);
							} else {
								throw new RegistrationStaff.InvalidID("Invalid Staff ID");
							}
							break;
						default:
							break;
					}
				}
				String updateQuery = "";
				for (String key : update.keySet()) {
					updateQuery += key + " = '" + update.get(key) + "',";
				}
				String temp = Util.createUpdateStatement(updateQuery, id, Constants.updateTest);
				Connector.createPreparedStatement(temp);
				Connector.executeUpdatePreparedQuery();
				System.out.println("Test Updated Successfully");
			}
			else{
				System.out.println("Test ID for the given Medical Record ID doesnt exist ");
			}
		} catch (Exception e) {
			System.out.println("Error occured while adding Test data" + e.getMessage());
		}
	}

	
	/**
	 * Method to update other fields of the Medical record
	 * @param input
	 * @param id
	 */
	public static void updateMROtherFields(Scanner input,String id){
		try {
			System.out.println("Choose fields to update( comma separate if multiple fields)");
			System.out.println("1.Start Date");
			System.out.println("2.End Date");
			System.out.println("3.Responsible Doctor Id");
			String choices = input.next();
			input.nextLine();
			String[] choice = choices.split(",");
			HashMap<String, String> update = new HashMap<String, String>();
			for (String c : choice) {
				switch (c) {
					case "1":
						System.out.println("Enter Start Date(YYYY-MM-DD)");
						update.put("StartDate", input.nextLine());
						break;
					case "2":
						System.out.println("Enter End Date(YYYY-MM-DD)");
						update.put("EndDate", input.nextLine());
						break;

					case "3":
						System.out.println("Enter Responsible Doctor Id");
						String temp = input.nextLine();
						if(RegistrationStaff.validateDoctor(Integer.parseInt(temp))) {
							update.put("StaffID", temp);
						} else {
							throw new RegistrationStaff.InvalidID("Invalid Staff ID");
						}
						break;
					default:
						break;
				}
			}
			String updateQuery = "";
			for (String key : update.keySet()) {
				updateQuery+=key+" = '"+ update.get(key)+ "',";
			}

			String temp=Util.createUpdateStatement( updateQuery,id, Constants.updateMROtherField );
			Connector.createPreparedStatement(temp);
			Connector.executeUpdatePreparedQuery();
			System.out.println("Medical Record Updated Successfully");
		} catch (Exception e) {
			System.out.println("Error occured while adding Medical Record  data" + e.getMessage());
		}
	}

}
