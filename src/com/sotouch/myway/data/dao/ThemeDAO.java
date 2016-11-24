package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Settings;
import com.sotouch.myway.data.model.Theme;

public class ThemeDAO extends BaseDAO<Theme> {

	public ThemeDAO() {
		super();
	}
	
	public Theme findByProject(Integer projectId) {
		Theme theme = null;
		List<Theme> themes = super.findByNamedQuery("select.theme.by.project", new Object[]{projectId});
		if (themes.size() > 0) {
			theme = themes.get(0);
		}
		return theme;
	}
}
