package scan;

/**********************************
 * Class used to store line information
 * 
 * @author RATEL Alexandre
 **********************************/
public class line
	{
	/***
	 * Variables
	 */
	private final String UUID;
	private String alertingName, displayName, lineTextLabel, description, externalphonenumbermask;
	
	
	public line(String UUID)
		{
		this.UUID = UUID;
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

	public String getExternalphonenumbermask()
		{
		return externalphonenumbermask;
		}

	public void setExternalphonenumbermask(String externalphonenumbermask)
		{
		this.externalphonenumbermask = externalphonenumbermask;
		}

	public String getUUID()
		{
		return UUID;
		}
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

