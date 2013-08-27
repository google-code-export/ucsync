package schedule;

import java.util.ArrayList;

import schedule.task.taskStatusType;
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
	 * Method used to check tasks status
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
			if((myTaskList.get(i).getStatus().equals(taskStatusType.init))&&(myTaskList.get(i).isItLaunchedTime()))
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
			else if((myTaskList.get(i).getStatus().equals(taskStatusType.waitingAck))&&(myTaskList.get(i).isExpired()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).setStatus(taskStatusType.toDelete);
					}
				else
					{
					//if needed
					}
				}
			else if((myTaskList.get(i).getStatus().equals(taskStatusType.pending))&&(myTaskList.get(i).isItLaunchedTime()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					((userSync)myTaskList.get(i)).executeToDoList();
					}
				else
					{
					//if needed
					}
				}
			else if(myTaskList.get(i).getStatus().equals(taskStatusType.done))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).setStatus(taskStatusType.toDelete);
					}
				else
					{
					//if needed
					}
				}
			else if((myTaskList.get(i).getStatus().equals(taskStatusType.working))&&(myTaskList.get(i).isExpired()))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).setStatus(taskStatusType.toDelete);
					variables.getLogger().info(myTaskList.get(i).getTInfo()+"is working since too many time. Deleting process is launched");
					}
				else
					{
					//if needed
					}
				}
			else if(myTaskList.get(i).getStatus().equals(taskStatusType.error))
				{
				if(myTaskList.get(i).getType().equals(taskType.userSync))
					{
					myTaskList.get(i).setStatus(taskStatusType.toDelete);
					
					//We have to send an email to the administrator to warn him
					StringBuffer cont = new StringBuffer("ERROR : \r\n");
					cont.append(myTaskList.get(i).getTInfo()+"has been deleted with error.\r\n");
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
			variables.getLogger().debug(myTaskList.get(i).getTInfo()+"is already exiting");
			variables.getLogger().debug(myTaskList.get(i).getTInfo()+"Status : "+myTaskList.get(i).getStatus().name());
			}
		
		for(int i=0; i<variables.getTabTasks().size(); i++)
			{
			boolean exist = false;
			if(taskAlreadyExisting.contains((Integer) i))
				{
				exist = true;
				}

			//If task doesn't already exist and it is not banned, we create it
			if((!exist)&&(!variables.getBannedTaskList().contains((Integer) i)))
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
						variables.getLogger().debug(myUserSync.getTInfo()+"of type UserSync has been launched with success");
						}
					catch (Exception exc)
						{
						exc.printStackTrace();
						variables.getLogger().error(exc);
						variables.getBannedTaskList().add(new Integer(i));
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

