

//Import
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Misc.device;
import Utils.GreenFire;
import Utils.report;
import Utils.variables;

/************************************************************
 * Classe qui s'occupe de lancer les Threads de récupération
 * des informations
 ************************************************************/


public class Travailleur extends Thread 
	{
	/************
	 * Variables
	 ************/
	int NbrThreadSimultane;
	int timeOut;
	ArrayList<String> ipRange;
	MIDMass monMID;
	
	/***************
	 * Constructeur
	 ***************/
	public Travailleur(ArrayList<String> ipRange, int NbrThreadSimultane, int timeOut, MIDMass monMID)
		{
		this.NbrThreadSimultane = NbrThreadSimultane;
		this.timeOut = timeOut;
		this.monMID = monMID;
		this.ipRange = ipRange;
		
		start();
		}
	
	public void run()
		{
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		variables.setReportFile(new File("./ExtractMacID_"+formatter.format(now)+".txt"));
		report.writeHeader();
		
		for(int i=0; i<ipRange.size(); i++)
			{
			while(true)
				{
				if(processing())
					{
					System.out.println("Nombre d'ip a traiter : "+GreenFire.getStatus().size());
					System.out.println("Thread pour inspecter le device "+(i+1)+" lancé");
					monMID.ajoutInfo("Thread "+i+" : tentative IP Phone : "+ipRange.get(i)+" lancé");
					new AspiURLManager(ipRange.get(i),timeOut,i,monMID);
					break;
					}
				try
					{
					sleep(100);
					}
				catch(Exception e)
					{
					System.out.println(e.getMessage());
					e.printStackTrace();
					}
				}
			System.out.println("Travailleur thread "+i+" terminé");
			}
		}

	public boolean processing()
		{
		int compteur = 0;
		for(int i=0; i<GreenFire.getStatus().size(); i++)
			{
			if(GreenFire.getStatus().get(i).compareTo("processing")==0)
				{
				compteur++;
				}
			}
		if(compteur < NbrThreadSimultane)
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	
	
	/*Fin classe*/
	}
