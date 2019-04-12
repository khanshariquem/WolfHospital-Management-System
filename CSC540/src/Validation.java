import java.sql.ResultSet;
import java.sql.SQLException;

public class Validation {
    String tableName;
    String fieldName;
    String fieldValue;
    public Validation(String tableName, String fieldName, String fieldValue){
        this.tableName=tableName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    public boolean validatePresence( ){
        try{
        String selectQuery="select * from "+ this.tableName+ " where  "+   this.fieldName +" = " + this.fieldValue;
            Connector.createPreparedStatement(selectQuery);

            ResultSet rs = Connector.executePreparedQuery();
            System.out.println(rs.toString());
            if(rs.next() )
                return true;




        } catch (SQLException e) {
        //todo
            e.printStackTrace(System.out);
    }
    return false;
    }
}
