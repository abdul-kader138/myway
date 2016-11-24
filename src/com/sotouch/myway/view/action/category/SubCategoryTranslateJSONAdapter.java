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
import com.sotouch.myway.data.model.SubCategoryContent;

/**
 */
public class SubCategoryTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public SubCategoryTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject categoryJson = new JSONObject();

			// Subcategory contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("name")) categoryJson.put("subCategoryContents["+i+"].name", languagesContentMap.get(i).get("name"));
				i++;
			}
		}

		return categoryJson;
    }
    
    private SubCategoryContent getSubCategoryContent(List<SubCategoryContent> subCategoryContents, Integer languageId) {
    	for (SubCategoryContent subCategoryContent : subCategoryContents) {
			if (subCategoryContent != null && subCategoryContent.getLanguage().getId().equals(languageId)) {
				return subCategoryContent;
			}
		}
    	return null;
    }
}
