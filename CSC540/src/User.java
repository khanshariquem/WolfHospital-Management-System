import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	public static String name;
	public static String id;
	
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
		try {
			Connector.createPreparedStatement(Constants.validatePatient);
			execute(id);
		}
		catch(SQLException e) {
			//todo
		}
		
	}
	
	private static void validateDoctor(int id) {
		try {
			Connector.createPreparedStatement(Constants.validateDoctor);
			execute(id);
		}
		catch(SQLException e) {
			//todo
		}
	}
	
	private static void validateRegistrationStaff(int id) {
		try {
			Connector.createPreparedStatement(Constants.validateRegistrationStaff);
			execute(id);
		}
		catch(SQLException e) {
			//todo
		}
		
	}
	
	private static void execute(int id) {
		try {
			Connector.setPreparedStatement(1, Integer.toString(id));
			ResultSet result = Connector.executePreparedQuery();
				if(result.next()) {
					name = result.getString("Name");
					User.id  = Integer.toString(id);
				}
				else
					name = null;
		} catch (SQLException e) {
			//todo
		}
	}
	
	
}
