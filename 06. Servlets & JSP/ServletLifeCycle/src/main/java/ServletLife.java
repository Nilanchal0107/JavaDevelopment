import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("")
public class ServletLife extends HttpServlet {
	
	static
	{
		System.out.println("Servlet is loaded...");
	}
	
	public ServletLife()
	{
		System.out.println("Servlet object is created");
	}
	
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		System.out.println("Servlet initialized!");
	}

	public void destroy()
	{
		System.out.println("Servlet destroyed!");
	}
 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Service method to handle http request and to response back");
	}

}
