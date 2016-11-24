package com.sotouch.myway.view.action.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class SettingsViewBean extends BaseViewBean {
	
	private String clock;
	private Boolean forceItemSizes;
	private String locationsWidth;
	private String locationsHeight;
	private String otherItemsWidth;
	private String otherItemsHeight;
	private Boolean itemsKeepRatio;
	private String inactivityDelay;
	private String bannerPosition;
	private Boolean autoUploadContacts;
	private String hourUploadContacts;
	private String emailSendContacts;
	private Boolean sendContactsEmail;
	private Boolean autoUpdateProject;
	private String hourUpdateProject;
	private Boolean autoUpdateSoftware;
	private String hourUpdateSoftware;
	private Integer projectId;
	private Boolean displayWholeMap;
	private Boolean allowWebPageNavigation;
	private String mixPanelKey;
	
	private String ivsESServerIP;
	
	private Integer settingId;
	
	private Boolean itemsInZone;
	private Boolean showNameInsteadofIcon;

	private Integer updateTryTimes;
	
	public Integer getSettingId() {
		return this.settingId;
	}
	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}
	
	public String getClock() {
		return this.clock;
	}
	public void setClock(String clock) {
		this.clock = clock;
	}

	public Boolean getForceItemSizes() {
		return this.forceItemSizes;
	}
	public void setForceItemSizes(Boolean forceItemSizes) {
		this.forceItemSizes = forceItemSizes;
	}

	public String getLocationsWidth() {
		return this.locationsWidth;
	}
	public void setLocationsWidth(String locationsWidth) {
		this.locationsWidth = locationsWidth;
	}

	public String getLocationsHeight() {
		return this.locationsHeight;
	}
	public void setLocationsHeight(String locationsHeight) {
		this.locationsHeight = locationsHeight;
	}

	public String getOtherItemsWidth() {
		return this.otherItemsWidth;
	}
	public void setOtherItemsWidth(String otherItemsWidth) {
		this.otherItemsWidth = otherItemsWidth;
	}

	public String getOtherItemsHeight() {
		return this.otherItemsHeight;
	}
	public void setOtherItemsHeight(String otherItemsHeight) {
		this.otherItemsHeight = otherItemsHeight;
	}

	public Boolean getItemsKeepRatio() {
		return this.itemsKeepRatio;
	}
	public void setItemsKeepRatio(Boolean itemsKeepRatio) {
		this.itemsKeepRatio = itemsKeepRatio;
	}

	public String getInactivityDelay() {
		return this.inactivityDelay;
	}
	public void setInactivityDelay(String inactivityDelay) {
		this.inactivityDelay = inactivityDelay;
	}

	public String getBannerPosition() {
		return bannerPosition;
	}
	public void setBannerPosition(String bannerPosition) {
		this.bannerPosition = bannerPosition;
	}

	public Boolean getAutoUploadContacts() {
		return this.autoUploadContacts;
	}
	public void setAutoUploadContacts(Boolean autoUploadContacts) {
		this.autoUploadContacts = autoUploadContacts;
	}

	public String getHourUploadContacts() {
		return this.hourUploadContacts;
	}
	public void setHourUploadContacts(String hourUploadContacts) {
		this.hourUploadContacts = hourUploadContacts;
	}

	public String getEmailSendContacts() {
		return this.emailSendContacts;
	}
	public void setEmailSendContacts(String emailSendContacts) {
		this.emailSendContacts = emailSendContacts;
	}
	
	public Boolean getSendContactsEmail() {
		return this.sendContactsEmail;
	}
	public void setSendContactsEmail(Boolean sendContactsEmail) {
		this.sendContactsEmail = sendContactsEmail;
	}
	
	public Boolean getAutoUpdateProject() {
		return this.autoUpdateProject;
	}
	public void setAutoUpdateProject(Boolean autoUpdateProject) {
		this.autoUpdateProject = autoUpdateProject;
	}

	public String getHourUpdateProject() {
		return this.hourUpdateProject;
	}
	public void setHourUpdateProject(String hourUpdateProject) {
		this.hourUpdateProject = hourUpdateProject;
	}

	public Boolean getAutoUpdateSoftware() {
		return this.autoUpdateSoftware;
	}
	public void setAutoUpdateSoftware(Boolean autoUpdateSoftware) {
		this.autoUpdateSoftware = autoUpdateSoftware;
	}

	public String getHourUpdateSoftware() {
		return this.hourUpdateSoftware;
	}
	public void setHourUpdateSoftware(String hourUpdateSoftware) {
		this.hourUpdateSoftware = hourUpdateSoftware;
	}
	
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Boolean getDisplayWholeMap() {
		return this.displayWholeMap;
	}
	public void setDisplayWholeMap(Boolean displayWholeMap) {
		this.displayWholeMap = displayWholeMap;
	}
	
	public Boolean getAllowWebPageNavigation() {
		return this.allowWebPageNavigation;
	}
	public void setAllowWebPageNavigation(Boolean allowWebPageNavigation) {
		this.allowWebPageNavigation = allowWebPageNavigation;
	}

	public String getMixPanelKey() {
		return mixPanelKey;
	}
	public void setMixPanelKey(String mixPanelKey) {
		this.mixPanelKey = mixPanelKey;
	}
	
	public String getIvsESServerIP() {
		return this.ivsESServerIP;
	}
	
	public void setIvsESServerIP(String ivsESServerIP) {
		this.ivsESServerIP = ivsESServerIP;
	}
	
	public Boolean getItemsInZone() {
		return this.itemsInZone;
	}
	public void setItemsInZone(Boolean itemsInZone) {
		this.itemsInZone = itemsInZone;
	}
	
	public Boolean getShowNameInsteadofIcon() {
		return this.showNameInsteadofIcon;
	}
	public void setShowNameInsteadofIcon(Boolean showNameInsteadofIcon) {
		this.showNameInsteadofIcon = showNameInsteadofIcon;
	}
	
	public Integer getUpdateTryTimes() {
		return this.updateTryTimes;
	}
	public void setUpdateTryTimes(Integer updateTryTimes) {
		this.updateTryTimes = updateTryTimes;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (forceItemSizes != null && forceItemSizes) {
			if (StringUtil.isEmpty(locationsWidth)) {
				errors.add(new JSONError("viewBean.locationsWidth", resources.getString("settings.erreur.locationsWidth.obligatoire")));
			}
			if (StringUtil.isEmpty(locationsHeight)) {
				errors.add(new JSONError("viewBean.locationsHeight", resources.getString("settings.erreur.locationsHeight.obligatoire")));
			}

			if (StringUtil.isEmpty(otherItemsWidth)) {
				errors.add(new JSONError("viewBean.otherItemsWidth", resources.getString("settings.erreur.otherItemsWidth.obligatoire")));
			}
			if (StringUtil.isEmpty(otherItemsHeight)) {
				errors.add(new JSONError("viewBean.otherItemsHeight", resources.getString("settings.erreur.otherItemsHeight.obligatoire")));
			}
		}
		
		if (autoUploadContacts != null && autoUploadContacts && StringUtil.isEmpty(hourUploadContacts)) {
			errors.add(new JSONError("viewBean.hourUploadContacts", resources.getString("settings.erreur.hourUploadContacts.obligatoire")));
		}
		
		if (autoUpdateProject != null && autoUpdateProject && StringUtil.isEmpty(hourUpdateProject)) {
			errors.add(new JSONError("viewBean.hourUpdateProject", resources.getString("settings.erreur.hourUpdateProject.obligatoire")));
		}

		return errors;
	}
	
}
