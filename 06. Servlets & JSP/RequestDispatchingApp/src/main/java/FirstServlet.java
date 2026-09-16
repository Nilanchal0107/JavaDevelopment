import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("Control in first Servlet");
		String name = request.getParameter("uname");
		String city = request.getParameter("ucity");
		
		RequestDispatcher reqDispatch = request.getRequestDispatcher("/SecondServlet");
		
		HttpSession session = request.getSession();
		session.setAttribute("name", name);
		session.setAttribute("city", city);
	 // session.setMaxInactiveInterval(20000);
		
		// forward() commits and closes the response, so FirstServlet must not write anything after it.
		// To show output from both servlets, write here first and use include() instead of forward().
		reqDispatch.forward(request, response);
     // reqDispatch.include(request, response);
	}
}
