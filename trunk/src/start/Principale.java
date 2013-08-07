package start;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import utils.MethodesUtiles;
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
		utils.variables.setVersion("3.1");
		//Nom du logiciel
		utils.variables.setNomProg("EXTools");
		
		/**********************
		 * Initialisation de la journalisation
		 ************/
		variables.setLogger(initLogging.init(variables.getNomProg()+"_LogFile.txt"));
		variables.getLogger().info("\r\nEntering application");
		variables.getLogger().info("## Welcome to : "+variables.getNomProg()+" : "+variables.getVersion()+" ##");
		variables.getLogger().info("## Author : RATEL Alexandre : 2013##");
		/**************/
		
		/***********
		 * Initialisation des variables
		 */
		new utils.variables();
		/************/
		
		/********************
		 * Check if java version is compatible
		 ********************/
		MethodesUtiles.checkJavaVersion();
		/*********************/
		
		/*************************
		 * Set the logging level
		 *************************/
		String level = MethodesUtiles.getTargetOption("log4j");
		if(level.compareTo("DEBUG")==0)
			{
			variables.getLogger().setLevel(Level.DEBUG);
			}
		else if (level.compareTo("INFO")==0)
			{
			variables.getLogger().setLevel(Level.INFO);
			}
		
		variables.getLogger().info("Niveau de log d'après le fichier de préférence : "+variables.getLogger().getLevel().toString());
		/*************************/
		
		/*********************************
		 * Lancement du Thread principal
		 *********************************/
		//Have to be written
		/*********************************/
		}
	
	
	public static void main(String[] args)
		{
		new Principale();
		}
	/*2013*//*RATEL Alexandre 8)*/
	}
