package dao;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import data.my_connection;
import model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user_dao {
    //dao -> data access object


    //we are making a boolean method to check if our email exists or not
    //by passing the email in the method ti check its existance
    public static boolean isExist(String email) throws SQLException
    {
        //getting connection from the file we made earlier with name my_connection
        Connection connection=my_connection.getConnection();

        //using prepared statement to make a query
        PreparedStatement ps= connection.prepareStatement("select email from users");

        //will give us a list of the results
        ResultSet rs=ps.executeQuery();

        while(rs.next()) {
            //we get the email values in 'e'
            String e = rs.getString(1);

            //and check if the email we want exists in the database or not
            if (e.equals(email))
                return true;
        }

        return false;
    }

    //
    public static int saveuser(user USER ) throws SQLException{
        Connection connection= my_connection.getConnection();
        String command="INSERT INTO users (name, email) VALUES (?, ?)";
        try(PreparedStatement ps=connection.prepareStatement(command)){

        ps.setString(1,USER.getName());
        ps.setString(2,USER.getEmail());
        return ps.executeUpdate(); // it returns the no of rows affected by the query executed by us
        }
    }
}
