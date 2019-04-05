import java.util.Scanner;

public class Index {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Database User Name");
		String userName = input.next();
		System.out.println("Enter Database Password");
		String password = input.next();
		System.out.println("Enter Database to use");
		String dbName = input.next();
		Connector.createConnection(dbName, userName, password);	
		homePage(input);
	}
	
	public static void homePage(Scanner input) {
		System.out.println("Welcome to WolfHospital Management System");
		System.out.println("Please select your role");
		while (true) {
			System.out.println("1. Registration Staff");
			System.out.println("2. Doctor");
			System.out.println("3. Patient");
			System.out.println("4. Exit");
			System.out.print("Enter Choice : ");
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				Connector.closeConnection();
				System.out.println("Thank you for using the application! Hope to see you soon !");
				System.exit(0);
			default:
				System.out.println("Enter correct choice");
			}
		}
	}

}
