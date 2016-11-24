package org.hurryapp.quickstart.data.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;

import com.sotouch.myway.data.model.Company;
import com.sotouch.myway.data.model.Project;

/**
 * User data bean
 * @hibernate.class
 * table="utilisateur"
 */
public class Utilisateur extends BaseDataBean {

	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	private String phone;
	private Set<Groupe> groupes = new LinkedHashSet<Groupe>();
	private Groupe groupe;
	private Company company;
	private Set<Project> projects = new LinkedHashSet<Project>();

	public Utilisateur() {
		super();
	}

	public Utilisateur(Integer id) {
		super(id);
	}

	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Groupe> getGroupes() {
		return this.groupes;
	}
	public void setGroupes(Set<Groupe> groupes) {
		this.groupes = groupes;
	}
	
	public Groupe getGroupe() {
		return groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public String getFullNameTruncated(int nbCharacters) {
		String fullName = "";
		fullName += prenom != null ? prenom : "";
		fullName += !fullName.equals("") ? " " : "";
		fullName += nom != null ? nom : "";
		return fullName.length() > nbCharacters ? fullName.substring(0, nbCharacters)+"..." : fullName;
	}

	/*
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (this.login == null ? 0 : this.login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Utilisateur other = (Utilisateur) obj;
		if (this.login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!this.login.equals(other.login)) {
			return false;
		}
		return true;
	}
	*/
}
