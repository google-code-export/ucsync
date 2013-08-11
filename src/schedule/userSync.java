package schedule;

import java.util.ArrayList;

import scan.inspection;
import utils.variables;
import execute.modify;

import misc.toDo;

/**********************************
 * Class used to manage userSync Process
 * 
 * @author RATEL Alexandre
 **********************************/
public class userSync extends task
	{
	/***
	 * Variables
	 */
	private ArrayList<toDo> toDoList;
	
	
	
	public userSync(int taskIndex) throws Exception
		{
		super(taskIndex,taskType.userSync);
		}
	
	/**
	 * Method used to fill the toDo list
	 */
	public void fillToDoList()
		{
		myWorker = new inspection(this);	
		}
	
	/**
	 * Method used to execute the toDo list
	 */
	public void executeToDoList()
		{
		myWorker = new modify(this);	
		}
	
	
	public ArrayList<toDo> getToDoList()
		{
		return toDoList;
		}

	public void setToDoList(ArrayList<toDo> toDoList)
		{
		this.toDoList = toDoList;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

