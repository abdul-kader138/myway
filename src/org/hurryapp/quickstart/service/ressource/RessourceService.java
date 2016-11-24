package org.hurryapp.quickstart.service.ressource;

import java.util.List;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.RessourceDAO;
import org.hurryapp.quickstart.data.model.Ressource;


public class RessourceService extends BaseCrudService {

	public RessourceService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	public Ressource findByLibelle(String libelle) {
		return ((RessourceDAO)super.entityDao).findByLibelle(libelle);
	}

	public List<Ressource> findByGroupe(Integer groupeId) {
		return ((RessourceDAO)super.entityDao).findByGroupe(groupeId);
	}
}
