package com.sotouch.myway.view.action.settings;

import java.util.Map;

import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONResponse;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.Settings;
import com.sotouch.myway.service.settings.SettingsService;

/**
 * Class which manages the settingss
 */
public class SettingsAction extends BaseCrudAction<SettingsViewBean> {

	@Override
	protected void doInit() throws Exception {
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
			Settings settings = ((SettingsService) entityService).findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			if (settings == null){
				settings = new Settings();
				settings.setProject(new Project((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID)));
				settings = (Settings) entityService.saveOrUpdate(settings);
			}
			this.jsonResponse = new JSONResponse(this.toViewBean(settings));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return SUCCESS_JSON;
	}
	
	@Override
	protected void doBeforeSave() throws Exception {
		SettingsViewBean settingsViewBean = (SettingsViewBean) this.viewBean;
		if(settingsViewBean.getSettingId() != null) {
			settingsViewBean.setId(settingsViewBean.getSettingId());
		}
	}
	
	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		SettingsViewBean settingsViewBean = (SettingsViewBean) viewBean;
		Settings settings = (Settings) dataBean;
		settings.setClock(NumberUtil.toInteger(settingsViewBean.getClock()));
		settings.setForceItemSizes(settingsViewBean.getForceItemSizes());
		settings.setLocationsWidth(NumberUtil.toInteger(settingsViewBean.getLocationsWidth()));
		settings.setLocationsHeight(NumberUtil.toInteger(settingsViewBean.getLocationsHeight()));
		settings.setOtherItemsWidth(NumberUtil.toInteger(settingsViewBean.getOtherItemsWidth()));
		settings.setOtherItemsHeight(NumberUtil.toInteger(settingsViewBean.getOtherItemsHeight()));
		settings.setItemsKeepRatio(settingsViewBean.getItemsKeepRatio());
		settings.setInactivityDelay(NumberUtil.toInteger(settingsViewBean.getInactivityDelay()));
		settings.setBannerPosition(settingsViewBean.getBannerPosition());
		settings.setAutoUploadContacts(settingsViewBean.getAutoUploadContacts());
		settings.setHourUploadContacts(settingsViewBean.getHourUploadContacts());
		settings.setEmailSendContacts(settingsViewBean.getEmailSendContacts());
		settings.setSendContactsEmail(settingsViewBean.getSendContactsEmail());
		settings.setAutoUpdateProject(settingsViewBean.getAutoUpdateProject());
		settings.setHourUpdateProject(settingsViewBean.getHourUpdateProject());
		settings.setDisplayWholeMap(settingsViewBean.getDisplayWholeMap());
		settings.setAllowWebPageNavigation(settingsViewBean.getAllowWebPageNavigation());
		settings.setMixPanelKey(settingsViewBean.getMixPanelKey());
		
		settings.setAutoUpdateSoftware(settingsViewBean.getAutoUpdateSoftware());
		settings.setHourUpdateSoftware(settingsViewBean.getHourUpdateSoftware());
		
		settings.setItemsInZone(settingsViewBean.getItemsInZone());
		settings.setShowNameInsteadofIcon(settingsViewBean.getShowNameInsteadofIcon());
		
		settings.setUpdateTryTimes(settingsViewBean.getUpdateTryTimes());
	}
	
	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Settings settings = (Settings) dataBean;
		SettingsViewBean settingsViewBean = (SettingsViewBean) viewBean;
		settingsViewBean.setSettingId(settings.getId());
		settingsViewBean.setClock(NumberUtil.toString(settings.getClock()));
		settingsViewBean.setForceItemSizes(settings.getForceItemSizes());
		settingsViewBean.setLocationsWidth(NumberUtil.toString(settings.getLocationsWidth()));
		settingsViewBean.setLocationsHeight(NumberUtil.toString(settings.getLocationsHeight()));
		settingsViewBean.setOtherItemsWidth(NumberUtil.toString(settings.getOtherItemsWidth()));
		settingsViewBean.setOtherItemsHeight(NumberUtil.toString(settings.getOtherItemsHeight()));
		settingsViewBean.setItemsKeepRatio(settings.getItemsKeepRatio());
		settingsViewBean.setInactivityDelay(NumberUtil.toString(settings.getInactivityDelay()));
		settingsViewBean.setBannerPosition(settings.getBannerPosition());
		settingsViewBean.setAutoUploadContacts(settings.getAutoUploadContacts());
		settingsViewBean.setHourUploadContacts(settings.getHourUploadContacts());
		settingsViewBean.setEmailSendContacts(settings.getEmailSendContacts());
		settingsViewBean.setSendContactsEmail(settings.getSendContactsEmail());
		settingsViewBean.setAutoUpdateProject(settings.getAutoUpdateProject());
		settingsViewBean.setHourUpdateProject(settings.getHourUpdateProject());
		settingsViewBean.setDisplayWholeMap(settings.getDisplayWholeMap());
		settingsViewBean.setAllowWebPageNavigation(settings.getAllowWebPageNavigation());
		settingsViewBean.setMixPanelKey(settings.getMixPanelKey());
		settingsViewBean.setProjectId(settings.getProject() != null ? settings.getProject().getId() : null);
		
		settingsViewBean.setAutoUpdateSoftware(settings.getAutoUpdateSoftware());
		settingsViewBean.setHourUpdateSoftware(settings.getHourUpdateSoftware());
		
		settingsViewBean.setItemsInZone(settings.getItemsInZone());
		settingsViewBean.setShowNameInsteadofIcon(settings.getShowNameInsteadofIcon());
		
		settingsViewBean.setUpdateTryTimes(settings.getUpdateTryTimes());
	}
	
	
	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================

}
