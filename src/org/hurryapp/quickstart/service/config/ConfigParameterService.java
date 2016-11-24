package org.hurryapp.quickstart.service.config;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;


public class ConfigParameterService extends BaseCrudService {

	public ConfigParameterService() {
	}

	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public Object findParameter(String domainLabel, String parameterLabel) {
		return ((ConfigParameterDAO) this.entityDao).findParameter(domainLabel, parameterLabel);
	}
}
