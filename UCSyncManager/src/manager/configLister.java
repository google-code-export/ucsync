package manager;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import utils.variables;

/**********************************
 * Class used to Manage To Do List
 * 
 * @author RATEL Alexandre
 **********************************/
public class configLister extends JPanel
	{
	/**
	 * Variables
	 */
	private JPanel header;
	private JPanel main;
	private JScrollPane scrollbar;
	
	public JButton update;
	
	private JLabel when,smartlinesearch,getmorethanprimaryline,ackmode,csvreport,testmode,axlhost,axlusername,
	axlpassword,devicedescription,linedescription,linealertingname,linedisplay,linetextlabel,lineexternalphonenumbermask,
	maxnumchar,replacefrenchchar,displayurlinreport;
	
	public JTextField axlhostText,axlusernameText,axlpasswordText,devicedescriptionText,devicedescriptiontoolongText,linedescriptionText,linedescriptiontoolongText,
	linealertingnameText,linealertingnametoolongText,linedisplayText,linedisplaytoolongText,linetextlabelText,linetextlabeltoolongText,lineexternalphonenumbermaskText;
	
	public JCheckBox smartlinesearchBox,getmorethanprimarylineBox,csvreportBox,testmodeBox,replacefrenchcharBox,displayurlinreportBox;
	
	public JComboBox whenCombo,hourCombo,minuteCombo,oneHourCombo,ackmodeCombo,maxnumcharCombo;
	
	public configLister()
		{
		/**
		 * Variables
		 */
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Header
		header = new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));
		header.setBackground(Color.GRAY);
		
		update = new JButton("Update");
		update.setBackground(Color.GRAY);
		update.setForeground(Color.WHITE);
		
		main = new JPanel();
		main.setLayout(new GridLayout(21,3));
		//main.setSize(300, 500);
		
		when = new JLabel("Schedule ");
		smartlinesearch = new JLabel("Smart line search ");
		getmorethanprimaryline = new JLabel("Get more than primary line ");
		ackmode = new JLabel("Report mode ");
		csvreport = new JLabel("Report using csv format ");
		testmode = new JLabel("Test mode ");
		axlhost = new JLabel("AXL Server ");
		axlusername = new JLabel("AXL Username ");
		axlpassword = new JLabel("AXL new password");
		devicedescription = new JLabel("Device description");
		linedescription = new JLabel("Line description");
		linealertingname = new JLabel("Alerting name");
		linedisplay = new JLabel("Display");
		linetextlabel = new JLabel("Line text label");
		lineexternalphonenumbermask = new JLabel("External phone number mask");
		maxnumchar = new JLabel("Maximum length ");
		replacefrenchchar = new JLabel("Replace non ASCII charactere ");
		displayurlinreport = new JLabel("Display URL in report");
		
		axlhostText = new JTextField();
		axlusernameText = new JTextField();
		axlpasswordText = new JTextField();
		devicedescriptionText = new JTextField();
		devicedescriptiontoolongText = new JTextField();
		linedescriptionText = new JTextField();
		linedescriptiontoolongText = new JTextField();
		linealertingnameText = new JTextField();
		linealertingnametoolongText = new JTextField();
		linedisplayText = new JTextField();
		linedisplaytoolongText = new JTextField();
		linetextlabelText = new JTextField();
		linetextlabeltoolongText = new JTextField();
		lineexternalphonenumbermaskText = new JTextField();
		
		smartlinesearchBox = new JCheckBox();
		csvreportBox = new JCheckBox();
		testmodeBox = new JCheckBox();
		replacefrenchcharBox = new JCheckBox();
		getmorethanprimarylineBox = new JCheckBox();
		displayurlinreportBox = new JCheckBox();
		
		whenCombo = new JComboBox(new String[]{"CONTINUOUS","DAILY","EVERY"});
		hourCombo = new JComboBox(new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"});
		oneHourCombo = new JComboBox(new String[]{"1","2","3","4","5","6","7","8","9"});
		ackmodeCombo = new JComboBox(new String[]{"report","manual","auto"});
		
		String[] minute = new String[60];
		for(int i=0; i<minute.length; i++)
			{
			if(i<10)
				{
				minute[i] = "0"+Integer.toString(i);
				}
			else
				{
				minute[i] = Integer.toString(i);
				}
			}
		minuteCombo = new JComboBox(minute);
		
		String[] maxnum = new String[50];
		for(int i=0; i<maxnum.length; i++)
			{
			maxnum[i] = Integer.toString(i+1);
			}
		maxnumcharCombo = new JComboBox(maxnum);
		
		/**
		 * Disposition
		 */
		//Schedule
		JPanel whenPane = new JPanel();
		whenPane.add(hourCombo);
		whenPane.add(oneHourCombo);
		hourCombo.setEnabled(false);
		minuteCombo.setEnabled(false);
		oneHourCombo.setVisible(false);
		whenPane.add(new JLabel(":"));
		whenPane.add(minuteCombo);
		main.add(when);main.add(whenCombo);main.add(whenPane);
		
		//Smart line search
		main.add(smartlinesearch);main.add(smartlinesearchBox);main.add(new JLabel(""));
		
		//Get more than primary line
		main.add(getmorethanprimaryline);main.add(getmorethanprimarylineBox);main.add(new JLabel(""));
		
		//Ack mode
		main.add(ackmode);main.add(ackmodeCombo);main.add(new JLabel(""));
		
		//CSV report
		main.add(csvreport);main.add(csvreportBox);main.add(new JLabel(""));
		
		//Test mode
		main.add(testmode);main.add(testmodeBox);main.add(new JLabel(""));
		
		//Max charactere
		main.add(maxnumchar);main.add(maxnumcharCombo);main.add(new JLabel(""));
		
		//Replace french char
		main.add(replacefrenchchar);main.add(replacefrenchcharBox);main.add(new JLabel(""));
		
		//Display URL in report
		main.add(displayurlinreport);main.add(displayurlinreportBox);main.add(new JLabel(""));
		
		//AXL
		main.add(new JLabel(""));main.add(new JLabel(""));main.add(new JLabel(""));
		main.add(axlhost);main.add(axlhostText);main.add(new JLabel(""));
		main.add(axlusername);main.add(axlusernameText);main.add(new JLabel(""));
		main.add(axlpassword);main.add(axlpasswordText);main.add(new JLabel(""));
		
		//pattern
		main.add(new JLabel(""));main.add(new JLabel(""));main.add(new JLabel(""));
		main.add(new JLabel(""));main.add(new JLabel("Normal length"));main.add(new JLabel("Too long"));
		main.add(devicedescription);main.add(devicedescriptionText);main.add(devicedescriptiontoolongText);
		main.add(linedescription);main.add(linedescriptionText);main.add(linedescriptiontoolongText);
		main.add(linealertingname);main.add(linealertingnameText);main.add(linealertingnametoolongText);
		main.add(linedisplay);main.add(linedisplayText);main.add(linedisplaytoolongText);
		main.add(linetextlabel);main.add(linetextlabelText);main.add(linetextlabeltoolongText);
		main.add(lineexternalphonenumbermask);main.add(lineexternalphonenumbermaskText);main.add(new JLabel(""));
		
		scrollbar = new JScrollPane(main);		
		
		header.add(Box.createHorizontalGlue());
		header.add(update);
		
		this.add(header);
		this.add(scrollbar);
		
		//Events
		configListerProcess myProcess = new configListerProcess(this);
		update.addActionListener(myProcess);
		whenCombo.addActionListener(myProcess);
		devicedescriptionText.addMouseListener(myProcess);
		devicedescriptiontoolongText.addMouseListener(myProcess);
		linedescriptionText.addMouseListener(myProcess);
		linedescriptiontoolongText.addMouseListener(myProcess);
		linealertingnameText.addMouseListener(myProcess);
		linealertingnametoolongText.addMouseListener(myProcess);
		linedisplayText.addMouseListener(myProcess);
		linedisplaytoolongText.addMouseListener(myProcess);
		linetextlabelText.addMouseListener(myProcess);
		linetextlabeltoolongText.addMouseListener(myProcess);
		lineexternalphonenumbermaskText.addMouseListener(myProcess);
		
		
		enableControl(false);
		
		fill();
		}
	
	/**
	 * Method used to fill configuration tab 
	 */
	public void fill()
		{
		if((variables.getTabTasks() != null) && (variables.getTabTasks().size() != 0))
			{
			for(int j=0; j<variables.getTabTasks().get(variables.getTaskIndex()).length; j++)
				{
				findAndFill(variables.getTabTasks().get(variables.getTaskIndex())[j][0],variables.getTabTasks().get(variables.getTaskIndex())[j][1]);
				}
			scrollbar.setVisible(true);
			}
		else
			{
			scrollbar.setVisible(false);
			}
		this.repaint();
		this.validate();
		}
	
	/**
	 * Method used to find which item is looked for
	 * and fill it the right way.
	 */
	private void findAndFill(String str, String value)
		{
		try
			{
			if(str.equals("when"))
				{
				String[] tag = value.split(" ");
				whenCombo.setSelectedItem(tag[0]);
				
				if(tag.length > 1)
					{
					if(tag[1].contains(":"))
						{
						//DAILY
						String[] tagg = tag[1].split(":");
						hourCombo.setSelectedItem(tagg[0]);
						minuteCombo.setSelectedItem(tagg[1]);
						}
					else
						{
						//EVERY
						oneHourCombo.setSelectedItem(tag[1]);
						oneHourCombo.setVisible(true);
						hourCombo.setVisible(false);
						minuteCombo.setEnabled(false);
						}
					}
				else
					{
					hourCombo.setEnabled(false);
					minuteCombo.setEnabled(false);
					}
				}
			else if(str.equals("smartlinesearch"))
				{
				smartlinesearchBox.setSelected(value.equals("true")?true:false);
				}
			else if(str.equals("getmorethanprimaryline"))
				{
				getmorethanprimarylineBox.setSelected(value.equals("true")?true:false);
				}
			else if(str.equals("displayurlinreport"))
				{
				displayurlinreportBox.setSelected(value.equals("true")?true:false);
				}
			else if(str.equals("ackmode"))
				{
				ackmodeCombo.setSelectedItem(value);
				}
			else if(str.equals("csvreport"))
				{
				csvreportBox.setSelected(value.equals("true")?true:false);
				}
			else if(str.equals("testmode"))
				{
				testmodeBox.setSelected(value.equals("true")?true:false);
				}
			else if(str.equals("axlhost"))
				{
				axlhostText.setText(value);
				}
			else if(str.equals("axlusername"))
				{
				axlusernameText.setText(value);
				}
			else if(str.equals("axlpassword"))
				{
				//axlpasswordText.setText(value);
				}
			else if(str.equals("devicedescription"))
				{
				devicedescriptionText.setText(value);
				}
			else if(str.equals("devicedescriptiontoolong"))
				{
				devicedescriptiontoolongText.setText(value);
				}
			else if(str.equals("linedescription"))
				{
				linedescriptionText.setText(value);
				}
			else if(str.equals("linedescriptiontoolong"))
				{
				linedescriptiontoolongText.setText(value);
				}
			else if(str.equals("linealertingname"))
				{
				linealertingnameText.setText(value);
				}
			else if(str.equals("linealertingnametoolong"))
				{
				linealertingnametoolongText.setText(value);
				}
			else if(str.equals("linedisplay"))
				{
				linedisplayText.setText(value);
				}
			else if(str.equals("linedisplaytoolong"))
				{
				linedisplaytoolongText.setText(value);
				}
			else if(str.equals("linetextlabel"))
				{
				linetextlabelText.setText(value);
				}
			else if(str.equals("linetextlabeltoolong"))
				{
				linetextlabeltoolongText.setText(value);
				}
			else if(str.equals("lineexternalphonenumbermask"))
				{
				lineexternalphonenumbermaskText.setText(value);
				}
			else if(str.equals("maxnumchar"))
				{
				maxnumcharCombo.setSelectedItem(value);
				}
			else if(str.equals("replacefrenchchar"))
				{
				replacefrenchcharBox.setSelected(value.equals("true")?true:false);
				}
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("Error during configuration tab filling : "+exc.getMessage());
			}
		
		}
	
	/**
	 * Method used to enable/disable control bar
	 */
	public void enableControl(boolean b)
		{
		this.update.setEnabled(b);
		}
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

