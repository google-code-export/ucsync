
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
public class MethodesUtiles
	{
	
	/**************************************************
	 * Method used to write the xml config file
	 **************************************************/
	public static void setOptionValue()
		{
		File fichier;
		fichier = new File(".\\"+variables.getConfigFileName());
		FileWriter fileWriter = null;
		BufferedWriter tampon = null;
		try
			{
			fileWriter = new FileWriter(fichier);
			tampon = new BufferedWriter(fileWriter);
			
			//Writing the file
			tampon.write("<xml>\r\n");
			tampon.write("<config>\r\n");
			for(int i=0;i<variables.getTabOptionGlobal().get(0).length; i++)
				{
				tampon.write("<"+variables.getTabOptionGlobal().get(0)[i][0]+">"+variables.getTabOptionGlobal().get(0)[i][1]+"</"+variables.getTabOptionGlobal().get(0)[i][0]+">\r\n");
				}
			tampon.write("</config>\r\n");
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
	
	/**************************************************
	 * Method used to write the xml task file
	 **************************************************/
	public static void setTaskValue()
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
			tampon.write("<tasks>\r\n");
			tampon.write("<usersync>\r\n");
			for(int i=0;i<variables.getTabTasks().get(0).length; i++)
				{
				tampon.write("<"+variables.getTabTasks().get(0)[i][0]+">"+variables.getTabTasks().get(0)[i][1]+"</"+variables.getTabTasks().get(0)[i][0]+">\r\n");
				}
			tampon.write("</usersync>\r\n");
			tampon.write("</tasks>\r\n");
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
	
	/************************************************
	 * Method used to get option write in the config file
	 ************************************************/
	public static ArrayList<String[][]> getOptionValue(boolean override)
		{
		String file = null;
		ArrayList<String[][]> answer;
		ArrayList<String> listParams;
		listParams = new ArrayList<String>();
		
		if(!override)
			{
			try
				{
				variables.getLogger().info("Lecture du fichier de préférence : "+variables.getConfigFileName());
				file = xMLReader.fileRead(".\\"+variables.getConfigFileName());
				
				listParams.add("config");
				answer= xMLGear.getResultListTab(file, listParams);
				
				return answer;
				}
			catch(FileNotFoundException fnfexc)
				{
				variables.getLogger().error("Fichier "+variables.getConfigFileName()+" non trouvé",fnfexc);
				fnfexc.printStackTrace();
				}
			catch(IOException ioexc)
				{
				ioexc.printStackTrace();
				variables.getLogger().error(ioexc.getMessage(),ioexc);
				}
			catch(Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc.getMessage(),exc);
				}
			}
		
		/************************************************
         * Si le fichier n'est pas trouvé on renvoi les
         * valeurs par défauts
         ************************************************/
        variables.getLogger().info("Génération des variables par défaut");
        answer = new ArrayList<String[][]>();
        String[][] tab = new String[14][2];
        tab[0][0] = "log4j";
        tab[0][1] = "DEBUG";
        tab[1][0] = "timeout";
        tab[1][1] = "15";
        tab[2][0] = "axlhost";
        tab[2][1] = "127.0.0.1";
        tab[3][0] = "axlport";
        tab[3][1] = "8443";
        tab[4][0] = "axlusername";
        tab[4][1] = "AXLUser";
        tab[5][0] = "axlpassword";
        tab[5][1] = "CCMCisco92";
        answer.add(tab);
        return answer;
        }
	
	
		/************************************************
		 * Method used to get data write in the task file
		 ************************************************/
		public static ArrayList<String[][]> getTaskValue(boolean override)
			{
			String file = null;
			ArrayList<String[][]> answer;
			ArrayList<String> listParams;
			listParams = new ArrayList<String>();
			
			if(!override)
				{
				try
					{
					variables.getLogger().info("Lecture du fichier des taches : "+variables.getTaskFileName());
					file = xMLReader.fileRead(".\\"+variables.getTaskFileName());
					
					listParams.add("tasks");
					listParams.add("usersync");
					answer= xMLGear.getResultListTab(file, listParams);
					
					return answer;
					}
				catch(FileNotFoundException fnfexc)
					{
					variables.getLogger().error("Fichier "+variables.getTaskFileName()+" non trouvé",fnfexc);
					fnfexc.printStackTrace();
					}
				catch(IOException ioexc)
					{
					ioexc.printStackTrace();
					variables.getLogger().error(ioexc.getMessage(),ioexc);
					}
				catch(Exception exc)
					{
					exc.printStackTrace();
					variables.getLogger().error(exc.getMessage(),exc);
					}
				}
		
		
		/************************************************
		 * Si le fichier n'est pas trouvé on renvoi les
		 * valeurs par défauts
		 ************************************************/
		variables.getLogger().info("Génération des taches par défaut");
		answer = new ArrayList<String[][]>();
		String[][] tab = new String[10][2];
		tab[0][0] = "description";
		tab[0][1] = "";
		tab[1][0] = "descriptiontoolong";
		tab[1][1] = "";
		tab[2][0] = "linedescription";
		tab[2][1] = "";
		tab[3][0] = "linedescriptiontoolong";
		tab[3][1] = "";
		tab[4][0] = "alertingname";
		tab[4][1] = "*M*cucm.nom+' '+*1M*cucm.prenom";
		tab[5][0] = "alertingnametoolong";
		tab[5][1] = "*M*cucm.nom";
		tab[6][0] = "display";
		tab[6][1] = "*M*cucm.nom+' '+*1M*cucm.prenom";
		tab[7][0] = "displaytoolong";
		tab[7][1] = "*M*cucm.nom";
		tab[8][0] = "linetextlabel";
		tab[8][1] = "*M*cucm.nom+' '+*1M*cucm.prenom";
		tab[9][0] = "linetextlabeltoolong";
		tab[9][1] = "*M*cucm.nom";
		answer.add(tab);
		return answer;
		}
	
	/**********************************
	 * Method used to define one value
	 * in the user preference XML file
	 **********************************/
	public static void setTargetOption(String node, String value)
		{
		boolean present = false;
		for(int i=0;i<variables.getTabOptionGlobal().get(0).length; i++)
			{
			if(variables.getTabOptionGlobal().get(0)[i][0].compareTo(node)==0)
				{
				present = true;
				variables.getTabOptionGlobal().get(0)[i][1] = value;
				}
			}
		if(!present)
			{
			System.out.println("## Redéfinition de l'option : "+node+" : "+value);
			/**
			 * Si on arrive ici, c'est que la valeur que l'on veut définir
			 * n'existe pas dans le fichier actuel de préférence
			 * Il faut donc ajouter l'option
			 */
			//On récupère le tableau de valeur actuel
			String[][] optionTab = variables.getTabOptionGlobal().get(0);
			
			//On en créer un nouveau plus grand contenant les nouvelles valeurs
			String[][] newOptionTab = new String[optionTab.length+1][2];
			for(int i=0;i<optionTab.length; i++)
				{
				newOptionTab[i][0] = optionTab[i][0];
				newOptionTab[i][1] = optionTab[i][1];
				}
			
			//On ajoute la nouvelle option au tableau
			newOptionTab[newOptionTab.length-1][0] = node;
			newOptionTab[newOptionTab.length-1][1] = value;
			
			//On redéfinit le tableau global des options
			variables.getTabOptionGlobal().clear();
			variables.getTabOptionGlobal().add(newOptionTab);
			}
		setOptionValue();
		}
	
	/**********************************
	 * Method used to define one value
	 * in the task config file
	 **********************************/
	public static void setTargetTask(String node, String value)
		{
		boolean present = false;
		for(int i=0;i<variables.getTabTasks().get(0).length; i++)
			{
			if(variables.getTabTasks().get(0)[i][0].compareTo(node)==0)
				{
				present = true;
				variables.getTabTasks().get(0)[i][1] = value;
				}
			}
		if(!present)
			{
			System.out.println("## Redéfinition de la tâche : "+node+" : "+value);
			/**
			 * Si on arrive ici, c'est que la valeur que l'on veut définir
			 * n'existe pas dans le fichier actuel de préférence
			 * Il faut donc ajouter l'option
			 */
			//On récupère le tableau de valeur actuel
			String[][] optionTab = variables.getTabTasks().get(0);
			
			//On en créer un nouveau plus grand contenant les nouvelles valeurs
			String[][] newOptionTab = new String[optionTab.length+1][2];
			for(int i=0;i<optionTab.length; i++)
				{
				newOptionTab[i][0] = optionTab[i][0];
				newOptionTab[i][1] = optionTab[i][1];
				}
			
			//On ajoute la nouvelle option au tableau
			newOptionTab[newOptionTab.length-1][0] = node;
			newOptionTab[newOptionTab.length-1][1] = value;
			
			//On redéfinit le tableau global des options
			variables.getTabTasks().clear();
			variables.getTabTasks().add(newOptionTab);
			}
		setTaskValue();
		}
	
	/***************************************
	 * Method used to get a specific value
	 * in the user preference XML File
	 ***************************************/
	public static String getTargetOption(String node)
		{
		variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabOptionGlobal().get(0).length; i++)
			{
			if(variables.getTabOptionGlobal().get(0)[i][0].compareTo(node)==0)
				{
				variables.getLogger().debug("Valeure trouvée : "+variables.getTabOptionGlobal().get(0)[i][1]);
				return variables.getTabOptionGlobal().get(0)[i][1];
				}
			}
		
		/******
		 * Si on arrive ici, c'est que l'option recherché n'existe pas.
		 * On renvoi donc les options par défauts
		 */
		variables.setTabOptionGlobal(true);
		return getTargetOption(node);
		}
	
	/***************************************
	 * Method used to get a specific value
	 * in the task config file
	 ***************************************/
	public static String getTargetTask(String node)
		{
		variables.getLogger().debug("Variable cherchée : "+node);
		for(int i=0;i<variables.getTabTasks().get(0).length; i++)
			{
			if(variables.getTabTasks().get(0)[i][0].compareTo(node)==0)
				{
				variables.getLogger().debug("Valeure trouvée : "+variables.getTabTasks().get(0)[i][1]);
				return variables.getTabOptionGlobal().get(0)[i][1];
				}
			}
		
		/******
		 * Si on arrive ici, c'est que l'option recherché n'existe pas.
		 * On renvoi donc les options par défauts
		 */
		variables.setTabTasks(true);
		return getTargetTask(node);
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
