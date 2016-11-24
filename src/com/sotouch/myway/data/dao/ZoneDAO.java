package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Zone;

public class ZoneDAO extends BaseDAO<Zone> {

	public ZoneDAO() {
		super();
	}

	public List<Zone> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.zone.by.project", new Object[]{projectId});
	}
	/**
	 * Supprime les contenus associ�s � une langue
	 * @param languageId
	 */
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from ZoneContent as z where z.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
