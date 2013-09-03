package misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.convertSOAPToString;
import utils.variables;
import utils.variables.toDoStatusType;

/*************************************
 * Class used to display one line in
 * the AXL status window
 *************************************/
public class statusLine extends JPanel implements ActionListener, MouseListener
	{
	/************
	 * variables
	 ************/
	private int step;
	private simpleToDo myToDo;
	
	//Contr�le
	private JCheckBox select;
	private JLabel name;
	private JLabel desc;
	private JLabel info;
	private JLabel displayResult;
	private JButton banned;
	private Color defaultFond;
	
	private JPanel main;
	private JPanel warn;
	
	/***************
	 * Constructeur
	 ***************/
	public statusLine(simpleToDo myToDo) throws Exception
		{
		this.myToDo = myToDo;
		step = 0;
		select = new JCheckBox();
		this.name = new JLabel();
		info = new JLabel();
		displayResult = new JLabel();
		banned = new JButton();
		desc = new JLabel();
		
		init();
		
		//Disposition
		main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.X_AXIS));
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//Warning
		warn = new JPanel();
		warn.setLayout(new BoxLayout(warn,BoxLayout.Y_AXIS));
		if(myToDo.isConflictDetected()||myToDo.isProblemDetected())
			{
			for(int i=0; i<myToDo.getConflictList().size(); i++)
				{
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.X_AXIS));
				myPanel.add(Box.createHorizontalStrut(15));
				myPanel.add(new JLabel("WARN : "+myToDo.getConflictList().get(i)));
				myPanel.add(Box.createHorizontalGlue());
				warn.add(myPanel);
				}
			for(int i=0; i<myToDo.getProblemList().size(); i++)
				{
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.X_AXIS));
				myPanel.add(Box.createHorizontalStrut(15));
				myPanel.add(new JLabel("WARN : "+myToDo.getProblemList().get(i)));
				myPanel.add(Box.createHorizontalGlue());
				warn.add(myPanel);
				}
			}
		
		//Assignation
		main.add(select);
		main.add(this.name);
		main.add(info);
		main.add(this.desc);
		main.add(Box.createHorizontalGlue());
		main.add(displayResult);
		main.add(banned);
		//main.add(new JLabel(" "));
		this.add(main);
		this.add(warn);
		
		//Events
		banned.addActionListener(this);
		select.addActionListener(this);
		info.addMouseListener(this);
		}
	
	public void setFond(Color couleur)
		{
		main.setBackground(couleur);
		for (int i = 0; i < warn.getComponents().length; i++)
			{
			warn.getComponents()[i].setBackground(couleur);
			}
		setBackground(couleur);
		select.setBackground(couleur);
		banned.setBackground(couleur);
		}
	
	public void check()
		{
		if(!myToDo.getStatus().equals(toDoStatusType.impossible))
			{
			select.setSelected(((select.isSelected())?false:true));
			manageSelect();
			}
		}
	
	public JCheckBox getSelect()
		{
		return this.select;
		}
	
	public boolean getCheckStatus()
		{
		return select.isSelected();
		}
	
	public void enableDisableSelect(boolean b)
		{
		select.setEnabled(b);
		}

	public String getResult()
		{
		return displayResult.getText();
		}

	public void setResult(String result)
		{
		displayResult.setText(result);
		}

	public void setName(String name)
		{
		this.name.setText(name+" | ");
		}

	public void setDesc(String desc)
		{
		this.desc.setText(desc);
		}

	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.banned)
			{
			if(banned.getText().compareTo(">")==0)
				{
				this.setFond(Color.GRAY);
				variables.getBannedToDoList().get(variables.getTaskIndex()).add(myToDo);
				this.desc.setText(myToDo.getDescription()+" |  Will be banned");
				this.displayResult.setText("Banned ");
				this.banned.setText("<");
				}
			else
				{
				variables.getBannedToDoList().get(variables.getTaskIndex()).remove(myToDo);
				init();
				this.setFond(this.defaultFond);
				this.banned.setText(">");
				}
			}
		
		if(evt.getSource() == this.select)
			{
			manageSelect();
			}
		}
	
	private void init()
		{
		name.setText(myToDo.getUser());
		if(myToDo.isConflictDetected())
			{
			this.desc.setText(myToDo.getDescription()+" |  \""+myToDo.getCurrentData()+"\" will not be update by \""+myToDo.getNewData()+"\"");
			}
		else if(myToDo.isProblemDetected())
			{
			this.desc.setText(myToDo.getDescription()+" |  \""+myToDo.getCurrentData()+"\" will not be update by \""+myToDo.getNewData()+"\"");
			}
		else
			{
			this.desc.setText(myToDo.getDescription()+" |  \""+myToDo.getCurrentData()+"\" will be update by \""+myToDo.getNewData()+"\"");
			}
		info.setText(" [..] ");
		displayResult.setText(myToDo.getStatus().name()+" ");
		banned.setText(">");
		
		if(myToDo.getStatus().equals(toDoStatusType.impossible))
			{
			disablePane();
			select.setSelected(false);
			}
		if(myToDo.getStatus().equals(toDoStatusType.conflict))
			{
			select.setSelected(false);
			}
		if(myToDo.getStatus().equals(toDoStatusType.waiting))
			{
			select.setSelected(true);
			}
		}
	
	public void setDefaultColor(Color myColor)
		{
		this.defaultFond = myColor;
		this.setFond(myColor);
		}
	
	private void disablePane()
		{
		select.setEnabled(false);
		}
	
	private void manageSelect()
		{
		if(select.isSelected())
			{
			myToDo.setStatus(toDoStatusType.waiting);
			}
		else
			{
			myToDo.setStatus(toDoStatusType.disabled);
			}
		}
	
	public void mouseClicked(MouseEvent evt)
		{
		if(evt.getSource() == this.info)
			{
			new axlRequestContentWindow(myToDo.getSoapMessage());
			}
		}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0)
		{
		// TODO Auto-generated method stub
		
		}
	
	/*2012*//*RATEL Alexandre 8)*/
	}