package com.sotouch.myway.view.action.zone;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.Zone;
import com.sotouch.myway.data.model.ZoneContent;
import com.sotouch.myway.service.building.BuildingService;
import com.sotouch.myway.service.building.FloorService;
import com.sotouch.myway.service.category.CategoryService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.view.action.item.ItemViewBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.springframework.dao.DataIntegrityViolationException;

public class ZoneAction extends BaseCrudAction<ZoneViewBean>
{

  @Resource(name="languageService")
  private LanguageService languageService;

  @Resource(name="translationService")
  private TranslationService translationService;
  
  @Resource(name="categoryService")
  private CategoryService categoryService;

  @Resource(name="buildingService")
  private BuildingService buildingService;
  
  @Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
  
  @RemoteProperty(jsonAdapter="properties:id,name,flag")
  private List languages;
  
  @RemoteProperty(jsonAdapter="properties:id,name")
  private List categories;

  protected void doInit()
    throws Exception
  {
    Integer projectId = (Integer)this.session.get("SESSION_PROJECT_ID");
    this.languages = this.languageService.findByProject(projectId); 
    //this.categories = this.categoryService.findByProject(projectId);
  }

  protected void populateSearchCriteria(Map<String, Object> criteriaMap)
  {
    ZoneViewBean zoneViewBean = (ZoneViewBean)this.viewBean;
    criteriaMap.put("name", zoneViewBean.getName());
    criteriaMap.put("building.id", zoneViewBean.getBuildingId());
  }

  protected String getDefaultSort()
  {
    return "name";
  }

  protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception
  {
    ZoneViewBean zoneViewBean = (ZoneViewBean)viewBean;
    Zone zone = (Zone)dataBean;
    zone.setName(zoneViewBean.getName());
    zone.setColor(zoneViewBean.getColor());
    zone.setTransparency(zoneViewBean.getTransparency());
    zone.setItemsInZone(zoneViewBean.getItemsInZone() == null ? false : zoneViewBean.getItemsInZone());
    
    if (zoneViewBean.getBuildingId() != null) {
      zone.setBuilding((Building)this.buildingService.findById(Integer.valueOf(zoneViewBean.getBuildingId().intValue())));
    }
    
    if (zoneViewBean.getImageFileName() != null && zoneViewBean.getImage() != null) {
		if (ImageResizer.getWeight(zoneViewBean.getImage()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
			jsonResponse.addError(new JSONError("viewBean.image", getText("common.error.image.maxSize")));
		}
		else {
			zone.setImage(zoneViewBean.getImageFileName());
		}
	}
    
    if (zoneViewBean.getCategoryId() != null) {
    	zone.setCategory(new Category(Integer.valueOf(zoneViewBean.getCategoryId().intValue())));
    }
    else {
    	zone.setCategory(null);
    }

    if (zoneViewBean.getSubCategoryId() != null) {
    	zone.setSubCategory(new SubCategory(Integer.valueOf(zoneViewBean.getSubCategoryId().intValue())));
    }
    else {
    	zone.setSubCategory(null);
    }

    Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));

    List zoneContents = new ArrayList();
    ZoneContent zoneContent = null;
    int i = 0;
    for (ZoneContentViewBean zoneContentViewBean : zoneViewBean.getZoneContents()) {
      zoneContent = new ZoneContent();
      zoneContent.setZone(zone);
      if (zoneContentViewBean != null) {
        zoneContent.setId(zoneContentViewBean.getId());
        zoneContent.setLanguage(new Language(zoneContentViewBean.getLanguageId()));
        zoneContent.setName(zoneContentViewBean.getName());

        if (((defaultLanguage == null) && (i == 0)) || ((defaultLanguage != null) && (defaultLanguage.getFlag().equals(zoneContentViewBean.getLanguageCode())))) {
          zone.setName(zoneContentViewBean.getName());
        }
      }
      zoneContent.setIndex(Integer.valueOf(i++));
      zoneContents.add(zoneContent);
    }
    zone.setZoneContents(zoneContents);

