package execute;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import schedule.task;
import schedule.userSync;
import utils.SOAPGear;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.taskStatusType;
import utils.variables.toDoStatusType;
import misc.report;
import misc.worker;

/**********************************
 * Class used to commit modification
 * found during the scan process
 * 
 * @author RATEL Alexandre
 **********************************/
public class modify extends worker
	{
	/**
	 * Variables
	 */
	private userSync myUSync;
	private String axlVersion;
	
	public modify(task myTask) throws Exception
		{
		super(myTask);
		myUSync = (userSync)myTask;
		axlVersion = methodesUtiles.getTargetTask("axlversion",myUSync.getTaskIndex());
		
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
			 * Step 1 : Execute toDo List
			 */
			if(isNotFinished)executeToDoList();
			/***************/
			
			/*****
			 * Step 2 : Send a report email
			 */
			if(isNotFinished)sendEmailReport();
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
			variables.getLogger().error(exc.getMessage());
			exc.printStackTrace();
			variables.getLogger().error(myUSync.getTInfo()+"An error occured : "+exc.getMessage()+" Task will be deleted");
			myUSync.setStatus(taskStatusType.toDelete);
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
	 * Method used to send Scan eMail report
	 */
	private void sendEmailReport() throws Exception
		{
		variables.getLogger().info("##Start## : Execution report email sending");
		for(int i=0; i<myUSync.getToDoList().size(); i++)
			{
			variables.getLogger().debug("##User : "+myUSync.getToDoList().get(i).getUser());
			variables.getLogger().debug("Status : "+myUSync.getToDoList().get(i).getStatus().name());
			}
		
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
				methodesUtiles.sendToAdminList("UCSync : Execute report "+myUSync.getId()+" "+dateFormat.format(now), report.makeExecuteReport(myUSync), "Execute report email sending");
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
		myUSync.setStatus(taskStatusType.done);	
		}
	
	/**
	 * Method used to execute each element of the 
	 * toDo ArrayList
	 */
	private void executeToDoList()
		{
		variables.getLogger().info("##Start## : ToDO List execution");
		for(int i=0;((i<myUSync.getToDoList().size()) && (isNotFinished)); i++)
			{
			/**
			 * We need to try to execute every request
			 * So we put a try catch block to manage
			 * exception here 
			 */
			try
				{
				if((myUSync.getToDoList().get(i).getStatus().equals(toDoStatusType.disabled))
						||(myUSync.getToDoList().get(i).getStatus().equals(toDoStatusType.conflict))
						|| (myUSync.getToDoList().get(i).getStatus().equals(toDoStatusType.impossible)))
					{
					variables.getLogger().info("ToDo : "+myUSync.getToDoList().get(i).getDescription()+" has not been executed : "+myUSync.getToDoList().get(i).getStatus().name());
					myUSync.getToDoList().get(i).setSoapResult("ToDo : "+myUSync.getToDoList().get(i).getDescription()+" has not been executed : "+myUSync.getToDoList().get(i).getStatus().name());
					}
				else
					{
					SOAPMessage soapAnswer = myUSync.getSoapGear().execute(myUSync.getToDoList().get(i).getSoapMessage());
					if (soapAnswer != null)
			        	{
			            SOAPPart replySP = soapAnswer.getSOAPPart();
			            SOAPEnvelope replySE = replySP.getEnvelope();
			            SOAPBody replySB = replySE.getBody();

			            if (replySB.hasFault())
			            	{
			                throw new Exception(replySB.getFault().getFaultString());
			            	}
			        	}
					
					myUSync.getToDoList().get(i).setStatus(toDoStatusType.success);
					myUSync.getToDoList().get(i).setSoapResult("Processed with success");
					}
				}
			catch (Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				variables.getLogger().error("ERROR : the request \""+myUSync.getToDoList().get(i).getDescription()+"\" throwed an exception : "+exc.getMessage());
				myUSync.getToDoList().get(i).setStatus(toDoStatusType.error);
				myUSync.getToDoList().get(i).setSoapResult(exc.getMessage());
				}
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

