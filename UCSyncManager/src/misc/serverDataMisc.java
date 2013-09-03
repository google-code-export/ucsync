package misc;

/**********************************
 * Class used to be extended. Aims to
 * be used for data exchange with UCSync
 * server
 * 
 * @author RATEL Alexandre
 **********************************/
public abstract class serverDataMisc extends Thread
	{
	/**
	 * Variables
	 */
	protected boolean finished;
	
	public serverDataMisc()
		{
		finished = false;
		}

	
	public boolean isFinished()
		{
		return finished;
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

