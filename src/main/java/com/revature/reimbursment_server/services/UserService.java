package com.revature.reimbursment_server.services;

import org.apache.log4j.Logger;

import com.revature.reimbursment_server.dao.UsersDAO;
import com.revature.reimbursment_server.exceptions.DataAccessException;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.utilities.Encryptor;

public class UserService 
{
	private static Logger log = Logger.getLogger(UserService.class);
	
	UsersDAO usersDAO = new UsersDAO();
	
	public User Login(String username, String password)
	{
		Encryptor encryptor;
		User user;
		
		try 
		{
			user = usersDAO.getUserByUsername(username);
			encryptor = new Encryptor();
		} 
		catch (DataAccessException e) 
		{
			log.error("Failed to login user: " + username);
			return null;
		}
		catch (Exception e)
		{
			log.error("Problem with creating encryptor: " + username);
			return null;
		}
		
		//decrypt the password from the database
		String decryptedPassword = encryptor.decrypt(user.getPassword());
		
		if (user != null && decryptedPassword.equals(password))
			return user;
		else
		{
			log.error("Failed to login user: username is wrong <" + username + ">");
			return null;// is wrong
		}
			
	}
	public User GetUser(int id)
	{
		try 
		{
			return usersDAO.getUserByID(id);
		} 
		catch (DataAccessException e) 
		{
			log.error("Failed to get user by id");
			return null;
		}
	}
}
