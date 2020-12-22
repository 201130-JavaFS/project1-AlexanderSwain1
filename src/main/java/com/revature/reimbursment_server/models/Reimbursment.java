package com.revature.reimbursment_server.models;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.revature.reimbursment_server.exceptions.MoneyParseException;
import com.revature.reimbursment_server.utilities.Utilities;

public class Reimbursment 
{
	//JDBC Driver info: https://jdbc.postgresql.org/documentation/head/java8-date-time.html
	private int id;
	private long amount;
	private LocalDateTime submitted;
	private LocalDateTime resolved;
	private String description;
	private byte[] receipt;
	private User author;
	private User resolver;
	private Status status;
	private Type type;

	
	
	public Reimbursment(int id, long amount, LocalDateTime submitted, LocalDateTime resolved, String description, byte[] receipt, User author, User resolver, Status status, Type type) 
	{
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
	
	public Reimbursment(ReimbursementDTO reimbursementDTO) 
	{
		super();
		try 
		{
			this.amount = Utilities.parseMoney(reimbursementDTO.amount);
		} 
		catch (MoneyParseException e) 
		{
			System.out.println("DTO error");
			this.amount = -1;
		}
		this.submitted = null;
		this.resolved = null;
		this.description = reimbursementDTO.description;
		this.receipt = null;
		this.resolver = null;
		this.status = Reimbursment.Status.Pending;
		this.type = Reimbursment.Type.valueOf(reimbursementDTO.type);
	}
	
	
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public long getAmount() 
	{
		return amount;
	}
	public void setAmount(long amount) 
	{
		this.amount = amount;
	}
	public LocalDateTime getSubmitted() 
	{
		return submitted;
	}
	public void setSubmitted(LocalDateTime submitted) 
	{
		this.submitted = submitted;
	}
	public LocalDateTime getResolved() 
	{
		return resolved;
	}
	public void setResolved(LocalDateTime resolved) 
	{
		this.resolved = resolved;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public byte[] getReceipt() 
	{
		return receipt;
	}
	public void setReceipt(byte[] receipt) 
	{
		this.receipt = receipt;
	}
	public User getAuthor() 
	{
		return author;
	}
	public void setAuthor(User author) 
	{
		this.author = author;
	}
	public User getResolver() 
	{
		return resolver;
	}
	public void setResolver(User resolver) 
	{
		this.resolver = resolver;
	}
	public Status getStatus() 
	{
		return status;
	}
	public void setStatus(Status status) 
	{
		this.status = status;
	}
	public Type getType()
	{
		return type;
	}
	public void setType(Type type) 
	{
		this.type = type;
	}

	

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursment other = (Reimbursment) obj;
		if (amount != other.amount)
			return false;
		if (author == null) 
		{
			if (other.author != null)
				return false;
		} 
		else if (!author.equals(other.author))
			return false;
		if (description == null) 
		{
			if (other.description != null)
				return false;
		} 
		else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (resolved == null) 
		{
			if (other.resolved != null)
				return false;
		} 
		else if (!resolved.equals(other.resolved))
			return false;
		if (resolver == null) 
		{
			if (other.resolver != null)
				return false;
		} 
		else if (!resolver.equals(other.resolver))
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) 
		{
			if (other.submitted != null)
				return false;
		} 
		else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}



	public enum Status
	{
		Pending,
		Approved,
		Denied,
	}
	public enum Type
	{
		Lodging,
		Travel,
		Food,
		Other
	}
}
