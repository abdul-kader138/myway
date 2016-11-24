package com.sotouch.myway.view.action.event;

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
import com.sotouch.myway.data.model.Event;
import com.sotouch.myway.data.model.EventContent;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.item.ItemService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;

/**
 * Class which manages the events
 */
public class EventAction extends BaseCrudAction<EventViewBean> {

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
		items = itemService.findByProject(projectId);
		languages = languageService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		EventViewBean eventViewBean = (EventViewBean) viewBean;
		criteriaMap.put("project.id", eventViewBean.getProjectId());
		criteriaMap.put("\\search", eventViewBean.getName());
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
				EventViewBean ebentViewBean = (EventViewBean) viewBean;
				if (ebentViewBean.getImage() != null) {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					String dir = uploadPath+"/"+Constants.DIR_EVENTS+"/"+ebentViewBean.getId();
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+ebentViewBean.getImageFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(ebentViewBean.getImage()), uploadedFile);
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
		EventViewBean eventViewBean = (EventViewBean) viewBean;
		Event event = (Event) dataBean;
		event.setStart(DateUtil.toDate(eventViewBean.getStart()+" "+eventViewBean.getHourStart(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		event.setEnd(DateUtil.toDate(eventViewBean.getEnd()+" "+eventViewBean.getHourEnd(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		if (eventViewBean.getImageFileName() != null) {
			event.setImage(eventViewBean.getImageFileName());
		}
		if (eventViewBean.getProjectId() != null) {
			event.setProject(new Project(Integer.valueOf(eventViewBean.getProjectId())));
		}
		if (eventViewBean.getItemId() != null) {
			event.setItem(new Item(Integer.valueOf(eventViewBean.getItemId())));
		}
		
		// Retreive default language
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));

		// Event contents
		List<EventContent> eventContents = new ArrayList<EventContent>();
		EventContent eventContent = null;
		int i = 0;
		for (EventContentViewBean eventContentViewBean : eventViewBean.getEventContents()) {
			eventContent = new EventContent();
			eventContent.setEvent(event);
			if (eventContentViewBean != null) {
				eventContent.setId(eventContentViewBean.getId());
				eventContent.setLanguage(new Language(eventContentViewBean.getLanguageId()));
				eventContent.setName(eventContentViewBean.getName());
				eventContent.setDescription(eventContentViewBean.getDescription());
				eventContent.setKeywords(eventContentViewBean.getKeywords());

				if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(eventContentViewBean.getLanguageCode()))) {
					event.setName(eventContentViewBean.getName());
				}
			}
			eventContent.setIndex(i++);
			eventContents.add(eventContent);
		}
		event.setEventContents(eventContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Event event = (Event) dataBean;
		EventViewBean eventViewBean = (EventViewBean) viewBean;
		eventViewBean.setName(event.getName());
		eventViewBean.setStart(DateUtil.toString(event.getStart(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		eventViewBean.setEnd(DateUtil.toString(event.getEnd(), Constants.DAY_FORMAT+" "+Constants.HOUR_FORMAT));
		eventViewBean.setImage(event.getImage() != null ? event.getImage().substring(event.getImage().lastIndexOf("/")+1) : "");
		eventViewBean.setProject(event.getProject() != null ? event.getProject().getName() : "");
		eventViewBean.setProjectId(event.getProject() != null ? event.getProject().getId() : null);
		eventViewBean.setItem(event.getItem() != null ? event.getItem().getName().toString() : "");
		eventViewBean.setItemId(event.getItem() != null ? event.getItem().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new EventJSONAdapter(languages));
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
				String targetLanguageId = request.getParameter("viewBean.eventContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();

				if(isEmpty(request.getParameter("viewBean.eventContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.eventContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.eventContents["+i+"].description"))) contentMap.put("description", translationService.translate(request.getParameter("viewBean.eventContents["+j+"].description"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.eventContents["+i+"].keywords"))) contentMap.put("keywords", translationService.translate(request.getParameter("viewBean.eventContents["+j+"].keywords"), sourceLanguage.getName(), targetLanguage.getName()));
	
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new Event();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new EventTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
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
			jsonResponse.addError(new JSONError(null, resources.getString("event.erreur.translate")));
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
