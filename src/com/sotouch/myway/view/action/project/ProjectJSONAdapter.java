package com.sotouch.myway.view.action.project;

import java.util.List;

import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONObject;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Event;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.ProjectContent;
import com.sotouch.myway.data.model.Promotion;

/**
 */
public class ProjectJSONAdapter implements JSONAdapter {
	private String projectKey;
	private String projectLogo;
	private List<Language> languages;
	private Long nbLanguages;
	private Long nbLicenses;
	private Long nbItems;
	private Long nbPromotions;
	private Long nbEvents;
	private Long nbNewsletterEmails;
	private String renewProjectUrl;
	private String buyLicenseUrl;
	
	public ProjectJSONAdapter(String projectKey, List<Language> languages, Long nbLanguages, Long nbLicenses, Long nbItems, Long nbPromotions, Long nbEvents, Long nbNewsletterEmails, String renewProjectUrl, String buyLicenseUrl) {
		this.projectKey = projectKey;
		this.languages = languages;
		this.nbLanguages = nbLanguages;
		this.nbLicenses = nbLicenses;
		this.nbItems= nbItems;
		this.nbPromotions = nbPromotions;
		this.nbEvents = nbEvents;
		this.nbNewsletterEmails = nbNewsletterEmails;
		this.renewProjectUrl = renewProjectUrl;
		this.buyLicenseUrl = buyLicenseUrl;
	}
	
    public JSONObject toJSONObject(Object object) throws Exception {
		JSONObject projectJson = new JSONObject();

		if (object instanceof Project) {
			Project project = (Project) object;
			projectJson.put("id", project.getId());
			projectJson.put("key", project.getKey());
			projectJson.put("name", project.getName());
			projectJson.put("orderNumber", project.getOrderNumber());
			projectJson.put("salesforceLink", project.getSalesforceLink());
			projectJson.put("dateStart", DateUtil.toString(project.getDateStart(), Constants.DAY_FORMAT));
			projectJson.put("dateExpire", DateUtil.toString(project.getDateExpire(), Constants.DAY_FORMAT));
			projectJson.put("logo", project.getLogo() != null ? project.getLogo() : "");
			projectJson.put("logoUrl", project.getLogo() != null ? Constants.DIR_PROJECTS+"/"+projectKey+"/"+Constants.DIR_PROJECT_LOGO+"/"+project.getLogo() : "");
			projectJson.put("newsletterLogo", project.getNewsletterLogo() != null ? project.getNewsletterLogo() : "");
			projectJson.put("newsletterLogoUrl", project.getNewsletterLogo() != null ? Constants.DIR_PROJECTS+"/"+projectKey+"/"+Constants.DIR_PROJECT_NEWSLETTER_LOGO+"/"+project.getNewsletterLogo() : "");
			projectJson.put("creditsImage", project.getCreditsImage() != null ? project.getCreditsImage() : "");
			projectJson.put("creditsImageUrl", project.getCreditsImage() != null ? Constants.DIR_PROJECTS+"/"+projectKey+"/"+Constants.DIR_PROJECT_CREDITS_IMAGE+"/"+project.getCreditsImage() : "");
			projectJson.put("company", project.getCompany().getName());
			projectJson.put("companyId", project.getCompany().getId());
			projectJson.put("previewLogoUrl", (project.getLogo() != null) && (project.getKey() != null) ? Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_PROJECT_LOGO+"/"+project.getLogo() : "");
			projectJson.put("nbLanguages", nbLanguages);
			projectJson.put("nbLicenses", nbLicenses);
			projectJson.put("nbItems", nbItems);
			projectJson.put("nbPromotions", nbPromotions);
			projectJson.put("nbEvents", nbEvents);
			projectJson.put("nbNewsletterEmails", nbNewsletterEmails);
			projectJson.put("renewProjectUrl", renewProjectUrl);
			projectJson.put("buyLicenseUrl", buyLicenseUrl);

			// Project contents
			int i = 0;
			for (Language language : languages) {
				ProjectContent projectContent = getProjectContent(project.getProjectContents(), language.getId());
				projectJson.put("projectContents["+i+"].languageId", language.getId());
				projectJson.put("projectContents["+i+"].languageCode", language.getFlag());
				if (projectContent != null) {
					projectJson.put("projectContents["+i+"].id", projectContent.getId());
					projectJson.put("projectContents["+i+"].newsletterTerms", projectContent.getNewsletterTerms());
				}
				i++;
			}
		}

		return projectJson;
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
