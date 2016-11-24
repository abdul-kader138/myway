package com.sotouch.myway.view.action.icon;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Icon;

/**
 */
public class IconJSONAdapter implements JSONAdapter {

	public JSONObject toJSONObject(Object object) throws Exception {
    	Icon icon = (Icon) object;
		JSONObject jsonObject = new JSONObject();
		
		StringBuffer iconName = new StringBuffer();
		if (icon.getItemType() != null) {
			iconName.append(icon.getItemType().getName()).append(" > ");
			jsonObject.put("typeId", icon.getItemType().getId());
		}
		else {
			iconName.append(icon.getIconGroup().getName()).append(" > ");
		}

		jsonObject.put("id", icon.getId());
		jsonObject.put("name", iconName.append(icon.getName()).toString());

		StringBuffer path = new StringBuffer();
		if (icon.getItemType() != null) {
			path.append("/").append(Constants.DIR_ICONS).append("/").append(icon.getId()).append("/").append(icon.getIcon());
		}
		else {
			if(icon.getIconGroup().getType() == 0) {
				path.append("/").append(Constants.DIR_PROJECTS).append("/").append(icon.getIconGroup().getProject().getKey()).append("/").append(Constants.DIR_ICONS).append("/").append(icon.getId()).append("/").append(icon.getIcon());
			}
			else {
				path.append("/").append(Constants.DIR_PROJECTS).append("/").append(icon.getIconGroup().getProject().getKey()).append("/").append(Constants.DIR_ICONS).append("/").append("custom").append("/").append(icon.getIcon());
				
			}
		}
		jsonObject.put("path", path.toString());

		return jsonObject;
    }
}
