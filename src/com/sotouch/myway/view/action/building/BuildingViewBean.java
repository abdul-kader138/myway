package com.sotouch.myway.view.action.building;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.view.action.license.LicenseContentViewBean;

public class BuildingViewBean extends BaseViewBean {
	
	private String name;
	private String index;
	private String project;
	private Integer projectId;
	private List<BuildingContentViewBean> buildingContents;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
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

	public List<BuildingContentViewBean> getBuildingContents() {
		return buildingContents;
	}
	
	public void setBuildingContents(List<BuildingContentViewBean> buildingContents) {
		this.buildingContents = buildingContents;
	}
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		/*if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("building.erreur.name.obligatoire")));
		}*/

		int i = 0;
		for (BuildingContentViewBean buildingContentViewBean : buildingContents) {
			if (StringUtil.isEmpty(buildingContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.buildingContents["+i+"].name", resources.getString("buildingContent.erreur.name.obligatoire")));
			}
			i++;
		}
		
		return errors;
	}
}
