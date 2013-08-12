package schedule;

import java.util.ArrayList;

import schedule.task.statusType;
import schedule.task.taskType;
import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to check if tasks have
 * to be run
 * 
 * @author RATEL Alexandre
 **********************************/
public class checkTask
	{
	/***
	 * Variables
	 */
	private ArrayList<task> myTaskList;
	
	
	public checkTask() throws Exception
		{
		myTaskList = variables.getTaskList();
		check();
		}
	
	/****
	 * Method used to check tasks status'
	 */
	public void check() throws Exception
		{
		/******
		 * If task list is empty or task are finish, we have to look for
		 * tasks in the taskFile and launch brand new task
		 *****/
		launchNewTask();
		
		for(int i=0; i<myTaskList.size(); i++)
			{
			if((myTaskList.get(i).getStatus().equals(statusType.init))&&(myTaskList.get(i).isItLaunchedTime()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).initStartTime();
					((userSync)myTaskList.get(i)).fillToDoList();
					}
				else
					{
					//if needed
					}
				}
			else if((myTaskList.get(i).getStatus().equals(statusType.waitingAck))&&(myTaskList.get(i).isExpired()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					//Have to be written
					}
				else
					{
					//if needed
					}
				}
			else if((myTaskList.get(i).getStatus().equals(statusType.pending))&&(myTaskList.get(i).isItLaunchedTime()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					//Have to be written
					}
				else
					{
					//if needed
					}
				}
			else if(myTaskList.get(i).getStatus().equals(statusType.done))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					//Have to be written
					}
				else
					{
					//if needed
					}
				}
			else if((myTaskList.get(i).getStatus().equals(statusType.working))&&(myTaskList.get(i).isExpired()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					//Have to be written
					}
				else
					{
					//if needed
					}
				}
			else if(myTaskList.get(i).getStatus().equals(statusType.error))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).setStatus(statusType.toDelete);
					
					//We have to send an email to the administrator to warn him
					StringBuffer cont = new StringBuffer("ERROR : \r\n");
					cont.append("The task "+myTaskList.get(i).getTaskIndex()+" ID:"+myTaskList.get(i).getId()+" has been deleted with error.\r\n");
					cont.append("Please consult appropriate logs and contact your administrator\r\n\r\n");
					cont.append("Best regards : The "+variables.getNomProg()+" team");
					methodesUtiles.sendToAdminList("ERROR : "+variables.getNomProg(), cont.toString(), "emailerrorsend");
					}
				else
					{
					//if needed
					}
				}
			}
		
		/**
		 * Garbage collector
		 * aims to delete task
		 */
		for(int i=0; i<myTaskList.size(); i++)
			{
			if(myTaskList.get(i).getStatus().equals(statusType.toDelete))
				{
				myTaskList.get(i).stopWorking();
				myTaskList.remove(i);
				}
			}
		}
	
	/**
	 * Method used to launch
	 * brand new Task
	 */
	public void launchNewTask() throws Exception
		{
		ArrayList<Integer> taskAlreadyExisting = new ArrayList<Integer>(); 
		
		variables.getLogger().debug("Check for tasks which are already existing");
		for(int i=0; i<myTaskList.size(); i++)
			{
			taskAlreadyExisting.add(new Integer(myTaskList.get(i).getTaskIndex()));
			variables.getLogger().debug("Task :"+myTaskList.get(i).getTaskIndex()+" is already exiting");
			}
		
		for(int i=0; i<variables.getTaskList().size(); i++)
			{
			boolean exist = false;
			for(int j=0; j<taskAlreadyExisting.size(); j++)
				{
				if(taskAlreadyExisting.get(j).intValue() == i)
					{
					exist = true;
					}
				}

			//If task doesn't already exist, we create it
			if(!exist)
				{
				if(methodesUtiles.getTargetTask("type",i).compareTo("usersync") == 0)
					{
					/**
					 * Failed to start a task don't have to freeze the scheduler
					 * so we manage this exception locally
					 */
					try
						{
						userSync myUserSync = new userSync(i); 
						myTaskList.add(myUserSync);
						variables.getLogger().debug("Task "+i+" of type UserSync has been launched with success. This ID is "+myUserSync.getId());
						}
					catch (Exception exc)
						{
						exc.printStackTrace();
						variables.getLogger().error(exc);
						}
					}
				else
					{
					//Will be wrote when another type of task will be created
					}
				}
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

