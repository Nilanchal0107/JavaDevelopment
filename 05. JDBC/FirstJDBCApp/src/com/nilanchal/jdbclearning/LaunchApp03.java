package com.nilanchal.jdbclearning;
import java.sql.*;
public class LaunchApp03 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		//Load and Register the Driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Establish the connection

		String url="jdbc:mysql://localhost:3306/jdbclearning";
		String user="root";
		String password="pass@12323";
		Connection connect = DriverManager.getConnection(url, user, password);

		//Creating Statement
		Statement statement = connect.createStatement();

		//Execute query
		String sql ="select * FROM studentinfo";
	    ResultSet rs = statement.executeQuery(sql);

		//Process the Result
	    while(rs.next())
	    {
	    	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
	    }

		//Close the resources
	    rs.close();
		statement.close();
		connect.close();
	}

}
