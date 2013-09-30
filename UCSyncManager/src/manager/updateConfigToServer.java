package manager;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import misc.serverDataMisc;
import misc.simpleToDo;
import schedule.simpleTask;
import utils.variables;
import utils.variables.sendReceiveType;
import utils.variables.serverStatusType;

/**********************************
 * Class used to manage config export
 * 
 * @author RATEL Alexandre
 **********************************/
public class updateConfigToServer extends serverDataMisc
	{
	/**
	 * Variables
	 */
	private boolean clear;
	
	public updateConfigToServer(boolean clear)
		{
		super();
		this.clear = clear;
		
		start();
		}
	
	public void run()
		{
		/**
		 * Get data from UCSync server
		 */
		try
			{
			//First we send data type
			variables.getOut().writeObject((Object)sendReceiveType.sendConfig);
			variables.getLogger().info("Data type sent with success");
			
			//Then we send the corresponding data
			variables.getOut().writeObject(variables.getTabTasks());
			variables.getLogger().info("TabTask exported with success");
			variables.getOut().flush();
			}
		catch(Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			JOptionPane.showMessageDialog(null,"Connection to UCSync server has failed\r\nCheck if network connectivity and server informations are correct","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		finished = true;
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

