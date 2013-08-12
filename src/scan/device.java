package scan;

import java.util.ArrayList;

import schedule.userSync;

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
	private String name;
	private ArrayList<line> associatedLine;
	private userSync myUSync;
	
	public device(String UUID, userSync myUSync)
		{
		this.UUID = UUID;
		this.myUSync = myUSync;
		
		fillAssociatedLine();
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
		for(int i=0; i<myUSync.getGlobalAssociatedLineList().size(); i++)
			{
			if(myUSync.getGlobalAssociatedLineList().get(i).getDevicePkid().compareTo(UUID) == 0)
				{
				associatedLine.add(new line(myUSync.getGlobalAssociatedLineList().get(i).getLinePkid()));
				}
			}
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

	public String getName()
		{
		return name;
		}

	public void setName(String name)
		{
		this.name = name;
		}
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

