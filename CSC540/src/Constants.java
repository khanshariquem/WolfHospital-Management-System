
public final class Constants {
	
	public static final String doctorRole = "D";
	public static final String patientRole = "P";
	public static final String registrationStaffRole = "R";
	public static final String checkStaff = "select * from Staff where StaffId = ?";
	public static final String validatePatient = "select * from Patient where PatientID = ?";
	public static final String validateWard = "select * from Ward where WardNo = ?";
	public static final String checkBeds = "select * from Bed where WardNo = ? and Status = 'N'";
	public static final String validateDoctor = "select * from Staff where StaffID = ? and JobTitle = 'Doctor'";
	public static final String validateRegistrationStaff = "select * from Staff where StaffID = ? and JobTitle <> 'Doctor' and JobTitle <> 'Nurse' ";
	public static final String validateNurse = "select * from Staff where StaffID = ? and JobTitle = 'Nurse'";
	
	public static final String createStaff = "insert into Staff (Name, Address, DOB, ProfTitle, Phone, Gender, JobTitle, Dept) values(?,?,?,?,?,?,?,?)";
	public static final String createPatient ="insert into Patient(Name, DOB, Address, Gender, Phone, SSN, Processing_Treatment_Plan, Completing_Treatment, StaffID) values(?,?,?,?,?,?,?,?,?)";
	
	public static final String createWard ="insert into Ward (Capacity,Charges,StaffID) values (?,?,?)";
	public static final String createBed = "insert into Bed(BedID, WardNo, Status) values (?,?,?)";
	public static final String deletePatient = "DELETE FROM Patient WHERE PatientID = ?";
	public static final String deleteBeds = "DELETE FROM Bed WHERE WardNo = ?";
	public static final String deleteWard = "DELETE FROM Ward WHERE WardNo = ?";
	public static final String deleteStaff = "DELETE FROM Staff WHERE StaffID = ?";
	public static final String reserveBed = "UPDATE Bed SET Status= CASE WHEN Status='Y' THEN 'N' ELSE Status END Where WardNo = ? AND BedID = ?";
	public static final String releaseBed = "UPDATE Bed SET Status= CASE WHEN Status='N' THEN 'Y' ELSE Status END Where WardNo = ? AND BedID = ?";
	public static final String assignBed = "INSERT INTO CheckIn (StaffID, PatientID, StartDate, WardNo, BedID) VALUES (?,?,?,?,?,?)";
	
	public static final String createBillingRecord = "INSERT INTO BillingRecord(VisitDate, StaffID, PatientID, MedicalRecordID, PaymentMethod, CardNumber, Fees, PayeeSSN, BillingAddress) VALUES (CURDATE(),?,?,?,?,?,?,?,?)";
	public static final String createMedicalRecord = "INSERT INTO MedicalRecord(StartDate, StaffID, PatientID) values (CURDATE(),?,?)";
	
	public static final String checkBedAvailabilityBasedOnBedType = "Select Count(B.Status) AS bedsAvailable FROM Ward W INNER JOIN Bed B ON W.WardNO = B.WardNO Where B.Status='Y' AND W.Capacity = ?";
	public static final String checkBedAvailabilityBasedWardNo = "Select Count(B.Status) AS bedsAvailable  FROM Ward W INNER JOIN Bed  B ON W.WardNO = B.WardNO Where B.Status='Y' AND W.WardNO = ?";
	public static final String getWardUsagePercentage = "select sum(w.Occupied) / sum(w.capacity) from ( select WardNo , count(BedID) as capacity , SUM(CASE WHEN status = 'Y' THEN 1 ELSE 0 END) as Available ,  SUM(CASE WHEN status = 'N' THEN 1 ELSE 0 END) as Occupied  from Bed group by WardNo ) as w";
	public static final String getBedUsage = "select WardNo, count(BedID) as capacity, SUM(CASE WHEN status = 'Y' THEN 1 ELSE 0 END) as Available , SUM(CASE WHEN status = 'N' THEN 1 ELSE 0 END) as Occupied from Bed group by WardNo";
	public static final String getActivePatientForDoctor = " select P.PatientID, P.Name, P.DOB, P.Address, P.Phone, P.Gender, P.SSN, P.Processing_Treatment_Plan , P.Completing_Treatment from Patient P INNER JOIN MedicalRecord MR ON P.PatientID= MR.PatientID Where MR.StaffID = ? AND (MR.EndDate IS NULL OR MR.EndDate >CURDATE())";
	public static final String getAllStaff = "Select * from Staff ORDER BY JobTitle";
	public static final String getPatientCount = "SELECT YEAR(VisitDate) YEAR, MONTH(VisitDate) MONTH, COUNT(*) COUNT FROM BillingRecord WHERE YEAR(VisitDate) = ? AND MONTH(VisitDate)= ? GROUP BY MONTH(VisitDate)";

