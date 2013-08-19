package schedule;

import schedule.task.taskStatusType;
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
				
				/**
				 * Garbage collector
				 * aims to delete task
				 */
				for(int i=0; i<variables.getTaskList().size(); i++)
					{
					if(variables.getTaskList().get(i).getStatus().equals(taskStatusType.toDelete))
						{
						variables.getTaskList().get(i).stopWorking();
						
						//We wait here for task ending
						int timeout = Integer.parseInt(methodesUtiles.getTargetTask("",variables.getTaskList().get(i).getTaskIndex()));
						int counter = 0;
						
						while(true)
							{
							if(variables.getTaskList().get(i).getMyWorker().isFinished())
								{
								variables.getTaskList().remove(i);
								//We start again from zero cause the taskList size is changed now. So 
								//it is possible to miss a "toDelete" task
								i=0;
								break;
								}
							else
								{
								this.sleep(Integer.parseInt(methodesUtiles.getTargetOption("garbagefreq")));
								counter ++;
								}
							if(counter>=timeout)
								{
								variables.getLogger().error("ERROR : "+variables.getTaskList().get(i).getTInfo()+"has been too long to finished and was finally removed. Check for potential malfunctioning");
								variables.getTaskList().remove(i);
								i=0;
								break;
								}
							}
						}
					}
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
