package org.hurryapp.quickstart.view.action.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseTreeViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class MenuViewBean extends BaseTreeViewBean {
	
	private String libelle;
	private String text;
	private String url;
	private String groupeIds;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getGroupeIds() {
		return groupeIds;
	}
	public void setGroupeIds(String groupeIds) {
		this.groupeIds = groupeIds;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(libelle)) {
			errors.add(new JSONError("viewBean.libelle", resources.getString("menu.erreur.libelle.obligatoire")));
		}

		if (url != null && url.length() > 0) {
			if (url.length() < 2) {
				errors.add(new JSONError("viewBean.url", resources.getString("menu.erreur.url.size")));
			}
			else if (!url.startsWith("/")) {
				errors.add(new JSONError("viewBean.url", resources.getString("menu.erreur.url.slash")));
			}
		}

		return errors;
	}
	
}
