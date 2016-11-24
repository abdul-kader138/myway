package com.sotouch.myway.view.action.item;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class ItemTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public ItemTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject itemJson = new JSONObject();

		// Item contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) itemJson.put("itemContents["+i+"].name", languagesContentMap.get(i).get("name"));
				if(languagesContentMap.get(i).containsKey("description")) itemJson.put("itemContents["+i+"].description", languagesContentMap.get(i).get("description"));
				if(languagesContentMap.get(i).containsKey("keywords")) itemJson.put("itemContents["+i+"].keywords", languagesContentMap.get(i).get("keywords"));
				if(languagesContentMap.get(i).containsKey("openingDays")) itemJson.put("itemContents["+i+"].openingDays", languagesContentMap.get(i).get("openingDays"));
				i++;
			}
		}

		return itemJson;
    }
    
    private ItemContent getItemContent(List<ItemContent> itemContents, Integer languageId) {
    	for (ItemContent itemContent : itemContents) {
			if (itemContent != null && itemContent.getLanguage().getId().equals(languageId)) {
				return itemContent;
			}
		}
    	return null;
    }
}
