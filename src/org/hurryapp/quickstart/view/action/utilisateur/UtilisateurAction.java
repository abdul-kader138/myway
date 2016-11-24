package org.hurryapp.quickstart.view.action.utilisateur;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.ActionUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.service.utilisateur.UtilisateurService;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.data.model.Company;
import com.sotouch.myway.service.company.CompanyService;

/**
 * Class which manages users
 */
public class UtilisateurAction extends BaseCrudAction<UtilisateurViewBean> {

	@Resource (name="groupeService")
	protected GroupeService groupeService;

	@Resource (name="companyService")
	protected CompanyService companyService;

	@Override
	protected void doInit() throws Exception {
	}

	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) throws Exception {
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) viewBean;
		criteriaMap.put("login", userViewBean.getLogin());
		criteriaMap.put("nom", userViewBean.getNom());
		criteriaMap.put("company.id", userViewBean.getCompanyId());
		criteriaMap.put("projects.id", userViewBean.getParentId());
	}


	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) viewBean;
		Utilisateur user = (Utilisateur) dataBean;
		user.setLogin(userViewBean.getLogin());
		user.setNom(userViewBean.getNom());
		user.setPrenom(userViewBean.getPrenom());
		user.setEmail(userViewBean.getEmail());
		user.setPhone(userViewBean.getPhone());
		if (!StringUtil.isEmpty(userViewBean.getChangePassword())) {
			user.setPassword(userViewBean.getPassword());
		}
		if (userViewBean.getCompanyId() != null) {
			user.setCompany((Company) companyService.findById(userViewBean.getCompanyId()));
		}
		if (userViewBean.getGroupeId() != null) {
			user.setGroupe((Groupe) groupeService.findById(userViewBean.getGroupeId()));
		}
		
		// Groupes
		if (userViewBean.getGroupeIds() != null) {
			List<Integer> groupeIds = ActionUtil.toListIds(userViewBean.getGroupeIds());
			Set<Groupe> groupes = new HashSet<Groupe>();
			for(Integer groupeId: groupeIds) {
				groupes.add(new Groupe(groupeId));
			}
			user.setGroupes(groupes);
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Utilisateur user = (Utilisateur) dataBean;
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) viewBean;
		userViewBean.setLogin(user.getLogin());
		userViewBean.setPassword(user.getPassword());
		userViewBean.setNom(user.getNom());
		userViewBean.setPrenom(user.getPrenom());
		userViewBean.setEmail(user.getEmail());
		userViewBean.setPhone(user.getPhone());
		if (user.getGroupe() != null) {
			userViewBean.setGroupeId(user.getGroupe().getId());
			userViewBean.setGroupe(user.getGroupe().getLibelle());
		}
		if (user.getCompany() != null) {
			userViewBean.setCompanyId(user.getCompany().getId());
			userViewBean.setCompany(user.getCompany().getName());
		}
	}

	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// Si l'utilisateur existe déjà
		if (e instanceof DataIntegrityViolationException) {
			// Messages d'erreur
			jsonResponse.addError(new JSONError("viewBean.login", resources.getString("utilisateur.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}


	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	/**
	 * Associe les groupes à l'utilisateur sélectionné
	 */
	public String saveGroupes() throws Exception {
		// Récupération de l'utilisateur
		UtilisateurViewBean utilisateurViewBean = (UtilisateurViewBean) viewBean;
		Integer utilisateurId = utilisateurViewBean.getId();
		List<Integer> groupesIds = ActionUtil.toListIds(utilisateurViewBean.getGroupeIds());

		// Mise à jour des groupes de l'utilisateur
		((UtilisateurService) entityService).saveGroupes(utilisateurId, groupesIds);

		// Message de succés
		jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));

		return SUCCESS_JSON;
	}

	/**
	 */
	public String loadByProject() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((UtilisateurService) entityService).findByProject(viewBean.getParentId())));
		return SUCCESS_JSON;
	}

	/**
	 */
	public String loadByCompanyNotInProject() throws Exception {
		UtilisateurViewBean utilisateurViewBean = (UtilisateurViewBean) viewBean;
		jsonResponse = new JSONResponse(toViewBeans(((UtilisateurService) entityService).findByCompanyNotInProject(utilisateurViewBean.getCompanyId(), projectId, utilisateurViewBean.getNom())));
		return SUCCESS_JSON;
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================

}
