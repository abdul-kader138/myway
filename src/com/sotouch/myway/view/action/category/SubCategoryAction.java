package com.sotouch.myway.view.action.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.SubCategoryContent;
import com.sotouch.myway.service.category.SubCategoryService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;

/**
 * Class which manages the item sub-categories
 */
public class SubCategoryAction extends BaseCrudAction<SubCategoryViewBean> {

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="translationService")
	private TranslationService translationService;
	
	private Integer destCategoryId;
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		SubCategoryViewBean subCategoryViewBean = (SubCategoryViewBean) viewBean;
		criteriaMap.put("name", subCategoryViewBean.getName());
		criteriaMap.put("category.id", subCategoryViewBean.getCategoryId());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		SubCategoryViewBean subCategoryViewBean = (SubCategoryViewBean) viewBean;
		SubCategory subCategory = (SubCategory) dataBean;
		subCategory.setName(subCategoryViewBean.getName());
		subCategory.setColor(subCategoryViewBean.getColor());
		if (subCategoryViewBean.getCategoryId() != null) {
			subCategory.setCategory(new Category(Integer.valueOf(subCategoryViewBean.getCategoryId())));
		}
		
		// Récupération de la langue par défaut
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		
		// SubCategory contents
		List<SubCategoryContent> subCategoryContents = new ArrayList<SubCategoryContent>();
		SubCategoryContent subCategoryContent = null;
		int i = 0;
		for (SubCategoryContentViewBean subCategoryContentViewBean : subCategoryViewBean.getSubCategoryContents()) {
			subCategoryContent = new SubCategoryContent();
			subCategoryContent.setSubCategory(subCategory);
			if (subCategoryContentViewBean != null) {
				subCategoryContent.setId(subCategoryContentViewBean.getId());
				subCategoryContent.setLanguage(new Language(subCategoryContentViewBean.getLanguageId()));
				subCategoryContent.setName(subCategoryContentViewBean.getName());

				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(subCategoryContentViewBean.getLanguageCode()))) {
					subCategory.setName(subCategoryContentViewBean.getName());
				}
			}
			subCategoryContent.setIndex(i++);
			subCategoryContents.add(subCategoryContent);
		}
		subCategory.setSubCategoryContents(subCategoryContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		SubCategory subCategory = (SubCategory) dataBean;
		SubCategoryViewBean subCategoryViewBean = (SubCategoryViewBean) viewBean;
		subCategoryViewBean.setName(subCategory.getName());
		subCategoryViewBean.setColor(subCategory.getColor());
		subCategoryViewBean.setCategoryId(subCategory.getCategory() != null ? subCategory.getCategory().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new SubCategoryJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("subCategory.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================
	/**
	 * Transform une catégorie en sous-catégorie
	 */
	public String transformCategoryToSubCategory() {
		((SubCategoryService) entityService).transformCategoryToSubCategory(viewBean.getId(), destCategoryId);
		return SUCCESS_JSON;
	}
	
	public String translate() throws Exception {
		try {
			
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			HashMap<Integer, HashMap<String, String>> languagesContentMap = new HashMap<Integer,HashMap<String, String>>();
			
			String sourceLanguageId = request.getParameter("sourceLanguageId");
			Language sourceLanguage = (Language) languageService.findById(Integer.valueOf(sourceLanguageId));
			System.out.println("Source language: '" + sourceLanguage.getName() + "' id: " + sourceLanguageId);
			int i = 0;
			int j = Integer.valueOf(request.getParameter("tabIndex"));
			for (@SuppressWarnings("unused") Language language : languages) {
				String targetLanguageId = request.getParameter("viewBean.subCategoryContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				if(isEmpty(request.getParameter("viewBean.subCategoryContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.subCategoryContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new SubCategory();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new SubCategoryTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
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
			jsonResponse.addError(new JSONError(null, resources.getString("subCategory.erreur.translate")));
		}
		else {
			throw e;
		}
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public Integer getDestCategoryId() {
		return destCategoryId;
	}
	public void setDestCategoryId(Integer destCategoryId) {
		this.destCategoryId = destCategoryId;
	}
}
