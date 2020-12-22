package com.revature.reimbursment_server.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.reimbursment_server.test.DAO_Test;

public class DatabaseConnection 
{
	public static final String DRIVER = "org.postgresql.Driver";
	
	private DatabaseConnection() 
	{
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		//For compatibility with other technologies/frameworks will need to register our Driver
		try 
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		String url;
		String username;
		String password;
		if (DAO_Test.isTestMode())
		{
			url = "jdbc:postgresql://localhost:5432/shield";
			username = "postgres";
			password = "Gokuisntgoku25!";
		}
		else
		{
			url = "jdbc:postgresql://localhost:5432/postgres";
			username = "postgres";
			password = "Gokuisntgoku25!";
		}

		return DriverManager.getConnection(url, username, password);
	}
}