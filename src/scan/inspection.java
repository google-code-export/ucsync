package scan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import axlmisc.sqlQuery;
import schedule.task;
import schedule.userSync;
import schedule.task.taskStatusType;
import utils.convertSOAPToString;
import utils.methodesUtiles;
import utils.variables;
import misc.emptyUserException;
import misc.report;
import misc.worker;
import misc.toDo.toDoStatusType;

/**********************************
 * Class used to inspect CUCM and
 * find data which has to by modify
 * 
 * @author RATEL Alexandre
 **********************************/
public class inspection extends worker
	{
	/**
	 * Variables
	 */
	private userSync myUSync;
	private String axlversion;
	
	
	public inspection(task myTask) throws Exception
		{
		super(myTask);
		myUSync = (userSync)myTask;
		axlversion = methodesUtiles.getTargetTask("axlversion",myTask.getTaskIndex());
		
		start();
		}
	
	
	public void run()
		{
		try
			{
			/*******
			 * Step 1 : Getting data from CUCM
			 */
			//Fill lists
			if(isNotFinished)fillLists();
			/***************/
			
			/*****
			 * Step 2 : Compare data and make to do list
			 */
			if(isNotFinished)findUnSyncData();
			/***************/
			
			/*****
			 * Step 3 : Detect and warn about detected conflict
			 */
			if(isNotFinished)findDataConflict();
			/***************/
			
			/*****
			 * Step 4 : Send a report email
			 */
			for(int i=0; i<myUSync.getToDoList().size(); i++)
				{
				variables.getLogger().debug("##User : "+myUSync.getToDoList().get(i).getUser());
				variables.getLogger().debug("Description : "+myUSync.getToDoList().get(i).getDescription());
				variables.getLogger().debug("Current Data : "+myUSync.getToDoList().get(i).getCurrentData());
				variables.getLogger().debug("New Data : "+myUSync.getToDoList().get(i).getNewData());
				variables.getLogger().debug("SOAPMessage : "+convertSOAPToString.convert(myUSync.getToDoList().get(i).getSoapMessage()));
				variables.getLogger().debug("Type : "+myUSync.getToDoList().get(i).getType().name());
				if(myUSync.getToDoList().get(i).isConflictDetected())
					{
					variables.getLogger().debug("WARN : "+myUSync.getToDoList().get(i).getConflictDesc());
					}
				}
			
			if(methodesUtiles.getTargetTask("ackmode", myUSync.getTaskIndex()).compareTo("report") == 0)
				{
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
				
				/**
				 * In case of smtp server failure, we try to send two times the email report
				 */
				int count = 0;
				while(true)
					{
					try
						{
						//methodesUtiles.sendToAdminList("UCSync : Scan report "+myUSync.getId()+" "+dateFormat.format(now), report.makeScanreport(myUSync), "Scan report email sending");
						break;
						}
					catch (Exception exc)
						{
						exc.printStackTrace();
						variables.getLogger().error(exc);
						variables.getLogger().error("Error during Email sending");
						if(count == 1)
							{
							throw new Exception("Too many attempts, failed to send email report, quit");
							}
						else
							{
							variables.getLogger().error("Try another time");
							}
						}
					finally
						{
						try
							{
							sleep(5000);
							count++;
							}
						catch(Exception exc)
							{
							exc.printStackTrace();
							variables.getLogger().error(exc);
							}
						}
					}
				myUSync.setStatus(taskStatusType.waitingAck);
				}
			else
				{
				/**
				 * Auto mode
				 * 
				 * ToDo List will be processed without administrator validation
				 */
				myUSync.setStatus(taskStatusType.pending);
				}
			/***************/
			
			
			/**
			 * finished = true
			 * 
			 * This is used to know when the task
			 * succeed to interrupt itself
			 */
			if(!isNotFinished)
				{
				finished = true;
				}
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+"An error occured : "+exc.getMessage()+" Task will be deleted");
			finished = true;
			myUSync.setStatus(taskStatusType.toDelete);
			}
		}
	/**
	 * Method used to fill every Global List
	 */
	private void fillLists() throws Exception
		{
		if(isNotFinished)fillAssociatedDeviceList();
		if(isNotFinished)fillAssociatedLineList();
		if(isNotFinished)fillDeviceList();
		if(isNotFinished)fillLineList();
		if(isNotFinished)fillUserList();
		}
	
	/**
	 * Main method aims to compare existing data
	 * and theoretical data
	 */
	private void findUnSyncData()
		{
		for(int i=0; (i<myUSync.getUserList().size())&&(isNotFinished) ; i++)
			{
			new userDataCompare(myUSync.getUserList().get(i), myUSync);
			}
		}
	
	/**
	 * Method used to find data conflict
	 * in the toDo list
	 */
	private void findDataConflict()
		{
		/**
		 * Find conflict inside the ToDo list
		 */
		for(int i=0; i<myUSync.getToDoList().size(); i++)
			{
			for(int j=i+1; j<myUSync.getToDoList().size(); j++)
				{
				if((myUSync.getToDoList().get(i).getUUID().compareTo(myUSync.getToDoList().get(j).getUUID()) == 0) 
						&& (myUSync.getToDoList().get(i).getType().equals(myUSync.getToDoList().get(j).getType()))
						&& (myUSync.getToDoList().get(i).getUser().compareTo(myUSync.getToDoList().get(j).getUser()) != 0))
					{
					//Conflict detected
					myUSync.getToDoList().get(i).setConflict(myUSync.getToDoList().get(j).getInfo());
					myUSync.getToDoList().get(j).setConflict(myUSync.getToDoList().get(i).getInfo());
					}
				}
			}
		
		/**
		 * Find potential conflict with existing data
		 */
		for(int i=0; i<myUSync.getToDoList().size(); i++)
			{
			for(int j=0; j<myUSync.getUserList().size(); j++)
				{
				//Looking for conflict in line associated with user
				for(int x=0; x<myUSync.getUserList().get(j).getAssociatedLine().size(); x++)
					{
					if((myUSync.getToDoList().get(i).getUUID().compareTo(myUSync.getUserList().get(j).getAssociatedLine().get(x).getUUID())== 0)
							&& (myUSync.getToDoList().get(i).getUser().compareTo(myUSync.getUserList().get(j).getUserid()) != 0))
						{
						if(!myUSync.getToDoList().get(i).isConflictDetected())
							{
							myUSync.getToDoList().get(i).setConflict(myUSync.getUserList().get(j).getUserid()+" directory number : "+
									myUSync.getUserList().get(j).getAssociatedLine().get(x).getPattern());
							}
						}
					}
				//Looking for conflict in Device associated with user
				for(int x=0; x<myUSync.getUserList().get(j).getAssociatedDevice().size(); x++)
					{
					if((myUSync.getToDoList().get(i).getUUID().compareTo(myUSync.getUserList().get(j).getAssociatedDevice().get(x).getUUID())== 0)
							&& (myUSync.getToDoList().get(i).getUser().compareTo(myUSync.getUserList().get(j).getUserid()) != 0))
						{
						if(!myUSync.getToDoList().get(i).isConflictDetected())
							{
							myUSync.getToDoList().get(i).setConflict(myUSync.getUserList().get(j).getUserid()+" device name : "+
									myUSync.getUserList().get(j).getAssociatedDevice().get(x).getName());
							}
						}
					//Looking for conflict in line's Device associated with user
					for(int y=0; y<myUSync.getUserList().get(j).getAssociatedDevice().get(x).getAssociatedLine().size(); y++)
						{
						if((myUSync.getToDoList().get(i).getUUID().compareTo(myUSync.getUserList().get(j).getAssociatedDevice().get(x).getAssociatedLine().get(y).getUUID())== 0)
								&& (myUSync.getToDoList().get(i).getUser().compareTo(myUSync.getUserList().get(j).getUserid()) != 0))
							{
							if(!myUSync.getToDoList().get(i).isConflictDetected())
								{
								myUSync.getToDoList().get(i).setConflict(myUSync.getUserList().get(j).getUserid()+" directory number : "+
										myUSync.getUserList().get(j).getAssociatedDevice().get(x).getAssociatedLine().get(y).getPattern());
								}
							}
						}
					}
				}
			}
		}
	
	private void fillUserList() throws Exception
		{
		ArrayList<userData> List = new ArrayList<userData>();
    	String req = new String("select pkid,firstname,lastname,userid,telephonenumber,department from enduser");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
    	Iterator iterator = replySB.getChildElements();
    	SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
    	//return
    	Iterator ite = bodyEle.getChildElements();
    	SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
    	//Element type
    	Iterator iter = bodyElem.getChildElements();
    	while(iter.hasNext())
    		{
    		SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
    		Iterator itera = bodyEleme.getChildElements();
    		
    		String pkid = new String();
    		String firstName = new String();
    		String lastName = new String();
    		String userID = new String();
    		String telephoneNumber = new String();
    		String department = new String();
    		
    		while(itera.hasNext())
    			{
    			SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
    			if(bodyElemen.getNodeName().compareTo("pkid") == 0)
    				{
    				pkid = bodyElemen.getTextContent();
    				}
    			else if(bodyElemen.getNodeName().compareTo("firstname") == 0)
    				{
    				firstName = bodyElemen.getTextContent();
    				}
    			else if(bodyElemen.getNodeName().compareTo("lastname") == 0)
    				{
    				lastName = bodyElemen.getTextContent();
    				}
    			else if(bodyElemen.getNodeName().compareTo("userid") == 0)
    				{
    				userID = bodyElemen.getTextContent();
    				}
    			else if(bodyElemen.getNodeName().compareTo("telephonenumber") == 0)
    				{
    				telephoneNumber = bodyElemen.getTextContent();
    				}
    			else if(bodyElemen.getNodeName().compareTo("department") == 0)
    				{
    				department = bodyElemen.getTextContent();
    				}
    			}
    		
			try
				{
				userData ud = new userData(pkid, firstName, lastName, userID, telephoneNumber, department, myUSync);
				List.add(ud);
				}
			catch (emptyUserException euexc)
				{
				variables.getLogger().debug(myUSync.getTInfo()+euexc.getMessage());
				}
    		}
    	
		myUSync.setUserList(List);
		}
	
	private void fillAssociatedDeviceList() throws Exception
		{
		ArrayList<userAssociatedDevice> List = new ArrayList<userAssociatedDevice>();
		String req = new String("select fkenduser,fkdevice from enduserdevicemap");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext())
			{
			SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
			Iterator itera = bodyEleme.getChildElements();
			
			String fkenduser = new String();
			String fkdevice = new String();
			
			while(itera.hasNext())
				{
				SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
				if(bodyElemen.getNodeName().compareTo("fkenduser") == 0)
					{
					fkenduser = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("fkdevice") == 0)
					{
					fkdevice = bodyElemen.getTextContent();
					}
				}
			userAssociatedDevice uad = new userAssociatedDevice(fkenduser, fkdevice);
			List.add(uad);
			}
		
		//We remove duplicate
		List = removeDuplicateAssociatedUser(List);
		
		myUSync.setGlobalAssociatedDeviceList(List);
		}
	
	private void fillAssociatedLineList() throws Exception
		{
		ArrayList<deviceAssociatedLine> List = new ArrayList<deviceAssociatedLine>();
		String req = new String("select fkdevice,fknumplan,label,display,e164mask,numplanindex from devicenumplanmap where numplanindex != 0");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext())
			{
			SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
			Iterator itera = bodyEleme.getChildElements();
			
			String fkdevice = new String();
			String fkline = new String();
			String label = new String();
			String display = new String();
			String mask = new String();
			String index = new String();
			
			while(itera.hasNext())
				{
				SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
				if(bodyElemen.getNodeName().compareTo("fknumplan") == 0)
					{
					fkline = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("fkdevice") == 0)
					{
					fkdevice = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("label") == 0)
					{
					label = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("display") == 0)
					{
					display = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("e164mask") == 0)
					{
					mask = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("numplanindex") == 0)
					{
					index = bodyElemen.getTextContent();
					}
				}
			deviceAssociatedLine dal = new deviceAssociatedLine(fkdevice, fkline, display, label, mask, Integer.parseInt(index));
			List.add(dal);
			}
		
		myUSync.setGlobalAssociatedLineList(List);
		}
	
	private void fillDeviceList() throws Exception
		{
		/**
		 * A filter to get only udp, phone and analog device is implemented
		 */
		ArrayList<device> List = new ArrayList<device>();
		String req = new String("select d.pkid,d.name,d.description,t.name as type,m.name as model from device d,typeclass t,typemodel m where d.tkclass=t.enum and d.tkmodel=m.enum and (t.name=\"Phone\" or t.name=\"Device Profile\") and d.name NOT LIKE \"ModelProfileFor%\"");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext())
			{
			SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
			Iterator itera = bodyEleme.getChildElements();
			
			String UUID = new String();
			String description = new String();
			String name = new String();
			String type = new String();
			String model = new String();
			
			while(itera.hasNext())
				{
				SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
				if(bodyElemen.getNodeName().compareTo("pkid") == 0)
					{
					UUID = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("name") == 0)
					{
					name = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("description") == 0)
					{
					description = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("type") == 0)
					{
					type = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("model") == 0)
					{
					model = bodyElemen.getTextContent();
					}
				}
			device d = new device(UUID, myUSync, description, name, methodesUtiles.getDeviceTypeFromString(type), model);
			List.add(d);
			}
		
		myUSync.setGlobalDeviceList(List);
		}
	
	private void fillLineList() throws Exception
		{
		/**
		 * The following filters are implemented :
		 * - Get only line associated to a device
		 * - Get only line with usage is "Device"
		 */
		ArrayList<line> List = new ArrayList<line>();
		String req = new String("select distinct n.pkid,n.dnorpattern,n.alertingname,n.description from numplan n,typepatternusage t,devicenumplanmap d where n.tkpatternusage=t.enum and n.pkid=d.fknumplan and t.name=\"Device\"");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext())
			{
			SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
			Iterator itera = bodyEleme.getChildElements();
			
			String UUID = new String();
			String pattern = new String();
			String alertingName = new String();
			String description = new String();
			
			while(itera.hasNext())
				{
				SOAPBodyElement bodyElemen = (SOAPBodyElement)itera.next();
				if(bodyElemen.getNodeName().compareTo("pkid") == 0)
					{
					UUID = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("dnorpattern") == 0)
					{
					pattern = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("alertingname") == 0)
					{
					alertingName = bodyElemen.getTextContent();
					}
				else if(bodyElemen.getNodeName().compareTo("description") == 0)
					{
					description = bodyElemen.getTextContent();
					}
				}
			line l = new line(UUID, myUSync, pattern, alertingName, description);
			List.add(l);
			}
		
		myUSync.setGlobalLineList(List);
		}
	
	private synchronized static ArrayList<userAssociatedDevice> removeDuplicateAssociatedUser(ArrayList<userAssociatedDevice> listIndex)
		{
		boolean rem = false;
		String currentData;
		for(int i=0; i<listIndex.size(); i++)
			{
			currentData = listIndex.get(i).getDevicePkid()+listIndex.get(i).getUserPkid();
			for(int j=i+1; j<listIndex.size(); j++)
				{
				if(currentData.compareTo(listIndex.get(j).getDevicePkid()+listIndex.get(j).getUserPkid()) == 0)
					{
					listIndex.remove(j);
					rem = true;
					}
				}
			}
		if(rem)
			{
			removeDuplicateAssociatedUser(listIndex);
			}
		else
			{
			return listIndex;
			}
		return listIndex;
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

