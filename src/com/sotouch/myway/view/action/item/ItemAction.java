package com.sotouch.myway.view.action.item;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Ad;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Icon;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.ItemType;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.Playlist;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.Zone;
import com.sotouch.myway.service.category.CategoryService;
import com.sotouch.myway.service.icon.IconService;
import com.sotouch.myway.service.item.ItemService;
import com.sotouch.myway.service.itemType.ItemTypeService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.translation.DefaultTranslationException;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.service.zone.ZoneService;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.service.config.ConfigParameterService;
import org.springframework.dao.DataIntegrityViolationException;

public class ItemAction extends BaseCrudAction<ItemViewBean>
{

  @Resource(name="itemTypeService")
  private ItemTypeService itemTypeService;

  @Resource(name="categoryService")
  private CategoryService categoryService;
  
  @Resource(name="zoneService")
  private ZoneService zoneService;

  @Resource(name="languageService")
  private LanguageService languageService;

  @Resource(name="iconService")
  private IconService iconService;

  @Resource(name="configParameterService")
  private ConfigParameterService configParameterService;

  @Resource(name="translationService")
  private TranslationService translationService;

  @RemoteProperty(jsonAdapter="properties:id,name")
  private List itemTypes;

  @RemoteProperty(jsonAdapter="properties:id,name")
  private List categories;
  
  @RemoteProperty(jsonAdapter="properties:id,name")
  private List zones;

  @RemoteProperty(jsonAdapter="properties:id,name,flag")
  private List languages;

  @RemoteProperty(jsonAdapter="com.sotouch.myway.view.action.icon.IconJSONAdapter")
  private List iconsCommon;

  @RemoteProperty(jsonAdapter="com.sotouch.myway.view.action.icon.IconJSONAdapter")
  private List iconsProject;

  protected void doInit()
    throws Exception
  {
    this.itemTypes = this.itemTypeService.findAll();
    Integer projectId = (Integer)this.session.get("SESSION_PROJECT_ID");
    this.categories = this.categoryService.findByProject(projectId);
    this.languages = this.languageService.findByProject(projectId);
    this.zones = this.zoneService.findByProject(projectId);

    this.iconsCommon = new ArrayList();
    this.iconsProject = this.iconService.findByProject(projectId);
  }

  protected void populateSearchCriteria(Map<String, Object> criteriaMap)
  {
    ItemViewBean itemViewBean = (ItemViewBean)this.viewBean;
    criteriaMap.put("\\search", itemViewBean.getName());
    criteriaMap.put("project.id", itemViewBean.getProjectId());
  }

  protected String getDefaultSort()
  {
    return "name";
  }

  protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception
  {
    ItemViewBean itemViewBean = (ItemViewBean)viewBean;
    Item item = (Item)dataBean;
    item.setAddress(itemViewBean.getAddress());
    item.setPhoneNumber(itemViewBean.getPhoneNumber());
    item.setEmail(itemViewBean.getEmail());
    item.setHourBegin(DateUtil.toDate(itemViewBean.getHourBegin(), "hh:mm a"));
    item.setHourEnd(DateUtil.toDate(itemViewBean.getHourEnd(), "hh:mm a"));
    item.setPhotos(itemViewBean.getPhotos());
    item.setPhotosDir(itemViewBean.getPhotosDir());
    item.setDisabledAccess(Boolean.valueOf(itemViewBean.isDisabledAccess()));

    item.setType(Integer.valueOf(1));

    if (itemViewBean.getItemTypeId() != null) {
      item.setItemType(new ItemType(Integer.valueOf(itemViewBean.getItemTypeId().intValue())));
    }

    if (itemViewBean.getProjectId() != null) {
      item.setProject(new Project(Integer.valueOf(itemViewBean.getProjectId().intValue())));
    }

    if (itemViewBean.getCategoryId() != null) {
      item.setCategory(new Category(Integer.valueOf(itemViewBean.getCategoryId().intValue())));
    }
    else {
      item.setCategory(null);
    }

    if (itemViewBean.getSubCategoryId() != null) {
      item.setSubCategory(new SubCategory(Integer.valueOf(itemViewBean.getSubCategoryId().intValue())));
    }
    else {
      item.setSubCategory(null);
    }
    
    if (itemViewBean.getZoneId() != null) {
    	item.setZone(new Zone(Integer.valueOf(itemViewBean.getZoneId().intValue())));
    }
    else {
    	item.setZone(null);
    }

    if (itemViewBean.getIconId() != null) {
      item.setIcon(new Icon(Integer.valueOf(itemViewBean.getIconId().intValue())));
    }

    Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));

    List itemContents = new ArrayList();
    ItemContent itemContent = null;
    int i = 0;
    for (ItemContentViewBean itemContentViewBean : itemViewBean.getItemContents()) {
      itemContent = new ItemContent();
      itemContent.setItem(item);
      if (itemContentViewBean != null) {
        itemContent.setId(itemContentViewBean.getId());
        itemContent.setLanguage(new Language(itemContentViewBean.getLanguageId()));
        itemContent.setName(itemContentViewBean.getName());
        itemContent.setDescription(itemContentViewBean.getDescription());
        itemContent.setKeywords(itemContentViewBean.getKeywords());
        itemContent.setOpeningDays(itemContentViewBean.getOpeningDays());
        itemContent.setUrl(itemContentViewBean.getUrl());

        if (((defaultLanguage == null) && (i == 0)) || ((defaultLanguage != null) && (defaultLanguage.getFlag().equals(itemContentViewBean.getLanguageCode())))) {
          item.setName(itemContentViewBean.getName());
        }
      }
      itemContent.setIndex(Integer.valueOf(i++));
      itemContents.add(itemContent);
    }
    item.setItemContents(itemContents);

    if (Constants.ITEM_TYPE_LOCATION.equals(itemViewBean.getItemTypeId()))
    {
      if (itemViewBean.getLogo() != null)
        if (ImageResizer.getWeight(itemViewBean.getLogo()) > 10.0D) {
          this.jsonResponse.addError(new JSONError("viewBean.logo", getText("common.error.image.maxSize")));
        }
        else {
          item.setLogo(itemViewBean.getLogoFileName());
          String uploadPath = (String)this.configParameterService.findParameter("paths", "externalFilesPath") + "/" + "project-exports" + "/" + this.session.get("SESSION_PROJECT_KEY");
          String dir = uploadPath + "/" + "items" + "/" + itemViewBean.getPhotosDir() + "/" + "icon";
          FileUtils.deleteDirectory(new File(dir));
          String filePath = dir + "/" + itemViewBean.getLogoFileName();
          File uploadedFile = new File(filePath);
          FileUtils.copyFile(new File(itemViewBean.getLogo()), uploadedFile);
        }
    }
  }

  protected void populateViewBean(BaseViewBean viewBean, Object dataBean)
    throws Exception
  {
    Item item = (Item)dataBean;
    ItemViewBean itemViewBean = (ItemViewBean)viewBean;
    itemViewBean.setName(item.getName());
    itemViewBean.setLogo(item.getLogo());
    itemViewBean.setAddress(item.getAddress());
    itemViewBean.setPhoneNumber(item.getPhoneNumber());
    itemViewBean.setEmail(item.getEmail());
    itemViewBean.setHourBegin(DateUtil.toString(item.getHourBegin(), "hh:mm a"));
    itemViewBean.setHourEnd(DateUtil.toString(item.getHourEnd(), "hh:mm a"));

    itemViewBean.setPhotosDir(item.getPhotosDir());
    itemViewBean.setDisabledAccess(item.getDisabledAccess().booleanValue());
    itemViewBean.setType(item.getType());
    itemViewBean.setItemType(item.getItemType() != null ? item.getItemType().getName() : "");
    itemViewBean.setItemTypeId(item.getItemType() != null ? item.getItemType().getId() : null);
    itemViewBean.setProject(item.getProject() != null ? item.getProject().getName() : "");
    itemViewBean.setProjectId(item.getProject() != null ? item.getProject().getId() : null);
    itemViewBean.setCategory(item.getCategory() != null ? item.getCategory().getName() : "");
    itemViewBean.setCategoryId(item.getCategory() != null ? item.getCategory().getId() : null);
    itemViewBean.setSubCategory(item.getSubCategory() != null ? item.getSubCategory().getName() : "");
    itemViewBean.setSubCategoryId(item.getSubCategory() != null ? item.getSubCategory().getId() : null);
    
    itemViewBean.setZone(item.getZone() != null ? item.getZone().getName() : "");
    itemViewBean.setZoneId(item.getZone() != null ? item.getZone().getId() : null);
    
    itemViewBean.setIcon(item.getIcon() != null ? item.getIcon().getName() : "");
    itemViewBean.setIconId(item.getIcon() != null ? item.getIcon().getId() : null);

    String keywords = null;
    Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
    List<ItemContent> itemContents = item.getItemContents();
    for (ItemContent itemContent : itemContents) {
      if ((itemContent != null) && (itemContent.getLanguage() != null) && (
        (defaultLanguage == null) || (itemContent.getLanguage().getFlag().equals(defaultLanguage.getFlag())))) {
        keywords = itemContent.getKeywords();
        itemViewBean.setName(itemContent.getName());
        break;
      }
    }

    if ((keywords == null) && (itemContents.size() > 0)) {
      keywords = ((ItemContent)itemContents.get(0)).getKeywords();
    }
    itemViewBean.setKeywords(keywords);

    String visual = "/";
    if (item.getIcon() != null) {
      if (item.getIcon().getItemType() != null) {
        visual = visual + "icons/" + item.getIcon().getId() + "/" + item.getIcon().getIcon();
      }
      else {
        visual = visual + "project-exports/" + this.session.get("SESSION_PROJECT_KEY") + "/" + "icons" + "/" + item.getIcon().getId() + "/" + item.getIcon().getIcon();
      }
    }
    else {
      visual = visual + "project-exports/" + this.session.get("SESSION_PROJECT_KEY") + "/" + "items" + "/" + item.getPhotosDir() + "/" + "icon" + "/" + item.getLogo();
    }

    itemViewBean.setVisual(visual);
  }

  public String edit() throws Exception
  {
    try {
      Object entity = this.entityService.findById(this.viewBean.getId());
      List languages = this.languageService.findByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
      this.jsonResponse.setData(entity);
      this.jsonResponse.encode(this.response, new ItemJSONAdapter((String)this.session.get("SESSION_PROJECT_KEY"), languages));
    }
    catch (Exception e) {
      checkEditException(e);
    }
    return null;
  }

  protected void checkDeleteException(Exception e) throws Exception
  {
    if ((e instanceof DataIntegrityViolationException)) {
      this.jsonResponse.addError(new JSONError(null, this.resources.getString("item.erreur.delete")));
    }
    else
      throw e;
  }

  public String query()
    throws Exception
  {
    String viewbeanZoneId = this.request.getParameter("viewbean.zoneId");
    Integer zoneId = (viewbeanZoneId == null) || (viewbeanZoneId.toString().equals("")) ? null : Integer.valueOf(Integer.parseInt(viewbeanZoneId));
    Object entity = null;
    if (zoneId == null) {
      entity = ((ItemService)this.entityService).findByProjectAndNoZone((Integer)this.session.get("SESSION_PROJECT_ID"));
    }
    else {
      entity = ((ItemService)this.entityService).findByZone(zoneId);
    }

    System.out.println("projectId: '" + (Integer)this.session.get("SESSION_PROJECT_ID") + "' zone length: " + ((List)entity).size());

    List languages = this.languageService.findByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
    this.jsonResponse.setDatas((Collection)entity);
    this.jsonResponse.encode(this.response, new ItemJSONAdapter((String)this.session.get("SESSION_PROJECT_KEY"), languages));
    return null;
  }
  
  	public String copy() throws Exception {
		Integer id = this.viewBean.getId();
		String photosDir = ((ItemViewBean)this.viewBean).getPhotosDir();
		
		
		Item item = null;
		if(id == null || (item = (Item)this.entityService.findById(id)) == null) {
			jsonResponse.addError(new JSONError(null, "item does not exists:" + id));
		}
		else {
			item.setId(null);
			item.setName("Copy Of " + item.getName());
			
			List<ItemContent> itemContentList = item.getItemContents();
			String origPhotosDir = item.getPhotosDir();
			item.setItemContents(null);
			item.setPhotosDir(photosDir);
			
			item = (Item)this.entityService.saveOrUpdate(item);
			
			if(itemContentList != null && itemContentList.size() > 0) {
				for(ItemContent itemContent : itemContentList) {
					itemContent.setId(null);
					itemContent.setName("Copy of " + itemContent.getName());
					itemContent.setItem(item);
				}
				item.setItemContents(itemContentList);
				
				item = (Item)this.entityService.saveOrUpdate(item);
			}
			
			String uploadPath = (String)this.configParameterService.findParameter("paths", "externalFilesPath") + "/" + "project-exports" + "/" + this.session.get("SESSION_PROJECT_KEY");
	        String orgiDir = uploadPath + "/" + "items" + "/" + origPhotosDir;
	        String dir = uploadPath + "/" + "items" + "/" + photosDir;
	        FileUtils.deleteDirectory(new File(dir + "/" + "icon"));
	        String origFilePath = orgiDir  + "/" + "icon" + "/" + item.getLogo();
	        String filePath = dir  + "/" + "icon" + "/" + item.getLogo();
	        if(new File(origFilePath).exists()) FileUtils.copyFile(new File(origFilePath), new File(filePath));
	        
	        
	        String photos = item.getPhotos();
	        if(photos != null && !photos.trim().isEmpty()) {
	        	for(String photo : photos.trim().split(";")) {
	        		origFilePath = orgiDir  + "/" + photo.trim();
	    	        filePath = dir  + "/" + photo.trim();
	        		if(new File(origFilePath).exists()) FileUtils.copyFile(new File(origFilePath), new File(filePath));
	        	}
	        }
			
			this.jsonResponse = new JSONResponse(toViewBean(item));
		}
		return SUCCESS_JSON;
	}
  public String translate() throws Exception {
    List<Language> languages = this.languageService.findByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
    HashMap languagesContentMap = new HashMap();

    String sourceLanguageId = this.request.getParameter("sourceLanguageId");
    Language sourceLanguage = (Language)this.languageService.findById(Integer.valueOf(sourceLanguageId));

    int i = 0;
    int j = Integer.valueOf(this.request.getParameter("tabIndex")).intValue();
    for (Language language : languages) {
      String targetLanguageId = this.request.getParameter("viewBean.itemContents[" + i + "].languageId");
      Language targetLanguage = (Language)this.languageService.findById(Integer.valueOf(targetLanguageId));

      HashMap contentMap = new HashMap();

      if (!sourceLanguageId.equals(targetLanguageId)) {
        try {
          if (isEmpty(this.request.getParameter("viewBean.itemContents[" + i + "].name"))) contentMap.put("name", this.translationService.translate(this.request.getParameter("viewBean.itemContents[" + j + "].name"), sourceLanguage.getName(), targetLanguage.getName()));
          if (isEmpty(this.request.getParameter("viewBean.itemContents[" + i + "].description"))) contentMap.put("description", this.translationService.translate(this.request.getParameter("viewBean.itemContents[" + j + "].description"), sourceLanguage.getName(), targetLanguage.getName()));
          if (isEmpty(this.request.getParameter("viewBean.itemContents[" + i + "].keywords"))) contentMap.put("keywords", this.translationService.translate(this.request.getParameter("viewBean.itemContents[" + j + "].keywords"), sourceLanguage.getName(), targetLanguage.getName()));
          if (isEmpty(this.request.getParameter("viewBean.itemContents[" + i + "].openingDays"))) contentMap.put("openingDays", this.translationService.translate(this.request.getParameter("viewBean.itemContents[" + j + "].openingDays"), sourceLanguage.getName(), targetLanguage.getName())); 
        }
        catch (DefaultTranslationException e)
        {
          this.jsonResponse.addError(new JSONError(e.getMessage()));
        }
      }

      languagesContentMap.put(Integer.valueOf(i), contentMap);
      i++;
    }

    Object entity = new Item();
    this.jsonResponse.setData(entity);
    this.jsonResponse.encode(this.response, new ItemTranslateJSONAdapter((String)this.session.get("SESSION_PROJECT_KEY"), languagesContentMap, languages));
    return null;
  }

  private boolean isEmpty(String str) {
    if ((str.equals("")) || (str.equals("<br>")) || (str.equals("<br/>")) || (str.equals("\n")) || (str.equals("\r")) || (str.equals("\r\n")) || (str.equals("<p>&nbsp;</p>"))) {
      return true;
    }

    return false;
  }

  public void setItemTypes(List itemTypes)
  {
    this.itemTypes = itemTypes;
  }
  public List getItemTypes() {
    return this.itemTypes;
  }

  public void setCategories(List categories) {
    this.categories = categories;
  }
  public List getCategories() {
    return this.categories;
  }
  
  public void setZones(List zones) {
	  this.zones = zones;
  }
  public List getZones() {
    return this.zones;
  }

  public List getLanguages() {
    return this.languages;
  }
  public void setLanguages(List languages) {
    this.languages = languages;
  }

  public List getIconsCommon() {
    return this.iconsCommon;
  }
  public void setIconsCommon(List iconsCommon) {
    this.iconsCommon = iconsCommon;
  }

  public List getIconsProject() {
    return this.iconsProject;
  }
  public void setIconsProject(List iconsProject) {
    this.iconsProject = iconsProject;
  }
}
