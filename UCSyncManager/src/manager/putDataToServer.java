package manager;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import misc.serverDataMisc;
import misc.simpleToDo;
import schedule.simpleTask;
import utils.variables;

/**********************************
 * Class used to manage data import
 * 
 * @author RATEL Alexandre
 **********************************/
public class putDataToServer extends serverDataMisc
	{
	/**
	 * Variables
	 */
	private boolean clear;
	
	public putDataToServer(boolean clear)
		{
		super();
		this.clear = clear;
		
		start();
		}
	
	public void run()
		{
		/**
		 * Method used to send new values to the 
		 * UCSync server
		 */
		try
			{
			//TaskList
			variables.getOut().writeObject((Object)variables.getTaskList());
			variables.getLogger().info("Task list exported with success");
			
			//BannedList
			variables.getOut().writeObject((Object)variables.getBannedToDoList());
			variables.getLogger().info("Banned toDo List exported with success");
			
			//TabTask
			variables.getOut().writeObject((Object)variables.getTabTasks());
			variables.getLogger().info("TabTask exported with success");
			
			variables.getOut().flush();
			
			JOptionPane.showMessageDialog(null,"Update has been sent with success","Success",JOptionPane.INFORMATION_MESSAGE);
			
			if(clear)
				{
				variables.setTaskList(new ArrayList<simpleTask>());
				variables.getMyToDoLister().fill();
				}
			}
		catch (IOException exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			JOptionPane.showMessageDialog(null,"Connection to UCSync server has failed\r\nCheck if network connectivity and server informations are correct","Erreur",JOptionPane.ERROR_MESSAGE);
			}
		finished = true;
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

