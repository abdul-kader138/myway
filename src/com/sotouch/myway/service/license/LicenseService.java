package com.sotouch.myway.service.license;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.model.Device;
import org.hurryapp.quickstart.data.model.Groupe;
import org.hurryapp.quickstart.data.model.Ressource;

import com.sotouch.myway.data.dao.LicenseDAO;
import com.sotouch.myway.data.model.License;

public class LicenseService extends BaseCrudService {

	public LicenseService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<License> findByProject(Integer projectId) {
		return ((LicenseDAO) entityDao).findByProject(projectId);
	}
	
	public Long countByProject(Integer projectId) {
		return ((LicenseDAO) entityDao).countByProject(projectId);
	}
	
	public void saveDevices(Integer licenseId, List<Integer> deviceIds) throws Exception {
		if (licenseId != null) {
			License license = (License) entityDao.findById(licenseId);
			// Ajout des ressources au groupe
			Set<Device> devices = license.getDevices();
			for(Device device : devices) {
				device.setLicense(null);
			}
			license.setDevices(devices);
			entityDao.saveOrUpdate(license);
			entityDao.getHibernateTemplate().flush();
			
			devices = new HashSet<Device>();
			for(Integer deviceId: deviceIds) {
				devices.add(new Device(deviceId));
			}
			license.setDevices(devices);
			entityDao.saveOrUpdate(license);
		}
	}
}
