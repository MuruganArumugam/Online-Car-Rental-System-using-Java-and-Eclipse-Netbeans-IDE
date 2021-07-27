package rentalsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PickUp
 */
@WebServlet("/PickUp")
public class PickUp extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
	      PrintWriter out = response.getWriter();
          
          String resid = request.getParameter("resid");
          String licenseno = request.getParameter("licenseno");
          String name = request.getParameter("ccname");
          String  cardno = request.getParameter("ccnumber");
          String year = request.getParameter("ccyear");
          String month = request.getParameter("ccmonth");
          String cvv = request.getParameter("cvv");
		
		//out.print(resid+" "+licenseno+" "+name+" "+cardno+" "+year+" "+month+" "+cvv);
          
          
          try {
              
             
              // loading drivers for mysql
              Class.forName("com.mysql.jdbc.Driver");
              
              //creating connection with the database 
              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","");
              
         
              
              PreparedStatement ps = con.prepareStatement("UPDATE reservation SET name= ? ,cardno= ? ,year= ? ,month= ? ,cvv= ? where resid ="+resid);
              
              ps.setString(1, name);
              ps.setString(2, cardno);
              ps.setString(3, year);
              ps.setString(4, month);
              ps.setString(5, cvv);
           //   ps.setString(6, resid);
             // ps.setString(7, licenseno);
              int i = ps.executeUpdate();
              
              if(i > 0) {
                  out.println("<p>You reservation is accepted.Kindly return the car in due. Thank you for having bussiness with us</p>");
                  RequestDispatcher rd=request.getRequestDispatcher("admin.html");
                  rd.include(request,response);
                 //SUV · Truck · Sedan · Van · Coupe · Wagon · Convertible.
              }
          
          }
          catch(Exception se) {
              se.printStackTrace();
          }
  	
          
          
          
          
		
	}

}
