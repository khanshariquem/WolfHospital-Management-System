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
		homePage(input);
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

}
