import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("Control in Register Servlet");
		
		String uname = request.getParameter("uname");
		String email = request.getParameter("email");
		String upassword = request.getParameter("upassword");
		String ucity = request.getParameter("ucity");
		
		String url = "jdbc:mysql://localhost:3306/javadevelopment";
		String user = "root";
		String password = "pass@12323";
		String sql = "INSERT INTO personalinfo (uname, email, upassword, ucity) VALUES (?, ?, ?, ?)";

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			return;
		}

		// try-with-resources closes pstmnt and connect even if an exception is thrown
		try (Connection connect = DriverManager.getConnection(url, user, password);
			 PreparedStatement pstmnt = connect.prepareStatement(sql))
		{
			pstmnt.setString(1,  uname);
			pstmnt.setString(2,  email);
			pstmnt.setString(3,  upassword);
			pstmnt.setString(4,  ucity);
			
			int RowAffected = pstmnt.executeUpdate();
			
			PrintWriter writer = response.getWriter();
			
			if(RowAffected!=0)
			{
				writer.println("<h1>Registration Success! </h1>");
			}
			else
			{
				writer.println("<h1>Registration Failed </h1>");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}

}
