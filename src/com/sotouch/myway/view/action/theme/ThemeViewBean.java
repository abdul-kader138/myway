package com.sotouch.myway.view.action.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.data.model.Project;

public class ThemeViewBean extends BaseViewBean {
	
	
	private String leftPanel;
	private String leftPanelFileName;
	private String landscapeBackground;
	private String landscapeBackgroundFileName;
	private String landscapeLightFontColor;
	private String landscapeTextFontColor;
	private String landscapeDarkFontColor;
	private String landscapePromotionHighlightColor;
	private String landscapeCategoryDefault;
	private String landscapeCategorySelected;
	private String landscapeNavigationHeaderColor;
	private String landscapeNavigationHeaderFontColor;
	private String landscapeNavigationBackgroundColor;
	private String landscapeNavigationSelectedBackgroundColor
	;
	private String topPanel;
	private String topPanelFileName;
	private String portraitBackground;
	private String portraitBackgroundFileName;
	private String portraitLightFontColor;
	private String portraitTextFontColor;
	private String portraitDarkFontColor;
	private String portraitPromotionHighlightColor;
	private String portraitCategoryDefault;
	private String portraitCategorySelected;
	private String portraitNavigationHeaderColor;
	private String portraitNavigationHeaderFontColor;
	private String portraitNavigationBackgroundColor;
	private String portraitNavigationSelectedBackgroundColor;
	
	private String iwHeaderBackground;
	private String iwHeaderBackgroundFileName;
	private String iwTouchWall;
	private String iwTouchWallFileName;
	private String iwMainBackground;
	private String iwMainBackgroundFileName;
	private String iwFooterBackground;
	private String iwFooterBackgroundFileName;
	private String iwButton;
	private String iwButtonFileName;
	private String iwSelectedButton;
	private String iwSelectedButtonFileName;
	
	private String project;
	private Integer projectId;

	public void setLeftPanel(String leftPanel) {
		this.leftPanel = leftPanel;
	}
	
	public String getLeftPanel() {
		return this.leftPanel;
	}
	
	public void setLeftPanelFileName(String leftPanelFileName) {
		this.leftPanelFileName = leftPanelFileName;
	}
	
	public String getLeftPanelFileName() {
		return this.leftPanelFileName;
	}
	
	public void setLandscapeBackground(String landscapeBackground) {
		this.landscapeBackground = landscapeBackground;
	}
	
	public String getLandscapeBackground() {
		return this.landscapeBackground;
	}
	
	public void setLandscapeBackgroundFileName(String landscapeBackgroundFileName) {
		this.landscapeBackgroundFileName = landscapeBackgroundFileName;
	}
	
	public String getLandscapeBackgroundFileName() {
		return this.landscapeBackgroundFileName;
	}
	
	public void setLandscapeTextFontColor(String landscapeTextFontColor) {
		this.landscapeTextFontColor = landscapeTextFontColor;
	}
	
	public String getLandscapeTextFontColor() {
		return this.landscapeTextFontColor;
	}
	
	public void setLandscapeLightFontColor(String landscapeLightFontColor) {
		this.landscapeLightFontColor = landscapeLightFontColor;
	}
	
	public String getLandscapeLightFontColor() {
		return this.landscapeLightFontColor;
	}
	
	public void setLandscapeDarkFontColor(String landscapeDarkFontColor) {
		this.landscapeDarkFontColor = landscapeDarkFontColor;
	}
	
	public String getLandscapeDarkFontColor() {
		return this.landscapeDarkFontColor;
	}
	
	public void setLandscapePromotionHighlightColor(String landscapePromotionHighlightColor) {
		this.landscapePromotionHighlightColor = landscapePromotionHighlightColor;
	}
	
	public String getLandscapePromotionHighlightColor() {
		return this.landscapePromotionHighlightColor;
	}
	
	
	public void setLandscapeCategoryDefaultColor(String landscapeCategoryDefault) {
		this.landscapeCategoryDefault = landscapeCategoryDefault;
	}
	
	public String getLandscapeCategoryDefaultColor() {
		return this.landscapeCategoryDefault;
	}
	
