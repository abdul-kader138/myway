package com.sotouch.myway.view.action.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.category.CategoryService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;

/**
 * Class which manages the item categories
 */
public class CategoryAction extends BaseCrudAction<CategoryViewBean> {

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="translationService")
	private TranslationService translationService;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		languages = languageService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		CategoryViewBean categoryViewBean = (CategoryViewBean) viewBean;
		criteriaMap.put("name", categoryViewBean.getName());
		criteriaMap.put("project.id", categoryViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		CategoryViewBean categoryViewBean = (CategoryViewBean) viewBean;
		Category category = (Category) dataBean;
		category.setName(categoryViewBean.getName());
		category.setColor(categoryViewBean.getColor());
		if (categoryViewBean.getProjectId() != null) {
			category.setProject(new Project(Integer.valueOf(categoryViewBean.getProjectId())));
		}
		
		// R�cup�ration de la langue par d�faut
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		
		// Category contents
		List<CategoryContent> categoryContents = new ArrayList<CategoryContent>();
		CategoryContent categoryContent = null;
		int i = 0;
		for (CategoryContentViewBean categoryContentViewBean : categoryViewBean.getCategoryContents()) {
			categoryContent = new CategoryContent();
			categoryContent.setCategory(category);
			if (categoryContentViewBean != null) {
				categoryContent.setId(categoryContentViewBean.getId());
				categoryContent.setLanguage(new Language(categoryContentViewBean.getLanguageId()));
				categoryContent.setName(categoryContentViewBean.getName());

				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(categoryContentViewBean.getLanguageCode()))) {
					category.setName(categoryContentViewBean.getName());
				}
			}
			categoryContent.setIndex(i++);
			categoryContents.add(categoryContent);
		}
		category.setCategoryContents(categoryContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Category category = (Category) dataBean;
		CategoryViewBean categoryViewBean = (CategoryViewBean) viewBean;
		
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		categoryViewBean.setName(category.getName());
		List<CategoryContent> categoryContents = category.getCategoryContents();
		if(defaultLanguage != null && defaultLanguage.getFlag() != null && categoryContents != null) {
			for(CategoryContent categoryContent : categoryContents) {
				if(categoryContent == null) continue;
				
				if(defaultLanguage.getFlag().equals(categoryContent.getLanguage().getFlag())) {
					categoryViewBean.setName(categoryContent.getName());
					break;
				}
			}
		}
		
		categoryViewBean.setColor(category.getColor());
		categoryViewBean.setProject(category.getProject() != null ? category.getProject().getName() : "");
		categoryViewBean.setProjectId(category.getProject() != null ? category.getProject().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new CategoryJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("category.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================
	/**
	 * Transform une sous-cat�gorie en cat�gorie
	 */
	public String transformSubCategoryToCategory() {
		((CategoryService) entityService).transformSubCategoryToCategory(viewBean.getId());
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
				String targetLanguageId = request.getParameter("viewBean.categoryContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				if(isEmpty(request.getParameter("viewBean.categoryContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.categoryContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new Category();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new CategoryTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
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
	// ACCESSORS
	//=========================================================================

	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
}
