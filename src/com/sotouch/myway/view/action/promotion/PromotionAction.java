package com.sotouch.myway.view.action.promotion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.Promotion;
import com.sotouch.myway.data.model.PromotionContent;
import com.sotouch.myway.service.item.ItemService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;

/**
 * Class which manages the promotions
 */
public class PromotionAction extends BaseCrudAction<PromotionViewBean> {

	@Resource (name="itemService")
	private ItemService itemService;

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Resource (name="translationService")
	private TranslationService translationService;

	@RemoteProperty(jsonAdapter="properties:id,name")
	private List items;

	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		items = itemService.findByProjectAndType(projectId, Constants.ITEM_TYPE_LOCATION);
		languages = languageService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		PromotionViewBean promotionViewBean = (PromotionViewBean) viewBean;
		criteriaMap.put("project.id", promotionViewBean.getProjectId());
		criteriaMap.put("\\search", promotionViewBean.getName());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	public String save() throws Exception {
		try {
			super.execute();
			
			if (jsonResponse.getErrors().size() < 1) {
				// Image upload
				PromotionViewBean promotionViewBean = (PromotionViewBean) viewBean;
				if (promotionViewBean.getImage() != null) {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					String dir = uploadPath+"/"+Constants.DIR_PROMOTIONS+"/"+promotionViewBean.getId();
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+promotionViewBean.getImageFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(promotionViewBean.getImage()), uploadedFile);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		PromotionViewBean promotionViewBean = (PromotionViewBean) viewBean;
		Promotion promotion = (Promotion) dataBean;
		promotion.setStart(DateUtil.toDate(promotionViewBean.getStart()+" "+promotionViewBean.getHourStart(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		promotion.setEnd(DateUtil.toDate(promotionViewBean.getEnd()+" "+promotionViewBean.getHourEnd(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		if (promotionViewBean.getImageFileName() != null) {
			promotion.setImage(promotionViewBean.getImageFileName());
		}
		if (promotionViewBean.getProjectId() != null) {
			promotion.setProject(new Project(Integer.valueOf(promotionViewBean.getProjectId())));
		}
		if (promotionViewBean.getItemId() != null) {
			promotion.setItem(new Item(Integer.valueOf(promotionViewBean.getItemId())));
		}
		
		// Retreive the default language
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));

		// Promotion contents
		List<PromotionContent> promotionContents = new ArrayList<PromotionContent>();
		PromotionContent promotionContent = null;
		int i = 0;
		for (PromotionContentViewBean promotionContentViewBean : promotionViewBean.getPromotionContents()) {
			promotionContent = new PromotionContent();
			promotionContent.setPromotion(promotion);
			if (promotionContentViewBean != null) {
				promotionContent.setId(promotionContentViewBean.getId());
				promotionContent.setLanguage(new Language(promotionContentViewBean.getLanguageId()));
				promotionContent.setName(promotionContentViewBean.getName());
				promotionContent.setDescription(promotionContentViewBean.getDescription());
				promotionContent.setKeywords(promotionContentViewBean.getKeywords());

				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(promotionContentViewBean.getLanguageCode()))) {
					promotion.setName(promotionContentViewBean.getName());
				}
			}
			promotionContent.setIndex(i++);
			promotionContents.add(promotionContent);
		}
		promotion.setPromotionContents(promotionContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Promotion promotion = (Promotion) dataBean;
		PromotionViewBean promotionViewBean = (PromotionViewBean) viewBean;
		promotionViewBean.setName(promotion.getName());
		promotionViewBean.setStart(DateUtil.toString(promotion.getStart(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		promotionViewBean.setEnd(DateUtil.toString(promotion.getEnd(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		promotionViewBean.setImage(promotion.getImage() != null ? promotion.getImage().substring(promotion.getImage().lastIndexOf("/")+1) : "");
		promotionViewBean.setProject(promotion.getProject() != null ? promotion.getProject().getName() : "");
		promotionViewBean.setProjectId(promotion.getProject() != null ? promotion.getProject().getId() : null);
		promotionViewBean.setItem(promotion.getItem() != null ? promotion.getItem().getName().toString() : "");
		promotionViewBean.setItemId(promotion.getItem() != null ? promotion.getItem().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new PromotionJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================

	public String translate() throws Exception {
		try {
			
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			HashMap<Integer, HashMap<String, String>> languagesContentMap = new HashMap<Integer,HashMap<String, String>>();
			
			String sourceLanguageId = request.getParameter("sourceLanguageId");
			Language sourceLanguage = (Language) languageService.findById(Integer.valueOf(sourceLanguageId));
			// System.out.println("Source language: '" + sourceLanguage.getName() + "' id: " + sourceLanguageId);
			int i = 0;
			int j = Integer.valueOf(request.getParameter("tabIndex"));
			for (Language language : languages) {
				String targetLanguageId = request.getParameter("viewBean.promotionContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();

				if(isEmpty(request.getParameter("viewBean.promotionContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.promotionContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.promotionContents["+i+"].description"))) contentMap.put("description", translationService.translate(request.getParameter("viewBean.promotionContents["+j+"].description"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.promotionContents["+i+"].keywords"))) contentMap.put("keywords", translationService.translate(request.getParameter("viewBean.promotionContents["+j+"].keywords"), sourceLanguage.getName(), targetLanguage.getName()));
	
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new Promotion();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new PromotionTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
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
			jsonResponse.addError(new JSONError(null, resources.getString("promotion.erreur.translate")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// ACCESSORS
	//=========================================================================

	public void setItems(List items) {
		this.items = items;
	}
	public List getItems() {
		return items;
	}

	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
}
