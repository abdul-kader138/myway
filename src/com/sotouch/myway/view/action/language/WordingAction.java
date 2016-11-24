package com.sotouch.myway.view.action.language;

import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;

import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Wording;
import com.sotouch.myway.service.language.LanguageService;

/**
 * Class which manages the wordings
 */
public class WordingAction extends BaseCrudAction<WordingViewBean> {

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		WordingViewBean wordingViewBean = (WordingViewBean) viewBean;
		criteriaMap.put("name", wordingViewBean.getName());
		criteriaMap.put("language.id", wordingViewBean.getLanguageId());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		WordingViewBean wordingViewBean = (WordingViewBean) viewBean;
		Wording wording = (Wording) dataBean;
		wording.setName(wordingViewBean.getName());
		wording.setValue(wordingViewBean.getValue());
		wording.setDescription(wordingViewBean.getDescription());
		if (wordingViewBean.getLanguageId() != null) {
			wording.setLanguage((Language) languageService.findById((Integer.valueOf(wordingViewBean.getLanguageId()))));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Wording wording = (Wording) dataBean;
		WordingViewBean wordingViewBean = (WordingViewBean) viewBean;
		wordingViewBean.setName(wording.getName());
		wordingViewBean.setValue(wording.getValue());
		wordingViewBean.setDescription(wording.getDescription());
		wordingViewBean.setLanguageId(wording.getLanguage() != null ? wording.getLanguage().getId() : null);
	}
	
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================
}
