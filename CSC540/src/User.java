import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to hold the current session of the user using the application
 *
 */
public class User {
	public static String name;
	public static String id;
	
	
	/**
	 * Method to validate the presence of the user in the database depedning upon the role which is selected for the user
	 * @param id
	 * @param role
	 */
	public static void validateUser(int id , String role) {
		// handle different roles - siddu
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
	
	/**
	 * Validates if the user id is present in the patient table
	 * @param id
	 */
	private static void validatePatient(int id) {
		try {
			Connector.createPreparedStatement(Constants.validatePatient);
			execute(id);
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
		
	}
	
	/**
	 *  Validates if the user id is present in the Staff table with job title Doctor
	 * @param id
	 */
	private static void validateDoctor(int id) {
		try {
			Connector.createPreparedStatement(Constants.validateDoctor);
			execute(id);
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}
	
	/**
	 * Validates if the user id is present in the Staff table with job title Registration Staff
	 * @param id
	 */
	private static void validateRegistrationStaff(int id) {
		try {
			Connector.createPreparedStatement(Constants.validateRegistrationStaff);
			execute(id);
		}
		catch(SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
		
	}
	
	/**
	 * Method to execute the prepared statement , get the user from database and set the attributes of the User class from the result
	 * @param id
	 */
	private static void execute(int id) {
		try {
			Connector.setPreparedStatementString(1, Integer.toString(id));
			ResultSet result = Connector.executePreparedQuery();
				if(result.next()) {
					name = result.getString("Name");
					User.id  = Integer.toString(id);
				}
				else
					name = null;
		} catch (SQLException e) {
			System.out.println("Error occured, try again"+e.getMessage());
		}
	}
	
	
}
