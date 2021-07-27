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
 * Servlet implementation class AddCar
 */
@WebServlet("/AddCar")
public class AddCar extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String carid = request.getParameter("carid");
        String cartype = request.getParameter("cartype");
        String rent = request.getParameter("rent");
        String vin = request.getParameter("vin");
        String status = "NOT BOOKED";
        String cust_email = "null";
        String cust_aadhar= "null";
      
  
        
        try {
            
           
            // loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            
            //creating connection with the database 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car","root","");
            
            PreparedStatement ps1 = con.prepareStatement("select * from rentcar where carid=?");
            ps1.setString(1, carid);
     
            ResultSet rs1 =ps1.executeQuery();
           boolean  st = rs1.next();
            if(st) {
                out.println("<p> Car with that ID already exists !!! Please enter another one </p>");
                RequestDispatcher rd=request.getRequestDispatcher("admin.html");
                rd.include(request,response);
            }
            
            PreparedStatement ps = con.prepareStatement("insert into rentcar values(?,?,?,?,?,?,?)");
            
            ps.setString(1, carid);
            ps.setString(2, cartype);
            ps.setString(3, rent);
            ps.setString(4, vin);
            ps.setString(5, cust_email);
            ps.setString(6, cust_aadhar);
            ps.setString(7, status);
            int i = ps.executeUpdate();
            
            if(i > 0) {
                out.println("<p>The Car is successfully inserted !!!</p>");
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
