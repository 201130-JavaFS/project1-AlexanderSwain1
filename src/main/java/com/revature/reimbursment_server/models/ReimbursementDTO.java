package com.revature.reimbursment_server.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.revature.reimbursment_server.models.Reimbursment.Status;
import com.revature.reimbursment_server.models.Reimbursment.Type;
import com.revature.reimbursment_server.utilities.Utilities;

public class ReimbursementDTO 
{
	public int id;
	public String amount;
	public Date submitted;
	public Date resolved;
	public String description;
	public byte[] receipt;
	public int author_id;
	public String author_fullname;
	public int resolver_id;
	public String resolver_fullname;
	public Status status;
	public String type;
	
	public ReimbursementDTO(Reimbursment r) 
	{
		super();
		id = r.getId();
		amount = Utilities.stringifyMoney(r.getAmount());
		submitted = r.getSubmitted() != null ? Date.from(r.getSubmitted().atZone(ZoneId.systemDefault()).toInstant()) : null;
		resolved = r.getResolved() != null ? Date.from(r.getResolved().atZone(ZoneId.systemDefault()).toInstant()) : null;
		description = r.getDescription();
		receipt = r.getReceipt();
		author_id = r.getAuthor().getId();
		author_fullname = r.getAuthor().getFirstName() + " " + r.getAuthor().getLastName();
		resolver_id = r.getResolver() != null ? r.getResolver().getId() : -1;
		resolver_fullname = r.getResolver() != null ? r.getResolver().getFirstName() + " " + r.getResolver().getLastName() : null;
		status = r.getStatus();
		type = r.getType().name();
	}
	
	public ReimbursementDTO() 
	{
		super();
	}
}
