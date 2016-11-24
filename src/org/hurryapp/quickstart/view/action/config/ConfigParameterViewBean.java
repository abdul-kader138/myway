package org.hurryapp.quickstart.view.action.config;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class ConfigParameterViewBean extends BaseViewBean {
	
	private Integer domainId;
	private String libelle;
	private String valeur;
	private String type;

	public Integer getDomainId() {
		return domainId;
	}
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(libelle)) {
			errors.add(new JSONError("viewBean.libelle", resources.getString("config.parameter.erreur.libelle.obligatoire")));
		}

		if (StringUtil.isEmpty(type)) {
			errors.add(new JSONError("viewBean.type", resources.getString("config.parameter.erreur.type.obligatoire")));
		}
		else {
			if (!StringUtil.isEmpty(valeur)) {
				if (type.equals("Integer")) {
					if (NumberUtil.toInteger(valeur) == null) {
						errors.add(new JSONError("viewBean.valeur", resources.getString("config.parameter.erreur.valeur.entier")));
					}
				}
				else if (type.equals("Float")) {
					if (NumberUtil.toDouble(valeur) == null) {
						errors.add(new JSONError("viewBean.valeur", resources.getString("config.parameter.erreur.valeur.reel")));
					}
				} 
			}
		}

		return errors;
	}
	
}
