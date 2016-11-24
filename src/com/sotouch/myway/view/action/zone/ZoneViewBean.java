package com.sotouch.myway.view.action.zone;

import com.sotouch.myway.view.action.item.ItemViewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class ZoneViewBean extends BaseViewBean
{
	private String name;
	private String color;
	private Integer buildingId;
	private Integer transparency;
	private String image;
	private String imageContentType;
	private String imageFileName;
  
	private String visual;
	private Boolean itemsInZone = false;
  
  	private String category;
	private Integer categoryId;
	private String subCategory;
	private Integer subCategoryId;
  
	private List<ZoneContentViewBean> zoneContents = new ArrayList();

	private List<ItemViewBean> items = new ArrayList();

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public Integer getBuildingId() {
		return this.buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
  
	public Integer getTransparency() {
		return this.transparency;
	}

	public void setTransparency(Integer transparency) {
		this.transparency = transparency;
	}
  
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	  
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
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

	public List<ZoneContentViewBean> getZoneContents() {
		return this.zoneContents;
	}
	public void setZoneContents(List<ZoneContentViewBean> zoneContents) {
		this.zoneContents = zoneContents;
	}

	public List<ItemViewBean> getItems() {
		return this.items;
	}

	public void setItems(List<ItemViewBean> items) {
		this.items = items;
	}
  
	public String getVisual() {
		return visual;
	}
	
	public void setVisual(String visual) {
		this.visual = visual;
	}
	
	public Boolean getItemsInZone() {
		return this.itemsInZone;
	}
	public void setItemsInZone(Boolean itemsInZone) {
		this.itemsInZone = itemsInZone;
	}

	public List<JSONError> validate(ResourceBundle resources) {
		List errors = new ArrayList();

		if (StringUtil.isEmpty(this.color)) {
			errors.add(new JSONError("viewBean.color", resources.getString("zoneContent.erreur.color.obligatoire")));
		}

		int i = 0;
		for (ZoneContentViewBean ZoneContentViewBean : this.zoneContents) {
			if (StringUtil.isEmpty(ZoneContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.zoneContents[" + i + "].name", resources.getString("zoneContent.erreur.name.obligatoire")));
			}
			i++;
		}

		return errors;
	}
}
