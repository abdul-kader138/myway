package com.sotouch.myway.view.action.building;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.BuildingContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class BuildingJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public BuildingJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject buildingJson = new JSONObject();

		if (object instanceof Building) {
			Building building = (Building) object;
			buildingJson.put("id", building.getId());
			buildingJson.put("index", building.getIndex());
			buildingJson.put("name", building.getName());
			
			// Category contents
			int i = 0;
			for (Language language : languages) {
				BuildingContent buildingContent = getBuildingContent(building.getBuildingContents(), language.getId());
				buildingJson.put("buildingContents["+i+"].languageId", language.getId());
				buildingJson.put("buildingContents["+i+"].languageCode", language.getFlag());
				if(buildingContent == null && language.getDefaultLanguage()) {
					buildingJson.put("buildingContents["+i+"].name", building.getName());
				}
				else if (buildingContent != null) {
					buildingJson.put("buildingContents["+i+"].id", buildingContent.getId());
					buildingJson.put("buildingContents["+i+"].name", buildingContent.getName());
				}
				i++;
			}
		}

		return buildingJson;
    }
    
    private BuildingContent getBuildingContent(List<BuildingContent> buildingContents, Integer languageId) {
    	for (BuildingContent buildingContent : buildingContents) {
			if (buildingContent != null && buildingContent.getLanguage().getId().equals(languageId)) {
				return buildingContent;
			}
		}
    	return null;
    }
}
