package service;

import java.util.Random;

public class generate_otp {

    //method to generate otp
    public static String getopt(){
        Random random = new Random();

        //using a string method to send string of 4 digit otp
        return String.format("%04d",random.nextInt(1000));
    }
}
