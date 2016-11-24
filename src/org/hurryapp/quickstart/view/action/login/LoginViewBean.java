package org.hurryapp.quickstart.view.action.login;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class LoginViewBean extends BaseViewBean {

	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(login)) {
			errors.add(new JSONError("viewBean.login", resources.getString("utilisateur.erreur.login.obligatoire")));
		}

		if (StringUtil.isEmpty(password)) {
			errors.add(new JSONError("viewBean.password", resources.getString("utilisateur.erreur.password.obligatoire")));
		}
		
		return errors;
	}

}
