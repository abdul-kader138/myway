package org.hurryapp.quickstart.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.quickstart.data.model.Ressource;
import org.hurryapp.quickstart.data.model.Software;

import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Settings;

public class SoftwareDAO extends BaseDAO<Software> {

	public SoftwareDAO() {
		super();
	}
	
	public Software findByVersion(String version) {
		return (Software) super.findUniqueResultByNamedQuery("select.softare.by.version", new Object[] { version });
	}
}
