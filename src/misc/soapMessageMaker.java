package misc;

import java.sql.PreparedStatement;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import scan.device;
import scan.line;
import schedule.userSync;
import schedule.userSync.deviceType;
import utils.methodesUtiles;
import utils.variables.patternType;

/**********************************
 * Class used to store static method
 * about soap message construction
 * 
 * @author RATEL Alexandre
 **********************************/
public class soapMessageMaker
	{
	/**
	 * Variables
	 */
	private SOAPMessage soapMessage;
	private SOAPEnvelope envelope;
	private SOAPBodyElement bodyElement;
	
	
	public soapMessageMaker()
		{
		}
	
	public SOAPMessage make(patternType type, String newData, userSync myUSync, device d) throws Exception
		{
		String cmdName;
		cmdName = d.getType().equals(deviceType.deviceprofile)?"updateDeviceProfile":"updatePhone";
		
		if(type.equals(patternType.devicedescription))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, cmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), d.getUUID());
			
		    mySOAPEleExt.addChildElement("description").addTextNode(newData);
			}
		
		return soapMessage;
		}
	
	public SOAPMessage make(patternType type, String newData, userSync myUSync, line l) throws Exception
		{
		String lineCmdName = "updateLine"; 
		String deviceCmdName = l.getMyDevice().getType().equals(deviceType.deviceprofile)?"updateDeviceProfile":"updatePhone";
		
		if(type.equals(patternType.linealertingname))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, lineCmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), l.getUUID());
			
		    mySOAPEleExt.addChildElement("alertingName").addTextNode(newData);
		    mySOAPEleExt.addChildElement("asciiAlertingName").addTextNode(newData);
			}
		else if(type.equals(patternType.linedescription))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, lineCmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), l.getUUID());
			
		    mySOAPEleExt.addChildElement("description").addTextNode(newData);
			}
		else if(type.equals(patternType.linedisplay))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, deviceCmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), l.getPhoneUUID());
		    
		    SOAPElement mySOAPEleExte = mySOAPEleExt.addChildElement("lines");
		    SOAPElement mySOAPEleExten = mySOAPEleExte.addChildElement("line");
		    mySOAPEleExten.addChildElement("dirn").addAttribute(envelope.createName("uuid"), l.getUUID());
		    mySOAPEleExten.addChildElement("index").addTextNode(Integer.toString(l.getIndex()));
		    mySOAPEleExten.addChildElement("display").addTextNode(newData);
		    mySOAPEleExten.addChildElement("displayAscii").addTextNode(newData);
			}
		else if(type.equals(patternType.linetextlabel))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, deviceCmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), l.getPhoneUUID());
		    
		    SOAPElement mySOAPEleExte = mySOAPEleExt.addChildElement("lines");
		    SOAPElement mySOAPEleExten = mySOAPEleExte.addChildElement("line");
		    mySOAPEleExten.addChildElement("dirn").addAttribute(envelope.createName("uuid"), l.getUUID());
		    mySOAPEleExten.addChildElement("index").addTextNode(Integer.toString(l.getIndex()));
		    mySOAPEleExten.addChildElement("label").addTextNode(newData);
		    mySOAPEleExten.addChildElement("asciiLabel").addTextNode(newData);
			}
		else if(type.equals(patternType.lineexternalphonenumbermask))
			{
		    SOAPElement mySOAPEleExt = getPreparedHeader(soapMessage, deviceCmdName, methodesUtiles.getTargetTask("axlversion", myUSync.getTaskIndex()), l.getPhoneUUID());
		    
		    SOAPElement mySOAPEleExte = mySOAPEleExt.addChildElement("lines");
		    SOAPElement mySOAPEleExten = mySOAPEleExte.addChildElement("line");
		    mySOAPEleExten.addChildElement("dirn").addAttribute(envelope.createName("uuid"), l.getUUID());
		    mySOAPEleExten.addChildElement("index").addTextNode(Integer.toString(l.getIndex()));
		    mySOAPEleExten.addChildElement("e164Mask").addTextNode(newData);
			}
		
		return soapMessage;
		}
	
	/**************************************************
	 * Method used to prepare AXL Body
	 **************************************************/
	private void prepareAXLBody(String versionCUCM, String cmdName) throws Exception
		{
		soapMessage = null;
		bodyElement = null;
		envelope = null;
		MessageFactory mf = MessageFactory.newInstance();
		SOAPFactory soapFactory = SOAPFactory.newInstance();
		
	    soapMessage = mf.createMessage();
	    MimeHeaders headers = soapMessage.getMimeHeaders();
	    headers.addHeader("SOAPAction", "CUCM:DB ver="+versionCUCM+".0");
	    envelope = soapMessage.getSOAPPart().getEnvelope();
	    SOAPBody bdy = envelope.getBody();
	    bodyElement = bdy.addBodyElement(soapFactory.createName(cmdName,"axl","http://www.cisco.com/AXL/API/"+versionCUCM+".0"));
	    bodyElement.addAttribute(envelope.createName("sequence"), String.valueOf(System.currentTimeMillis()));
		}
	
	private SOAPElement getPreparedHeader(SOAPMessage soapMessage, String cmdName, String axlVersion, String UUID) throws Exception
		{
		prepareAXLBody(axlVersion, cmdName);
	    SOAPBody bdy = envelope.getBody();
	    Iterator iterator = bdy.getChildElements();
	    SOAPElement mySOAPEleExt = (SOAPElement)iterator.next();
	    
	    mySOAPEleExt.addChildElement("uuid").addTextNode(UUID);
	    return mySOAPEleExt;
		}
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

