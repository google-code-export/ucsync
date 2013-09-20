package misc;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.soap.SOAPMessage;

import utils.variables.patternType;
import utils.variables.toDoStatusType;

/**********************************
 * Class used to store ToDo data a
 * simplier way. Next it is easy to share
 * it throw an ObjectOutputStream
 * 
 * @author RATEL Alexandre
 **********************************/
public class simpleToDo implements Serializable
	{
	/**
	 * Variables
	 */
	private String description, currentData, newData, user, UUID, soapMessage;
	private toDoStatusType status;
	private ArrayList<String> conflictList, problemList;
	private boolean conflictDetected, problemDetected;
	
	/***************
	 * Constructor
	 ***************/
	public simpleToDo(String description, String currentData, String newData,
			String user, String UUID, String soapMessage,
			toDoStatusType status, ArrayList<String> conflictList,
			ArrayList<String> problemList, boolean conflictDetected,
			boolean problemDetected)
		{
		this.description = description;
		this.currentData = currentData;
		this.newData = newData;
		this.user = user;
		this.UUID = UUID;
		this.soapMessage = soapMessage;
		this.status = status;
		this.conflictList = conflictList;
		this.problemList = problemList;
		this.conflictDetected = conflictDetected;
		this.problemDetected = problemDetected;
		}

	/**
	 * Constructor used to define a new banned toDo
	 */
	public simpleToDo(String description, String user, String UUID)
		{
		this.description = description;
		this.user = user;
		this.UUID = UUID;
		this.status = toDoStatusType.banned;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public String getCurrentData()
		{
		return currentData;
		}

	public void setCurrentData(String currentData)
		{
		this.currentData = currentData;
		}

	public String getNewData()
		{
		return newData;
		}

	public void setNewData(String newData)
		{
		this.newData = newData;
		}

	public String getUser()
		{
		return user;
		}

	public void setUser(String user)
		{
		this.user = user;
		}

	public String getUUID()
		{
		return UUID;
		}

	public void setUUID(String uUID)
		{
		UUID = uUID;
		}

	public toDoStatusType getStatus()
		{
		return status;
		}

	public void setStatus(toDoStatusType status)
		{
		this.status = status;
		}

	public String getSoapMessage()
		{
		return soapMessage;
		}

	public void setSoapMessage(String soapMessage)
		{
		this.soapMessage = soapMessage;
		}

	public ArrayList<String> getConflictList()
		{
		return conflictList;
		}

	public void setConflictList(ArrayList<String> conflictList)
		{
		this.conflictList = conflictList;
		}

	public ArrayList<String> getProblemList()
		{
		return problemList;
		}

	public void setProblemList(ArrayList<String> problemList)
		{
		this.problemList = problemList;
		}

	public boolean isConflictDetected()
		{
		return conflictDetected;
		}

	public void setConflictDetected(boolean conflictDetected)
		{
		this.conflictDetected = conflictDetected;
		}

	public boolean isProblemDetected()
		{
		return problemDetected;
		}

	public void setProblemDetected(boolean problemDetected)
		{
		this.problemDetected = problemDetected;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

