package manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import schedule.simpleTask;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.taskStatusType;
import utils.variables.toDoStatusType;

import misc.finishedMonitor;
import misc.statusLine;

/**********************************
 * Class used to Manage To Do List
 * 
 * @author RATEL Alexandre
 **********************************/
public class toDoLister extends JPanel implements ActionListener
	{
	/**
	 * Variables
	 */
	private JCheckBox selectAll;
	private JButton validation;
	private JButton update;
	private JButton newScan;
	private ArrayList<statusLine> listeLine;
	private JPanel listToDoList;
	private JScrollPane scrollbar;
	private JPanel control;
	private JPanel Principale;
	
	
	public toDoLister()
		{
		validation = new JButton("Validate this report");
		update = new JButton("Update this report");
		newScan = new JButton("new scan now");
		selectAll = new JCheckBox("Check All",true);
		listeLine = new ArrayList<statusLine>();
		
		control = new JPanel();
		Principale = new JPanel();
		control.setPreferredSize(new Dimension(3000,25));
		control.setBackground(Color.GRAY);
		validation.setBackground(Color.GRAY);
		validation.setForeground(Color.WHITE);
		update.setBackground(Color.GRAY);
		update.setForeground(Color.WHITE);
		newScan.setBackground(Color.GRAY);
		newScan.setForeground(Color.WHITE);
		selectAll.setBackground(Color.GRAY);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		control.setLayout(new BoxLayout(control,BoxLayout.X_AXIS));
		Principale.setLayout(new BoxLayout(Principale,BoxLayout.Y_AXIS));
		listToDoList = new JPanel();
		listToDoList.setLayout(new BoxLayout(listToDoList,BoxLayout.Y_AXIS));
		scrollbar = new JScrollPane(listToDoList);
		
		//Assignation
		control.add(selectAll);
		control.add(Box.createHorizontalGlue());
		control.add(newScan);
		control.add(update);
		control.add(validation);
		Principale.add(scrollbar);
		this.add(control);
		this.add(Principale);
		
		//Event
		validation.addActionListener(this);
		update.addActionListener(this);
		newScan.addActionListener(this);
		selectAll.addActionListener(this);
				
		fill();
		}
	
	/**
	 * Method used to fill the panel
	 */
	public void fill()
		{
		try
			{
			listToDoList.removeAll();
			listeLine.clear();
			
			if((variables.getTaskList().size() == 0) || (variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size() == 0))
				{
				listToDoList.add(new JLabel("No data to display"));
				}
			else
				{
				for(int i=0; i<variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size(); i++)
					{
					statusLine myLine = new statusLine(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i));
					if(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.impossible))
						{
						myLine.setDefaultColor(Color.red);
						}
					else if(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.banned))
						{
						myLine.bannedThis();
						}
					else
						{
						myLine.setDefaultColor((i%2==0)?Color.WHITE:Color.LIGHT_GRAY);
						}
					listeLine.add(myLine);
					listToDoList.add(myLine);
					}
				}
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			}
		this.repaint();
		this.validate();
		}

	
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == validation)
			{
			variables.getTaskList().get(variables.getTaskIndex()).setStatus(taskStatusType.pending);
			putDataToServer myPut = new putDataToServer(true);
			new finishedMonitor(myPut);
			}
		if(evt.getSource() == update)
			{
			putDataToServer myPut = new putDataToServer(false);
			new finishedMonitor(myPut);
			}
		if(evt.getSource() == newScan)
			{
			variables.getTaskList().get(variables.getTaskIndex()).setStatus(taskStatusType.toDelete);
			putDataToServer myPut = new putDataToServer(true);
			new finishedMonitor(myPut);
			}
		if(evt.getSource() == selectAll)
			{
			for(int i=0; i<listeLine.size(); i++)
				{
				listeLine.get(i).check();
				}
			}
		}
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

