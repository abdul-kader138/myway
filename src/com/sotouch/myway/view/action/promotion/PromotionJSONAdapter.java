package com.sotouch.myway.view.action.promotion;

import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Promotion;
import com.sotouch.myway.data.model.PromotionContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class PromotionJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public PromotionJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject promotionJson = new JSONObject();

		if (object instanceof Promotion) {
			Promotion promotion = (Promotion) object;
			promotionJson.put("id", promotion.getId());
			promotionJson.put("start", DateUtil.toString(promotion.getStart(), Constants.DAY_FORMAT));
			promotionJson.put("hourStart", DateUtil.toString(promotion.getStart(), Constants.HOUR_FORMAT));
			promotionJson.put("end", DateUtil.toString(promotion.getEnd(), Constants.DAY_FORMAT));
			promotionJson.put("hourEnd", DateUtil.toString(promotion.getEnd(), Constants.HOUR_FORMAT));
			promotionJson.put("image", promotion.getImage());
			promotionJson.put("projectId", promotion.getProject().getId());
			promotionJson.put("itemId", promotion.getItem() != null ?  promotion.getItem().getId() : null);
			promotionJson.put("item", promotion.getItem() != null ?  promotion.getItem().getName() : null);
			
			// Promotion contents
			int i = 0;
			for (Language language : languages) {
				PromotionContent promotionContent = getPromotionContent(promotion.getPromotionContents(), language.getId());
				promotionJson.put("promotionContents["+i+"].languageId", language.getId());
				promotionJson.put("promotionContents["+i+"].languageCode", language.getFlag());
				if (promotionContent != null) {
					promotionJson.put("promotionContents["+i+"].id", promotionContent.getId());
					promotionJson.put("promotionContents["+i+"].name", promotionContent.getName());
					promotionJson.put("promotionContents["+i+"].description", promotionContent.getDescription());
					promotionJson.put("promotionContents["+i+"].keywords", promotionContent.getKeywords());
				}
				i++;
			}
		}

		return promotionJson;
    }
    
    private PromotionContent getPromotionContent(List<PromotionContent> promotionContents, Integer languageId) {
    	for (PromotionContent promotionContent : promotionContents) {
			if (promotionContent != null && promotionContent.getLanguage().getId().equals(languageId)) {
				return promotionContent;
			}
		}
    	return null;
    }
}
