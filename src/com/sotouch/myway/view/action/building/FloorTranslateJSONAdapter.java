package com.sotouch.myway.view.action.building;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Language;

/**
 */
public class FloorTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public FloorTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject floorJson = new JSONObject();

			// Category contents
		int i = 0;
		System.out.println("languagesContentMap count: '" + String.valueOf(languagesContentMap.size()) + "'");
		
		for (Language language : languages) {
			System.out.println("query index: '" + String.valueOf(i) + "'");
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) floorJson.put("floorContents["+i+"].name", languagesContentMap.get(i).get("name"));
				if(languagesContentMap.get(i).containsKey("label")) floorJson.put("floorContents["+i+"].label", languagesContentMap.get(i).get("label"));
			}
			i++;
		}
		return floorJson;
    }
}
