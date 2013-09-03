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
public class getDataFromServer extends serverDataMisc
	{
	/**
	 * Variables
	 */
	
	public getDataFromServer()
		{
		super();
		
		start();
		}
	
	public void run()
		{
		/**
		 * Get data from UCSync server
		 */
		try
			{
			//TabTask
			variables.setTabTasks((ArrayList<String[][]>)variables.getIn().readObject());
			variables.getLogger().info("TabTask imported with success");
			
			//TaskList
			variables.setTaskList(((ArrayList<simpleTask>)variables.getIn().readObject()));
			variables.getLogger().info("Task list imported with success");
			
			//BannedList
			variables.setBannedToDoList((ArrayList<ArrayList<simpleToDo>>)variables.getIn().readObject());
			variables.getLogger().info("Banned toDo List imported with success");
			
			variables.getMyToDoLister().fill();
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

