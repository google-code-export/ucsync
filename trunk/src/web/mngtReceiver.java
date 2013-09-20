package web;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import misc.simpleToDo;
import misc.toDo;

import schedule.scheduler;
import schedule.simpleTask;
import schedule.task;
import schedule.userSync;
import utils.convertSOAPToString;
import utils.methodesUtiles;
import utils.testeur;
import utils.variables;
import utils.variables.sendReceiveType;
import utils.variables.serverStatusType;

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
			 * Step 2 : receive and reply
			 */
			sendAndReceive();
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
	 * Method used to manage communication with
	 * the management client
	 */
	private void sendAndReceive()
		{
		try
			{
			while(true)
				{
				sendReceiveType myType = ((sendReceiveType)in.readObject());
				
				if(myType.equals(sendReceiveType.getAll))
					{
					initValues();
					
					//Here we send all list
					if((variables.getTabTasks() != null))
						{
						out.writeObject((Object)variables.getTabTasks());
						variables.getLogger().info("Task config values sent with success");
						}
					else
						{
						out.writeObject((Object)new ArrayList<String[][]>());
						variables.getLogger().info("No available Task config values to manage");
						}
					if((variables.getSimpleTaskList() != null) && (variables.isReadyToPublish()))
						{
						out.writeObject((Object)variables.getSimpleTaskList());
						variables.getLogger().info("Task list sent with sucess");
						}
					else
						{
						out.writeObject((Object)new ArrayList<simpleTask>());
						variables.getLogger().info("No available task to manage");
						}
					if((variables.getBannedToDoList() != null))
						{
						out.writeObject((Object)variables.getBannedToDoList());
						variables.getLogger().info("Banned ToDo list sent with success");
						}
					else
						{
						out.writeObject((Object)new ArrayList<ArrayList<simpleToDo>>());
						variables.getLogger().info("No available banned toDo to manage");
						}
					out.flush();
					}
				else if(myType.equals(sendReceiveType.sendAll))
					{
					//Here we get task list
					methodesUtiles.updateTaskList((ArrayList<simpleTask>)in.readObject());
					variables.getLogger().info("New Task List received with success");
					
					//here we get Banned to do list
					variables.setBannedToDoList(((ArrayList<ArrayList<simpleToDo>>)in.readObject()));
					variables.getLogger().info("New Banned List received with success");
					methodesUtiles.writeBannedToDoList();
					
					//here we get config list
					variables.setTabTasks(((ArrayList<String[][]>)in.readObject()));
					variables.getLogger().info("New Tab Task received with success");
					}
				else if(myType.equals(sendReceiveType.sendConfig))
					{
					//here we get config list
					variables.setTabTasks(((ArrayList<String[][]>)in.readObject()));
					variables.getLogger().info("New Tab Task received with success");
					}
				else if(myType.equals(sendReceiveType.startService))
					{
					if(variables.getMyScheduler() == null)
						{
						variables.setMyScheduler(new scheduler());
						variables.getLogger().debug("Service Started with success");
						}
					}
				else if(myType.equals(sendReceiveType.stopService))
					{
					if(variables.getMyScheduler() != null)
						{
						variables.getMyScheduler().interrupt();
						variables.setMyScheduler(null);
						variables.getLogger().debug("Service Stopped with success");
						}
					}
				else if(myType.equals(sendReceiveType.serviceStatus))
					{
					if(variables.getMyScheduler() == null)
						{
						out.writeObject((Object)serverStatusType.stopped);
						}
					else
						{
						out.writeObject((Object)serverStatusType.started);
						}
					}
				}
			}
		catch(SocketException sexc)
			{
			variables.getLogger().error("END manager connection "+sexc.getMessage());
			}
		catch(EOFException eoexc)
			{
			variables.getLogger().error("END manager connection "+eoexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			}
		}
	
	/***
	 * Method used to Init values which will be exchange throw 
	 * ObjectStream
	 * @throws Exception 
	 */
	private void initValues() throws Exception
		{
		//Simple Task List
		ArrayList<simpleTask> mySimpleTaskList = new ArrayList<simpleTask>();
		for(int i=0; i<variables.getTaskList().size(); i++)
			{
			ArrayList<simpleToDo> myToDoList = new ArrayList<simpleToDo>();
			for(int j=0; j<variables.getTaskList().get(i).getToDoList().size(); j++)
				{
				toDo myToDo = variables.getTaskList().get(i).getToDoList().get(j);
				myToDoList.add(new simpleToDo(myToDo.getDescription(), myToDo.getCurrentData(), myToDo.getNewData(), 
						myToDo.getUser(), myToDo.getUUID(), convertSOAPToString.convert(myToDo.getSoapMessage()), 
						myToDo.getStatus(), myToDo.getConflictList(), myToDo.getProblemList(), myToDo.isConflictDetected(), myToDo.isProblemDetected()));
				}
			task myTask = variables.getTaskList().get(i);
			mySimpleTaskList.add(new simpleTask(myTask.getStatus(), myTask.getType(), myTask.getDescription(), myToDoList, myTask.getId(), myTask.getTaskIndex()));
			}
		variables.setSimpleTaskList(mySimpleTaskList);
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