	/*Viviniya Changes*/

	public static final String getMedicalRecordForPatient = " Select MR.*, " +
			"M.Name AS Medicine_Name ,M.Manufacturer AS Medicine_Manuf, T.Name As Test_Name,T.Lab As Test_Lab,T.Result As Test_Result, T.StaffID AS Test_Staff FROM MedicalRecord MR LEFT JOIN Medicine M ON MR.MedicalRecordID = M.MedicalRecordID LEFT JOIN Test T ON MR.MedicalRecordID= T.MedicalRecordID" +
			" WHERE MONTH(StartDate) = ? AND YEAR(StartDate) = ? AND PatientID = ?";
	public static final String getMedicalRecordForPatientBetween = "Select MR.*, " +
			"M.Name AS Medicine_Name ,M.Manufacturer AS Medicine_Manuf, T.Name As Test_Name,T.Lab As Test_Lab,T.Result As Test_Result, T.StaffID AS Test_Staff FROM MedicalRecord MR LEFT JOIN Medicine M ON MR.MedicalRecordID = M.MedicalRecordID LEFT JOIN Test T ON MR.MedicalRecordID= T.MedicalRecordID " +
			"WHERE MONTH(StartDate) between ? AND ? AND YEAR(StartDate) = ? AND PatientID = ?";

	public static final String createCheckIn ="INSERT INTO CheckIn ( StaffID, PatientID, StartDate, WardNo,BedID) VALUES (?,?, ?, ?,?)";
	public static final String createMedicine =" insert into Medicine (Name, Manufacturer, MedicalRecordID) values (?,?,?)";
	public static final String createTest =" insert into Test (Name, Lab, StaffID, MedicalRecordID) values (?,?,?,?)";
	public static final String updateMedicine ="update Medicine set ? where MedicalRecordID = ?";
	public static final String updateTest ="update Test set ? where MedicalRecordID = ?";
	public static final String updateMROtherField="update MedicalRecord set ? where MedicalRecordID = ?";


	public static final String updateStaffDetails ="update Staff set ? where StaffId = ?";
	public static final String updatePatientDetails ="update Patient set ? where PatientId = ?";
	public static final String updateWardDetails ="update Ward set ? where WardNo = ?";
	public static final String updateBRDetails ="update BillingRecord set ? where BillingRecordID = ?";
	public static final String updateCheckInDetails ="update CheckIn set ? where PatientID = ? and EndDate is NULL";


	/* CREATE TABLES */
	public static final String createStaffTable ="CREATE TABLE Staff ( StaffID INT NOT NULL AUTO_INCREMENT, Name VARCHAR(100) NOT NULL," +
			" Address VARCHAR(255) NOT NULL, DOB DATE NOT NULL, ProfTitle VARCHAR(100) , Phone VARCHAR(20) NOT NULL, Gender CHAR(2) NOT NULL, " +
			"JobTitle VARCHAR(30) NOT NULL, Dept VARCHAR(50) NOT NULL, PRIMARY KEY (StaffID), CONSTRAINT restrict_gender_vals CHECK (Gender IN ('M', 'F', 'NB', 'U')))";

