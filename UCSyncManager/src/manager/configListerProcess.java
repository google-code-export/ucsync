package manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**********************************
 * Class used to manage event from
 * configLister
 * 
 * @author RATEL Alexandre
 **********************************/
public class configListerProcess implements ActionListener
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
			System.out.println("update");
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

	/*2013*//*RATEL Alexandre 8)*/
	}

