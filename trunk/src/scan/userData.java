package scan;

import java.util.ArrayList;

import misc.emptyUserException;
import misc.miscData;

import schedule.userSync;
import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to store device informations
 * 
 * @author RATEL Alexandre
 **********************************/
public class userData extends miscData
	{
	/**
	 * Variables
	 */
	private String firstName, lastName, userid, telephoneNumber, departement;
	private ArrayList<device> associatedDevice; //Contient les devices associés
	private ArrayList<line> associatedLine; //Le cas échéant, contient les lignes associées
	
	public userData(String UUID, String firstName, String lastName, String userid, String telephoneNumber, String departement, userSync myUSync) throws Exception
		{
		super(UUID,myUSync);
		this.firstName = firstName;
		this.lastName = lastName;
		this.userid = userid;
		this.telephoneNumber = telephoneNumber;
		this.departement = departement;
		
		associatedDevice = new ArrayList<device>();
		associatedLine = new ArrayList<line>();
		
		autoComplete();
		
		if((this.getAssociatedDevice().size() == 0)&&(this.getAssociatedLine().size() == 0))
			{
			//User with no associated data are excluded
			throw new emptyUserException("The user "+this.getUserid()+" has no associated data. it will be ignored");
			}
		}
	
	/**
	 * Method used to fill associated 
	 * data like device or line
	 */
	public void autoComplete() throws Exception
		{
		try
			{
			fillAssociatedDevice();
			
			if(methodesUtiles.getTargetTask("smartlinesearch",myUSync.getTaskIndex()).compareTo("true") == 0)
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
	public void fillAssociatedDevice() throws Exception
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
	public void fillAssociatedLine() throws Exception
		{
		/**
		 * Je dois améliorer cette methode :
		 * - Prendre en compte un filtre de modification du telephoneNumber
		 * - Ne pas prendre en compte une ligne déja associé à l'utilisateur via un device
		 * - Gérer une liste des objets bannis à ne pas traiter
		 */
		String number = this.telephoneNumber;
		for(int i=0; i<myUSync.getGlobalLineList().size(); i++)
			{
			for (int j=0; j<myUSync.getGlobalAssociatedLineList().size(); j++)
				{
				if((myUSync.getGlobalLineList().get(i).getPattern().compareTo(number) == 0) && (myUSync.getGlobalLineList().get(i).getUUID() == myUSync.getGlobalAssociatedLineList().get(j).getLinePkid()))
					{
					if((myUSync.getGlobalAssociatedLineList().get(j).getIndex()==1)||(methodesUtiles.getTargetTask("getmorethanprimaryline",myUSync.getTaskIndex()).compareTo("true") == 0))
						{
						this.associatedLine.add(new line(myUSync.getGlobalLineList().get(i).getUUID(), myUSync, myUSync.getGlobalAssociatedLineList().get(j).getDevicePkid()));
						}
					}
				}
			}
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

	public ArrayList<line> getAssociatedLine()
		{
		return associatedLine;
		}

	public void setAssociatedLine(ArrayList<line> associatedLine)
		{
		this.associatedLine = associatedLine;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

