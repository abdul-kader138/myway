package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Language;

public class LanguageDAO extends BaseDAO<Language> {

	public LanguageDAO() {
		super();
	}
	
	public List<Language> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.language.by.project", new Object[]{projectId});
	}
	
	public Long countByProject(Integer projectId) {
		return (Long) super.findUniqueResultByNamedQuery("count.language.by.project", new Object[]{projectId});
	}
	
	public Language findDefaultByProject(Integer projectId) {
		return (Language) super.findUniqueResultByNamedQuery("select.language.default.by.project", new Object[]{projectId});
	}
	
	public List<Language> findByFlag(String flag) {
		return super.findByNamedQuery("select.language.by.flag", new Object[]{flag});
	}
	
	public List<Language> findTemplates() {
		return super.findByNamedQuery("select.language.templates");
	}
}
