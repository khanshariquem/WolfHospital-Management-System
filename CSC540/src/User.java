import java.sql.ResultSet;
import java.sql.SQLException;

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
		Connector.createPreparedStatement(Constants.validatePatient);
		execute(id);
		
	}
	
	private static void validateDoctor(int id) {
		Connector.createPreparedStatement(Constants.validateDoctor);
		execute(id);
	}
	
	private static void validateRegistrationStaff(int id) {
		Connector.createPreparedStatement(Constants.validateRegistrationStaff);
		execute(id);
		
	}
	
	private static void execute(int id) {
		Connector.setPreparedStatement(1, Integer.toString(id));
		ResultSet result = Connector.executePreparedQuery();
		try {
			if(result.next()) {
				name = result.getString("Name");
			}
			else
				name = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
