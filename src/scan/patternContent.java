package scan;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import schedule.userSync;
import schedule.userSync.patternType;
import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to store pattern content
 * 
 * @author RATEL Alexandre
 **********************************/
public class patternContent
	{
	/**
	 * Variables
	 */
	private patternType name;
	private ArrayList<String> pattern;
	private userSync myUSync;
	
	public patternContent(patternType name, userSync myUSync) throws Exception
		{
		this.name = name;
		this.myUSync = myUSync;
		pattern = new ArrayList<String>();
		
		fill();
		}

	/**
	 * Method used to fill the pattern entry
	 */
	private void fill() throws Exception
		{
		String pat = methodesUtiles.getTargetTask(name.name(),myUSync.getTaskIndex());
		
		if((pat == null) || (pat.compareTo("") == 0))
			{
			throw new IllegalArgumentException("ERROR : The argument looked for, is empty");
			}
		else
			{
			String[] patType = pat.split("\\+");
			for(int i=0; i<patType.length; i++)
				{
				pattern.add(patType[i]);
				}
			}
		}
	
	/**
	 * Method used to return the compile string
	 * obtain from the task Config File
	 */
	public String getRegex(device myDevice, userData myUser)
		{
		StringBuffer buffer = new StringBuffer("");
		
		for(int i=0; i<pattern.size(); i++)
			{
			String pat = pattern.get(i);
			
			if(Pattern.matches(".*cucm.*", pat))
				{
				if(Pattern.matches(".*prenom", pat))
					{
					buffer.append(specialRegex(myUser.getFirstName(),pat));
					}
				else if(Pattern.matches(".*nom", pat))
					{
					buffer.append(specialRegex(myUser.getLastName(),pat));
					}
				else if(Pattern.matches(".*userid", pat))
					{
					buffer.append(specialRegex(myUser.getUserid(),pat));
					}
				else if(Pattern.matches(".*dept", pat))
					{
					buffer.append(specialRegex(myUser.getDepartement(),pat));
					}
				else if(Pattern.matches(".*number", pat))
					{
					buffer.append(specialRegex(myUser.getTelephoneNumber(),pat));
					}
				else if(Pattern.matches(".*tposte", pat))
					{
					buffer.append(specialRegex(myDevice.getModel(),pat));
					}
				else if(Pattern.matches(".*tname", pat))
					{
					buffer.append(specialRegex(myDevice.getName(),pat));
					}
				}
			else if(Pattern.matches(".*mactived.*", pat))
				{
				//In a future release
				}
			else
				{
				buffer.append(pat.replace("'", ""));
				}
			}
		
		//Rajouter une gestion des chaines trop longues et des caractères non supportés 
		return buffer.toString();
		}
	
	/**
	 * Method used to return the compile string
	 * obtain from the task Config File
	 */
	public String getRegex(line myLine, userData myUser)
		{
		StringBuffer buffer = new StringBuffer("");
		
		for(int i=0; i<pattern.size(); i++)
			{
			String pat = pattern.get(i);
			
			if(Pattern.matches(".*cucm.*", pat))
				{
				if(Pattern.matches(".*prenom", pat))
					{
					buffer.append(specialRegex(myUser.getFirstName(),pat));
					}
				else if(Pattern.matches(".*nom", pat))
					{
					buffer.append(specialRegex(myUser.getLastName(),pat));
					}
				else if(Pattern.matches(".*userid", pat))
					{
					buffer.append(specialRegex(myUser.getUserid(),pat));
					}
				else if(Pattern.matches(".*dept", pat))
					{
					buffer.append(specialRegex(myUser.getDepartement(),pat));
					}
				else if(Pattern.matches(".*number", pat))
					{
					buffer.append(specialRegex(myUser.getTelephoneNumber(),pat));
					}
				else if(Pattern.matches(".*nligne", pat))
					{
					buffer.append(specialRegex(myLine.getPattern(),pat));
					}
				}
			else if(Pattern.matches(".*mactived.*", pat))
				{
				//In a future release
				}
			else
				{
				buffer.append(pat.replace("'", ""));
				}
			}
		return buffer.toString();
		}
	
	/**
	 * Getters and setters
	 */
	public ArrayList<String> getPattern()
		{
		return pattern;
		}

	public void setPattern(ArrayList<String> pattern)
		{
		this.pattern = pattern;
		}

	public patternType getName()
		{
		return name;
		}

	public void setName(patternType name)
		{
		this.name = name;
		}
	
	/******************************************
	 * Method used to apply special regex type
	 ******************************************/
	private String specialRegex(String pat, String param)
		{
		try
			{
			String newValue = new String(pat);
			
			/*********
			 * Number
			 **/
			if(Pattern.matches(".*\\*\\d+\\*.*", param))
				{
				int number = howMany("\\*\\d+\\*", param);
				if(newValue.length() >= number)
					{
					newValue = newValue.substring(0, number);
					}
				}
			/**
			 * Fin number
			 *************/
			
			/*************
			 * Majuscule
			 **/
			if(Pattern.matches(".*\\*M\\*.*", param))
				{
				newValue = newValue.toUpperCase();
				}
			if(Pattern.matches(".*\\*\\d+M\\*.*", param))
				{
				int majuscule = howMany("\\*\\d+M\\*", param);
				if(newValue.length() >= majuscule)
					{
					String temp = new String("");
					temp = newValue.substring(0, majuscule);
					temp = temp.toUpperCase();
					newValue = temp+newValue.substring(majuscule,newValue.length()).toLowerCase();
					}
				}
			/**
			 * Fin majuscule
			 ****************/
			
			/*************
			 * Minuscule
			 **/
			if(Pattern.matches(".*\\*m\\*.*", param))
				{
				newValue = newValue.toLowerCase();
				}
			if(Pattern.matches(".*\\*\\d+m\\*.*", param))
				{
				int minuscule = howMany("\\*\\d+m\\*", param);
				if(newValue.length() >= minuscule)
					{
					String temp = new String("");
					temp = newValue.substring(0, minuscule);
					temp = temp.toLowerCase();
					newValue = temp+newValue.substring(minuscule,newValue.length());
					}
				}
			/**
			 * Fin minuscule
			 ****************/
			
			return newValue;
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+"ERROR : Regex resolution has failed. default value returned : "+pat);
			return pat;
			}
		}
	
	/**
	 * Method used to return a number present in a regex
	 * 
	 * by example : *1M* return 1
	 */
	private int howMany(String regex, String param) throws Exception
		{
		Pattern p = Pattern.compile(regex);
		Pattern pChiffre = Pattern.compile("\\d+");
		Matcher m = p.matcher(param);
		
		if(m.find())
			{
			Matcher mChiffre = pChiffre.matcher(m.group());
			if(mChiffre.find())
				{
				return Integer.parseInt(mChiffre.group());
				}
			}
		return 0;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

