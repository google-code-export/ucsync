//Imports
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.*;

import Misc.device;
import Utils.GreenFire;
import Utils.xMLGear;
import Utils.variables.infoDeviceType;
import Utils.variables.infoNetworkType;

public class AspiURLNetwork
	{
	//Variables
	String adresseIP;
	int adresseActuelle;
	String inputline;
	String total;
	int waitTime;
	String ObjetXMLReponse;
	int index;
	MIDMass monMID;
	private device myDevice;
	
	//Connexion Socket
	BufferedReader in;
	BufferedWriter  out;
	
	//Connexion http
	URL myUrl;
	HttpURLConnection connexion;
	
	
	//Constructeur
	public AspiURLNetwork(device myDevice,int waitTime, int index, MIDMass monMID) throws Exception
		{
		this.myDevice= myDevice; 
		this.adresseIP = myDevice.getIp();
		inputline = new String();
		total = new String();
		this.waitTime = waitTime;
		this.index = index;
		this.monMID = monMID;
		
		launch();
		}

	/***************************************************
	 * Méthode qui envoi la requete http au serveur et 
	 * réceptionne la réponse
	 ***************************************************/
	public void launch() throws Exception
		{
		try
			{
			System.out.println("Début de récupération des info Network");
			myUrl = new URL("http://"+adresseIP+"/NetworkConfigurationX");
			
			connexion = null;
			connexion = (HttpURLConnection)myUrl.openConnection();
			connexion.setRequestMethod("GET");
			connexion.setConnectTimeout(waitTime*1000);
			connexion.setRequestProperty("Content-Type", "text/html");
			connexion.setUseCaches(false);
			connexion.setDoInput(true);
			connexion.setDoOutput(true);
			
		    //Accueil de la réponse
		    in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		    while ((inputline = in.readLine()) != null)
	        	{
	            ObjetXMLReponse += inputline;
	         	}
		    
		    System.out.println("Reponse thread "+index+" : "+ObjetXMLReponse);
		    
		    /************************
		     * Lecture de la réponse
		     ************************/
		    
		    if(Pattern.matches(".*<NetworkConfiguration>.*", ObjetXMLReponse))
		    	{
		    	ObjetXMLReponse = ObjetXMLReponse.replace("null", "");
		    	ObjetXMLReponse = ObjetXMLReponse.replace("<NetworkConfiguration>", "<Device><NetworkConfiguration>");
		    	ObjetXMLReponse += "</Device>"; 
		    	System.out.println("Reponse thread "+index+" remise en forme : "+ObjetXMLReponse);
		    	loadAdresse();
		    	}
		    else
		    	{
		    	//Affichage du résultat
		    	System.out.println("Reponse erroné du phone");
		    	throw new Exception("Reponse erroné du phone");
		    	}
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			throw new Exception("Erreur de récupération d'information");
			}
		finally
			{
			try
				{
				in.close();
				connexion.disconnect();
				}
			catch(Exception e)
				{
				System.out.println("BIG ERROR : "+e.getMessage());
				e.printStackTrace();
				throw new Exception("Erreur de récupération d'information");
				}
			}
		}
	
	public void loadAdresse() throws IndexOutOfBoundsException
		{
		try
			{
			for(int i=0; i<infoNetworkType.values().length; i++)
				{
				ArrayList<String> listParams = new ArrayList<String>();
				listParams.add("NetworkConfiguration");
				listParams.add(infoNetworkType.values()[i].name());
				myDevice.setInfo(infoNetworkType.values()[i],xMLGear.getResultList(ObjetXMLReponse, listParams).get(0));
				}
			}
		catch(Exception exc)
			{
			System.out.println("Error Extract du XML :"+exc.getMessage());
			exc.printStackTrace();
			}
		}
	
	
	/*Fin Classe*//*AR :)*/
	}
