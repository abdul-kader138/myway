package org.hurryapp.quickstart.data.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Groupe extends BaseDataBean {
	
    private String libelle;
	private Set<Utilisateur> utilisateurs = new LinkedHashSet<Utilisateur>();
	private Set<Ressource> ressources = new LinkedHashSet<Ressource>();

    public Groupe() {
		super();
	}

    public Groupe(Integer id) {
		super(id);
	}
    
    public String getLibelle () {
        return this.libelle;
    }
    public void setLibelle (String value) {
        this.libelle = value; 
    }
	
	public Set<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Set<Ressource> getRessources() {
		return ressources;
	}
	public void setRessources(Set<Ressource> ressources) {
		this.ressources = ressources;
	}

}
