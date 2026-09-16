package com.nilanchal.jdbclearning;
import java.sql.*;
import java.util.Scanner;
public class LaunchApp09 {

	public static void main(String[] args)
	{
		Connection connect = null;
		PreparedStatement prestatement = null;

		try
			{
				connect = jdbcUtil.getConnection();

				//Execute query
				String query ="DELETE FROM studentinfo WHERE id=?";
				prestatement = connect.prepareStatement(query);

				System.out.println("Please enter info that needs to be deleted");
	            Scanner scan = new Scanner(System.in);

	            System.out.println("Kindly enter your id");
	            Integer id = scan.nextInt();

	            // Set parameters for the Prepared Statement
	            prestatement.setInt(1, id);

	            int rowAffected = prestatement.executeUpdate();


	          //Process the Result
	    		if (rowAffected == 0)
	    		{
	    			System.out.println("Unable to delete data");
	    		}
	    		else
	    		{
	    			System.out.println("Data deleted Successfully");
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
