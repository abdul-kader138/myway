package org.hurryapp.quickstart.view.action.device;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class DeviceViewBean extends BaseViewBean {
	
	private String name;
	private String key;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("device.erreur.name.obligatoire")));
		}
		
		if (StringUtil.isEmpty(key)) {
			errors.add(new JSONError("viewBean.key", resources.getString("device.erreur.key.obligatoire")));
		}
		
		return errors;
	}
}
