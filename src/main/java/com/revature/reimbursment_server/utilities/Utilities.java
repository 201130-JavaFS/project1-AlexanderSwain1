package com.revature.reimbursment_server.utilities;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursment_server.exceptions.*;
import com.revature.reimbursment_server.services.UserService;

public class Utilities
{
	private static Logger log = Logger.getLogger(UserService.class);
	private static ObjectMapper om = new ObjectMapper();
	
	//A long can hold all the money in the world in a single account if held in cents
	public static long parseMoney(String value) throws MoneyParseException
	{
		String[] splitted = value.split("\\.");
		if (splitted.length == 2)
		{
			long dollars;
			long cents;
			try
			{
				dollars = Long.parseLong(splitted[0]);
				cents = Long.parseLong("" + splitted[1].charAt(0) + splitted[1].charAt(1));
			}
			catch (NumberFormatException e)
			{
				throw new MoneyParseException("The provided string is not in the right format");
			}
			return dollars * 100 + cents;
		}
		else if (splitted.length == 1)
		{
			try 
			{
				long dollars = Long.parseLong(value);
				return dollars * 100;
			}
			catch (NumberFormatException e)
			{
				throw new MoneyParseException("The provided string is not in the right format");
			}
		}
		else
			throw new MoneyParseException("The provided string is not in the right format");
	}
	
	public static String stringifyMoney(long value)
	{
		long dollars = value / 100;
		long cents = value % 100;
		return String.valueOf(dollars) + "." + String.valueOf(cents);
	}
	
	public static String readBody(HttpServletRequest request)
	{
		try
		{
			BufferedReader reader = request.getReader();
			
			StringBuilder s = new StringBuilder();
			
			String line = reader.readLine();
			//This gets each line of the Request's body and appends it to the StringBuilder
			while(line != null) {
				s.append(line);
				line = reader.readLine(); 
			}
			
			return new String(s); 
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			return null;
		}
	}
	public static boolean respondWithJSON(HttpServletResponse response, Object obj)
	{
		try 
		{
			String responseBody = om.writeValueAsString(obj);
			response.getWriter().print(responseBody);
			return true;
		} 
		catch (JsonProcessingException e) 
		{
			log.error(e.getMessage());
			return false;
		}
		catch (IOException e)
		{
			log.error(e.getMessage());
			return false;
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			return false;
		}
	}
}
