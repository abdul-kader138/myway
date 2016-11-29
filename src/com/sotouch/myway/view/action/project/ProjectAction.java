package com.sotouch.myway.view.action.project;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.ActionUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.hurryapp.quickstart.utils.ThreeDESCode;
import org.hurryapp.quickstart.view.AccessUtil;
import org.springframework.dao.DataIntegrityViolationException;

import com.mysql.jdbc.StringUtils;
import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Company;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.ProjectContent;
import com.sotouch.myway.service.company.CompanyService;
import com.sotouch.myway.service.event.EventService;
import com.sotouch.myway.service.item.ItemService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.newsletterEmail.NewsletterEmailService;
import com.sotouch.myway.service.project.ProjectService;
import com.sotouch.myway.service.promotion.PromotionService;
import com.sotouch.myway.service.translation.TranslationService;


/**
 * Class which manages the projects
 */
public class ProjectAction extends BaseCrudAction<ProjectViewBean> {

	@Resource (name="projectService")
	private ProjectService projectService;
	
	@Resource (name="companyService")
	private CompanyService companyService;

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Resource (name="translationService")
	private TranslationService translationService;

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="licenseService")
	private LicenseService licenseService;
	
	@Resource (name="itemService")
	private ItemService itemService;
	
	@Resource (name="promotionService")
	private PromotionService promotionService;
	
	@Resource (name="eventService")
	private EventService eventService;
	
	@Resource (name="newsletterEmailService")
	private NewsletterEmailService newsletterEmailService;
	
	@RemoteProperty(jsonAdapter="properties:id,name")
	private List companys;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	private Long nbLicenses;

	private Long nbItems;
	
	private Long nbPromotions;
	
	private Long nbEvents;
	
	private Long nbNewsletterEmails;
	
	private String fileType;
	private static int projectId=8;
	
	@Override
	public String init() throws Exception {
		// Init
		this.doInit();

		
		if (projectId != 0) {
			Date dateStart = (Date) projectService.findById(projectId).getDateStart();
			Date dateExpire = (Date) projectService.findById(projectId).getDateExpire();
			Integer daylength = 86400000;
			Utilisateur user = AccessUtil.getUtilisateur(session);
			if (AccessUtil.canAccessGroup(user, Constants.GROUPE_SUPER_ADMIN) || 
					(/*(dateStart != null) && dateStart.before(new Date(System.currentTimeMillis() - daylength)) 
							&& */(dateExpire != null) && dateExpire.after(new Date(System.currentTimeMillis() - daylength)))) {
				return "success-edit";
			}
			else {
				return "deny-edit";
			}
		}
		else {
			return SUCCESS;
		}
	}



	@Override
	protected void doInit() throws Exception {
		Utilisateur user = AccessUtil.getUtilisateur(session);
//		System.out.println(user.get);
		if (AccessUtil.canAccessGroup(user, Constants.GROUPE_SUPER_ADMIN)) {
			companys = companyService.findAll();
		}
		else {
			companys = new ArrayList<Company>();
			companys.add(user.getCompany());
		}
		languages = languageService.findByProject(projectId);
		nbLicenses = licenseService.countByProject(projectId);
		nbItems= itemService.countByProject(projectId);
		nbPromotions = promotionService.countByProject(projectId);
		nbEvents = eventService.countByProject(projectId);
		nbNewsletterEmails = newsletterEmailService.countByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) throws Exception {
		ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
		criteriaMap.put("name", projectViewBean.getName());
		criteriaMap.put("enabled", Boolean.TRUE);
		if (projectViewBean.getCompanyId() != null && projectViewBean.getCompanyId() == -1) {
			projectViewBean.setCompanyId(null);
		}
		
		Utilisateur user = AccessUtil.getUtilisateur(session);
		if (AccessUtil.canAccessGroup(user, Constants.GROUPE_SUPER_ADMIN)) {
			criteriaMap.put("company.id", projectViewBean.getCompanyId());
		}
		else {
			criteriaMap.put("company.id", user.getCompany().getId());
			criteriaMap.put("users.id", user.getId());
		}
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	public String save() throws Exception {
		try {
			super.execute();
			
			if (jsonResponse.getErrors().size() < 1) {
				// Logo upload
				ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
				if (projectViewBean.getLogo() != null) {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+projectViewBean.getKey();
					String filePath = uploadPath+"/"+projectViewBean.getLogoFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(projectViewBean.getLogo()), uploadedFile);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
		Project project = (Project) dataBean;
		long seconde = 1000;
		long minute = 60 * seconde;
		long hour = 60 * minute;
		long day = 24 * hour;
		if (project.getId() == null) {
			Date date = new Date(System.currentTimeMillis()+(day*365));
			String projectKey = ((ProjectService) entityService).generateProjectKey();
			projectViewBean.setKey(projectKey);
			project.setKey(projectKey);
			project.setDateExpire(date);
			project.setEnabled(Boolean.TRUE);
		}
		project.setName(projectViewBean.getName());
		project.setOrderNumber(projectViewBean.getOrderNumber());
		project.setSalesforceLink(projectViewBean.getSalesforceLink());
		
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DAY_FORMAT);
		if(projectViewBean.getDateStart() != null && !StringUtils.isEmptyOrWhitespaceOnly(projectViewBean.getDateStart())) project.setDateStart(sdf.parse(projectViewBean.getDateStart()));
		if(projectViewBean.getDateExpire() != null && !StringUtils.isEmptyOrWhitespaceOnly(projectViewBean.getDateExpire())) project.setDateExpire(sdf.parse(projectViewBean.getDateExpire()));
		
		
		if (projectViewBean.getLogoFileName() != null) {
			project.setLogo(projectViewBean.getLogoFileName());
		}
		if (projectViewBean.getNewsletterLogoFileName() != null) {
			project.setLogo(projectViewBean.getNewsletterLogoFileName());
		}
		if (projectViewBean.getCreditsImageFileName() != null) {
			project.setLogo(projectViewBean.getCreditsImageFileName());
		}
		if (projectViewBean.getCompanyId() != null) {
			project.setCompany((Company) companyService.findById(Integer.valueOf(projectViewBean.getCompanyId())));
		}
		
		if(projectViewBean.getAdsDateExpire() != null) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projectViewBean.getAdsDateExpire());
			project.setAdsDateExpire(date);
		}
		
		// Project contents
		List<ProjectContent> projectContents = new ArrayList<ProjectContent>();
		ProjectContent projectContent = null;
		int i = 0;
		for (ProjectContentViewBean projectContentViewBean : projectViewBean.getProjectContents()) {
			projectContent = new ProjectContent();
			projectContent.setProject(project);
			if (projectContentViewBean != null) {
				projectContent.setId(projectContentViewBean.getId());
				projectContent.setLanguage(new Language(projectContentViewBean.getLanguageId()));
				projectContent.setNewsletterTerms(projectContentViewBean.getNewsletterTerms());
			}
			projectContent.setIndex(i++);
			projectContents.add(projectContent);
		}
		project.setProjectContents(projectContents);
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Project project = (Project) dataBean;
		ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
		projectViewBean.setKey(project.getKey());
		projectViewBean.setName(project.getName());
		projectViewBean.setOrderNumber(project.getOrderNumber());
		projectViewBean.setSalesforceLink(project.getSalesforceLink());
		//projectViewBean.setDateExpire(project.getDateExpire().toString());
		
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DAY_FORMAT);
		
		projectViewBean.setDateStart(project.getDateStart() != null ? sdf.format(project.getDateStart()) : "");
		projectViewBean.setDateExpire(project.getDateExpire() != null ? sdf.format(project.getDateExpire()) : "");
		projectViewBean.setAdsDateExpire(project.getAdsDateExpire() != null ? sdf.format(project.getAdsDateExpire()) : "");
		projectViewBean.setLogo(project.getLogo() != null ? project.getLogo().substring(project.getLogo().lastIndexOf("/")+1) : "");
		projectViewBean.setCompany(project.getCompany() != null ? project.getCompany().getName() : "");
		projectViewBean.setCompanyId(project.getCompany() != null ? project.getCompany().getId() : null);
	}

	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			Long nbLanguages = languageService.countByProject(this.viewBean.getId());
			Long nbLicenses = licenseService.countByProject(this.viewBean.getId());
			Long nbItems = itemService.countByProject(this.viewBean.getId());
			Long nbPromotions = promotionService.countByProject(this.viewBean.getId());
			Long nbEvents = eventService.countByProject(this.viewBean.getId());
			Long nbNewsletterEmails = newsletterEmailService.countByProject(this.viewBean.getId());
			String renewProjectUrl = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_RENEW_PROJECT_URL);
			String buyLicenseUrl = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_BUY_LICENSE_URL);
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new ProjectJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languages, nbLanguages, nbLicenses, nbItems, nbPromotions, nbEvents, nbNewsletterEmails, renewProjectUrl, buyLicenseUrl));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}

	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("project.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================
	public String select() {		
		return SUCCESS_JSON;
	}

	/**
	 * Link user to selected project
	 */
	public String saveUsers() throws Exception {
		ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
		Integer projectId = projectViewBean.getId();
		List<Integer> userIds = ActionUtil.toListIds(projectViewBean.getUserIds());

		// Update projecte's users
		((ProjectService) entityService).saveUsers(projectId, userIds);

		// Success message
		jsonResponse.setMessage(super.getTexts("global-messages").getString("message.succes"));

		return SUCCESS_JSON;
	}

	/**
	 * Files upload (logo, newsletter logo, credits image)
	 * @return
	 * @throws Exception
	 */
	public String uploadFiles() throws Exception {
		ProjectViewBean projectViewBean = (ProjectViewBean) viewBean;
		Project project = (Project) entityService.findById((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		List<String> fileUrls = new ArrayList<String>();

		// Logo upload
		String logoFileName = projectViewBean.getLogoFileName();
		if (!StringUtil.isEmpty(logoFileName) && !logoFileName.toLowerCase().endsWith(".png") && !logoFileName.toLowerCase().endsWith(".gif") && !logoFileName.toLowerCase().endsWith(".jpg")) {
			jsonResponse.getErrors().add(new JSONError("viewBean.logo", resources.getString("project.erreur.logo.format")));
		}
		else {
			if (projectViewBean.getLogo() != null) {
				if (ImageResizer.getWeight(projectViewBean.getLogo()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
					jsonResponse.addError(new JSONError("viewBean.logo", getText("common.error.image.maxSize")));
				}
				else {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_LOGO;
					String filePath = uploadPath+"/"+logoFileName;
					FileUtils.deleteDirectory(new File(uploadPath));
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(projectViewBean.getLogo()), uploadedFile);

					// Resize image
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_PROJECT_LOGO_WIDTH, Constants.IMAGE_SIZE_PROJECT_LOGO_HEIGHT, null);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);

					project.setLogo(newFileName);
					fileUrls.add(Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_LOGO+"/"+newFileName);
				}
			}
			else {
				fileUrls.add("");
			}
		}
		
		// Newsletter logo upload
		String newsletterLogoFileName = projectViewBean.getNewsletterLogoFileName();
		if (!StringUtil.isEmpty(newsletterLogoFileName) && !newsletterLogoFileName.toLowerCase().endsWith(".png") && !newsletterLogoFileName.toLowerCase().endsWith(".gif") && !newsletterLogoFileName.toLowerCase().endsWith(".jpg")) {
			jsonResponse.getErrors().add(new JSONError("viewBean.newsletterLogo", resources.getString("project.erreur.logo.format")));
		}
		else {
			if (projectViewBean.getNewsletterLogo() != null) {
				if (ImageResizer.getWeight(projectViewBean.getNewsletterLogo()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
					jsonResponse.addError(new JSONError("viewBean.creditsImage", getText("common.error.file.maxSize")));
				}
				else {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_NEWSLETTER_LOGO;
					String filePath = uploadPath+"/"+newsletterLogoFileName;
					FileUtils.deleteDirectory(new File(uploadPath));
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(projectViewBean.getNewsletterLogo()), uploadedFile);
	
					// Resize image
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_NEWSLETTER_LOGO_WIDTH, Constants.IMAGE_SIZE_NEWSLETTER_LOGO_HEIGHT, null);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
	
					project.setNewsletterLogo(newFileName);
					fileUrls.add(Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_NEWSLETTER_LOGO+"/"+newFileName);
				}
			}
			else {
				fileUrls.add("");
			}
		}

		// Credits images upload
		String creditsImageFileName = projectViewBean.getCreditsImageFileName();
		if (!StringUtil.isEmpty(creditsImageFileName) && !creditsImageFileName.toLowerCase().endsWith(".png") && !creditsImageFileName.toLowerCase().endsWith(".gif") && !creditsImageFileName.toLowerCase().endsWith(".jpg")) {
			jsonResponse.getErrors().add(new JSONError("viewBean.creditsImage", resources.getString("project.erreur.logo.format")));
		}
		else {
			if (projectViewBean.getCreditsImage() != null) {
				if (ImageResizer.getWeight(projectViewBean.getCreditsImage()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
					jsonResponse.addError(new JSONError("viewBean.newsletterLogo", getText("common.error.file.maxSize")));
				}
				else {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_CREDITS_IMAGE;
					String filePath = uploadPath+"/"+creditsImageFileName;
					FileUtils.deleteDirectory(new File(uploadPath));
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(projectViewBean.getCreditsImage()), uploadedFile);
	
					// Resize image
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_CREDITS_LOGO_WIDTH, Constants.IMAGE_SIZE_CREDITS_LOGO_HEIGHT, null);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
	
					project.setCreditsImage(newFileName);
					fileUrls.add(Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_PROJECT_CREDITS_IMAGE+"/"+newFileName);
				}
			}
			else {
				fileUrls.add("");
			}
		}
		
		// Update project
		entityService.saveOrUpdate(project);
		
		// Return file URLs
		jsonResponse.setDatas(fileUrls);
		return SUCCESS_JSON;
	}
	
	/**
	 * Delete a file (logo, newsletter logo, credits image)
	 * @return
	 * @throws Exception
	 */
	public String deleteFile() throws Exception {
		Project project = (Project) entityService.findById(viewBean.getId());
		String projectDir = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+project.getKey();

		// Logo
		if (fileType.equals("logo")) {
			project.setLogo(null);
			FileUtils.deleteDirectory(new File(projectDir+"/"+Constants.DIR_PROJECT_LOGO));
		}
		// Newsletter logo upload
		else if (fileType.equals("newsletterLogo")) {
			project.setNewsletterLogo(null);
			FileUtils.deleteDirectory(new File(projectDir+"/"+Constants.DIR_PROJECT_NEWSLETTER_LOGO));
		}
		// Credits images upload
		else if (fileType.equals("creditsImage")) {
			project.setCreditsImage(null);
			FileUtils.deleteDirectory(new File(projectDir+"/"+Constants.DIR_PROJECT_CREDITS_IMAGE));
		}
		
		// Update the projet
		entityService.saveOrUpdate(project);
		
		return SUCCESS_JSON;
	}
	
	
	/**
	 * Translate wordings
	 * @return
	 * @throws Exception
	 */
	public String translate() throws Exception {
		try {
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			HashMap<Integer, HashMap<String, String>> languagesContentMap = new HashMap<Integer,HashMap<String, String>>();
			
			String sourceLanguageId = request.getParameter("sourceLanguageId");
			Language sourceLanguage = (Language) languageService.findById(Integer.valueOf(sourceLanguageId));
			// System.out.println("Source language: '" + sourceLanguage.getName() + "' id: " + sourceLanguageId);
			int i = 0;
			int j = Integer.valueOf(request.getParameter("tabIndex"));
			for (@SuppressWarnings("unused") Language language : languages) {
				String targetLanguageId = request.getParameter("viewBean.projectContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				if(isEmpty(request.getParameter("viewBean.projectContents["+i+"].newsletterTerms"))) contentMap.put("newsletterTerms", translationService.translate(request.getParameter("viewBean.projectContents["+j+"].newsletterTerms"), sourceLanguage.getName(), targetLanguage.getName()));
	
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = this.entityService.findById(this.viewBean.getId());
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new ProjectTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
		}
		catch (Exception e) {
			this.checkTranslateException(e);
		}
		return null;
	}
	

	private boolean isEmpty(String str) {
		if (str.isEmpty() || str.equals("<br>") || str.equals("<br/>") || str.equals("\n") || str.equals("\r") || str.equals("\r\n") || str.equals("<p>&nbsp;</p>")){
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void checkTranslateException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("project.erreur.translate")));
		}
		else {
			throw e;
		}
	}
	
	
	
	//=========================================================================
	// ACCESSORS
	//=========================================================================

	public void setCompanys(List companys) {
		this.companys = companys;
	}
	public List getCompanys() {
		return companys;
	}

	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
	
	public Long getNbLicenses() {
		return nbLicenses;
	}
	public void setNbLicenses(Long nbLicenses) {
		this.nbLicenses = nbLicenses;
	}
	
	public Long getNbItems() {
		return nbItems;
	}
	public void setNbItems(Long nbItems) {
		this.nbItems = nbItems;
	}
	
	public Long getNbPromotions() {
		return nbPromotions;
	}
	public void setNbPromotions(Long nbPromotions) {
		this.nbPromotions = nbPromotions;
	}
	
	public Long getNbEvents() {
		return nbEvents;
	}
	public void setNbEvents(Long nbEvents) {
		this.nbEvents = nbEvents;
	}
	
	public Long getNbNewsletterEmails() {
		return nbNewsletterEmails;
	}
	public void setNbNewsletterEmails(Long nbNewsletterEmails) {
		this.nbNewsletterEmails = nbNewsletterEmails;
	}

	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
