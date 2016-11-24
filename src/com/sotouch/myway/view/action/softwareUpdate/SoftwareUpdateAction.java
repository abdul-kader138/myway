package com.sotouch.myway.view.action.softwareUpdate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.quickstart.service.config.ConfigParameterService;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.SoftwareUpdate;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.project.ProjectService;

/**
 * Class which manages resources (used for rights management)
 */
@Unsecured
public class SoftwareUpdateAction extends BaseCrudAction<SoftwareUpdateViewBean> {

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="projectService")
	private ProjectService projectService;
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		SoftwareUpdateViewBean softwareViewBean = (SoftwareUpdateViewBean) viewBean;
		criteriaMap.put("\\search", softwareViewBean.getSearch());
		criteriaMap.put("license.id", softwareViewBean.getLicenseId());
	}

	@Override
	protected String getDefaultSort() {
		return "updateTime";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		SoftwareUpdateViewBean softwareUpdateViewBean = (SoftwareUpdateViewBean) viewBean;
		SoftwareUpdate softwareUpdate = (SoftwareUpdate) dataBean;
		
		softwareUpdate.setPcName(softwareUpdateViewBean.getPcName());
		if(StringUtil.isEmpty(softwareUpdateViewBean.getIp())) {
			softwareUpdate.setIp(getIpAddr());
		}
		else {
			softwareUpdate.setIp(softwareUpdateViewBean.getIp());
		}
		
		softwareUpdate.setOldVersion(softwareUpdateViewBean.getOldVersion());
		softwareUpdate.setNewVersion(softwareUpdateViewBean.getNewVersion());
		
		softwareUpdate.setLogType(softwareUpdateViewBean.getLogType());
		softwareUpdate.setDescription(softwareUpdateViewBean.getDescription());
		softwareUpdate.setUpdateTime(new Date(System.currentTimeMillis()));
		
		Project project = projectService.findByKey(softwareUpdateViewBean.getProjectKey());
		if(project != null) {
			softwareUpdate.setProject(project);
		}
		
		softwareUpdate.setLicense(new License(softwareUpdateViewBean.getLicenseId()));
		
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		SoftwareUpdate softwareUpdate = (SoftwareUpdate) dataBean;
		SoftwareUpdateViewBean softwareUpdateViewBean = (SoftwareUpdateViewBean) viewBean;
		
		softwareUpdateViewBean.setPcName(softwareUpdate.getPcName());
		softwareUpdateViewBean.setIp(softwareUpdate.getIp());
		softwareUpdateViewBean.setOldVersion(softwareUpdate.getOldVersion());
		softwareUpdateViewBean.setNewVersion(softwareUpdate.getNewVersion());
		
		softwareUpdateViewBean.setLogType(softwareUpdate.getLogType());
		softwareUpdateViewBean.setDescription(softwareUpdate.getDescription());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		softwareUpdateViewBean.setUpdateTime(sdf.format(softwareUpdate.getUpdateTime()));
		
		softwareUpdateViewBean.setProjectKey(softwareUpdate.getProject().getKey());
		softwareUpdateViewBean.setLicenseId(softwareUpdate.getLicense().getId());
		
		softwareUpdateViewBean.setProjectName(softwareUpdate.getProject().getName());
		softwareUpdateViewBean.setLicenseName(getLicenseName(softwareUpdate.getLicense()));
	}
	
	
	public String getLicenseName(License license) {
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		for(LicenseContent licenseContent : license.getLicenseContents()) {
			if(licenseContent.getLanguage().getCode().equals(defaultLanguage.getCode())) {
				return licenseContent.getName();
			}
		}
		return null;
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	public String getIpAddr() { 
       String ip = request.getHeader("x-forwarded-for"); 
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
           ip = request.getHeader("Proxy-Client-IP"); 
       } 
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
           ip = request.getHeader("WL-Proxy-Client-IP"); 
       } 
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
           ip = request.getRemoteAddr(); 
       } 
       return ip; 
   } 
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}