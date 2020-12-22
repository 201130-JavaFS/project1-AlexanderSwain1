package com.revature.reimbursment_server.test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.revature.reimbursment_server.exceptions.MoneyParseException;
import com.revature.reimbursment_server.utilities.Encryptor;
import com.revature.reimbursment_server.utilities.UrlUtilities;
import com.revature.reimbursment_server.utilities.Utilities;

public class UtilityTest 
{
	@Test
	public void parseMoney() throws MoneyParseException
	{
		long twelveDollarsAndFiftySevenCents = Utilities.parseMoney("12.57");
		assert(twelveDollarsAndFiftySevenCents == 1257);
	}
	
	@Test
	public void stringifyMoney() throws MoneyParseException
	{
		String twelveDollarsAndFiftySevenCents = Utilities.stringifyMoney(1257);
		assert(twelveDollarsAndFiftySevenCents.equals("12.57"));
	}
	
	@Test
	public void isIntTest()
	{
		assert(UrlUtilities.isInt("14947") == true);
	}
	
	@Test
	public void getPathParameter()
	{
		assert(UrlUtilities.getPathParameter("Reimbursements/3") == 3);
	}
	
	@Test
	public void cipherTest()
	{
		Encryptor encryptor = new Encryptor();
		String encrypted = encryptor.encrypt("Password4John");
		String decrypted = encryptor.decrypt(encrypted);
		assert(decrypted.equals("Password4John"));
	}
}
