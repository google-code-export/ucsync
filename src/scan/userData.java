package scan;

import java.util.ArrayList;

import schedule.userSync;
import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to store device informations
 * 
 * @author RATEL Alexandre
 **********************************/
public class userData
	{
	/**
	 * Variables
	 */
	private userSync myUSync;
	private final String UUID; 
	private String firstName, lastName, userid, telephoneNumber, departement;
	private ArrayList<device> associatedDevice; //Contient les devices associ�s
	private ArrayList<line> associatedLine; //Le cas �ch�ant, contient les lignes associ�es
	
	public userData(String UUID, String firstName, String lastName, String userid, String telephoneNumber, String departement, userSync myUSync) throws Exception
		{
		this.UUID = UUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userid = userid;
		this.telephoneNumber = telephoneNumber;
		this.departement = departement;
		this.myUSync = myUSync;
		
		associatedDevice = new ArrayList<device>();
		associatedLine = new ArrayList<line>();
		
		fillAssociated();
		}
	
	/**
	 * Method used to fill associated 
	 * data like device or line
	 */
	public void fillAssociated() throws Exception
		{
		try
			{
			fillAssociatedDevice();
			
			if(methodesUtiles.getTargetTask("smartlinesearch",myUSync.getTaskIndex()).compareTo("") == 0)
				{
				fillAssociatedLine();
				}
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			throw new Exception(myUSync.getTInfo()+"ERROR during associated device research");
			}
		}
	
	
	
	
	/**
	 * Method used to fill the
	 * associatedDevice Arraylist
	 */
	public void fillAssociatedDevice()
		{
		/****
		 * In the first time I will get only 
		 * UUIDs of associated device
		 * I mixed udp, device and analog cause they are all device
		 * in the CUCM database
		 */
		for(int i=0; i<myUSync.getGlobalAssociatedDeviceList().size(); i++)
			{
			if(myUSync.getGlobalAssociatedDeviceList().get(i).getUserPkid().compareTo(UUID) == 0)
				{
				associatedDevice.add(new device(myUSync.getGlobalAssociatedDeviceList().get(i).getDevicePkid(), myUSync));
				}
			}
		}
	
	/**
	 * Method used to fill the
	 * associatedLine Arraylist
	 */
	public void fillAssociatedLine()
		{
		
		
		}
	
	
	/**
	 * Getters and setters
	 */

	public String getFirstName()
		{
		return firstName;
		}

	public void setFirstName(String firstName)
		{
		this.firstName = firstName;
		}

	public String getLastName()
		{
		return lastName;
		}

	public void setLastName(String lastName)
		{
		this.lastName = lastName;
		}

	public String getUserid()
		{
		return userid;
		}

	public void setUserid(String userid)
		{
		this.userid = userid;
		}

	public String getTelephoneNumber()
		{
		return telephoneNumber;
		}

	public void setTelephoneNumber(String telephoneNumber)
		{
		this.telephoneNumber = telephoneNumber;
		}

	public String getDepartement()
		{
		return departement;
		}

	public void setDepartement(String departement)
		{
		this.departement = departement;
		}

	public ArrayList<device> getAssociatedDevice()
		{
		return associatedDevice;
		}

	public void setAssociatedDevice(ArrayList<device> associatedDevice)
		{
		this.associatedDevice = associatedDevice;
		}

	public String getUUID()
		{
		return UUID;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
