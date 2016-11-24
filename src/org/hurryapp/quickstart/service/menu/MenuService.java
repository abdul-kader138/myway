package org.hurryapp.quickstart.service.menu;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseTreeCrudService;
import org.hurryapp.quickstart.data.dao.GroupeDAO;
import org.hurryapp.quickstart.data.dao.RessourceDAO;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Menu;
import org.hurryapp.quickstart.data.model.Ressource;



public class MenuService extends BaseTreeCrudService {

	@Resource (name="ressourceDao")
	protected RessourceDAO ressourceDao;

	@Resource (name="groupeDao")
	protected GroupeDAO groupeDao;


	public MenuService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	/**
	 * Associe des groupes à un menu
	 * @param menuId
	 * @param groupesIds
	 * @throws Exception
	 */
	public void saveGroupes(Integer menuId, List<Integer> groupesIds) throws Exception {
		if (menuId != null) {
			// Récupération du menu sélectionné
			Menu menu = (Menu) entityDao.findById(menuId);
			if (menu != null) {
				// Suppression de toutes les associations entre la ressource du menu et ses groupe
				Ressource ressource = menu.getRessource();
				ressource.setGroupes(new HashSet<Groupe>());
				ressourceDao.saveOrUpdate(ressource);
				ressourceDao.getHibernateTemplate().flush();
				
				// Ajout de la ressource du menu aux groupes sélectionnés
				for(Integer groupeId: groupesIds) {
					Groupe groupe = (Groupe) groupeDao.findById(groupeId);
					if (groupe != null) {
						groupe.getRessources().add(menu.getRessource());
						// Mise à jour du groupe
						groupeDao.saveOrUpdate(groupe);
					}
				}
			}
		}
	}
	
}
