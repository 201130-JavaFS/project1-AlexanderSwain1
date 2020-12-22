package com.revature.reimbursment_server.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursment_server.models.ReimbursementDTO;
import com.revature.reimbursment_server.models.Reimbursment;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.services.ReimbursementService;
import com.revature.reimbursment_server.services.UserService;
import com.revature.reimbursment_server.utilities.Utilities;
import com.revature.reimbursment_server.web.FrontController;

public class ReimbursementController 
{
	private static Logger log = Logger.getLogger(ReimbursementController.class);
	
	private ObjectMapper om = new ObjectMapper();
	private UserService userService = new UserService();
	private ReimbursementService reimbursementService = new ReimbursementService();
	
	public void getReimbursements(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			//used for final permission filter
			User user = (User)session.getAttribute("user");
			
			Map<String, String[]> params = request.getParameterMap();
			List<Reimbursment> result = new ArrayList<Reimbursment>();
			
			//this version will only support up to one query parameter
			if (params.size() == 1)
			{
				for (String key : params.keySet())
				{
					Integer value;
					try
					{
						value = Integer.parseInt(params.get(key)[0]);
					}
					catch (NumberFormatException e)
					{
						response.setStatus(400);
						log.error("Error in request");
						return;
					}
					
					if (key.equals("author"))
					{
						User author = userService.GetUser(value);
						if (author != null)
							result = reimbursementService.getReimbursements(author);
						else
						{
							response.setStatus(400);
							log.error("Error in request");
							return;
						}
					}
					else if (key.equals("resolver"))
					{
						User resolver = userService.GetUser(value);
						if (resolver != null)
							result = reimbursementService.getReimbursementsWithResolver(resolver);
						else
						{
							response.setStatus(400);
							log.error("Error in request");
							return;
						}
					}
					else if (key.equals("status"))
					{
						int status = value - 1;
						result = reimbursementService.getReimbursements(Reimbursment.Status.values()[status]);
					}
					else
					{
						response.setStatus(400);
						log.error("Error in request");
						return;
					}
				}
			}
			else if (params.size() == 0)
				result = reimbursementService.getReimbursements();
			else
			{
				response.setStatus(400);
				log.error("Error in request");
				return;
			}
			
			
			//filter for permissions
			if (user.getRole() != User.Role.Manager)
				result = filterForUserPermission(user, result);
			
			List<ReimbursementDTO> asDTO = result.stream().map(item -> { return new ReimbursementDTO(item); }).collect(Collectors.toList());
			if (Utilities.respondWithJSON(response, asDTO))
				response.setStatus(200);
			else
			{
				response.setStatus(500);
				log.error("Failed to response with JSON");
			}
		}
		else
		{
			response.setStatus(400);
			log.error("User is not logged in.");
			return;
		}
	}

	public void getReimbursement(Integer pathParameter, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			//used for final permission filter
			User user = (User)session.getAttribute("user");
			
			Reimbursment result = reimbursementService.getReimbursement(pathParameter);
			
			if (result != null) 
			{
				if (user.getRole() == User.Role.Manager || result.getAuthor().getId() == user.getId())
				{
					if (Utilities.respondWithJSON(response, new ReimbursementDTO(result)))
						response.setStatus(200);
					else
					{
						response.setStatus(500);
						log.error("Failed to response with JSON");
					}
				}
				else
				{
					response.setStatus(400);
					log.error("The user doens't have the right permissions for this data");
					return;
				}
			}
			else
			{
				response.setStatus(400);
				log.error("Error in request");
				return;
			}
		}
		else
		{
			response.setStatus(400);
			log.error("User is not logged in.");
			return;
		}
	}
	//Im sure theres a better way than this
	public void get_prepared_view(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
			getReimbursement((int)session.getAttribute("viewId"), request, response);
		else
		{
			response.setStatus(400);
			log.error("User is not logged in.");
		}
	}
	public void prepare_view(Integer pathParameter, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			Reimbursment result = reimbursementService.getReimbursement(pathParameter);
			
			if (result != null) 
			{
				session.setAttribute("viewId", result.getId());
				response.setStatus(200);
			}
			else
			{
				response.setStatus(400);
				log.error("There's an error in the request");
				return;
			}
		}
		else
		{
			response.setStatus(400);
			log.error("User is not logged in.");
			return;
		}
	}

	public void create(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		
		if (session != null)
		{
			String body = Utilities.readBody(request);
			
			if (body != null)
			{
				try 
				{
					User author = (User)session.getAttribute("user");
					ReimbursementDTO reimbursementDTO = om.readValue(body, ReimbursementDTO.class);
					Reimbursment reimbursement = new Reimbursment(reimbursementDTO);
					reimbursement.setAuthor(author);
					reimbursement.setSubmitted(LocalDateTime.now());
					
					if (reimbursementService.createReimbursement(reimbursement))
						response.setStatus(200);
					else
					{
						log.error("Failed to create reimbursement");
						response.setStatus(500);
					}
				} 
				catch (JsonProcessingException e) 
				{
					log.error("Unable to parse JSON: " + e.getMessage());
					response.setStatus(400);
				}
			}
			else
			{
				log.error("Failed to read the request");
				response.setStatus(500);
			}
		}
		else
		{
			log.error("User is not logged in.");
			response.setStatus(400);
		}
	}

	public void decision(HttpServletRequest request, HttpServletResponse response, Reimbursment.Status status)
	{
		HttpSession session = request.getSession(false);
		
		if (session != null)
		{
			String body = Utilities.readBody(request);
			
			if (body != null)
			{
				try 
				{
					User resolver = (User)session.getAttribute("user");
					ReimbursementDTO reimbursementDTO = om.readValue(body, ReimbursementDTO.class);
					Reimbursment reimbursement = reimbursementService.getReimbursement(reimbursementDTO.id);
					if (reimbursement != null && resolver.getRole() == User.Role.Manager && reimbursement.getStatus() == Reimbursment.Status.Pending)
					{
						reimbursement.setResolver(resolver);
						reimbursement.setResolved(LocalDateTime.now());
						reimbursement.setStatus(status);
						if (reimbursementService.updateReimbursement(reimbursement))
							response.setStatus(200);
						else
						{
							log.error("Failed to update reimbursement.");
							response.setStatus(500);
						}
					}
					else
					{
						//reimbursement doesn't exist, don't have manager permissions, or reimbursement decision has already been made
						log.error("The requested resource doesn't exist.");
						response.setStatus(400);
					}
				} 
				catch (JsonProcessingException e) 
				{
					log.error("Unable to parse JSON: " + e.getMessage());
					response.setStatus(400);
				}
			}
			else
			{
				log.error("Failed to read the request");
				response.setStatus(500);
			}
		}
		else
		{
			System.out.println("User is not logged in");
			response.setStatus(400);
		}
	}
	
	private List<Reimbursment> filterForUserPermission(User user, List<Reimbursment> toFilter)
	{
		return toFilter.stream()
				.filter(reimbursement -> { return reimbursement.getAuthor().getId() == user.getId(); })
				.collect(Collectors.toList());
	}
	private List<ReimbursementDTO> convertToDTO(List<Reimbursment> list)
	{
		List<ReimbursementDTO> result = new ArrayList<ReimbursementDTO>();
		for (Reimbursment r : list)
			result.add(new ReimbursementDTO(r));
		return result;
	}
}
