
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
	public static final String deletePatient = "DELETE FROM Patient WHERE PatientID = ?";
	public static final String deleteStaff = "DELETE FROM Staff WHERE StaffID = ?";
	public static final String reserveBed = "UPDATE Bed SET Status= CASE WHEN Status='Y' THEN 'N' ELSE Status END Where WardNo = ? AND BedID = ?";
	public static final String releaseBed = "UPDATE Bed SET Status= CASE WHEN Status='N' THEN 'Y' ELSE Status END Where WardNo = ? AND BedID = ?";
	public static final String assignBed = "INSERT INTO CheckIn (StaffID, PatientID, StartDate, WardNo, BedID) VALUES (?,?,?,?,?,?)";
	
	public static final String createBillingRecord = "INSERT INTO BillingRecord(VisitDate, StaffID, PatientID, MedicalRecordID, PaymentMethod, CardNumber, Fees, PayeeSSN, BillingAddress) VALUES (CURDATE(),?,?,?,?,?,?,?,?)";
	public static final String createMedicalRecord = "INSERT INTO MedicalRecord(StartDate, StaffID, PatientID) values (CURDATE(),?,?)";
	
	public static final String checkBedAvailabilityBasedOnBedType = "Select Count(B.Status) AS bedsAvailable FROM Ward W INNER JOIN Bed B ON W.WardNO = B.WardNO Where B.Status='Y' AND W.Capacity = ?";
	public static final String checkBedAvailabilityBasedWardNo = "Select Count(B.Status) AS bedsAvailable  FROM Ward W INNER JOIN Bed  B ON W.WardNO = B.WardNO Where B.Status='Y' AND W.WardNO = ?";
	public static final String getWardUsagePercentage = "select sum(w.Occupied) / sum(w.capacity) from ( select WardNo , count(BedID) as capacity , SUM(CASE WHEN status = 'Y' THEN 1 ELSE 0 END) as Available ,  SUM(CASE WHEN status = 'N' THEN 1 ELSE 0 END) as Occupied  from Bed group by WardNo ) as w";
	public static final String getActivePatientForDoctor = "select P.PatientID, P.Name, P.DOB, P.Address,P.Gender, P.SSN, P.TreatmentStatus from Patient P INNER JOIN MedicalRecord MR ON P.PatientID= MR.PatientID Where MR.StaffID = ? AND (MR.EndDate IS NULL OR MR.EndDate >CURDATE())";
	public static final String getAllStaff = "Select * from Staff ORDER BY JobTitle";
}
