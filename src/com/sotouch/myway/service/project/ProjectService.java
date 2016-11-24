package com.sotouch.myway.service.project;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;
import org.hurryapp.quickstart.data.model.Utilisateur;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.IconDAO;
import com.sotouch.myway.data.dao.IconGroupDAO;
import com.sotouch.myway.data.dao.ItemTypeDAO;
import com.sotouch.myway.data.dao.ProjectDAO;
import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.IconGroup;
import com.sotouch.myway.data.model.ItemType;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.language.LanguageService;


public class ProjectService extends BaseCrudService {

	@Resource (name="languageService")
	private LanguageService languageService;

	@Resource (name="itemTypeDao")
	private ItemTypeDAO itemTypeDao;

	@Resource (name="iconGroupDao")
	private IconGroupDAO iconGroupDao;

	@Resource (name="iconDao")
	private IconDAO iconDao;

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	public ProjectService() {
	}
	
	@Override
	public void delete(Object entity) throws Exception {
		Project project = (Project) entity;
		// Suppresion des langues
		//List<Language> languages = languageDao.findByProject(id);
		//for (Language language : languages) {
		//	languageDao.delete(language);
		//}

		// Suppresion du projet ==> flag enabled = false
		//super.deleteById(id);
		project.setEnabled(false);
		entityDao.saveOrUpdate(project);

		// Suppression du r�pertoire
		//String projectDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+project.getKey();
		//FileUtils.deleteDirectory(new File(projectDir));

		// Suppression du fichier "project.xml"
		String projectDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+project.getKey();
		File file = new File(projectDir+"/project.xml");
		if (file.exists()) {
			file.delete();
		}
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Project project = (Project) entityDao.findById(id);
		this.delete(project);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public Project findByKey(String key) {
		return ((ProjectDAO) entityDao).findByKey(key);
	}
	
	@Override
	public Project findById(Integer id) {
		return ((ProjectDAO) entityDao).findById(id);
	}
	
	public List<Project> findByCompany(Integer companyId) {
		return ((ProjectDAO) entityDao).findByCompany(companyId);
	}
	
	@Override
	public Object saveOrUpdate(Object entity) throws Exception {
		Project project = (Project) entity;
		Integer projectId = project.getId();
		
		// Save project
		project = (Project) super.saveOrUpdate(entity);
		
		if (projectId == null) {
			//---------------------------------------------------------------------
			// Cr�ation de la langue anglaise
			//---------------------------------------------------------------------
			Language language = new Language();
			language.setProject(project);
			language.setName(Constants.LANGUAGE_NAME_ENGLISH);
			language.setCode("en");
			language.setFlag(Constants.LANGUAGE_CODE_ENGLISH);
			language.setDefaultLanguage(true);
			languageService.saveOrUpdate(language);
			
			//---------------------------------------------------------------------
			// Cr�ation des groupes d'ic�nes du projet � partir des des ic�nes des types d'items
			//---------------------------------------------------------------------
			String externalDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
			String commonIconsDir = externalDir+"/"+Constants.DIR_ICONS;
			String customIconsDir = externalDir+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ICONS;
			IconGroup iconGroup = null;
			Icon icon = null;

			List<ItemType> itemTypes = itemTypeDao.findAllWithTerminal();
			for (ItemType itemType : itemTypes) {
				// Cr�ation du groupe d'ic�nes
				iconGroup = new IconGroup();
				iconGroup.setProject(project);
				iconGroup.setName(itemType.getName());
				iconGroup = (IconGroup) iconGroupDao.saveOrUpdate(iconGroup);
				
				List<Icon> icons = iconDao.findByItemType(itemType.getId());
				for (Icon iconTmp : icons) {
					// Cr�ation de l'ic�ne
					icon = new Icon();
					icon.setIconGroup(iconGroup);
					icon.setName(iconTmp.getName());
					icon.setIcon(iconTmp.getIcon());
					icon = (Icon) iconDao.saveOrUpdate(icon);
					
					// Copy du fichier dans l'arborescence projet
					FileUtils.copyFile(
							new File(commonIconsDir+"/"+iconTmp.getId()+"/"+iconTmp.getIcon()),
							new File(customIconsDir+"/"+icon.getId()+"/"+icon.getIcon()));
				}
			}
		}
		
		if(iconGroupDao.findByProjectAndName(project.getId(), "Custom") == null) {
			IconGroup iconGroup = new IconGroup();
			iconGroup.setProject(project);
			iconGroup.setName("Custom");
			iconGroup.setType(1);
			iconGroupDao.saveOrUpdate(iconGroup);
		}
		
		return project;
	}
	
	/**
	 * Associe des utilisateur � un projet
	 * @param projectId
	 * @param userIds
	 * @throws Exception
	 */
	public void saveUsers(Integer projectId, List<Integer> userIds) throws Exception {
		if (projectId != null) {
			// R�cup�ration du projet
			Project project = (Project) entityDao.findById(projectId);

			// Ajout des utilisateurs au projet
			Set<Utilisateur> users = new HashSet<Utilisateur>();
			for(Integer userId: userIds) {
				users.add(new Utilisateur(userId));
			}
			project.setUsers(users);

			// Mise � jour du projet
			entityDao.saveOrUpdate(project);
		}
	}
	
	public String generateProjectKey() {
		return UUID.randomUUID().toString();
	}
}