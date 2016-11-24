package org.hurryapp.quickstart.view;

import java.util.Map;

import org.hurryapp.fwk.view.exception.NotLoggedException;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Ressource;
import org.hurryapp.quickstart.data.model.Utilisateur;

/**
 */
public class AccessUtil {

	public static Utilisateur getUtilisateur(Map<String, Object> session) throws NotLoggedException {
		Utilisateur user = (Utilisateur) session.get(Constants.SESSION_KEY_USER);
		if (user == null) {
			throw new NotLoggedException("User not logged");
		}
		return user;
	}

	public static void setUtilisateur(Map<String, Object> session, Utilisateur user) {
		session.put(Constants.SESSION_KEY_USER, user);
	}

	public static boolean canAccessResource(Utilisateur user, String ressource) {
		if (ressource == null) {
			return false;
		}
		else {
			if (user.getGroupe() != null) {
				for(Ressource ressourceTmp: user.getGroupe().getRessources()) {
					if (ressourceTmp.getLibelle().equals(ressource)) {
						return true;
					}
				}
			}
			/*
			for(Groupe groupe: user.getGroupes()) {
				for(Ressource ressourceTmp: groupe.getRessources()) {
					if (ressourceTmp.getLibelle().equals(ressource)) {
						return true;
					}
				}
			}
			*/
			return false;
		}
	}

	public static boolean canAccessGroup(Utilisateur user, Integer groupeId) {
		if (groupeId == null) {
			return false;
		}
		else {
			if (user.getGroupe() != null && user.getGroupe().getId().equals(groupeId)) {
				return true;
			}
			/*
			for(Groupe groupeTmp: user.getGroupes()) {
				if (groupeTmp.getId().equals(groupe)) {
					return true;
				}
			}
			*/
			return false;
		}
	}
}
