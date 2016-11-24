package com.sotouch.myway.view.action.theme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.service.config.ConfigParameterService;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.Settings;
import com.sotouch.myway.data.model.Theme;
import com.sotouch.myway.view.action.project.ProjectViewBean;
import com.sotouch.myway.view.action.settings.SettingsViewBean;
import com.sotouch.myway.service.theme.ThemeService;

/**
/ * Class which manages login
 */

public class ThemeAction extends BaseCrudAction<ThemeViewBean> {

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	private String fileType;
	
	@Override
	protected void doInit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
	}

	@Override
	protected String getDefaultSort() {
		return null;
	}

	/**
	 * Load the project settings
	 */
	public String edit() throws Exception {
		try {
			Theme theme = ((ThemeService) entityService).findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			if (theme == null){
				theme = new Theme();
				theme.setProject(new Project((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID)));
				theme = (Theme) entityService.saveOrUpdate(theme);
			}
			this.jsonResponse = new JSONResponse(this.toViewBean(theme));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return SUCCESS_JSON;
	}
	
	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		ThemeViewBean themeViewBean = (ThemeViewBean) viewBean;
		Theme theme = (Theme) dataBean;
		
		theme.setLeftPanel(themeViewBean.getLeftPanel());
		theme.setLandscapeBackground(themeViewBean.getLandscapeBackground());
		theme.setLandscapeLightFontColor(themeViewBean.getLandscapeLightFontColor());
		theme.setLandscapeTextFontColor(themeViewBean.getLandscapeTextFontColor());
		theme.setLandscapeDarkFontColor(themeViewBean.getLandscapeDarkFontColor());
		theme.setLandscapePromotionHighlightColor(themeViewBean.getLandscapePromotionHighlightColor());
		theme.setLandscapeCategoryDefault(themeViewBean.getLandscapeCategoryDefaultColor());
		theme.setLandscapeCategorySelected(themeViewBean.getLandscapeCategorySelectedColor());
		theme.setLandscapeNavigationHeaderColor(themeViewBean.getLandscapeNavigationHeaderColor());
		theme.setLandscapeNavigationHeaderFontColor(themeViewBean.getLandscapeNavigationHeaderFontColor());
		theme.setLandscapeNavigationBackgroundColor(themeViewBean.getLandscapeNavigationBackgroundColor());
		theme.setLandscapeNavigationSelectedBackgroundColor(themeViewBean.getLandscapeNavigationSelectedBackgroundColor());
		theme.setTopPanel(themeViewBean.getTopPanel());
		theme.setPortraitBackground(themeViewBean.getPortraitBackground());
		theme.setPortraitLightFontColor(themeViewBean.getPortraitLightFontColor());
		theme.setPortraitTextFontColor(themeViewBean.getPortraitTextFontColor());
		theme.setPortraitDarkFontColor(themeViewBean.getPortraitDarkFontColor());
		theme.setPortraitPromotionHighlightColor(themeViewBean.getPortraitPromotionHighlightColor());
		theme.setPortraitCategoryDefault(themeViewBean.getPortraitCategoryDefaultColor());
		theme.setPortraitCategorySelected(themeViewBean.getPortraitCategorySelectedColor());
		theme.setPortraitNavigationHeaderColor(themeViewBean.getPortraitNavigationHeaderColor());
		theme.setPortraitNavigationHeaderFontColor(themeViewBean.getPortraitNavigationHeaderFontColor());
		theme.setPortraitNavigationBackgroundColor(themeViewBean.getPortraitNavigationBackgroundColor());
		theme.setPortraitNavigationSelectedBackgroundColor(themeViewBean.getPortraitNavigationSelectedBackgroundColor());
		theme.setIwHeaderBackground(themeViewBean.getIwHeaderBackground());
		theme.setIwTouchWall(themeViewBean.getIwTouchWall());
		theme.setIwMainBackground(themeViewBean.getIwMainBackground());
		theme.setIwFooterBackground(themeViewBean.getIwFooterBackground());
		theme.setIwButton(themeViewBean.getIwButton());
		theme.setIwSelectedButton(themeViewBean.getIwSelectedButton());
		
		theme.setProject(new Project((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID)));
	}
	
	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Theme theme = (Theme) dataBean;
		ThemeViewBean themeViewBean = (ThemeViewBean) viewBean;
		
		themeViewBean.setLeftPanel(theme.getLeftPanel());
		themeViewBean.setLandscapeBackground(theme.getLandscapeBackground());
		themeViewBean.setLandscapeTextFontColor(theme.getLandscapeTextFontColor());
		themeViewBean.setLandscapeLightFontColor(theme.getLandscapeLightFontColor());
		themeViewBean.setLandscapeDarkFontColor(theme.getLandscapeDarkFontColor());
		themeViewBean.setLandscapePromotionHighlightColor(theme.getLandscapePromotionHighlightColor());
		themeViewBean.setLandscapeCategoryDefaultColor(theme.getLandscapeCategoryDefault());
		themeViewBean.setLandscapeCategorySelectedColor(theme.getLandscapeCategorySelected());
		
		themeViewBean.setLandscapeNavigationHeaderColor(theme.getLandscapeNavigationHeaderColor());
		themeViewBean.setLandscapeNavigationHeaderFontColor(theme.getLandscapeNavigationHeaderFontColor());
		themeViewBean.setLandscapeNavigationBackgroundColor(theme.getLandscapeNavigationBackgroundColor());
		themeViewBean.setLandscapeNavigationSelectedBackgroundColor(theme.getLandscapeNavigationSelectedBackgroundColor());
		themeViewBean.setTopPanel(theme.getTopPanel());
		themeViewBean.setPortraitBackground(theme.getPortraitBackground());
		themeViewBean.setPortraitTextFontColor(theme.getPortraitTextFontColor());
		themeViewBean.setPortraitLightFontColor(theme.getPortraitLightFontColor());
		themeViewBean.setPortraitDarkFontColor(theme.getPortraitDarkFontColor());
		themeViewBean.setPortraitPromotionHighlightColor(theme.getPortraitPromotionHighlightColor());
		themeViewBean.setPortraitCategoryDefaultColor(theme.getPortraitCategoryDefault());
		themeViewBean.setPortraitCategorySelectedColor(theme.getPortraitCategorySelected());
		themeViewBean.setPortraitNavigationHeaderColor(theme.getPortraitNavigationHeaderColor());
		themeViewBean.setPortraitNavigationHeaderFontColor(theme.getPortraitNavigationHeaderFontColor());
		themeViewBean.setPortraitNavigationBackgroundColor(theme.getPortraitNavigationBackgroundColor());
		themeViewBean.setPortraitNavigationSelectedBackgroundColor(theme.getPortraitNavigationSelectedBackgroundColor());
		themeViewBean.setIwHeaderBackground(theme.getIwHeaderBackground());
		themeViewBean.setIwTouchWall(theme.getIwTouchWall());
		themeViewBean.setIwMainBackground(theme.getIwMainBackground());
		themeViewBean.setIwFooterBackground(theme.getIwFooterBackground());
		themeViewBean.setIwButton(theme.getIwButton());
		themeViewBean.setIwSelectedButton(theme.getIwSelectedButton());
		
		themeViewBean.setProject(theme.getProject().getName());
		themeViewBean.setProjectId(theme.getProject().getId());
	}
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================
	
	/**
	 * Files upload (logo, newsletter logo, credits image)
	 * @return
	 * @throws Exception
	 */
	public String uploadFiles() throws Exception {
		ThemeViewBean themeViewBean = (ThemeViewBean) viewBean;
		
		Theme theme = (Theme) ((ThemeService)entityService).findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		List<String> fileUrls = new ArrayList<String>();
		
		if(themeViewBean.getLeftPanelFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getLeftPanel(), themeViewBean.getLeftPanelFileName(), theme.getLeftPanel(), "viewBean.leftPanel", "leftPanel"));
			theme.setLeftPanel(themeViewBean.getLeftPanelFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getLandscapeBackgroundFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getLandscapeBackground(), themeViewBean.getLandscapeBackgroundFileName(), theme.getLandscapeBackground(), "viewBean.landscapeBackground", "landscapeBackground"));
			theme.setLandscapeBackground(themeViewBean.getLandscapeBackgroundFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getTopPanelFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getTopPanel(), themeViewBean.getTopPanelFileName(), theme.getTopPanel(), "viewBean.topPanel", "topPanel"));
			theme.setTopPanel(themeViewBean.getTopPanelFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getPortraitBackgroundFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getPortraitBackground(), themeViewBean.getPortraitBackgroundFileName(), theme.getPortraitBackground(), "viewBean.portraitBackground", "portraitBackground"));
			theme.setPortraitBackground(themeViewBean.getPortraitBackgroundFileName());
		}
		else {
			fileUrls.add("");
		}
		
		
		
		if(themeViewBean.getIwHeaderBackgroundFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwHeaderBackground(), themeViewBean.getIwHeaderBackgroundFileName(), theme.getIwHeaderBackground(), "viewBean.iwHeaderBackground", "iwHeaderBackground"));
			theme.setIwHeaderBackground(themeViewBean.getIwHeaderBackgroundFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getIwTouchWallFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwTouchWall(), themeViewBean.getIwTouchWallFileName(), theme.getIwTouchWall(), "viewBean.iwTouchWall", "iwTouchWall"));
			theme.setIwTouchWall(themeViewBean.getIwTouchWallFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getIwMainBackgroundFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwMainBackground(), themeViewBean.getIwMainBackgroundFileName(), theme.getIwMainBackground(), "viewBean.iwMainBackground", "iwMainBackground"));
			theme.setIwMainBackground(themeViewBean.getIwMainBackgroundFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getIwFooterBackgroundFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwFooterBackground(), themeViewBean.getIwFooterBackgroundFileName(), theme.getIwFooterBackground(), "viewBean.iwFooterBackground", "iwFooterBackground"));
			theme.setIwFooterBackground(themeViewBean.getIwFooterBackgroundFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getIwButtonFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwButton(), themeViewBean.getIwButtonFileName(), theme.getIwButton(), "viewBean.iwButton", "iwButton"));
			theme.setIwButton(themeViewBean.getIwButtonFileName());
		}
		else {
			fileUrls.add("");
		}
		
		if(themeViewBean.getIwSelectedButtonFileName() != null) {
			fileUrls.add(uploadFile(themeViewBean.getIwSelectedButton(), themeViewBean.getIwSelectedButtonFileName(), theme.getIwSelectedButton(), "viewBean.iwSelectedButton", "iwSelectedButton"));
			theme.setIwSelectedButton(themeViewBean.getIwSelectedButtonFileName());
		}
		else {
			fileUrls.add("");
		}
		
		// Update project
		entityService.saveOrUpdate(theme);
		
		// Return file URLs
		jsonResponse.setDatas(fileUrls);
		return SUCCESS_JSON;
	}
	
	public String deleteFile() throws Exception {
		
		Theme theme = (Theme) ((ThemeService)entityService).findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		String uploadDir = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_THEME;

		if (fileType.equals("leftPanel")) {
			theme.setLeftPanel(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/leftPanel"));
		}
		else if (fileType.equals("landscapeBackground")) {
			theme.setLandscapeBackground(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/landscapeBackground"));
		}
		else if (fileType.equals("topPanel")) {
			theme.setTopPanel(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/topPanel"));
		}
		else if (fileType.equals("portraitBackground")) {
			theme.setPortraitBackground(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/portraitBackground"));
		}
		
		else if (fileType.equals("iwHeaderBackground")) {
			theme.setIwHeaderBackground(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwHeaderBackground"));
		}
		else if (fileType.equals("iwTouchWall")) {
			theme.setIwTouchWall(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwTouchWall"));
		}
		else if (fileType.equals("iwMainBackground")) {
			theme.setIwMainBackground(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwMainBackground"));
		}
		
		else if (fileType.equals("iwFooterBackground")) {
			theme.setIwFooterBackground(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwFooterBackground"));
		}
		else if (fileType.equals("iwButton")) {
			theme.setIwButton(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwButton"));
		}
		else if (fileType.equals("iwSelectedButton")) {
			theme.setIwSelectedButton(null);
			FileUtils.deleteDirectory(new File(uploadDir+"/iwSelectedButton"));
		}
		
		// Update the projet
		entityService.saveOrUpdate(theme);
		
		return SUCCESS_JSON;
	}
	
	private String uploadFile(String file, String fileName, String preFileName, String viewBeanName, String subdir) {
		String newFileName = "";
		if (file != null) {
			if (ImageResizer.getWeight(file) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
				jsonResponse.addError(new JSONError(viewBeanName, getText("common.error.file.maxSize")));
			}
			else {
				String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_THEME + "/" + subdir;
				String filePath = uploadPath+"/"+fileName;
				File uploadedFile = new File(filePath);
				try {
					new File(uploadPath + "/" + preFileName).delete();
					FileUtils.copyFile(new File(file), uploadedFile);
				} catch(Exception e) {
				}

				newFileName = Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_THEME+"/"+fileName;
			}
		}
		return newFileName;
	}

	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
