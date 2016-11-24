package com.sotouch.myway.view.action.newsletterEmail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONUtil;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.NewsletterEmail;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.newsletterEmail.NewsletterEmailService;

/**
 * Class which manages the newsletter emails
 */
@Unsecured
public class NewsletterEmailAction extends BaseCrudAction<NewsletterEmailViewBean> {
	
	private String projectKey;
	private String contacts;

	private static Log log = LogFactory.getLog(NewsletterEmailAction.class);

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="licenseService")
	private LicenseService licenseService;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@RemoteProperty(jsonAdapter="properties:id,key")
	private List<License> licenses;
	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		languages = languageService.findByProject(projectId);
		licenses = licenseService.findByProject(projectId);
		for(License license : licenses) {
			license.setKey(getLicenseName(license));
		}
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		NewsletterEmailViewBean newsletterEmailViewBean = (NewsletterEmailViewBean) viewBean;
		criteriaMap.put("email", newsletterEmailViewBean.getEmail());
		criteriaMap.put("project.id", newsletterEmailViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "email";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		NewsletterEmailViewBean newsletterEmailViewBean = (NewsletterEmailViewBean) viewBean;
		NewsletterEmail newsletterEmail = (NewsletterEmail) dataBean;
		newsletterEmail.setName(newsletterEmailViewBean.getName());
		newsletterEmail.setFirstName(newsletterEmailViewBean.getFirstName());
		newsletterEmail.setEmail(newsletterEmailViewBean.getEmail());
		newsletterEmail.setCity(newsletterEmailViewBean.getCity());
		newsletterEmail.setGender(newsletterEmailViewBean.getGenderId());
		newsletterEmail.setCategories(newsletterEmailViewBean.getCategories());
		newsletterEmail.setItems(newsletterEmailViewBean.getItems());
		if (newsletterEmailViewBean.getProjectId() != null) {
			newsletterEmail.setProject(new Project(Integer.valueOf(newsletterEmailViewBean.getProjectId())));
		}
		if (newsletterEmailViewBean.getLicenseId() != null) {
			newsletterEmail.setLicense(new License(Integer.valueOf(newsletterEmailViewBean.getLicenseId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		NewsletterEmail newsletterEmail = (NewsletterEmail) dataBean;
		NewsletterEmailViewBean newsletterEmailViewBean = (NewsletterEmailViewBean) viewBean;
		newsletterEmailViewBean.setName(newsletterEmail.getName());
		newsletterEmailViewBean.setFirstName(newsletterEmail.getFirstName());
		newsletterEmailViewBean.setEmail(newsletterEmail.getEmail());
		newsletterEmailViewBean.setCity(newsletterEmail.getCity());
		newsletterEmailViewBean.setGenderId(newsletterEmail.getGender());
		//newsletterEmailViewBean.setGender(newsletterEmail.getGender());
		newsletterEmailViewBean.setCategories(newsletterEmail.getCategories());
		newsletterEmailViewBean.setItems(newsletterEmail.getItems());
		newsletterEmailViewBean.setProjectId(newsletterEmail.getProject() != null ? newsletterEmail.getProject().getId() : null);
		
		newsletterEmailViewBean.setLicenseId(newsletterEmail.getLicense() != null ? newsletterEmail.getLicense().getId() : null);
		newsletterEmailViewBean.setLicenseName("");
		
		if(newsletterEmail.getLicense() != null) {
			newsletterEmailViewBean.setLicenseName(getLicenseName(newsletterEmail.getLicense()));
		}
	}
	
	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================
	protected String excelExtension = "xls";
	public InputStream excelStream;

	public String export() throws IOException {
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		excelStream = ((NewsletterEmailService) entityService).export((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID), defaultLanguage);
		return EXCEL;
	}

	public String getExcelContentType() {
	    return excelExtension == "xlsx" ? "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" : "application/vnd.ms-excel";
	}

	public String getExcelFileName() {
	    return "newsLetter-"+session.get(Constants.SESSION_KEY_PROJECT_NAME)+"-"+DateUtil.toString(new Date(), "yyyyMMdd")+"."+excelExtension;
	}

	//=========================================================================
	// SUBSCRIPTION SERVICE
	//=========================================================================
	/**
	 * Add a contact
	 * request params : viewBean.name=Bernard&viewBean.firstName=Florian&viewBean.email:flo@so-touch.com&viewBean.projectId=7
	 */
	public String addContact() throws Exception {
		try {
			NewsletterEmailViewBean newsletterEmailViewBean = (NewsletterEmailViewBean) viewBean;
			NewsletterEmail newsletterEmail = ((NewsletterEmailService) this.entityService).findByEmail(newsletterEmailViewBean.getEmail());
			if (newsletterEmail == null) {
				newsletterEmail = new NewsletterEmail();
			}
			
			this.populateDataBean(newsletterEmail, newsletterEmailViewBean);
			//newsletterEmail.setProject(new Project((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID)));
			
			// Acc�s db
			this.entityService.saveOrUpdate(this.toDataBean(this.viewBean));
		}
		catch (Exception e) {
			log.error("Erreur lors de l'ajout d'un contact", e);
			return ERROR_TXT;
		}
		return SUCCESS_TXT;
	}

	/**
	 * Add several contacts
	 * request params : contacts={name:'Bernard',firstName:'Florian',email:'flo@so-touch.com'};{name:'Lescure',firstName:'Julien',email:'ju@so-touch.com'}&projectKey=trial-xxxxxxxx-xxxxxxx-xxxxxxx
	 */
	public String addContacts() {
		try {
			// Retreive the project
			Project project = projectService.findByKey(this.projectKey);
			
			// Add contacts
			if (!StringUtil.isEmpty(this.contacts)) {
				String[] jsonObjects = this.contacts.split(";");
				for (String jsonObject : jsonObjects) {
					// Coversion JSON --> viewBean
					this.viewBean = (BaseViewBean) JSONUtil.toObject(jsonObject, this.viewBeanClass);
					// ViewBean validation
					List<JSONError> errors = this.viewBean.validate(this.resources);
					if (!errors.isEmpty()) {
						this.jsonResponse.addErrors(errors);
					}
					else {
						// Copy properties from the viewBean to the dataBean
						NewsletterEmail newsletterEmail = ((NewsletterEmailService) this.entityService).findByEmail(((NewsletterEmailViewBean) this.viewBean).getEmail());
						if (newsletterEmail == null) {
							newsletterEmail = new NewsletterEmail();
						}
						this.populateDataBean(newsletterEmail, this.viewBean);
						newsletterEmail.setProject(project);
						
						// Update the dataBean
						this.entityService.saveOrUpdate(newsletterEmail);
					}
				}
			}
		}
		catch (Exception e) {
			log.error("Erreur lors de l'ajout de contacts", e);
			return ERROR_TXT;
		}
		return SUCCESS_TXT;
	}
	
	public String getLicenseName(License license) {
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		for(LicenseContent licenseContent : license.getLicenseContents()) {
			if(licenseContent.getLanguage().getCode().equals(defaultLanguage.getCode())) {
				return licenseContent.getName();
			}
		}
		return null;
	}


	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	public List getLanguages() {
		return languages;
	}
	
	public void setLanguages(List languages) {
		this.languages = languages;
	}
	
	public List getLicenses() {
		return licenses;
	}
	public void setLicenses(List licenses) {
		this.licenses = licenses;
	}
}
