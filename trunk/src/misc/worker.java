package misc;

import schedule.task;
import schedule.task.taskStatusType;

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
	protected boolean finished;
	protected task myTask;
	
	public worker(task myTask)
		{
		isNotFinished = true;
		finished = false;
		myTask.setStatus(taskStatusType.working);
		this.myTask = myTask;
		}

	public void interrupt()
		{
		isNotFinished = false;
		myTask.setStatus(taskStatusType.toDelete);
		}
	
	public boolean isFinished()
		{
		return finished;
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

