package scan;

import java.util.ArrayList;

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
	private final String UUID; 
	private String firstName, lastName, userid, telephoneNumber, departement;
	private ArrayList<device> associatedDevice;
	
	public userData(String UUID, String firstName, String lastName, String userid, String telephoneNumber, String departement)
		{
		this.UUID = UUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userid = userid;
		this.telephoneNumber = telephoneNumber;
		this.departement = departement;
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
		 * I mixed udp, device and line cause they are all device
		 * in the CUCM database
		 */
		
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

