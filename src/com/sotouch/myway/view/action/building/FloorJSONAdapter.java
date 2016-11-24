package com.sotouch.myway.view.action.building;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.BuildingContent;
import com.sotouch.myway.data.model.Floor;
import com.sotouch.myway.data.model.FloorContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class FloorJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public FloorJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject floorJson = new JSONObject();

		if (object instanceof Floor) {
			Floor floor = (Floor) object;
			floorJson.put("id", floor.getId());
			floorJson.put("index", floor.getIndex());
			floorJson.put("name", floor.getName());
			floorJson.put("label", floor.getLabel());
			floorJson.put("image", floor.getImage());
			
			// Category contents
			int i = 0;
			for (Language language : languages) {
				FloorContent floorContent = getFloorContent(floor.getFloorContents(), language.getId());
				floorJson.put("floorContents["+i+"].languageId", language.getId());
				floorJson.put("floorContents["+i+"].languageCode", language.getFlag());
				if(floorContent == null && language.getDefaultLanguage()) {
					floorJson.put("floorContents["+i+"].name", floor.getName());
					floorJson.put("floorContents["+i+"].label", floor.getLabel());
				}
				else if (floorContent != null) {
					floorJson.put("floorContents["+i+"].id", floorContent.getId());
					floorJson.put("floorContents["+i+"].name", floorContent.getName());
					floorJson.put("floorContents["+i+"].label", floorContent.getLabel());
				}
				i++;
			}
		}

		return floorJson;
    }
    
    private FloorContent getFloorContent(List<FloorContent> floorContents, Integer languageId) {
    	for (FloorContent floorContent : floorContents) {
			if (floorContent != null && floorContent.getLanguage().getId().equals(languageId)) {
				return floorContent;
			}
		}
    	return null;
    }
}
