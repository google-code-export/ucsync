package start;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Level;

import schedule.scheduler;
import utils.methodesUtiles;
import utils.initLogging;
import utils.variables;

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
			variables.getLogger().info("Lecture du fichier de configuration : "+variables.getConfigFileName());
			variables.setTabConfig(methodesUtiles.initConfigValue());
			
			variables.getLogger().info("Lecture du fichier des taches : "+variables.getTaskFileName());
			variables.setTabTasks(methodesUtiles.initTaskValue());
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Il n'est pas acceptable que l'initialisation est échouée. Fin du programme : System.exit(0)");
			System.exit(0);
			}
		/*********/
		
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
		
		/*********************************
		 * Lancement du Thread principal
		 *********************************/
		new scheduler();
		/*********************************/
		}
	
	
	public static void main(String[] args)
		{
		new Principale();
		}
	/*2013*//*RATEL Alexandre 8)*/
	}
