package Utils;

import java.util.ArrayList;


/********************************************************************
 * Classe aux methodes static qui permet g�rer l'arrayList Status
 ********************************************************************/



public class GreenFire
	{
	/************
	 * Variables
	 ************/
	//Pour g�rer l'�tat d'avancement 
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
