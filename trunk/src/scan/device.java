package scan;

import java.util.ArrayList;

import misc.miscData;

import schedule.userSync;
import schedule.userSync.deviceType;
import utils.methodesUtiles;

/**********************************
 * Class used to store device information
 * 
 * @author RATEL Alexandre
 **********************************/
public class device extends miscData
	{
	/**
	 * Variables
	 */
	private deviceType type;
	private String description, name, model;
	private ArrayList<line> associatedLine;
	
	/**
	 * Constructor used for device association
	 */
	public device(String UUID, userSync myUSync) throws Exception
		{
		super(UUID,myUSync);
		associatedLine = new ArrayList<line>();
		
		autoComplete();
		}
	
	/**
	 * Constructor used for data storage only
	 */
	public device(String UUID, userSync myUSync, String description, String name, deviceType type, String model) throws Exception
		{
		super(UUID,myUSync);
		this.description = description;
		this.name = name;
		this.type = type;
		this.model = model;
		}
	
	
	public void autoComplete() throws Exception
		{
		fillpersonalDetail();
		fillAssociatedLine();
		}
	
	
	/**
	 * Method used to fill the
	 * associatedLine Arraylist
	 */
	private void fillAssociatedLine() throws Exception
		{
		for(int i=0; i<myUSync.getGlobalAssociatedLineList().size(); i++)
			{
			if(myUSync.getGlobalAssociatedLineList().get(i).getDevicePkid().compareTo(UUID) == 0)
				{
				if((myUSync.getGlobalAssociatedLineList().get(i).getIndex()<1)||(methodesUtiles.getTargetTask("getmorethanprimaryline").compareTo("true") == 0))
					{
					line myLine = new line(myUSync.getGlobalAssociatedLineList().get(i).getLinePkid(), myUSync, this.getUUID());
					myLine.setDisplayName(myUSync.getGlobalAssociatedLineList().get(i).getDisplayName());
					myLine.setLineTextLabel(myUSync.getGlobalAssociatedLineList().get(i).getLineTextLabel());
					myLine.setExternalPhoneNumberMask(myUSync.getGlobalAssociatedLineList().get(i).getExternalPhoneNumberMask());
					myLine.setIndex(myUSync.getGlobalAssociatedLineList().get(i).getIndex());
					associatedLine.add(myLine);
					}
				}
			}
		}
	
	/**
	 * Method used to get device personal detail
	 */
	private void fillpersonalDetail() throws Exception
		{
		for(int i=0; i<myUSync.getGlobalDeviceList().size(); i++)
			{
			if(myUSync.getGlobalDeviceList().get(i).getUUID().compareTo(UUID) == 0)
				{
				this.name = myUSync.getGlobalDeviceList().get(i).getName();
				this.description = myUSync.getGlobalDeviceList().get(i).getDescription();
				this.type = myUSync.getGlobalDeviceList().get(i).getType();
				this.model = myUSync.getGlobalDeviceList().get(i).getModel();
				}
			}
		}
	
	/**
	 * Getters and Setters
	 */
	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public ArrayList<line> getAssociatedLine()
		{
		return associatedLine;
		}

	public void setAssociatedLine(ArrayList<line> associatedLine)
		{
		this.associatedLine = associatedLine;
		}

	public String getName()
		{
		return name;
		}

	public void setName(String name)
		{
		this.name = name;
		}

	public deviceType getType()
		{
		return type;
		}

	public void setType(deviceType type)
		{
		this.type = type;
		}

	public String getModel()
		{
		return model;
		}

	public void setModel(String model)
		{
		this.model = model;
		}
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

