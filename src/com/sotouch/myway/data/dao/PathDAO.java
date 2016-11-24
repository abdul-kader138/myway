package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Path;

public class PathDAO extends BaseDAO<Path> {

	public PathDAO() {
		super();
	}
	
	public List<Path> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.path.by.project", new Object[]{projectId});
	}

	public void deleteByProject(Integer projectId) {
		Query query = super.getSession(false).createSQLQuery("delete from PATH where ITM_ID_START in (select ITM_ID from ITEM_MAP IM, ITEM I where IM.ITE_ID=I.ITE_ID and I.PRO_ID=?)");
		query.setInteger(0, projectId);
		query.executeUpdate();
	}
}
