package misc;

import javax.xml.soap.SOAPMessage;

import schedule.userSync;
import schedule.userSync.deviceType;
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
	public enum toDoStatusType{success,error,processing,waiting,delete,disabled,init};
	
	private patternType type;
	private String description, currentData, newData, soapResult;
	private SOAPMessage soapMessage;
	private userSync myUSync;
	
	public toDo(String currentData, String newData, patternType type, userSync myUSync, SOAPMessage soapMessage)
		{
		this.type = type;
		this.description = new String(type.name()+" change needed");
		this.currentData = currentData;
		this.newData = newData;
		this.soapResult = new String();
		this.myUSync = myUSync;
		this.soapMessage = soapMessage;
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
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

