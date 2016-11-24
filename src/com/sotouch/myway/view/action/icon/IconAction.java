package com.sotouch.myway.view.action.icon;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.IconGroup;
import com.sotouch.myway.data.model.ItemType;
import com.sotouch.myway.service.icon.IconService;

import com.sotouch.myway.service.iconGroup.IconGroupService;
/**
 * Class which manages the icons
 */
public class IconAction extends BaseCrudAction<IconViewBean> {

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Resource (name="iconGroupService")
	private IconGroupService iconGroupService;
	
	private String timestamp;

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		IconViewBean iconViewBean = (IconViewBean) viewBean;
		criteriaMap.put("name", iconViewBean.getName());
		criteriaMap.put("itemType.id", iconViewBean.getItemTypeId());
		criteriaMap.put("iconGroup.id", iconViewBean.getIconGroupId());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	public String save() throws Exception {
		try {
			
			IconViewBean iconViewBean = (IconViewBean) viewBean;
			Icon iconTmp = ((IconService)entityService).findByGroupAndName(iconViewBean.getIconGroupId(), iconViewBean.getName());
			if(iconTmp != null) {
				iconViewBean.setId(iconTmp.getId());
			}
			else {
				super.execute();
			}
			
			if (jsonResponse.getErrors().size() < 1) {
				// Icon upload
				if (iconViewBean.getIcon() != null) {
					String uploadPath;
					if (iconViewBean.getItemTypeId() != null) {
						uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
					}
					else {
						uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					}
					
					IconGroup iconGroup = (IconGroup)iconGroupService.findById(iconViewBean.getIconGroupId());
					String filePath = "";
					if(iconGroup.getType() == 0) {
						String dir = uploadPath+"/"+Constants.DIR_ICONS+"/"+iconViewBean.getId();
						FileUtils.deleteDirectory(new File(dir));
						filePath = dir+"/"+iconViewBean.getIconFileName();
						File uploadedFile = new File(filePath);
						FileUtils.copyFile(new File(iconViewBean.getIcon()), uploadedFile);
					}
					else {
						String dir = uploadPath+"/"+Constants.DIR_ICONS+"/custom";
						filePath = dir+"/"+iconViewBean.getName() + "." + getExtName(iconViewBean.getIconFileName());
						File uploadedFile = new File(filePath);
						uploadedFile.delete();
						FileUtils.copyFile(new File(iconViewBean.getIcon()), uploadedFile);
					}
					// Resize image
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_ICON_WIDTH, Constants.IMAGE_SIZE_ICON_HEIGHT, null);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
					Icon icon = (Icon) entityService.findById(iconViewBean.getId());
					icon.setIcon(newFileName);
					entityService.saveOrUpdate(icon);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}

	private String getExtName(String s) {
        String[] parts = s.split("\\.");
        return parts[parts.length - 1];
    }
	
	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		IconViewBean iconViewBean = (IconViewBean) viewBean;
		Icon icon = (Icon) dataBean;
		icon.setName(iconViewBean.getName());
		if (iconViewBean.getIconFileName() != null && iconViewBean.getIcon() != null) {
			if (ImageResizer.getWeight(iconViewBean.getIcon()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
				jsonResponse.addError(new JSONError("viewBean.icon", getText("common.error.image.maxSize")));
			}
			else {
				icon.setIcon(iconViewBean.getIconFileName());
			}
		}
		if (iconViewBean.getItemTypeId() != null) {
			icon.setItemType(new ItemType(Integer.valueOf(iconViewBean.getItemTypeId())));
		}
		if (iconViewBean.getIconGroupId() != null) {
			icon.setIconGroup(new IconGroup(Integer.valueOf(iconViewBean.getIconGroupId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Icon icon = (Icon) dataBean;
		IconViewBean iconViewBean = (IconViewBean) viewBean;
		iconViewBean.setName(icon.getName());
		iconViewBean.setIcon(icon.getIcon());
		iconViewBean.setItemTypeId(icon.getItemType() != null ? icon.getItemType().getId() : null);
		iconViewBean.setIconGroupId(icon.getIconGroup() != null ? icon.getIconGroup().getId() : null);
		
		if (timestamp == null) {
			timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmssS");
		}
		iconViewBean.setTimestamp(timestamp);
		
		iconViewBean.setSubFolder((icon.getIconGroup().getType() == 0) ? icon.getId().toString() : "custom");
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("icon.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// If the icon already exists
		if (e instanceof DataIntegrityViolationException) {
			// Error message
			jsonResponse.addError(new JSONError("viewBean.name", resources.getString("icon.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}

	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================
	public String restoreDefaultIcons() throws Exception {
		((IconService) entityService).restoreDefaultIcons(((IconViewBean) viewBean).getIconGroupId());
		return SUCCESS_JSON;
	}
	

	//=========================================================================
	// ACCESSORS
	//=========================================================================
}
