package com.sotouch.myway.view.action.category;

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
public class CategoryTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public CategoryTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject categoryJson = new JSONObject();

			// Category contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) categoryJson.put("categoryContents["+i+"].name", languagesContentMap.get(i).get("name"));
				i++;
			}
		}

		return categoryJson;
    }
    
    private CategoryContent getcategoryContent(List<CategoryContent> categoryContents, Integer languageId) {
    	for (CategoryContent categoryContent : categoryContents) {
			if (categoryContent != null && categoryContent.getLanguage().getId().equals(languageId)) {
				return categoryContent;
			}
		}
    	return null;
    }
}
