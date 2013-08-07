package schedule;

import java.util.ArrayList;
import java.util.Date;

import execute.modify;

import scan.inspection;
import utils.methodesUtiles;

import misc.toDo;
import misc.worker;

/**********************************
 * Class used to design and manage 
 * a task
 * 
 * @author RATEL Alexandre
 **********************************/
public class task
	{
	/**
	 * Variables
	 */
	public enum typeStatus{init,working,waitingAck,done,toDelete,error};
	protected typeStatus status;
	protected String description;
	protected int age;
	protected Date when;
	protected worker myWorker;
	protected ArrayList<toDo> toDoList;
	protected final String id;
	
	public task()
		{
		status = typeStatus.init;
		age = 0;
		id = methodesUtiles.getID();
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

	/**
	 * Method used to stop the task
	 */
	public void stopWorking()
		{
		myWorker.interrupt();
		}
	
	
	
	
	/**
	 * Getters and setters
	 */
	public typeStatus getStatus()
		{
		return status;
		}

	public void setStatus(typeStatus status)
		{
		this.status = status;
		}

	public String getDescription()
		{
		return description;
		}

	public void setDescription(String description)
		{
		this.description = description;
		}

	public int getAge()
		{
		return age;
		}

	public void setAge(int age)
		{
		this.age = age;
		}

	public Date getWhen()
		{
		return when;
		}

	public void setWhen(Date when)
		{
		this.when = when;
		}

	public worker getMyWorker()
		{
		return myWorker;
		}

	public void setMyWorker(worker myWorker)
		{
		this.myWorker = myWorker;
		}

	public ArrayList<toDo> getToDoList()
		{
		return toDoList;
		}

	public void setToDoList(ArrayList<toDo> toDoList)
		{
		this.toDoList = toDoList;
		}

	public String getId()
		{
		return id;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

