package Utils;

import java.io.File;

/**********************************
 * Class used to store static variable
 * 
 * @author RATEL Alexandre
 **********************************/
public class variables
	{
	/**
	 * Variables
	 */
	private static File reportFile;
	private static File sourceFile;
	public enum infoDeviceType{MACAddress,serialNumber,phoneDN,appLoadID,bootLoadID,versionID,modelNumber,time,date};
	public enum infoNetworkType{UserLocale,TFTPServer1,CallManager1,CallManager2,CallManager3,UserLocaleVersion};
	
	
	
	public static File getReportFile()
		{
		return reportFile;
		}

	public static void setReportFile(File reportFile)
		{
		variables.reportFile = reportFile;
		}

	public static File getSourceFile()
		{
		return sourceFile;
		}

	public static void setSourceFile(File sourceFile)
		{
		variables.sourceFile = sourceFile;
		}

		
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

