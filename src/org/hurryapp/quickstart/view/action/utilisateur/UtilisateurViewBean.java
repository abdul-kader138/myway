package org.hurryapp.quickstart.view.action.utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class UtilisateurViewBean extends BaseViewBean {

	private String login;
	private String changePassword;
	private String password;
	private String passwordConfirmed;
	private String nom;
	private String prenom;
	private String email;
	private String phone;
	private Integer groupeId;
	private String groupe;
	private String groupeIds;
	private Integer companyId;
	private String company;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getChangePassword() {
		return changePassword;
	}
	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirmed() {
		return passwordConfirmed;
	}
	public void setPasswordConfirmed(String passwordConfirmed) {
		this.passwordConfirmed = passwordConfirmed;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getGroupeId() {
		return groupeId;
	}
	public void setGroupeId(Integer groupeId) {
		this.groupeId = groupeId;
	}
	
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getGroupeIds() {
		return groupeIds;
	}
	public void setGroupeIds(String groupeIds) {
		this.groupeIds = groupeIds;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Override
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();

		if (StringUtil.isEmpty(login)) {
			errors.add(new JSONError("viewBean.login", resources.getString("utilisateur.erreur.login.obligatoire")));
		}

		if (!StringUtil.isEmpty(email) && !StringUtil.isEMail(email)) {
			errors.add(new JSONError("viewBean.email", resources.getString("utilisateur.erreur.email.invalide")));
		}

		if (groupeId == null) {
			errors.add(new JSONError("viewBean.groupeId", resources.getString("utilisateur.erreur.groupe.obligatoire")));
		}

		if (!StringUtil.isEmpty(changePassword)) {
			if (!password.equals(passwordConfirmed)) {
				errors.add(new JSONError("viewBean.passwordConfirmed", resources.getString("utilisateur.erreur.password.invalide")));
			}
			else {
				if (StringUtil.isEmpty(password)) {
					errors.add(new JSONError("viewBean.password", resources.getString("utilisateur.erreur.password.obligatoire")));
				}
			}
		}

		return errors;
	}

}