	public static final String createPatientTable ="CREATE TABLE Patient ( PatientID INT NOT NULL AUTO_INCREMENT, " +
			"Name VARCHAR(100) NOT NULL, DOB DATE NOT NULL, Address VARCHAR(255) NOT NULL,Phone VARCHAR(20) NOT NULL, Gender CHAR(2) NOT NULL," +
			" SSN VARCHAR(20), StaffID INT NOT NULL,Processing_Treatment_Plan INT NOT NULL," +
			"Completing_Treatment CHAR(3) NOT NULL, PRIMARY KEY (PatientID)," +
			"CONSTRAINT restrict_gender_vals_patient CHECK (Gender IN ('M', 'F', 'NB', 'U')),CONSTRAINT restrict_Treatment_vals CHECK (Completing_Treatment IN ('Yes', 'No'))," +
			"CONSTRAINT FK_StaffID FOREIGN KEY (StaffID) REFERENCES Staff(StaffID) );";
	public static final String createWardTable ="CREATE TABLE Ward ( WardNo INT NOT NULL AUTO_INCREMENT, Charges INT NOT NULL, " +
			"Capacity INT NOT NULL,StaffID INT NOT NULL, PRIMARY KEY (WardNo),CONSTRAINT FK_StaffIDWard FOREIGN KEY (StaffID) REFERENCES Staff(StaffID) );";
	public static final String createBedTable ="CREATE TABLE Bed ( BedID CHAR(1) NOT NULL, WardNo INT NOT NULL, Status char(1) NOT NULL, PRIMARY KEY (BedID, WardNo), CONSTRAINT restrict_Status_vals CHECK (Status IN ('Y', 'N')), CONSTRAINT FK_WardNo FOREIGN KEY (WardNo) REFERENCES Ward(WardNo) );";
	public static final String createCheckInTable ="CREATE TABLE CheckIn ( StaffID INT NOT NULL, PatientID INT NOT NULL, StartDate DATE NOT NULL, WardNo INT NOT NULL, BedID char(1) NOT NULL, EndDate DATE, PRIMARY KEY (StaffID, PatientID, StartDate, WardNo, BedID), CONSTRAINT FK_PatientID FOREIGN KEY (PatientID) REFERENCES Patient(PatientID), CONSTRAINT FK_StaffIDCheckIn FOREIGN KEY (StaffID) REFERENCES Staff(StaffID), CONSTRAINT FK_BedID FOREIGN KEY (BedID) REFERENCES Bed(BedID), CONSTRAINT FK_WardNoCheckIn FOREIGN KEY (WardNo) REFERENCES Ward(WardNo) );";
	public static final String createMedicalRecordTable ="CREATE TABLE MedicalRecord ( MedicalRecordID INT NOT NULL AUTO_INCREMENT, StartDate DATE NOT NULL, EndDate DATE, StaffID INT NOT NULL, PatientID INT NOT NULL, PRIMARY KEY (MedicalRecordID), CONSTRAINT FK_StaffIDMR FOREIGN KEY (StaffID) REFERENCES Staff(StaffID), CONSTRAINT FK_PatientIDMR FOREIGN KEY (PatientID) REFERENCES Patient(PatientID) );";
	public static final String createBillingRecordTable ="CREATE TABLE BillingRecord ( BillingRecordID INT NOT NULL AUTO_INCREMENT, VisitDate DATE NOT NULL, StaffID INT NOT NULL, PatientID INT NOT NULL, MedicalRecordID INT NOT NULL, PRIMARY KEY (BillingRecordID), PaymentMethod VARCHAR(50), CardNumber VARCHAR(20), Fees Decimal UNSIGNED ZEROFILL NOT NULL, PayeeSSN VARCHAR(20), BillingAddress VARCHAR(200), CONSTRAINT FK_StaffIDBR FOREIGN KEY (StaffID) REFERENCES Staff(StaffID), CONSTRAINT FK_PatientIDBR FOREIGN KEY (PatientID) REFERENCES Patient(PatientID), CONSTRAINT FK_MedicalRecordID FOREIGN KEY (MedicalRecordID) REFERENCES MedicalRecord(MedicalRecordID), CONSTRAINT restrict_PaymentMethod_vals CHECK (PaymentMethod IN ('Cash', 'Card', 'Online Transfer')) );";
	public static final String createMedicineTable ="CREATE TABLE Medicine ( MedicineID INT NOT NULL AUTO_INCREMENT, Name VARCHAR(30) NOT NULL, Cost INT, Manufacturer VARCHAR(30) NOT NULL, MedicalRecordID INT NOT NULL, PRIMARY KEY (MedicineID), CONSTRAINT FK_MRIDMed FOREIGN KEY (MedicalRecordID) REFERENCES MedicalRecord(MedicalRecordID) );";
	public static final String createTestTable ="CREATE TABLE Test ( TestID INT NOT NULL AUTO_INCREMENT, Name VARCHAR(30) NOT NULL, Cost INT, Lab VARCHAR(30) NOT NULL, Result VARCHAR(255), StaffID INT NOT NULL, MedicalRecordID INT NOT NULL, PRIMARY KEY (TestID), CONSTRAINT FK_StaffIDTest FOREIGN KEY (StaffID) REFERENCES Staff(StaffID), CONSTRAINT FK_MRIDTest FOREIGN KEY (MedicalRecordID) REFERENCES MedicalRecord(MedicalRecordID) );";

	/* DROP TABLES */
	public static final String dropStaffTable ="DROP TABLE IF EXISTS Staff;";
	public static final String dropPatientTable ="DROP TABLE IF EXISTS Patient;";
	public static final String dropWardTable ="DROP TABLE IF EXISTS Ward;";
	public static final String dropBedTable ="DROP TABLE IF EXISTS Bed;";
	public static final String dropCheckInTable ="DROP TABLE IF EXISTS  CheckIn;";
	public static final String dropMedicalRecordTable ="DROP TABLE IF EXISTS MedicalRecord;";
	public static final String dropBillingRecordTable ="DROP TABLE IF EXISTS BillingRecord;";
	public static final String dropMedicineTable ="DROP TABLE IF EXISTS Medicine;";
	public static final String dropTestTable ="DROP TABLE IF EXISTS Test;";


