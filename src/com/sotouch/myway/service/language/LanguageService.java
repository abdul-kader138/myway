package com.sotouch.myway.service.language;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.BuildingDAO;
import com.sotouch.myway.data.dao.CategoryDAO;
import com.sotouch.myway.data.dao.EventDAO;
import com.sotouch.myway.data.dao.FloorDAO;
import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.dao.LanguageDAO;
import com.sotouch.myway.data.dao.LicenseDAO;
import com.sotouch.myway.data.dao.ProjectDAO;
import com.sotouch.myway.data.dao.PromotionDAO;
import com.sotouch.myway.data.dao.SubCategoryDAO;
import com.sotouch.myway.data.dao.WordingDAO;
import com.sotouch.myway.data.dao.ZoneDAO;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Wording;


public class LanguageService extends BaseCrudService {

	@Resource (name="projectDao")
	private ProjectDAO projectDao;

	@Resource (name="itemDao")
	private ItemDAO itemDao;

	@Resource (name="promotionDao")
	private PromotionDAO promotionDao;

	@Resource (name="eventDao")
	private EventDAO eventDao;

	@Resource (name="categoryDao")
	private CategoryDAO categoryDao;

	@Resource (name="subCategoryDao")
	private SubCategoryDAO subCategoryDao;

	@Resource (name="wordingDao")
	private WordingDAO wordingDao;

	@Resource (name="licenseDao")
	private LicenseDAO licenseDao;
	
	@Resource (name="buildingDao")
	private BuildingDAO buildingDao;
	
	@Resource (name="floorDao")
	private FloorDAO floorDao;
	
	@Resource (name="zoneDao")
	private ZoneDAO zoneDao;
	
	public LanguageService() {
	}
	
	@Override
	public Object saveOrUpdate(Object entity) throws Exception {
		Language language = (Language) entity;
		Integer languageId = language.getId();
		
		if (language.getDefaultLanguage()) {
			// Passage des autres langues � "non par d�faut"
			List<Language> languages = ((LanguageDAO) entityDao).findByProject(language.getProject().getId());
			for (Language languageTmp : languages) {
				languageTmp.setDefaultLanguage(Boolean.FALSE);
				entityDao.saveOrUpdate(languageTmp);
			}
		}
		
		language = (Language) super.saveOrUpdate(entity);
		
		if (languageId == null) {
			//---------------------------------------------------------------------
			// Cr�ation des wordings � partir du template
			//---------------------------------------------------------------------
			String flag = language.getFlag();
			if (language.getProject() == null) {
				// Si la langue est un template on la cr�e � partir de la langue anglaise
				flag = "gb";
			}
			List<Wording> wordings = wordingDao.findTemplatesByLanguage(flag);
			Wording newWording = null;
			for (Wording wording : wordings) {
				newWording = new Wording();
				newWording.setLanguage(language);
				newWording.setName(wording.getName());
				newWording.setValue(wording.getValue());
				newWording.setDescription(wording.getDescription());
				wordingDao.saveOrUpdate(newWording);
			}
		}

		return language;
	}
	
	@Override
	public void delete(Object entity) throws Exception {
		Language language = (Language) entity;

		if (language.getDefaultLanguage() == null || language.getDefaultLanguage() == false) {
			// Suppression de tous les contenus associ�s � la langue
			projectDao.deleteContent(language.getId());
			itemDao.deleteContent(language.getId());
			promotionDao.deleteContent(language.getId());
			eventDao.deleteContent(language.getId());
			categoryDao.deleteContent(language.getId());
			subCategoryDao.deleteContent(language.getId());
			
			buildingDao.deleteContent(language.getId());
			floorDao.deleteContent(language.getId());
			zoneDao.deleteContent(language.getId());
			licenseDao.deleteContent(language.getId());
			// Suppression du wording
			wordingDao.deleteByLanguage(language.getId());
			
			
			
			super.delete(language);
		}
		else {
			throw new DefaultLanguageException();
		}
	}

	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<Language> findByProject(Integer projectId) {
		return ((LanguageDAO) entityDao).findByProject(projectId);
	}

	public Long countByProject(Integer projectId) {
		return ((LanguageDAO) entityDao).countByProject(projectId);
	}
	
	public Language findDefaultByProject(Integer projectId) {
		return ((LanguageDAO) entityDao).findDefaultByProject(projectId);
	}
	
	public List<Language> findTemplates() {
		return ((LanguageDAO) entityDao).findTemplates();
	}
	
	/**
	 * Initialise les langues de chaque projet avec les wordings de r�f�rence
	 */
	public void initProjectLanguages() {
		List<Language> languagesTemplates = ((LanguageDAO) entityDao).findTemplates();
		for (Language languageTemplate : languagesTemplates) {
			List<Wording> wordingsTemplate = wordingDao.findByLanguage(languageTemplate.getId());
			List<Language> languagesProjects = ((LanguageDAO) entityDao).findByFlag(languageTemplate.getFlag());
			for (Language language : languagesProjects) {
				wordingDao.deleteByLanguage(language.getId());
				for (Wording wordingTemplate : wordingsTemplate) {
					Wording wording = new Wording();
					wording.setLanguage(language);
					wording.setName(wordingTemplate.getName());
					wording.setValue(wordingTemplate.getValue());
					wording.setDescription(wordingTemplate.getDescription());
					wordingDao.save(wording);
				}
			}
		}
	}
}
