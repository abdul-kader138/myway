package org.hurryapp.quickstart.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Groupe;

public class GroupeDAO extends BaseDAO<Groupe> {

	public GroupeDAO() {
		super();
	}

	public Groupe findByLibelle(String libelle) {
		return (Groupe) super.findUniqueResultByNamedQuery("select.groupe.by.libelle", new Object[] { libelle });
	}

	public List<Groupe> findByUtilisateur(Integer utilisateurId) {
		return super.findByNamedQuery("select.groupe.by.utilisateur", new Object[] {utilisateurId});
	}

	public List<Groupe> findByMenu(Integer menuId) {
		return super.findByNamedQuery("select.groupe.by.menu", new Object[] {menuId});
	}

	public List<Groupe> findByRessource(Integer ressourceId) {
		return super.findByNamedQuery("select.groupe.by.ressource", new Object[] {ressourceId});
	}

}
