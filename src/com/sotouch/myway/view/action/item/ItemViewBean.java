package com.sotouch.myway.view.action.item;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.Constants;

public class ItemViewBean extends BaseViewBean {
	
	private String name;
	private String logo;
	private String logoContentType;
	private String logoFileName;
	private String address;
	private String phoneNumber;
	private String email;
	private String hourBegin;
	private String hourEnd;
	private String photos;
	private String photosDir;
	private boolean disabledAccess;
	private Integer type;
	private String itemType;
	private Integer itemTypeId;
	private String project;
	private Integer projectId;
	private String category;
	private Integer categoryId;
	private String subCategory;
	private Integer subCategoryId;
	private String zone;
	private Integer zoneId;
	private String icon;
	private Integer iconId;
	private String openingDays;
	private String visual;
	private String keywords;
	private List<ItemContentViewBean> itemContents = new ArrayList<ItemContentViewBean>();

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoContentType() {
		return logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getHourBegin() {
		return this.hourBegin;
	}
	public void setHourBegin(String hourBegin) {
		this.hourBegin = hourBegin;
	}

	public String getHourEnd() {
		return this.hourEnd;
	}
	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}

	public String getPhotos() {
		return this.photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPhotosDir() {
		return photosDir;
	}
	public void setPhotosDir(String photosDir) {
		this.photosDir = photosDir;
	}

	public boolean isDisabledAccess() {
		return disabledAccess;
	}
	public void setDisabledAccess(boolean disabledAccess) {
		this.disabledAccess = disabledAccess;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public String getItemType() {
		return this.itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getItemTypeId() {
		return this.itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getProject() {
		return this.project;
	}
	public void setProject(String project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getCategory() {
		return this.category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategory() {
		return this.subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getSubCategoryId() {
		return this.subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	
	public String getZone() {
		return this.zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getZoneId() {
		return this.zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Integer getIconId() {
		return iconId;
	}
	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}

	public String getOpeningDays() {
		return openingDays;
	}
	public void setOpeningDays(String openingDays) {
		this.openingDays = openingDays;
	}

	public String getVisual() {
		return visual;
	}
	public void setVisual(String visual) {
		this.visual = visual;
	}
	
	public List<ItemContentViewBean> getItemContents() {
		return itemContents;
	}
	public void setItemContents(List<ItemContentViewBean> itemContents) {
		this.itemContents = itemContents;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (itemTypeId == Constants.ITEM_TYPE_LOCATION && categoryId == null) {
			errors.add(new JSONError("viewBean.categoryId", resources.getString("item.erreur.category.obligatoire")));
		}
		
		//if (itemTypeId == Constants.ITEM_TYPE_LOCATION && subCategoryId == null) {
		//	errors.add(new JSONError("viewBean.subCategoryId", resources.getString("item.erreur.subCategory.obligatoire")));
		//}

		if (id == null) {
			if (itemTypeId == Constants.ITEM_TYPE_LOCATION && logo == null) {
				errors.add(new JSONError("viewBean.logo", resources.getString("item.erreur.logo.obligatoire")));
			}

			if (itemTypeId != Constants.ITEM_TYPE_LOCATION && itemTypeId != Constants.ITEM_TYPE_WEBVIEW && iconId == null) {
				errors.add(new JSONError("viewBean.iconId", resources.getString("item.erreur.icon.obligatoire")));
			}
		}

		int i = 0;
		for (ItemContentViewBean itemContentViewBean : itemContents) {
			if (StringUtil.isEmpty(itemContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.itemContents["+i+"].name", resources.getString("itemContent.erreur.name.obligatoire")));
			}
			i++;
		}

		return errors;
	}
	
}
