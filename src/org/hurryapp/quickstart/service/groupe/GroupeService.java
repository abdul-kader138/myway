package org.hurryapp.quickstart.service.groupe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.GroupeDAO;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Ressource;


public class GroupeService extends BaseCrudService {

	public GroupeService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	public Groupe findByLibelle(String libelle) {
		return ((GroupeDAO) super.entityDao).findByLibelle(libelle);
	}
	
	public List<Groupe> findByUtilisateur(Integer utilisateurId) {
		return ((GroupeDAO) super.entityDao).findByUtilisateur(utilisateurId);
	}
	
	public List<Groupe> findByMenu(Integer menuId) {
		return ((GroupeDAO) super.entityDao).findByMenu(menuId);
	}
	
	public List<Groupe> findByRessource(Integer ressourceId) {
		return ((GroupeDAO) super.entityDao).findByRessource(ressourceId);
	}
	
	/**
	 * Associe des ressources à un groupe
	 * @param groupeId
	 * @param ressourcesIds
	 * @throws Exception
	 */
	public void saveRessources(Integer groupeId, List<Integer> ressourcesIds) throws Exception {
		// Récupération du groupe
		if (groupeId != null) {
			Groupe groupe = (Groupe) entityDao.findById(groupeId);

			// Ajout des ressources au groupe
			Set<Ressource> ressources = new HashSet<Ressource>();
			for(Integer ressourceId: ressourcesIds) {
				ressources.add(new Ressource(ressourceId));
			}
			groupe.setRessources(ressources);

			// Mise à jour du groupe
			entityDao.saveOrUpdate(groupe);
		}
	}

}
