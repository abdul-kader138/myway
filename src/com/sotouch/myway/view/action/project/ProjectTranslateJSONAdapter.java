package com.sotouch.myway.view.action.project;

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
import com.sotouch.myway.data.model.ProjectContent;

/**
 */
public class ProjectTranslateJSONAdapter implements JSONAdapter {
	private String projectKey;
	private HashMap<Integer, HashMap<String, String>> languagesContentMap;
	private List<Language> languages;
	
	public ProjectTranslateJSONAdapter(String projectKey, HashMap<Integer, HashMap<String, String>> languagesContentMap, List<Language> languages) {
		this.projectKey = projectKey;
		this.languagesContentMap = languagesContentMap;
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject itemJson = new JSONObject();

		// Project contents
		int i = 0;
		for (Language language : languages) {
			if(languagesContentMap.containsKey(i)) {
				if(languagesContentMap.get(i).containsKey("newsletterTerms")) itemJson.put("projectContents["+i+"].newsletterTerms", languagesContentMap.get(i).get("newsletterTerms"));
				i++;
			}
		}

		return itemJson;
    }
    
    private ProjectContent getProjectContent(List<ProjectContent> projectContents, Integer languageId) {
    	for (ProjectContent projectContent : projectContents) {
			if (projectContent != null && projectContent.getLanguage().getId().equals(languageId)) {
				return projectContent;
			}
		}
    	return null;
    }
}
