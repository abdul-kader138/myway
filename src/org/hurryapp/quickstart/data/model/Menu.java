package org.hurryapp.quickstart.data.model;

import org.hurryapp.fwk.data.model.BaseTreeNode;


/**
 * @hibernate.class table="menu"
 */
public class Menu extends BaseTreeNode {

	private String libelle;
	private String url;
	private Ressource ressource;
	
	public Menu() {
	}

	public Menu(Integer id) {
		super(id);
	}

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Ressource getRessource() {
		return ressource;
	}
	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

}
