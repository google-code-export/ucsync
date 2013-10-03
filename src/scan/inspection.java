package scan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import axlmisc.sqlQuery;
import schedule.task;
import schedule.userSync;
import utils.SOAPGear;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.taskStatusType;
import misc.emptyUserException;
import misc.report;
import misc.simpleToDo;
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
		axlversion = methodesUtiles.getTargetTask("axlversion",myUSync.getTaskIndex());
		
		variables.getLogger().info("Init AXL connection");
		String axlport = methodesUtiles.getTargetTask("axlport",myUSync.getTaskIndex());
		String axlhost = methodesUtiles.getTargetTask("axlhost",myUSync.getTaskIndex());
		String axluser = methodesUtiles.getTargetTask("axlusername",myUSync.getTaskIndex());
		String axlpassword = methodesUtiles.getTargetTask("axlpassword",myUSync.getTaskIndex());
		myUSync.setSoapGear(new SOAPGear(axlport,axlhost,axluser,axlpassword));
		
		start();
		}
	
	
	public void run()
		{
		try
			{
			/*******
			 * Step 1 : Getting data from CUCM
			 */
			if(isNotFinished)fillLists();
			/***************/
			
			/*****
			 * Step 2 : Compare data and make to do list
			 */
			if(isNotFinished)findUnSyncData();
			/***************/
			
			/*****
			 * Step 3 : Remove banned todo
			 */
			variables.getLogger().info("##Start## : Remove banned toDo list");
			if(isNotFinished)removeBannedToDo();
			/***************/
			
			/*****
			 * Step 4 : Detect and warn about detected conflict
			 */
			if(isNotFinished)findDataConflict();
			/***************/
			
			/*****
			 * Step 5 : Send a report email
			 */
			if(isNotFinished)sendEmailReport();
			/***************/
			
			
			/**
			 * finished = true
			 * 
			 * This is used to know when the task
			 * succeed to interrupt itself
			 */
			variables.setReadyToPublish(true);
			variables.getLogger().info(myUSync.getTInfo()+"##Stop## : Normal End of inspection process");
			if(!isNotFinished)
				{
				finished = true;
				}
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			variables.getLogger().error(exc.getMessage());
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+"An error occured : "+exc.getMessage()+" : Task will be deleted");
			myUSync.setStatus(taskStatusType.error);
			}
		finally
			{
			try
				{
				finished = true;
				myUSync.getSoapGear().closeCon();
				}
			catch (Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				}
			}
		}
	/**
	 * Method used to fill every Global List
	 */
	private void fillLists() throws Exception
		{
		variables.getLogger().info(myUSync.getTInfo()+"##Start## : Getting CUCM data");
		if(isNotFinished)fillAssociatedDeviceList();
		variables.getLogger().debug("List associated device user size :"+myUSync.getGlobalAssociatedDeviceList().size());
		if(isNotFinished)fillAssociatedLineList();
		variables.getLogger().debug("List associated line device size :"+myUSync.getGlobalAssociatedLineList().size());
		if(isNotFinished)fillDeviceList();
		variables.getLogger().debug("List device size :"+myUSync.getGlobalDeviceList().size());
		if(isNotFinished)fillLineList();
		variables.getLogger().debug("List line size :"+myUSync.getGlobalLineList().size());
		if(isNotFinished)fillUserList();
		}
	
	/**
	 * Main method aims to compare existing data
	 * and theoretical data
	 */
	private void findUnSyncData()
		{
		variables.getLogger().info(myUSync.getTInfo()+"##Start## : data comparison");
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
		variables.getLogger().info(myUSync.getTInfo()+"##Start## : conflict detection");
		/**
		 * Find conflict inside the ToDo list
		 */
		for(int i=0; (i<myUSync.getToDoList().size())&&(isNotFinished); i++)
			{
			if(!myUSync.getToDoList().get(i).isProblemDetected())
				{
				for(int j=i+1; j<myUSync.getToDoList().size(); j++)
					{
					if((myUSync.getToDoList().get(i).getUUID().equals(myUSync.getToDoList().get(j).getUUID())) 
							&& (myUSync.getToDoList().get(i).getType().equals(myUSync.getToDoList().get(j).getType()))
							&& (!myUSync.getToDoList().get(i).getUser().equals(myUSync.getToDoList().get(j).getUser())))
						{
						//Conflict detected
						myUSync.getToDoList().get(i).setConflict(myUSync.getToDoList().get(j).getInfo());
						myUSync.getToDoList().get(j).setConflict(myUSync.getToDoList().get(i).getInfo());
						}
					}
				}
			}
		
		/**
		 * Find potential conflict with existing data
		 */
		for(int i=0; (i<myUSync.getToDoList().size())&&(isNotFinished); i++)
			{
			if((!myUSync.getToDoList().get(i).isProblemDetected()) && (!myUSync.getToDoList().get(i).isConflictDetected()))
				{
				for(int j=0; j<myUSync.getUserList().size(); j++)
					{
					if(!myUSync.getToDoList().get(i).getUser().equals(myUSync.getUserList().get(j).getUserid()))
						{
						//Looking for conflict in line associated with user
						for(int x=0; x<myUSync.getUserList().get(j).getAssociatedLine().size(); x++)
							{
							if(myUSync.getToDoList().get(i).getUUID().equals(myUSync.getUserList().get(j).getAssociatedLine().get(x).getUUID()))
								{
								myUSync.getToDoList().get(i).setConflict(myUSync.getUserList().get(j).getUserid()+" directory number : "+
										myUSync.getUserList().get(j).getAssociatedLine().get(x).getPattern());
								}
							}
						//Looking for conflict in Device associated with user
						for(int x=0; x<myUSync.getUserList().get(j).getAssociatedDevice().size(); x++)
							{
							if(myUSync.getToDoList().get(i).getUUID().equals(myUSync.getUserList().get(j).getAssociatedDevice().get(x).getUUID()))
								{
								myUSync.getToDoList().get(i).setConflict(myUSync.getUserList().get(j).getUserid()+" device name : "+
										myUSync.getUserList().get(j).getAssociatedDevice().get(x).getName());
								}
							//Looking for conflict in line's Device associated with user
							for(int y=0; y<myUSync.getUserList().get(j).getAssociatedDevice().get(x).getAssociatedLine().size(); y++)
								{
								if(myUSync.getToDoList().get(i).getUUID().equals(myUSync.getUserList().get(j).getAssociatedDevice().get(x).getAssociatedLine().get(y).getUUID()))
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
		
		//Remove toDoList conflict duplicate
		for(int i=0; i<myUSync.getToDoList().size() ; i++)
			{
			myUSync.getToDoList().get(i).removeDuplicate();
			}
		}
	
	/**
	 * Method used to remove banned todo
	 */
	private void removeBannedToDo()
		{
		boolean duplicatesFound = false;
		
		try
			{
			if((variables.getBannedToDoList().size() >= (myUSync.getTaskIndex()+1)) && (variables.getBannedToDoList().get(myUSync.getTaskIndex()) != null))
				{
				ArrayList<simpleToDo> myBannedToDoList = variables.getBannedToDoList().get(myUSync.getTaskIndex());
				
				//Finding
				for(int i=0; (i<myBannedToDoList.size())&&(isNotFinished); i++)
					{
					for(int j=0; j<myUSync.getToDoList().size(); j++)
						{
						if(myBannedToDoList.get(i).getUUID().equals(myUSync.getToDoList().get(j).getUUID()))
							{
							myUSync.getToDoList().remove(j);
							duplicatesFound = true;
							}
						}
					}
				
				if(duplicatesFound)
					{
					removeBannedToDo();
					}
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error("ERROR during banned toDo list process : "+exc.getMessage());
			}
		}
	
	
	/**
	 * Method used to send Scan eMail report
	 */
	private void sendEmailReport() throws Exception
		{
		variables.getLogger().info("##Start## : Report email sending");
		/*
		for(int i=0; i<myUSync.getToDoList().size(); i++)
			{
			variables.getLogger().debug("##User : "+myUSync.getToDoList().get(i).getUser());
			variables.getLogger().debug("Description : "+myUSync.getToDoList().get(i).getDescription());
			variables.getLogger().debug("Current Data : "+myUSync.getToDoList().get(i).getCurrentData());
			variables.getLogger().debug("New Data : "+myUSync.getToDoList().get(i).getNewData());
			variables.getLogger().debug("SOAPMessage : "+convertSOAPToString.convert(myUSync.getToDoList().get(i).getSoapMessage()));
			variables.getLogger().debug("Type : "+myUSync.getToDoList().get(i).getType().name());
			variables.getLogger().debug("Status : "+myUSync.getToDoList().get(i).getStatus().name());
			if(myUSync.getToDoList().get(i).isConflictDetected())
				{
				for(int x=0; x<myUSync.getToDoList().get(i).getConflictList().size(); x++)
					{
					variables.getLogger().debug("WARN : "+myUSync.getToDoList().get(i).getConflictList().get(x));
					}
				}
			if(myUSync.getToDoList().get(i).isProblemDetected())
				{
				for(int x=0; x<myUSync.getToDoList().get(i).getProblemList().size(); x++)
					{
					variables.getLogger().debug("WARN : "+myUSync.getToDoList().get(i).getProblemList().get(x));
					}
				}
			}*/
		
		if(methodesUtiles.getTargetTask("ackmode", myUSync.getTaskIndex()).equals("report"))
			{
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
			
			/**
			 * In case of smtp server failure, we try to send two times the email report
			 */
			int count = 0;
			boolean isNotDone = true;
			while(isNotDone)
				{
				try
					{
					methodesUtiles.sendToAdminList("UCSync : Scan report "+myUSync.getId()+" "+dateFormat.format(now), report.makeScanReport(myUSync), "Scan report email sending");
					isNotDone = false;
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
						if(isNotDone)
							{
							sleep(5000);
							count++;
							}
						}
					catch(Exception exc)
						{
						exc.printStackTrace();
						variables.getLogger().error(exc);
						}
					}
				}
			if(myUSync.getToDoList().size() != 0)
				{
				myUSync.setStatus(taskStatusType.waitingAck);
				}
			else
				{
				variables.getLogger().info(myUSync.getTInfo()+"No unsynced data. Task will be deleted");
				myUSync.setStatus(taskStatusType.toDelete);
				}
			}
		else if(methodesUtiles.getTargetTask("ackmode", myUSync.getTaskIndex()).equals("manual"))
			{
			/**
			 * Manual mode :
			 * 
			 * Administrator have to validate report
			 * using the Management interface
			 * 
			 * No email are sent
			 */
			if(myUSync.getToDoList().size() != 0)
				{
				myUSync.setStatus(taskStatusType.waitingAck);
				}
			else
				{
				variables.getLogger().info(myUSync.getTInfo()+"No unsynced data. Task will be deleted");
				myUSync.setStatus(taskStatusType.toDelete);
				}
			}
		else
			{
			/**
			 * Auto mode
			 * 
			 * ToDo List will be processed without administrator validation
			 */
			if(myUSync.getToDoList().size() != 0)
				{
				myUSync.setStatus(taskStatusType.pending);
				}
			else
				{
				variables.getLogger().info(myUSync.getTInfo()+"No unsynced data. Task will be deleted");
				myUSync.setStatus(taskStatusType.toDelete);
				}
			}
		}
	
	private void fillUserList() throws Exception
		{
		ArrayList<userData> List = new ArrayList<userData>();
    	String req = new String("select pkid,firstname,lastname,userid,telephonenumber,department from enduser where telephonenumber != \"\" and department != \"\"");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
    	Iterator iterator = replySB.getChildElements();
    	SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
    	//return
    	Iterator ite = bodyEle.getChildElements();
    	SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
    	//Element type
    	Iterator iter = bodyElem.getChildElements();
    	while(iter.hasNext()&&isNotFinished)
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
				//variables.getLogger().debug("###User : "+ud.getUserid()+" correctly added");
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
		String req = new String("select distinct fkenduser,fkdevice from enduserdevicemap");
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext()&&isNotFinished)
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
		String req = new String();
		String regex = methodesUtiles.getTargetTask("linesearchpattern", myUSync.getTaskIndex());
		
		if((regex != null) && (!regex.equals("")))
			{
			req = new String("select map.fkdevice,map.fknumplan,map.label,map.display,map.e164mask,map.numplanindex,n.dnorpattern from devicenumplanmap map, numplan n where n.pkid=map.fknumplan and numplanindex != \"0\" and n.dnorpattern not like \""+regex+"%\"");
			}
		else
			{
			req = new String("select fkdevice,fknumplan,label,display,e164mask,numplanindex from devicenumplanmap where numplanindex != \"0\"");
			}
		
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext()&&isNotFinished)
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
		while(iter.hasNext()&&isNotFinished)
			{
			SOAPBodyElement bodyEleme = (SOAPBodyElement)iter.next();
			Iterator itera = bodyEleme.getChildElements();
			
			String UUID = new String();
			String description = new String();
			String name = new String();
			String type = new String();
			String model = new String();
			String dpname = new String();
			
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
				else if(bodyElemen.getNodeName().compareTo("dpname") == 0)
					{
					dpname = bodyElemen.getTextContent();
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
		String req = new String();
		String regex = methodesUtiles.getTargetTask("linesearchpattern", myUSync.getTaskIndex());
		
		if((regex != null) && (!regex.equals("")))
			{
			req = new String("select distinct n.pkid,n.dnorpattern,n.alertingname,n.description from numplan n,typepatternusage t,devicenumplanmap d where n.tkpatternusage=t.enum and n.pkid=d.fknumplan and t.name=\"Device\" and n.dnorpattern not like \""+regex+"\"");
			}
		else
			{
			req =  new String("select distinct n.pkid,n.dnorpattern,n.alertingname,n.description from numplan n,typepatternusage t,devicenumplanmap d where n.tkpatternusage=t.enum and n.pkid=d.fknumplan and t.name=\"Device\"");
			}
		
		SOAPBody replySB = sqlQuery.execute(req, myUSync.getSoapGear(), axlversion);
		
		Iterator iterator = replySB.getChildElements();
		SOAPBodyElement bodyEle = (SOAPBodyElement)iterator.next();
		//return
		Iterator ite = bodyEle.getChildElements();
		SOAPBodyElement bodyElem = (SOAPBodyElement)ite.next();
		//Element type
		Iterator iter = bodyElem.getChildElements();
		while(iter.hasNext()&&isNotFinished)
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

