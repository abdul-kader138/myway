package org.hurryapp.quickstart.view.action.software;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.data.model.Software;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.software.SoftwareService;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.utils.CertificateUtils;
import org.hurryapp.quickstart.Constants;

import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.IconGroup;
import com.sotouch.myway.service.icon.IconService;
import com.sotouch.myway.view.action.icon.IconViewBean;

/**
 * Class which manages resources (used for rights management)
 */
//@Unsecured
public class SoftwareAction extends BaseCrudAction<SoftwareViewBean> {

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		SoftwareViewBean softwareViewBean = (SoftwareViewBean) viewBean;
		criteriaMap.put("version", softwareViewBean.getVersion());
	}

	@Override
	protected String getDefaultSort() {
		return "version";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		SoftwareViewBean softwareViewBean = (SoftwareViewBean) viewBean;
		Software software = (Software) dataBean;
		software.setVendor(softwareViewBean.getVendor());
		software.setVersion(softwareViewBean.getVersion());
		software.setInstallPack(softwareViewBean.getInstallPack());
		if(softwareViewBean.getId() == null) {
			software.setUploadTime(new Date(System.currentTimeMillis()));
			software.setUser((Utilisateur) this.getSession().get(Constants.SESSION_KEY_USER));
		}
		software.setModifyTime(new Date(System.currentTimeMillis()));
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Software software = (Software) dataBean;
		SoftwareViewBean softwareViewBean = (SoftwareViewBean) viewBean;
		
		softwareViewBean.setVendor(software.getVendor());
		softwareViewBean.setVersion(software.getVersion());
		softwareViewBean.setInstallPack(software.getInstallPack());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		softwareViewBean.setUploadTime(sdf.format(software.getUploadTime()));
		softwareViewBean.setModifyTime(sdf.format(software.getModifyTime()));
		softwareViewBean.setUserName(software.getUser().getPrenom() + " " + software.getUser().getNom());
	}
	
	@Override
	public String save() throws Exception {
		try {
			SoftwareViewBean softwareViewBean = (SoftwareViewBean) viewBean;
			List errors = softwareViewBean.validate(this.resources);
	        if (!errors.isEmpty()) {
	            this.jsonResponse.addErrors(errors);
	        }
	        else {
				if(softwareViewBean.getId() == null) {
					String uploadPath = (String) configParameterService.findParameter(com.sotouch.myway.Constants.CONFIG_DOMAIN_PATHS, com.sotouch.myway.Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH) + "/uploads";
					String filePath = uploadPath+"/"+softwareViewBean.getInstallPackFileName();
					new File(filePath).delete();
					FileUtils.copyFile(new File(softwareViewBean.getInstallPack()), new File(filePath));
					softwareViewBean.setInstallPack(softwareViewBean.getInstallPackFileName());
				}
	
				super.execute();
	        }
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}
	
	@Override
	public String delete() throws Exception {
		String uploadPath = (String) configParameterService.findParameter(com.sotouch.myway.Constants.CONFIG_DOMAIN_PATHS, com.sotouch.myway.Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH) + "/uploads";
		String[] idsTab = getSelectedIds().split(";");
	    try {
	    	for (String element : idsTab) {
	    		Software software = (Software)this.entityService.findById(Integer.valueOf(element));
	    		
				String filePath = uploadPath+"/"+software.getInstallPack();
				new File(filePath).delete();
	    		
	    		this.entityService.deleteById(Integer.valueOf(element));  
	      	}
	    }
	    catch (Exception e) {
	    	checkDeleteException(e);
	    }

	    if (this.jsonResponse.getErrors().isEmpty()) {
	    	this.jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));
	    }
	    return "success-json";
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}