	public void setLandscapeCategorySelectedColor(String landscapeCategorySelected) {
		this.landscapeCategorySelected = landscapeCategorySelected;
	}
	
	public String getLandscapeCategorySelectedColor() {
		return this.landscapeCategorySelected;
	}
	
	
	public void setLandscapeNavigationHeaderColor(String landscapeNavigationHeaderColor) {
		this.landscapeNavigationHeaderColor = landscapeNavigationHeaderColor;
	}
	
	public String getLandscapeNavigationHeaderColor() {
		return this.landscapeNavigationHeaderColor;
	}
	
	public void setLandscapeNavigationHeaderFontColor(String landscapeNavigationHeaderFontColor) {
		this.landscapeNavigationHeaderFontColor = landscapeNavigationHeaderFontColor;
	}
	
	public String getLandscapeNavigationHeaderFontColor() {
		return this.landscapeNavigationHeaderFontColor;
	}
	
	public void setLandscapeNavigationBackgroundColor(String landscapeNavigationBackgroundColor) {
		this.landscapeNavigationBackgroundColor = landscapeNavigationBackgroundColor;
	}
	
	public String getLandscapeNavigationBackgroundColor() {
		return this.landscapeNavigationBackgroundColor;
	}
	
	public void setLandscapeNavigationSelectedBackgroundColor(String landscapeNavigationSelectedBackgroundColor) {
		this.landscapeNavigationSelectedBackgroundColor = landscapeNavigationSelectedBackgroundColor;
	}
	
	public String getLandscapeNavigationSelectedBackgroundColor() {
		return this.landscapeNavigationSelectedBackgroundColor;
	}
	
	
	public void setTopPanel(String topPanel) {
		this.topPanel = topPanel;
	}
	
	public String getTopPanel() {
		return this.topPanel;
	}

	public void setTopPanelFileName(String topPanelFileName) {
		this.topPanelFileName = topPanelFileName;
	}
	
	public String getTopPanelFileName() {
		return this.topPanelFileName;
	}
	
	public void setPortraitBackground(String portraitBackground) {
		this.portraitBackground = portraitBackground;
	}
	
	public String getPortraitBackground() {
		return this.portraitBackground;
	}
	
	public void setPortraitBackgroundFileName(String portraitBackgroundFileName) {
		this.portraitBackgroundFileName = portraitBackgroundFileName;
	}
	
	public String getPortraitBackgroundFileName() {
		return this.portraitBackgroundFileName;
	}
	
	public void setPortraitTextFontColor(String portraitTextFontColor) {
		this.portraitTextFontColor = portraitTextFontColor;
	}
	
	public String getPortraitTextFontColor() {
		return this.portraitTextFontColor;
	}
	
	public void setPortraitLightFontColor(String portraitLightFontColor) {
		this.portraitLightFontColor = portraitLightFontColor;
	}
	
	public String getPortraitLightFontColor() {
		return this.portraitLightFontColor;
	}
	
	public void setPortraitDarkFontColor(String portraitDarkFontColor) {
		this.portraitDarkFontColor = portraitDarkFontColor;
	}
	
	public String getPortraitDarkFontColor() {
		return this.portraitDarkFontColor;
	}
	
	public void setPortraitPromotionHighlightColor(String portraitPromotionHighlightColor) {
		this.portraitPromotionHighlightColor = portraitPromotionHighlightColor;
	}
	
	public String getPortraitPromotionHighlightColor() {
		return this.portraitPromotionHighlightColor;
	}
	
	
	public void setPortraitCategoryDefaultColor(String portraitCategoryDefault) {
		this.portraitCategoryDefault = portraitCategoryDefault;
	}
	
	public String getPortraitCategoryDefaultColor() {
		return this.portraitCategoryDefault;
	}
	
	public void setPortraitCategorySelectedColor(String portraitCategorySelected) {
		this.portraitCategorySelected = portraitCategorySelected;
	}
	
	public String getPortraitCategorySelectedColor() {
		return this.portraitCategorySelected;
	}
	
	
	public void setPortraitNavigationHeaderColor(String portraitNavigationHeaderColor) {
		this.portraitNavigationHeaderColor = portraitNavigationHeaderColor;
	}
	
