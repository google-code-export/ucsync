package scan;

/**********************************
 * Class used to store associated 
 * device table
 * 
 * @author RATEL Alexandre
 **********************************/
public class userAssociatedDevice
	{
	/**
	 * Variables
	 */
	private String userPkid;
	private String devicePkid;
	
	
	public userAssociatedDevice(String userPkid, String devicePkid)
		{
		this.userPkid = userPkid;
		this.devicePkid = devicePkid;
		}

	

	public String getUserPkid()
		{
		return userPkid;
		}

	public void setUserPkid(String userPkid)
		{
		this.userPkid = userPkid;
		}

	public String getDevicePkid()
		{
		return devicePkid;
		}

	public void setDevicePkid(String devicePkid)
		{
		this.devicePkid = devicePkid;
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

