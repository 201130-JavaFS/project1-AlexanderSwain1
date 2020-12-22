package com.revature.reimbursment_server.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.reimbursment_server.controllers.UserController;
import com.revature.reimbursment_server.dao.ReimbursmentsDAO;
import com.revature.reimbursment_server.models.Reimbursment;
import com.revature.reimbursment_server.models.Reimbursment.Status;
import com.revature.reimbursment_server.models.User;

public class ReimbursementService 
{
	private static Logger log = Logger.getLogger(ReimbursementService.class);
	
	ReimbursmentsDAO reimbursementDao = new ReimbursmentsDAO();
	
	public List<Reimbursment> getReimbursements()
	{
		try
		{
			return reimbursementDao.getReimbursments();
		}
		catch (Exception e)
		{
			log.error("Failed to get the reimbursements.");
			return null;
		}
	}
	public List<Reimbursment> getReimbursements(User author)
	{
		try
		{
			return reimbursementDao.getReimbursements(author);
		}
		catch (Exception e)
		{
			log.error("Failed to get the reimbursements.");
			return null;
		}
	}
	public List<Reimbursment> getReimbursementsWithResolver(User resolver)
	{
		try
		{
			return reimbursementDao.getReimbursementsWithResolver(resolver);
		}
		catch (Exception e)
		{
			log.error("Failed to get the reimbursements by resolver.");
			return null;
		}
	}
	public List<Reimbursment> getReimbursements(Status status)
	{
		try
		{
			return reimbursementDao.getReimbursements(status);
		}
		catch (Exception e)
		{
			log.error("Failed to get the reimbursements by status.");
			return null;
		}
	}
	public Reimbursment getReimbursement(int id)
	{
		try
		{
			return reimbursementDao.getReimbursment(id);
		}
		catch (Exception e)
		{
			log.error("Failed to get the reimbursements by id.");
			return null;
		}
	}
	public boolean createReimbursement(Reimbursment r)
	{
		try
		{
			reimbursementDao.createReimbursment(r);
			return true;
		}
		catch (Exception e)
		{
			log.error("Failed to create the reimbursement.");
			return false;
		}
	}
	public boolean updateReimbursement(Reimbursment r)
	{
		try
		{
			reimbursementDao.updateReimbursment(r);
			return true;
		}
		catch (Exception e)
		{
			log.error("Failed to update the reimbursement.");
			return false;
		}
	}
}
