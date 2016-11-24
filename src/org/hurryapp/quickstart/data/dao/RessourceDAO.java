package org.hurryapp.quickstart.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Ressource;

public class RessourceDAO extends BaseDAO<Ressource> {

	public RessourceDAO() {
		super();
	}

	public Ressource findByLibelle(String libelle) {
		return (Ressource) super.findUniqueResultByNamedQuery("select.ressource.by.libelle", new Object[] { libelle });
	}

	public List<Ressource> findByGroupe(Integer groupeId) {
		return super.findByNamedQuery("select.ressource.by.groupe", new Object[] {groupeId});
	}
}
