package com.sotouch.myway.view.action.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.json.JSONError;

import com.opensymphony.xwork2.inject.Inject;
import com.sotouch.myway.Constants;
import com.sotouch.myway.service.map.MapService;

import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.mail.MailService;
import org.hurryapp.quickstart.service.utilisateur.UtilisateurService;
import org.hurryapp.quickstart.utils.ThreeDESCode;
import org.hurryapp.quickstart.view.AccessUtil;
import org.hurryapp.quickstart.view.action.utilisateur.UtilisateurAction;

/**
 * Class which manages login
 */
@Unsecured
public class UpdateAction extends BaseAction<UpdateViewBean> {
	
	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;
	
	@Resource (name="mapService")
	private MapService mapService;
	
	private String key;
	private String date;
	private String struct;
	private String downFileName;
	private long downFileSize;
	private String terminalId;
	
	public void setKey(String key) {  
        this.key = key;
    }  
  
    public String getKey() {  
        return key;  
    } 
    
    public void setDate(String date) {  
        this.date = date;
    }  
  
    public String getDate() {  
        return date;  
    } 
    
    public void setStruct(String struct) {  
        this.struct = struct;
    }  
  
    public String getStruct() {  
        return (struct == null) ? "" : struct;
    } 
    
    public void setDownFileName(String downFileName) {
    	this.downFileName = downFileName;
    }
    
    public String getDownFileName() {
    	return this.downFileName;
    }
    
    public void setDownFileSize(long  downFileSize) {
    	this.downFileSize = downFileSize;
    }
    
    public String getTerminalId() {
    	return terminalId == null ? "all" : terminalId;
    }
    
    public void setTerminalId(String  terminalId) {
    	this.terminalId = terminalId;
    }
    
    public long getDownFileSize() {
    	return this.downFileSize;
    }
    
	public String keyValidate() throws Exception {
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		String pathname = projectExportPath+"/"+getKey();
		this.jsonResponse.setMessage(pathname);
		if(new File(pathname).exists()) {
			this.jsonResponse.setData("true");
		} 
		else {
			this.jsonResponse.setData("false");
		}
		
		return SUCCESS_JSON;
	}
	
	public String checkForceUpdate() throws Exception {
		//String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		//File projectFile = new File(projectExportPath+"/"+getKey() + "/project.xml");
		//this.jsonResponse.setMessage(projectFile.getAbsolutePath());
		this.jsonResponse.setData("false");
		
		String content = mapService.getProjectFile(getKey());
		if(content == null) return SUCCESS_JSON;

		Element force_update = null, project_date = null;
		
		try {
			Document document = DocumentHelper.parseText(content);
			Element root = document.getRootElement();
			force_update = root.element("force_update");
			project_date = root.element("creation_date");
		} catch(Exception e) {
		}
		
		if(force_update != null && project_date != null && force_update.getText().equals("true") 
				&& !project_date.getText().equals(getDate())) {
			this.jsonResponse.setData("true");
		}
		
		this.jsonResponse.setMessage(force_update.getText() + ":" + project_date.getText() + ";" + getDate());
		
		return SUCCESS_JSON;
	}
	
	public String getProjectZip() throws Exception {
		
		if(getKey() == null || getKey().equals("")) {
			this.jsonResponse.setData("key_id not set");
			return SUCCESS_JSON;
		}
		
		mapService.addDevicesToProject(getKey());
		
		setDownFileName("Wayfinder_Export-" + getTerminalId()/*new SimpleDateFormat( "y-M-d" ).format(new Date())*/ + ".mwp");
		
		//queryUrl( this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + "/map/publishProject?project_id=" + getKey());
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		File exportFile = new File(projectExportPath+"/" + getKey() + "/" + getDownFileName());
		
		if(exportFile.exists()) exportFile.delete();
		
		this.jsonResponse.setData(exportFile.getAbsolutePath());
		HashMap<String, String> ignoredFiles = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(getStruct());
			Element root = document.getRootElement();
			for(Element elt : (List<Element>)root.elements()) {
				String filePath = elt.attributeValue("path");
				String fileSize = elt.attributeValue("filesize");
				if(filePath == null && fileSize == null) {
					continue;
				}
				ignoredFiles.put(URLDecoder.decode(filePath, "UTF-8"), URLDecoder.decode(fileSize, "UTF-8"));	
			}
		} catch(Exception e) {
			//this.jsonResponse.setMessage("Exception jdom:" + e);
		}
		
		try {
			ZipFile zipFile = new ZipFile(exportFile);
			zipFiles(projectExportPath+"/" + getKey(), getKey(), ignoredFiles, ".*\\.mwp.*", zipFile);
			zipFiles(projectExportPath+"/../icons", "icons", ignoredFiles, "", zipFile);
		} catch(Exception e) {
			this.jsonResponse.setMessage("Exception zip4j:" + e);
		}
		
		if(!exportFile.exists()) {
			this.jsonResponse.setData("create project zip failed");
			return SUCCESS_JSON;
		}

		return SUCCESS;
	}
	
	private void zipFiles(String dirPath, String rootFolder, HashMap<String, String> ignoredFiles, String ignoredPattern, ZipFile zipFile) {
        if(rootFolder.endsWith("/")) rootFolder = rootFolder.substring(0, rootFolder.length() - 1);

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if(files == null) return;

        ArrayList<File> filesToAdd = new ArrayList<File>();

        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) {
                zipFiles(files[i].getAbsolutePath(), rootFolder + "/" + files[i].getName(), ignoredFiles, ignoredPattern, zipFile);
            } else { 
            	if(!files[i].getName().equals("project.xml")) {
            		String searchFile = (rootFolder + "/" + files[i].getName()).replaceAll(getKey() + "/", "");
                
            		if(files[i].getName().matches(ignoredPattern)) continue;
                	
            		if(ignoredFiles.containsKey(searchFile) && ignoredFiles.get(searchFile).equals(String.valueOf(files[i].length()))) continue;
            	}
                filesToAdd.add(files[i]);
            }
        }

        if(filesToAdd.isEmpty()) return;

        try {
            ZipParameters parameters = new ZipParameters();  
            parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);  
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);  
            parameters.setRootFolderInZip(rootFolder);

            zipFile.addFiles(filesToAdd, parameters);
        } catch(Exception e) {
            System.out.println("exception " + dirPath + ":::" + e);
        }
    }
	
	private int queryUrl(String sUrl) {
		try {
			URL url = new URL(sUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setInstanceFollowRedirects(false);
    	    httpConnection.setConnectTimeout(3000);
            httpConnection.setRequestMethod("HEAD");
            httpConnection.setDoInput(false);
            httpConnection.setDoOutput(false);
            httpConnection.setRequestProperty("Connection","close");
		    return httpConnection.getResponseCode();
        } catch(Exception e) {
            return 404;
        }
	}
	
	
	public InputStream getInputStream(){  
		InputStream is = null;
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		File exportFile = new File(projectExportPath+"/" + getKey() + "/" + getDownFileName());
		setDownFileSize(exportFile.length());
	    try {
	    	is = new FileInputStream(exportFile);
	    } catch(Exception e) {
	    }
	    return is;
	}  
	/*  
	*  downFileName的setter和getter方法  
	*/  

	@Override
	protected void doInit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doExecute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}
