import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ServletApp")
public class ServletApp extends HttpServlet {

	protected void doPost(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		System.out.println("Control in servlet/controller");
		
		String name = request.getParameter("uname");
		String ucity = request.getParameter("ucity");
	
		if("Rohan".equals(name) && "Pune".equals(ucity))
		{
			System.out.println("Success! He is the right rohan logged in");
		}
		else
		{
			System.out.println("Diff Rohan logged in");
		}
	}
}
