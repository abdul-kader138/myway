package org.hurryapp.quickstart.data.model.config;

import org.hurryapp.fwk.data.model.BaseDataBean;


/**
 * @hibernate.class table="config_parameter"
 */
public class ConfigParameter extends BaseDataBean {

	private String libelle;
	private String valeur;
	private String type;
	private ConfigDomain domain;
	
	public ConfigParameter() {
	}

	public ConfigParameter(Integer id) {
		super(id);
	}

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public ConfigDomain getDomain() {
		return domain;
	}
	public void setDomain(ConfigDomain domain) {
		this.domain = domain;
	}
}
