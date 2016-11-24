package org.hurryapp.quickstart.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Utilisateur;

public class UtilisateurDAO extends BaseDAO<Utilisateur> {

	public UtilisateurDAO() {
		super();
	}

	public Utilisateur findByLogin(String login) {
		Utilisateur ut=(Utilisateur) super.findUniqueResultByNamedQuery("select.utilisateur.by.login", new Object[] {login});
		return (Utilisateur) super.findUniqueResultByNamedQuery("select.utilisateur.by.login", new Object[] {login});
	}

	public List<Utilisateur> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.utilisateur.by.project", new Object[] {projectId});
	}

	public Utilisateur findFirstAdminByCompany(Integer companyId) {
		Utilisateur user = null;
		List<Utilisateur> users = super.findByNamedQuery("select.utilisateur.admins.by.company", new Object[] {companyId});
		if (users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}
}
