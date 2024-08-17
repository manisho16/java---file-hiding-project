package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//javax.mail provides us classes for creating and sending email messages

import java.util.*;

public class sendotpservice {

    public static void sendotp(String email, String otp){

        // making 'to' for the destination of the otp
        String to = email;

        //making 'from' the source of the otp
        String from = "manishmamgai.1312@gmail.com";

        //host is the SMTP server address
        //also known as Simple Mail Transfer Protocol
        //it handles outgoing email messages
        String host="smtp.gmail.com";

        //properties hold the configuration details for our mail server
        Properties propertie =System.getProperties();
        //now we change the smtp properties as per our requirements
        propertie.put("mail.smtp.host",host);//specifies the smtp server
        propertie.put("mail.smtp.port","465");//species the port no, 465 fot ssl/tls
        propertie.put("mail.smtp.ssl.enable","true");//enabling ssl encryption
        propertie.put("mail.smtp.auth","true");//making authentication a requirement

        //now we create session
        Session session = Session.getInstance(propertie,new javax.mail.Authenticator(){
            //authenticator provide the email credentials which should be filled in the from and password field
            protected PasswordAuthentication getPasswordAuthentication() {
                //a dummy password is used to access my google account
                return new PasswordAuthentication(from,"yqfc dzwa djeb dfto");
            }
        });
        session.setDebug(true);

        try{
            //creating a default message
            MimeMessage message= new MimeMessage(session);

            //setting the from for the message: header file
            message.setFrom(new InternetAddress(from));

            //then we set he to for the message:
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding the subject of the message
            message.setSubject("file encrypt otp");

            //setting the message of the email we have sent while generating otp
            message.setText("your otp ofr file encryption is "+ otp);

            System.out.println("sending.........");

            //sending the message
            Transport.send(message);
            System.out.println("message send successfully...");
        }
        catch(MessagingException mex ){
            System.err.println("Failed to send OTP email: " + mex.getMessage());
            mex.printStackTrace();
        }
    }
}
