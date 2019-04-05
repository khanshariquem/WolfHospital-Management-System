
public class User {
	public static String name;
	
	public static void validateUser(int id , String role) {
		switch (role) {
		case Constants.doctorRole:
			validateDoctor(id);
			break;
		case Constants.patientRole:
			validatePatient(id);
			break;
		case Constants.registrationStaffRole:
			validateRegistrationStaff(id);
			break;
		}
			
	}
	
	private static void validatePatient(int id) {
		
	}
	
	private static void validateDoctor(int id) {
		
	}
	
	private static void validateRegistrationStaff(int id) {
		
	}
	
	
}
