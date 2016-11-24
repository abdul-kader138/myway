package com.sotouch.myway.service.softwareUpdate;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.SoftwareUpdateDAO;
import com.sotouch.myway.data.model.SoftwareUpdate;

public class SoftwareUpdateService extends BaseCrudService {

	public SoftwareUpdateService() {
	}
	
	public SoftwareUpdate findByLicense(Integer licenseId) {
		return ((SoftwareUpdateDAO)super.entityDao).findByLicense(licenseId);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
}
