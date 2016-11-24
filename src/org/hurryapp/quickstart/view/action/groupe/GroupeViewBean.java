package org.hurryapp.quickstart.view.action.groupe;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class GroupeViewBean extends BaseViewBean {
	
	private String libelle;
	private String ressourceIds;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getRessourceIds() {
		return ressourceIds;
	}
	public void setRessourceIds(String ressourceIds) {
		this.ressourceIds = ressourceIds;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(libelle)) {
			errors.add(new JSONError("viewBean.libelle", resources.getString("groupe.erreur.libelle.obligatoire")));
		}

		return errors;
	}
	
}
