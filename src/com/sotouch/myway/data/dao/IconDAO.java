package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Icon;

public class IconDAO extends BaseDAO<Icon> {

	public IconDAO() {
		super();
	}

	public List<Icon> findByProject(Integer projectId) {
		if (projectId == null) {
			return super.findByNamedQuery("select.icon.common");
		}
		else {
			return super.findByNamedQuery("select.icon.by.project", new Object[]{projectId});
		}
	}

	public List<Icon> findByItemType(Integer itemTypeId) {
		return super.findByNamedQuery("select.icon.by.itemType", new Object[]{itemTypeId});
	}
	
	public Icon findByGroupAndName(Integer groupId, String iconName) {
		return (Icon) super.findUniqueResultByNamedQuery("select.icon.by.group.and.name", new Object[]{groupId, iconName});
	}
}
