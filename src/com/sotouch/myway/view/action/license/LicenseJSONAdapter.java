package com.sotouch.myway.view.action.license;

import java.util.List;

import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Language;

/**
 */
public class LicenseJSONAdapter implements JSONAdapter {
	private List<Language> languages;
	
	public LicenseJSONAdapter(List<Language> languages) {
		this.languages = languages;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject licenseJson = new JSONObject();

		if (object instanceof License) {
			License license = (License) object;
			licenseJson.put("id", license.getId());
			licenseJson.put("idBis", license.getId());
			licenseJson.put("key", license.getKey());
			licenseJson.put("description", license.getDescription());
			licenseJson.put("orientation", license.getOrientation());
			licenseJson.put("logo", license.getLogo());
			
			// Category contents
			int i = 0;
			for (Language language : languages) {
				LicenseContent licenseContent = getLicenseContent(license.getLicenseContents(), language.getId());
				licenseJson.put("licenseContents["+i+"].languageId", language.getId());
				licenseJson.put("licenseContents["+i+"].languageCode", language.getFlag());
				if(licenseContent == null && language.getDefaultLanguage()) {
					//licenseJson.put("categoryContents["+i+"].id", licenseContent.getId());
					licenseJson.put("licenseContents["+i+"].name", license.getKey());
					licenseJson.put("licenseContents["+i+"].description", license.getDescription());
				}
				else if (licenseContent != null) {
					licenseJson.put("licenseContents["+i+"].id", licenseContent.getId());
					licenseJson.put("licenseContents["+i+"].name", licenseContent.getName());
					licenseJson.put("licenseContents["+i+"].description", licenseContent.getDescription());
				}
				i++;
			}
		}

		return licenseJson;
    }
    
    private LicenseContent getLicenseContent(List<LicenseContent> licenseContents, Integer languageId) {
    	for (LicenseContent licenseContent : licenseContents) {
			if (licenseContent != null && licenseContent.getLanguage().getId().equals(languageId)) {
				return licenseContent;
			}
		}
    	return null;
    }
}
