package org.hurryapp.quickstart.service.utilisateur;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sotouch.myway.data.model.Company;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.UtilisateurDAO;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Utilisateur;

public class UtilisateurService extends BaseCrudService {

	public UtilisateurService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	public Utilisateur findByLogin(String login) {
		Utilisateur ut=new Utilisateur();
		ut.setPhone("");
		ut.setEmail("");
		ut.setPassword("sh");
		ut.setLogin("sh");
//		Company cm=new Company();
//		return ut;
		return ((UtilisateurDAO) super.entityDao).findByLogin(login);
	}

	public List<Utilisateur> findByProject(Integer projectId) {
		return ((UtilisateurDAO) super.entityDao).findByProject(projectId);
	}

	public List<Utilisateur> findByCompanyNotInProject(Integer companyId, Integer projectId, String nom) {
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("company.id", companyId);
		criteriaMap.put("nom", nom);
		List<Utilisateur> usersCompany = ((UtilisateurDAO) super.entityDao).findPageByCriteria(criteriaMap, "nom", null);
		List<Utilisateur> usersProject = ((UtilisateurDAO) super.entityDao).findByProject(projectId);
		usersCompany.removeAll(usersProject);
		return usersCompany;
	}

	public Utilisateur findFirstAdminByCompany(Integer companyId) {
		return ((UtilisateurDAO) super.entityDao).findFirstAdminByCompany(companyId);
	}

	/**
	 * Associe des groupes � un utilisateur
	 * @param utilisateurId
	 * @param groupesIds
	 * @throws Exception
	 */
	public void saveGroupes(Integer utilisateurId, List<Integer> groupesIds) throws Exception {
		// R�cup�ration du groupe
		if (utilisateurId != null) {
			Utilisateur user = (Utilisateur) entityDao.findById(utilisateurId);

			// Ajout des groupes � l'utilisateur
			Set<Groupe> groupes = new HashSet<Groupe>();
			for(Integer groupeId: groupesIds) {
				groupes.add(new Groupe(groupeId));
			}
			user.setGroupes(groupes);

			// Mise � jour de l'utilisateur
			entityDao.saveOrUpdate(user);
		}
	}

}
