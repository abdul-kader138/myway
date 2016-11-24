package org.hurryapp.quickstart.view.action.groupe;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hurryapp.fwk.view.ActionUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Ressource;
import org.hurryapp.quickstart.service.groupe.GroupeService;

/**
 * Class which manages user groups
 */
public class GroupeAction extends BaseCrudAction<GroupeViewBean> {

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		GroupeViewBean groupeViewBean = (GroupeViewBean) viewBean;
		criteriaMap.put("libelle", groupeViewBean.getLibelle());
	}

	@Override
	protected String getDefaultSort() {
		return "libelle";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		GroupeViewBean groupeViewBean = (GroupeViewBean) viewBean;
		Groupe groupe = (Groupe) dataBean;
		groupe.setLibelle(groupeViewBean.getLibelle());
		
		// Ressources
		if (groupeViewBean.getRessourceIds() != null) {
			List<Integer> ressourcesIds = ActionUtil.toListIds(groupeViewBean.getRessourceIds());
			Set<Ressource> ressources = new HashSet<Ressource>();
			for(Integer ressourceId: ressourcesIds) {
				ressources.add(new Ressource(ressourceId));
			}
			groupe.setRessources(ressources);
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Groupe groupe = (Groupe) dataBean;
		GroupeViewBean groupeViewBean = (GroupeViewBean) viewBean;
		groupeViewBean.setLibelle(groupe.getLibelle());
	}
	
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	/**
	 * Associe les ressources au groupe sélectionné
	 */
	public String saveRessources() throws Exception {
		// Récupération du groupe
		GroupeViewBean groupeViewBean = (GroupeViewBean) viewBean;
		Integer groupeId = groupeViewBean.getId();
		List<Integer> ressourcesIds = ActionUtil.toListIds(groupeViewBean.getRessourceIds());

		// Mise à jour des ressources du groupe
		((GroupeService) entityService).saveRessources(groupeId, ressourcesIds);
		
		// Message de succés
		jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));

		return SUCCESS_JSON;
	}

	/**
	 */
	public String loadByUtilisateur() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((GroupeService) entityService).findByUtilisateur(viewBean.getParentId())));
		return SUCCESS_JSON;
	}

	/**
	 */
	public String loadByMenu() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((GroupeService) entityService).findByMenu(viewBean.getParentId())));
		return SUCCESS_JSON;
	}

	/**
	 */
	public String loadByRessource() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((GroupeService) entityService).findByRessource(viewBean.getParentId())));
		return SUCCESS_JSON;
	}


	//=========================================================================
	// ACCESSORS
	//=========================================================================

}
