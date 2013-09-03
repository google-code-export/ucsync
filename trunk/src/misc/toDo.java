package misc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.soap.SOAPMessage;
import utils.variables.patternType;
import utils.variables.toDoStatusType;

/**********************************
 * Class used to store data which 
 * have to be modify in the cucm
 * 
 * @author RATEL Alexandre
 **********************************/
public class toDo
	{
	/**
	 * Variables
	 */
	private patternType type;
	private String description, currentData, newData, soapResult, user, UUID, dataType;
	private SOAPMessage soapMessage;
	private toDoStatusType status;
	private ArrayList<String> conflictList, problemList;
	private boolean conflictDetected, problemDetected;
	
	public toDo(String currentData, String newData, patternType type, SOAPMessage soapMessage, String user, String UUID, String dataDesc, String dataType)
		{
		this.type = type;
		this.description = new String(dataDesc+" "+dataType+" "+type.name()+" change needed");
		this.currentData = currentData;
		this.newData = newData;
		this.soapResult = new String("");
		this.conflictList = new ArrayList<String>();
		this.problemList = new ArrayList<String>();
		this.soapMessage = soapMessage;
		this.user = user;
		this.status = toDoStatusType.waiting;
		this.UUID = UUID;
		this.conflictDetected = false;
		this.problemDetected = false;
		this.dataType = dataType;
		}

	/**
	 * Method used to define conflict 
	 */
	public void setConflict(String dataInfo)
		{
		this.setStatus(toDoStatusType.conflict);
		this.conflictDetected = true;
		conflictList.add(new String("A conflict has been detected with \""+dataInfo+"\" this data will not be updated"));
		}
	
	/**
	 * Method used to define problem 
	 */
	public void setProblem(String dataInfo)
		{
		this.setStatus(toDoStatusType.impossible);
		this.problemDetected = true;
		problemList.add(new String("A problem has been detected : \""+dataInfo+"\" this data will not be updated"));
		}
	
	public String getInfo()
		{
		return user+" "+description;
		}
	
	public void removeDuplicate()
		{
		Set setItems = new LinkedHashSet(conflictList);
		conflictList.clear();
		conflictList.addAll(setItems);
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

	public String getSoapResult()
		{
		return soapResult;
		}

	public void setSoapResult(String soapResult)
		{
		this.soapResult = soapResult;
		}

	public SOAPMessage getSoapMessage()
		{
		return soapMessage;
		}

	public void setSoapMessage(SOAPMessage soapMessage)
		{
		this.soapMessage = soapMessage;
		}

	public patternType getType()
		{
		return type;
		}

	public void setType(patternType type)
		{
		this.type = type;
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

	public boolean isConflictDetected()
		{
		return conflictDetected;
		}

	public void setConflictDetected(boolean conflictDetected)
		{
		this.conflictDetected = conflictDetected;
		}

	public String getDataType()
		{
		return dataType;
		}

	public void setDataType(String dataType)
		{
		this.dataType = dataType;
		}

	public boolean isProblemDetected()
		{
		return problemDetected;
		}

	public void setProblemDetected(boolean problemDetected)
		{
		this.problemDetected = problemDetected;
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
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

