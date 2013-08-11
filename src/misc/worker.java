package misc;

import schedule.task;
import schedule.task.statusType;

/**********************************
 * Class used to be extended
 * it aims to be a thread for scan
 * or execute process
 * 
 * @author RATEL Alexandre
 **********************************/
public abstract class worker extends Thread implements workimpl
	{
	protected boolean isNotFinished;
	protected task myTask;
	
	public worker(task myTask)
		{
		isNotFinished = true;
		myTask.setStatus(statusType.working);
		this.myTask = myTask;
		}

	public void interrupt()
		{
		isNotFinished = false;
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

