package org.hurryapp.quickstart.service.software;

import java.util.List;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.RessourceDAO;
import org.hurryapp.quickstart.data.dao.SoftwareDAO;
import org.hurryapp.quickstart.data.model.Software;

public class SoftwareService extends BaseCrudService {

	public SoftwareService() {
	}
	
	public Software findByVersion(String version) {
		return ((SoftwareDAO)super.entityDao).findByVersion(version);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
}
