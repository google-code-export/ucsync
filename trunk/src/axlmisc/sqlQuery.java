package axlmisc;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import utils.SOAPGear;
import utils.variables;

/**********************************
 * Class used to execute SQL query throw AXL
 * 
 * @author RATEL Alexandre
 **********************************/
public class sqlQuery
	{
	
	public static SOAPBody execute(String req, SOAPGear sg, String AXLVersion) throws Exception
		{
		MessageFactory mf = MessageFactory.newInstance();
		SOAPFactory soapFactory = SOAPFactory.newInstance();
	    SOAPMessage soapMessage = mf.createMessage();
	    MimeHeaders headers = soapMessage.getMimeHeaders();
	    headers.addHeader("SOAPAction", " CUCM:DB ver="+AXLVersion+".0");
	    SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
	    SOAPBody bdy = envelope.getBody();
	    SOAPBodyElement bodyElement = bdy.addBodyElement(soapFactory.createName("executeSQLQuery","axl","http://www.cisco.com/AXL/API/"+AXLVersion+".0"));
	    bodyElement.addAttribute(envelope.createName("sequence"), String.valueOf(System.currentTimeMillis()));
	    
	    bodyElement.addChildElement("sql").addTextNode(req);
	    
		SOAPMessage soapAnswer = sg.execute(soapMessage);
		SOAPPart replySP = soapAnswer.getSOAPPart();
	    SOAPEnvelope replySE = replySP.getEnvelope();
	    SOAPBody replySB = replySE.getBody();
	    
	    if (replySB.hasFault())
	    	{
	    	throw new SOAPException(replySB.getFault().getFaultString());
	    	}
	    else
	    	{
	    	return replySB;
	    	}
		}
	
	/*2013*//*RATEL Alexandre 8)*/
	}

