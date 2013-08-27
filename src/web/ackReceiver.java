package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import schedule.task.taskStatusType;
import utils.testeur;
import utils.variables;

/**********************************
 * Class used to manage Web request
 * 
 * @author RATEL Alexandre
 **********************************/
public class ackReceiver extends Thread
	{
	/**
	 * Variables
	 */
	private Socket myS;
	private String ID;
	
	
	public ackReceiver(Socket myS)
		{
		this.myS = myS;
		}
	
	
	public void run()
		{
		
		try
			{
			/*******
			 * Step 1 : get ID from ack received
			 */
			getID();
			/***************/
			
			/*******
			 * Step 2 : Activate task waiting for ack
			 */
			taskActivation();
			/***************/
			
			/*******
			 * Step 3 : Display response web page
			 */
			displaySuccessPage();
			/***************/
			}
		catch (Exception exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("ERROR : "+exc.getMessage());
			
			//Display error page
			displayErrorPage();
			}
		finally
			{
			try
				{
				myS.close();
				}
			catch (IOException exc)
				{
				exc.printStackTrace();
				variables.getLogger().error(exc);
				}
			}
		}
	
	/**
	 * Method used to activate Task
	 */
	private boolean taskActivation() throws Exception
		{
		for(int i=0;i<variables.getTaskList().size(); i++)
			{
			if(variables.getTaskList().get(i).getId().compareTo(ID) == 0)
				{
				variables.getTaskList().get(i).setStatus(taskStatusType.pending);
				variables.getLogger().info(variables.getTaskList().get(i).getTInfo()+"is now pending");
				return true;
				}
			}
		throw new Exception();
		}
	
	/**
	 * Method used to get task ID from the url
	 */
	private void getID() throws Exception
		{
		//I only need the first line of the http ack
		BufferedReader in = new BufferedReader(new InputStreamReader(myS.getInputStream()));
		String url = in.readLine();
		String[] tab = url.split(" ");
		String id = tab[1];
		id = id.replace("/", "");
		
		if(testeur.isMD5(id))
			{
			ID = id;
			variables.getLogger().debug("ID : "+ID+" got");
			}
		}
	
	/**
	 * Method used to display success web page
	 */
	private void displaySuccessPage()
		{
		try
			{
			StringBuffer page = new StringBuffer("");
			PrintWriter out = new PrintWriter(myS.getOutputStream());
			
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html; charset=UTF-8");
			page.append("<html>" +
					"<head>" +
					"<title>" +
					"Task has been activated with success" +
					"</title>" +
					"</head>" +
					"<body>" +
					"<h1>Task </h1>\""+ID+"\"<h1> has been activated with success</h1>" +
					"</body>" +
					"</html>");
			
			out.println("Content-Length: "+page.toString().length()+"\r\n");
			out.println(page.toString());
			out.flush();
			out.close();
			
			variables.getLogger().debug("Web response sent :"+page.toString());
			}
		catch (IOException exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("ERROR : unable to display response succes web page : "+exc.getMessage());
			}
		}
	
	/**
	 * Method used to display error web page
	 */
	private void displayErrorPage()
		{
		try
			{
			StringBuffer page = new StringBuffer("");
			PrintWriter out = new PrintWriter(myS.getOutputStream());
			
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html; charset=UTF-8");
			page.append("<html>" +
					"<head>" +
					"<title>" +
					"Task not found" +
					"</title>" +
					"</head>" +
					"<body>" +
					"<h1>Sorry, but this task hasn't been found</h1>" +
					"</body>" +
					"</html>");
			
			out.println("Content-Length: "+page.toString().length()+"\r\n");
			out.println(page.toString());
			out.flush();
			out.close();
			
			variables.getLogger().debug("Web response sent :"+page.toString());
			}
		catch (IOException exc)
			{
			exc.printStackTrace();
			variables.getLogger().error(exc);
			variables.getLogger().error("ERROR : unable to display response error web page : "+exc.getMessage());
			}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

