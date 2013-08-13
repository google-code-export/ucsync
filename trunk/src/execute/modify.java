package execute;

import schedule.task;
import schedule.task.taskStatusType;
import utils.variables;
import misc.worker;

/**********************************
 * Class used to commit modification
 * found during the scan process
 * 
 * @author RATEL Alexandre
 **********************************/
public class modify extends worker
	{
	/**
	 * Variables
	 */
	
	
	public modify(task myTask)
		{
		super(myTask);
		start();
		}
	
	public void run()
		{
		try
			{
			//Have to written
			
			
			
			myTask.setStatus(taskStatusType.done);
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			exc.printStackTrace();
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

