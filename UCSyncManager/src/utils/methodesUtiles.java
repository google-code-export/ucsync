
package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import manager.getDataFromServer;
import manager.putDataToServer;
import misc.finishedMonitor;
import misc.simpleToDo;
import misc.toDo;

import org.apache.commons.codec.digest.DigestUtils;

import schedule.simpleTask;
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
					JOptionPane.showMessageDialog(null,"La version de JAVA ("+jVer+") install�e n'est pas compatible.\r\nUne version au minimum 1.6.0_20 est n�cessaire\r\nLe programme va maintenant se terminer","Erreur",JOptionPane.ERROR_MESSAGE);
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
	 * Method used to init connection to UCSync server
	 */
	public static void initConnection()
		{
		try
			{
			/**
			 * Init connection
			 */
			variables.setMyS(new Socket(methodesUtiles.getTargetOption("ucsyncserverhost"), Integer.parseInt(methodesUtiles.getTargetOption("ucsyncserverport"))));
			variables.setOut(new ObjectOutputStream(variables.getMyS().getOutputStream()));
			variables.setIn(new ObjectInputStream(variables.getMyS().getInputStream()));
			variables.getLogger().info("Connected to UCSync server");
			
			getDataFromServer myData = new getDataFromServer();
			new finishedMonitor(myData);
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Application failed to init Socket : System.exit(0) : "+exc.getMessage());
			JOptionPane.showMessageDialog(null,"Connection to UCSync server has failed\r\nCheck if network connectivity and server informations are correct","Erreur",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			}
		}
	
	/**
	 * Method used to update connection and get
	 * new data
	 */
	public static void updateConnection()
		{
		try
			{
			/**
			 * Restart connection
			 */
			variables.getMyS().close();
			variables.setMyS(new Socket(methodesUtiles.getTargetOption("ucsyncserverhost"), Integer.parseInt(methodesUtiles.getTargetOption("ucsyncserverport"))));
			variables.setOut(new ObjectOutputStream(variables.getMyS().getOutputStream()));
			variables.setIn(new ObjectInputStream(variables.getMyS().getInputStream()));
			variables.getLogger().info("Connected to UCSync server");
			
			getDataFromServer myData = new getDataFromServer();
			new finishedMonitor(myData);
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Application failed to update socket : "+exc.getMessage());
			JOptionPane.showMessageDialog(null,"Unable to get data","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}
	
	
	
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
	
	/*2013*//*RATEL Alexandre 8)*/
	}