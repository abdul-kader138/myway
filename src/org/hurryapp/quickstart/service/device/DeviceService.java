package org.hurryapp.quickstart.service.device;

import java.util.List;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.DeviceDAO;
import org.hurryapp.quickstart.data.model.Device;

public class DeviceService extends BaseCrudService {

	public DeviceService() {
	}
	
	public List<Device> findByLicense(Integer licenseId) {
		return ((DeviceDAO) super.entityDao).findByLicense(licenseId);
	}
	
	public List<Device> findByNoLicense() {
		return ((DeviceDAO) super.entityDao).findByNoLicense();
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================

}
