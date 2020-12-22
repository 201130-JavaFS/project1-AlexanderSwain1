package com.revature.reimbursment_server.exceptions;

public class DataAccessException extends Exception
{
	public DataAccessException(final String message)
	{
		super(message);
	}
}