package manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import utils.Centrer;
import utils.variables;
import utils.variables.dataTypes;
import utils.variables.serverStatusType;

/**********************************
 * Class used to be the pattern editor window
 * 
 * @author RATEL Alexandre
 **********************************/
public class patternEditor extends JFrame implements ActionListener
	{
	/**
	 * variables
	 */
	private JPanel mainPanel;
	private JPanel footerPanel;
	private JTextField pattern;
	
	private JComboBox valueList,argList;
	private JButton addValue,addArg,addFreeText,addPlusSign,finish;
	private JTextField myText;
	private dataTypes dataType;
	
	public patternEditor(JTextField myText, dataTypes dataType)
		{
		this.myText = myText;
		this.dataType = dataType;
		mainPanel = new JPanel();
		footerPanel = new JPanel();
		
		//Title
		this.setTitle("Pattern Editor");
		
		//Positionnement
		this.setSize(new Dimension(800,120));
		new Centrer(this);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		/**
		 * Main Panel
		 */
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		/**
		 * Header
		 */
		pattern = new JTextField();
		pattern.setText(myText.getText());
		
		/**
		 * Footer
		 */
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		
		if(dataType.equals(dataTypes.line))
			{
			valueList = new JComboBox(new String[]{"cucm.nom","cucm.prenom","cucm.dept","cucm.userid","cucm.number","cucm.nligne"});
			}
		else
			{
			valueList = new JComboBox(new String[]{"cucm.nom","cucm.prenom","cucm.dept","cucm.userid","cucm.number","cucm.tposte","cucm.dname"});
			}
		argList = new JComboBox(new String[]{"*M*","*1M*","*m*","*1m*","*1S/*","*\"\"R\"\"*"});
		
		addValue = new JButton("Add Value");
		addArg = new JButton("Add arg");
		addFreeText = new JButton("Add Free Text");
		addPlusSign = new JButton("+");
		finish = new JButton("Finish");
		finish.setBackground(Color.GREEN);
		finish.setForeground(Color.WHITE);
		footerPanel.add(valueList);
		footerPanel.add(addValue);
		footerPanel.add(argList);
		footerPanel.add(addArg);
		footerPanel.add(addFreeText);
		footerPanel.add(addPlusSign);
		footerPanel.add(finish);
		
		//Assignement
		
		mainPanel.add(new JLabel("Be careful, the system can't check if the pattern you are writing is correct"));
		mainPanel.add(pattern);
		mainPanel.add(footerPanel);
		this.getContentPane().add(mainPanel);
		
		
		
		//Event
		addValue.addActionListener(this);
		addArg.addActionListener(this);
		addFreeText.addActionListener(this);
		addPlusSign.addActionListener(this);
		finish.addActionListener(this);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		}
	

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == this.addValue)
			{
			pattern.setText(pattern.getText()+valueList.getSelectedItem().toString());
			}
		if(evt.getSource() == this.addArg)
			{
			pattern.setText(pattern.getText()+argList.getSelectedItem().toString());
			}
		if(evt.getSource() == this.addFreeText)
			{
			pattern.setText(pattern.getText()+"' '");
			}
		if(evt.getSource() == this.addPlusSign)
			{
			pattern.setText(pattern.getText()+"+");
			}
		if(evt.getSource() == this.finish)
			{
			myText.setText(pattern.getText());
			this.dispose();
			}
		}
	
	
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

