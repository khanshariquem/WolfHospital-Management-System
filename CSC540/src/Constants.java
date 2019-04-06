
public final class Constants {
	
	public static final String doctorRole = "D";
	public static final String patientRole = "P";
	public static final String registrationStaffRole = "R";
	
	public static final String validatePatient = "select * from Patient where PatientID = ?";
	public static final String validateDoctor = "select * from Staff where StaffID = ? and JobTitle = 'Doctor'";
	public static final String validateRegistrationStaff = "select * from Staff where StaffID = ? and JobTitle = 'RegistrationStaff'";
	
	public static final String createStaff = "insert into Staff (Name, Address, DOB, ProfTitle, Phone, Gender, JobTitle, Dept) values(?,?,?,?,?,?,?,?)";
	public static final String createPatient ="insert into Patient(Name, DOB, Address, Gender, SSN, TreatmentStatus, StaffID) values(?,?,?,?,?,?,?)";
	public static final String createWard ="insert into Ward (Capacity,Charges) values (?, ?)";
	public static final String createBed = "insert into Bed(BedID, WardNo, Status) values (?,?,?)";
}
