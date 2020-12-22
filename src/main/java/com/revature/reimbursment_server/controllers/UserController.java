package com.revature.reimbursment_server.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursment_server.models.DashboardInfo_DTO;
import com.revature.reimbursment_server.models.LoginDTO;
import com.revature.reimbursment_server.models.LoginSuccessfulDTO;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.services.UserService;
import com.revature.reimbursment_server.utilities.Utilities;
import com.revature.reimbursment_server.web.FrontController;

public class UserController 
{
	private static Logger log = Logger.getLogger(UserController.class);
	
	UserService userService = new UserService();
	private ObjectMapper om = new ObjectMapper();
	
	public void getUsers(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			log.error("This method is not implemented.");
			PrintWriter pw = response.getWriter();
			pw.append("Get Users Succeeded.");
			response.setStatus(200);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void getUser(Integer pathParameter, HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			log.error("This method is not implemented.");
			PrintWriter pw = response.getWriter();
			pw.append("Get User Succeeded.");
			response.setStatus(200);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void login(HttpServletRequest request, HttpServletResponse response) 
	{
		String body = Utilities.readBody(request);
		
		if (body != null)
		{
			try 
			{
				LoginDTO loginDTO = om.readValue(body, LoginDTO.class);
				User user = userService.Login(loginDTO.username, loginDTO.password);
				
				if (user != null)
				{
					LoginSuccessfulDTO lsDTO = new LoginSuccessfulDTO(user.getRole() == User.Role.Employee ? "EMPLOYEE" : "MANAGER");
					if (Utilities.respondWithJSON(response, lsDTO))
					{
						HttpSession session = request.getSession();
						session.setAttribute("user", user);
						response.setStatus(200);
					}
					else
					{
						response.setStatus(500);
						log.error("There was an error with responsding with JSON.");
					}
				}
				else
				{
					response.setStatus(400);
					log.error("User is not logged in.");
				}
			} 
			catch (JsonProcessingException e) 
			{
				response.setStatus(400);
				log.error("Failed to parse JSON: " + e.getMessage());
			}
		}
		else
		{
			response.setStatus(500);
			log.error("Failed to read the request.");
		}
	}
	
	public void validate(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			User loggedInUser = ((User)session.getAttribute("user")).getClone();
			DashboardInfo_DTO dashboardInfo = new DashboardInfo_DTO(loggedInUser);
			
			if (Utilities.respondWithJSON(response, dashboardInfo))
				response.setStatus(200);
			else
			{
				response.setStatus(500);
				log.error("There was an error with responsding with JSON.");
			}
		}
		else
		{
			response.setStatus(400);
			log.error("User is not logged in.");
		}
	}

	public void create(HttpServletRequest request, HttpServletResponse response) 
	{
		try 
		{
			PrintWriter pw = response.getWriter();
			pw.append("Create User Succeeded.");
			response.setStatus(200);
		} 
		catch (IOException e) 
		{
			log.error("An IOException has occured" + e.getMessage());
		}
	}

}
