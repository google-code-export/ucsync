package schedule;

import java.util.ArrayList;

import scan.device;
import scan.deviceAssociatedLine;
import scan.inspection;
import scan.line;
import scan.patternContent;
import scan.userAssociatedDevice;
import scan.userData;
import utils.SOAPGear;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.patternType;
import utils.variables.taskStatusType;
import utils.variables.taskType;
import execute.modify;

import misc.toDo;

/**********************************
 * Class used to manage userSync Process
 * 
 * @author RATEL Alexandre
 **********************************/
public class userSync extends task
	{
	/***
	 * Variables
	 */
	public enum deviceType{phone,deviceprofile,analog};
	private SOAPGear soapGear;
	private ArrayList<userData> userList;
	private ArrayList<device> globalDeviceList;
	private ArrayList<userAssociatedDevice> globalAssociatedDeviceList;
	private ArrayList<deviceAssociatedLine> globalAssociatedLineList;
	private ArrayList<line> globalLineList;
	private ArrayList<patternContent> userSyncTemplate;
	
	
	public userSync(int taskIndex) throws Exception
		{
		super(taskIndex,taskType.userSync);
		
		userSyncTemplate = new ArrayList<patternContent>();
		fillUserSyncTemplate();
		}
	
	/**
	 * Method used to fill the toDo list
	 */
	public void fillToDoList()
		{
		try
			{
			myWorker = new inspection(this);
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error(this.getTInfo()+"An error occured. Task will be deleted");
			this.setStatus(taskStatusType.toDelete);
			}
		}
	
	/**
	 * Method used to execute the toDo list
	 */
	public void executeToDoList()
		{
		try
			{
			if(methodesUtiles.getTargetTask("testmode", this.getTaskIndex()).compareTo("true") == 0)
				{
				variables.getLogger().info(this.getTInfo()+"Test mode activated. task will be deleted");
				this.setStatus(taskStatusType.toDelete);
				}
			else
				{
				myWorker = new modify(this);
				}
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error(this.getTInfo()+"An error occured. Task will be deleted");
			this.setStatus(taskStatusType.toDelete);
			}
		}

	
	/**
	 * Method used to get Task pattern
	 * 
	 * these pattern will be used next to
	 * find and modify CUCM user data
	 */
	public void fillUserSyncTemplate() throws Exception
		{
		for(patternType pt : patternType.values())
			{
			try
				{
				userSyncTemplate.add(new patternContent(pt, this));
				}
			catch(IllegalArgumentException iaexc)
				{
				variables.getLogger().debug(getTInfo()+iaexc.getMessage()+" : Pattern "+pt.name()+" is empty : IGNORED");
				}
			catch(Exception exc)
				{
				throw exc;
				}
			}
		
		}
	

	public ArrayList<userData> getUserList()
		{
		return userList;
		}

	public void setUserList(ArrayList<userData> userList)
		{
		this.userList = userList;
		}

	public ArrayList<device> getGlobalDeviceList()
		{
		return globalDeviceList;
		}

	public void setGlobalDeviceList(ArrayList<device> globalDeviceList)
		{
		this.globalDeviceList = globalDeviceList;
		}

	public ArrayList<userAssociatedDevice> getGlobalAssociatedDeviceList()
		{
		return globalAssociatedDeviceList;
		}

	public void setGlobalAssociatedDeviceList(ArrayList<userAssociatedDevice> globalAssociatedDeviceList)
		{
		this.globalAssociatedDeviceList = globalAssociatedDeviceList;
		}

	public ArrayList<deviceAssociatedLine> getGlobalAssociatedLineList()
		{
		return globalAssociatedLineList;
		}

	public void setGlobalAssociatedLineList(ArrayList<deviceAssociatedLine> globalAssociatedLineList)
		{
		this.globalAssociatedLineList = globalAssociatedLineList;
		}

	public ArrayList<line> getGlobalLineList()
		{
		return globalLineList;
		}

	public void setGlobalLineList(ArrayList<line> globalLineList)
		{
		this.globalLineList = globalLineList;
		}

	public SOAPGear getSoapGear()
		{
		return soapGear;
		}

	public ArrayList<patternContent> getUserSyncTemplate()
		{
		return userSyncTemplate;
		}

	public void setUserSyncTemplate(ArrayList<patternContent> userSyncTemplate)
		{
		this.userSyncTemplate = userSyncTemplate;
		}

	public void setSoapGear(SOAPGear soapGear)
		{
		this.soapGear = soapGear;
		}
	

	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

