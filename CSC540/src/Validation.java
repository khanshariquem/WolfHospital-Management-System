import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Validation class to incorporate the generic menthods used to validate an entry in database
 *
 */
public class Validation {
    String tableName;
    String fieldName;
    String fieldValue;
    /**
     * constructor for class Validation
     * @param tableName
     * @param fieldName
     * @param fieldValue
     */
    public Validation(String tableName, String fieldName, String fieldValue){
        this.tableName=tableName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    /**
     * This method validates the entry in the database. The function wil take the parameters from the values of attributes of class.
     * @return True if the entry is presnet in db else false
     */
    public boolean validatePresence( ){
        try{
        String selectQuery="select * from "+ this.tableName+ " where  "+   this.fieldName +" = " + this.fieldValue;
            Connector.createPreparedStatement(selectQuery);
            ResultSet rs = Connector.executePreparedQuery();
            if(rs.next() )
                return true;
        } catch (Exception e) {
           System.out.println(e.getMessage());
    }
    return false;
    }
}
