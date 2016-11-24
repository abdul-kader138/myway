package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.SubCategory;

public class SubCategoryDAO extends BaseDAO<SubCategory> {

	public SubCategoryDAO() {
		super();
	}
	
	public List<SubCategory> findByCategory(Integer categoryId) {
		return super.findByNamedQuery("select.subCategory.by.category", new Object[]{categoryId});
	}

	/**
	 * Supprime les contenus associés à une langue
	 * @param languageId
	 */
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from SubCategoryContent as c where c.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
