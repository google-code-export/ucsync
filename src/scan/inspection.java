package scan;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import axlmisc.sqlQuery;
import schedule.task;
import schedule.userSync;
import schedule.task.statusType;
import utils.methodesUtiles;
import utils.variables;
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
			fillLists();
			/***************/
			
			
			
			/*****
			 * Step 2 : Compare data and make to do list
			 */
			
			/***************/
			
			/*****
			 * Step 3 : Send a report email
			 */
			
			/***************/
			
			
			myTask.setStatus(statusType.waitingAck);
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+"An error occured : "+exc.getMessage()+" Task will be deleted");
			myUSync.setStatus(statusType.toDelete);
			}
		}
	
	private void fillLists() throws Exception
		{
		fillAssociatedDeviceList();
		fillAssociatedLineList();
		fillDeviceList();
		fillLineList();
		fillUserList();
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
    		userData ud = new userData(pkid, firstName, lastName, userID, telephoneNumber, department, myUSync);
    		List.add(ud);
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
		
		}
	
	private void fillDeviceList() throws Exception
		{
		/**
		 * Implement a filter to get only udp, phone and analog device
		 */
		
		
		}
	
	private void fillLineList() throws Exception
		{
		/**
		 * Implement a filter to get only line which is :
		 * - Associated to a device
		 * - In addition, device type has to be udp, phone or analog
		 * 
		 * I'm not sure it's possible a simple way :(
		 * 
		 * See "tkPatternUsage = Device" in the table "numPlan"
		 */
		
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