	public String getPortraitNavigationHeaderColor() {
		return this.portraitNavigationHeaderColor;
	}
	
	public void setPortraitNavigationHeaderFontColor(String portraitNavigationHeaderFontColor) {
		this.portraitNavigationHeaderFontColor = portraitNavigationHeaderFontColor;
	}
	
	public String getPortraitNavigationHeaderFontColor() {
		return this.portraitNavigationHeaderFontColor;
	}
	
	public void setPortraitNavigationBackgroundColor(String portraitNavigationBackgroundColor) {
		this.portraitNavigationBackgroundColor = portraitNavigationBackgroundColor;
	}
	
	public String getPortraitNavigationBackgroundColor() {
		return this.portraitNavigationBackgroundColor;
	}
	
	public void setPortraitNavigationSelectedBackgroundColor(String portraitNavigationSelectedBackgroundColor) {
		this.portraitNavigationSelectedBackgroundColor = portraitNavigationSelectedBackgroundColor;
	}
	
	public String getPortraitNavigationSelectedBackgroundColor() {
		return this.portraitNavigationSelectedBackgroundColor;
	}
	
	public void setIwHeaderBackground(String iwHeaderBackground) {
		this.iwHeaderBackground = iwHeaderBackground;
	}
	
	public String getIwHeaderBackground() {
		return this.iwHeaderBackground;
	}
	
	public void setIwTouchWall(String iwTouchWall) {
		this.iwTouchWall = iwTouchWall;
	}
	
	public String getIwTouchWall() {
		return this.iwTouchWall;
	}
	
	public void setIwMainBackground(String iwMainBackground) {
		this.iwMainBackground = iwMainBackground;
	}
	
	public String getIwMainBackground() {
		return this.iwMainBackground;
	}
	
	public void setIwFooterBackground(String iwFooterBackground) {
		this.iwFooterBackground = iwFooterBackground;
	}
	
	public String getIwFooterBackground() {
		return this.iwFooterBackground;
	}
	
	public void setIwButton(String iwButton) {
		this.iwButton = iwButton;
	}
	
	public String getIwButton() {
		return this.iwButton;
	}
	
	public void setIwSelectedButton(String iwSelectedButton) {
		this.iwSelectedButton = iwSelectedButton;
	}
	
	public String getIwSelectedButton() {
		return this.iwSelectedButton;
	}
	
	
	public void setIwHeaderBackgroundFileName(String iwHeaderBackgroundFileName) {
		this.iwHeaderBackgroundFileName = iwHeaderBackgroundFileName;
	}
	
	public String getIwHeaderBackgroundFileName() {
		return this.iwHeaderBackgroundFileName;
	}
	
	public void setIwTouchWallFileName(String iwTouchWallFileName) {
		this.iwTouchWallFileName = iwTouchWallFileName;
	}
	
	public String getIwTouchWallFileName() {
		return this.iwTouchWallFileName;
	}
	
	public void setIwMainBackgroundFileName(String iwMainBackgroundFileName) {
		this.iwMainBackgroundFileName = iwMainBackgroundFileName;
	}
	
	public String getIwMainBackgroundFileName() {
		return this.iwMainBackgroundFileName;
	}
	
	public void setIwFooterBackgroundFileName(String iwFooterBackgroundFileName) {
		this.iwFooterBackgroundFileName = iwFooterBackgroundFileName;
	}
	
	public String getIwFooterBackgroundFileName() {
		return this.iwFooterBackgroundFileName;
	}
	
	public void setIwButtonFileName(String iwButtonFileName) {
		this.iwButtonFileName = iwButtonFileName;
	}
	
	public String getIwButtonFileName() {
		return this.iwButtonFileName;
	}
	
	public void setIwSelectedButtonFileName(String iwSelectedButtonFileName) {
		this.iwSelectedButtonFileName = iwSelectedButtonFileName;
	}
	
	public String getIwSelectedButtonFileName() {
		return this.iwSelectedButtonFileName;
	}

	public String getProject() {
		return this.project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		return errors;
	}

}
