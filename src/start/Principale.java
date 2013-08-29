package start;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Level;

import schedule.scheduler;
import utils.SOAPGear;
import utils.methodesUtiles;
import utils.initLogging;
import utils.variables;
import web.webAckReceiver;

/*********************************************
 * Main class called "Principale in French"
 * cause I'm French of course ;)
 * 
 * @author RATEL Alexandre
 * *******************************************/
public class Principale
	{
	//Variables
	String version;
	String nom;
	Date now;
	SimpleDateFormat formatHeure;
	
	//Constructeur
	public Principale()
		{
		//version du logiciel
		utils.variables.setVersion("1.0");
		//Nom du logiciel
		utils.variables.setNomProg("UCSync");
		
		/**************************************
		 * Initialisation de la journalisation
		 **************************************/
		variables.setLogger(initLogging.init(variables.getNomProg()+"_LogFile.txt"));
		variables.getLogger().info("\r\n");
		variables.getLogger().info("#### Entering application");
		variables.getLogger().info("## Welcome to : "+variables.getNomProg()+" version "+variables.getVersion());
		variables.getLogger().info("## Author : RATEL Alexandre : 2013");
		/**************/
		
		/***********
		 * Initialisation des variables
		 */
		new utils.variables();
		/************/
		
		/*************
		 * Lectures des fichiers xml de paramétrage
		 */
		try
			{
			//Config file reading
			variables.setTabConfig(methodesUtiles.initConfigValue());
			
			//Task file reading
			variables.setTabTasks(methodesUtiles.initTaskValue());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Application failed to init : System.exit(0)");
			System.exit(0);
			}
		/*********/
		
		/********************
		 * Get exception list
		 ********************/
		try
			{
			variables.setExceptionList(methodesUtiles.initExceptionValue());
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage());
			}
		/*********************/
		
		/********************
		 * Get banned ToDo List
		 ********************/
		try
			{
			variables.setBannedToDoList(methodesUtiles.initBannedToDoList());
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc.getMessage());
			}
		/*********************/
		
		/********************
		 * Check if java version is compatible
		 ********************/
		methodesUtiles.checkJavaVersion();
		/*********************/
		
		/*************************
		 * Set the logging level
		 *************************/
		try
			{
			String level = methodesUtiles.getTargetOption("log4j");
			if(level.compareTo("DEBUG")==0)
				{
				variables.getLogger().setLevel(Level.DEBUG);
				}
			else if (level.compareTo("INFO")==0)
				{
				variables.getLogger().setLevel(Level.INFO);
				}
			variables.getLogger().info("Niveau de log d'après le fichier de configuration : "+variables.getLogger().getLevel().toString());
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc.getMessage());
			variables.getLogger().error("Le niveau de log n'a pas réussi à être lu depuis le fichier de configuration");
			variables.getLogger().error("Le niveau de log est donc fixé au niveau par défaut : INFO");
			variables.getLogger().setLevel(Level.INFO);
			}
		/*************************/
		
		/****
		 * Init eMail server
		 */
		methodesUtiles.initEMailServer();
		/****/
		
		/****
		 * Start internal web server
		 */
		variables.setMyWebServer(new webAckReceiver());
		/****/
		
		/*********************************
		 * Lancement du Thread principal
		 *********************************/
		variables.setMyScheduler(new scheduler());
		/*********************************/
		}
	
	
	public static void main(String[] args)
		{
		new Principale();
		}
	/*2013*//*RATEL Alexandre 8)*/
	}
