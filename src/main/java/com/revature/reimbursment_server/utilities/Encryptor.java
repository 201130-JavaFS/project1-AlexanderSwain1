package com.revature.reimbursment_server.utilities;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor 
{
	private static final String key = "Bar12345Bar12345";
	private Key aesKey;
	private Cipher cipher;
	
	public Encryptor()
	{
		try
		{
			cipher = Cipher.getInstance("AES");
			aesKey = new SecretKeySpec(key.getBytes(), "AES");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public String encrypt(String s)
	{
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(s.getBytes());
			return new String(encrypted);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public String decrypt(String s)
	{
		try
		{
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(s.getBytes()));
            return decrypted;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
