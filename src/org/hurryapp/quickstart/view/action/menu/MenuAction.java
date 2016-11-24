package org.hurryapp.quickstart.view.action.menu;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.ActionUtil;
import org.hurryapp.fwk.view.action.BaseTreeCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.model.Menu;
import org.hurryapp.quickstart.data.model.Ressource;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.service.menu.MenuService;
import org.hurryapp.quickstart.service.ressource.RessourceService;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Class which manages menus
 */
public class MenuAction extends BaseTreeCrudAction<MenuViewBean> {
	
	@Resource (name="ressourceService")
	protected RessourceService ressourceService;

	@Resource (name="groupeService")
	protected GroupeService groupeService;

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		// Critères de recherche
		MenuViewBean menuViewBean = (MenuViewBean) viewBean;
		criteriaMap.put("libelle", menuViewBean.getLibelle());
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		MenuViewBean menuViewBean = (MenuViewBean) viewBean;
		Menu menu = (Menu) dataBean;
		menu.setParent(new Menu(menuViewBean.getParentId()));
		menu.setLibelle(menuViewBean.getLibelle());
		menu.setUrl(menuViewBean.getUrl());

		// Création ou mise à jour de la ressource associée au menu
		String libelleRessource = "menu."+menu.getLibelle().toLowerCase();
		Ressource ressource = menu.getRessource();
		if (ressource == null) {
			ressource = new Ressource();
		}
		ressource.setLibelle(libelleRessource);
		menu.setRessource(ressource);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Menu menu = (Menu) dataBean;
		MenuViewBean menuViewBean = (MenuViewBean) viewBean;
		menuViewBean.setParentId((menu.getParent().getId()));
		menuViewBean.setText(menu.getLibelle());
		menuViewBean.setLibelle(menu.getLibelle());
		menuViewBean.setUrl(menu.getUrl());
		if (!StringUtil.isEmpty(menu.getUrl())) {
			//menuViewBean.setLeaf(Boolean.TRUE);
			menuViewBean.setLoaded(Boolean.TRUE);
			menuViewBean.setExpanded(Boolean.TRUE);
		}
		else {
			//menuViewBean.setLeaf(Boolean.FALSE);
			menuViewBean.setLoaded(Boolean.FALSE);
			menuViewBean.setExpanded(Boolean.FALSE);
		}
	}

	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// Si le menu existe déjà
		if (e instanceof DataIntegrityViolationException) {
			// Messages d'erreur
			jsonResponse.addError(new JSONError("viewBean.libelle", resources.getString("menu.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}

	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	/**
	 * Associe les groupes au menu sélectionné
	 */
	public String saveGroupes() throws Exception {
		// Récupération du menu sélectionné
		MenuViewBean menuViewBean = (MenuViewBean) viewBean;
		Integer menuId = menuViewBean.getId();
		List<Integer> groupesIds = ActionUtil.toListIds(menuViewBean.getGroupeIds());

		// Mise à jour des groupes du menu
		((MenuService) entityService).saveGroupes(menuId, groupesIds);

		// Message de succés
		jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));

		return SUCCESS_JSON;
	}

	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}
