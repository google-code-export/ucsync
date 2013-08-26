package misc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
	
	public static String makeScanreport(userSync myUSync) throws Exception
		{
		ArrayList<toDo> myToDoList = myUSync.getToDoList();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
		StringBuffer content = new StringBuffer();
		content.append(dateFormat.format(now)+" SCAN REPORT from "+variables.getNomProg()+" ["+variables.getVersion()+"] : \r\n\r\n");
		String userID = new String(myToDoList.get(0).getUser());
		
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
					}
				content.append("\r\n");
				}
			}
		else
			{
			for(int i=0; i<myToDoList.size(); i++)
				{
				content.append("User : "+myToDoList.get(i).getUser()+", "+
						myToDoList.get(i).getDescription()+", "+
						"Data \""+myToDoList.get(i).getCurrentData()+"\""+
						" will be replaced by \""+myToDoList.get(i).getNewData()+"\"");
				if(myToDoList.get(i).isConflictDetected())
					{
					content.append("\r\nWARN : "+myToDoList.get(i).getConflictDesc());
					}
				
				if(myToDoList.get(i).getUser().compareTo(userID) != 0)
					{
					userID = myToDoList.get(i).getUser();
					content.append("\r\n-------------");
					}
				content.append("\r\n");
				}
			}
		
		//Add ack URL
		content.append("\r\nTo validate this report clic on the following link : ");
		content.append(methodesUtiles.getAckURL(myUSync.getId()));
		content.append("\r\n\r\nBe carreful, this is a local URL. It will only works from a local computer");
		
		return content.toString();
		}
	
	
	
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

