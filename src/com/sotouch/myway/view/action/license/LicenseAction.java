package com.sotouch.myway.view.action.license;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.view.ActionUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.hurryapp.quickstart.view.action.software.SoftwareViewBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.view.action.category.CategoryJSONAdapter;
import com.sotouch.myway.view.action.category.CategoryTranslateJSONAdapter;
import com.sotouch.myway.view.action.item.ItemContentViewBean;

/**
 * Class which manages the licenses
 */

public class LicenseAction extends BaseCrudAction<LicenseViewBean> {
	
	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@Resource (name="translationService")
	private TranslationService translationService;
	
	@Override
	protected void doInit() throws Exception {
		session.put("buyLicenseUrl", (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_BUY_LICENSE_URL));
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		languages = languageService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		LicenseViewBean licenseViewBean = (LicenseViewBean) viewBean;
		criteriaMap.put("key", licenseViewBean.getKey());
		criteriaMap.put("project.id", licenseViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "key";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		LicenseViewBean licenseViewBean = (LicenseViewBean) viewBean;
		License license = (License) dataBean;
		license.setKey(licenseViewBean.getKey());
		license.setDescription(licenseViewBean.getDescription());
		license.setOrientation(licenseViewBean.getOrientation());
		if(licenseViewBean.getLogoFileName() != null && licenseViewBean.getLogo() != null ) {
			license.setLogo(licenseViewBean.getLogoFileName());
		}
		
		if (licenseViewBean.getProjectId() != null) {
			license.setProject(new Project(Integer.valueOf(licenseViewBean.getProjectId())));
		}
		if (licenseViewBean.getItemId() != null) {
			license.setItem(new Item(Integer.valueOf(licenseViewBean.getItemId())));
		}
		
		// Get default language
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));

		// Item contents
		List<LicenseContent> licenseContents = new ArrayList<LicenseContent>();
		LicenseContent licenseContent = null;
		
		int i = 0;
		for (LicenseContentViewBean licenseContentViewBean : licenseViewBean.getLicenseContents()) {
			licenseContent = new LicenseContent();
			licenseContent.setLicense(license);
			
			if (licenseContentViewBean != null) {
				licenseContent.setId(licenseContentViewBean.getId());
				licenseContent.setLanguage(new Language(licenseContentViewBean.getLanguageId()));
				licenseContent.setName(licenseContentViewBean.getName());
				licenseContent.setDescription(licenseContentViewBean.getDescription());
				
				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(licenseContentViewBean.getLanguageCode()))) {
					license.setKey(licenseContentViewBean.getName());
					license.setDescription(licenseContentViewBean.getDescription());
				}
			}
			licenseContent.setIndex(i++);
			licenseContents.add(licenseContent);
		}
		license.setLicenseContents(licenseContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		License license = (License) dataBean;
		LicenseViewBean licenseViewBean = (LicenseViewBean) viewBean;
		
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		LicenseContent licenseContent = getLicenseContent(license.getLicenseContents(), defaultLanguage.getId());
		if(licenseContent != null) {
			licenseViewBean.setKey(licenseContent.getName());
			licenseViewBean.setDescription(licenseContent.getDescription());
		}
		else {
			licenseViewBean.setKey(license.getKey());
			licenseViewBean.setDescription(license.getDescription());
		}
		
		licenseViewBean.setLogo(license.getLogo());
		licenseViewBean.setOrientation(license.getOrientation());
		licenseViewBean.setProjectId(license.getProject() != null ? license.getProject().getId() : null);
		licenseViewBean.setItemId(license.getItem() != null ? license.getItem().getId() : null);
		
		if(license.getLogo() == null || license.getLogo().equals("")) {
			licenseViewBean.setVisual(Constants.DIR_ICONS + "/18/terminal_icon.png");
		}
		else {
			String uploadPath = Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
			String dir = uploadPath + "/" + Constants.DIR_LICENSES + "/" + license.getId();
			String filePath = dir + "/" + license.getLogo();
			
			licenseViewBean.setVisual(filePath);
		}
	}
	
	private LicenseContent getLicenseContent(List<LicenseContent> licenseContents, Integer languageId) {
    	for (LicenseContent licenseContent : licenseContents) {
			if (licenseContent != null && licenseContent.getLanguage().getId().equals(languageId)) {
				return licenseContent;
			}
		}
    	return null;
    }
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new LicenseJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	@Override
	public String save() throws Exception {
		try {
			LicenseViewBean licenseViewBean = (LicenseViewBean) viewBean;
			
			List errors = licenseViewBean.validate(this.resources);
	        if (!errors.isEmpty()) {
	            this.jsonResponse.addErrors(errors);
	        }
	        else {
				//String tmpFile = licenseViewBean.getLogo();
				//licenseViewBean.setLogo(licenseViewBean.getLogoFileName());
				super.execute();
				
				if (jsonResponse.getErrors().size() < 1 && licenseViewBean.getLogoFileName() != null && licenseViewBean.getLogo() != null ) {
			       
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					String dir = uploadPath + "/" + Constants.DIR_LICENSES + "/" + viewBean.getId();
					String filePath = dir + "/" + licenseViewBean.getLogoFileName();
					
					FileUtils.deleteDirectory(new File(dir));
					
					FileUtils.copyFile(new File(licenseViewBean.getLogo()), new File(filePath));
				}
	        }
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}
	
	public String translate() throws Exception {
		try {
			
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			HashMap<Integer, HashMap<String, String>> languagesContentMap = new HashMap<Integer,HashMap<String, String>>();
			
			String sourceLanguageId = request.getParameter("sourceLanguageId");
			Language sourceLanguage = (Language) languageService.findById(Integer.valueOf(sourceLanguageId));
			int i = 0;
			int j = Integer.valueOf(request.getParameter("tabIndex"));
			for (@SuppressWarnings("unused") Language language : languages) {
				if(i == j) {
					++i;
					continue;
				}
				
				String targetLanguageId = request.getParameter("viewBean.licenseContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				
				if(isEmpty(request.getParameter("viewBean.licenseContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.licenseContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.licenseContents["+i+"].description"))) contentMap.put("description", translationService.translate(request.getParameter("viewBean.licenseContents["+j+"].description"), sourceLanguage.getName(), targetLanguage.getName()));
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new License();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new LicenseTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
		}
		catch (Exception e) {
			this.checkTranslateException(e);
		}
		return null;
	}
	
	private boolean isEmpty(String str) {
		if (str.equals("") || str.equals("<br>") || str.equals("<br/>") || str.equals("\n") || str.equals("\r") || str.equals("\r\n") || str.equals("<p>&nbsp;</p>")){
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void checkTranslateException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("category.erreur.translate")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	public String saveDevices() throws Exception {
		LicenseViewBean licenseViewBean = (LicenseViewBean) viewBean;
		Integer licenseId = licenseViewBean.getId();
		List<Integer> deviceIds = ActionUtil.toListIds(licenseViewBean.getDeviceIds());

		// Mise � jour des ressources du groupe
		((LicenseService) entityService).saveDevices(licenseId, deviceIds);
		
		// Message de succ�s
		jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));

		return SUCCESS_JSON;
	}
	
	//=========================================================================
	// ACCESSORS
	//=========================================================================

	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
}
