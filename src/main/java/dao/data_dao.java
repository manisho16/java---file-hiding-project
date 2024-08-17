package dao;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import model.data;
import com.mysql.cj.MysqlConnection;
import data.my_connection;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class data_dao {

    //making a getfiles function to return a list of data of all the files the user has
    //by using the unique email of the user
    public static List<data> getfiles(String email) throws SQLException
    {
        //from the my_connection class we get the conenction established
        Connection connection= my_connection.getConnection();
        String command="SELECT * FROM DATA WHERE EMAIL=?";
        try(PreparedStatement ps= connection.prepareStatement(command)){
            ps.setString(1,email);//setting 1st ? with email;
            ResultSet rs=ps.executeQuery();
            //making a new list data struture
            List<data> files=new ArrayList<>();
            while(rs.next()){
                int id=rs.getInt(1);
                String file_name= rs.getString(2);
                String path=rs.getString(3);
                files.add(new data(id, file_name, path));
            }
            //returns the file data
            return files;
        }
    }

    public static int hidefiles(data files) throws SQLException, FileNotFoundException, IOException {
        File fl = new File(files.getPath());
        if (!fl.exists()) {
            throw new FileNotFoundException("File does not exist: " + fl.getAbsolutePath());
        }
        if (!fl.canRead()) {
            throw new IOException("Access denied to file: " + fl.getAbsolutePath());
        }

        Connection connection=my_connection.getConnection();
        String command="INSERT INTO DATA (file_name,path,email,bin_data) VALUES (?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(command)){
            ps.setString(1,files.getFILE_NAME());
            ps.setString(2,files.getPath());
            ps.setString(3,files.getEmail());

            //we make a new file variable to store the file path
            File f= new File(files.getPath());
            //then using file reader we read the file path
            FileReader fr = new FileReader(f);
            ps.setCharacterStream(4,fr,f.length());
            int ans=ps.executeUpdate();
            fr.close();
            f.delete();
            return ans;
        }
    }

    public static void unhide(int id) throws SQLException, IOException{
        Connection connection=my_connection.getConnection();
        String command="SELECT path, bin_data FROM DATA WHERE ?";
        PreparedStatement ps=connection.prepareStatement(command);
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        rs.next();
        //coloumn level->name of the column
        String path=rs.getString("path");
        Clob c=rs.getClob("bin_data");

        //we will read each character from the clob bin data
        Reader r=c.getCharacterStream();
        FileWriter fw= new FileWriter(path);
        int i;
        while( (i=r.read()) !=-1){
            fw.write( (char)i);
        }
        fw.close();
        String command2="DELETE FROM data WHERE ID=?";
        ps=connection.prepareStatement(command2);
        ps.setInt(1,id);
        ps.executeUpdate();
        System.out.println("successfully unhidden files");
    }
}
