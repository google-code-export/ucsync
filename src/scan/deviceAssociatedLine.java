package scan;

/**********************************
 * Class used to store data about
 * device line association
 * 
 * @author RATEL Alexandre
 **********************************/
public class deviceAssociatedLine
	{
	/**
	 * Variables
	 */
	private String devicePkid, linePkid;
	private String displayName, lineTextLabel, externalPhoneNumberMask;
	private int index;
	
	
	public deviceAssociatedLine(String devicePkid, String linePkid, String displayName, String lineTextLabel, String externalPhoneNumberMask, int index)
		{
		this.devicePkid = devicePkid;
		this.linePkid = linePkid;
		this.displayName = displayName;
		this.lineTextLabel = lineTextLabel;
		this.externalPhoneNumberMask = externalPhoneNumberMask;
		this.index = index;
		}

	

	public String getDevicePkid()
		{
		return devicePkid;
		}

	public void setDevicePkid(String devicePkid)
		{
		this.devicePkid = devicePkid;
		}

	public String getLinePkid()
		{
		return linePkid;
		}

	public void setLinePkid(String linePkid)
		{
		this.linePkid = linePkid;
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

	public String getExternalPhoneNumberMask()
		{
		return externalPhoneNumberMask;
		}

	public void setExternalPhoneNumberMask(String externalPhoneNumberMask)
		{
		this.externalPhoneNumberMask = externalPhoneNumberMask;
		}

	public int getIndex()
		{
		return index;
		}

	public void setIndex(int index)
		{
		this.index = index;
		}

	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

