package rentalsystem;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String aadhar = request.getParameter("aadhar");
        String fname = request.getParameter("first");
        String lname = request.getParameter("last");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");
        if(!password.equals(confirmpassword)) {
            out.println("<br><b><p>'Password' and 'Confirm Password' fields do not match</p></b>");
            RequestDispatcher rd=request.getRequestDispatcher("Home.html");
            rd.include(request,response);
        }
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
        catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
        }
        
        try {
            
           
            // loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            
            //creating connection with the database 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","");
            
            PreparedStatement ps1 = con.prepareStatement("select * from customer where aadhar=?");
            ps1.setString(1, aadhar);
     
            ResultSet rs1 =ps1.executeQuery();
           boolean  st = rs1.next();
            if(st) {
                out.println("<p> User already exists !!! </p>");
                RequestDispatcher rd=request.getRequestDispatcher("Home.html");
                rd.include(request,response);
            }
            
            PreparedStatement ps = con.prepareStatement("insert into customer values(?,?,?,?,?,?)");
            
            ps.setString(1, aadhar);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, hashtext);
            int i = ps.executeUpdate();
            
            if(i > 0) {
                out.println("<p>You are sucessfully registered "+fname+".Now Login to Reserve car !!!</p>");
                RequestDispatcher rd=request.getRequestDispatcher("Home.html");
                rd.include(request,response);
               
            }
        
        }
        catch(Exception se) {
            se.printStackTrace();
        }
	
	
	
	}
}
