package org.hurryapp.quickstart.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;
import com.sotouch.myway.data.model.License;

public class Device extends BaseDataBean {

	private String name;
	private String key;
	private License license;

	public Device() {
		super();
	}

	public Device(Integer id) {
		super(id);
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public License getLicense() {
		return this.license;
	}

	public void setLicense(License license) {
		this.license = license;
	}
	
	
}