    Set items = new LinkedHashSet();
    for (ItemViewBean itemViewBean : zoneViewBean.getItems()) {
      items.add(new Item(itemViewBean.getId()));
    }
    zone.setItems(items);
  }

  protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception
  {
    Zone zone = (Zone)dataBean;
    ZoneViewBean zoneViewBean = (ZoneViewBean)viewBean;

    Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
    zoneViewBean.setName(zone.getName());
    List<ZoneContent> zoneContents = zone.getZoneContents();
    if ((defaultLanguage != null) && (defaultLanguage.getFlag() != null) && (zoneContents != null)) {
      for (ZoneContent zoneContent : zoneContents) {
        if (zoneContent != null)
        {
          if (defaultLanguage.getFlag().equals(zoneContent.getLanguage().getFlag())) {
            zoneViewBean.setName(zoneContent.getName());
            break;
          }
        }
      }
    }
    zoneViewBean.setColor(zone.getColor());
    zoneViewBean.setTransparency(zone.getTransparency());
    zoneViewBean.setImage(zone.getImage() != null ? zone.getImage().substring(zone.getImage().lastIndexOf("/")+1) : "");
    zoneViewBean.setBuildingId(zone.getBuilding() != null ? zone.getBuilding().getId() : null);
    zoneViewBean.setItemsInZone(zone.getItemsInZone());
    
    zoneViewBean.setCategory(zone.getCategory() != null ? zone.getCategory().getName() : "");
    zoneViewBean.setCategoryId(zone.getCategory() != null ? zone.getCategory().getId() : null);
    zoneViewBean.setSubCategory(zone.getSubCategory() != null ? zone.getSubCategory().getName() : "");
    zoneViewBean.setSubCategoryId(zone.getSubCategory() != null ? zone.getSubCategory().getId() : null);
    
    String visual = "/";
    if (zone.getImage() != null) {
    	visual = visual + "project-exports/" + this.session.get("SESSION_PROJECT_KEY") + "/" + Constants.DIR_ZONES + "/" + zone.getId() + "/" + zone.getImage();
    }
    zoneViewBean.setVisual(visual);
  }
  
  @Override
	public String save() throws Exception {
		try {
			super.execute();
			
			if (jsonResponse.getErrors().size() < 1) {
				// Image upload
				ZoneViewBean zoneViewBean = (ZoneViewBean) viewBean;
				if (zoneViewBean.getImage() != null) {
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
					String dir = uploadPath+"/"+Constants.DIR_ZONES+"/"+zoneViewBean.getId();
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+zoneViewBean.getImageFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(zoneViewBean.getImage()), uploadedFile);

					// Resize image
					//filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_ICON_WIDTH, Constants.IMAGE_SIZE_ICON_WIDTH, Constants.IMAGE_SIZE_FLOOR_NB_PIXELS);
					String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
					Zone zone = (Zone) entityService.findById(zoneViewBean.getId());
					zone.setImage(newFileName);
					entityService.saveOrUpdate(zone);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}

  public String edit() throws Exception
  {
    try {
      Object entity = this.entityService.findById(this.viewBean.getId());
      List languages = this.languageService.findByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
      this.jsonResponse.setData(entity);
      this.jsonResponse.encode(this.response, new ZoneJSONAdapter(languages));
    }
    catch (Exception e) {
      checkEditException(e);
    }
    return null;
  }

  protected void checkDeleteException(Exception e) throws Exception
  {
    if ((e instanceof DataIntegrityViolationException)) {
      this.jsonResponse.addError(new JSONError(null, this.resources.getString("zone.erreur.delete")));
    }
    else
      throw e;
  }

  public String translate()
    throws Exception
  {
    try
    {
      List<Language> languages = this.languageService.findByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
      HashMap languagesContentMap = new HashMap();

      String sourceLanguageId = this.request.getParameter("sourceLanguageId");
      Language sourceLanguage = (Language)this.languageService.findById(Integer.valueOf(sourceLanguageId));
      int i = 0;
      int j = Integer.valueOf(this.request.getParameter("tabIndex")).intValue();
      for (Language language : languages) {
        String targetLanguageId = this.request.getParameter("viewBean.zoneContents[" + i + "].languageId");
        Language targetLanguage = (Language)this.languageService.findById(Integer.valueOf(targetLanguageId));

        HashMap contentMap = new HashMap();

        if (isEmpty(this.request.getParameter("viewBean.zoneContents[" + i + "].name"))) contentMap.put("name", this.translationService.translate(this.request.getParameter("viewBean.zoneContents[" + j + "].name"), sourceLanguage.getName(), targetLanguage.getName()));

        languagesContentMap.put(Integer.valueOf(i), contentMap);
        i++;
      }

      Object entity = new Zone();
      this.jsonResponse.setData(entity);
      this.jsonResponse.encode(this.response, new ZoneTranslateJSONAdapter((String)this.session.get("SESSION_PROJECT_KEY"), languagesContentMap, languages));
    }
    catch (Exception e)
    {
      checkTranslateException(e);
    }
    return null;
  }

  private boolean isEmpty(String str) {
    if ((str.equals("")) || (str.equals("<br>")) || (str.equals("<br/>")) || (str.equals("\n")) || (str.equals("\r")) || (str.equals("\r\n")) || (str.equals("<p>&nbsp;</p>"))) {
      return true;
    }

    return false;
  }

  protected void checkTranslateException(Exception e) throws Exception
  {
    if ((e instanceof DataIntegrityViolationException)) {
      this.jsonResponse.addError(new JSONError(null, this.resources.getString("zone.erreur.translate")));
    }
    else
      throw e;
  }

  public List getLanguages()
  {
    return this.languages;
  }
  public void setLanguages(List languages) {
    this.languages = languages;
  }
  
  
  	public void setCategories(List categories) {
  		this.categories = categories;
	}
	public List getCategories() {
	  return this.categories;
	}
}
