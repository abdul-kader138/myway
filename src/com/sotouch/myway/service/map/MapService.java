package com.sotouch.myway.service.map;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.util.xml.XmlUtil;
import org.hurryapp.quickstart.data.dao.DeviceDAO;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;
import org.hurryapp.quickstart.data.model.Device;
import org.hurryapp.quickstart.utils.ThreeDESCode;

import com.opensymphony.xwork2.inject.Inject;
import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.AdDAO;
import com.sotouch.myway.data.dao.BuildingDAO;
import com.sotouch.myway.data.dao.CategoryDAO;
import com.sotouch.myway.data.dao.EventDAO;
import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.dao.LanguageDAO;
import com.sotouch.myway.data.dao.LicenseDAO;
import com.sotouch.myway.data.dao.PlaylistDAO;
import com.sotouch.myway.data.dao.ProjectDAO;
import com.sotouch.myway.data.dao.PromotionDAO;
import com.sotouch.myway.data.dao.SettingsDAO;
import com.sotouch.myway.data.dao.ThemeDAO;
import com.sotouch.myway.data.dao.WordingDAO;
import com.sotouch.myway.data.dao.ZoneDAO;
import com.sotouch.myway.data.model.Ad;
import com.sotouch.myway.data.model.Building;
import com.sotouch.myway.data.model.BuildingContent;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Event;
import com.sotouch.myway.data.model.EventContent;
import com.sotouch.myway.data.model.Floor;
import com.sotouch.myway.data.model.FloorContent;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Playlist;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.ProjectContent;
import com.sotouch.myway.data.model.Promotion;
import com.sotouch.myway.data.model.PromotionContent;
import com.sotouch.myway.data.model.Settings;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.SubCategoryContent;
import com.sotouch.myway.data.model.Theme;
import com.sotouch.myway.data.model.Wording;
import com.sotouch.myway.data.model.Zone;
import com.sotouch.myway.data.model.ZoneContent;
import com.sotouch.myway.service.language.LanguageService;

public class MapService {
	
	@Resource (name="projectDao")
	private ProjectDAO projectDao;

	@Resource (name="licenseDao")
	private LicenseDAO licenseDao;

	@Resource (name="languageDao")
	private LanguageDAO languageDao;

	@Resource (name="wordingDao")
	private WordingDAO wordingDao;

	@Resource (name="categoryDao")
	private CategoryDAO categoryDao;

	@Resource (name="buildingDao")
	private BuildingDAO buildingDao;
	
	@Resource (name="zoneDao")
	private ZoneDAO zoneDao;

	@Resource (name="itemDao")
	private ItemDAO itemDao;

	//@Resource (name="itemMapDao")
	//private ItemMapDAO itemMapDao;

	@Resource (name="promotionDao")
	private PromotionDAO promotionDao;

	@Resource (name="eventDao")
	private EventDAO eventDao;

	//@Resource (name="pathDao")
	//private PathDAO pathDao;

	@Resource (name="settingsDao")
	private SettingsDAO settingsDao;
	
	@Resource (name="themeDao")
	private ThemeDAO themeDao;

	@Resource (name="playlistDao")
	private PlaylistDAO playlistDao;

	@Resource (name="adDao")
	private AdDAO adDao;

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;
	
	@Resource (name="deviceDao")
	private DeviceDAO deviceDao;
	
	@Resource(name="languageService")
	private LanguageService languageService;
	
