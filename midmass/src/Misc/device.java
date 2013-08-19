package Misc;

import java.lang.reflect.Field;

import Utils.variables.infoDeviceType;
import Utils.variables.infoNetworkType;

/**********************************
 * Class used to store device data
 * 
 * @author RATEL Alexandre
 **********************************/
public class device
	{
	/**
	 * Variable
	 */
	private String ip,MAC,SN,DN,loadID,bootLoadID,versionID,modelNumber,time,date,userLocale,tftpServer1,ccm1,ccm2,ccm3,userLocaleVersion;
	
	
	public device(String ip)
		{
		this.ip = ip;
		System.out.println("Nouveau device : "+ip);
		}
	
	public String getData() throws IllegalArgumentException, IllegalAccessException
		{
		StringBuffer st = new StringBuffer("");
		Field[] tab = this.getClass().getDeclaredFields();
		for(int i=0; i<tab.length; i++)
			{
			st.append(((String)tab[i].get(this)).trim());
			if(i != tab.length-1)
				{
				st.append(",");
				}
			}
		return st.toString();
		}
	
	public void setInfo(infoDeviceType id, String data)
		{
		if(id.equals(infoDeviceType.MACAddress))setMAC(data);
		if(id.equals(infoDeviceType.serialNumber))setSN(data);
		if(id.equals(infoDeviceType.phoneDN))setDN(data);
		if(id.equals(infoDeviceType.appLoadID))setLoadID(data);
		if(id.equals(infoDeviceType.bootLoadID))setBootLoadID(data);
		if(id.equals(infoDeviceType.versionID))setVersionID(data);
		if(id.equals(infoDeviceType.modelNumber))setModelNumber(data);
		if(id.equals(infoDeviceType.time))setTime(data);
		if(id.equals(infoDeviceType.date))setDate(data);
		}
	
	public void setInfo(infoNetworkType nt, String data)
		{
		if(nt.equals(infoNetworkType.UserLocale))setUserLocale(data);
		if(nt.equals(infoNetworkType.TFTPServer1))setTftpServer1(data);
		if(nt.equals(infoNetworkType.CallManager1))setCcm1(data);
		if(nt.equals(infoNetworkType.CallManager2))setCcm2(data);
		if(nt.equals(infoNetworkType.CallManager3))setCcm3(data);
		if(nt.equals(infoNetworkType.UserLocaleVersion))setUserLocaleVersion(data);
		}


	public String getIp()
		{
		return ip;
		}


	public void setIp(String ip)
		{
		this.ip = ip;
		}


	public String getMAC()
		{
		return MAC;
		}


	public void setMAC(String mAC)
		{
		MAC = mAC;
		}


	public String getSN()
		{
		return SN;
		}


	public void setSN(String sN)
		{
		SN = sN;
		}


	public String getDN()
		{
		return DN;
		}


	public void setDN(String dN)
		{
		DN = dN;
		}


	public String getLoadID()
		{
		return loadID;
		}


	public void setLoadID(String loadID)
		{
		this.loadID = loadID;
		}


	public String getBootLoadID()
		{
		return bootLoadID;
		}


	public void setBootLoadID(String bootLoadID)
		{
		this.bootLoadID = bootLoadID;
		}


	public String getVersionID()
		{
		return versionID;
		}


	public void setVersionID(String versionID)
		{
		this.versionID = versionID;
		}


	public String getModelNumber()
		{
		return modelNumber;
		}


	public void setModelNumber(String modelNumber)
		{
		this.modelNumber = modelNumber;
		}


	public String getTime()
		{
		return time;
		}


	public void setTime(String time)
		{
		this.time = time;
		}


	public String getDate()
		{
		return date;
		}


	public void setDate(String date)
		{
		this.date = date;
		}


	public String getUserLocale()
		{
		return userLocale;
		}


	public void setUserLocale(String userLocale)
		{
		this.userLocale = userLocale;
		}


	public String getCcm1()
		{
		return ccm1;
		}


	public void setCcm1(String ccm1)
		{
		this.ccm1 = ccm1;
		}


	public String getCcm2()
		{
		return ccm2;
		}


	public void setCcm2(String ccm2)
		{
		this.ccm2 = ccm2;
		}


	public String getCcm3()
		{
		return ccm3;
		}

	public void setCcm3(String ccm3)
		{
		this.ccm3 = ccm3;
		}

	public String getUserLocaleVersion()
		{
		return userLocaleVersion;
		}

	public void setUserLocaleVersion(String userLocaleVersion)
		{
		this.userLocaleVersion = userLocaleVersion;
		}

	public String getTftpServer1()
		{
		return tftpServer1;
		}

	public void setTftpServer1(String tftpServer1)
		{
		this.tftpServer1 = tftpServer1;
		}
	
	
	
	
	
	/*2013*//*RATEL Alexandre 8)*/
	}

