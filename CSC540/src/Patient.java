import java.util.Scanner;

public class Patient {
	
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
				break;
			case 2:
				break;
			case 3:
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

}
