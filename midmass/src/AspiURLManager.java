import Misc.device;
import Utils.GreenFire;
import Utils.report;


/**********************************
 * Class used to manage AspiUrl classes
 * 
 * @author RATEL Alexandre
 **********************************/
public class AspiURLManager extends Thread
	{
	/**
	 * Variables
	 */
	private device d;
	private String ip;
	private int timeOut;
	private int index;
	private MIDMass monMID;
	
	
	public AspiURLManager(String ip, int timeOut, int index, MIDMass monMID)
		{
		this.ip = ip;
		this.timeOut = timeOut;
		this.index = index;
		this.monMID = monMID;
		
		d = new device(ip);
		GreenFire.getStatus().set(index, "processing");
		
		start();
		}
	
	public void run()
		{
		try
			{
			System.out.println("D�but de r�cup�ration des infos");
			new AspiURLInfo(d, timeOut, index, monMID);
			new AspiURLNetwork(d, timeOut, index, monMID);
			
			//Affichage du r�sultat
	    	GreenFire.getStatus().set(index, "success");
	    	System.out.println("Reponse correcte du phone");
	    	monMID.ajoutInfo("Thread "+index+" : informations r�cup�r�s");
	    	report.write(d);
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			GreenFire.getStatus().set(index, "error");
			monMID.ajoutErreur("Thread "+index+" : "+d.getIp()+" : �chec");
			}
		
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}
