package com.sotouch.myway.service.icon;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.IconDAO;
import com.sotouch.myway.data.dao.IconGroupDAO;
import com.sotouch.myway.data.dao.ItemTypeDAO;
import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.IconGroup;
import com.sotouch.myway.data.model.ItemType;


public class IconService extends BaseCrudService {

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	@Resource (name="iconGroupDao")
	private IconGroupDAO iconGroupDao;

	@Resource (name="itemTypeDao")
	private ItemTypeDAO itemTypeDao;

	public IconService() {
	}
	
	public Icon findByGroupAndName(int groupId, String name) {
		return (Icon)((IconDAO)entityDao).findByGroupAndName(groupId, name);
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Icon icon = (Icon) entityDao.findById(id);
		super.deleteById(id);
		entityDao.getHibernateTemplate().flush();
		
		// Suppression du r�pertoire
		String projectDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
		String iconDir = null;
		if (icon.getItemType() != null) {
			iconDir = projectDir + "/"+Constants.DIR_ICONS+"/"+icon.getId();
		}
		else {
			if(icon.getIconGroup().getType() == 0) {
				iconDir = projectDir + "/"+Constants.DIR_PROJECTS + "/" + icon.getIconGroup().getProject().getKey()+"/"+Constants.DIR_ICONS+"/"+icon.getId();
			}
			else {
				iconDir = projectDir + "/"+Constants.DIR_PROJECTS+"/"+icon.getIconGroup().getProject().getKey()+"/"+Constants.DIR_ICONS+"/custom";
				File iconFile = new File(iconDir + "/" + icon.getIcon());
				iconFile.delete();
				File origFIle = new File(projectDir + "/"+Constants.DIR_ICONS+"/custom/" + icon.getIcon());
				if(origFIle.exists()) {
					FileUtils.copyFile(origFIle, iconFile);
				}
				return;
			}
		}
		FileUtils.deleteDirectory(new File(iconDir));
		
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<Icon> findByProject(Integer projectId) {
		return ((IconDAO) entityDao).findByProject(projectId);
	}
	
	/**
	 * Restaure les ic�nes par d�faut en fonction de ceux du type d'item correspondant au groupe
	 * @param iconGroupId
	 * @throws Exception 
	 */
	public void restoreDefaultIcons(Integer iconGroupId) throws Exception {
		// R�cup�ration du groupe d'ic�nes
		IconGroup iconGroup = iconGroupDao.findById(iconGroupId);
		
		// R�cup�ration du type d'item correspondant
		ItemType itemType = itemTypeDao.findByName(iconGroup.getName());
		
		if (itemType != null) {
			// R�cup�ration des icone du type d'item
			List<Icon> icons = ((IconDAO) entityDao).findByItemType(itemType.getId());
			
			// Parcours des ic�nes du type d'item
			String externalDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
			String commonIconsDir = externalDir+"/"+Constants.DIR_ICONS;
			String customIconsDir = externalDir+"/"+Constants.DIR_PROJECTS+"/"+iconGroup.getProject().getKey()+"/"+Constants.DIR_ICONS;
			for (Icon iconTmp : icons) {
				// R�cup�ration de l'ic�ne correspondant dans le groupe
				Icon icon = ((IconDAO) entityDao).findByGroupAndName(iconGroupId, iconTmp.getName());
				if (icon == null) {
					icon = new Icon();
					icon.setIconGroup(iconGroup);
					icon.setName(iconTmp.getName());
				}
				else {
					// Suppression du fichier
					FileUtils.deleteDirectory(new File(customIconsDir+"/"+icon.getId()));
				}
				icon.setIcon(iconTmp.getIcon());
				icon = (Icon) entityDao.saveOrUpdate(icon);
				
				// Copy du fichier dans l'arborescence projet
				FileUtils.copyFile(
						new File(commonIconsDir+"/"+iconTmp.getId()+"/"+iconTmp.getIcon()),
						new File(customIconsDir+"/"+icon.getId()+"/"+icon.getIcon()));
			}
		}
	}
}
