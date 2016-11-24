package com.sotouch.myway.view.action.event;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.EventContent;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class EventTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public EventTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject itemJson = new JSONObject();

			// Event contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) itemJson.put("eventContents["+i+"].name", languagesContentMap.get(i).get("name"));
				if(languagesContentMap.get(i).containsKey("description")) itemJson.put("eventContents["+i+"].description", languagesContentMap.get(i).get("description"));
				if(languagesContentMap.get(i).containsKey("keywords")) itemJson.put("eventContents["+i+"].keywords", languagesContentMap.get(i).get("keywords"));
				i++;
			}
		}

		return itemJson;
    }
    
    private EventContent getItemContent(List<EventContent> eventContents, Integer languageId) {
    	for (EventContent eventContent : eventContents) {
			if (eventContent != null && eventContent.getLanguage().getId().equals(languageId)) {
				return eventContent;
			}
		}
    	return null;
    }
}
