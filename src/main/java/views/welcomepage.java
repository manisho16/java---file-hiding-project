package views;

import dao.user_dao;
import model.user;
import service.generate_otp;
import service.sendotpservice;
import service.userservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

public class welcomepage {

    //making a function to call the welcome screen
    public void welcomeScreen(){
        //we make a buffer reader to store the give input by the user
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("welcome to the app user");
        System.out.println("press 1 to login");
        System.out.println("press 2 to signup");
        System.out.println("press 0 to exit");

        //we make a default value of users choice
        int choice=0;
        try{
            //converting the userinput to integer value
            choice=Integer.parseInt(br.readLine());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        //we make choice switch case
        switch(choice){
            case 1: testlogin();
                    break;
            case 2: signup();
                    break;
            case 0: System.out.println("exited the operations");
                    System.exit(0);
            default:System.out.println("enter valid entry");
                    break;
        }


    }

    private void testlogin() {
        Scanner sc= new Scanner(System.in);
        //for login, we are taking email of the user
        String email="mrmongo03@gmail.com";
        System.out.println("email -> "+email);
        try{
            //if the user exists in the database
            // then we generate the otp and send otp to the users mail
//            if(user_dao.isExist(email)){
//                String otp= generate_otp.getopt();
//                sendotpservice.sendotp(email,otp);
//                //we ask the user fot his generated otp
//                System.out.println("enter the otp");
//                String user_otp=sc.nextLine();
//                //we check if the generated otp and entered otp are same or not
//                if(user_otp.equals(otp)){
//                    //making a new object of our user dashbord
//                    //where we pass our unique email of the user
                    new user_dash(email).user_screen();
//                }else{
//                    System.out.println("wrong otp");
//                }
//            }
//            else{
//                System.out.println("user not found");
//            }
        }catch(SQLException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        Scanner sc= new Scanner(System.in);
        //for login, we are taking email of the user
        System.out.println("enter the email");
        String email=sc.nextLine();
        try{
            //if the user exists in the database
            // then we generate the otp and send otp to the users mail
            if(user_dao.isExist(email)){
                String otp= generate_otp.getopt();
                sendotpservice.sendotp(email,otp);
                //we ask the user fot his generated otp
                System.out.println("enter the otp");
                String user_otp=sc.nextLine();
                //we check if the generated otp and entered otp are same or not
                if(user_otp.equals(otp)){
                    //making a new object of our user dashbord
                    //where we pass our unique email of the user
                    new user_dash(email).user_screen();
                }else{
                    System.out.println("wrong otp");
                }
            }
            else{
                System.out.println("user not found");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void signup() {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter your name");
        String name=sc.nextLine();
        System.out.println("enter your email");
        String email=sc.nextLine();
        String otp=generate_otp.getopt();

        sendotpservice.sendotp(email,otp);
        //we check if the generated otp and entered otp are same or not
        boolean is_valid_otp=false;
        for(int i=3;i>0;i--) {
            System.out.println("enter the otp");
            String user_otp = sc.nextLine();
            if (otp.equals(user_otp)) {
                is_valid_otp = true;
                break;
            } else {
                System.out.println("Wrong OTP. " + (i - 1) + " chances left.");
            }
        }
        if(is_valid_otp) {
            //making a new user object and passing name and email to it
            user new_user = new user(name, email);
            Integer response = userservice.saveuser(new_user);
            if(response==null){
                System.out.println("failed to save user");
            }
            if(response!=null){
                switch (response) {
                    //if we have 0 as response that means the user already exists
                    case 0: System.out.println("user already exists");
                            break;
                    case 1: System.out.println("user registered");
                            break;
                    default: System.out.println("failed to save user");
                }
            }else{
                System.out.println("Failed to save user or no rows affected");
            }
        }
        else{
            System.out.println("registration failed");
        }
    }
}
