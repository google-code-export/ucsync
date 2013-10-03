package utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import manager.bannedLister;
import manager.configLister;
import manager.mainWindow;
import manager.toDoLister;
import misc.simpleToDo;

import org.apache.log4j.Logger;

import schedule.simpleTask;


/**********************************
 * Class used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class variables
	{
	/**
	 * Variables
	 */
	public enum patternType {devicedescription,devicedescriptiontoolong,
	linedescription,linedescriptiontoolong,
	linealertingname,linealertingnametoolong,
	linedisplay,linedisplaytoolong,
	linetextlabel,linetextlabeltoolong,
	lineexternalphonenumbermask};
	
	public enum toDoStatusType{success,error,processing,waiting,delete,disabled,init,conflict,impossible,banned};
	public enum taskStatusType{init,working,waitingAck,pending,done,toDelete,error,banned};
	public enum taskType{userSync};
	public enum sendReceiveType{getAll,sendAll,sendConfig,stopService,startService,serviceStatus};
	public enum serverStatusType{started,stopped};
	public enum dataTypes{device,line};
	
	private static String version;
	private static String nomProg;
	private static Logger logger;
	private static String configFileName;
	private static ArrayList<String[][]> tabConfig;
	private static ArrayList<String[][]> tabTasks;
	private static ArrayList<simpleTask> taskList;
	private static ArrayList<ArrayList<simpleToDo>> bannedToDoList;
	private static eMailSender eMSender;
	private static Socket myS;
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static int taskIndex;
	private static toDoLister myToDoLister;
	private static bannedLister myBannedLister;
	private static configLister myConfigLister;
	private static mainWindow myWindow;
	private static serverStatusType serverStatus;
	
	
	public variables()
		{
		configFileName = new String("configFile.xml");
		tabTasks = new ArrayList<String[][]>();
		taskList = new ArrayList<simpleTask>();
		bannedToDoList = new ArrayList<ArrayList<simpleToDo>>();
		taskIndex = 0;
		serverStatus = serverStatusType.stopped;
		myToDoLister = new toDoLister();
		myBannedLister = new bannedLister();
		myConfigLister = new configLister();
		}




	/***
	 * Getters and setters
	 */
	public static String getVersion()
		{
		return version;
		}

	public static void setVersion(String version)
		{
		variables.version = version;
		}

	public static String getNomProg()
		{
		return nomProg;
		}

	public static void setNomProg(String nomProg)
		{
		variables.nomProg = nomProg;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		variables.logger = logger;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		variables.configFileName = configFileName;
		}

	public static ArrayList<String[][]> getTabConfig()
		{
		return tabConfig;
		}

	public static void setTabConfig(ArrayList<String[][]> tabConfig)
		{
		variables.tabConfig = tabConfig;
		}

	public static ArrayList<String[][]> getTabTasks()
		{
		return tabTasks;
		}

	public static void setTabTasks(ArrayList<String[][]> tabTasks)
		{
		variables.tabTasks = tabTasks;
		}

	public static ArrayList<simpleTask> getTaskList()
		{
		return taskList;
		}

	public static void setTaskList(ArrayList<simpleTask> taskList)
		{
		variables.taskList = taskList;
		}

	public static ArrayList<ArrayList<simpleToDo>> getBannedToDoList()
		{
		return bannedToDoList;
		}

	public static void setBannedToDoList(ArrayList<ArrayList<simpleToDo>> bannedToDoList)
		{
		variables.bannedToDoList = bannedToDoList;
		}

	public static eMailSender geteMSender()
		{
		return eMSender;
		}

	public static void seteMSender(eMailSender eMSender)
		{
		variables.eMSender = eMSender;
		}

	public static Socket getMyS()
		{
		return myS;
		}

	public static void setMyS(Socket myS)
		{
		variables.myS = myS;
		}

	public static ObjectInputStream getIn()
		{
		return in;
		}

	public static void setIn(ObjectInputStream in)
		{
		variables.in = in;
		}

	public static ObjectOutputStream getOut()
		{
		return out;
		}

	public static void setOut(ObjectOutputStream out)
		{
		variables.out = out;
		}

	public static int getTaskIndex()
		{
		return taskIndex;
		}

	public static void setTaskIndex(int taskIndex)
		{
		variables.taskIndex = taskIndex;
		}

	public static toDoLister getMyToDoLister()
		{
		return myToDoLister;
		}

	public static void setMyToDoLister(toDoLister myToDoLister)
		{
		variables.myToDoLister = myToDoLister;
		}

	public static bannedLister getMyBannedLister()
		{
		return myBannedLister;
		}

	public static void setMyBannedLister(bannedLister myBannedLister)
		{
		variables.myBannedLister = myBannedLister;
		}

	public static configLister getMyConfigLister()
		{
		return myConfigLister;
		}

	public static void setMyConfigLister(configLister myConfigLister)
		{
		variables.myConfigLister = myConfigLister;
		}

	public static mainWindow getMyWindow()
		{
		return myWindow;
		}

	public static void setMyWindow(mainWindow myWindow)
		{
		variables.myWindow = myWindow;
		}

	public static serverStatusType getServerStatus()
		{
		return serverStatus;
		}

	public static void setServerStatus(serverStatusType serverStatus)
		{
		variables.serverStatus = serverStatus;
		}
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

