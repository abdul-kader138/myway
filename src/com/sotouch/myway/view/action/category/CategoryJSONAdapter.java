package com.sotouch.myway.view.action.category;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class CategoryJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public CategoryJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject categoryJson = new JSONObject();

		if (object instanceof Category) {
			Category category = (Category) object;
			categoryJson.put("id", category.getId());
			categoryJson.put("name", category.getName());
			categoryJson.put("color", category.getColor());
			categoryJson.put("projectId", category.getProject().getId());
			
			// Category contents
			int i = 0;
			for (Language language : languages) {
				CategoryContent categoryContent = getCategoryContent(category.getCategoryContents(), language.getId());
				categoryJson.put("categoryContents["+i+"].languageId", language.getId());
				categoryJson.put("categoryContents["+i+"].languageCode", language.getFlag());
				if (categoryContent != null) {
					categoryJson.put("categoryContents["+i+"].id", categoryContent.getId());
					categoryJson.put("categoryContents["+i+"].name", categoryContent.getName());
				}
				i++;
			}
		}

		return categoryJson;
    }
    
    private CategoryContent getCategoryContent(List<CategoryContent> categoryContents, Integer languageId) {
    	for (CategoryContent categoryContent : categoryContents) {
			if (categoryContent != null && categoryContent.getLanguage().getId().equals(languageId)) {
				return categoryContent;
			}
		}
    	return null;
    }
}
