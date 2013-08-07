
package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

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
					JOptionPane.showMessageDialog(null,"La version de JAVA ("+jVer+") installée n'est pas compatible.\r\nUne version au minimum 1.6.0_20 est nécessaire\r\nLe programme va maintenant se terminer","Information",JOptionPane.INFORMATION_MESSAGE);
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
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
