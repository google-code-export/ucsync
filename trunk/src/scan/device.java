package scan;

import java.util.ArrayList;

/**********************************
 * Class used to store device information
 * 
 * @author RATEL Alexandre
 **********************************/
public class device
	{
	/**
	 * Variables
	 */
	private final String UUID; 
	private String description;
	private ArrayList<line> associatedLine;
	
	public device(String UUID)
		{
		this.UUID = UUID;
		}
	
	/**
	 * Method used to fill the
	 * associatedLine Arraylist
	 */
	public void fillAssociatedLine()
		{
		/****
		 * In the first time I will get only 
		 * UUIDs of associated line
		 */
		
		}

	
	/**
	 * Getters and Setters
	 */
	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public ArrayList<line> getAssociatedLine()
		{
		return associatedLine;
		}

	public void setAssociatedLine(ArrayList<line> associatedLine)
		{
		this.associatedLine = associatedLine;
		}

	public String getUUID()
		{
		return UUID;
		}
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

