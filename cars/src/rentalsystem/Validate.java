package rentalsystem;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validate {
    public static boolean checkUser(String email,String password) 
    {
    	String hashtext="";
    	 try {
    	 // md object calls getInstance() method is called with algorithm SHA-1
    	 MessageDigest md = MessageDigest.getInstance("SHA-1");
    	 // digest() method is called
    	 // to calculate message digest of the input string
    	 // returned as array of byte
    	 byte[] messageDigest = md.digest(password.getBytes());
    	 // for(int i=0;i<messageDigest.length;i++)
    	 // {
    	 // System.out.print(messageDigest[i]+ " ");
    	 // }
    	 // Convert byte array into signum representation
    	 BigInteger no = new BigInteger(1, messageDigest);
    	 // System.out.println("");
    	 // System.out.println(no);
    	 // Convert message digest into hex value
    	 hashtext = no.toString(16);
    	 // System.out.println(hashtext);
    	 // Add preceding 0s to make it 32 bit
    	 while (hashtext.length() < 32) {
    	 hashtext = "0" + hashtext;
    	 }
    	 }
    	 // For specifying wrong message digest algorithms
    	 catch (NoSuchAlgorithmException e) {
    	 throw new RuntimeException(e);
    	 }
    	 
    	 //validation
        boolean st =false;
        try {

            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","");
            PreparedStatement ps = con.prepareStatement("select * from customer where email=? and password=?");
            ps.setString(1, email);
            ps.setString(2, hashtext);
            ResultSet rs =ps.executeQuery();
            st = rs.next();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return st;                 
    }   
}