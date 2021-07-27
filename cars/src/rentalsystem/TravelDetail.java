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
import java.util.Date;
import java.util.Random;
import java.util.Calendar; 
 
 

 

@WebServlet("/TravelDetail")
public class TravelDetail extends HttpServlet{
 
 
     	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            

      	 
      	    int row;
      	    response.setContentType("text/html");
         
              
              HttpSession session=request.getSession(false);  
              String n=(String)session.getAttribute("email");  
           //   out.print("Hello "+n);  
                  
      
              String n1 = request.getParameter("sdate");
              String n2 = request.getParameter("edate");
      
float nod = 0;
         	 try {
         		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd");
         	 Date dateBefore = myFormat.parse(n1);
         	 Date dateAfter = myFormat.parse(n2);
         	 long difference = dateAfter.getTime() - dateBefore.getTime();
         	 float daysBetween = (difference / (1000*60*60*24));
         	// out.println("Number of Days between starting and ending date is :"+daysBetween);
         	       
                 nod =daysBetween;
                
         	            	  
         	 
            
            String carid = request.getParameter("carid");
            String pickup = request.getParameter("pickup");
            String dropoff = request.getParameter("drop");
            String licenseno = request.getParameter("licenseno");
            
      
            int resid = 10000 + new Random().nextInt(90000);
        //    out.println(carid+" "+resid);
                  
            

                
               
                // loading drivers for mysql
                Class.forName("com.mysql.jdbc.Driver");
                
                //creating connection with the database 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","");
                
          //finding the rent per day value
                String sql = "SELECT * FROM rentcar WHERE carid = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, carid);
                ResultSet rs = st.executeQuery();
                String rent = null;
                if (rs.next()) {
					rent = rs.getString(3);
                }
                float fare = Float.parseFloat(rent); 
             float totalrent = fare * nod;
             String tor = Float.toString(totalrent);
             String res =Float.toString(resid);
            // out.println("   "+tor+" "+res);
             
             //finding the customer aadhar
             String sql5 = "SELECT * FROM customer WHERE email = ?";
             PreparedStatement st5 = con.prepareStatement(sql5);
             st5.setString(1, n);
             ResultSet rs5 = st5.executeQuery();
             String aadhar = null;
             if (rs5.next()) {
					aadhar = rs5.getString(1);
             }
             
             
             
             
             //inserting the data as booked
             PreparedStatement stm3 = con.prepareStatement("update rentcar set status=?,cust_email=?,cust_aadhar=? where carid=? ");
             stm3.setString(1, "BOOKED"); stm3.setString(2,n);stm3.setString(3,aadhar);stm3.setString(4,carid);
             stm3.executeUpdate();
             
             
        
             
             
             //insert for reservation
             PreparedStatement ps = con.prepareStatement("insert into reservation values(?,?,?,?,?,?,?,?,?,?,?,?)");
             
             ps.setString(1, res);
             ps.setString(2, carid);
             ps.setString(3, tor);
             ps.setString(4, n);
             ps.setString(5, pickup);
             ps.setString(6, dropoff);
             ps.setString(7, licenseno);
             ps.setString(8, "null");
             ps.setString(9, "null");
             ps.setString(10, "null");
             ps.setString(11, "null");
             ps.setString(12, "null");
             int i = ps.executeUpdate();

             if(i > 0) {
            	 HttpSession session3=request.getSession();  
                 session3.setAttribute("resid",res); 
                 out.println("<p>Your vechicle is successfully reserved. You can pick your car from us. Bring credit card when picking the car</p>");
                 RequestDispatcher rd=request.getRequestDispatcher("car2.jsp");
                 rd.include(request,response);
                //SUV · Truck · Sedan · Van · Coupe · Wagon · Convertible.
             }
            }
         	 catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             catch(Exception se) {
                 se.printStackTrace();
             }
         	 
    	
         	 }
    	
    	
  
            
   
    	
    }

