package scan;

import schedule.task;
import schedule.task.statusType;
import utils.variables;
import misc.worker;

/**********************************
 * Class used to inspect CUCM and
 * find data which has to by modify
 * 
 * @author RATEL Alexandre
 **********************************/
public class inspection extends worker
	{
	/**
	 * Variables
	 */
	
	
	
	public inspection(task myTask)
		{
		super(myTask);
		start();
		}
	
	
	public void run()
		{
		try
			{
			/*******
			 * Step 1 : Getting data from CUCM
			 */
			
			/***************/
			
			
			
			/*****
			 * Step 2 : Compare data and make to do list
			 */
			
			/***************/
			
			
			
			
			myTask.setStatus(statusType.waitingAck);
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			exc.printStackTrace();
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

