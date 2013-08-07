
package utils;

import java.util.ArrayList;
import org.apache.log4j.Logger;

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
	private static String nomProg;
	private static String version;
	private static Logger logger;
	private static String configFileContent;
	private static String configFileName;
	private static String taskFileName;
	private static String taskFileContent;
	private static ArrayList<String[][]> tabOptionGlobal;
	private static eMailSender eMSender;
	private static String axlPort;
	private static String versionCCM;
	
	
	
	/**
	 * Contructeur
	 */
	public variables()
		{
		configFileName = new String("configFile.xml");
		setTabOptionGlobal(false);
		axlPort = "8443";
		versionCCM = "8";
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

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		variables.logger = logger;
		}

	public static String getConfigFileContent()
		{
		return configFileContent;
		}

	public static void setConfigFileContent(String configFileContent)
		{
		variables.configFileContent = configFileContent;
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

	public static String getAxlPort()
		{
		return axlPort;
		}

	public static void setAxlPort(String axlPort)
		{
		variables.axlPort = axlPort;
		}

	public static String getVersionCCM()
		{
		return versionCCM;
		}

	public static void setVersionCCM(String versionCCM)
		{
		variables.versionCCM = versionCCM;
		}

	public static String getTaskFileName()
		{
		return taskFileName;
		}

	public static void setTaskFileName(String taskFileName)
		{
		variables.taskFileName = taskFileName;
		}

	public static String getTaskFileContent()
		{
		return taskFileContent;
		}

	public static void setTaskFileContent(String taskFileContent)
		{
		variables.taskFileContent = taskFileContent;
		}

	public static ArrayList<String[][]> getTabOptionGlobal()
		{
		return tabOptionGlobal;
		}

	public static void setTabOptionGlobal(boolean override)
		{
		tabOptionGlobal = MethodesUtiles.getOptionValue(override);
		if(!override)
			{
			//On écrit le fichier
			MethodesUtiles.setOptionValue();
			}
		}
	
	
	/*****
	 * End of getters and Setters 
	 */
	
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
