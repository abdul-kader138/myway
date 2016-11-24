package com.sotouch.myway.view.action.language;

import java.util.List;
import java.util.Map;

import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.language.DefaultLanguageException;
import com.sotouch.myway.service.language.LanguageService;

/**
 * Class which manages the languages
 */
public class LanguageAction extends BaseCrudAction<LanguageViewBean> {

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		LanguageViewBean languageViewBean = (LanguageViewBean) viewBean;
		criteriaMap.put("name", languageViewBean.getName());
		criteriaMap.put("project.id", languageViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "index";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		LanguageViewBean languageViewBean = (LanguageViewBean) viewBean;
		Language language = (Language) dataBean;
		language.setName(languageViewBean.getName());
		language.setCode(languageViewBean.getCode());
		language.setFlag(languageViewBean.getFlag());
		language.setIndex(NumberUtil.toInteger(languageViewBean.getIndex()));
		language.setDefaultLanguage(languageViewBean.isDefaultLanguage());
		if (languageViewBean.getProjectId() != null) {
			language.setProject(new Project(Integer.valueOf(languageViewBean.getProjectId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Language language = (Language) dataBean;
		LanguageViewBean languageViewBean = (LanguageViewBean) viewBean;
		languageViewBean.setName(language.getName());
		languageViewBean.setFlag(language.getFlag());
		languageViewBean.setCode(language.getCode());
		languageViewBean.setIndex(NumberUtil.toString(language.getIndex()));
		languageViewBean.setDefaultLanguage(language.getDefaultLanguage() != null ? language.getDefaultLanguage() : Boolean.FALSE);
		languageViewBean.setProject(language.getProject() != null ? language.getProject().getName() : "");
		languageViewBean.setProjectId(language.getProject() != null ? language.getProject().getId() : null);
	}
	
	@Override
	protected void checkSaveException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError("viewBean.flag", resources.getString("language.erreur.save")));
		}
		else {
			throw e;
		}
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("language.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	@Override
	public String delete() throws Exception {
		String[] idsTab = this.getSelectedIds().split(";");
		try {
			for (String element : idsTab) {
				Language language = (Language) this.entityService.findById(Integer.valueOf(element));
				try {
					this.entityService.delete(language);
				}
				catch (DefaultLanguageException e) {
					jsonResponse.addError(new JSONError(resources.getString("language.erreur.delete.default")));
				}
			}
		}
		catch (Exception e) {
			this.checkDeleteException(e);
		}

		// Message de succ�s
		if (this.jsonResponse.getErrors().isEmpty()) {
			this.jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));
		}
		return SUCCESS_JSON;
	}
	
	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================
	public String loadTemplates() throws Exception {
		List<Language> languages = ((LanguageService) entityService).findTemplates();
		jsonResponse.setDatas(toViewBeans(languages));
		return SUCCESS_JSON;
	}
	
	/**
	 * Initialize the projects' languages with the reference wordings
	 * @return
	 * @throws Exception
	 */
	public String resetProjectsLaguages() throws Exception {
		((LanguageService) entityService).initProjectLanguages();
		return SUCCESS_JSON;
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================
}
