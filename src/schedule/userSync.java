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
	public enum patternType {devicedescription,devicedescriptiontoolong,
							linedescription,linedescriptiontoolong,
							alertingname,alertingnametoolong,
							display,displaytoolong,
							linetextlabel,linetextlabeltoolong,
							externalphonenumbermask};
	public enum deviceType{phone,udp,analog};
	private patternType pattern;
	private ArrayList<toDo> toDoList;
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
		
		variables.getLogger().info("Init AXL connection");
		String axlport = methodesUtiles.getTargetTask("axlport",this.getTaskIndex());
		String axlhost = methodesUtiles.getTargetTask("axlhost",this.getTaskIndex());
		String axluser = methodesUtiles.getTargetTask("axlusername",this.getTaskIndex());
		String axlpassword = methodesUtiles.getTargetTask("axlpassword",this.getTaskIndex());
		
		soapGear = new SOAPGear(axlport,axlhost,axluser,axlpassword);
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
	
	public void fillUserSyncTemplate() throws Exception
		{
		for(patternType pt : patternType.values())
			{
			try
				{
				userSyncTemplate.add(new patternContent(pt.name(), this));
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
	
	/**
	 * Method used to execute the toDo list
	 */
	public void executeToDoList()
		{
		myWorker = new modify(this);
		}

	public ArrayList<toDo> getToDoList()
		{
		return toDoList;
		}

	public void setToDoList(ArrayList<toDo> toDoList)
		{
		this.toDoList = toDoList;
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
	

	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

