package com.sotouch.myway.view.action.promotion;

import java.util.HashMap;
import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Promotion;
import com.sotouch.myway.data.model.PromotionContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class PromotionTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public PromotionTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
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
				if(languagesContentMap.get(i).containsKey("name")) itemJson.put("promotionContents["+i+"].name", languagesContentMap.get(i).get("name"));
				if(languagesContentMap.get(i).containsKey("description")) itemJson.put("promotionContents["+i+"].description", languagesContentMap.get(i).get("description"));
				if(languagesContentMap.get(i).containsKey("keywords")) itemJson.put("promotionContents["+i+"].keywords", languagesContentMap.get(i).get("keywords"));
				i++;
			}
		}

		return itemJson;
    }
    
    private PromotionContent getItemContent(List<PromotionContent> promotionContents, Integer languageId) {
    	for (PromotionContent promotionContent : promotionContents) {
			if (promotionContent != null && promotionContent.getLanguage().getId().equals(languageId)) {
				return promotionContent;
			}
		}
    	return null;
    }
}
