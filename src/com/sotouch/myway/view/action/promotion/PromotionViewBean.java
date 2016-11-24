package com.sotouch.myway.view.action.promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class PromotionViewBean extends BaseViewBean {
	
	private String name;
	private String start;
	private String hourStart;
	private String end;
	private String hourEnd;
	private String image;
	private String imageContentType;
	private String imageFileName;
	private String project;
	private Integer projectId;
	private String item;
	private Integer itemId;
	private List<PromotionContentViewBean> promotionContents = new ArrayList<PromotionContentViewBean>();

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getStart() {
		return this.start;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public String getHourStart() {
		return hourStart;
	}
	public void setHourStart(String hourStart) {
		this.hourStart = hourStart;
	}

	public String getEnd() {
		return this.end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

	public String getHourEnd() {
		return hourEnd;
	}
	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}

	public String getImage() {
		return image;
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

	public String getItem() {
		return this.item;
	}
	public void setItem(String item) {
		this.item = item;
	}

	public Integer getItemId() {
		return this.itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public List<PromotionContentViewBean> getPromotionContents() {
		return promotionContents;
	}
	public void setPromotionContents(List<PromotionContentViewBean> promotionContents) {
		this.promotionContents = promotionContents;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(start)) {
			errors.add(new JSONError("viewBean.start", resources.getString("promotion.erreur.start.obligatoire")));
		}

		if (StringUtil.isEmpty(hourStart)) {
			//errors.add(new JSONError("viewBean.hourStart", resources.getString("promotion.erreur.hourStart.obligatoire")));
			hourStart = "12:00 AM";
		}

		if (StringUtil.isEmpty(end)) {
			errors.add(new JSONError("viewBean.end", resources.getString("promotion.erreur.end.obligatoire")));
		}

		if (StringUtil.isEmpty(hourEnd)) {
			//errors.add(new JSONError("viewBean.hourEnd", resources.getString("promotion.erreur.hourEnd.obligatoire")));
			hourEnd = "12:00 AM";
		}

		int i = 0;
		for (PromotionContentViewBean promotionContentViewBean : promotionContents) {
			if (StringUtil.isEmpty(promotionContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.promotionContents["+i+"].name", resources.getString("promotionContent.erreur.name.obligatoire")));
			}
			i++;
		}
		return errors;
	}
	
}
