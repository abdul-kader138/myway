package com.sotouch.myway.service.settings;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.SettingsDAO;
import com.sotouch.myway.data.model.Settings;


public class SettingsService extends BaseCrudService {

	public SettingsService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public Settings findByProject(Integer projectId) throws Exception {
		return ((SettingsDAO) entityDao).findByProject(projectId);
	}	

}
