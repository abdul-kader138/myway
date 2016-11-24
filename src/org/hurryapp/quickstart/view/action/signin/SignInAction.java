package org.hurryapp.quickstart.view.action.signin;

import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.service.mail.MailService;
import org.hurryapp.quickstart.view.action.utilisateur.UtilisateurViewBean;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Class which manages user signin
 */
@Unsecured
public class SignInAction extends BaseCrudAction<UtilisateurViewBean> {

	@Resource (name="groupeService")
	private GroupeService groupeService;

	@Resource (name="mailService")
	private MailService mailService;

	@Override
	protected void doInit() throws Exception {
	}

	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) this.viewBean;
		criteriaMap.put("login", userViewBean.getLogin());
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) viewBean;
		Utilisateur user = (Utilisateur) dataBean;
		user.setLogin(userViewBean.getLogin());
		user.setNom(userViewBean.getNom());
		user.setPrenom(userViewBean.getPrenom());
		if (!StringUtil.isEmpty(userViewBean.getChangePassword())) {
			user.setPassword(userViewBean.getPassword());
		}
		user.getGroupes().add(this.groupeService.findByLibelle("Utilisateur"));
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Utilisateur user = (Utilisateur) dataBean;
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) viewBean;
		userViewBean.setLogin(user.getLogin());
		userViewBean.setPassword(user.getPassword());
		userViewBean.setNom(user.getNom());
		userViewBean.setPrenom(user.getPrenom());
	}
	@Override
	protected void doAfterSave(Object dataBean) throws Exception {
		this.jsonResponse.setMessage(super.getText("signin.succes"));

		// Envoi du mail de confirmation
		UtilisateurViewBean userViewBean = (UtilisateurViewBean) this.viewBean;
		this.mailService.sendMail(
				super.getText("signin.mail.from"),
				userViewBean.getLogin(),
				super.getText("signin.mail.subject", super.getText("application")),
				super.getText(
						"signin.mail.content",
						new String[] {
								super.getText("application"),
								userViewBean.getLogin(),
								userViewBean.getPassword(),
								super.getText("application.url"),
								super.getText("application")
						}
				)
		);
	}

	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// Si l'utilisateur existe déjà
		if (e instanceof DataIntegrityViolationException) {
			// Messages d'erreur
			this.jsonResponse.addError(new JSONError("viewBean.login", this.resources.getString("utilisateur.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}

	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================

}
