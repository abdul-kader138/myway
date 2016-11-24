package com.sotouch.myway.view.action.zone;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.ZoneContent;

/**
 */
public class ZoneTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public ZoneTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject zoneJson = new JSONObject();

			// Category contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) zoneJson.put("zoneContents["+i+"].name", languagesContentMap.get(i).get("name"));
				i++;
			}
		}

		return zoneJson;
    }
    
    /*private ZoneContent getZoneContent(List<ZoneContent> zoneContents, Integer languageId) {
    	for (ZoneContent zoneContent : zoneContents) {
			if (zoneContent != null && zoneContent.getLanguage().getId().equals(languageId)) {
				return zoneContent;
			}
		}
    	return null;
    }*/
}
