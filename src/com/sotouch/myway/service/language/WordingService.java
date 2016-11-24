package com.sotouch.myway.service.language;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.LanguageDAO;
import com.sotouch.myway.data.dao.WordingDAO;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Wording;


public class WordingService extends BaseCrudService {

	@Resource (name="languageDao")
	private LanguageDAO languageDao;

	public WordingService() {
	}
	
	@Override
	public Object saveOrUpdate(Object entity) throws Exception {
		Wording wording = (Wording) entity;
		Wording existWording = ((WordingDAO) entityDao).findByLanguageAndName(wording.getLanguage().getId(), wording.getName());
		if(existWording != null) {
			wording.setId(existWording.getId());
		}
		
		//if (wording.getId() == null) {
			// R�cup�ration de toutes les langues
			List<Language> languages = languageDao.findAll();
			Wording newWording = null;
			for (Language language : languages) {
				if (!language.getId().equals(wording.getLanguage().getId())) {
					// Cr�ation du wording pour toutes les langues
					newWording = ((WordingDAO) entityDao).findByLanguageAndName(language.getId(), wording.getName());
					if (newWording == null) {
						newWording = new Wording();
					}
					newWording.setName(wording.getName());
					newWording.setLanguage(language);
					if (language.getFlag().equals(wording.getLanguage().getFlag())) {
						newWording.setValue(wording.getValue());
						newWording.setDescription(wording.getDescription());
					}
					super.saveOrUpdate(newWording);
				}
			}
		//}
		
		return super.saveOrUpdate(entity);
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Wording wording = (Wording) super.findById(id);
		((WordingDAO) entityDao).deleteByName(wording.getName());
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
}
