package org.hurryapp.quickstart.view.action.login;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.mail.MailService;
import org.hurryapp.quickstart.service.utilisateur.UtilisateurService;
import org.hurryapp.quickstart.utils.CertificateUtils;
import org.hurryapp.quickstart.utils.Md5Utils;
import org.hurryapp.quickstart.utils.ShellUtils;
import org.hurryapp.quickstart.view.AccessUtil;
import org.hurryapp.quickstart.view.action.utilisateur.UtilisateurAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * Class which manages login
 */
@Unsecured // don't check session
//@Secured // ssl
public class LoginAction extends BaseAction<LoginViewBean> {

	@Resource (name="utilisateurService")
	private UtilisateurService utilisateurService;

	@Resource(name = "utilisateurAction")
	private UtilisateurAction utilisateurAction;

	@Resource(name = "mailService")
	private MailService mailService;

	@Override
	protected void doInit() throws Exception {
		//-----------------------------------------------------------------
		// Logo
		//-----------------------------------------------------------------
		String logoUrl = request.getParameter("logo");
		if (StringUtil.isEmpty(logoUrl)) {
			logoUrl = (String) session.get("logoUrl");
			if (StringUtil.isEmpty(logoUrl)) {
				logoUrl = "../images/logo.png";
			}
		}
		session.put("logoUrl", logoUrl);
	}

	@Override
	public void doExecute() throws Exception {
		LoginViewBean loginForm = (LoginViewBean) super.viewBean;

		//-----------------------------------------------------------------
		// Authentification
		//-----------------------------------------------------------------
		Utilisateur user = this.utilisateurService.findByLogin(loginForm.getLogin());
		if (user != null && user.getPassword().equals(loginForm.getPassword())) {
			// Mise en session de l'utilisateur
			AccessUtil.setUtilisateur(this.session, user);

			// Mise en session des projets de l'utilisateur
			/*
			Set<Project> userProjects = user.getProjects();
			if (userProjects != null && userProjects.size() > 0) {
				this.session.put(com.sotouch.myway.Constants.SESSION_KEY_PROJECT_ID, ((Project) userProjects.toArray()[0]).getId());
			}
			else {
				this.session.put(com.sotouch.myway.Constants.SESSION_KEY_PROJECT_ID, -1);
			}
			this.session.put(com.sotouch.myway.Constants.SESSION_KEY_PROJECTS, userProjects);
			*/

			// Nettoyage du menu en session
			this.session.put(Constants.SESSION_KEY_MENU, null);

			Collection<Object> datas = new ArrayList<Object>();

			// Retour de l'utilisateur
			datas.add(utilisateurAction.toViewBean(user));
			
			jsonResponse.setDatas(datas);
			//jsonResponse.setRedirectURL("/utilisateur/init");
		}
		else {
			this.jsonResponse.addError(new JSONError("viewBean.password", super.getTexts("global-messages").getString("login.erreur.password.invalide")));
		}
		jsonResponse.setMessage("locale:" + getLocale());
	}
	
	public String logout() {
		String logoUrl = (String) session.get("logoUrl");
		this.session.clear();
		session.put("logoUrl", logoUrl);
		return LOGIN;
	}

	/**
	 * Send a message when password has been forgotten
	 * @return
	 * @throws Exception
	 */
	public String passwordForgotten() throws Exception {
		Utilisateur user = utilisateurService.findByLogin(((LoginViewBean) viewBean).getLogin());
		if (user != null && !StringUtil.isEmpty(user.getEmail())) {
			mailService.sendMail(
					user.getEmail(),
					getText("common.contactAdmin"),
					getText("login.password.forgotten.message.object"),
					getText("login.password.forgotten.message.content") + user.getPassword());
			
			jsonResponse.setData(user.getEmail());
		}
		else {
			jsonResponse.setData("");
		}
		
		return SUCCESS_JSON;
	}
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================}
}
