package com.sotouch.myway.view.action.event;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class EventViewBean extends BaseViewBean {
	
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
	private List<EventContentViewBean> eventContents = new ArrayList<EventContentViewBean>();

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

	public List<EventContentViewBean> getEventContents() {
		return eventContents;
	}
	public void setEventContents(List<EventContentViewBean> eventContents) {
		this.eventContents = eventContents;
	}

	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(start)) {
			errors.add(new JSONError("viewBean.start", resources.getString("event.erreur.start.obligatoire")));
		}

		if (StringUtil.isEmpty(hourStart)) {
			//errors.add(new JSONError("viewBean.hourStart", resources.getString("event.erreur.hourStart.obligatoire")));
			hourStart = "12:00 AM";
		}

		if (StringUtil.isEmpty(end)) {
			errors.add(new JSONError("viewBean.end", resources.getString("event.erreur.end.obligatoire")));
		}

		if (StringUtil.isEmpty(hourEnd)) {
			//errors.add(new JSONError("viewBean.hourEnd", resources.getString("event.erreur.hourEnd.obligatoire")));
			hourEnd = "11:59 PM";
		}

		int i = 0;
		for (EventContentViewBean eventContentViewBean : eventContents) {
			if (StringUtil.isEmpty(eventContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.eventContents["+i+"].name", resources.getString("eventContent.erreur.name.obligatoire")));
			}
			i++;
		}

		return errors;
	}
	
}
