package web;

import java.net.ServerSocket;
import java.net.Socket;

import misc.workimpl;

import utils.methodesUtiles;
import utils.variables;

/**********************************
 * Class used to manage Manager
 * 
 * @author RATEL Alexandre
 **********************************/
public class webMngtManager extends Thread implements workimpl
	{
	/**
	 * Variables
	 */
	private ServerSocket ss;
	private boolean isWorking;
	private Socket incoming;
	
	
	public webMngtManager()
		{
		isWorking = true;
		start();
		}
	
	public void run()
		{
		try
			{
			ss = new ServerSocket(Integer.parseInt(methodesUtiles.getTargetOption("managerserverport")));
			
			while(isWorking)
				{
				variables.getLogger().info("Internal Management server : Waiting for a new connection");
				
				//Connection accepted
				incoming = ss.accept();
				variables.getLogger().info("management server : Connection accepted from : "+incoming.getInetAddress().toString());
				
				new mngtReceiver(incoming).start();
				}
			}
		catch(Exception exc)
			{
			variables.getLogger().error(exc);
			exc.printStackTrace();
			}
		}

	
	public boolean isWorking()
		{
		return isWorking;
		}

	public void interrupt()
		{
		this.isWorking = false;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

