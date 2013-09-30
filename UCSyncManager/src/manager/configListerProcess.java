package manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import utils.methodesUtiles;
import utils.variables;
import utils.variables.dataTypes;

/**********************************
 * Class used to manage event from
 * configLister
 * 
 * @author RATEL Alexandre
 **********************************/
public class configListerProcess implements ActionListener, MouseListener
	{
	/**
	 * Variables
	 */
	configLister myLister;
	
	
	
	public configListerProcess(configLister myLister)
		{
		this.myLister = myLister;
		}


	@Override
	public void actionPerformed(ActionEvent evt)
		{
		if(evt.getSource() == myLister.update)
			{
			//Get the new configuration value and then update the tabTask
			
			try
				{
				//when
				String value = new String("");
				if(((String)myLister.whenCombo.getSelectedItem()).equals("CONTINUOUS"))
					{
					value = myLister.whenCombo.getSelectedItem().toString();
					}
				else if(((String)myLister.whenCombo.getSelectedItem()).contains("DAILY"))
					{
					value = myLister.whenCombo.getSelectedItem().toString()+" "+myLister.hourCombo.getSelectedItem().toString()+":"+myLister.minuteCombo.getSelectedItem().toString();
					}
				else if(((String)myLister.whenCombo.getSelectedItem()).contains("EVERY"))
					{
					value = myLister.whenCombo.getSelectedItem().toString()+" "+myLister.oneHourCombo.getSelectedItem().toString();
					}
				methodesUtiles.setTargetTask("when", variables.getTaskIndex(), value);
				
				//smartlinesearch
				methodesUtiles.setTargetTask("smartlinesearch", variables.getTaskIndex(), myLister.smartlinesearchBox.isSelected()?"true":"false");
				
				//getmorethanprimaryline
				methodesUtiles.setTargetTask("getmorethanprimaryline", variables.getTaskIndex(), myLister.getmorethanprimarylineBox.isSelected()?"true":"false");
				
				//ackmode
				methodesUtiles.setTargetTask("ackmode", variables.getTaskIndex(), myLister.ackmodeCombo.getSelectedItem().toString());
				
				//csvreport
				methodesUtiles.setTargetTask("csvreport", variables.getTaskIndex(), myLister.csvreportBox.isSelected()?"true":"false");
				
				//testmode
				methodesUtiles.setTargetTask("testmode", variables.getTaskIndex(), myLister.testmodeBox.isSelected()?"true":"false");
				
				//axlhost
				methodesUtiles.setTargetTask("axlhost", variables.getTaskIndex(), myLister.axlhostText.getText());
				
				//axlusername
				methodesUtiles.setTargetTask("axlusername", variables.getTaskIndex(), myLister.axlusernameText.getText());
				
				//axlpassword
				if(!myLister.axlpasswordText.getText().equals(""))methodesUtiles.setTargetTask("axlpassword", variables.getTaskIndex(), myLister.axlpasswordText.getText());
				
				//devicedescription
				methodesUtiles.setTargetTask("devicedescription", variables.getTaskIndex(), myLister.devicedescriptionText.getText());
				methodesUtiles.setTargetTask("devicedescriptiontoolong", variables.getTaskIndex(), myLister.devicedescriptiontoolongText.getText());
				
				//linedescription
				methodesUtiles.setTargetTask("linedescription", variables.getTaskIndex(), myLister.linedescriptionText.getText());
				methodesUtiles.setTargetTask("linedescriptiontoolong", variables.getTaskIndex(), myLister.linedescriptiontoolongText.getText());
				
				//linealertingname
				methodesUtiles.setTargetTask("linealertingname", variables.getTaskIndex(), myLister.linealertingnameText.getText());
				methodesUtiles.setTargetTask("linealertingnametoolong", variables.getTaskIndex(), myLister.linealertingnametoolongText.getText());
				
				//linedisplay
				methodesUtiles.setTargetTask("linedisplay", variables.getTaskIndex(), myLister.linedisplayText.getText());
				methodesUtiles.setTargetTask("linedisplaytoolong", variables.getTaskIndex(), myLister.linedisplaytoolongText.getText());
				
				//linetextlabel
				methodesUtiles.setTargetTask("linetextlabel", variables.getTaskIndex(), myLister.linetextlabelText.getText());
				methodesUtiles.setTargetTask("linetextlabeltoolong", variables.getTaskIndex(), myLister.linetextlabeltoolongText.getText());
				
				//lineexternalphonenumbermask
				methodesUtiles.setTargetTask("lineexternalphonenumbermask", variables.getTaskIndex(), myLister.lineexternalphonenumbermaskText.getText());
				
				//maxnumchar
				methodesUtiles.setTargetTask("maxnumchar", variables.getTaskIndex(), myLister.maxnumcharCombo.getSelectedItem().toString());
				
				//replacefrenchchar
				methodesUtiles.setTargetTask("replacefrenchchar", variables.getTaskIndex(), myLister.replacefrenchcharBox.isSelected()?"true":"false");
				
				//displayurlinreport
				methodesUtiles.setTargetTask("displayurlinreport", variables.getTaskIndex(), myLister.displayurlinreportBox.isSelected()?"true":"false");
				
				//Send new configuration values to the server 
				methodesUtiles.updateTaskConfig();
				}
			catch (Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				}
			
			
			}
		if(evt.getSource() == myLister.whenCombo)
			{
			if(((String)myLister.whenCombo.getSelectedItem()).equals("CONTINUOUS"))
				{
				myLister.hourCombo.setEnabled(false);
				myLister.minuteCombo.setEnabled(false);
				myLister.oneHourCombo.setEnabled(false);
				myLister.hourCombo.setVisible(true);
				myLister.oneHourCombo.setVisible(false);
				myLister.minuteCombo.setVisible(true);
				}
			else if(((String)myLister.whenCombo.getSelectedItem()).contains("DAILY"))
				{
				myLister.hourCombo.setEnabled(true);
				myLister.minuteCombo.setEnabled(true);
				myLister.oneHourCombo.setEnabled(false);
				myLister.hourCombo.setVisible(true);
				myLister.oneHourCombo.setVisible(false);
				myLister.minuteCombo.setVisible(true);
				}
			else if(((String)myLister.whenCombo.getSelectedItem()).contains("EVERY"))
				{
				myLister.hourCombo.setEnabled(false);
				myLister.minuteCombo.setEnabled(false);
				myLister.oneHourCombo.setEnabled(true);
				myLister.hourCombo.setVisible(false);
				myLister.oneHourCombo.setVisible(true);
				myLister.minuteCombo.setVisible(false);
				}
			}
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evt)
		{
		if((evt.getSource() == myLister.devicedescriptionText) || (evt.getSource() == myLister.devicedescriptiontoolongText))
			{
			new patternEditor((JTextField)evt.getSource(), dataTypes.device);
			}
		if((evt.getSource() == myLister.linedescriptionText) 
				|| (evt.getSource() == myLister.linedescriptiontoolongText)
				|| (evt.getSource() == myLister.linealertingnameText)
				|| (evt.getSource() == myLister.linealertingnametoolongText)
				|| (evt.getSource() == myLister.linedisplayText)
				|| (evt.getSource() == myLister.linedisplaytoolongText)
				|| (evt.getSource() == myLister.linetextlabelText)
				|| (evt.getSource() == myLister.linetextlabeltoolongText)
				|| (evt.getSource() == myLister.lineexternalphonenumbermaskText))
			{
			new patternEditor((JTextField)evt.getSource(), dataTypes.line);
			}
		}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
		{
		// TODO Auto-generated method stub
		
		}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
		{
		// TODO Auto-generated method stub
		
		}

	/*2013*//*RATEL Alexandre 8)*/
	}

