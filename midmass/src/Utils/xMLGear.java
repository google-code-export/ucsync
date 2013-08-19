package Utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;



/*************************************************************
 * Class ayant pour but d'exploiter facilement une source XML
 * tout en étant grandement réutilisable
 * 
 * Elle reçoit en argument la source (File ou String) et une
 * liste contenant les paramètres XML à rechercher
 *************************************************************/

/**********
 * Checked
 **********/

public class xMLGear
	{
	/************
	 * Variables
	 ************/
	private static ArrayList<String> listParams;
	private static ArrayList<String> listResult;
	private static ArrayList<String[]> listTabResult;
	private static Element root;
	
	public synchronized static ArrayList<String> getResultList(File fichierXML, ArrayList<String> listParams) throws Exception
		{
		root = null;
		listResult = null;
		listResult = new ArrayList<String>();
		
		try
			{
			root = parseXMLFile(fichierXML);
			exploreLayerElement(root,listParams,listParams.size());
			}
		catch (Exception e)
			{
			throw new Exception();
			}
		return listResult;
		}
	
	public synchronized static ArrayList<String> getResultList(String sourceXML, ArrayList<String> listParams) throws Exception
		{
		root = null;
		listResult = null;
		listResult = new ArrayList<String>();
		
		try
			{
			root = parseXMLString(sourceXML);
			exploreLayerElement(root,listParams,listParams.size());
			}
		catch (Exception e)
			{
			throw new Exception();
			}
		return listResult;
		}
	
	public synchronized static ArrayList<String[]> getResultListTab(File fichierXML, ArrayList<String> listParams) throws Exception
		{
		root = null;
		listTabResult = null;
		listTabResult = new ArrayList<String[]>();
		
		try
			{
			root = parseXMLFile(fichierXML);
			exploreLayerElementTab(root,listParams,listParams.size());
			}
		catch (Exception e)
			{
			throw new Exception();
			}
		return listTabResult;
		}
	
	public synchronized static ArrayList<String[]> getResultListTab(String sourceXML, ArrayList<String> listParams) throws Exception
		{
		root = null;
		listTabResult = null;
		listTabResult = new ArrayList<String[]>();
		
		try
			{
			root = parseXMLString(sourceXML);
			exploreLayerElementTab(root,listParams,listParams.size());
			}
		catch (Exception e)
			{
			throw new Exception();
			}
		return listTabResult;
		}
	
	private static void exploreLayerElementTab(Element layer, ArrayList<String> node, int index)
		{
		Iterator i = layer.getChildren(node.get(node.size()-index)).iterator();
		while(i.hasNext())
			{
			Element courant = (Element)i.next();
			if(index<=2)
				{
				List PhoneList = courant.getChildren();
				Iterator j = PhoneList.iterator();
				
				int a = 0;
				//On créer un tableau de la bonne taille
				String[] tabArgs = new String[PhoneList.size()];
				//On remplie le tableau
				while(j.hasNext())
					{
					Element courant2 = (Element)j.next();
					tabArgs[a] = (courant2.getText());
					a++;
					}
				listTabResult.add(tabArgs);
				}
			else
				{
				exploreLayerElementTab(courant, node, --index);
				}
			}
		index++;
		}
	
	private static void exploreLayerElement(Element layer, ArrayList<String> node, int index) throws Exception
		{
		Iterator i = layer.getChildren(node.get(node.size()-index)).iterator();
		while(i.hasNext())
			{
			Element courant = (Element)i.next();
			if(index<=2)
				{
				listResult.add(courant.getChild(node.get(node.size()-1)).getText());
				}
			else
				{
				exploreLayerElement(courant, node, --index);
				}
			}
		index++;
		}
	
	
	private static Element parseXMLFile(File FichierXML) throws JDOMException, IOException
		{
		SAXBuilder sxb = new SAXBuilder();
		return sxb.build(FichierXML).getRootElement();
		}
	
	private static Element parseXMLString(String sourceXML) throws JDOMException, IOException
		{
		SAXBuilder sxb = new SAXBuilder();
		return sxb.build(new InputSource(new StringReader(sourceXML))).getRootElement();
		}
	
	/*Fin Classe*//*AR :)*/
	}
