package schedule;

import java.io.Serializable;
import java.util.ArrayList;
import misc.simpleToDo;
import utils.variables.taskStatusType;
import utils.variables.taskType;

/**********************************
 * Class used to store Task data a
 * simplier way. Next it is easy to share
 * it throw an ObjectOutputStream
 * 
 * @author RATEL Alexandre
 **********************************/
public class simpleTask implements Serializable
	{
	/**
	 * Variables
	 */
	protected taskStatusType status;
	protected taskType type;
	protected String description;
	private ArrayList<simpleToDo> toDoList;
	protected final String id;
	protected int taskIndex;
	


	/***************
	 * Constructor
	 ***************/
	public simpleTask(taskStatusType status, taskType type, String description, ArrayList<simpleToDo> toDoList, String id, int taskIndex)
		{
		this.status = status;
		this.type = type;
		this.description = description;
		this.toDoList = toDoList;
		this.id = id;
		this.taskIndex = taskIndex;
		}



	public taskStatusType getStatus()
		{
		return status;
		}

	public void setStatus(taskStatusType status)
		{
		this.status = status;
		}

	public taskType getType()
		{
		return type;
		}

	public void setType(taskType type)
		{
		this.type = type;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public ArrayList<simpleToDo> getToDoList()
		{
		return toDoList;
		}

	public void setToDoList(ArrayList<simpleToDo> toDoList)
		{
		this.toDoList = toDoList;
		}

	public int getTaskIndex()
		{
		return taskIndex;
		}

	public void setTaskIndex(int taskIndex)
		{
		this.taskIndex = taskIndex;
		}

	public String getId()
		{
		return id;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

