package com.sotouch.myway.data.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Event;

public class EventDAO extends BaseDAO<Event> {

	public EventDAO() {
		super();
	}
	
	public List<Event> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.event.by.project", new Object[]{projectId});
	}
	
	public Long countByProject(Integer projectId) {
		return (Long) super.findUniqueResultByNamedQuery("count.event.by.project", new Object[]{projectId});
	}

	@Override
	protected Criteria getPageCriteria(Map<String, Object> criteriaMap, String order, String dir) {
		Criteria criteria = super.getPageCriteria(criteriaMap, order, dir);
		
		String search = (String) criteriaMap.get("\\search");
		if (search != null) {
			criteria.createAlias("eventContents", "ec");

	        Criterion name = Restrictions.like("ec.name", "%"+search+"%");
			Criterion keywords = Restrictions.like("ec.keywords", "%"+search+"%");
	        LogicalExpression or = Restrictions.or(name, keywords);
			criteria.add(or);
		}

		return criteria;
	}

	/**
	 * Supprime les contenus associés à une langue
	 * @param languageId
	 */
	public void deleteContent(Integer languageId) {
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from EventContent as c where c.language.id = ?");
		query.setInteger(0, languageId);
		query.executeUpdate();
	}
}
