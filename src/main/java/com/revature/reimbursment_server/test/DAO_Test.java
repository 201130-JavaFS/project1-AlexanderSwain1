package com.revature.reimbursment_server.test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import com.revature.reimbursment_server.dao.ReimbursmentsDAO;
import com.revature.reimbursment_server.dao.UsersDAO;
import com.revature.reimbursment_server.exceptions.DataAccessException;
import com.revature.reimbursment_server.exceptions.MoneyParseException;
import com.revature.reimbursment_server.models.Reimbursment;
import com.revature.reimbursment_server.models.User;
import com.revature.reimbursment_server.utilities.Utilities;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAO_Test 
{

	static UsersDAO usersDAO = new UsersDAO();
	static ReimbursmentsDAO reimbursmentDAO = new ReimbursmentsDAO();
	
	static Reimbursment reimbursement1;
	static Reimbursment reimbursement2;
	static Reimbursment reimbursement3;
	
	@BeforeAll
	public static void initialize() throws DataAccessException 
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get cleared
		assertTrue(isTestMode()); 
		
		//if decide to implement registration, uncomment this line and update tests
		//usersDAO.clearUsers();
		
		reimbursmentDAO.clearReimbursements();
	}
	
	@AfterAll
	public static void cleanUp() throws DataAccessException 
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get cleared
		assertTrue(isTestMode());
		
		//if decide to implement registration, uncomment this line and update tests
		//usersDAO.clearUsers();
		
		reimbursmentDAO.clearReimbursements();
	}
	
	
	@Test
	//getExistingUser
	public void test1() throws DataAccessException 
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		User johnTest0 = usersDAO.getUserByID(1);
		User johnTest1 = usersDAO.getUserByUsername("John");
		User johnTest2 = usersDAO.getUserByEmail("JohnTaylor@armyspy.com");
		
		assertTrue(johnTest0.equals(johnTest1) && johnTest1.equals(johnTest2));
	}
	
	@Test
	//createReimbursement
	public void test2() throws DataAccessException, MoneyParseException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		User john = usersDAO.getUserByUsername("John");
		User jennifer = usersDAO.getUserByUsername("Jennifer");
		User brian = usersDAO.getUserByUsername("Brian");
		reimbursement1 = new Reimbursment(0, Utilities.parseMoney("120052765.45"), LocalDateTime.now(), null, "Required Certification Test", null, john, null, Reimbursment.Status.Pending, Reimbursment.Type.Other);
		reimbursement2 = new Reimbursment(0, Utilities.parseMoney("305.10"), LocalDateTime.now(), null, "AirBNB fees", null, john, null, Reimbursment.Status.Pending, Reimbursment.Type.Lodging);
		reimbursement3 = new Reimbursment(0, Utilities.parseMoney("200.06"), LocalDateTime.now(), null, "Food for mandatory employee event", null, jennifer, null, Reimbursment.Status.Pending, Reimbursment.Type.Food);
		
		reimbursmentDAO.createReimbursment(reimbursement1);
		reimbursmentDAO.createReimbursment(reimbursement2);
		reimbursmentDAO.createReimbursment(reimbursement3);
	}
	
	@Test
	//getReimbursements
	public void test3() throws DataAccessException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		List<Reimbursment> reimbursements = reimbursmentDAO.getReimbursments();
		
		//should contain all the reimbursements
		//assertTrue(reimbursements.contains(reimbursement1));
		//assertTrue(reimbursements.contains(reimbursement2));
		//assertTrue(reimbursements.contains(reimbursement3));
	}
	
	@Test
	//updateReimbursements
	public void test4() throws DataAccessException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		User brian = usersDAO.getUserByUsername("Brian");
		reimbursement3.setStatus(Reimbursment.Status.Approved);
		reimbursement3.setResolver(brian);
		reimbursement3.setResolved(LocalDateTime.now());
		reimbursmentDAO.updateReimbursment(reimbursement3);
	}
	
	@Test
	//filterReimbursementsByStatus
	public void test5() throws DataAccessException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		List<Reimbursment> reimbursements = reimbursmentDAO.getReimbursements(Reimbursment.Status.Pending);
		
		//should contain these pending reimbursements
		//assertTrue(reimbursements.contains(reimbursement1));
		//assertTrue(reimbursements.contains(reimbursement2));
		
		//should not contain this approved reimbursement
		//assertTrue(!reimbursements.contains(reimbursement3));
	}
	
	@Test
	//filterReimbursementsByAuthor
	public void test6() throws DataAccessException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		User jennifer = usersDAO.getUserByUsername("Jennifer");
		List<Reimbursment> reimbursements = reimbursmentDAO.getReimbursements(jennifer);
		
		//should contain not these reimbursements by John
		//assertTrue(!reimbursements.contains(reimbursement1));
		//assertTrue(!reimbursements.contains(reimbursement2));
		
		//should contain this reimbursement by Jennifer
		//assertTrue(reimbursements.contains(reimbursement3));
	}
	
	@Test
	//filterReimbursementsByResolver
	public void test7() throws DataAccessException
	{
		//DatabaseConnection dependents:
		//make sure this is working so the main database doesn't get modified
		assertTrue(isTestMode());
		
		User brian = usersDAO.getUserByUsername("Brian");
		List<Reimbursment> reimbursements = reimbursmentDAO.getReimbursementsWithResolver(brian);
		
		//should contain not these reimbursements by John
		//assertTrue(!reimbursements.contains(reimbursement1));
		//assertTrue(!reimbursements.contains(reimbursement2));
		
		//should contain this reimbursement resolved by Brian
		//assertTrue(reimbursements.contains(reimbursement3));
	}
	
	//Why is this not a feature of JUnit
	public static boolean isTestMode() 
	{
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) 
		{
			if (element.getClassName().startsWith("org.junit.")) 
			{
				return true;
			}
		}
		return false;
	}
}
