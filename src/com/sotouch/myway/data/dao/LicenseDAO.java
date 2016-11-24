package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Settings;

public class LicenseDAO extends BaseDAO<License> {

	public LicenseDAO() {
		super();
	}
	
	public List<License> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.license.by.project", new Object[]{projectId});
	}
	
	public Long countByProject(Integer projectId) {
		return (Long) super.findUniqueResultByNamedQuery("count.license.by.project", new Object[]{projectId});
	}
	
	
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from LicenseContent as l where l.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
