package com.sotouch.myway.view.action.category;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.SubCategoryContent;

/**
 */
public class SubCategoryJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public SubCategoryJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject subCategoryJson = new JSONObject();

		if (object instanceof SubCategory) {
			SubCategory subCategory = (SubCategory) object;
			subCategoryJson.put("id", subCategory.getId());
			subCategoryJson.put("name", subCategory.getName());
			subCategoryJson.put("color", subCategory.getColor());
			subCategoryJson.put("categoryId", subCategory.getCategory().getId());
			
			// SubCategory contents
			int i = 0;
			for (Language language : languages) {
				SubCategoryContent subCategoryContent = getSubCategoryContent(subCategory.getSubCategoryContents(), language.getId());
				subCategoryJson.put("subCategoryContents["+i+"].languageId", language.getId());
				subCategoryJson.put("subCategoryContents["+i+"].languageCode", language.getFlag());
				if (subCategoryContent != null) {
					subCategoryJson.put("subCategoryContents["+i+"].id", subCategoryContent.getId());
					subCategoryJson.put("subCategoryContents["+i+"].name", subCategoryContent.getName());
				}
				i++;
			}
		}

		return subCategoryJson;
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
