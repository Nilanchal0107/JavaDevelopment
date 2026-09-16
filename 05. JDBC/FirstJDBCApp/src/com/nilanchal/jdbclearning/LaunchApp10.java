package com.nilanchal.jdbclearning;
import java.sql.*;
import java.util.Scanner;
public class LaunchApp10 {

	public static void main(String[] args)
	{
		Connection connect = null;
		PreparedStatement prestatement = null;
		ResultSet rs = null;

		try
			{
				connect = jdbcUtil.getConnection();

				//Execute query
				String query ="SELECT * FROM studentinfo WHERE id=?";
				prestatement = connect.prepareStatement(query);

	            Scanner scan = new Scanner(System.in);

	            System.out.println("Kindly enter your id for which Data to be Retrived: ");
	            Integer id = scan.nextInt();

	            // Set parameters for the Prepared Statement
	            prestatement.setInt(1, id);

	            rs= prestatement.executeQuery();


	          //Process the Result
	    		if (rs.next())
	    		{
	    	    	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
	    		}
	    		else
	    		{
	    			System.out.println("There is no record with id = " + id);
	    		}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//Close the resources
			try
			{
				jdbcUtil.closeConnection(connect, prestatement);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
