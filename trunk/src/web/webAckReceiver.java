package web;

import java.net.ServerSocket;
import java.net.Socket;

import utils.MethodesUtiles;
import utils.variables;

/**********************************
 * Class used to manage web ack
 * 
 * @author RATEL Alexandre
 **********************************/
public class webAckReceiver extends Thread
	{
	/**
	 * Variables
	 */
	private ServerSocket ss;
	private boolean isWorking;
	private Socket incoming;
	
	
	public webAckReceiver()
		{
		isWorking = true;
		start();
		}
	
	public void run()
		{
		try
			{
			ss = new ServerSocket(Integer.parseInt(MethodesUtiles.getTargetOption("webserverport")));
			
			while(isWorking)
				{
				System.out.println("En attente de connexion");
				
				//Acceptation de la connexion
				incoming = ss.accept();
				variables.getLogger().info("Web server : Connection accepted from : "+incoming.getInetAddress().toString());
				
				new ackReceiver(incoming).start();
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

