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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.toDoStatusType;
import misc.bannedLine;

/**********************************
 * Class used to Manage To Do List
 * 
 * @author RATEL Alexandre
 **********************************/
public class bannedLister extends JPanel implements ActionListener
	{
	/**
	 * Variables
	 */
	private JCheckBox selectAll;
	private JButton update;
	private JButton deleteAll;
	private ArrayList<bannedLine> listeLine;
	private JPanel listToDoList;
	private JScrollPane scrollbar;
	private JPanel control;
	private JPanel Principale;
	
	
	public bannedLister()
		{
		update = new JButton("Update the Banned List");
		update.setToolTipText("Click here to send new data to the server");
		deleteAll = new JButton("Delete all selected");
		deleteAll.setToolTipText("Click here to delete all selected banned task");
		selectAll = new JCheckBox("Check All",true);
		listeLine = new ArrayList<bannedLine>();
		
		control = new JPanel();
		Principale = new JPanel();
		control.setPreferredSize(new Dimension(3000,25));
		control.setBackground(Color.GRAY);
		update.setBackground(Color.GRAY);
		update.setForeground(Color.WHITE);
		deleteAll.setBackground(Color.GRAY);
		deleteAll.setForeground(Color.WHITE);
		selectAll.setBackground(Color.GRAY);
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		control.setLayout(new BoxLayout(control,BoxLayout.X_AXIS));
		Principale.setLayout(new BoxLayout(Principale,BoxLayout.Y_AXIS));
		listToDoList = new JPanel();
		listToDoList.setLayout(new BoxLayout(listToDoList,BoxLayout.Y_AXIS));
		scrollbar = new JScrollPane(listToDoList);
		
		//Assignation
		control.add(selectAll);
		control.add(deleteAll);
		control.add(Box.createHorizontalGlue());
		control.add(update);
		Principale.add(scrollbar);
		this.add(control);
		this.add(Principale);
		
		//Event
		update.addActionListener(this);
		selectAll.addActionListener(this);
		deleteAll.addActionListener(this);
		
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
			
			if((variables.getBannedToDoList().size() == 0)
					|| (variables.getBannedToDoList().size()<(variables.getTaskIndex()+1))
					|| (variables.getBannedToDoList().get(variables.getTaskIndex()).size() == 0)
					)
				{
				listToDoList.add(new JLabel("No banned toDo"));
				}
			else
				{
				//We remove BannedToDoList duplicates
				methodesUtiles.removeBannedToDoDuplicate();
				
				for(int i=0; i<variables.getBannedToDoList().get(variables.getTaskIndex()).size(); i++)
					{
					bannedLine myLine = new bannedLine(variables.getBannedToDoList().get(variables.getTaskIndex()).get(i));
					
					myLine.setDefaultColor((i%2==0)?Color.WHITE:Color.LIGHT_GRAY);
					
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
		if(evt.getSource() == update)
			{
			methodesUtiles.updateData(false);
			}
		if(evt.getSource() == deleteAll)
			{
			removeBanned();
			
			this.repaint();
			this.validate();
			}
		if(evt.getSource() == selectAll)
			{
			for(int i=0; i<listeLine.size(); i++)
				{
				listeLine.get(i).check();
				}
			}
		}
	
	public void removeBanned()
		{
		boolean deleteFound = false;
		for(int i=0; i<listeLine.size(); i++)
			{
			if(listeLine.get(i).getCheckStatus())
				{
				//we change the corresponding task status
				if(variables.getTaskList().size() != 0)
					{
					for(int j=0; j<variables.getTaskList().get(variables.getTaskIndex()).getToDoList().size(); j++)
						{
						if(variables.getBannedToDoList().get(variables.getTaskIndex()).get(i).getUUID().equals(variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(j).getUUID()))
							{
							variables.getTaskList().get(variables.getTaskIndex()).getToDoList().get(j).setStatus(toDoStatusType.disabled);
							variables.getMyToDoLister().getListeLine().get(j).unBannedThis(false);
							}
						}
					}
				
				listeLine.remove(i);
				listToDoList.remove(i);
				variables.getLogger().debug("Banned toDo "+variables.getBannedToDoList().get(variables.getTaskIndex()).get(i).getUser()
						+" : "+variables.getBannedToDoList().get(variables.getTaskIndex()).get(i).getUUID()
						+" has been unbanned");
				variables.getBannedToDoList().get(variables.getTaskIndex()).remove(i);
				deleteFound = true;
				}
			}
		
		if(deleteFound)
			{
			removeBanned();
			}
		}
	
	/**
	 * Method used to enable/disable control bar
	 */
	public void enableControl(boolean b)
		{
		this.selectAll.setEnabled(b);
		this.update.setEnabled(b);
		this.deleteAll.setEnabled(b);
		}
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