	/**
	 * Generate the XML stream for map editor initialization
	 * @param projectId
	 * @param contextPath
	 * @throws Exception
	 */
	public String init(Integer projectId, String contextPath) throws Exception {
		Project project = projectDao.findById(projectId);
		Document document = DocumentHelper.createDocument();
		Element dataElt = document.addElement("data");
		Element elt = null;
		String str = null;

		Language projectDefaultLanguage = this.languageService.findDefaultByProject(projectId);
		
		//---------------------------------------------------------------------
		// Project key
		//---------------------------------------------------------------------
		Element keyElt = dataElt.addElement("key_id");
		keyElt.setText(project.getKey());

		//---------------------------------------------------------------------
		// Buildings
		//---------------------------------------------------------------------
		// Add builings and floors
		Element buildingsElt = dataElt.addElement("buildings");
		Element buildingElt = null;
		Element floorsElt = null;
		Element floorElt = null;
		List<Building> buildings = buildingDao.findByProject(projectId);
		for (Building building : buildings) {
			if (building.getFloors().size() > 0) {
				buildingElt = buildingsElt.addElement("building");
				buildingElt.addAttribute("id", building.getId().toString());
				
				Element nameElt = buildingElt.addElement("name"); nameElt.setText(building.getName());
				
				/*if(building.getBuildingContents().size() > 0) {
					for (BuildingContent buildingContent : building.getBuildingContents()) {
						if (buildingContent != null) {
							elt = nameElt.addElement(buildingContent.getLanguage().getFlag()); elt.setText(buildingContent.getName());
						}
					}
				}
				else {
					elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(building.getName());
				}*/

				floorsElt = buildingElt.addElement("floors");
				for (Floor floor : building.getFloors()) {
					if (floor != null) {
						floorElt = floorsElt.addElement("floor");
						floorElt.addAttribute("id", floor.getId().toString());
						elt = floorElt.addElement("label"); elt.setText(floor.getLabel());
						
						nameElt = floorElt.addElement("name");nameElt.setText(floor.getName());
						/*if(floor.getFloorContents().size() > 0) {
							for (FloorContent floorContent : floor.getFloorContents()) {
								if (floorContent != null) {
									elt = nameElt.addElement(floorContent.getLanguage().getFlag()); elt.setText(floorContent.getName());
								}
							}
						}
						else {
							elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(floor.getName());
						}*/
						
						if (!StringUtil.isEmpty(floor.getImage())) {
							str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_FLOORS+"/"+floor.getId()+"/"+floor.getImage();
						}
						else {
							str = "";
						}
						elt = floorElt.addElement("image"); elt.setText(str);
					}
				}
			}
		}
		
		//---------------------------------------------------------------------
		// Zones
		//---------------------------------------------------------------------
		// Add zones
		Element zonesElt = dataElt.addElement("zones");
		Element zoneElt = null;
		List<Zone> zones = zoneDao.findByProject(projectId);
		for (Zone zone : zones) {
			zoneElt = zonesElt.addElement("zone");
			zoneElt.addAttribute("id", "zone:" + zone.getId().toString());
			
			Element nameElt = zoneElt.addElement("name");nameElt.setText(zone.getName());
			//for (ZoneContent zoneContent : zone.getZoneContents()) {
			//	if (zoneContent != null) {
			//		elt = nameElt.addElement(zoneContent.getLanguage().getFlag()); elt.setText(zoneContent.getName());
			//	}
			//}
			elt = zoneElt.addElement("color");elt.setText(zone.getColor());
			elt = zoneElt.addElement("transparency");elt.setText(zone.getTransparency().toString());
			if (!StringUtil.isEmpty(zone.getImage())) {
				str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ZONES+"/"+zone.getId()+"/"+zone.getImage();
			}
			else {
				str = "";
			}
			elt = zoneElt.addElement("icon"); elt.setText(str);
			elt = zoneElt.addElement("building");elt.addAttribute("id", zone.getBuilding().getId().toString());
			elt = zoneElt.addElement("cat"); elt.setText(zone.getCategory() != null ? zone.getCategory().getId().toString() : "");
			elt = zoneElt.addElement("subCat"); elt.setText(zone.getSubCategory() != null ? zone.getSubCategory().getId().toString() : "");
			elt = zoneElt.addElement("displayItemInZone"); elt.setText(zone.getItemsInZone() != null && zone.getItemsInZone() ? "true" : "false");
		}
		
		//---------------------------------------------------------------------
		// Categories
		//---------------------------------------------------------------------
		List<Category> categories = categoryDao.findByProject(projectId);
		Element categoriesElt = dataElt.addElement("categories");
		Element categoryElt = null;
		Element subCategoriesElt = null;
		Element subCategoryElt = null;
		Element nameElt = null;
		for (Category category : categories) {
			categoryElt = categoriesElt.addElement("category");
			categoryElt.addAttribute("id", category.getId().toString());

			nameElt = categoryElt.addElement("name");
			for (CategoryContent categoryContent : category.getCategoryContents()) {
				if (categoryContent != null && categoryContent.getLanguage().getDefaultLanguage()) {
					//elt = nameElt.addElement(categoryContent.getLanguage().getFlag()); elt.setText(categoryContent.getName());
					nameElt.setText(categoryContent.getName());
				}
			}
			if (category.getColor() != null) {
				elt = categoryElt.addElement("color"); elt.setText(category.getColor());
			}
			
			// Sub-categories
			subCategoriesElt = categoryElt.addElement("sub-categories");
			for (SubCategory subCategory : category.getSubCategories()) {
				subCategoryElt = subCategoriesElt.addElement("sub-category");
				subCategoryElt.addAttribute("id", subCategory.getId().toString());
				nameElt = subCategoryElt.addElement("name");
				for (SubCategoryContent subCategoryContent : subCategory.getSubCategoryContents()) {
					if (subCategoryContent != null && subCategoryContent.getLanguage().getDefaultLanguage()) {
						//elt = nameElt.addElement(subCategoryContent.getLanguage().getFlag()); elt.setText(subCategoryContent.getName());
						nameElt.setText(subCategoryContent.getName());
					}
				}
				if (subCategory.getColor() != null) {
					elt = subCategoryElt.addElement("color"); elt.setText(subCategory.getColor());
				}
			}			
		}
				
		//---------------------------------------------------------------------
		// Items
		//---------------------------------------------------------------------
		// Add "location" items 
		Element itemsListElt = dataElt.addElement("items_list");
		Element itemsElt = itemsListElt.addElement("locations");
		Element itemElt = null;
		List<Item> items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_LOCATION);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("location");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName());
				
				if (!StringUtil.isEmpty(item.getLogo())) {
					str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ITEMS+"/"+item.getPhotosDir()+"/"+Constants.DIR_ICON+"/"+item.getLogo();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
			}
		}
		
		// Add "FUTO" items
		itemsElt = itemsListElt.addElement("futos");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_FUTO);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("futo");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName());
				if (item.getIcon() != null) {
					if (item.getIcon().getItemType() != null) {
						str = contextPath+"/"+Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
					}
					else {
						str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
					}
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
			}
		}
		
		// Add "access" items
		itemsElt = itemsListElt.addElement("accesses");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_ACCESS);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("access");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName());
				elt = itemElt.addElement("type"); elt.setText(item.getType().toString());
				elt = itemElt.addElement("disabled_accessible"); elt.setText(item.getDisabledAccess() ? "true" : "false");
				if (item.getIcon() != null) {
					if (item.getIcon().getItemType() != null) {
						str = contextPath+"/"+Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
					}
					else {
						str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
					}
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
			}
		}
		
		// Add "terminal" items 
		/*
		itemsElt = itemsListElt.addElement("terminals");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_TERMINAL);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsListElt.addElement("terminal");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName());
				if (item.getIcon() != null) {
					str = contextPath+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
			}
		}
		*/

		//---------------------------------------------------------------------
		// Licenses (<=> terminals)
		//---------------------------------------------------------------------
		itemsElt = itemsListElt.addElement("terminals");
		List<License> licenses = licenseDao.findByProject(projectId);
		for (License license : licenses) {
			itemElt = itemsElt.addElement("terminal");
			itemElt.addAttribute("id", license.getId().toString());
			nameElt = itemElt.addElement("name"); nameElt.setText(license.getKey());
			/*if(license.getLicenseContents().size() > 0) {
				for(LicenseContent licenseContent : license.getLicenseContents()) {
					elt = nameElt.addElement(licenseContent.getLanguage().getFlag()); elt.setText(licenseContent.getName());
				}
			}
			else  {
				elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(license.getKey());
			}*/
			
			if(license.getLogo() == null || license.getLogo().equals("")) {
				elt = itemElt.addElement("icon"); elt.setText(contextPath+"/"+ Constants.DIR_ICONS+"/18/terminal_icon.png");
			}
			else {
				elt = itemElt.addElement("icon"); elt.setText(contextPath+"/"+ Constants.DIR_PROJECTS+"/"+project.getKey()+"/" + Constants.DIR_LICENSES + "/" + license.getId() + "/" + license.getLogo());
			}
		}

		//---------------------------------------------------------------------
		// Map config
		//---------------------------------------------------------------------
		if (!StringUtil.isEmpty(project.getMapConfig())) {
			Document mapConfigDoc = XmlUtil.parse(project.getMapConfig());
			if (mapConfigDoc != null) {
				dataElt.add(mapConfigDoc.getRootElement());
			}
		}
		else {
			dataElt.addElement("map_config");
		}

		return document.asXML();
	}

	/**
	 * Generate the XML file "project.xml" used by the terminals
	 * @param projectId
	 * @param publishMode
	 * @param paths
	 * @param contextPath
	 * @throws Exception
	 */
	public void export(Integer projectId, String publishMode, String paths, String contextPath) throws Exception {
		Project project = projectDao.findById(projectId);
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element dataElt = document.addElement("data");
		Element elt = null;
		String str = null;

		Language projectDefaultLanguage = this.languageService.findDefaultByProject(projectId);
		
		//---------------------------------------------------------------------
		// Project
		//---------------------------------------------------------------------
		Element keyElt = dataElt.addElement("key_id");
		keyElt.setText(project.getKey());
		Element logoElt = dataElt.addElement("logo");
		if (project.getLogo() != null) {
			logoElt.setText(Constants.DIR_PROJECT_LOGO+"/"+project.getLogo());
		}
		Element newsletterLogoElt = dataElt.addElement("info_logo");
		if (project.getNewsletterLogo() != null) {
			newsletterLogoElt.setText(Constants.DIR_PROJECT_NEWSLETTER_LOGO+"/"+project.getNewsletterLogo());
		}
		Element creditsImageElt = dataElt.addElement("credits_img");
		if (project.getCreditsImage() != null) {
			creditsImageElt.setText(Constants.DIR_PROJECT_CREDITS_IMAGE+"/"+project.getCreditsImage());
		}
		
        // devices
        Element devicesElt = dataElt.addElement("devices");
        for (Device device : deviceDao.findAll()) {
        	elt = devicesElt.addElement("device"); elt.setText(device.getKey());
		}
        
		// Project contents
		Element termsElt = dataElt.addElement("terms");
		for (ProjectContent projectContent : project.getProjectContents()) {
			if (projectContent != null) {
				String contentFlag = projectContent.getLanguage().getFlag();
				elt = termsElt.addElement(contentFlag); elt.setText(projectContent.getNewsletterTerms());
			}
		}

		//---------------------------------------------------------------------
		// Date creation
		//---------------------------------------------------------------------
		Element dateElt = dataElt.addElement("creation_date");
		dateElt.setText(DateUtil.toString(new Date(), DateUtil.FORMAT_DATE_TIMESTAMP));

		//---------------------------------------------------------------------
		// Languages
		//---------------------------------------------------------------------
		List<Language> languages = languageDao.findByProject(projectId);
		Element languagesElt = dataElt.addElement("languages");
		Element languageElt = null;
		for (Language language : languages) {
			languageElt = languagesElt.addElement("language");
			languageElt.addAttribute("id", language.getFlag());
			languageElt.addAttribute("code", language.getCode());
			languageElt.addAttribute("def", language.getDefaultLanguage() ? "1" : "0");
			languageElt.setText(language.getName());
		}
		
		//---------------------------------------------------------------------
		// Wording
		//---------------------------------------------------------------------
		List<Wording> wordings = wordingDao.findByProjectGroupByName(projectId);
		Element wordingsElt = dataElt.addElement("wording");
		Element wordingElt = null;
		String lastWordingName = "";
		for (Wording wording : wordings) {
			if (!lastWordingName.equals(wording.getName())) {
				wordingElt = wordingsElt.addElement(wording.getName());
				lastWordingName = wording.getName();
			}
			elt = wordingElt.addElement(wording.getLanguage().getFlag());
			elt.setText(wording.getValue() != null ? wording.getValue() : "");
		}
		
		//---------------------------------------------------------------------
		// Categories
		//---------------------------------------------------------------------
		List<Category> categories = categoryDao.findByProject(projectId);
		Element categoriesElt = dataElt.addElement("categories");
		Element categoryElt = null;
		Element subCategoriesElt = null;
		Element subCategoryElt = null;
		Element nameElt = null;
		for (Category category : categories) {
			categoryElt = categoriesElt.addElement("category");
			categoryElt.addAttribute("id", category.getId().toString());

			nameElt = categoryElt.addElement("name");
			if(category.getCategoryContents().size() > 0) {
				for (CategoryContent categoryContent : category.getCategoryContents()) {
					if (categoryContent != null) {
						elt = nameElt.addElement(categoryContent.getLanguage().getFlag()); elt.setText(categoryContent.getName());
					}
				}
			}
			else {
				elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(category.getName());
			}
			
			if (category.getColor() != null) {
				elt = categoryElt.addElement("color"); elt.setText(category.getColor());
			}
			
			// Sub-categories
			subCategoriesElt = categoryElt.addElement("sub-categories");
			for (SubCategory subCategory : category.getSubCategories()) {
				subCategoryElt = subCategoriesElt.addElement("sub-category");
				subCategoryElt.addAttribute("id", subCategory.getId().toString());
				nameElt = subCategoryElt.addElement("name");
				if(subCategory.getSubCategoryContents().size() > 0) {
					for (SubCategoryContent subCategoryContent : subCategory.getSubCategoryContents()) {
						if (subCategoryContent != null) {
							elt = nameElt.addElement(subCategoryContent.getLanguage().getFlag()); elt.setText(subCategoryContent.getName());
						}
					}
				}
				else {
					elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(subCategory.getName());
				}
				if (subCategory.getColor() != null) {
					elt = subCategoryElt.addElement("color"); elt.setText(subCategory.getColor());
				}
			}			
		}
		
		//---------------------------------------------------------------------
		// Buildings
		//---------------------------------------------------------------------
		// Add buildings and floors
		Element buildingsElt = dataElt.addElement("buildings");
		Element buildingElt = null;
		Element floorsElt = null;
		Element floorElt = null;
		List<Building> buildings = buildingDao.findByProject(projectId);
		for (Building building : buildings) {
			if (building.getFloors().size() > 0) {
				buildingElt = buildingsElt.addElement("building");
				buildingElt.addAttribute("id", building.getId().toString());
				
				nameElt = buildingElt.addElement("name");
				if(building.getBuildingContents().size() > 0) {
					for (BuildingContent buildingContent : building.getBuildingContents()) {
						if (buildingContent != null) {
							elt = nameElt.addElement(buildingContent.getLanguage().getFlag()); elt.setText(buildingContent.getName());
						}
					}
				}
				else {
					elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(building.getName());
				}
				

				floorsElt = buildingElt.addElement("floors");
				for (Floor floor : building.getFloors()) {
					if (floor != null) {
						floorElt = floorsElt.addElement("floor");
						floorElt.addAttribute("id", floor.getId().toString());
						elt = floorElt.addElement("label"); elt.setText(floor.getLabel());
						
						nameElt = floorElt.addElement("name");
						if(floor.getFloorContents().size() > 0) {
							for (FloorContent floorContent : floor.getFloorContents()) {
								if (floorContent != null) {
									elt = nameElt.addElement(floorContent.getLanguage().getFlag()); elt.setText(floorContent.getName());
								}
							}
						}
						else {
							elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(floor.getName());
						}
						
						if (!StringUtil.isEmpty(floor.getImage())) {
							str = Constants.DIR_FLOORS+"/"+floor.getId()+"/"+floor.getImage();
						}
						else {
							str = "";
						}
						elt = floorElt.addElement("image"); elt.setText(str);
					}
				}
			}
		}
		
		
		//---------------------------------------------------------------------
		// Zones
		//---------------------------------------------------------------------
		// Add zones
		Element zonesElt = dataElt.addElement("zones");
		Element zoneElt = null;
		List<Zone> zones = zoneDao.findByProject(projectId);
		for (Zone zone : zones) {
			zoneElt = zonesElt.addElement("zone");
			zoneElt.addAttribute("id", "zone:" + zone.getId().toString());
			
			nameElt = zoneElt.addElement("name");
			for (ZoneContent zoneContent : zone.getZoneContents()) {
				if (zoneContent != null) {
					elt = nameElt.addElement(zoneContent.getLanguage().getFlag()); elt.setText(zoneContent.getName());
				}
			}
			elt = zoneElt.addElement("color");elt.setText(zone.getColor());
			elt = zoneElt.addElement("transparency");elt.setText(zone.getTransparency().toString());
			
			if (!StringUtil.isEmpty(zone.getImage())) {
				str = Constants.DIR_ZONES+"/"+zone.getId()+"/"+zone.getImage();
			}
			else {
				str = "";
			}
			elt = zoneElt.addElement("icon"); elt.setText(str);
			elt = zoneElt.addElement("building");elt.addAttribute("id", zone.getBuilding().getId().toString());
			elt = zoneElt.addElement("cat"); elt.setText(zone.getCategory() != null ? zone.getCategory().getId().toString() : "");
			elt = zoneElt.addElement("subCat"); elt.setText(zone.getSubCategory() != null ? zone.getSubCategory().getId().toString() : "");
			elt = zoneElt.addElement("displayItemInZone"); elt.setText(zone.getItemsInZone() != null && zone.getItemsInZone() ? "true" : "false");
		}
		
		//---------------------------------------------------------------------
		// Items
		//---------------------------------------------------------------------
		// Add "location" items
		Element itemsListElt = dataElt.addElement("items_list");
		Element itemsElt = itemsListElt.addElement("locations");
		Element itemElt = null;
		Element descElt = null;
		Element keywordsElt = null;
		Element openingDaysElt = null;
		Element urlElt = null;
		Element photosElt = null;
		String[] photosTab = null;
		List<Item> items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_LOCATION);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("location");
				itemElt.addAttribute("id", item.getId().toString());
				
				nameElt = itemElt.addElement("name");
				descElt = itemElt.addElement("description");
				keywordsElt = itemElt.addElement("keywords");
				openingDaysElt = itemElt.addElement("opening_days");
				for (ItemContent itemContent : item.getItemContents()) {
					if (itemContent != null) {
						String contentFlag = itemContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag);        elt.setText(itemContent.getName());
						elt = descElt.addElement(contentFlag);        if (itemContent.getDescription() != null) elt.add(DocumentHelper.createCDATA(itemContent.getDescription()));
						//elt = descElt.addElement(contentFlag);        elt.setText(itemContent.getDescription() != null ? itemContent.getDescription() : "");
						elt = keywordsElt.addElement(contentFlag);    elt.setText(itemContent.getKeywords() != null ? itemContent.getKeywords() : "");
						elt = openingDaysElt.addElement(contentFlag); elt.setText(itemContent.getOpeningDays() != null ? itemContent.getOpeningDays() : "");
					}
				}

				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				elt = itemElt.addElement("address"); elt.setText(item.getAddress() != null ? item.getAddress() : "");
				elt = itemElt.addElement("phone"); elt.setText(item.getPhoneNumber() != null ? item.getPhoneNumber() : "");
				elt = itemElt.addElement("email"); elt.setText(item.getEmail() != null ? item.getEmail() : "");
				elt = itemElt.addElement("hour_start"); elt.setText(item.getHourBegin() != null ? DateUtil.toString(item.getHourBegin(), Constants.HOUR_FORMAT) : "");
				elt = itemElt.addElement("hour_end"); elt.setText(item.getHourEnd() != null ? DateUtil.toString(item.getHourEnd(), Constants.HOUR_FORMAT) : "");
				
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
				
				// Icon
				if (!StringUtil.isEmpty(item.getLogo())) {
					str = Constants.DIR_ITEMS+"/"+item.getPhotosDir()+"/"+Constants.DIR_ICON+"/"+item.getLogo();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);

				// Photos
				photosElt = itemElt.addElement("photos");
				if (!StringUtil.isEmpty(item.getPhotos())) {
					photosTab = item.getPhotos().split(";");
					for (String photo : photosTab) {
						elt = photosElt.addElement("photo"); elt.setText(Constants.DIR_ITEMS+"/"+item.getPhotosDir()+"/"+photo);
					}
				}
			}
		}
		
		// Add "FUTO" items
		itemsElt = itemsListElt.addElement("futos");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_FUTO);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("futo");
				itemElt.addAttribute("id", item.getId().toString());

				nameElt = itemElt.addElement("name");
				descElt = itemElt.addElement("description");
				keywordsElt = itemElt.addElement("keywords");
				for (ItemContent itemContent : item.getItemContents()) {
					if (itemContent != null) {
						String contentFlag = itemContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag);     elt.setText(itemContent.getName());
						elt = descElt.addElement(contentFlag);     elt.setText(itemContent.getDescription() != null ? itemContent.getDescription() : "");
						elt = keywordsElt.addElement(contentFlag); elt.setText(itemContent.getKeywords() != null ? itemContent.getKeywords() : "");
					}
				}

				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
				
				// Icon
				if (item.getIcon() != null) {
					str = Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
			}
		}
		
		// Add "access" items
		itemsElt = itemsListElt.addElement("accesses");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_ACCESS);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("access");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("type"); elt.setText(item.getType() != null ? item.getType().toString() : "");

				nameElt = itemElt.addElement("name");
				descElt = itemElt.addElement("description");
				keywordsElt = itemElt.addElement("keywords");
				for (ItemContent itemContent : item.getItemContents()) {
					if (itemContent != null) {
						String contentFlag = itemContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag);     elt.setText(itemContent.getName());
						elt = descElt.addElement(contentFlag);     elt.setText(itemContent.getDescription() != null ? itemContent.getDescription() : "");
						elt = keywordsElt.addElement(contentFlag); elt.setText(itemContent.getKeywords() != null ? itemContent.getKeywords() : "");
					}
				}

				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
				//elt = itemElt.addElement("zone"); elt.setText(item.getZone() != null ? (item.getZone().getId().toString()) : "");
				
				// Icon
				if (item.getIcon() != null) {
					str = Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
			}
		}
		
		// Add "webview" items 
		itemsElt = itemsListElt.addElement("webviews");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_WEBVIEW);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("webview");
				itemElt.addAttribute("id", item.getId().toString());

				nameElt = itemElt.addElement("name");
				urlElt = itemElt.addElement("url");
				for (ItemContent itemContent : item.getItemContents()) {
					if (itemContent != null) {
						String contentFlag = itemContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag); elt.setText(itemContent.getName());
						elt = urlElt.addElement(contentFlag);  elt.setText(itemContent.getUrl() != null ? itemContent.getUrl() : "");
					}
				}

				elt = itemElt.addElement("cat"); elt.setText(item.getCategory() != null ? item.getCategory().getId().toString() : "");
				elt = itemElt.addElement("subCat"); elt.setText(item.getSubCategory() != null ? item.getSubCategory().getId().toString() : "");
			}
		}
		
		// Add "terminal" items 
		/*
		itemsElt = itemsListElt.addElement("terminals");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_TERMINAL);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsListElt.addElement("terminal");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName());
				if (item.getIcon() != null) {
					str = Constants.DIR_ICONS+"/"+item.getIcon().getId()+"/"+item.getIcon().getIcon();
				}
				else {
					str = "";
				}
				elt = itemElt.addElement("icon"); elt.setText(str);
			}
		}
		*/
		
		//---------------------------------------------------------------------
		// Licenses (<=> terminals)
		//---------------------------------------------------------------------
		itemsElt = itemsListElt.addElement("terminals");
		List<License> licenses = licenseDao.findByProject(projectId);
		for (License license : licenses) {
			itemElt = itemsElt.addElement("terminal");
			itemElt.addAttribute("id", license.getId().toString());
			
			nameElt = itemElt.addElement("name");
			Element desElt = itemElt.addElement("description");
			if(license.getLicenseContents().size() > 0) {
				for(LicenseContent licenseContent : license.getLicenseContents()) {
					elt = nameElt.addElement(licenseContent.getLanguage().getFlag()); elt.setText(licenseContent.getName());
					elt = desElt.addElement(licenseContent.getLanguage().getFlag()); elt.setText(licenseContent.getDescription());
				}
			}
			else  {
				elt = nameElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(license.getKey());
				elt = desElt.addElement(projectDefaultLanguage.getFlag()); elt.setText(license.getDescription());
			}
			
			elt = itemElt.addElement("orientation"); elt.setText(license.getOrientation() != null ? license.getOrientation() : "");
			//elt = itemElt.addElement("icon"); elt.setText(Constants.DIR_ICONS+"/18/terminal_icon.png");
			if(license.getLogo() == null || license.getLogo().equals("")) {
				elt = itemElt.addElement("icon"); elt.setText(Constants.DIR_ICONS+"/18/terminal_icon.png");
			}
			else {
				elt = itemElt.addElement("icon"); elt.setText(Constants.DIR_LICENSES + "/" + license.getId() + "/" + license.getLogo());
			}
			elt = itemElt.addElement("icon_here"); elt.setText(Constants.DIR_ICONS+"/20/terminal_here_icon.png");
			
			//Element devicesInLicenseElt = itemElt.addElement("devices");
			//for (Device device : deviceDao.findByLicense(license.getId())) {
	        //	elt = devicesInLicenseElt.addElement("device"); elt.setText(device.getKey());
			//}
		}

		//---------------------------------------------------------------------
		// Promotions
		//---------------------------------------------------------------------
		List<Promotion> promotions = promotionDao.findByProject(projectId);
		Element promotionsElt = dataElt.addElement("promotions");
		Element promotionElt = null;
		for (Promotion promotion : promotions) {
			if (!StringUtil.isEmpty(promotion.getName())) {
				promotionElt = promotionsElt.addElement("promotion");
				promotionElt.addAttribute("id", promotion.getId().toString());
				
				nameElt = promotionElt.addElement("name");
				descElt = promotionElt.addElement("description");
				keywordsElt = promotionElt.addElement("keywords");
				for (PromotionContent promotionContent : promotion.getPromotionContents()) {
					if (promotionContent != null) {
						String contentFlag = promotionContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag);     elt.setText(promotionContent.getName());
						elt = descElt.addElement(contentFlag);     elt.setText(promotionContent.getDescription() != null ? promotionContent.getDescription() : "");
						elt = keywordsElt.addElement(contentFlag); elt.setText(promotionContent.getKeywords() != null ? promotionContent.getKeywords() : "");
					}
				}

				elt = promotionElt.addElement("date_start"); elt.setText(promotion.getStart() != null ? DateUtil.toString(promotion.getStart(), Constants.DAY_FORMAT) : "");
				elt = promotionElt.addElement("hour_start"); elt.setText(promotion.getStart() != null ? DateUtil.toString(promotion.getStart(), Constants.HOUR_FORMAT) : "");
				elt = promotionElt.addElement("date_end"); elt.setText(promotion.getEnd() != null ? DateUtil.toString(promotion.getEnd(), Constants.DAY_FORMAT) : "");
				elt = promotionElt.addElement("hour_end"); elt.setText(promotion.getEnd() != null ? DateUtil.toString(promotion.getEnd(), Constants.HOUR_FORMAT) : "");
				elt = promotionElt.addElement("item_id"); elt.setText(promotion.getItem().getId().toString());
				
				// Image
				if (!StringUtil.isEmpty(promotion.getImage())) {
					str = Constants.DIR_PROMOTIONS+"/"+promotion.getId()+"/"+promotion.getImage();
				}
				else {
					str = "";
				}
				elt = promotionElt.addElement("image"); elt.setText(str);
			}
		}
		
		//---------------------------------------------------------------------
		// Events
		//---------------------------------------------------------------------
		List<Event> events = eventDao.findByProject(projectId);
		Element eventsElt = dataElt.addElement("events");
		Element eventElt = null;
		for (Event event : events) {
			if (!StringUtil.isEmpty(event.getName())) {
				eventElt = eventsElt.addElement("event");
				eventElt.addAttribute("id", event.getId().toString());
				
				nameElt = eventElt.addElement("name");
				descElt = eventElt.addElement("description");
				keywordsElt = eventElt.addElement("keywords");
				for (EventContent eventContent : event.getEventContents()) {
					if (eventContent != null) {
						String contentFlag = eventContent.getLanguage().getFlag();
						elt = nameElt.addElement(contentFlag);     elt.setText(eventContent.getName());
						elt = descElt.addElement(contentFlag);     elt.setText(eventContent.getDescription() != null ? eventContent.getDescription() : "");
						elt = keywordsElt.addElement(contentFlag); elt.setText(eventContent.getKeywords() != null ? eventContent.getKeywords() : "");
					}
				}

				elt = eventElt.addElement("date_start"); elt.setText(event.getStart() != null ? DateUtil.toString(event.getStart(), Constants.DAY_FORMAT) : "");
				elt = eventElt.addElement("hour_start"); elt.setText(event.getStart() != null ? DateUtil.toString(event.getStart(), Constants.HOUR_FORMAT) : "");
				elt = eventElt.addElement("date_end"); elt.setText(event.getEnd() != null ? DateUtil.toString(event.getEnd(), Constants.DAY_FORMAT) : "");
				elt = eventElt.addElement("hour_end"); elt.setText(event.getEnd() != null ? DateUtil.toString(event.getEnd(), Constants.HOUR_FORMAT) : "");
				elt = eventElt.addElement("item_id"); elt.setText(event.getItem().getId().toString());
				
				// Image
				if (!StringUtil.isEmpty(event.getImage())) {
					str = Constants.DIR_EVENTS+"/"+event.getId()+"/"+event.getImage();
				}
				else {
					str = "";
				}
				elt = eventElt.addElement("image"); elt.setText(str);
			}
		}
		
		//---------------------------------------------------------------------
		// Ads
		//---------------------------------------------------------------------
		elt = dataElt.addElement("ads_date_expire");
		elt.setText(project.getAdsDateExpire() != null ? DateUtil.toString(project.getAdsDateExpire(), DateUtil.FORMAT_DATE_TIMESTAMP) : "");
		List<Playlist> playlists = playlistDao.findByProject(projectId);
		Element adsElt = dataElt.addElement("ads");
		Element adElt = null;
		Element typeElt = null;
		for (Playlist playlist : playlists) {
			if (playlist.getActive() != null && playlist.getActive()) {
				List<Ad> ads = adDao.findByPlaylist(playlist.getId());
				for (Ad ad : ads) {
					adElt = adsElt.addElement("ad");
					adElt.addAttribute("id", ad.getId().toString());
					elt = adElt.addElement("name"); elt.setText(ad.getName());
					
					if(playlist.getLicense() != null) {
						elt = adElt.addElement("terminal"); elt.setText(playlist.getLicense().getId().toString());
					}

					// Fullscreen ad
					if (ad.getFullscreen() != null & ad.getFullscreen()) {
						typeElt = adElt.addElement("fullscreen");
						// File
						String fileExtension = ad.getFile().substring(ad.getFile().lastIndexOf('.')+1).toUpperCase();
						elt = typeElt.addElement("file"); elt.setText(Constants.DIR_ADS+"/"+ad.getId()+"/"+Constants.DIR_ADS_FULLSCREEN+"/"+ad.getFile());
						if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
							elt.addAttribute("width", ad.getWidth().toString());
							elt.addAttribute("height", ad.getHeight().toString());
						}

						// Linked to
						elt = typeElt.addElement("linked_to");
						switch (ad.getLinkTo()) {
						case 1:
							elt.addAttribute("type", "url");
							elt.setText(ad.getUrl());
							break;
						case 2:
							elt.addAttribute("type", "item");
							elt.setText(ad.getItem().getId().toString());
							break;
						case 3:
							elt.addAttribute("type", "event");
							elt.setText(ad.getEvent().getId().toString());
							break;
						case 4:
							elt.addAttribute("type", "promotion");
							elt.setText(ad.getPromotion().getId().toString());
							break;
						default:
							break;
						}

						// Skip after
						elt = typeElt.addElement("skip_after");
						if ("MOV".equals(fileExtension) || "FLV".equals(fileExtension) || "MP4".equals(fileExtension)) {
							if (ad.getSkipAfterComplete() != null && ad.getSkipAfterComplete()) {
								elt.addAttribute("complete", "true");
							}
							else {
								elt.addAttribute("seconds", ad.getSkipAfterNbSec().toString());
							}
						}
						else if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
							if (ad.getSkipAfterAsEvent() != null && ad.getSkipAfterAsEvent()) {
								elt.addAttribute("actionscript_event", "true");
							}
							else {
								elt.addAttribute("seconds", ad.getSkipAfterNbSec().toString());
							}
						}
						else {
							elt.addAttribute("seconds", ad.getSkipAfterNbSec().toString());
						}
					}

					// Banner ad
					if (ad.getBanner() != null & ad.getBanner()) {
						typeElt = adElt.addElement("banner");
						// File
						String fileExtension = ad.getBannerFile().substring(ad.getBannerFile().lastIndexOf('.')+1).toUpperCase();
						elt = typeElt.addElement("file"); elt.setText(Constants.DIR_ADS+"/"+ad.getId()+"/"+Constants.DIR_ADS_BANNER+"/"+ad.getBannerFile());
						if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
							elt.addAttribute("width", ad.getBannerWidth().toString());
							elt.addAttribute("height", ad.getBannerHeight().toString());
						}

						// Linked to
						elt = typeElt.addElement("linked_to");
						switch (ad.getBannerLinkTo()) {
						case 1:
							elt.addAttribute("type", "url");
							elt.setText(ad.getBannerUrl());
							break;
						case 2:
							elt.addAttribute("type", "item");
							elt.setText(ad.getBannerItem().getId().toString());
							break;
						case 3:
							elt.addAttribute("type", "event");
							elt.setText(ad.getBannerEvent().getId().toString());
							break;
						case 4:
							elt.addAttribute("type", "promotion");
							elt.setText(ad.getBannerPromotion().getId().toString());
							break;
						case 5:
							elt.addAttribute("type", "fullscreenAd");
							break;
						default:
							break;
						}

						// Skip after
						elt = typeElt.addElement("skip_after");
						if ("MOV".equals(fileExtension) || "FLV".equals(fileExtension) || "MP4".equals(fileExtension)) {
							if (ad.getBannerSkipAfterComplete() != null && ad.getBannerSkipAfterComplete()) {
								elt.addAttribute("complete", "true");
							}
							else {
								elt.addAttribute("seconds", ad.getBannerSkipAfterNbSec().toString());
							}
						}
						else if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
							if (ad.getBannerSkipAfterAsEvent() != null && ad.getBannerSkipAfterAsEvent()) {
								elt.addAttribute("actionscript_event", "true");
							}
							else {
								elt.addAttribute("seconds", ad.getBannerSkipAfterNbSec().toString());
							}
						}
						else {
							elt.addAttribute("seconds", ad.getBannerSkipAfterNbSec().toString());
						}
					}
				}
			}
		}
		
		//---------------------------------------------------------------------
		// Settings
		//---------------------------------------------------------------------
		Settings settings = settingsDao.findByProject(projectId);
		if (settings == null) {
			settings = new Settings();
		}
		Element settingsElt = dataElt.addElement("settings");
		elt = settingsElt.addElement("clock_24hours"); elt.setText(settings.getClock() == 24 ? "true" : "false");
		elt = settingsElt.addElement("inactivity_delay"); elt.setText(settings.getInactivityDelay().toString());
		
		elt = settingsElt.addElement("displayItemInZone"); elt.setText(settings.getItemsInZone() != null && settings.getItemsInZone() ? "true" : "false");
		
		elt = settingsElt.addElement("mapItemsKeepRatio"); elt.setText(settings.getItemsKeepRatio() != null && settings.getItemsKeepRatio() ? "true" : "false");
		elt = settingsElt.addElement("mapItemsForceSize"); elt.setText(settings.getForceItemSizes() != null && settings.getForceItemSizes() ? "true" : "false");
		elt = settingsElt.addElement("bannersPosition"); elt.setText(settings.getBannerPosition() != null ? settings.getBannerPosition() : "top");
		if (settings.getForceItemSizes() != null && settings.getForceItemSizes()) {
			String sizes = settings.getLocationsWidth()+"x"+settings.getLocationsHeight()+"-"+settings.getOtherItemsWidth()+"x"+settings.getOtherItemsHeight();
			elt = settingsElt.addElement("mapItemsSize"); elt.setText(sizes);
		}
		elt = settingsElt.addElement("autoUploadContacts"); elt.setText(settings.getAutoUploadContacts() != null && settings.getAutoUploadContacts() ? "true" : "false");
		if (settings.getAutoUploadContacts() != null && settings.getAutoUploadContacts()) {
			Date hour = DateUtil.toDate(settings.getHourUploadContacts(), "hh:mm a");
			if (hour != null) {
				Calendar cal = Calendar.getInstance(); cal.setTime(hour);
				elt = settingsElt.addElement("autoUploadContactsTime"); elt.setText(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
			}
		}
		elt = settingsElt.addElement("autoCheckUpdate"); elt.setText(settings.getAutoUpdateProject() != null && settings.getAutoUpdateProject() ? "true" : "false");
		if (settings.getAutoUpdateProject() != null && settings.getAutoUpdateProject()) {
			Date hour = DateUtil.toDate(settings.getHourUpdateProject(), "hh:mm a");
			if (hour != null) {
				Calendar cal = Calendar.getInstance(); cal.setTime(hour);
				elt = settingsElt.addElement("autoCheckUpdateTime"); elt.setText(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
			}
		}
		elt = settingsElt.addElement("autoUpdateSoftware"); elt.setText(settings.getAutoUpdateSoftware() != null && settings.getAutoUpdateSoftware() ? "true" : "false");
		if (settings.getAutoUpdateSoftware() != null && settings.getAutoUpdateSoftware()) {
			Date hour = DateUtil.toDate(settings.getHourUpdateSoftware(), "hh:mm a");
			if (hour != null) {
				Calendar cal = Calendar.getInstance(); cal.setTime(hour);
				elt = settingsElt.addElement("autoUpdateSoftwareHour"); elt.setText(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
			}
			
			Integer tryTimes = settings.getUpdateTryTimes();
			elt = settingsElt.addElement("autoUpdateTryTimes"); elt.setText(String.valueOf(tryTimes));
		}
		elt = settingsElt.addElement("displayWholeMap"); elt.setText(settings.getDisplayWholeMap() != null && settings.getDisplayWholeMap() ? "true" : "false");
		elt = settingsElt.addElement("allowWebPageNavigation"); elt.setText(settings.getAllowWebPageNavigation() != null && settings.getAllowWebPageNavigation() ? "true" : "false");
		elt = settingsElt.addElement("mixPanelKey"); elt.setText(settings.getMixPanelKey() != null ? settings.getMixPanelKey() : "");

		
		//---------------------------------------------------------------------
		// Theme
		//---------------------------------------------------------------------
		Theme theme = themeDao.findByProject(projectId);
		if (theme == null) {
			theme = new Theme();
		}
		
		String uploadDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+project.getKey()+"/"+Constants.DIR_THEME;
		
		Element themeElt = dataElt.addElement("theme");
		elt = themeElt.addElement("leftPanel"); elt.setText(theme.getLeftPanel() == null ? "" : uploadDir + "/leftPanel/" + theme.getLeftPanel());
		elt = themeElt.addElement("landscapeBackground"); elt.setText(theme.getLandscapeBackground() == null ? "" : uploadDir + "/landscapeBackground/" + theme.getLandscapeBackground());
		elt = themeElt.addElement("landscapeLightFontColor"); elt.setText(theme.getLandscapeLightFontColor() == null ? "" : theme.getLandscapeLightFontColor());
		elt = themeElt.addElement("landscapeTextFontColor"); elt.setText(theme.getLandscapeTextFontColor() == null ? "" : theme.getLandscapeTextFontColor());
		elt = themeElt.addElement("landscapeDarkFontColor"); elt.setText(theme.getLandscapeDarkFontColor() == null ? "" : theme.getLandscapeDarkFontColor());
		elt = themeElt.addElement("landscapePromotionHighlightColor"); elt.setText(theme.getLandscapePromotionHighlightColor() == null ? "" : theme.getLandscapePromotionHighlightColor());
		elt = themeElt.addElement("landscapeCategoryDefault"); elt.setText(theme.getLandscapeCategoryDefault() == null ? "" : theme.getLandscapeCategoryDefault());
		elt = themeElt.addElement("landscapeCategorySelected"); elt.setText(theme.getLandscapeCategorySelected() == null ? "" : theme.getLandscapeCategorySelected());
		elt = themeElt.addElement("landscapeNavigationHeaderColor"); elt.setText(theme.getLandscapeNavigationHeaderColor() == null ? "" : theme.getLandscapeNavigationHeaderColor());
		elt = themeElt.addElement("landscapeNavigationHeaderFontColor"); elt.setText(theme.getLandscapeNavigationHeaderFontColor() == null ? "" : theme.getLandscapeNavigationHeaderFontColor());
		elt = themeElt.addElement("landscapeNavigationBackgroundColor"); elt.setText(theme.getLandscapeNavigationBackgroundColor() == null ? "" : theme.getLandscapeNavigationBackgroundColor());
		elt = themeElt.addElement("landscapeNavigationSelectedBackgroundColor"); elt.setText(theme.getLandscapeNavigationSelectedBackgroundColor() == null ? "" : theme.getLandscapeNavigationSelectedBackgroundColor());
		
		
		elt = themeElt.addElement("topPanel"); elt.setText(theme.getTopPanel() == null ? "" : uploadDir + "/topPanel/" + theme.getTopPanel());
		elt = themeElt.addElement("portraitBackground"); elt.setText(theme.getPortraitBackground() == null ? "" : uploadDir + "/portraitBackground/" + theme.getPortraitBackground());
		elt = themeElt.addElement("portraitLightFontColor"); elt.setText(theme.getPortraitLightFontColor() == null ? "" : theme.getPortraitLightFontColor());
		elt = themeElt.addElement("portraitTextFontColor"); elt.setText(theme.getPortraitTextFontColor() == null ? "" : theme.getPortraitTextFontColor());
		elt = themeElt.addElement("portraitDarkFontColor"); elt.setText(theme.getPortraitDarkFontColor() == null ? "" : theme.getPortraitDarkFontColor());
		elt = themeElt.addElement("portraitPromotionHighlightColor"); elt.setText(theme.getPortraitPromotionHighlightColor() == null ? "" : theme.getPortraitPromotionHighlightColor());
		elt = themeElt.addElement("portraitCategoryDefault"); elt.setText(theme.getPortraitCategoryDefault() == null ? "" : theme.getPortraitCategoryDefault());
		elt = themeElt.addElement("portraitCategorySelected"); elt.setText(theme.getPortraitCategorySelected() == null ? "" : theme.getPortraitCategorySelected());
		elt = themeElt.addElement("portraitNavigationHeaderColor"); elt.setText(theme.getPortraitNavigationHeaderColor() == null ? "" : theme.getPortraitNavigationHeaderColor());
		elt = themeElt.addElement("portraitNavigationHeaderFontColor"); elt.setText(theme.getPortraitNavigationHeaderFontColor() == null ? "" : theme.getPortraitNavigationHeaderFontColor());
		elt = themeElt.addElement("portraitNavigationBackgroundColor"); elt.setText(theme.getPortraitNavigationBackgroundColor() == null ? "" : theme.getPortraitNavigationBackgroundColor());
		elt = themeElt.addElement("portraitNavigationSelectedBackgroundColor"); elt.setText(theme.getPortraitNavigationSelectedBackgroundColor() == null ? "" : theme.getPortraitNavigationSelectedBackgroundColor());
		
		elt = themeElt.addElement("iwHeaderBackground"); elt.setText(theme.getIwHeaderBackground() == null ? "" : uploadDir + "/iwHeaderBackground/" + theme.getIwHeaderBackground());
		elt = themeElt.addElement("iwTouchWall"); elt.setText(theme.getIwTouchWall() == null ? "" : uploadDir + "/iwTouchWall/" + theme.getIwTouchWall());
		elt = themeElt.addElement("iwMainBackground"); elt.setText(theme.getIwMainBackground() == null ? "" : uploadDir + "/iwMainBackground/" + theme.getIwMainBackground());
		elt = themeElt.addElement("iwFooterBackground"); elt.setText(theme.getIwFooterBackground() == null ? "" : uploadDir + "/iwFooterBackground/" + theme.getIwFooterBackground());
		elt = themeElt.addElement("iwButton"); elt.setText(theme.getIwButton() == null ? "" : uploadDir + "/iwButton/" + theme.getIwButton());
		elt = themeElt.addElement("iwSelectedButton"); elt.setText(theme.getIwSelectedButton() == null ? "" : uploadDir + "/iwSelectedButton/" + theme.getIwSelectedButton());

		//---------------------------------------------------------------------
		// Paths (items map)
		//---------------------------------------------------------------------
		if (!StringUtil.isEmpty(paths)) {
			Document pathsDoc = XmlUtil.parse(paths);
			if (pathsDoc != null) {
				dataElt.add(pathsDoc.getRootElement());
			}
		}

		//---------------------------------------------------------------------
		// Force update
		//---------------------------------------------------------------------
		if ("forceUpdate".equals(publishMode)) {
			Element forceUpdateElt = dataElt.addElement("force_update");
			forceUpdateElt.setText("true");			
		}
		
		saveProjectFile(project.getKey(), document.asXML());
		
		//PrintWriter fileWriter = new PrintWriter(pathname+"/project.xml", "UTF-8");
		//fileWriter.print(ThreeDESCode.encryptThreeDESECB(document.asXML()));
		//fileWriter.close();
		
	}

	public void addDevicesToProject(String key) {
		Project project = projectDao.findByKey(key);
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		File projectFile = new File(projectExportPath+"/"+ project.getKey() + "/project.xml");
		
		try {
			String content = org.apache.commons.io.FileUtils.readFileToString(projectFile, "UTF-8");
			Document document = DocumentHelper.parseText(ThreeDESCode.decryptThreeDESECB(content));
			Element root = document.getRootElement();
			Element devicesElt = root.element("devices");
			devicesElt.detach();
			devicesElt = root.addElement("devices");
			
			Element elt = null;
	        for (Device device : deviceDao.findAll()) {
	        	elt = devicesElt.addElement("device"); elt.setText(device.getKey());
			}
	        org.apache.commons.io.FileUtils.writeStringToFile(projectFile, encrypt ? ThreeDESCode.encryptThreeDESECB(document.asXML()) : document.asXML(), "UTF-8");
//	        org.apache.commons.io.FileUtils.writeStringToFile(projectFile, encrypt ? ThreeDESCode.encryptThreeDESECB(document.asXML()) : document.asXML(), "UTF-8", false);
		} catch(Exception e) {
		}
	}
	
	private boolean encrypt = true;
	
	public void setEncrypt(String encrypt) {
		this.encrypt = Boolean.parseBoolean(encrypt);
	}
	
	public void saveProjectFile(String key, String content) {
		//---------------------------------------------------------------------
		// Generate "project.xml" file
		//---------------------------------------------------------------------
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		String pathname = projectExportPath+"/" + key;
		File path = new File(pathname);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		try {
			org.apache.commons.io.FileUtils.writeStringToFile(new File(pathname+"/project.xml"), encrypt ? ThreeDESCode.encryptThreeDESECB(content) : content, "UTF-8");
//			org.apache.commons.io.FileUtils.writeStringToFile(new File(pathname+"/project.xml"), encrypt ? ThreeDESCode.encryptThreeDESECB(content) : content, "UTF-8", false);
		} catch(Exception e) {
		}
	}
	
	public String getProjectFile(String key) {
		//---------------------------------------------------------------------
		// Generate "project.xml" file
		//---------------------------------------------------------------------
		String projectExportPath = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS;
		String pathname = projectExportPath+"/" + key;

		try {
			String content = org.apache.commons.io.FileUtils.readFileToString(new File(pathname+"/project.xml"), "UTF-8");
			return encrypt ? ThreeDESCode.decryptThreeDESECB(content) : content;
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * List all project items
	 * @param projectId
	 * @throws Exception
	 */
	public String listItems(Integer projectId) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element dataElt = document.addElement("data");
		Element elt = null;

		//---------------------------------------------------------------------
		// Items
		//---------------------------------------------------------------------
		// Add "location" items 
		Element itemsListElt = dataElt.addElement("items_list");
		Element itemsElt = itemsListElt.addElement("locations");
		Element itemElt = null;
		List<Item> items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_LOCATION);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("location");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName() != null ? item.getName() : "");
			}
		}
		
		// Add "FUTO" items 
		itemsElt = itemsListElt.addElement("futos");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_FUTO);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("futo");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName() != null ? item.getName() : "");
			}
		}
		
		// Add "access" items
		itemsElt = itemsListElt.addElement("accesses");
		items = itemDao.findByProjectAndType(projectId, Constants.ITEM_TYPE_ACCESS);
		for (Item item : items) {
			if (!StringUtil.isEmpty(item.getName())) {
				itemElt = itemsElt.addElement("access");
				itemElt.addAttribute("id", item.getId().toString());
				elt = itemElt.addElement("name"); elt.setText(item.getName() != null ? item.getName() : "");
			}
		}

		//---------------------------------------------------------------------
		// Generate XML string
		//---------------------------------------------------------------------
		return document.asXML();
	}

	/**
	 * Save item's positions and paths
	 * @param projectId
	 * @param xml
	 * @throws Exception
	 */
	public void save(Integer projectId, String xml) throws Exception {
		System.out.println("MapService save project id is: '" + projectId.toString());
		
		Project project = projectDao.findById(projectId);
		project.setMapConfig(xml);
		projectDao.saveOrUpdate(project);
	}
}
