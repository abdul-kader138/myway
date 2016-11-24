package com.sotouch.myway.view.action.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.BuildingContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.category.CategoryService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.view.action.license.LicenseTranslateJSONAdapter;

/**
 * Class which manages the buildings
 */
public class BuildingAction extends BaseCrudAction<BuildingViewBean> {

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource(name="categoryService")
	  private CategoryService categoryService;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@RemoteProperty(jsonAdapter="properties:id,name")
	  private List categories;
	
	
	@Resource (name="translationService")
	private TranslationService translationService;
	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		languages = languageService.findByProject(projectId);
		this.categories = this.categoryService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		BuildingViewBean buildingViewBean = (BuildingViewBean) viewBean;
		criteriaMap.put("name", buildingViewBean.getName());
		criteriaMap.put("project.id", buildingViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "index";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		BuildingViewBean buildingViewBean = (BuildingViewBean) viewBean;
		Building building = (Building) dataBean;
		building.setName(buildingViewBean.getName());
		building.setIndex(NumberUtil.toInteger(buildingViewBean.getIndex()));
		if (buildingViewBean.getProjectId() != null) {
			building.setProject(new Project(Integer.valueOf(buildingViewBean.getProjectId())));
		}
		
		// Get default language
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));

		// Item contents
		List<BuildingContent> buildingContents = new ArrayList<BuildingContent>();
		BuildingContent buildingContent = null;
		
		int i = 0;
		for (BuildingContentViewBean buildingContentViewBean : buildingViewBean.getBuildingContents()) {
			buildingContent = new BuildingContent();
			buildingContent.setBuilding(building);
			
			if (buildingContentViewBean != null) {
				buildingContent.setId(buildingContentViewBean.getId());
				buildingContent.setLanguage(new Language(buildingContentViewBean.getLanguageId()));
				buildingContent.setName(buildingContentViewBean.getName());
				
				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(buildingContentViewBean.getLanguageCode()))) {
					building.setName(buildingContentViewBean.getName());
				}
			}
			
			buildingContent.setIndex(i++);
			buildingContents.add(buildingContent);
		}
		building.setBuildingContents(buildingContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Building building = (Building) dataBean;
		BuildingViewBean buildingViewBean = (BuildingViewBean) viewBean;
		
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		BuildingContent buildingContent = getBuildingContent(building.getBuildingContents(), defaultLanguage.getId());
		
		if(buildingContent != null) {
			buildingViewBean.setName(buildingContent.getName());
		}
		else {
			buildingViewBean.setName(building.getName());
		}
		
		//buildingViewBean.setName(building.getName());
		buildingViewBean.setIndex(NumberUtil.toString(building.getIndex()));
		buildingViewBean.setProject(building.getProject() != null ? building.getProject().getName() : "");
		buildingViewBean.setProjectId(building.getProject() != null ? building.getProject().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new BuildingJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("building.erreur.delete")));
		}
		else {
			throw e;
		}
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
				
				String targetLanguageId = request.getParameter("viewBean.buildingContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				
				if(isEmpty(request.getParameter("viewBean.buildingContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.buildingContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				//if(isEmpty(request.getParameter("viewBean.buildingContents["+i+"].description"))) contentMap.put("description", translationService.translate(request.getParameter("viewBean.buildingContents["+j+"].description"), sourceLanguage.getName(), targetLanguage.getName()));
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new License();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new BuildingTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
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

	private BuildingContent getBuildingContent(List<BuildingContent> buildingContents, Integer languageId) {
    	for (BuildingContent buildingContent : buildingContents) {
			if (buildingContent != null && buildingContent.getLanguage().getId().equals(languageId)) {
				return buildingContent;
			}
		}
    	return null;
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
	
	public void setCategories(List categories) {
  		this.categories = categories;
	}
	public List getCategories() {
	  return this.categories;
	}
}
