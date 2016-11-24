package org.hurryapp.quickstart.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Device;

import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Settings;

public class DeviceDAO extends BaseDAO<Device> {

	public DeviceDAO() {
		super();
	}
	
	public List<Device> findByLicense(Integer licenseId) {
		return super.findByNamedQuery("select.device.by.license", new Object[]{licenseId});
	}
	
	public List<Device> findByNoLicense() {
		return super.findByNamedQuery("select.device.by.no.license", new Object[]{});
	}
}
