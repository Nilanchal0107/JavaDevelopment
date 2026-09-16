package main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Model 
{
	private String uname;
	private String upassword;
	private String email;
	private String ucity;
	private Connection connect = null;
	private PreparedStatement pstmnt = null;
	private int row = 0;
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUcity() {
		return ucity;
	}
	public void setUcity(String ucity) {
		this.ucity = ucity;
	}
	
	public int register()
	{
		try 
		{
			connect = JdbcUtil.getDBConnection();
			String sql = "INSERT INTO personalinfo (uname, email, upassword, ucity) VALUES(?, ?, ?, ?)";
			pstmnt = connect.prepareStatement(sql);
			pstmnt.setString(1, uname);
			pstmnt.setString(2, email);
			pstmnt.setString(3, upassword);
			pstmnt.setString(4, ucity);
			
			row = pstmnt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				JdbcUtil.closeResource(connect, pstmnt);
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return row;
	}
}
