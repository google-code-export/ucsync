package scan;

import java.util.ArrayList;
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
import misc.worker;

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
			 * Step 3 : Send a report email
			 */
			for(int i=0; i<myUSync.getToDoList().size(); i++)
				{
				variables.getLogger().debug("Current Data : "+myUSync.getToDoList().get(i).getCurrentData());
				variables.getLogger().debug("New Data : "+myUSync.getToDoList().get(i).getNewData());
				variables.getLogger().debug("Description : "+myUSync.getToDoList().get(i).getDescription());
				variables.getLogger().debug("SOAPMessage : "+convertSOAPToString.convert(myUSync.getToDoList().get(i).getSoapMessage()));
				variables.getLogger().debug("Type : "+myUSync.getToDoList().get(i).getType().name());
				}
			/***************/
			
			
			/**
			 * finished = true
			 * 
			 * This is used to know when the task
			 * succeed to interrupt itself
			 * 
			 * The normal ending is to put the task in 
			 * waiting ack
			 */
			if(isNotFinished)
				{
				myTask.setStatus(taskStatusType.waitingAck);
				}
			else
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
	 * - Have to remove duplicate data !!
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
	
	/*2013*//*RATEL Alexandre 8)*/
	}

