package com.sotouch.myway.view.action.license;

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

/**
 */
public class LicenseTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public LicenseTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject licenseJson = new JSONObject();

			// Category contents
		int i = 0;
		System.out.println("languagesContentMap count: '" + String.valueOf(languagesContentMap.size()) + "'");
		
		for (Language language : languages) {
			System.out.println("query index: '" + String.valueOf(i) + "'");
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) licenseJson.put("licenseContents["+i+"].name", languagesContentMap.get(i).get("name"));
				if(languagesContentMap.get(i).containsKey("description")) licenseJson.put("licenseContents["+i+"].description", languagesContentMap.get(i).get("description"));
			}
			i++;
		}
		return licenseJson;
    }
}
