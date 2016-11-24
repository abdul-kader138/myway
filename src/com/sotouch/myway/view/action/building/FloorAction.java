package com.sotouch.myway.view.action.building;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONUtil;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.BuildingContent;
import com.sotouch.myway.data.model.Floor;
import com.sotouch.myway.data.model.FloorContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.service.building.BuildingService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.view.action.newsletterEmail.NewsletterEmailAction;

/**
 * Class which manages the floors
 */
public class FloorAction extends BaseCrudAction<FloorViewBean> {

	@Resource (name="buildingService")
	private BuildingService buildingService;

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;

	@Resource (name="languageService")
	private LanguageService languageService;
	
	@Resource (name="translationService")
	private TranslationService translationService;
	
	@RemoteProperty(jsonAdapter="properties:id,name,flag")
	private List languages;
	
	private static Log log = LogFactory.getLog(FloorAction.class);

	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		languages = languageService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		FloorViewBean floorViewBean = (FloorViewBean) viewBean;
		criteriaMap.put("name", floorViewBean.getName());
		criteriaMap.put("building.id", floorViewBean.getBuildingId());
	}

	@Override
	protected String getDefaultSort() {
		return "index";
	}
	
	@Override
	protected String getDefaultDir()
	{
		return "DESC";
	}
	
	@Override
	public String save() throws Exception {
		try {
			super.execute();
			
			if (jsonResponse.getErrors().size() < 1) {
				// Image upload
				FloorViewBean floorViewBean = (FloorViewBean) viewBean;
				if (floorViewBean.getImage() != null) {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					String dir = uploadPath+"/"+Constants.DIR_FLOORS+"/"+floorViewBean.getId();
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+floorViewBean.getImageFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(floorViewBean.getImage()), uploadedFile);

					// Resize image
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_FLOOR_WIDTH, Constants.IMAGE_SIZE_FLOOR_HEIGHT, Constants.IMAGE_SIZE_FLOOR_NB_PIXELS);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
					Floor floor = (Floor) entityService.findById(floorViewBean.getId());
					floor.setImage(newFileName);
					entityService.saveOrUpdate(floor);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}
	
	@Override
    public String updateModified() throws Exception {
		log.error("Update modified");
		if (!StringUtil.isEmpty(this.getSelectedObjects())) {
			try {
				String[] jsonObjects = this.getSelectedObjects().split(";");
				for (String jsonObject : jsonObjects) {
					// Coversion JSON --> viewBean
					log.info("Selected Object : " + jsonObject);
					viewBean = (BaseViewBean) JSONUtil.toObject(jsonObject, viewBeanClass);
					// Indexe update
					log.info("Viewbean: " + viewBean);
					
					Floor floor = (Floor) entityService.findById(viewBean.getId());
					if (floor != null) {
						populateDataBean(floor, viewBean);
						entityService.saveOrUpdate(floor);
					}
				}				
			} catch (Exception ex) {
				log.error("Update modified error: ", ex);
			}
		}
		return SUCCESS_JSON;
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		FloorViewBean floorViewBean = (FloorViewBean) viewBean;
		Floor floor = (Floor) dataBean;
		floor.setName(floorViewBean.getName());
		floor.setLabel(floorViewBean.getLabel());
		
		if (floorViewBean.getImageFileName() != null && floorViewBean.getImage() != null) {
			if (ImageResizer.getWeight(floorViewBean.getImage()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
				jsonResponse.addError(new JSONError("viewBean.image", getText("common.error.image.maxSize")));
			}
			else {
				floor.setImage(floorViewBean.getImageFileName());
			}
		}
		floor.setIndex(NumberUtil.toInteger(floorViewBean.getIndex()));
		if (floorViewBean.getBuildingId() != null) {
			floor.setBuilding((Building) buildingService.findById(Integer.valueOf(floorViewBean.getBuildingId())));
		}
		
		// Get default language
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));

		// Item contents
		List<FloorContent> floorContents = new ArrayList<FloorContent>();
		FloorContent floorContent = null;
		
		int i = 0;
		if (floorViewBean.getFloorContents() != null) {
			for (FloorContentViewBean floorContentViewBean : floorViewBean.getFloorContents()) {
				floorContent = new FloorContent();
				floorContent.setFloor(floor);
				
				if (floorContentViewBean != null) {
					floorContent.setId(floorContentViewBean.getId());
					floorContent.setLanguage(new Language(floorContentViewBean.getLanguageId()));
					floorContent.setName(floorContentViewBean.getName());
					//floorContent.setLabel(floorContentViewBean.getLabel());
					
					if ((defaultLanguage == null && i == 0) || (defaultLanguage != null && defaultLanguage.getFlag().equals(floorContentViewBean.getLanguageCode()))) {
						floor.setName(floorContentViewBean.getName());
					}
				}
				
				floorContent.setIndex(i++);
				floorContents.add(floorContent);
			}
			floor.setFloorContents(floorContents);
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Floor floor = (Floor) dataBean;
		FloorViewBean floorViewBean = (FloorViewBean) viewBean;
		floorViewBean.setName(floor.getName());
		floorViewBean.setLabel(floor.getLabel());
		
		Language defaultLanguage = languageService.findDefaultByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
		FloorContent floorContent = getFloorContent(floor.getFloorContents(), defaultLanguage.getId());
		
		if(floorContent != null) {
			floorViewBean.setName(floorContent.getName());
		}
		
		//floorViewBean.setName(floor.getName());
		//floorViewBean.setLabel(floor.getLabel());
		floorViewBean.setImage(floor.getImage() != null ? floor.getImage().substring(floor.getImage().lastIndexOf("/")+1) : "");
		floorViewBean.setIndex(NumberUtil.toString(floor.getIndex()));
		floorViewBean.setBuildingId(floor.getBuilding() != null ? floor.getBuilding().getId() : null);
	}
	
	@Override
	public String edit() throws Exception {
		try {
			Object entity = this.entityService.findById(this.viewBean.getId());
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new FloorJSONAdapter(languages));
		}
		catch (Exception e) {
			this.checkEditException(e);
		}
		return null;
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("floor.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	public String translate() throws Exception {
		try {
			
			List<Language> languages = languageService.findByProject((Integer) session.get(Constants.SESSION_KEY_PROJECT_ID));
			HashMap<Integer, HashMap<String, String>> languagesContentMap = new HashMap<Integer,HashMap<String, String>>();
			
			String sourceLanguageId = request.getParameter("sourceLanguageId");
			Language sourceLanguage = (Language) languageService.findById(Integer.valueOf(sourceLanguageId));
			int i = 0;
			int j = Integer.valueOf(request.getParameter("tabIndex"));
			for (@SuppressWarnings("unused") Language language : languages) {
				if(i == j) {
					++i;
					continue;
				}
				
				String targetLanguageId = request.getParameter("viewBean.floorContents["+i+"].languageId");
				Language targetLanguage = (Language) languageService.findById(Integer.valueOf(targetLanguageId));
				// System.out.println("Target language: '" + targetLanguage.getName() + "' id: " + targetLanguageId);
				HashMap<String, String> contentMap = new HashMap<String, String>();
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				
				if(isEmpty(request.getParameter("viewBean.floorContents["+i+"].name"))) contentMap.put("name", translationService.translate(request.getParameter("viewBean.floorContents["+j+"].name"), sourceLanguage.getName(), targetLanguage.getName()));
				if(isEmpty(request.getParameter("viewBean.floorContents["+i+"].label"))) contentMap.put("label", translationService.translate(request.getParameter("viewBean.floorContents["+j+"].label"), sourceLanguage.getName(), targetLanguage.getName()));
				
				System.out.println("languagesContentMap put index: '" + String.valueOf(i) + "'");
				languagesContentMap.put(i, contentMap);
				i++;
			}
		
			Object entity = new License();
			this.jsonResponse.setData(entity);
			this.jsonResponse.encode(response, new FloorTranslateJSONAdapter((String) session.get(Constants.SESSION_KEY_PROJECT_KEY), languagesContentMap, languages));
			
		}
		catch (Exception e) {
			this.checkTranslateException(e);
		}
		return null;
	}
	

	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================
	public String preview() throws Exception {
		// Image upload in tmp directory
		FloorViewBean floorViewBean = (FloorViewBean) viewBean;
		if (floorViewBean.getImage() != null) {
			String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
			String filePath = "/tmp/"+DateUtil.toString(new Date(), "yyyyMMddHHmmssS")+floorViewBean.getImageFileName();
			File uploadedFile = new File(uploadPath+filePath);
			FileUtils.copyFile(new File(floorViewBean.getImage()), uploadedFile);
			
			jsonResponse.setData(filePath);
		}
		else if (floorViewBean.getId() != null) {
			Floor floor = (Floor) entityService.findById(floorViewBean.getId());
			jsonResponse.setData("/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY)+"/"+Constants.DIR_FLOORS+"/"+floor.getId()+"/"+floor.getImage());
		}
		
		return SUCCESS_JSON;
	}
	
	private FloorContent getFloorContent(List<FloorContent> floorContents, Integer languageId) {
    	for (FloorContent floorContent : floorContents) {
			if (floorContent != null && floorContent.getLanguage().getId().equals(languageId)) {
				return floorContent;
			}
		}
    	return null;
    }

	private boolean isEmpty(String str) {
		if (str.equals("") || str.equals("<br>") || str.equals("<br/>") || str.equals("\n") || str.equals("\r") || str.equals("\r\n") || str.equals("<p>&nbsp;</p>")){
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void checkTranslateException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("category.erreur.translate")));
		}
		else {
			throw e;
		}
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public List getLanguages() {
		return languages;
	}
	public void setLanguages(List languages) {
		this.languages = languages;
	}
}
