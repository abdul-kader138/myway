package org.hurryapp.quickstart.view.action.config;

import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.model.config.ConfigDomain;
import org.hurryapp.quickstart.data.model.config.ConfigParameter;
import org.hurryapp.quickstart.service.config.ConfigDomainService;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Class which manages config parameters
 */
public class ConfigParameterAction extends BaseCrudAction<ConfigParameterViewBean> {

	@Resource (name="configDomainService")
	protected ConfigDomainService domainService;

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		ConfigParameterViewBean paramViewBean = (ConfigParameterViewBean) viewBean;
		criteriaMap.put("domain.id", paramViewBean.getDomainId());
		criteriaMap.put("libelle", paramViewBean.getLibelle());
	}

	@Override
	protected String getDefaultSort() {
		return "libelle";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		ConfigParameterViewBean paramViewBean = (ConfigParameterViewBean) viewBean;
		ConfigParameter param = (ConfigParameter) dataBean;
		param.setLibelle(paramViewBean.getLibelle());
		param.setValeur(paramViewBean.getValeur());
		param.setType(paramViewBean.getType());
		if (paramViewBean.getDomainId() != null) {
			param.setDomain((ConfigDomain) domainService.findById(paramViewBean.getDomainId()));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		ConfigParameter param = (ConfigParameter) dataBean;
		ConfigParameterViewBean paramViewBean = (ConfigParameterViewBean) viewBean;
		paramViewBean.setLibelle(param.getLibelle());
		paramViewBean.setValeur(param.getValeur());
		paramViewBean.setType(param.getType());
		paramViewBean.setDomainId(param.getDomain().getId());
	}
	
	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// Si le paramètre existe déjà
		if (e instanceof DataIntegrityViolationException) {
			// Messages d'erreur
			jsonResponse.addError(new JSONError("viewBean.libelle", resources.getString("config.parameter.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}

	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================


	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}
