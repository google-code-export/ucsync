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
	private String devicePkid;
	private String linePkid;
	
	
	public deviceAssociatedLine(String devicePkid, String linePkid)
		{
		this.devicePkid = devicePkid;
		this.linePkid = linePkid;
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

	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

