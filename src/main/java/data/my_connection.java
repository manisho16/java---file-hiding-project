package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class my_connection {
    public static Connection connection ;//making a global static connection

    //making a function to have the connection setup
    public static Connection getConnection(){
        // to load a class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// we write the fully classified name of the class
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/file_hiding?useSSL=False","root","password");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("connection successful");
        return connection;
    }

    //fucntion to close the connection
    public static void CloseConnection(){
        if(connection!=null){
            try{
                connection.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
