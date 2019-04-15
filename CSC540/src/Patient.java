import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;


public class Patient {
    /**
     * Gives the user the menu to choose the options in accordance to his role of Patient
     * @param input
     * @return
     */
	public static void menu(Scanner input) {
		while (true) {
			System.out.println("Hi " + User.name + " , Welcome to WolfHospital Management System");
			System.out.println("Menu:");
            System.out.println("1. Update my details");
            System.out.println("2. View my medical record");
            System.out.println("3. View my billing record");
			System.out.println("4. Sign Out");
			System.out.println("5. Exit");
			System.out.print("Enter Choice : ");
			int choice = input.nextInt();
			switch (choice) {
			case 1:
			    try{
                    updatePatientDetails(input);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } catch (RegistrationStaff.InvalidChoice e) {
                    System.out.println(e.getMessage());
                }
				break;
			case 2:
			    viewMedicalRecord(input);
				break;
			case 3:
                viewBillingRecord();
				break;
			case 4:
				User.name = null;
				Index.homePage(input);
				break;
			case 5:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}

    /**
     * Use method to update patient details such as Name, Address, DOB, Gender, Phone, SSN
     * @param input
     * @return
     */
    private static void updatePatientDetails(Scanner input) throws SQLException, RegistrationStaff.InvalidChoice {
        try {
            Connector.setAutoCommit(false);
            System.out.println("Choose fields to update (comma separate if multiple fields)");
            System.out.println("1.Name  2.Address  3.DOB  4.Phone  5.Gender  6.SSN ");
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
                    default:
                        throw new RegistrationStaff.InvalidChoice("Invalid menu option");  // Throws a custom exception if user chooses invalid menu option
                }
            }
            String updateQuery = "";
            for (String key : update.keySet()) {   // All update key, values are concatenated
                updateQuery += key + " = '" + update.get(key) + "',";
            }
            String temp = Util.createUpdateStatement(updateQuery, User.id, Constants.updatePatientDetails);
            Connector.createPreparedStatement(temp);
            Connector.executeUpdatePreparedQuery();    // Executes update of all user selected fields
            System.out.println("Details updated Successfully");
            Connector.commit();    // Commits the transaction
        } catch (SQLException e) {
            Connector.rollback();    //In case of error, the transaction is rollbacked
            System.out.println("Error occured while updating your details" + e.getMessage());
            //e.printStackTrace(System.out);
        }
        Connector.setAutoCommit(true);     // Auto commit enabled post transaction
    }

    /**
     * Use method to view medical record of a patient, also by a doctor for a particular patient
     * @param input
     * @return
     */
    public static void viewMedicalRecord(Scanner input) {
        viewPatientMedicalRecord(input,Integer.parseInt(User.id));
    }

    /**
     * Use method to view medical record of a patient given his id.
     * @param input
     * @param id
     * @return
     */
    public static void viewPatientMedicalRecord(Scanner input,int id) {
        try {
            Connector.createPreparedStatement(Constants.getMedicalRecordForPatient);
            Connector.setPreparedStatementInt(3, id);
            System.out.println("Enter Month:");
            String stMonth=input.next();
            Connector.setPreparedStatementString(1, stMonth);
            System.out.println("Enter Year:");
            stMonth=input.next();
            Connector.setPreparedStatementString(2, stMonth);
            ResultSet rs = Connector.executePreparedQuery();
            String leftAlignFormat = "|   %-13s  |  %-16s   |   %-15s  |      %-14s |      %-15s |      %-18s     |%n";
            System.out.format("+------------------+---------------------+---------------------+--------------------+----------------------+---------------------------------+%n");
            System.out.format("|  Start Date      |    End Date         |      Medicine Name  |     Test Name      |      Test Lab        |          Test Result            |%n");
            System.out.format("+------------------+---------------------+---------------------+--------------------+----------------------+---------------------------------+%n");

            while(rs.next()) {
                System.out.format(leftAlignFormat,rs.getString(2),rs.getString(3),rs.getString(6),rs.getString( 7),rs.getString(8),rs.getString(9));
            }
            System.out.println();
        } catch(SQLException e) {
            System.out.println("Error occured, try again"+e.getMessage());
        }
    }

    /**
     * Use method to view billing record of a patient
     * @return
     */
    private static void viewBillingRecord() {
        try {
            Connector.createPreparedStatement(Constants.getBillingRecordsForPatient);
            Connector.setPreparedStatementInt(1, Integer.parseInt(User.id));
            ResultSet rs = Connector.executePreparedQuery();
            String leftAlignFormat = "|   %-13s  |  %-16s   |   %-14s  |      %-13s |      %-15s |      %-12s     |%n";
            System.out.format("+------------------+---------------------+---------------------+--------------------+----------------------+---------------------------------+%n");
            System.out.format("|  Visit Date      |    Payment Method   |      Card Number    |          Fees      |      Payee SSN       |          Billing Address        |%n");
            System.out.format("+------------------+---------------------+---------------------+--------------------+----------------------+---------------------------------+%n");

            while(rs.next()) {
                System.out.format(leftAlignFormat,rs.getString(2),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
            }
            System.out.println();
        } catch(SQLException e) {
            System.out.println("Error occured, try again"+e.getMessage());
        }
    }
}

