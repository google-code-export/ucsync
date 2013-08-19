package Utils;

import java.util.ArrayList;


/********************************************************************
 * Classe aux methodes static qui permet gérer l'arrayList Status
 ********************************************************************/



public class GreenFire
	{
	/************
	 * Variables
	 ************/
	//Pour gérer l'état d'avancement 
	private static ArrayList<String> status; 
	
	public static void setStatus()
		{
		status = new ArrayList<String>();
		}
	
	public static void addStatus(String currentStatus)
		{
		status.add(currentStatus);
		}
	
	public synchronized static ArrayList<String> getStatus()
		{
		return status;
		}
	
	/*Fin classe*//*AR :)*/
	}
