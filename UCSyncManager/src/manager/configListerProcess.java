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
		}

	/*2013*//*RATEL Alexandre 8)*/
	}

