package com.nilanchal.demorest;

import java.util.ArrayList;
import java.util.List;

import com.nilanchal.demorest.Alien;

import java.sql.*;

public class AlienRepository 
{
	
	Connection con = null;
	
	public AlienRepository()
	{
		String url = "jdbc:mysql://localhost:3306/restdb?useSSL=false&allowPublicKeyRetrieval=true";
		String username = "root";
		String password = "pass@12323";
		
		try 
		{
			// Driver is loaded and caught safely entirely inside this block
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} 
		catch (ClassNotFoundException e) 
		{
			System.err.println("MySQL Driver class not found! Check your pom.xml dependency.");
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			System.err.println("Database connection credentials failed!");
			e.printStackTrace();
		}
	}
	
	public List<Alien> getAliens()
	{
		List<Alien> aliens = new ArrayList<>();
		String sql = "SELECT * FROM alien";
		try 
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				Alien a = new Alien();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
				
				aliens.add(a);
				
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return aliens;
	}
	
	public Alien getAlien(int id)
	{		
		String sql = "SELECT * FROM alien WHERE id = " + id;
		Alien a = new Alien();
		try 
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next())
			{
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return a;
	}

	public void create(Alien a1) {
		String sql = "INSERT INTO alien VALUES (?,?,?)";
		try 
		{
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setString(2, a1.getName());
			st.setInt(3, a1.getPoints());
			st.executeUpdate();

		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void update(Alien a1) {
		String sql = "UPDATE alien SET name = ?, points=? WHERE id=?;";
		try 
		{
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, a1.getName());
			st.setInt(2, a1.getPoints());
			st.setInt(3, a1.getId());
			st.executeUpdate();

		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		
		String sql = "DELETE FROM alien WHERE id=?;";
		try 
		{
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
}
