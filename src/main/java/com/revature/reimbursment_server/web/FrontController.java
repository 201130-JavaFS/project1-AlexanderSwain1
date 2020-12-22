package com.revature.reimbursment_server.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.reimbursment_server.controllers.ReimbursementController;
import com.revature.reimbursment_server.controllers.UserController;
import com.revature.reimbursment_server.models.Reimbursment;
import com.revature.reimbursment_server.utilities.UrlUtilities;


public class FrontController extends HttpServlet 
{
	private static Logger log = Logger.getLogger(FrontController.class);
	
	UserController userController = new UserController();
	ReimbursementController reimbursementController = new ReimbursementController();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        log.info("Received request: " + request.getRequestURI());

		//API logic
		String controllerName = UrlUtilities.getAtLevel(request.getRequestURI(), 2);
		String actionName = UrlUtilities.getAtLevel(request.getRequestURI(), 3);
		Integer pathParameter = UrlUtilities.getPathParameter(request.getRequestURI());
		
		//Controller Mapping
		response.setContentType("application/json");
		switch (controllerName) 
		{
			case "users":
				if (actionName == null)
				{
					if (pathParameter == null)
						userController.getUsers(request, response);
					else
						userController.getUser(pathParameter, request, response);
				}
				else
					switch (actionName)
					{
						case "login":
							userController.login(request, response);
							break;
						case "validate":
							userController.validate(request, response);
							break;
						case "create":
							userController.create(request, response);
							break;
						default:
							response.setStatus(400);
							log.error("Bad request");
							break;
					}
				break;
			case "reimbursements":
				if (actionName == null)
				{
					if (pathParameter == null)
						reimbursementController.getReimbursements(request, response);
					else
						reimbursementController.getReimbursement(pathParameter, request, response);
				}
				else
					switch (actionName)
					{
						case "create":
							reimbursementController.create(request, response);
							break;
						case "approve":
							reimbursementController.decision(request, response, Reimbursment.Status.Approved);
							break;
						case "deny":
							reimbursementController.decision(request, response, Reimbursment.Status.Denied);
							break;
						case "prepare_view":
							reimbursementController.prepare_view(pathParameter, request, response);
							break;
						case "get_prepared_view":
							reimbursementController.get_prepared_view(request, response);
							break;
						default:
							response.setStatus(400);
							log.error("Bad request");
							break;
					}
					break;
			default:
				response.setStatus(400);
				log.error("Bad request");
				break;
		}
	}
}
