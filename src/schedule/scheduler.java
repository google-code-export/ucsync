package schedule;

import java.util.ArrayList;

import utils.methodesUtiles;
import utils.variables;
import utils.variables.taskStatusType;

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
		variables.setTaskList(new ArrayList<task>());
		variables.setSimpleTaskList(new ArrayList<simpleTask>());
		
		start();
		}
	
	
	public void run()
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
				
				garbageCollector();
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
					sleep(Integer.parseInt(methodesUtiles.getTargetOption("checkfreq")));
					}
				catch(Exception exc)
					{
					variables.getLogger().error(exc);
					}
				}
			}
		}
	
	/**
	 * Garbage collector
	 * aims to delete task
	 */
	public void garbageCollector() throws Exception
		{
		for(int i=0; i<variables.getTaskList().size(); i++)
			{
			if(variables.getTaskList().get(i).getStatus().equals(taskStatusType.toDelete))
				{
				variables.getTaskList().get(i).stopWorking();
				
				//We wait here for task ending
				int timeout = Integer.parseInt(methodesUtiles.getTargetTask("timeout",variables.getTaskList().get(i).getTaskIndex()));
				int counter = 0;
				
				while(true)
					{
					if(variables.getTaskList().get(i).getMyWorker().isFinished())
						{
						String TInfo = variables.getTaskList().get(i).getTInfo();
						variables.getTaskList().remove(i);
						variables.getLogger().debug(TInfo+"Has been removed successfully");
						//We start again from zero cause the taskList size is changed now. So 
						//it is possible to miss a "toDelete" task
						i=0;
						break;
						}
					else
						{
						sleep(Integer.parseInt(methodesUtiles.getTargetOption("garbagefreq")));
						counter ++;
						}
					if(counter>=timeout)
						{
						variables.getLogger().error("ERROR : "+variables.getTaskList().get(i).getTInfo()+"has been too long to finished and was finally removed. Check for potential malfunctioning");
						String TInfo = variables.getTaskList().get(i).getTInfo();
						variables.getTaskList().remove(i);
						variables.getLogger().debug(TInfo+"Has been removed successfully");
						i=0;
						break;
						}
					}
				}
			}
		/*******
		 * Force System Garbage Collector
		 */
		System.gc();
		/*******/
		}
	
	public void endTasks()
		{
		
		}
	
	
	/**
	 * Method used to Kill scheduler
	 */
	public void interrupt()
		{
		try
			{
			for(int i=0; i<variables.getTaskList().size(); i++)
				{
				variables.getTaskList().get(i).setStatus(taskStatusType.toDelete);
				}
			
			garbageCollector();
			
			isNotFinished = false;
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Failed to interrupt scheduler");
			}
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

