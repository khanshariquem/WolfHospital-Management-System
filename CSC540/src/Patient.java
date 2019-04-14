import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Patient {
	
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
                updatePatientDetails(input);
				break;
			case 2:
                viewMedicalRecord();
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

    private static void updatePatientDetails(Scanner input) {
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
                        break;
                }
            }
            String updateQuery = "";
            for (String key : update.keySet()) {
                updateQuery += key + " = '" + update.get(key) + "',";
            }
            String temp = Util.createUpdateStatement(updateQuery, User.id, Constants.updatePatientDetails);
            Connector.createPreparedStatement(temp);
            Connector.executeUpdatePreparedQuery();
            System.out.println("Details updated Successfully");
            Connector.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error occured while updating your details" + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private static void viewMedicalRecord() {

	}

    private static void viewBillingRecord() {

    }
}

