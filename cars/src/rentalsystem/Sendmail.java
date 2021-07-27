package rentalsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mysql.jdbc.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

//Java program to send email

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


 

@WebServlet("/Sendmail")
public class Sendmail extends HttpServlet{
 

 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	
        HttpSession session=request.getSession(false);  
        String n=(String)session.getAttribute("email");  
     //  out.print("Hello "+n);  
            
       HttpSession session3=request.getSession(false);  
       String resid=(String)session3.getAttribute("resid");  
    //out.print("   "+resid);  
           
     
       
       //code to send email
       final String username = "admin@fast.com";//your email id
       final String password = "iloveBMW";// your password
       Properties props = new Properties();
       props.put("mail.smtp.auth", true);
       props.put("mail.smtp.starttls.enable", true);
       props.put("mail.smtp.host", "smtp.gmail.com");
       props.put("mail.smtp.port", "587");
       Session session1 = Session.getInstance(props,
               new javax.mail.Authenticator() {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });
       try {
           Message message = new MimeMessage(session1);
           String email=n;
		message.setFrom(new InternetAddress(email));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
           MimeBodyPart textPart = new MimeBodyPart();
           Multipart multipart = new MimeMultipart();
           String subject="Reservation id from FAST motors";
		
		String final_Text = "Greetings from FAST motors, Your Reservation id is "+resid+" .Kindly bring your credit card while picking car from us. Regards, Fast Motors Web team";
           textPart.setText(final_Text);
           message.setSubject(subject);
           multipart.addBodyPart(textPart);
           message.setContent(multipart);
           message.setSubject("Reservation id from FAST motors");
           //out.println("Sending");
           Transport.send(message);
           boolean bln = true;
           if(bln) {
        		  out.println("<br><b><p>Your reservation ID is sent to your mail. Thank you for having bussiness with us.</p></b>");
                  RequestDispatcher rd=request.getRequestDispatcher("Home.html");
                  rd.include(request,response);
           }
       } catch (Exception e) {
           System.out.println(e);
       }
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
	
}
 	
}