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
public class bannedLine extends JPanel implements ActionListener
	{
	/************
	 * variables
	 ************/
	private int step;
	private simpleToDo myToDo;
	
	//Contrôle
	private JCheckBox select;
	private JLabel name;
	private JLabel desc;
	private JLabel info;
	private JLabel displayResult;
	private Color defaultFond;
	
	private JPanel main;
	
	/***************
	 * Constructeur
	 ***************/
	public bannedLine(simpleToDo myToDo) throws Exception
		{
		this.myToDo = myToDo;
		step = 0;
		select = new JCheckBox();
		this.name = new JLabel();
		info = new JLabel();
		displayResult = new JLabel();
		desc = new JLabel();
		
		init();
		
		//Disposition
		main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.X_AXIS));
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//Assignation
		main.add(select);
		main.add(this.name);
		main.add(info);
		main.add(this.desc);
		main.add(Box.createHorizontalGlue());
		main.add(displayResult);
		this.add(main);
		
		//Events
		}
	
	public void setFond(Color couleur)
		{
		main.setBackground(couleur);
		setBackground(couleur);
		select.setBackground(couleur);
		}
	
	public void check()
		{
		select.setSelected(((select.isSelected())?false:true));
		manageSelect();
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
		
		
		}
	
	private void init()
		{
		name.setText(myToDo.getUser());
		
		this.desc.setText(" UUID : \""+myToDo.getUUID()+"\" Has been banned");
		
		info.setText(" [..] ");
		displayResult.setText(myToDo.getStatus().name()+" ");
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
		
		}
	
	/*2012*//*RATEL Alexandre 8)*/
	}
