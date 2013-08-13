package scan;

import java.util.ArrayList;
import schedule.userSync;
import utils.methodesUtiles;

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
	private String name;
	private ArrayList<String> pattern;
	private userSync myUserSync;
	
	public patternContent(String name, userSync myUserSync) throws Exception
		{
		this.name = name;
		this.myUserSync = myUserSync;
		pattern = new ArrayList<String>();
		
		fill();
		}

	/**
	 * Method used to fill the pattern entry
	 */
	private void fill() throws Exception
		{
		String pat = methodesUtiles.getTargetTask(name,myUserSync.getTaskIndex());
		
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
	 * Getters and setters
	 */
	public String getName()
		{
		return name;
		}

	public void setName(String name)
		{
		this.name = name;
		}

	public ArrayList<String> getPattern()
		{
		return pattern;
		}

	public void setPattern(ArrayList<String> pattern)
		{
		this.pattern = pattern;
		}

	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

