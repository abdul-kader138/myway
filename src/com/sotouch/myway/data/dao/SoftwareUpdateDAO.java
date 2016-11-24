package com.sotouch.myway.data.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Ressource;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.SoftwareUpdate;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Settings;

public class SoftwareUpdateDAO extends BaseDAO<SoftwareUpdate> {

	public SoftwareUpdateDAO() {
		super();
	}
	
	public SoftwareUpdate findByLicense(Integer licenseId) {
		return (SoftwareUpdate) super.findUniqueResultByNamedQuery("select.softareupdate.by.license", new Object[] { licenseId });
	}
	
	@Override
	protected Criteria getPageCriteria(Map<String, Object> criteriaMap, String order, String dir) {
		Criteria criteria = super.getPageCriteria(criteriaMap, order, dir);
		String search = (String) criteriaMap.get("\\search");
		if (search != null) {
			Criterion oldVersion = Restrictions.like("oldVersion", "%"+search+"%");
			Criterion newVersion = Restrictions.like("newVersion", "%"+search+"%");
			LogicalExpression or = Restrictions.or(oldVersion, newVersion);
			criteria.add(or);
		}
		return criteria;
	}
}
