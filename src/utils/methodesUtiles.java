
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
import misc.toDo;

import org.apache.commons.codec.digest.DigestUtils;

import schedule.simpleTask;
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
			variables.getLogger().error("Fichier "+variables.getConfigFileName()+" non trouv�",fnfexc);
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
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouv�",fnfexc);
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
			answer= xMLGear.getResultListTabExt(file, listParams);
			
			for(int i=0; i<answer.size(); i++)
				{
				ArrayList<String> list = new ArrayList<String>();
				for(int j=0; j<answer.get(i).size(); j++)
					{
					for(int a=0; a<answer.get(i).get(j).length; a++)
						{
						if(answer.get(i).get(j)[a][0].equals("except"))
							{
							list.add(new String(answer.get(i).get(j)[a][1]));
							variables.getLogger().debug("Exception found in task "+i+" : "+answer.get(i).get(j)[a][1]);
							}
						}
					}
				returnedValues.add(list);
				}
			
			return returnedValues;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouv�",fnfexc);
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
			variables.getLogger().info("Get the Banned ToDo list from the file : "+variables.getBannedToDoListFileName());
			file = xMLReader.fileRead(".\\"+variables.getBannedToDoListFileName());
			
			listParams.add("tasks");
			listParams.add("task");
			
			answer= xMLGear.getResultListTabExt(file, listParams);
			
			for(int i=0; i<variables.getTabTasks().size(); i++)
				{
				String uuid = null;
				String description = null;
				String user = null;	
				ArrayList<simpleToDo> tempBannedList = new ArrayList<simpleToDo>();
				
				if(answer.size() >= (i+1))
					{
					for(int j=0; j<answer.get(i).size(); j++)
						{
						for(int a=0; a<answer.get(i).get(j).length; a++)
							{
							if(answer.get(i).get(j)[a][0].equals("uuid"))uuid = answer.get(i).get(j)[a][1];
							if(answer.get(i).get(j)[a][0].equals("description"))description = answer.get(i).get(j)[a][1];
							if(answer.get(i).get(j)[a][0].equals("user"))user = answer.get(i).get(j)[a][1];
							}
						if((uuid != null) && (description != null) && (user != null))
							{
							tempBannedList.add(new simpleToDo(description,user,uuid));
							variables.getLogger().debug("New banned toDo found for task "+i+" : "+user+", "+description+", "+uuid);
							}
						else
							{
							throw new Exception("Failed to build banned to do List");
							}
						}
					}
				bannedList.add(tempBannedList);
				}
			
			return bannedList;
			}
		catch(FileNotFoundException fnfexc)
			{
			variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouv�",fnfexc);
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
		//variables.getLogger().debug("Variable cherch�e : "+node);
		for(int i=0;i<variables.getTabConfig().get(0).length; i++)
			{
			if(variables.getTabConfig().get(0)[i][0].compareTo(node)==0)
				{
				//variables.getLogger().debug("Valeure trouv�e : "+variables.getTabConfig().get(0)[i][1]);
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
		//variables.getLogger().debug("Variable cherch�e : "+node);
		for(int i=0;i<variables.getTabTasks().get(index).length; i++)
			{
			if(variables.getTabTasks().get(index)[i][0].compareTo(node)==0)
				{
				//variables.getLogger().debug("Valeure trouv�e : "+variables.getTabTasks().get(index)[i][1]);
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
	public synchronized static boolean isExceptionWord(String str, int index)
		{
		for(int i=0; i<variables.getExceptionList().get(index).size(); i++)
			{
			if(str.contains(variables.getExceptionList().get(index).get(i)))
				{
				variables.getLogger().debug("Exception word found in : "+str);
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
			variables.getLogger().error("Le serveur n'a pas r�ussi � �tre initialis� : "+exc.getMessage());
			exc.printStackTrace();
			variables.getLogger().error("Le programme doit donc �tre arr�t� : System.exit(0)");
			System.exit(0);
			}
		}
	
	
	/**
	 * Methods used to send an email to the administrator
	 */
	public synchronized static void sendToAdminList(String sub, String cont, String desc)
		{
		try
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
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Failed to send email to the admin list. check if the smtp server is reachable : "+exc.getMessage());
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
			buffRapport.append("	<tasks>\r\n");
			
			for(int i=0; i<variables.getTaskList().size(); i++)
				{
				buffRapport.append("		<task>\r\n");
				if((variables.getBannedToDoList().get(i) != null) &&(variables.getBannedToDoList().get(i).size() != 0))
					{
					for(int j=0; j<variables.getBannedToDoList().get(i).size(); j++)
						{
						buffRapport.append("			<todo>\r\n");
						buffRapport.append("				<uuid>"+variables.getBannedToDoList().get(i).get(j).getUUID()+"</uuid>\r\n");
						buffRapport.append("				<description>"+variables.getBannedToDoList().get(i).get(j).getDescription()+"</description>\r\n");
						buffRapport.append("				<user>"+variables.getBannedToDoList().get(i).get(j).getUser()+"</user>\r\n");
						buffRapport.append("			</todo>\r\n");
						}
					}
				buffRapport.append("		</task>\r\n");
				}
			
			buffRapport.append("	</tasks>\r\n");
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
	
	/**
	 * Method used to update task list 
	 * after changes made by the Manager
	 */
	public static void updateTaskList(ArrayList<simpleTask> simpleTaskList)
		{
		/**
		 * We update the real task list
		 */
		if((variables.getTaskList().size() != 0) && (variables.getSimpleTaskList().size() != 0))
			{
			for(int i=0; i<simpleTaskList.size(); i++)
				{
				if(variables.getTaskList().get(i).getId().equals(simpleTaskList.get(i).getId()))
					{
					for(int j=0; j<simpleTaskList.get(i).getToDoList().size(); j++)
						{
						//ToDo List
						variables.getTaskList().get(i).getToDoList().get(j).setStatus(simpleTaskList.get(i).getToDoList().get(j).getStatus());
						variables.getSimpleTaskList().get(i).getToDoList().get(j).setStatus(simpleTaskList.get(i).getToDoList().get(j).getStatus());
						}
					//Task
					variables.getTaskList().get(i).setStatus(simpleTaskList.get(i).getStatus());
					variables.getSimpleTaskList().get(i).setStatus(simpleTaskList.get(i).getStatus());
					}
				else
					{
					variables.getLogger().error("Task sent by Manager is not the same one, it will be ignored");
					}
				}
			}
		}
	
	/**
	 * Method used to write new task config File
	 */
	public static void writeTaskFile()
		{
		File fichier;
		fichier = new File(".\\"+variables.getTaskFileName());
		FileWriter fileWriter = null;
		BufferedWriter tampon = null;
		try
			{
			fileWriter = new FileWriter(fichier);
			tampon = new BufferedWriter(fileWriter);
			
			//Writing the file
			tampon.write("<xml>\r\n");
			tampon.write("	<tasks>\r\n");
			for(int i=0;i<variables.getTabTasks().size(); i++)
				{
				tampon.write("		<task>\r\n");
				for(int j=0;j<variables.getTabTasks().get(i).length; j++)
					{
					String[][] value = variables.getTabTasks().get(i);
					
					//Exception list
					if(value[j][0].equals("exception"))
						{
						tampon.write("			<exception>\r\n");
						for(int a=0;a<variables.getExceptionList().get(i).size(); a++)
							{
							tampon.write("				<except>"+variables.getExceptionList().get(i).get(a)+"</except>\r\n");
							}
						tampon.write("			</exception>\r\n");
						}
					else
						{
						tampon.write("			<"+value[j][0]+">"+value[j][1]+"</"+value[j][0]+">\r\n");
						}
					}
				tampon.write("		</task>\r\n");
				}
			tampon.write("	</tasks>\r\n");
			tampon.write("</xml>\r\n");
			}
		catch(Exception e)
			{
			e.printStackTrace();
			System.out.println(e.getMessage());
			}
		finally
			{
			try
				{
				tampon.flush();
				tampon.close();
				fileWriter.close();
				}
			catch(Exception e)
				{
				System.out.println(e.getMessage());
				e.printStackTrace();
				}
			}
		}	
	
	/**
	 * Method used to add a task to the
	 * banned task list
	 * 
	 * A banned task will never been launched again
	 */
	public static void addBannedTask(Integer i)
		{
		variables.getTaskList().get(i).setToDoList(new ArrayList<toDo>());
		variables.getLogger().debug("Task "+i+" has been banned, please restart "+variables.getNomProg()+" if you want to try again");
		}
		
	
	/*2013*//*RATEL Alexandre 8)*/	
	}
	
