package misc;

import javax.xml.soap.SOAPMessage;
import schedule.userSync.patternType;

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
	public enum toDoStatusType{success,error,processing,waiting,delete,disabled,init,conflict};
	
	private patternType type;
	private String description, currentData, newData, soapResult, user, UUID, conflictDesc, dataType;
	private SOAPMessage soapMessage;
	private toDoStatusType status;
	private boolean conflictDetected;
	
	public toDo(String currentData, String newData, patternType type, SOAPMessage soapMessage, String user, String UUID, String dataDesc, String dataType)
		{
		this.type = type;
		this.description = new String(dataDesc+" "+dataType+" "+type.name()+" change needed");
		this.currentData = currentData;
		this.newData = newData;
		this.soapResult = new String("");
		this.conflictDesc = new String("");
		this.soapMessage = soapMessage;
		this.user = user;
		this.status = toDoStatusType.waiting;
		this.UUID = UUID;
		this.conflictDetected = false;
		this.dataType = dataType;
		}

	
	public void setConflict(String dataInfo)
		{
		this.setStatus(toDoStatusType.conflict);
		this.conflictDetected = true;
		conflictDesc = new String("A conflict has been detected with \""+dataInfo+"\" this data will not be updated");
		}
	
	public String getInfo()
		{
		return user+" "+description;
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

	public String getConflictDesc()
		{
		return conflictDesc;
		}

	public void setConflictDesc(String conflictDesc)
		{
		this.conflictDesc = conflictDesc;
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
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

