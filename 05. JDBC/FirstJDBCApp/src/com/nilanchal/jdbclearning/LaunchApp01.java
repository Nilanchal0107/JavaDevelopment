package com.nilanchal.jdbclearning;
import java.sql.*;
public class LaunchApp01 {

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
		String sql ="INSERT INTO studentinfo(id, sname, sage, scity) VALUES(1, 'Nilanchal', 19, 'Kalyan')";
		int rowAffected=statement.executeUpdate(sql);

		//Process the Result
		if (rowAffected == 0)
		{
			System.out.println("Updation Failed");
		}
		else
		{
			System.out.println("Updated Successfully");
		}

		//Close the resources
		statement.close();
		connect.close();
	}

}
