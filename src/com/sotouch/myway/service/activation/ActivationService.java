package com.sotouch.myway.service.activation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.utilisateur.UtilisateurService;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Company;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.company.CompanyService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.project.ProjectService;

public class ActivationService {

	@Resource (name="companyService")
	private CompanyService companyService;

	@Resource (name="projectService")
	private ProjectService projectService;

	@Resource (name="licenseService")
	private LicenseService licenseService;

	@Resource (name="utilisateurService")
	private UtilisateurService utilisateurService;

	/**
	 * Création de projets pour une company
	 * @param companyId
	 * @param companyName
	 * @param name
	 * @param surname
	 * @param projectNames
	 * @throws Exception
	 */
	public String createProjects(String companyId, String companyName, String name, String surname, String projectNames) throws Exception {
		Document document = DocumentHelper.createDocument();
		try {
			//---------------------------------------------------------------------
			// Company
			//---------------------------------------------------------------------
			Company company = null;
			if (StringUtil.isEmpty(companyId)) {
				// Création de la company
				company = new Company();
				company.setName(companyName);
				try {
					company = (Company) companyService.saveOrUpdate(company);
				}
				catch (DataIntegrityViolationException e) {
					throw new ActivationException("Company '"+companyName+"' already exists");
				}
			}
			else {
				// Récupération de la company
				company = companyService.findByIdStr(companyId);
			}
			
			//---------------------------------------------------------------------
			// User admin
			//---------------------------------------------------------------------
			String loginAdmin = null;
			String passwordAdmin = null;
			Utilisateur user = utilisateurService.findFirstAdminByCompany(company.getId());
			if (user == null) {
				if (StringUtil.isEmpty(name) || StringUtil.isEmpty(surname)) {
					throw new ActivationException("Name and surname required");
				}
				loginAdmin = name.replaceAll(" ", "") + surname.replaceAll(" ", "");
				passwordAdmin = getRandomPassword();
	
				user = new Utilisateur();
				user.setCompany(company);
				user.setGroupe(new Groupe(Constants.GROUPE_ADMIN));
				user.setNom(surname);
				user.setPrenom(name);
				user.setLogin(loginAdmin);
				user.setPassword(passwordAdmin);
				int i = 1;
				while (true) {
					try {
						user = (Utilisateur) utilisateurService.saveOrUpdate(user);
						break;
					}
					catch (DataIntegrityViolationException e) {
						loginAdmin = loginAdmin + i;
						user.setLogin(loginAdmin);
						i++;
					}
				}
			}
			else {
				loginAdmin = user.getLogin();
				passwordAdmin = user.getPassword();
			}
			
			//---------------------------------------------------------------------
			// Projects et terminals
			//---------------------------------------------------------------------
			Project project = null;
			License license = null;
			long seconde = 1000;
			long minute = 60 * seconde;
			long hour = 60 * minute;
			long day = 24 * hour;
			String[] projectNamesTab = !StringUtil.isEmpty(projectNames) ? (projectNames+" ").split(",") : new String[]{};
			for (int i = 0; i < projectNamesTab.length; i++) {
				// Project
				project = new Project();
				project.setCompany(company);
				project.setKey(projectService.generateProjectKey());
				project.setName(projectNamesTab[i]);
				project.setEnabled(Boolean.TRUE);
				Set<Utilisateur> users = new HashSet<Utilisateur>();
				users.add(user);
				project.setUsers(users);
				Date date = new Date(System.currentTimeMillis()+(day*365));
				project.setDateExpire(date);
				project = (Project) projectService.saveOrUpdate(project);
				
				// Terminal
				license = new License();
				license.setProject(project);
				license.setKey("Terminal 1");
				license.setDescription("");
				license.setOrientation("north");
				licenseService.saveOrUpdate(license);
			}
			
			// Retour du login et du mot de passe de l'admin
			Element rootElt = document.addElement("success");
			Element elt = rootElt.addElement("login");
			elt.setText(loginAdmin);
			elt = rootElt.addElement("password");
			elt.setText(passwordAdmin);
		}
		catch (ActivationException e) {
			Element elt = document.addElement("error");
			elt.setText(e.getMessage());
		}
			
		return document.asXML();
	}

	/**
	 * Création de terminaux pour un projet
	 * @param projecyKey
	 * @param names
	 * @param descriptions
	 * @throws Exception
	 */
	public String createTerminals(String projecyKey, String names, String descriptions) throws Exception {
		Document document = DocumentHelper.createDocument();
		try {
			//---------------------------------------------------------------------
			// Projects et terminals
			//---------------------------------------------------------------------
			Project project = projectService.findByKey(projecyKey);
			if (project != null) {
				License license = null;
				String[] namesTab = !StringUtil.isEmpty(names) ? (names+" ").split(",") : new String[]{};
				String[] descriptionsTab = !StringUtil.isEmpty(descriptions) ? descriptions.split(",") : new String[]{};
				for (int i = 0; i < namesTab.length; i++) {
					// Terminal
					String terminalName = namesTab[i];
					license = new License();
					license.setProject(project);
					license.setKey(terminalName);
					license.setDescription(i < descriptionsTab.length ? descriptionsTab[i] : "");
					license.setOrientation("north");
					try {
						licenseService.saveOrUpdate(license);
					}
					catch (DataIntegrityViolationException e) {
						throw new ActivationException("Terminal '"+terminalName+"' already exists");
					}
				}
			}
			else {
				throw new ActivationException("Project '"+projecyKey+"' doesn't exist");
			}

			document.addElement("success");
		}
		catch (ActivationException e) {
			Element elt = document.addElement("error");
			elt.setText(e.getMessage());
		}
		
		return document.asXML();
	}

	/**
	 * Activation des ads pour un projet
	 * @param projecyKey
	 * @param expirationDate
	 * @param descriptions
	 * @throws Exception
	 */
	public String activateAds(String projecyKey, String expirationDate) throws Exception {
		Document document = DocumentHelper.createDocument();
		try {
			//---------------------------------------------------------------------
			// Projects et terminals
			//---------------------------------------------------------------------
			Project project = projectService.findByKey(projecyKey);
			if (project != null) {
				Date date = DateUtil.toDate(expirationDate, "yyyy-MM-dd");
				if (date == null) {
					throw new ActivationException("Date format invalid");
				}
				project.setAdsDateExpire(date);
				projectService.saveOrUpdate(project);
			}
			else {
				throw new ActivationException("Project '"+projecyKey+"' doesn't exist");
			}

			document.addElement("success");
		}
		catch (ActivationException e) {
			Element elt = document.addElement("error");
			elt.setText(e.getMessage());
		}
		
		return document.asXML();
	}

	/*
	private String getRandomSuffix() {
		String password = "";
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 4; i++) {
			int character = (int)(Math.random()*36);
			password += alphabet.substring(character, character+1);
		}
		return password;
	}
	*/

	private String getRandomPassword() {
		String password = "";
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 6; i++) {
			int character = (int)(Math.random()*36);
			password += alphabet.substring(character, character+1);
		}
		return password;
	}
}
