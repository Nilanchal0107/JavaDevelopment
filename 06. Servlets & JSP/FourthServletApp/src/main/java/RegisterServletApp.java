import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Register")
public class RegisterServletApp extends HttpServlet {
	
	public RegisterServletApp()
	{
		System.out.println("Servlet obj is created interally by container");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		System.out.println("Control in servlet/controller");
		
		String name = request.getParameter("uname");
		String ucity = request.getParameter("ucity");
		System.out.println("Name " + name + " City " + ucity);
		
		// PrintWriter writer = response.getWriter();
		
		response.sendRedirect("/FourthServletApp/success.jsp");
	
//		writer.println("<html> <head> <title> Second App</title></head>");
//		writer.println("<body bgcolor='cyan'> <h1><marquee> Welcome to our dynamic app </marquee></h1>");
//		writer.println("<table>");
//		writer.println("<tr> <th>NAME</th>   <th>CITY</th> </tr>");
//		writer.println("<tr> <td> "+name+"</td> <td> "+ucity+" </td> </tr>");
//		
//		writer.println("</table></body>");
//		
//		writer.println("</html>");
//		
//		writer.close();
	}
}
