package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Wording;

public class WordingDAO extends BaseDAO<Wording> {

	public WordingDAO() {
		super();
	}
	
	public List<Wording> findByLanguage(Integer languageId) {
		return super.findByNamedQuery("select.wording.by.language", new Object[]{languageId});
	}
	
	public Wording findByLanguageAndName(Integer languageId, String name) {
		return (Wording) super.findUniqueResultByNamedQuery("select.wording.by.language.and.name", new Object[]{languageId, name});
	}
	
	public List<Wording> findTemplatesByLanguage(String languageFlag) {
		return super.findByNamedQuery("select.wording.templates.by.languageFlag", new Object[]{languageFlag});
	}

	public List<Wording> findByProjectGroupByName(Integer projectId) {
		return super.findByNamedQuery("select.wording.by.project.groupBy.name", new Object[]{projectId});
	}

	public void deleteByName(String name) {
		Query query = super.getSession(false).getNamedQuery("delete.wording.by.name");
		query.setString(0, name);
		query.executeUpdate();
	}

	public void deleteByLanguage(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from Wording as w where w.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
