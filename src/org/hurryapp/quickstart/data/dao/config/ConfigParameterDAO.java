package org.hurryapp.quickstart.data.dao.config;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.config.ConfigParameter;

public class ConfigParameterDAO extends BaseDAO<ConfigParameter> {

	public ConfigParameterDAO() {
		super();
	}

	public Object findParameter(String domainLabel, String parameterLabel) {
		ConfigParameter parameter = (ConfigParameter) super.findUniqueResultByNamedQuery("select.configParameter.by.domain.and.libelle", new Object[] { domainLabel, parameterLabel });

		Object value = null;
		if (parameter != null) {
			if ("Integer".equals(parameter.getType())) {
				value = Integer.valueOf(parameter.getValeur());
			}
			else if ("Float".equals(parameter.getType())) {
				value = Double.valueOf(parameter.getValeur());
			}
			else {
				value = parameter.getValeur();
			}
		}

		return value;
	}
}
