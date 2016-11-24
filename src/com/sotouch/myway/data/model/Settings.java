package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Settings extends BaseDataBean {

	private Integer clock = 12;
	private Boolean forceItemSizes = Boolean.FALSE;
	private Integer locationsWidth = 60;
	private Integer locationsHeight = 60;
	private Integer otherItemsWidth = 40;
	private Integer otherItemsHeight = 40;
	private Boolean itemsKeepRatio = Boolean.FALSE;
	private Integer inactivityDelay = 30;
	private String bannerPosition = "top";
	private Boolean autoUploadContacts = Boolean.TRUE;
	private String hourUploadContacts = "00:00 AM";
	private Boolean autoUpdateProject = Boolean.TRUE;
	private String hourUpdateProject = "11:00 PM";
	private Project project;
	private Boolean displayWholeMap = Boolean.FALSE;
	private Boolean allowWebPageNavigation = Boolean.FALSE;
	private String mixPanelKey;
	
	private Boolean autoUpdateSoftware;
	private String hourUpdateSoftware;
	
	private Boolean itemsInZone = Boolean.FALSE;
	private Boolean showNameInsteadofIcon;

	private Integer updateTryTimes;
	
	private String emailSendContacts;
	private Boolean sendContactsEmail;
	
	public Settings() {
		super();
	}

	public Settings(Integer id) {
		super(id);
	}

	public Integer getClock() {
		return this.clock;
	}
	public void setClock(Integer clock) {
		this.clock = clock;
	}

	public Boolean getForceItemSizes() {
		return this.forceItemSizes;
	}
	public void setForceItemSizes(Boolean forceItemSizes) {
		this.forceItemSizes = forceItemSizes;
	}

	public Integer getLocationsWidth() {
		return this.locationsWidth;
	}
	public void setLocationsWidth(Integer locationsWidth) {
		this.locationsWidth = locationsWidth;
	}

	public Integer getLocationsHeight() {
		return this.locationsHeight;
	}
	public void setLocationsHeight(Integer locationsHeight) {
		this.locationsHeight = locationsHeight;
	}

	public Integer getOtherItemsWidth() {
		return this.otherItemsWidth;
	}
	public void setOtherItemsWidth(Integer otherItemsWidth) {
		this.otherItemsWidth = otherItemsWidth;
	}

	public Integer getOtherItemsHeight() {
		return this.otherItemsHeight;
	}
	public void setOtherItemsHeight(Integer otherItemsHeight) {
		this.otherItemsHeight = otherItemsHeight;
	}

	public Boolean getItemsKeepRatio() {
		return this.itemsKeepRatio;
	}
	public void setItemsKeepRatio(Boolean itemsKeepRatio) {
		this.itemsKeepRatio = itemsKeepRatio;
	}

	public Integer getInactivityDelay() {
		return this.inactivityDelay;
	}
	public void setInactivityDelay(Integer inactivityDelay) {
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

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
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
}
