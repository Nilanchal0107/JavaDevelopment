package com.nilanchal.jdbclearning;
import java.sql.*;

public class LaunchBatch {

	public static void main(String[] args)
	{
		Connection connect = null;
		PreparedStatement prestatement = null;

		try
			{
				connect = jdbcUtil.getConnection();

				String query ="UPDATE studentinfo SET sage=? WHERE id=?";
				prestatement = connect.prepareStatement(query);

	            // Set parameters for the Prepared Statement
	            prestatement.setInt(1, 19);
	            prestatement.setInt(2, 3);
	            prestatement.addBatch();

	            prestatement.setInt(1, 17);
	            prestatement.setInt(2, 2);
	            prestatement.addBatch();

	            prestatement.setInt(1, 20);
	            prestatement.setInt(2, 3);
	            prestatement.addBatch();

	            prestatement.executeBatch();


	          //Process the Result
	    		System.out.println("Check the db");
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
