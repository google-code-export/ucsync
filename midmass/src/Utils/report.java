package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Misc.device;
import Utils.variables.infoDeviceType;
import Utils.variables.infoNetworkType;

/**********************************
 * Class used to write report
 * 
 * @author RATEL Alexandre
 **********************************/
public class report
	{
	
	/**
	 * Write report header file 
	 */
	public static void writeHeader()
		{
		File fichierLog = variables.getReportFile();
		FileWriter monFichierLog = null;
		BufferedWriter tampon  = null;
		
		try
			{
			monFichierLog = new FileWriter(fichierLog,true);
			tampon = new BufferedWriter(monFichierLog);
			
			//Ecriture
			tampon.write("IP,");
			
			for(int i=0; i<infoDeviceType.values().length; i++)
				{
				tampon.write(infoDeviceType.values()[i].name());
				tampon.write(",");
				}
			for(int i=0; i<infoNetworkType.values().length; i++)
				{
				tampon.write(infoNetworkType.values()[i].name());
				if(i != infoNetworkType.values().length-1)
					{
					tampon.write(",");
					}
				}
			
			tampon.write("\r\n");
			}
		catch(IOException exception)
			{
			System.out.println("Erreur d'écriture : "+exception.getMessage());
			}
		finally
			{
			try
				{
				tampon.flush();
				tampon.close();
				monFichierLog.close();
				}
			catch(Exception e)
				{
				System.out.println(e.getMessage());
				}
			}
		}
	
	
	
	
	/**
	 * Write one line with device data
	 */
	public synchronized static void write(device d) throws Exception
		{
		File fichierLog = variables.getReportFile();
		FileWriter monFichierLog = null;
		BufferedWriter tampon  = null;
		
		try
			{
			monFichierLog = new FileWriter(fichierLog,true);
			tampon = new BufferedWriter(monFichierLog);
			
			//Ecriture
			tampon.write(d.getData());
			tampon.write("\r\n");
			}
		catch(Exception exception)
			{
			System.out.println("Erreur d'écriture : "+exception.getMessage());
			throw new Exception("Erreur d'écriture : "+exception.getMessage());
			}
		finally
			{
			try
				{
				tampon.flush();
				tampon.close();
				monFichierLog.close();
				}
			catch(Exception e)
				{
				System.out.println(e.getMessage());
				throw new Exception(e.getMessage());
				}
			}
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

