package misc;

import schedule.userSync;

/**********************************
 * 
 * 
 * @author RATEL Alexandre
 **********************************/
public abstract class miscData implements miscDataImpl
	{
	/**
	 * Variables
	 */
	protected final String UUID;
	protected userSync myUSync;
	
	
	
	public miscData(String UUID, userSync myUSync)
		{
		this.UUID = UUID;
		this.myUSync = myUSync;
		}

	public userSync getMyUSync()
		{
		return myUSync;
		}

	public void setMyUSync(userSync myUSync)
		{
		this.myUSync = myUSync;
		}

	public String getUUID()
		{
		return UUID;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

