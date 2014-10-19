package com.ardu.gorient;

public class Crypt {
	private int increment=2;
	String encrypt(String original )
	{
		String encryptedString=new String();
		for(int i=0;i<original.length();i++)
		{	
			char c;
			c=original.charAt(i);
			c+=increment;
			encryptedString+=c;
		}
		return encryptedString;
	}
	String decrypt(String original)
	{
		String decryptedString=new String();
		for(int i=0;i<original.length();i++)
		{	
			char c;
			c=original.charAt(i);
			c-=increment;
			decryptedString+=c;
		}
		return decryptedString;
	}

}
