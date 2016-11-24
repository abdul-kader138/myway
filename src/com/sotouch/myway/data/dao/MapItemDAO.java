package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.MapItem;

public class MapItemDAO extends BaseDAO<MapItem> {
	
	public MapItemDAO() {
		super();
	}
	
	public List<MapItem> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.mapItem.by.project", new Object[]{projectId});
	}

	public MapItem findByItemId(String itemId) {
		return (MapItem)super.findUniqueResultByNamedQuery("select.mapItem.by.itemId", new Object[]{itemId});
	}

	public void deleteByProject(Integer projectId) {
		Query query = super.getSession(false).createSQLQuery("delete from MAP_ITEM where PROJECT_ID = ?");
		query.setInteger(0, projectId);
		query.executeUpdate();
	}


}
