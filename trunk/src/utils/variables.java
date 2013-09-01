
package utils;

import java.util.ArrayList;

import misc.toDo;

import org.apache.log4j.Logger;

import scan.device;
import scan.line;
import scan.userData;
import schedule.scheduler;
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
	private static ArrayList<ArrayList<toDo>> bannedToDoList;
	
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
		bannedToDoList = new ArrayList<ArrayList<toDo>>();
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

	public static ArrayList<task> getTaskList()
		{
		return taskList;
		}

	public static void setTaskList(ArrayList<task> taskList)
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

	public static ArrayList<ArrayList<toDo>> getBannedToDoList()
		{
		return bannedToDoList;
		}

	public static void setBannedToDoList(ArrayList<ArrayList<toDo>> bannedToDoList)
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
	
	
	
	/*****
	 * End of getters and Setters 
	 */
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}