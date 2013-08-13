package scan;

import schedule.userSync;
import misc.miscData;

/**********************************
 * Class used to store line information
 * 
 * @author RATEL Alexandre
 **********************************/
public class line extends miscData
	{
	/***
	 * Variables
	 */
	private String pattern,alertingName, displayName, lineTextLabel, description, externalPhoneNumberMask;
	private int index;
	
	/**
	 * Constructor used for line association
	 */
	public line(String UUID, userSync myUSync) throws Exception
		{
		super(UUID, myUSync);
		
		autoComplete();
		}

	/**
	 * Constructor used for data storage only
	 */
	public line(String UUID, userSync myUSync, String pattern, String alertingName, String description) throws Exception
		{
		super(UUID, myUSync);
		
		this.pattern = pattern;
		this.alertingName = alertingName;
		this.description = description;
		}
	
	
	
	public void autoComplete() throws Exception
		{
		fillpersonalDetail();
		}


	/**
	 * Method used to get line personal detail
	 */
	private void fillpersonalDetail() throws Exception
		{
		for(int i=0; i<myUSync.getGlobalLineList().size(); i++)
			{
			if(myUSync.getGlobalLineList().get(i).getUUID().compareTo(UUID) == 0)
				{
				this.pattern = myUSync.getGlobalLineList().get(i).getPattern();
				this.alertingName = myUSync.getGlobalLineList().get(i).getAlertingName();
				this.description = myUSync.getGlobalLineList().get(i).getDescription();
				}
			}
		}
	
	
	/**
	 * Getters and setters
	 */
	public String getAlertingName()
		{
		return alertingName;
		}

	public void setAlertingName(String alertingName)
		{
		this.alertingName = alertingName;
		}

	public String getDisplayName()
		{
		return displayName;
		}

	public void setDisplayName(String displayName)
		{
		this.displayName = displayName;
		}

	public String getLineTextLabel()
		{
		return lineTextLabel;
		}

	public void setLineTextLabel(String lineTextLabel)
		{
		this.lineTextLabel = lineTextLabel;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public String getExternalPhoneNumberMask()
		{
		return externalPhoneNumberMask;
		}

	public String getUUID()
		{
		return UUID;
		}

	public String getPattern()
		{
		return pattern;
		}

	public void setPattern(String pattern)
		{
		this.pattern = pattern;
		}

	public int getIndex()
		{
		return index;
		}

	public void setIndex(int index)
		{
		this.index = index;
		}

	public void setExternalPhoneNumberMask(String externalPhoneNumberMask)
		{
		this.externalPhoneNumberMask = externalPhoneNumberMask;
		}
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

