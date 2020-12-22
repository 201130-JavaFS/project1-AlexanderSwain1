package com.revature.reimbursment_server.models;

import java.util.List;

public class DashboardInfo_DTO 
{
	public int id;
	public String username;
	public String first_name;
	public String last_name;
	public String role;
	
	public DashboardInfo_DTO(User user) 
	{
		super();
		id = user.getId();
		username = user.getUsername();
		first_name = user.getFirstName();
		last_name = user.getLastName();
		role = user.getRole().name();
	}
	public DashboardInfo_DTO() 
	{
		super();
	}
}
