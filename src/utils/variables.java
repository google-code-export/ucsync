
package utils;

import java.util.ArrayList;

import misc.simpleToDo;
import misc.toDo;

import org.apache.log4j.Logger;

import scan.device;
import scan.line;
import scan.userData;
import schedule.scheduler;
import schedule.simpleTask;
import schedule.task;
import web.webAckReceiver;
import web.webMngtManager;

/*********************************************
 * Classe contenant les variables statiques
 * 
 * @author RATEL Alexandre
 *********************************************/
public class variables
	{
	/**
	 * Listes des variables statiques
	 */
	public enum patternType {devicedescription,devicedescriptiontoolong,
	linedescription,linedescriptiontoolong,
	linealertingname,linealertingnametoolong,
	linedisplay,linedisplaytoolong,
	linetextlabel,linetextlabeltoolong,
	lineexternalphonenumbermask};
	
	public enum toDoStatusType{success,error,processing,waiting,delete,disabled,init,conflict,impossible,banned};
	public enum taskStatusType{init,working,waitingAck,pending,done,toDelete,error};
	public enum taskType{userSync};
	public enum sendReceiveType{getAll,sendAll,sendConfig,stopService,startService,serviceStatus};
	public enum serverStatusType{started,stopped};
	
	private static String nomProg;
	private static String version;
	private static Logger logger;
	private static String configFileName;
	private static String taskFileName;
	private static String bannedToDoListFileName;
	private static ArrayList<String[][]> tabConfig;
	private static ArrayList<String[][]> tabTasks;
	private static eMailSender eMSender;
	private static ArrayList<task> taskList;
	private static ArrayList<Integer> bannedTaskList;
	private static scheduler myScheduler;
	private static webAckReceiver myWebServer;
	private static webMngtManager myMngtServer;
	private static ArrayList<ArrayList<String>> exceptionList;
	private static ArrayList<ArrayList<simpleToDo>> bannedToDoList;
	private static ArrayList<simpleTask> simpleTaskList;
	private static boolean ReadyToPublish;
	
	/**
	 * Contructeur
	 */
	public variables()
		{
		configFileName = new String("configFile.xml");
		taskFileName = new String("taskFile.xml");
		bannedToDoListFileName = new String("bannedToDoList.xml");
		taskList = new ArrayList<task>();
		bannedTaskList = new ArrayList<Integer>();
		exceptionList = new ArrayList<ArrayList<String>>();
		bannedToDoList = new ArrayList<ArrayList<simpleToDo>>();
		simpleTaskList = new ArrayList<simpleTask>();
		ReadyToPublish = false;
		}
	
	/****
	 * Getters and Setters
	 */

	public static String getNomProg()
		{
		return nomProg;
		}

	public static void setNomProg(String nomProg)
		{
		variables.nomProg = nomProg;
		}

	public static String getVersion()
		{
		return version;
		}

	public static void setVersion(String version)
		{
		variables.version = version;
		}

	public synchronized static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		variables.logger = logger;
		}

	public static eMailSender geteMSender()
		{
		return eMSender;
		}

	public static void seteMSender(eMailSender eMSender)
		{
		variables.eMSender = eMSender;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		variables.configFileName = configFileName;
		}

	public static String getTaskFileName()
		{
		return taskFileName;
		}

	public static void setTaskFileName(String taskFileName)
		{
		variables.taskFileName = taskFileName;
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

	public synchronized static ArrayList<task> getTaskList()
		{
		return taskList;
		}

	public synchronized static void setTaskList(ArrayList<task> taskList)
		{
		variables.taskList = taskList;
		}

	public static ArrayList<Integer> getBannedTaskList()
		{
		return bannedTaskList;
		}

	public static void setBannedTaskList(ArrayList<Integer> bannedTaskList)
		{
		variables.bannedTaskList = bannedTaskList;
		}

	public static scheduler getMyScheduler()
		{
		return myScheduler;
		}

	public static void setMyScheduler(scheduler myScheduler)
		{
		variables.myScheduler = myScheduler;
		}

	public static webAckReceiver getMyWebServer()
		{
		return myWebServer;
		}

	public static void setMyWebServer(webAckReceiver myWebServer)
		{
		variables.myWebServer = myWebServer;
		}

	public static ArrayList<ArrayList<String>> getExceptionList()
		{
		return exceptionList;
		}

	public static void setExceptionList(ArrayList<ArrayList<String>> exceptionList)
		{
		variables.exceptionList = exceptionList;
		}

	public static String getBannedToDoListFileName()
		{
		return bannedToDoListFileName;
		}

	public static void setBannedToDoListFileName(String bannedToDoListFileName)
		{
		variables.bannedToDoListFileName = bannedToDoListFileName;
		}

	public static ArrayList<ArrayList<simpleToDo>> getBannedToDoList()
		{
		return bannedToDoList;
		}

	public static void setBannedToDoList(ArrayList<ArrayList<simpleToDo>> bannedToDoList)
		{
		variables.bannedToDoList = bannedToDoList;
		}

	public static webMngtManager getMyMngtServer()
		{
		return myMngtServer;
		}

	public static void setMyMngtServer(webMngtManager myMngtServer)
		{
		variables.myMngtServer = myMngtServer;
		}

	public synchronized static ArrayList<simpleTask> getSimpleTaskList()
		{
		return simpleTaskList;
		}

	public synchronized static void setSimpleTaskList(ArrayList<simpleTask> simpleTaskList)
		{
		variables.simpleTaskList = simpleTaskList;
		}

	public static boolean isReadyToPublish()
		{
		return ReadyToPublish;
		}

	public static void setReadyToPublish(boolean isReadyToPublish)
		{
		variables.ReadyToPublish = isReadyToPublish;
		}
	

	
	
	/*****
	 * End of getters and Setters 
	 */
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
