
public final class Constants {
	
	public static final String doctorRole = "D";
	public static final String patientRole = "P";
	public static final String registrationStaffRole = "R";
	
	public static final String validatePatient = "select * from Patient where PatientID = ?";
	public static final String validateDoctor = "select * from Staff where StaffID = ? and JobTitle = 'Doctor'";
	public static final String validateRegistrationStaff = "select * from Staff where StaffID = ? and JobTitle = 'RegistrationStaff'";
	
	
}
