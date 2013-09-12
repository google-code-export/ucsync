package schedule;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import utils.methodesUtiles;
import utils.variables;
import utils.variables.taskStatusType;
import utils.variables.taskType;
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
	protected taskStatusType status;
	protected taskType type;
	protected String description;
	private ArrayList<toDo> toDoList;
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
		toDoList = new ArrayList<toDo>();
		age = 0;
		id = methodesUtiles.getID();
		when = methodesUtiles.getTargetTask("when",taskIndex);
		description = methodesUtiles.getTargetTask("description",taskIndex);
		startTime = System.currentTimeMillis();
		lastLaunchedTime = null;
		variables.setReadyToPublish(false);
		}

	/**
	 * Method used to stop the task
	 */
	public void stopWorking()
		{
		myWorker.interrupt();
		}
	
	/**
	 * Method used to know if task has to be launch
	 */
	public boolean isItLaunchedTime()
		{
		String[] dateTag = when.split(" ");
		if(dateTag.length > 2)
			{
			variables.getLogger().error("Task config file is corrupted, so it is not possible to know the correct launch time for the "+getTInfo());
			variables.getLogger().error("isItLaunchedTime, return false");
			return false;
			}
		
		if((when.equals("CONTINUOUS"))||(when.equals("")))
			{
			return true;
			}
		else if(dateTag[0].equals("DAILY"))
			{
			try
				{
				//get the current hour
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String currentHour = dateFormat.format(now); 
				
				//We now have to find if it's time to launch the task using minute precision
				if(currentHour.equals(dateTag[1]))
					{
					return true;
					}
				else
					{
					return false;
					}
				}
			catch(Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				variables.getLogger().error("It was not possible to find the correct hour for a DAILY schedule "+getTInfo());
				variables.getLogger().error("isItLaunchedTime, return false");
				return false;
				}
			}
		else if(dateTag[0].equals("EVERY"))
			{
			try
				{
				//get the current hour
				Date now = new Date();
				long currentHour = now.getTime(); 
				long lastLaunchedHour = lastLaunchedTime.getTime();
				
				long diff = currentHour - lastLaunchedHour;
				
				if((diff/(1000*60*60))>=Integer.parseInt(dateTag[1]))
					{
					lastLaunchedTime = now;
					return true;
					}
				else
					{
					return false;
					}
				}
			catch(Exception exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				variables.getLogger().error("It was not possible to find the correct hour for a DAILY schedule "+getTInfo());
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
	 * 
	 * - Définir un max à une journée 1440
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

