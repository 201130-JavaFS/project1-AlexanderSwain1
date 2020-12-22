package com.revature.reimbursment_server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.reimbursment_server.exceptions.DataAccessException;
import com.revature.reimbursment_server.models.Reimbursment;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.utilities.DatabaseConnection;
import com.revature.reimbursment_server.utilities.Queries;

public class ReimbursmentsDAO 
{
	private static Logger log = Logger.getLogger(ReimbursmentsDAO.class);
	
	UsersDAO userDAO = new UsersDAO();
	
	public void clearReimbursements() throws DataAccessException
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.TRUNCATE_REIMBURSEMENTS;
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
	
	public Reimbursment getReimbursment(int id) throws DataAccessException 
	{
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_REIMBURSMENT_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Timestamp resolved = resultSet.getTimestamp("resolved");
				Integer resolverValue = resultSet.getInt("resolver");
				if (resultSet.wasNull())
					resolverValue = null;
				 return new Reimbursment(resultSet.getInt("id"),
						resultSet.getLong("amount"),
						resultSet.getTimestamp("submitted").toLocalDateTime(),
						resolved != null ? resolved.toLocalDateTime() : null,
						resultSet.getString("description"),
						resultSet.getBytes("receipt"),
						userDAO.getUserByID(resultSet.getInt("author")),
						resolverValue != null ? userDAO.getUserByID(resolverValue) : null,
						Reimbursment.Status.values()[resultSet.getInt("status_id") - 1],
						Reimbursment.Type.values()[resultSet.getInt("type_id") - 1]);
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
	
	public List<Reimbursment> getReimbursments() throws DataAccessException
	{
		List<Reimbursment> result = new ArrayList<Reimbursment>();
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_ALL_REIMBURSMENTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Timestamp resolved = resultSet.getTimestamp("resolved");
				Integer resolverValue = resultSet.getInt("resolver");
				if (resultSet.wasNull())
					resolverValue = null;
				result.add(new Reimbursment(resultSet.getInt("id"),
						resultSet.getLong("amount"),
						resultSet.getTimestamp("submitted").toLocalDateTime(),
						resolved != null ? resolved.toLocalDateTime() : null,
						resultSet.getString("description"),
						resultSet.getBytes("receipt"),
						userDAO.getUserByID(resultSet.getInt("author")),
						resolverValue != null ? userDAO.getUserByID(resolverValue) : null,
						Reimbursment.Status.values()[resultSet.getInt("status_id") - 1],
						Reimbursment.Type.values()[resultSet.getInt("type_id") - 1]));
			}
			return result;
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
	public List<Reimbursment> getReimbursements(User author) throws DataAccessException
	{
		List<Reimbursment> result = new ArrayList<Reimbursment>();
		Connection connection = null;
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_ALL_REIMBURSMENTS_WITH_AUTHOR;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, author.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				Timestamp resolved = resultSet.getTimestamp("resolved");
				Integer resolverValue = resultSet.getInt("resolver");
				if (resultSet.wasNull())
					resolverValue = null;
				result.add(new Reimbursment(resultSet.getInt("id"),
						resultSet.getLong("amount"),
						resultSet.getTimestamp("submitted").toLocalDateTime(),
						resolved != null ? resolved.toLocalDateTime() : null,
						resultSet.getString("description"),
						resultSet.getBytes("receipt"),
						userDAO.getUserByID(resultSet.getInt("author")),
						resolverValue != null ? userDAO.getUserByID(resolverValue) : null,
						Reimbursment.Status.values()[resultSet.getInt("status_id") - 1],
						Reimbursment.Type.values()[resultSet.getInt("type_id") - 1]));
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
		return result;
	}
	public List<Reimbursment> getReimbursementsWithResolver(User resolver) throws DataAccessException
	{
		List<Reimbursment> result = new ArrayList<Reimbursment>();
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_ALL_REIMBURSMENTS_WITH_AUTHOR;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, resolver.getId());

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Timestamp resolved = resultSet.getTimestamp("resolved");
				Integer resolverValue = resultSet.getInt("resolver");
				if (resultSet.wasNull())
					resolverValue = null;
				result.add(new Reimbursment(resultSet.getInt("id"),
						resultSet.getLong("amount"),
						resultSet.getTimestamp("submitted").toLocalDateTime(),
						resolved != null ? resolved.toLocalDateTime() : null,
						resultSet.getString("description"),
						resultSet.getBytes("receipt"),
						userDAO.getUserByID(resultSet.getInt("author")),
						resolverValue != null ? userDAO.getUserByID(resolverValue) : null,
						Reimbursment.Status.values()[resultSet.getInt("status_id") - 1],
						Reimbursment.Type.values()[resultSet.getInt("type_id") - 1]));
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
		return result;
	}
	public List<Reimbursment> getReimbursements(Reimbursment.Status status) throws DataAccessException
	{
		List<Reimbursment> result = new ArrayList<Reimbursment>();
		Connection connection = null;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.GET_ALL_REIMBURSMENTS_WITH_STATUS;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, status.ordinal() + 1);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Timestamp resolved = resultSet.getTimestamp("resolved");
				Integer resolverValue = resultSet.getInt("resolver");
				if (resultSet.wasNull())
					resolverValue = null;
				result.add(new Reimbursment(resultSet.getInt("id"),
						resultSet.getLong("amount"),
						resultSet.getTimestamp("submitted").toLocalDateTime(),
						resolved != null ? resolved.toLocalDateTime() : null,
						resultSet.getString("description"),
						resultSet.getBytes("receipt"),
						userDAO.getUserByID(resultSet.getInt("author")),
						resolverValue != null ? userDAO.getUserByID(resolverValue) : null,
						Reimbursment.Status.values()[resultSet.getInt("status_id") - 1],
						Reimbursment.Type.values()[resultSet.getInt("type_id") - 1]));
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
		return result;
	}
	
	public void createReimbursment(Reimbursment reimbursment) throws DataAccessException
	{
		Connection connection = null;
		Reimbursment result;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.CREATE_REIMBURSMENT;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			//preparedStatement.setInt(1, reimbursment.getId());
			preparedStatement.setLong(1, reimbursment.getAmount());
			preparedStatement.setTimestamp(2,  Timestamp.valueOf(reimbursment.getSubmitted()));
			preparedStatement.setTimestamp(3, reimbursment.getResolved() != null ? Timestamp.valueOf(reimbursment.getResolved()) : null);
			preparedStatement.setString(4, reimbursment.getDescription());
			preparedStatement.setBytes(5, reimbursment.getReceipt());
			preparedStatement.setInt(6,  reimbursment.getAuthor().getId());
			if (reimbursment.getResolver() != null)
				preparedStatement.setInt(7,  reimbursment.getResolver().getId());
			else
				preparedStatement.setNull(7, Types.INTEGER);
			preparedStatement.setInt(8, reimbursment.getStatus().ordinal() + 1);
			preparedStatement.setInt(9,  reimbursment.getType().ordinal() + 1);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				reimbursment.setId(resultSet.getInt(1));
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
	
	public void updateReimbursment(Reimbursment reimbursment) throws DataAccessException
	{
		Connection connection = null;
		Reimbursment result;
		
		try
		{
			connection = DatabaseConnection.getConnection();
			String sqlCommand = Queries.UPDATE_REIMBURSMENT;
			PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
			
			preparedStatement.setLong(1, reimbursment.getAmount());
			preparedStatement.setTimestamp(2,  Timestamp.valueOf(reimbursment.getSubmitted()));
			preparedStatement.setTimestamp(3, reimbursment.getResolved() != null ? Timestamp.valueOf(reimbursment.getResolved()) : null);
			preparedStatement.setString(4, reimbursment.getDescription());
			preparedStatement.setBytes(5, reimbursment.getReceipt());
			preparedStatement.setInt(6,  reimbursment.getAuthor().getId());
			if (reimbursment.getResolver() != null)
				preparedStatement.setInt(7,  reimbursment.getResolver().getId());
			else
				preparedStatement.setNull(7, Types.INTEGER);
			preparedStatement.setInt(8, reimbursment.getStatus().ordinal() + 1);
			preparedStatement.setInt(9,  reimbursment.getType().ordinal() + 1);
			preparedStatement.setInt(10, reimbursment.getId());

			preparedStatement.executeUpdate();

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
}