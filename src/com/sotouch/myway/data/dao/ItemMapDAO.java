package com.sotouch.myway.data.dao;

import java.util.List;

import org.hibernate.Query;
import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.ItemMap;

public class ItemMapDAO extends BaseDAO<ItemMap> {

	public ItemMapDAO() {
		super();
	}
	
	public List<ItemMap> findByProjectAndType(Integer projectId, Integer typeItemId) {
		return super.findByNamedQuery("select.itemMap.by.project.and.type", new Object[]{projectId, typeItemId});
	}

	public void deleteByProject(Integer projectId) {
		Query query = super.getSession(false).createSQLQuery("delete from ITEM_MAP where ITE_ID in (select ITE_ID from ITEM I where I.PRO_ID=?)");
		query.setInteger(0, projectId);
		query.executeUpdate();
	}
}
