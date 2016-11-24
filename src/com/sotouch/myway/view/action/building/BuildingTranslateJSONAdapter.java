package com.sotouch.myway.view.action.building;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Language;

/**
 */
public class BuildingTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public BuildingTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject buildingJson = new JSONObject();

			// Category contents
		int i = 0;
		System.out.println("languagesContentMap count: '" + String.valueOf(languagesContentMap.size()) + "'");
		
		for (Language language : languages) {
			System.out.println("query index: '" + String.valueOf(i) + "'");
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) buildingJson.put("buildingContents["+i+"].name", languagesContentMap.get(i).get("name"));
			}
			i++;
		}
		return buildingJson;
    }
}
