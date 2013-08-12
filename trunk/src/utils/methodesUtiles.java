
package utils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
//import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

import schedule.task;

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
	public static ArrayList<String[][]> initConfigValue() throws Exception
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
	
	/***************************************
	 * Method used to get a specific value
	 * in the user preference XML File
	 ***************************************/
	public static String getTargetOption(String node) throws Exception
		{
		variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabConfig().get(0).length; i++)
			{
			if(variables.getTabConfig().get(0)[i][0].compareTo(node)==0)
				{
				variables.getLogger().debug("Valeure trouvée : "+variables.getTabConfig().get(0)[i][1]);
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
	 * in the task config file
	 ***************************************/
	public static String getTargetTask(String node) throws Exception
		{
		int index = getTaskIndex();
		variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabTasks().get(index).length; i++)
			{
			if(variables.getTabTasks().get(index)[i][0].compareTo(node)==0)
				{
				variables.getLogger().debug("Valeure trouvée : "+variables.getTabTasks().get(index)[i][1]);
				return variables.getTabTasks().get(index)[i][1];
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
	public static String getTargetTask(String node, int index) throws Exception
		{
		variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabTasks().get(index).length; i++)
			{
			if(variables.getTabTasks().get(index)[i][0].compareTo(node)==0)
				{
				variables.getLogger().debug("Valeure trouvée : "+variables.getTabTasks().get(index)[i][1]);
				return variables.getTabTasks().get(index)[i][1];
				}
			}
		
		/***********
		 * If this point is reached, the option looked for was not found
		 */
		throw new Exception("Option not found"); 
		}
	
	/************************
	 * Check if java version
	 * is correct
	 ***********************/
	public static void checkJavaVersion()
		{
		try
			{
			String jVer = new String(System.getProperty("java.version"));
			variables.getLogger().info("Version de JRE détecté sur machine cliente : "+jVer);
			if(jVer.contains("1.6"))
				{
				if(Integer.parseInt(jVer.substring(6,8))<16)
					{
					variables.getLogger().info("Le programme a été terminé car la version de java n'est pas compatible");
					System.exit(0);
					}
				}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().info("Erreur lors de la détection de la version de Java",exc);
			}
		}
	/***********************/
	
	/**********************************
	 * Method used to return
	 * a unique ID based on current date and
	 * a random number
	 **********************************/
	public static String getID()
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
	public static void initEMailServer()
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
	public static void sendToAdminList(String sub, String cont, String desc)
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
			variables.getLogger().error("Error during Email sending");
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}
