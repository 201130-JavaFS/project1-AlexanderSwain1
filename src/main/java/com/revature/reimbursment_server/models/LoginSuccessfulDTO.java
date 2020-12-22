package com.revature.reimbursment_server.models;

public class LoginSuccessfulDTO 
{
	public String role;
	
	public LoginSuccessfulDTO(String role) 
	{
		super();
		this.role = role;
	}

	public LoginSuccessfulDTO() 
	{
		super();
	}
}
