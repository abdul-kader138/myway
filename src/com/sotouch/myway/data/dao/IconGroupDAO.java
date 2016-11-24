package com.sotouch.myway.data.dao;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.IconGroup;

public class IconGroupDAO extends BaseDAO<IconGroup> {

	public IconGroupDAO() {
		super();
	}
	
	public IconGroup findByProjectAndName(int id, String name) {
		return (IconGroup) super.findUniqueResultByNamedQuery("select.by.project.name", new Object[]{id, name});
	}
}
