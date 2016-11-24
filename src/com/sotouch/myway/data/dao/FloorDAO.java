package com.sotouch.myway.data.dao;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Floor;

public class FloorDAO extends BaseDAO<Floor> {

	public FloorDAO() {
		super();
	}
	
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from FloorContent as f where f.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
