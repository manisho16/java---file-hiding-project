package views;

import dao.data_dao;
import model.data;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class user_dash {
    //making a dashboard with options for our loggined user
    private String email;
    user_dash(String email){
        this.email=email;
    }
    public void user_screen() throws SQLException, IOException {
        //this function provides us with option
        do{
            System.out.println("welcome "+ this.email);
            System.out.println("1-> to show hidden files");
            System.out.println("2-> to hide new files");
            System.out.println("3-> to unhide files");
            System.out.println("0-> to exit ");
            Scanner sc= new Scanner(System.in);
            int ch = -1;
            try{
                ch=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException ex){
                ex.printStackTrace();
            }

            switch(ch){
                case 1->{
                    //to show the hidden files
                    List<data> files = data_dao.getfiles(this.email);
                    System.out.println("id\tfilename");
                    for(data f:files){
                        System.out.println(f.getId()+"\t"+f.getFILE_NAME());
                    }
                }

                case 2->{
                    //to hide files
                    System.out.println("enter the file path");
                    String path=sc.nextLine();
                    File f=new File(path);
                    data file=new data(0,f.getName(),path,this.email);
                    try{
                        data_dao.hidefiles(file);
                    }
                    catch (FileNotFoundException e) {
                        if (e.getMessage().contains("Access is denied")) {
                            System.err.println("Error: Access to the folder is denied. Please check your permissions.");
                            System.out.println("chose your option again");
                        } else {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3->{
                    //to unhide the file
                    //to show the hidden files
                    List<data> files = data_dao.getfiles(this.email);
                    System.out.println("id\tfilename");
                    for(data f:files){
                        System.out.println(f.getId()+"\t"+f.getFILE_NAME());
                    }

                    System.out.println("enter the file id to unhide");
                    int id=Integer.parseInt(String.valueOf(sc.nextInt()));
                    boolean isvalid=false;
                    for(data f:files){
                        if(f.getId()==id){
                            isvalid=true;
                            break;
                        }
                    }
                    if(isvalid){
                        data_dao.unhide(id);
                    }else{
                        System.err.println("wrong id entered");
                    }

                }
                case 0-> {
                    System.err.println("exited the operations");
                    System.exit(0);
                }
                default -> {
                    System.err.println("enter a valid option");
                }
            }
        }while(true);
    }
}
