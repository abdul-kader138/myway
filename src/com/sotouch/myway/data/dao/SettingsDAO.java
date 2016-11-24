package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Settings;

public class SettingsDAO extends BaseDAO<Settings> {

	public SettingsDAO() {
		super();
	}
	
	public Settings findByProject(Integer projectId) {
		Settings settings = null;
		List<Settings> settingss = super.findByNamedQuery("select.settings.by.project", new Object[]{projectId});
		if (settingss.size() > 0) {
			settings = settingss.get(0);
		}
		return settings;
	}
}
