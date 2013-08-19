package scan;

import javax.xml.soap.SOAPMessage;

import misc.soapMessageMaker;
import misc.toDo;
import schedule.userSync;
import schedule.userSync.patternType;
import utils.variables;

/**********************************
 * Class used to compare data for 
 * a user entry
 * 
 * @author RATEL Alexandre
 **********************************/
public class userDataCompare
	{
	/**
	 * Variables
	 */
	private userSync myUSync;
	private userData myUser;
	
	
	public userDataCompare(userData myUser, userSync myUSync)
		{
		this.myUser = myUser;
		this.myUSync = myUSync;
		
		compare();
		}
	
	/**
	 * Method used to compare user data get
	 * in the CUCM database and user data
	 * compile with task config file
	 */
	public void compare()
		{
		try
			{
			/**
			 * Device and associated line Treatment
			 */
			for(int i=0; i<myUser.getAssociatedDevice().size(); i++)
				{
				deviceCompareJob(myUser.getAssociatedDevice().get(i));
				}
			
			/**
			 * Line treatment
			 */
			for(int i=0; i<myUser.getAssociatedLine().size(); i++)
				{
				lineCompareJob(myUser.getAssociatedLine().get(i));
				}
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+" ERROR detected with user "+myUser.getUserid()+" : "+exc.getMessage());
			}
		}
	
	/**
	 * do the compare job for device and line
	 */
	private void deviceCompareJob(device d) throws Exception
		{
		patternType pt;
		for(int j=0; j<myUSync.getUserSyncTemplate().size(); j++)
			{
			/**
			 * Device description
			 */
			pt = patternType.devicedescription;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findDeviceChange(d, pt, myUSync.getUserSyncTemplate().get(j));
				}
			}
		/**
		 * Associated line treatment
		 */
		for(int a=0; a<d.getAssociatedLine().size(); a++)
			{
			lineCompareJob(d.getAssociatedLine().get(a));
			}
		}
	
	/**
	 * do the compare job for line only
	 */
	private void lineCompareJob(line l) throws Exception
		{
		patternType pt;
		
		for(int j=0; j<myUSync.getUserSyncTemplate().size(); j++)
			{
			/**
			 * Line description
			 */
			pt = patternType.linedescription;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, pt, myUSync.getUserSyncTemplate().get(j));
				}
			
			/**
			 * Line label
			 */
			pt = patternType.linetextlabel;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, pt, myUSync.getUserSyncTemplate().get(j));
				}
			
			/**
			 * Line display name
			 */
			pt = patternType.linedisplay;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, pt, myUSync.getUserSyncTemplate().get(j));
				}
			
			/**
			 * Line alerting
			 */
			pt = patternType.linealertingname;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, pt, myUSync.getUserSyncTemplate().get(j));
				}
			
			/**
			 * Line external phone number mask
			 */
			pt = patternType.lineexternalphonenumbermask;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, pt, myUSync.getUserSyncTemplate().get(j));
				}
			}
		}
	
	/**
	 * Method used to find a "device" data change then add a new todo
	 */
	private void findDeviceChange(device d, patternType pt, patternContent pc) throws Exception
		{
		String newData = new String(pc.getRegex(d, myUser));
		String currentData = d.getDescription();
		variables.getLogger().debug("Device data comparison : '"+currentData+"' compare to '"+newData+"'");
		if(currentData.compareTo(newData) != 0)
			{
			myUSync.getToDoList().add(new toDo(currentData, newData, pt, myUSync, soapMessageMaker.make(pt, newData, myUSync, d)));
			}
		}
	
	/**
	 * Method used to find a "line" data change then add a new todo
	 */
	private void findLineChange(line l, patternType pt, patternContent pc) throws Exception
		{
		String newData = new String(pc.getRegex(l, myUser));
		String currentData = l.getDescription();
		variables.getLogger().debug("Line data comparison : '"+currentData+"' compare to '"+newData+"'");
		if(currentData.compareTo(newData) != 0)
			{
			myUSync.getToDoList().add(new toDo(currentData, newData, pt, myUSync, soapMessageMaker.make(pt, newData, myUSync, l)));
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

