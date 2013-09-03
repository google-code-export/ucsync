
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import misc.simpleToDo;
import org.apache.commons.codec.digest.DigestUtils;
import schedule.userSync.deviceType;

/*********************************************
 * Class used to store miscellaneous static method
 * 
 * @author RATEL Alexandre
 *********************************************/
public class methodesUtiles
	{
	
	/************************************************
	 * Method used to get option write in the config file
	 ************************************************/
	public synchronized static ArrayList<String[][]> initConfigValue() throws Exception
		{
		String file = null;
		ArrayList<String[][]> answer;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			variables.getLogger().info("Lecture du fichier de configuration : "+variables.getConfigFileName());
			file = xMLReader.fileRead(".\\"+variables.getConfigFileName());
			
			listParams.add("config");
			answer= xMLGear.getResultListTab(file, listParams);
			
			return answer;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getConfigFileName()+" non trouvé",fnfexc);
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The config file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the configuration file : "+exc.getMessage());
			}
        }
	
	
	/************************************************
	 * Method used to get data write in the task file
	 ************************************************/
	public static ArrayList<String[][]> initTaskValue() throws Exception
		{
		String file = null;
		ArrayList<String[][]> answer;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			variables.getLogger().info("Lecture du fichier des taches : "+variables.getTaskFileName());
			file = xMLReader.fileRead(".\\"+variables.getTaskFileName());
			
			listParams.add("tasks");
			listParams.add("task");
			answer= xMLGear.getResultListTab(file, listParams);
			
			return answer;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouvé",fnfexc);
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The task file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR with the task file : "+exc.getMessage());
			}
		}
		
	/************************************************
	 * Method used to get data write in the task file
	 ************************************************/
	public static ArrayList<ArrayList<String>> initExceptionValue() throws Exception
		{
		String file = null;
		ArrayList<ArrayList<String>> returnedValues = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String[][]>> answer;
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			variables.getLogger().info("Get the Exception list from this file : "+variables.getTaskFileName());
			file = xMLReader.fileRead(".\\"+variables.getTaskFileName());
			
			listParams.add("tasks");
			listParams.add("task");
			listParams.add("exception");
			answer= xMLGear.getResultListTabExt(file, listParams);
			
			for(int i=0; i<answer.size(); i++)
				{
				ArrayList<String> list = new ArrayList<String>();
				for(int j=0; j<answer.get(i).size(); j++)
					{
					for(int a=0; a<answer.get(i).get(j).length; a++)
						{
						list.add(new String(answer.get(i).get(j)[a][1]));
						variables.getLogger().debug("Exception found : "+answer.get(i).get(j)[a][1]);
						}
					}
				returnedValues.add(list);
				}
			
			return returnedValues;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouvé",fnfexc);
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The task file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR during exception list building : "+exc.getMessage());
			}
		}
		
	/************************************************
	 * Method used to get data write in the task file
	 * @throws Exception 
	 ************************************************/
	public static ArrayList<ArrayList<simpleToDo>> initBannedToDoList() throws Exception
		{
		String file = null;
		ArrayList<ArrayList<String[][]>> answer;
		ArrayList<ArrayList<simpleToDo>> bannedList = new ArrayList<ArrayList<simpleToDo>>();
		ArrayList<String> listParams = new ArrayList<String>();
		
		try
			{
			variables.getLogger().info("Get the Banned ToDo list from this file : "+variables.getBannedToDoListFileName());
			file = xMLReader.fileRead(".\\"+variables.getBannedToDoListFileName());
			
			listParams.add("task");
			listParams.add("todo");
			answer= xMLGear.getResultListTabExt(file, listParams);
			
			for(int i=0; i<answer.size(); i++)
				{
				ArrayList<simpleToDo> tempBannedList = new ArrayList<simpleToDo>();
				
				for(int j=0; j<answer.get(i).size(); j++)
					{
					String uuid = null;
					String description = null;
					String user = null;	
					
					for(int a=0; a<answer.get(i).get(j).length; a++)
						{
						System.out.println("##"+answer.get(i).get(j)[a][0]+" "+answer.get(i).get(j)[a][1]);
						if(answer.get(i).get(j)[a][0].equals("uuid"))uuid = answer.get(i).get(j)[a][1];
						if(answer.get(i).get(j)[a][0].equals("description"))description = answer.get(i).get(j)[a][1];
						if(answer.get(i).get(j)[a][0].equals("user"))user = answer.get(i).get(j)[a][1];
						}
					if((uuid != null) && (description != null) && (user != null))
						{
						tempBannedList.add(new simpleToDo(description,user,uuid));
						}
					else
						{
						throw new Exception("Failed to build banned to do List");
						}
					}
				bannedList.add(tempBannedList);
				}
			variables.getLogger().info("Banned toDo Lit size : "+bannedList.get(0).size());
			
			return bannedList;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouvé",fnfexc);
			fnfexc.printStackTrace();
			throw new FileNotFoundException("ERROR : The Banned ToDo List file was not found : "+fnfexc.getMessage());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage(),exc);
			throw new Exception("ERROR during Banned ToDo list building : "+exc.getMessage());
			}
		}
	
	/***************************************
	 * Method used to get a specific value
	 * in the user preference XML File
	 ***************************************/
	public synchronized static String getTargetOption(String node) throws Exception
		{
		//variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabConfig().get(0).length; i++)
			{
			if(variables.getTabConfig().get(0)[i][0].compareTo(node)==0)
				{
				//variables.getLogger().debug("Valeure trouvée : "+variables.getTabConfig().get(0)[i][1]);
				return variables.getTabConfig().get(0)[i][1];
				}
			}
		
		/***********
		 * If this point is reached, the option looked for was not found
		 */
		throw new Exception("Option not found"); 
		}
	
	
	/***************************************
	 * Method used to get a specific value
	 * in the task config file using task index
	 ***************************************/
	public synchronized static String getTargetTask(String node, int index) throws Exception
		{
		//variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabTasks().get(index).length; i++)
			{
			if(variables.getTabTasks().get(index)[i][0].compareTo(node)==0)
				{
				//variables.getLogger().debug("Valeure trouvée : "+variables.getTabTasks().get(index)[i][1]);
				return variables.getTabTasks().get(index)[i][1];
				}
			}
		
		/***********
		 * If this point is reached, the option looked for was not found
		 */
		throw new Exception("Option not found"); 
		}
	
	/***************************************
	 * Method used to get a specific Exception value
	 ***************************************/
	public synchronized static boolean isExceptionWord(String str)
		{
		for(int i=0; i<variables.getExceptionList().size(); i++)
			{
			if(variables.getExceptionList().get(i).equals(str))
				{
				variables.getLogger().debug("Exception word found : "+str);
				return true;
				}
			}
		return false;
		}
	
	/************************
	 * Check if java version
	 * is correct
	 ***********************/
	public synchronized static void checkJavaVersion()
		{
		try
			{
			String jVer = new String(System.getProperty("java.version"));
			variables.getLogger().info("Detected JRE version : "+jVer);
			if(jVer.contains("1.6"))
				{
				if(Integer.parseInt(jVer.substring(6,8))<16)
					{
					variables.getLogger().info("JRE version is not compatible. The application will now exit. system.exit(0)");
					System.exit(0);
					}
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().info("ERROR : It has not been possible to detect JRE version",exc);
			}
		}
	/***********************/
	
	/**********************************
	 * Method used to return
	 * a unique ID based on current date and
	 * a random number
	 **********************************/
	public synchronized static String getID()
		{
		//Date and time
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String currentDate = dateFormat.format(now);
		
		//get a random number
		Random rn = new Random();
		int rnValue = rn.nextInt();
		
		//Generate the md5 hash and return
		return DigestUtils.md5Hex(currentDate+Integer.toString(rnValue));
		}
	
	
	/********************************************
	 * Method used to init the class eMailsender
	 ********************************************/
	public synchronized static void initEMailServer()
		{
		try
			{
			String port = methodesUtiles.getTargetOption("smtpemailport");
			String protocol = methodesUtiles.getTargetOption("smtpemailprotocol");
			String server = methodesUtiles.getTargetOption("smtpemailserver");
			String user = methodesUtiles.getTargetOption("smtpemail");
			String password = methodesUtiles.getTargetOption("smtpemailpassword");
			variables.seteMSender(new eMailSender(port, protocol, server, user, password));
			}
		catch(Exception exc)
			{
			variables.getLogger().error("Le serveur n'a pas réussi à être initialisé : "+exc.getMessage());
			exc.printStackTrace();
			variables.getLogger().error("Le programme doit donc être arrêté : System.exit(0)");
			System.exit(0);
			}
		}
	
	/**
	 * Method used to get the task index 
	 * using stack trace and reflexion
	 * 
	 * !!!! Have to be test !!!!
	 */
	public static int getTaskIndex() throws Exception
		{
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		for (int i=0; i<stackTraceElements.length; i++)
			{
			variables.getLogger().debug("StackTraceElement :"+i+" "+stackTraceElements[i].getClassName());
			if(stackTraceElements[i].getClassName().compareTo("task") == 0)
				{
				String value = (String) stackTraceElements[i].getClass().getField("taskIndex").get(stackTraceElements[i]);
				return Integer.parseInt(value);
				}
			}
		throw new Exception("ERROR : It has not been possible to determine task index");
		}
	
	/**
	 * Methods used to send an email to the administrator
	 */
	public synchronized static void sendToAdminList(String sub, String cont, String desc) throws Exception
		{
		String sendTo = new String("");
		String subject = sub;
		String content = cont;
		String eMailDesc = desc;
		
		String[] emailTab = methodesUtiles.getTargetOption("smtpemailadmin").split(",");
		
		for(int j=0; j<emailTab.length; j++)
			{
			sendTo = emailTab[j];
			variables.geteMSender().send(sendTo, subject, content, eMailDesc);
			}
		}
	
	/**
	 * Method used to return an enum deviceType
	 * from an approximative string
	 */
	public synchronized static deviceType getDeviceTypeFromString(String text) throws Exception
		{
		if((text != null) && (text.compareTo("") != 0))
			{
			text = text.replace(" ", "");
			for(deviceType type : deviceType.values())
				{
				if(text.equalsIgnoreCase(type.name()))
					{
					return type;
					}
				}
			}
		throw new Exception("ERROR : It has not been possible to determine a deviceType from this text : "+text);
		}
	
	/**
	 * Method used to get acquittement URL 
	 */
	public synchronized static String getAckURL(String ID) throws Exception
		{
		//Get my IP Address
		InetAddress addr = InetAddress.getLocalHost();
        String ipAddr = addr.getHostAddress();
		
        StringBuffer url = new StringBuffer();
        
        url.append("http://");
        url.append(ipAddr);
        url.append(":");
        url.append(methodesUtiles.getTargetOption("webserverport"));
        url.append("/");
        url.append(ID);
		
        return url.toString();
		}
	
	/**
	 * Method used to write the XML
	 * banned toDo List
	 */
	public static void writeBannedToDoList()
		{
		File fichierRapport = new File(variables.getBannedToDoListFileName());
		FileWriter monFichierRapport = null;
		BufferedWriter tamponRapport  = null;
		
		try
			{
			removeBannedToDoDuplicate();
			
			monFichierRapport = new FileWriter(fichierRapport, false);
			tamponRapport = new BufferedWriter(monFichierRapport);
			StringBuffer buffRapport = new StringBuffer("");
			
			//Ecriture
			buffRapport.append("<xml>\r\n");
			
			for(int i=0; i<variables.getBannedToDoList().size(); i++)
				{
				buffRapport.append("	<task>\r\n");
				if((variables.getBannedToDoList().get(i) != null) &&(variables.getBannedToDoList().get(i).size() != 0))
					{
					for(int j=0; j<variables.getBannedToDoList().get(i).size(); j++)
						{
						buffRapport.append("		<todo>\r\n");
						buffRapport.append("			<uuid>"+variables.getBannedToDoList().get(i).get(j).getUUID()+"</uuid>\r\n");
						buffRapport.append("			<description>"+variables.getBannedToDoList().get(i).get(j).getDescription()+"</description>\r\n");
						buffRapport.append("			<user>"+variables.getBannedToDoList().get(i).get(j).getUser()+"</user>\r\n");
						buffRapport.append("		</todo>\r\n");
						}
					}
				buffRapport.append("	</task>\r\n");
				}
			
			buffRapport.append("</xml>\r\n");
			
			tamponRapport.write(buffRapport.toString());
			}
		catch(Exception exception)
			{
			exception.printStackTrace();
			variables.getLogger().error(exception);
			}
		finally
			{
			try
				{
				tamponRapport.flush();
				tamponRapport.close();
				monFichierRapport.close();
				variables.getLogger().info("Banned toDo List XML file has been written with success");
				}
			catch(Exception e)
				{
				e.printStackTrace();
				variables.getLogger().error(e);
				}
			}
		}
	
	/**
	 * Method used to fill the banned ToDo list
	 * with an empty list
	 */
	public static void defaultBannedToDoList()
		{
		ArrayList<ArrayList<simpleToDo>> myList = new ArrayList<ArrayList<simpleToDo>>();
		
		for(int i=0; i<variables.getTabTasks().size(); i++)
			{
			ArrayList<simpleToDo> myToDoList = new ArrayList<simpleToDo>();
			myList.add(myToDoList);
			}
		variables.setBannedToDoList(myList);
		}
	
	/**
	 * Method used to remove banned to do list duplicates
	 */
	public static void removeBannedToDoDuplicate()
		{
		boolean duplicatefound = false;
		
		for(int x=0; x<variables.getBannedToDoList().size(); x++)
			{
			for(int i=0; i<variables.getBannedToDoList().get(x).size(); i++)
				{
				simpleToDo myToDo1= variables.getBannedToDoList().get(x).get(i);
				for(int j=i+1; j<variables.getBannedToDoList().get(x).size(); j++)
					{
					simpleToDo myToDo2= variables.getBannedToDoList().get(x).get(j);
					if(myToDo1.getUUID().equals(myToDo2.getUUID()))
						{
						variables.getBannedToDoList().get(x).remove(j);
						duplicatefound = true;
						}
					}
				}
			}
		if(duplicatefound)
			{
			removeBannedToDoDuplicate();
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
