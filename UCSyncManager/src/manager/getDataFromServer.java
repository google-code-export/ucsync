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
 * Class used to manage data import
 * 
 * @author RATEL Alexandre
 **********************************/
public class getDataFromServer extends serverDataMisc
	{
	/**
	 * Variables
	 */
	private boolean clear;
	
	public getDataFromServer(boolean clear)
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
			variables.getOut().writeObject((Object)sendReceiveType.serviceStatus);
			if(((serverStatusType)variables.getIn().readObject()).equals(serverStatusType.stopped))
				{
				variables.setServerStatus(serverStatusType.stopped);
				}
			else
				{
				variables.setServerStatus(serverStatusType.started);
				}
			if(variables.getMyWindow() != null)variables.getMyWindow().updateStartStop();
				
			//First we send data type involved in the communication
			variables.getOut().writeObject((Object)sendReceiveType.getAll);
			variables.getLogger().info("Data type sent with success : "+sendReceiveType.getAll);
			
			//TabTask
			variables.setTabTasks((ArrayList<String[][]>)variables.getIn().readObject());
			variables.getLogger().info("TabTask imported with success");
			
			//TaskList
			variables.setTaskList(((ArrayList<simpleTask>)variables.getIn().readObject()));
			variables.getLogger().info("Task list imported with success");
			
			//BannedList
			variables.setBannedToDoList((ArrayList<ArrayList<simpleToDo>>)variables.getIn().readObject());
			variables.getLogger().info("Banned toDo List imported with success");
			
			if(clear)
				{
				variables.getMyToDoLister().fill();
				variables.getMyToDoLister().enableControl(true);
				variables.getMyBannedLister().fill();
				variables.getMyBannedLister().enableControl(true);
				variables.getMyConfigLister().fill();
				variables.getMyConfigLister().enableControl(true);
				}
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

