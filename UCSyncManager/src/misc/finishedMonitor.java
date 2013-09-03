package misc;

import javax.swing.JLabel;

import utils.variables;

/**********************************
 * Class used to monitor if data request is finished
 * 
 * @author RATEL Alexandre
 **********************************/
public class finishedMonitor extends serverDataMisc
	{
	/**
	 * Variables
	 */
	private serverDataMisc myServer;
	
	public finishedMonitor(serverDataMisc myServer)
		{
		super();
		this.myServer = myServer;
		
		start();
		}
	
	public void run()
		{
		while(!finished)
			{
			if(myServer.isFinished())
				{
				if(variables.getMyWindow() != null)
					{
					variables.getMyWindow().updateList();
					variables.getMyWindow().getWait().setText(" ");
					}
				finished = true;
				}
			else
				{
				try
					{
					if(variables.getMyWindow() != null)
						{
						variables.getMyWindow().getWait().setText("Please wait");
						}
					sleep(250);
					}
				catch(Exception exc)
					{
					exc.printStackTrace();
					variables.getLogger().error(exc);
					}
				}
			}
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

