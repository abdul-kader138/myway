package org.hurryapp.quickstart.view.action.device;

import java.util.Map;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.data.model.Device;
import org.hurryapp.quickstart.service.device.DeviceService;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.utils.CertificateUtils;

/**
 * Class which manages resources (used for rights management)
 */
@Unsecured
public class DeviceAction extends BaseCrudAction<DeviceViewBean> {
	
	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		DeviceViewBean deviceViewBean = (DeviceViewBean) viewBean;
		criteriaMap.put("name", deviceViewBean.getName());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		DeviceViewBean deviceViewBean = (DeviceViewBean) viewBean;
		Device device = (Device) dataBean;
		device.setName(deviceViewBean.getName());
		device.setKey(deviceViewBean.getKey());
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Device device = (Device) dataBean;
		DeviceViewBean deviceViewBean = (DeviceViewBean) viewBean;
		deviceViewBean.setName(device.getName());
		deviceViewBean.setKey(device.getKey());
	}
	
	protected void doBeforeSave() throws Exception {
		try{
			CertificateUtils certificateUtils = new CertificateUtils();
			int numberLimit = certificateUtils.getDeviceNumber();
			DeviceService deviceService = (DeviceService)this.entityService;
			if(deviceService.findAll().size() >= numberLimit) throw new Exception("limit arrived");
			
		} catch(Exception e) {
			this.jsonResponse.addError(new JSONError("certificate", super.getTexts("global-messages").getString("device.erreur.certificate.exceed")));
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================

	/**
	 */
//	@SuppressWarnings("unchecked")
//	public String loadByGroupe() throws Exception {
//		jsonResponse = new JSONResponse(toViewBeans(((DeviceService) entityService).findByGroupe(viewBean.getParentId())));
//		return SUCCESS_JSON;
//	}

	public String loadByLicense() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((DeviceService) entityService).findByLicense(viewBean.getParentId())));
		return SUCCESS_JSON;
	}
	
	public String loadByNoLicense() throws Exception {
		jsonResponse = new JSONResponse(toViewBeans(((DeviceService) entityService).findByNoLicense()));
		return SUCCESS_JSON;
	}
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

}