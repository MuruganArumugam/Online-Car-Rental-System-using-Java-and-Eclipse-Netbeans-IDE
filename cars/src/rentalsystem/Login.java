package rentalsystem;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;


@WebServlet("/Login")
public class Login extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        if(email.equals("admin@fast.com") && pass.equals("admin")) {
        	 RequestDispatcher rs = request.getRequestDispatcher("admin.html");
             rs.forward(request, response);
        }else if(Validate.checkUser(email, pass))
        {	    HttpSession session=request.getSession();  
        session.setAttribute("email",email);  
            RequestDispatcher rs = request.getRequestDispatcher("car.jsp");
            rs.forward(request, response);
        }
        else
        {
        	  out.println("<br><b><p>Email or Password is incorrect</p></b>");
              RequestDispatcher rd=request.getRequestDispatcher("Home.html");
              rd.include(request,response);
        }

    }  
}