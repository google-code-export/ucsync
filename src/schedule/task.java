package schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import execute.modify;

import scan.inspection;
import utils.methodesUtiles;
import utils.variables;

import misc.toDo;
import misc.worker;

/**********************************
 * Class used to design and manage 
 * a task
 * 
 * @author RATEL Alexandre
 **********************************/
public abstract class task
	{
	/**
	 * Variables
	 */
	public enum taskStatusType{init,working,waitingAck,pending,done,toDelete,error};
	public enum taskType{userSync};
	protected taskStatusType status;
	protected taskType type;
	protected String description;
	protected int age;
	protected String when;
	protected worker myWorker;
	protected final String id;
	protected int taskIndex;
	protected long startTime;
	protected Date lastLaunchedTime;
	
	public task(int taskIndex, taskType type) throws Exception
		{
		this.taskIndex = taskIndex;
		this.type = type;
		status = taskStatusType.init;
		age = 0;
		id = methodesUtiles.getID();
		when = methodesUtiles.getTargetTask("when",taskIndex);
		description = methodesUtiles.getTargetTask("description",taskIndex);
		startTime = System.currentTimeMillis();
		}

	/**
	 * Method used to stop the task
	 */
	public void stopWorking()
		{
		myWorker.interrupt();
		}
	
	public boolean isItLaunchedTime()
		{
		String[] dateTag = when.split(" ");
		if(dateTag.length >= 2)
			{
			variables.getLogger().error("Task config file is corrupted, so it is not possible to know the correct launch time for the "+getTInfo());
			variables.getLogger().error("isItLaunchedTime, return false");
			return false;
			}
		
		if((when.compareTo("CONTINUOUS") == 0)||(when.compareTo("") == 0))
			{
			return true;
			}
		else if(dateTag[0].compareTo("DAILY") == 0)
			{
			try
				{
				/**
				 * Here we have to launch task once a day
				 */
				//get the current day
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
				String currentDate = dateFormat.format(now); 
				//Add it to the task file hour
				String dateString = currentDate+" "+dateTag[1];
				
				//Then convert it to a Date object
				DateFormat df = DateFormat.getDateInstance();
				Date taskDate = df.parse(dateString);
				
				//We have to check if the last launched time was the same day
				SimpleDateFormat format = new SimpleDateFormat("dd");
				if(format.format(now).compareTo(format.format(lastLaunchedTime)) == 0)
					{
					return false;
					}
				
				//We now have to find if it's time to launch the task
				if(now.after(taskDate))
					{
					return true;
					}
				else
					{
					return false;
					}
				}
			catch (ParseException exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				variables.getLogger().error("It was not possible to find the correct hour for a DAILY "+getTInfo());
				variables.getLogger().error("isItLaunchedTime, return false");
				return false;
				}
			}
		else
			{
			//By default, we don't launch the task
			return false;
			}
		}
	
	/**
	 * Method used to know if the task
	 * is expired and therefore, if it should
	 * be deleted
	 */
	public boolean isExpired() throws Exception
		{
		long currentTime = System.currentTimeMillis();
		long maxTime = (long)(Integer.parseInt(methodesUtiles.getTargetOption("maxtasktime"))*60*1000);//in milliseconds
		
		if((currentTime - startTime)>= maxTime)
			{
			variables.getLogger().info(getTInfo()+"has expired");
			return true;
			}
		return false;
		}
	
	/**
	 * Method used to init start time
	 */
	public void initStartTime()
		{
		startTime = System.currentTimeMillis();
		lastLaunchedTime = new Date();
		variables.getLogger().debug("task ");
		}
	
	/**
	 * Method used get task information
	 * used to write logs 
	 */
	public String getTInfo()
		{
		return "Task "+this.taskIndex+" ID:"+this.id+" ";
		}
	
	/**
	 * Getters and setters
	 */
	public taskStatusType getStatus()
		{
		return status;
		}

	public void setStatus(taskStatusType status)
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

	public String getWhen()
		{
		return when;
		}

	public void setWhen(String when)
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

	public String getId()
		{
		return id;
		}

	public int getTaskIndex()
		{
		return taskIndex;
		}

	public void setTaskIndex(int taskIndex)
		{
		this.taskIndex = taskIndex;
		}

	public taskType getType()
		{
		return type;
		}

	public void setType(taskType type)
		{
		this.type = type;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

