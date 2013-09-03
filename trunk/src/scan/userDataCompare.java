package scan;

import misc.soapMessageMaker;
import misc.toDo;
import schedule.userSync;
import utils.ClearFrenchString;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.patternType;

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
				findDeviceChange(d, d.getDescription(), pt, j);
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
				findLineChange(l, l.getDescription(), pt, j);
				}
			
			/**
			 * Line label
			 */
			pt = patternType.linetextlabel;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, l.getLineTextLabel(), pt, j);
				}
			
			/**
			 * Line display name
			 */
			pt = patternType.linedisplay;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, l.getDisplayName(), pt, j);
				}
			
			/**
			 * Line alerting
			 */
			pt = patternType.linealertingname;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, l.getAlertingName(), pt, j);
				}
			
			/**
			 * Line external phone number mask
			 */
			pt = patternType.lineexternalphonenumbermask;
			if(myUSync.getUserSyncTemplate().get(j).getName().equals(pt))
				{
				findLineChange(l, l.getExternalPhoneNumberMask(), pt, j);
				}
			}
		}
	
	/**
	 * Method used to find a "device" data change then add a new todo
	 */
	private void findDeviceChange(device d, String currentData, patternType pt, int indexPatternContent) throws Exception
		{
		if(!methodesUtiles.isExceptionWord(currentData))
			{
			Boolean newDataIsImpossible = false;
			String problemDesc = new String("");
			String newData = new String(myUSync.getUserSyncTemplate().get(indexPatternContent).getRegex(d, myUser));//We get the theoretical value build with regex
			if(!pt.equals(patternType.lineexternalphonenumbermask))
				{
				if(isTooLong(newData))//We test if value is too long
					{
					newData = new String(myUSync.getUserSyncTemplate().get(indexPatternContent+1).getRegex(d, myUser));//We get the too long theoretical value build with regex
					if(isTooLong(newData))//We test if value is still too long
						{
						newDataIsImpossible = true;
						problemDesc = "Value is too long";
						}
					}
				if(methodesUtiles.getTargetTask("replacefrenchchar", myUSync.getTaskIndex()).equals("true"))
					{
					try
						{
						newData = ClearFrenchString.translate(newData);
						}
					catch (Exception exc)
						{
						exc.printStackTrace();
						problemDesc = exc.getMessage();
						newDataIsImpossible = true;
						}
					}
				}
			//variables.getLogger().debug("Device data comparison "+pt.name()+": '"+currentData+"' compare to '"+newData+"'");
			if(!currentData.equals(newData))
				{
				toDo myToDo = new toDo(currentData, newData, pt, new soapMessageMaker().make(pt, newData, myUSync, d), myUser.getUserid(), d.getUUID(), d.getName(), d.getType().name());
				if(newDataIsImpossible)
					{
					myToDo.setProblem(problemDesc);
					}
				myUSync.getToDoList().add(myToDo);
				}
			}
		}
	
	/**
	 * Method used to find a "line" data change then add a new todo
	 */
	private void findLineChange(line l, String currentData, patternType pt, int indexPatternContent) throws Exception
		{
		if(!methodesUtiles.isExceptionWord(currentData))
			{
			Boolean newDataIsImpossible = false;
			String problemDesc = new String("");
			String newData = new String(myUSync.getUserSyncTemplate().get(indexPatternContent).getRegex(l, myUser));
			if(!pt.equals(patternType.lineexternalphonenumbermask))
				{
				if(isTooLong(newData))//We test if value is too long
					{
					newData = new String(myUSync.getUserSyncTemplate().get(indexPatternContent+1).getRegex(l, myUser));//We get the too long theoretical value build with regex
					if(isTooLong(newData))//We test if value is still too long
						{
						newDataIsImpossible = true;
						problemDesc = "Value is too long";
						}
					}
				if(methodesUtiles.getTargetTask("replacefrenchchar", myUSync.getTaskIndex()).equals("true"))
					{
					try
						{
						newData = ClearFrenchString.translate(newData);
						}
					catch (Exception exc)
						{
						problemDesc = exc.getMessage();
						newDataIsImpossible = true;
						}
					}
				}
			
			//variables.getLogger().debug("Line data comparison "+pt.name()+" : '"+currentData+"' compare to '"+newData+"'");
			if(!currentData.equals(newData))
				{
				toDo myToDo = new toDo(currentData, newData, pt, new soapMessageMaker().make(pt, newData, myUSync, l), myUser.getUserid(), l.getUUID(), l.getPattern(), "line");
				if(newDataIsImpossible)
					{
					myToDo.setProblem(problemDesc);
					}
				myUSync.getToDoList().add(myToDo);
				}
			}
		}
	
	/**
	 * Method used to check if data is too long
	 */
	private boolean isTooLong(String str)
		{
		int maxLength = 30;
		try
			{
			maxLength = Integer.parseInt(methodesUtiles.getTargetTask("maxnumchar", myUSync.getTaskIndex()));
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage());
			}
		if(str.length()>maxLength)
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

