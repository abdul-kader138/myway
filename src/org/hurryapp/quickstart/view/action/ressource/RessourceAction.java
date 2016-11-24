package org.hurryapp.quickstart.view.action.ressource;

import java.util.Map;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.data.model.Ressource;
import org.hurryapp.quickstart.service.ressource.RessourceService;

/**
 * Class which manages resources (used for rights management)
 */
public class RessourceAction extends BaseCrudAction<RessourceViewBean> {
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		RessourceViewBean ressourceViewBean = (RessourceViewBean) viewBean;
		criteriaMap.put("libelle", ressourceViewBean.getLibelle());
	}

	@Override
	protected String getDefaultSort() {
		return "libelle";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		RessourceViewBean ressourceViewBean = (RessourceViewBean) viewBean;
		Ressource ressource = (Ressource) dataBean;
		ressource.setLibelle(ressourceViewBean.getLibelle());
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Ressource ressource = (Ressource) dataBean;
		RessourceViewBean ressourceViewBean = (RessourceViewBean) viewBean;
		ressourceViewBean.setLibelle(ressource.getLibelle());
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	/**
	 */
	@SuppressWarnings("unchecked")
	public String loadByGroupe() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((RessourceService) entityService).findByGroupe(viewBean.getParentId())));
		return SUCCESS_JSON;
	}

	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}
