package com.revature.reimbursment_server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.reimbursment_server.exceptions.DataAccessException;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.utilities.DatabaseConnection;
import com.revature.reimbursment_server.utilities.Queries;

public class UsersDAO
{
	private static Logger log = Logger.getLogger(UsersDAO.class);
	
	public void clearUsers() throws DataAccessException
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.TRUNCATE_USERS;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);

			boolean isSuccess = preparedStatement.execute();
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new DataAccessException("A failed to execute query on database.");
		}
		finally
		{
			try 
			{
				if (connection != null)
					connection.close();
			} 
			catch (SQLException e)
			{
				log.error(e.getMessage());
				throw new DataAccessException("Failed to close connection.");
			}
		}
	}
	
	public User getUserByID(int id) throws DataAccessException
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_USER_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				return new User(resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						User.Role.values()[resultSet.getInt("role_id") - 1]);
			}
		} 
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new DataAccessException("A failed to execute query on database.");
		}
		finally
		{
			try 
			{
				if (connection != null)
					connection.close();
			} 
			catch (SQLException e)
			{
				log.error(e.getMessage());
				throw new DataAccessException("Failed to close connection.");
			}
		}
		return null;
	}
	
	public User getUserByUsername(String username) throws DataAccessException
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_USER_BY_USERNAME;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				return new User(resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						User.Role.values()[resultSet.getInt("role_id") - 1]);
			}
		} 
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new DataAccessException("A failed to execute query on database.");
		}
		finally
		{
			try 
			{
				if (connection != null)
					connection.close();
			} 
			catch (SQLException e)
			{
				log.error(e.getMessage());
				throw new DataAccessException("Failed to close connection.");
			}
		}
		return null;
	}
	
	public User getUserByEmail(String email) throws DataAccessException
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_USER_BY_EMAIL;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				return new User(resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						User.Role.values()[resultSet.getInt("role_id") - 1]);
			}
		} 
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new DataAccessException("A failed to execute query on database.");
		}
		finally
		{
			try 
			{
				if (connection != null)
					connection.close();
			} 
			catch (SQLException e)
			{
				log.error(e.getMessage());
				throw new DataAccessException("Failed to close connection.");
			}
		}
		return null;
	}
	
	//public boolean createUser(User user)
	//{
	//	return false;
	//}
}
