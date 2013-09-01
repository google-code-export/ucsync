package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import misc.toDo;

import schedule.task;
import schedule.userSync;
import schedule.task.taskStatusType;
import schedule.task.taskType;
import utils.testeur;
import utils.variables;

/**********************************
 * Class used to manage Management request
 * 
 * @author RATEL Alexandre
 **********************************/
public class mngtReceiver extends Thread
	{
	/**
	 * Variables
	 */
	private Socket myS;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public mngtReceiver(Socket myS)
		{
		this.myS = myS;
		}
	
	
	public void run()
		{
		try
			{
			/*******
			 * Step 1 : IniStream
			 */
			in = new ObjectInputStream(myS.getInputStream());
			out = new ObjectOutputStream(myS.getOutputStream());
			/***************/
			
			/*******
			 * Step 2 : Send task list
			 */
			sendTask();
			/***************/
			
			/*******
			 * Step 3 : get new task List
			 */
			manageTask();
			/***************/
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("ERROR : "+exc.getMessage());
			}
		finally
			{
			try
				{
				myS.close();
				}
			catch (IOException exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				}
			}
		}
	
	/**
	 * Method used to send Object
	 */
	private void sendTask()
		{
		try
			{
			if((variables.getTaskList() != null) && (variables.getTaskList().size() != 0))
				{
				out.writeObject((Object)variables.getTaskList());
				variables.getLogger().info("Task list sent with sucess");
				if((variables.getBannedToDoList() != null) && (variables.getBannedToDoList().size() != 0))
					{
					out.writeObject((Object)variables.getBannedToDoList());
					variables.getLogger().info("Banned ToDo list sent with success");
					}
				else
					{
					out.writeObject((Object)new ArrayList<ArrayList<toDo>>());
					variables.getLogger().info("No available banned toDo to manage");
					}
				if((variables.getTabTasks() != null) && (variables.getTabTasks().size() != 0))
					{
					out.writeObject((Object)variables.getTabTasks());
					variables.getLogger().info("Task config values sent with success");
					}
				else
					{
					out.writeObject((Object)new ArrayList<String[][]>());
					variables.getLogger().info("No available Task config values to manage");
					}
				}
			else
				{
				out.writeObject((Object)new ArrayList<task>());
				variables.getLogger().info("No available task to manage");
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			}
		}
	
	/**
	 * Method used to manage user modification on
	 * the current Task
	 */
	private void manageTask()
		{
		try
			{
			while(true)
				{
				variables.setTaskList((ArrayList<task>)in.readObject());
				variables.getLogger().info("New Task List received with success");
				variables.setBannedToDoList(((ArrayList<ArrayList<toDo>>)in.readObject()));
				variables.getLogger().info("New Banned List received with success");
				variables.setTabTasks(((ArrayList<String[][]>)in.readObject()));
				variables.getLogger().info("New Tab Task received with success");
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

