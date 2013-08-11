package schedule;

import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to launch tasks at 
 * the dedicated hour
 * 
 * @author RATEL Alexandre
 **********************************/
public class scheduler extends Thread
	{
	/**
	 * Variables
	 */
	private boolean isNotFinished;
	
	
	
	public scheduler()
		{
		isNotFinished = true;
		
		launch();
		}
	
	
	public void launch()
		{
		while(isNotFinished)
			{
			try
				{
				/**
				 * We launch a new task checker
				 */
				new checkTask();
				/*****/
				}
			catch (Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				}
			finally
				{
				try
					{
					//We wait before check again if a task is ready
					this.sleep(Integer.parseInt(methodesUtiles.getTargetOption("checkfreq")));
					}
				catch(Exception exc)
					{
					variables.getLogger().error(exc);
					}
				}
			}
		}
	
	
	
	public void interrupt()
		{
		isNotFinished = false;
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

