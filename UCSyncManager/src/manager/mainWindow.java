package manager;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import utils.Centrer;
import utils.variables;
import utils.variables.serverStatusType;

/**********************************
 * Class used to be the main window
 * 
 * @author RATEL Alexandre
 **********************************/
public class mainWindow extends JFrame
	{
	/**
	 * variables
	 */
	private JPanel mainPanel;
	private JPanel headerPanel;
	private JPanel footerPanel;
	
	private JLabel wait;
	
	private JTabbedPane myTabbedPane;
	
	private String[] tList;
	public JComboBox taskList;
	public JButton go;
	public JButton startStop;
	
	
	
	public mainWindow()
		{
		mainPanel = new JPanel();
		headerPanel = new JPanel();
		footerPanel = new JPanel();
		
		//Title
		this.setTitle(variables.getNomProg()+" - "+variables.getVersion());
		
		//Positionnement
		this.setSize(new Dimension(1200,600));
		new Centrer(this);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		/**
		 * Main Panel
		 */
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		/**
		 * Header
		 */
		go = new JButton("GO");
		startStop = new JButton("");
		startStop.setToolTipText("Click here to stop/start service. Once stopped, nothing will be synchronised");
		taskList = new JComboBox();
		taskList.setPreferredSize(new Dimension(100,25));
		
		wait = new JLabel(" ");
		
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.setMaximumSize(new Dimension(3000,50));
		headerPanel.add(taskList);
		headerPanel.add(go);
		headerPanel.add(wait);
		headerPanel.add(Box.createHorizontalGlue());
		headerPanel.add(startStop);
		
		/**
		 * Middle
		 */
		myTabbedPane = new JTabbedPane();
		myTabbedPane.addTab("Scanned list", variables.getMyToDoLister());
		myTabbedPane.addTab("Banned list", variables.getMyBannedLister());
		myTabbedPane.addTab("Configuration", variables.getMyConfigLister());
		
		/**
		 * Footer
		 */
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		
		//Assignement
		
		mainPanel.add(headerPanel);
		mainPanel.add(myTabbedPane);
		mainPanel.add(footerPanel);
		this.getContentPane().add(mainPanel);
		
		
		
		//Event
		mainWindowProcess myProcess = new mainWindowProcess(this);
		taskList.addActionListener(myProcess);
		go.addActionListener(myProcess);
		startStop.addActionListener(myProcess);
		this.addWindowListener(myProcess);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setVisible(true);
		
		updateList();
		updateStartStop();
		}
	
	/**
	 * Method used to update list
	 */
	public void updateList()
		{
		if(variables.getTabTasks().size() == 0)
			{
			tList = new String[]{"No Task to Manage - Please click GO to update"};
			}
		else
			{
			tList = new String[variables.getTabTasks().size()];
			for(int i=0; i<tList.length; i++)
				{
				tList[i] = "Manage Task "+(i+1);
				}
			}
		
		taskList.removeAllItems();
		
		for(String s:tList)
			{
			taskList.addItem(s);
			}
		
		taskList.setSelectedIndex(variables.getTaskIndex());
		
		this.repaint();
		this.validate();
		}

	
	public void updateStartStop()
		{
		if(variables.getServerStatus().equals(serverStatusType.stopped))
			{
			this.startStop.setText("Start UCSync");
			}
		else
			{
			this.startStop.setText("Stop UCSync");
			}
		}


	public JTabbedPane getMyTabbedPane()
		{
		return myTabbedPane;
		}

	public void setMyTabbedPane(JTabbedPane myTabbedPane)
		{
		this.myTabbedPane = myTabbedPane;
		}

	public JLabel getWait()
		{
		return wait;
		}

	public void setWait(JLabel wait)
		{
		this.wait = wait;
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