	/* Demo Data */
	public static final String insertStaffs ="insert into Staff(StaffID, Name, Address, DOB, ProfTitle, Phone, Gender, JobTitle, Dept) values " +
			"	(100, 'Mary', '90 ABC St , Raleigh NC 27', '1989-12-17', 'senior', '654', 'F', 'Doctor', 'Neurology')," +
			" (101, 'John', '798 XYZ St , Rochester NY 54', '1976-06-01', NULL, '564', 'M', 'Billing staff', 'Office'), " +
			"(102,'Carol', '351 MH St , Greensboro NC 27', '1992-09-06', NULL, '911', 'F', 'Nurse', 'ER')," +
			" (103,'Emma', '49 ABC St , Raleigh NC 27', '1992-09-06', 'Senior surgeon', '546', 'F', 'Doctor', 'Oncological Surgery')," +
			"(104,'Ava', '425 RG St , Raleigh NC 27', '1992-09-06', NULL, '777', 'M', 'Front Desk Staff', 'Office')," +
			"(105,'Peter', '475 RG St , Raleigh NC 27', '1992-09-06', 'Anesthetist', '724', 'M', 'Doctor', 'Oncological Surgery')," +
			"(106,'Olivia', '325 PD St , Raleigh NC 27', '1992-09-06', NULL, '799', 'F', 'Nurse', 'Neurology')";
	public static final String insertPatients ="insert into Patient" +
			"(PatientID,Name, DOB, Address, Gender,Phone, SSN, Processing_Treatment_Plan,Completing_Treatment, StaffID) values " +
			"(1001,'David', '1980-01-30', '69 ABC St , Raleigh NC 27730', 'M', '919-123-3324','000-01-1234',20,  'No',104), " +
			"(1002,'Sarah', '1971-01-30', '81 DEF St , Cary NC 27519', 'F', '919-563-3478','000-02-1234',20,'No', 104)," +
			" (1003,'Joseph', '1987-01-30', '31 OPG St , Cary NC 27519', 'M','919-957-2199' ,'000-03-1234',10,'No', 102)," +
			" (1004,'Lucy', '1985-01-30', '10 TBC St , Raleigh NC 27730', 'F','919-838-7123', '000-04-1234',5,  'Yes',102);";

	public static final String insertMedicalRecords ="insert into MedicalRecord" +
			"(MedicalRecordID,StartDate, StaffID, PatientID) values" +
			"(1,'2019-03-01', 100,1001), " +
			"(2,'2019-03-10',100,1004)," +
			"(3,'2019-03-15', 100,1003)," +
			"(4,'2019-03-14' ,103,1002);";
	public static final String insertBillingRecords =	"insert into BillingRecord (VisitDate, StaffID, PatientID, MedicalRecordID, PaymentMethod, CardNumber, Fees, PayeeSSN, BillingAddress)values " +
			"('2019-03-01',101,1001,1,'Card','4044875409613234',100,'000-01-1234', '69 ABC St , Raleigh NC 27730')," +
			"('2019-03-10',102,1004,2,'Card', '4401982398541143',100, '000-02-1234', '81 DEF St , Cary NC 27519')," +
			"('2019-03-15',103,1003,3,'Cash',NULL ,100,'000-03-1234', '31 OPG St , Cary NC 27519')," +
			"('2019-03-14',104,1002,4,'Card', 4044987612349123,100,'000-04-1234','10 TBC St. Raleigh NC 27730');";
	public static final String insertWards ="insert into Ward(WardNo,Charges, Capacity,StaffID) values " +
			"(001,50, 4, 102), (002,50, 4, 102), (003,100, 2, 106), (004,100, 2, 106);";
	public static final String insertBeds ="insert into Bed(BedID, WardNo, Status) values " +
			"('A', 001, 'N'), ('B', 001, 'N'),('C', 001, 'Y'),('D', 001, 'Y'), " +
			"('A', 002, 'N'), ('B', 002, 'Y'),('C', 002, 'Y'),('D', 002, 'Y')," +
			" ('A', 003, 'N'), ('B', 003, 'N'), ('A', 004, 'Y'), ('B',004,'Y');";
	public static final String insertCheckIns ="insert into CheckIn (StaffID, PatientID, StartDate, WardNo, BedID, EndDate)" +
			" values (102 ,1001 , '2019-03-01', 001 , 'A' , NULL), " +
			"( 102 ,1002 , '2019-03-10', 002 , 'A' , NULL), " +
			"( 102 , 1003 ,'2019-03-15', 001 , 'B' , NULL), " +
			"( 106 ,1004 , '2019-03-17', 003 , 'A' , '2019-03-21');";








	public static final String selectPatient ="Select * from Patient";
	public static final String selectMedicalRecord ="Select * from MedicalRecord";
}


