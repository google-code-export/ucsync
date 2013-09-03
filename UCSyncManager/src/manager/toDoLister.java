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
import javax.swing.JComboBox;
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
import misc.simpleToDo;
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
	private JLabel infoList;
	private boolean canBeUpdate;
	
	private String[] filter;
	private JComboBox filterCombo;
	
	
	public toDoLister()
		{
		canBeUpdate = true;
		filter = new String[]{"No Filter","Only Warn","Only Waiting"};
		filterCombo = new JComboBox(filter);
		filterCombo.setMaximumSize(new Dimension(60,20));
		
		validation = new JButton("Validate this report");
		update = new JButton("Update this report");
		newScan = new JButton("new scan now");
		selectAll = new JCheckBox("Check All",true);
		listeLine = new ArrayList<statusLine>();
		infoList = new JLabel("");
		infoList.setForeground(Color.WHITE);
		
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
		control.add(infoList);
		control.add(filterCombo);
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
		filterCombo.addActionListener(this);
		
		enableControl(false);
		
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
				setInfoList();
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
			if((variables.getTaskList().size() != 0)&&(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size() != 0))
				{
				variables.getTaskList().get(variables.getTaskIndex()).setStatus(taskStatusType.pending);
				methodesUtiles.updateData(true);
				}
			else
				{
				JOptionPane.showMessageDialog(null,"No data to update","Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		if(evt.getSource() == update)
			{
			if((variables.getTaskList().size() != 0)&&(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size() != 0))
				{
				methodesUtiles.updateData(false);
				}
			else
				{
				JOptionPane.showMessageDialog(null,"No data to update","Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		if(evt.getSource() == newScan)
			{
			if((variables.getTaskList().size() != 0)&&(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size() != 0))
				{
				variables.getTaskList().get(variables.getTaskIndex()).setStatus(taskStatusType.toDelete);
				methodesUtiles.updateData(true);
				}
			else
				{
				JOptionPane.showMessageDialog(null,"A scan is currently in progress, please wait","Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		if(evt.getSource() == filterCombo)
			{
			variables.getMyWindow().getWait().setText("Please wait");
			
			if(filterCombo.getSelectedIndex() == 0)
				{
				//All
				for(int i=0; i<listeLine.size(); i++)
					{
					listToDoList.getComponents()[i].setVisible(true);
					}
				}
			else if(filterCombo.getSelectedIndex() == 1)
				{
				//Warn only
				for(int i=0; i<listeLine.size(); i++)
					{
					if((listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.waiting))
							||(listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.disabled)))
						{
						listToDoList.getComponents()[i].setVisible(false);
						}
					else
						{
						listToDoList.getComponents()[i].setVisible(true);
						}
					}
				}
			else
				{
				//Wait only
				for(int i=0; i<listeLine.size(); i++)
					{
					if((listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.conflict))
							||(listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.impossible))
							||(listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.banned)))
						{
						listToDoList.getComponents()[i].setVisible(false);
						}
					else
						{
						listToDoList.getComponents()[i].setVisible(true);
						}
					}
				}
			variables.getMyWindow().getWait().setText(" ");
			this.repaint();
			this.validate();
			}
		if(evt.getSource() == selectAll)
			{
			variables.getMyWindow().getWait().setText("Please wait");
			canBeUpdate = false;
			for(int i=0; i<listeLine.size(); i++)
				{
				if((listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.conflict))
						||(listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.impossible))
						||(listeLine.get(i).getMyToDo().getStatus().equals(toDoStatusType.banned)))
					{
					//we do nothing
					}
				else
					{
					listeLine.get(i).check();
					}
				}
			canBeUpdate = true;
			setInfoList();
			variables.getMyWindow().getWait().setText(" ");
			}
		}
	
	public void setInfoList()
		{
		if(canBeUpdate)
			{
			int total = variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size();
			int warn = 0;
			int wait = 0;
			
			for(int i=0; i<variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size(); i++)
				{
				if((variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.conflict))
						||(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.impossible))
						||(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.banned))
						||(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(i).getStatus().equals(toDoStatusType.disabled)))
					{
					warn++;
					}
				else
					{
					wait++;
					}
				}
			
			infoList.setText(" "+total+" / "+warn+" / "+wait+"  ");
			}
		}
	
	/**
	 * Method used to enable/disable control bar
	 */
	public void enableControl(boolean b)
		{
		this.selectAll.setEnabled(b);
		this.validation.setEnabled(b);
		this.update.setEnabled(b);
		this.newScan.setEnabled(b);
		this.filterCombo.setEnabled(b);
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

