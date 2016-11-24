package com.sotouch.myway.view.action.event;

import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Event;
import com.sotouch.myway.data.model.EventContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class EventJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public EventJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject eventJson = new JSONObject();

		if (object instanceof Event) {
			Event event = (Event) object;
			eventJson.put("id", event.getId());
			eventJson.put("start", DateUtil.toString(event.getStart(), Constants.DAY_FORMAT));
			eventJson.put("hourStart", DateUtil.toString(event.getStart(), Constants.HOUR_FORMAT));
			eventJson.put("end", DateUtil.toString(event.getEnd(), Constants.DAY_FORMAT));
			eventJson.put("hourEnd", DateUtil.toString(event.getEnd(), Constants.HOUR_FORMAT));
			eventJson.put("image", event.getImage());
			eventJson.put("projectId", event.getProject().getId());
			eventJson.put("itemId", event.getItem() != null ?  event.getItem().getId() : null);
			eventJson.put("item", event.getItem() != null ?  event.getItem().getName() : null);
			
			// Event contents
			int i = 0;
			for (Language language : languages) {
				EventContent eventContent = getEventContent(event.getEventContents(), language.getId());
				eventJson.put("eventContents["+i+"].languageId", language.getId());
				eventJson.put("eventContents["+i+"].languageCode", language.getFlag());
				if (eventContent != null) {
					eventJson.put("eventContents["+i+"].id", eventContent.getId());
					eventJson.put("eventContents["+i+"].name", eventContent.getName());
					eventJson.put("eventContents["+i+"].description", eventContent.getDescription());
					eventJson.put("eventContents["+i+"].keywords", eventContent.getKeywords());
				}
				i++;
			}
		}

		return eventJson;
    }
    
    private EventContent getEventContent(List<EventContent> eventContents, Integer languageId) {
    	for (EventContent eventContent : eventContents) {
			if (eventContent != null && eventContent.getLanguage().getId().equals(languageId)) {
				return eventContent;
			}
		}
    	return null;
    }
}
