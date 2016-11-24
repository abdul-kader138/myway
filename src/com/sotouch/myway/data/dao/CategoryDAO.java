package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Category;

public class CategoryDAO extends BaseDAO<Category> {

	public CategoryDAO() {
		super();
	}
	
	public List<Category> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.category.by.project", new Object[]{projectId});
	}

	/**
	 * Supprime les contenus associés à une langue
	 * @param languageId
	 */
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from CategoryContent as c where c.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
