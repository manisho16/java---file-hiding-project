package service;

import dao.user_dao;
import model.user;

import java.sql.SQLException;

public class userservice {
    //this is used to saving the user in the database
    //user the signup page

    public static Integer saveuser(user USER){
        try{
            if(user_dao.isExist(USER.getEmail())){
                return 0;
                //we return 0 if the user exists with in the database
            }else{
                //else we return the no of queries affected by us
                return user_dao.saveuser(USER);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }
}
