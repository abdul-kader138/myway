package org.hurryapp.quickstart.view.action.config;

import java.util.List;

import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.quickstart.data.model.config.ConfigDomain;

/**
 * Class which manages config domains
 */
public class ConfigDomainAction extends BaseCrudAction<ConfigDomainViewBean> {
	
	@RemoteProperty(jsonAdapter="org.hurryapp.fwk.view.json.adapter.LibelleIdJSONAdapter")
	private List domains; 

	@Override
	protected void doInit() throws Exception {
		domains = entityService.findAll();
	}
	
	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		ConfigDomainViewBean domainViewBean = (ConfigDomainViewBean) viewBean;
		ConfigDomain domain = (ConfigDomain) dataBean;
		domain.setLibelle(domainViewBean.getLibelle());
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		ConfigDomain domain = (ConfigDomain) dataBean;
		ConfigDomainViewBean domainViewBean = (ConfigDomainViewBean) viewBean;
		domainViewBean.setLibelle(domain.getLibelle());
	}

	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================


	//=========================================================================
	// ACCESSEURS
	//=========================================================================
	
	public List getDomains() {
		return domains;
	}
	public void setDomains(List domains) {
		this.domains = domains;
	}

}
