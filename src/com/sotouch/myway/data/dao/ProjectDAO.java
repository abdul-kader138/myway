package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Project;

public class ProjectDAO extends BaseDAO<Project> {

	public ProjectDAO() {
		super();
	}
	
	public Project findByKey(String key) {
		return (Project) super.findUniqueResultByNamedQuery("select.project.by.key", new Object[]{key});
	}

	public List<Project> findByCompany(Integer companyId) {
		return super.findByNamedQuery("select.project.by.company", new Object[]{companyId});
	}

	/**
	 * Supprime les contenus associés à une langue
	 * @param languageId
	 */
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from ProjectContent as c where c.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
