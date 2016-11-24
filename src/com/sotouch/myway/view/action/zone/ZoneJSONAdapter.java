package com.sotouch.myway.view.action.zone;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Zone;
import com.sotouch.myway.data.model.ZoneContent;

/**
 */
public class ZoneJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public ZoneJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject zoneJson = new JSONObject();

		if (object instanceof Zone) {
			Zone zone = (Zone) object;
			zoneJson.put("id", zone.getId());
			zoneJson.put("name", zone.getName());
			zoneJson.put("color", zone.getColor());
			zoneJson.put("transparency", zone.getTransparency());
			zoneJson.put("image", zone.getImage());
			zoneJson.put("buildingId", zone.getBuilding().getId());
			
			if(zone.getCategory() != null) zoneJson.put("categoryId", zone.getCategory().getId());
			if(zone.getSubCategory() != null) zoneJson.put("subCategoryId", zone.getSubCategory().getId());
			zoneJson.put("itemsInZone", zone.getItemsInZone() ? "true" : "false");
			
			// Category contents
			int i = 0;
			for (Language language : languages) {
				ZoneContent zoneContent = getZoneContent(zone.getZoneContents(), language.getId());
				zoneJson.put("zoneContents["+i+"].languageId", language.getId());
				zoneJson.put("zoneContents["+i+"].languageCode", language.getFlag());
				if (zoneContent != null) {
					zoneJson.put("zoneContents["+i+"].id", zoneContent.getId());
					zoneJson.put("zoneContents["+i+"].name", zoneContent.getName());
				}
				i++;
			}
		}

		return zoneJson;
    }
    
    private ZoneContent getZoneContent(List<ZoneContent> zoneContents, Integer languageId) {
    	for (ZoneContent zoneContent : zoneContents) {
			if (zoneContent != null && zoneContent.getLanguage().getId().equals(languageId)) {
				return zoneContent;
			}
		}
    	return null;
    }
}
