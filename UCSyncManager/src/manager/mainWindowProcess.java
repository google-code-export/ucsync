package manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.serverStatusType;

/**********************************
 * Class used to manage main window
 * 
 * @author RATEL Alexandre
 **********************************/
public class mainWindowProcess implements ActionListener, WindowListener
	{
	/**
	 * variables
	 */
	private mainWindow myWindow; 
	
	
	public mainWindowProcess(mainWindow myWindow)
		{
		this.myWindow = myWindow;
		
		}


	/**
	 * Method used to manage action did in the main window
	 */
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == myWindow.go)
			{
			myWindow.go.setEnabled(false);
			variables.setTaskIndex(myWindow.taskList.getSelectedIndex());
			methodesUtiles.updateConnection();
			}
		if(evt.getSource() == myWindow.startStop)
			{
			if(variables.getServerStatus().equals(serverStatusType.stopped))
				{
				
				methodesUtiles.startServer();
				}
			else
				{
				methodesUtiles.stopServer();
				}
			}
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	
	public void windowClosed(WindowEvent arg0)
		{
		variables.getLogger().info("Closing window : System.exit(0)");
		System.exit(0);
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

