package utils;

import java.util.StringTokenizer;
import java.util.regex.Pattern;




public class testeur
	{
	/************
	 * Variables
	 ************/
	
	public static boolean testSuccess(String texte)
		{
		texte.replace("\n", "");
		System.out.println(texte);
		
		if(Pattern.matches(".*<success/>.*", texte))
			{
			return true;
			}
		return false;
		}
	
	
	/*******************************************************
	 * Methode qui vérifie une adresse IP
	 *******************************************************/
	public static boolean IPValide(String IP)
		{
		boolean done = true;
		int a=0;
		try
			{
			StringTokenizer st = new StringTokenizer(IP,".");
			while(st.hasMoreTokens() && done)
				{
				String tok = st.nextToken();
				if(Pattern.matches("\\d*", tok))
					{
					int nb = Integer.parseInt(tok);
					if((nb>=0)&&(nb<=255))
						{
						done = true;
						}
					else
						{
						done = false;
						}
					}
				else
					{
					done = false;
					}
				a++;
				}
			if(done && (a == 4))
				{
				return true;
				}
			}
		catch(Exception e)
			{
			System.out.println("Problème de verif IP");
			e.printStackTrace();
			}
		return false;
		}
	
	/*************************************
	 * Methode qui vérfie une adresse MAC
	 *************************************/
	public static boolean macValide(String mac)
		{
		try
			{
			if((mac.compareTo("") != 0) && (mac.length() == 12) && Pattern.matches("\\p{XDigit}*", mac))
				{
				return true;
				}
			else
				{
				return false;
				}
			}
		catch(Exception e)
			{
			System.out.println("Problème de verif MAC");
			e.printStackTrace();
			}
		return false;
		}
	
	/**
	 * Method used to know is a STring is an MD5 hash
	 */
	public static boolean isMD5(String md5) throws Exception
		{
		if((md5.compareTo("") != 0) && (md5.length() == 32) && Pattern.matches("\\p{XDigit}*", md5))
			{
			return true;
			}
		else
			{
			throw new Exception(md5+" is not a MD5 hash");
			}
		}
	
	
	/*Fin classe*/
	}
