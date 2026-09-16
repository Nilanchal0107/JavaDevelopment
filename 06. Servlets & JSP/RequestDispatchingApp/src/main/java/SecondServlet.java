
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SecondServlet")
public class SecondServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		System.out.println("Control in Second Servlet");
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		
		PrintWriter writer = response.getWriter();
		writer.println("<h1>Response from Second Servlet </h1>" + "<h1>" + name + "</h1>");
		writer.close();
	}

}

