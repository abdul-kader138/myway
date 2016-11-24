package org.hurryapp.quickstart.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONTokener;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;


public class CertificateUtils {
	JSONObject dataObj;
	public CertificateUtils() throws CertificateNotFound,CertificateInvalid {
		String certificatePath = null;
		try {
			ServletContext sc = (ServletContext)ActionContext.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		    certificatePath = sc.getRealPath("/WEB-INF/classes") + "/cert.dat";
		    String data = FileUtils.readFileToString(new File(certificatePath), "UTF-8");
		    dataObj = (JSONObject)new JSONTokener(ThreeDESCode.decryptThreeDESECB(data)).nextValue();
		    if(!dataObj.has("key") || !dataObj.has("deviceNumber")) throw new Exception("invalid certificate");
		} catch(IOException e) {
			throw new CertificateNotFound("certificate not found:" + certificatePath);
		} catch(Exception e) {
			throw new CertificateNotFound("invalid certificate");
		}
	}
	
	public String getUniqueKey() {
		try {
			return (String)dataObj.get("key");
		} catch(Exception e) {
			return "";
		}
	}
	
	public static String getServerUniqueKey() throws Exception {
		ShellUtils.CommandResult result = ShellUtils.execCommand("ifconfig eth0 | awk '/HWaddr/ {print $5}'", false, true);
		if(result.result != 0) throw new Exception("can not get unique key, please check if ech0 is well config" + result.errorMsg);
		
		return Md5Utils.getMD5String(new ByteArrayInputStream(result.successMsg.getBytes("UTF-8")));
	}
	
	public int getDeviceNumber() {
		try {
			String strNumber = (String)dataObj.get("deviceNumber");
			return Integer.parseInt(strNumber);
		} catch(Exception e) {
			return 0;
		}
	}
	
	public static class CertificateNotFound extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	    public CertificateNotFound() {
	    	super();
	    }

	    public CertificateNotFound(String message) {
	    	super(message);
	    }

	    public CertificateNotFound(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public CertificateNotFound(Throwable cause) {
	        super(cause);
	    }
		
	}
	
	public static class CertificateInvalid extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	    public CertificateInvalid() {
	    	super();
	    }

	    public CertificateInvalid(String message) {
	    	super(message);
	    }

	    public CertificateInvalid(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public CertificateInvalid(Throwable cause) {
	        super(cause);
	    }
		
	}
}
