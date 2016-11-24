package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Building;

public class BuildingDAO extends BaseDAO<Building> {

	public BuildingDAO() {
		super();
	}
	
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from BuildingContent as b where b.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
	
	public List<Building> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.building.by.project", new Object[]{projectId});
	}
}
