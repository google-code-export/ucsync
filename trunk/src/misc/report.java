package misc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import misc.toDo.toDoStatusType;

import schedule.userSync;
import utils.convertSOAPToString;
import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to make scan and
 * execute report
 * 
 * @author RATEL Alexandre
 **********************************/
public class report
	{
	/**
	 * Variables 
	 */
	
	
	/**
	 * Method used to build CUCM Execute process report
	 */
	public synchronized static String makeExecuteReport(userSync myUSync) throws Exception
		{
		ArrayList<toDo> myToDoList = myUSync.getToDoList();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
		StringBuffer content = new StringBuffer();
		content.append(dateFormat.format(now)+" EXECUTE REPORT from "+variables.getNomProg()+" ["+variables.getVersion()+"] : \r\n\r\n");
		String userID = new String(myToDoList.get(0).getUser());
		boolean conflictPresent = false;
		boolean problemPresent = false;
		
		//Header
		if(methodesUtiles.getTargetTask("csvreport",myUSync.getTaskIndex()).compareTo("true") == 0)
			{
			content.append("Report type : CSV\r\n\r\n");
			content.append("User ID,Description,Current data,New data,Result\r\n");
			}
		else
			{
			content.append("Report type : Verbose\r\n\r\n");	
			}
		
		
		if(methodesUtiles.getTargetTask("csvreport",myUSync.getTaskIndex()).compareTo("true") == 0)
			{
			for(int i=0; i<myToDoList.size(); i++)
				{
				content.append(myToDoList.get(i).getUser()+","+
						myToDoList.get(i).getDescription()+","+
						myToDoList.get(i).getCurrentData()+","+
						myToDoList.get(i).getNewData()+","+
						myToDoList.get(i).getStatus().name());
				content.append("\r\n");
				}
			}
		else
			{
			for(int i=0; i<myToDoList.size(); i++)
				{
				if(myToDoList.get(i).getUser().compareTo(userID) != 0)
					{
					userID = myToDoList.get(i).getUser();
					content.append("\r\n-------------");
					}
				content.append("\r\n");
				
				content.append("User : "+myToDoList.get(i).getUser()+", "+
						myToDoList.get(i).getDescription()+", "+
						"Data \""+myToDoList.get(i).getCurrentData()+"\"");
				
				if(myToDoList.get(i).getStatus().equals(toDoStatusType.success))
					{
					content.append(" has been replaced by \""+myToDoList.get(i).getNewData()+"\""+
						" with "+myToDoList.get(i).getStatus().name());
					}
				else if(myToDoList.get(i).getStatus().equals(toDoStatusType.error))
					{
					content.append(" has not been replaced by \""+myToDoList.get(i).getNewData()+"\""+
							" an "+myToDoList.get(i).getStatus().name()+" appends : "+myToDoList.get(i).getSoapResult());
					}
				else if(myToDoList.get(i).getStatus().equals(toDoStatusType.disabled))
					{
					content.append(" has not been replaced by \""+myToDoList.get(i).getNewData()+"\""+
							" because this task has been "+myToDoList.get(i).getStatus().name());
					}
				else if(myToDoList.get(i).getStatus().equals(toDoStatusType.conflict))
					{
					content.append(" has not been replaced by \""+myToDoList.get(i).getNewData()+"\""+
							" "+myToDoList.get(i).getConflictDesc());
					conflictPresent = true;
					}
				else if(myToDoList.get(i).getStatus().equals(toDoStatusType.impossible))
					{
					content.append(" has not been replaced by \""+myToDoList.get(i).getNewData()+"\""+
							" because "+myToDoList.get(i).getImpossibleDesc());
					problemPresent = true;
					}
				}
			}
		
		//Footer
		content.append(getExecuteReportFooter(conflictPresent, problemPresent));
		
		return content.toString();
		}
	
	/**
	 * Method used to build CUCM scan process report
	 */
	public synchronized static String makeScanReport(userSync myUSync) throws Exception
		{
		ArrayList<toDo> myToDoList = myUSync.getToDoList();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
		StringBuffer content = new StringBuffer();
		content.append(dateFormat.format(now)+" SCAN REPORT from "+variables.getNomProg()+" ["+variables.getVersion()+"] : \r\n\r\n");
		String userID = new String(myToDoList.get(0).getUser());
		boolean conflictPresent = false;
		boolean problemPresent = false;
		
		int toDoListSize = myToDoList.size();
		int toDoListConflict = 0;
		int toDoListProblem = 0;
		
		for(int i=0; i<myToDoList.size(); i++)
			{
			if(myToDoList.get(i).isConflictDetected())toDoListConflict++;
			if(myToDoList.get(i).isProblemDetected())toDoListProblem++;
			}
		
		
		if(myToDoList.size() != 0)
			{
			//Header
			if(methodesUtiles.getTargetTask("csvreport",myUSync.getTaskIndex()).compareTo("true") == 0)
				{
				content.append("Report type : CSV\r\n\r\n");
				content.append("User ID,Description,Current data,New data,Conflict\r\n");
				}
			else
				{
				content.append("Report type : Verbose\r\n\r\n");	
				}
			
			
			if(methodesUtiles.getTargetTask("csvreport",myUSync.getTaskIndex()).compareTo("true") == 0)
				{
				for(int i=0; i<myToDoList.size(); i++)
					{
					content.append(myToDoList.get(i).getUser()+","+
							myToDoList.get(i).getDescription()+","+
							myToDoList.get(i).getCurrentData()+","+
							myToDoList.get(i).getNewData()+",");
					if(myToDoList.get(i).isConflictDetected())
						{
						content.append(myToDoList.get(i).getConflictDesc());
						conflictPresent = true;
						}
					else if(myToDoList.get(i).isProblemDetected())
						{
						content.append(myToDoList.get(i).getImpossibleDesc());
						problemPresent = true;
						}
					content.append("\r\n");
					}
				}
			else
				{
				for(int i=0; i<myToDoList.size(); i++)
					{
					if(myToDoList.get(i).getUser().compareTo(userID) != 0)
						{
						userID = myToDoList.get(i).getUser();
						content.append("\r\n-------------");
						}
					content.append("\r\n");
					
					content.append("User : "+myToDoList.get(i).getUser()+", "+
							myToDoList.get(i).getDescription()+", "+
							"Data \""+myToDoList.get(i).getCurrentData()+"\""+
							" will be replaced by \""+myToDoList.get(i).getNewData()+"\"");
					if(myToDoList.get(i).isConflictDetected())
						{
						content.append("\r\nWARN : "+myToDoList.get(i).getConflictDesc());
						conflictPresent = true;
						}
					else if(myToDoList.get(i).isProblemDetected())
						{
						content.append("\r\nWARN : "+myToDoList.get(i).getImpossibleDesc());
						problemPresent = true;
						}
					}
				}
			//Add score
			content.append("\r\n\r\nTotal : "+toDoListSize);
			content.append("\r\nConflict : "+toDoListConflict);
			content.append("\r\nProblem : "+toDoListProblem);
			content.append("\r\nTotal : "+(toDoListSize-(toDoListConflict+toDoListProblem))+" values will be updated");
			
			//Add ack URL
			content.append(getScanReportFooter(myUSync.getId(), conflictPresent, problemPresent));
			}
		else
			{
			content.append("\r\nNo unsynced data. Nothing need to be corrected");
			}
		
		return content.toString();
		}
	
	
	/**
	 * Method used to build scan report footer
	 * - Add process time
	 */
	private synchronized static String getScanReportFooter(String ID, boolean conflictPresent, boolean problemPresent) throws Exception
		{
		StringBuffer footer = new StringBuffer();
		footer.append("\r\n\r\nTo validate this report clic on the following link : ");
		footer.append(methodesUtiles.getAckURL(ID));
		footer.append("\r\n\r\nBe carreful, this is a local URL. It will only works from a local computer");
		if(conflictPresent)footer.append("\r\n\r\nIn case of conflict, please take a moment to resolve it. Otherwise, report will be complicated to understand");
		if(conflictPresent)footer.append("\r\n\r\nSome problem has been detected during scan process, please take a moment to check if it's normal");
		
		return footer.toString();
		}
	
	/**
	 * Method used to build Execute report footer
	 */
	private synchronized static String getExecuteReportFooter(boolean conflictPresent, boolean problemPresent) throws Exception
		{
		StringBuffer footer = new StringBuffer();
		
		if(conflictPresent)footer.append("\r\n\r\nIn case of conflict, please take some time to resolve it. Otherwise, report will be complicated to understand");
		if(conflictPresent)footer.append("\r\n\r\nSome problem has been detected during scan process, please take a moment to check if it's normal");
		
		return footer.toString();
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

