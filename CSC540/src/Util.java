import java.util.Calendar;
import java.util.Date;

public class Util {
    //Use method to create update query of type "update TableName set ? where ID = ?";
    public static String createUpdateStatement(String query1,String query2,String constQuery ){
        query1 = query1.substring(0, query1.length() - 1);
        //	Connector.createPreparedStatement(Constants.updateMedicine);
        String temp = constQuery.replaceFirst("\\?",query1 );
        StringBuffer buffer = new StringBuffer(temp);
        temp = buffer.reverse().toString().replaceFirst("\\?",query2);
        temp = new StringBuffer(temp).reverse().toString();;
        return temp;
    }
    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }

}
