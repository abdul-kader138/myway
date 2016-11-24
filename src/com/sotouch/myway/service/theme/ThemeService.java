package com.sotouch.myway.service.theme;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.ThemeDAO;
import com.sotouch.myway.data.model.Theme;


public class ThemeService extends BaseCrudService {

	public ThemeService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public Theme findByProject(Integer projectId) throws Exception {
		return ((ThemeDAO) entityDao).findByProject(projectId);
	}	

}
