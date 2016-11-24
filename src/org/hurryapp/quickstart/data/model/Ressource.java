package org.hurryapp.quickstart.data.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Ressource extends BaseDataBean {
	
    private String libelle;
	private Set<Groupe> groupes = new LinkedHashSet<Groupe>();

    public Ressource() {
		super();
	}

    public Ressource(Integer id) {
		super(id);
	}
    
    public String getLibelle () {
        return this.libelle;
    }
    public void setLibelle (String value) {
        this.libelle = value; 
    }
	
	public Set<Groupe> getGroupes() {
		return groupes;
	}
	public void setGroupes(Set<Groupe> groupes) {
		this.groupes = groupes;
	}

}
