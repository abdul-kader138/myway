package com.sotouch.myway.view.action.building;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

public class FloorViewBean extends BaseViewBean {
	
	private String name;
	private String label;
	private String image;
	private String imageContentType;
	private String imageFileName;
	private String index;
	private Integer buildingId;

	private List<FloorContentViewBean> floorContents;
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
	
	public String getIndex() {
		return this.index;
	}
	public void setIndex(String index) {
		this.index = index;
	}

	public Integer getBuildingId() {
		return this.buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public List<FloorContentViewBean> getFloorContents() {
		return floorContents;
	}
	
	public void setFloorContents(List<FloorContentViewBean> floorContents) {
		this.floorContents = floorContents;
	}
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		//if (StringUtil.isEmpty(name)) {
		//	errors.add(new JSONError("viewBean.name", resources.getString("floor.erreur.name.obligatoire")));
		//}

		/* if (StringUtil.isEmpty(label)) {
			errors.add(new JSONError("viewBean.label", resources.getString("floor.erreur.label.obligatoire")));
		}
		else */ 
		//if (label.length() > 2) {
		//	errors.add(new JSONError("viewBean.label", resources.getString("floor.erreur.label.tooLong")));
		//}

		if (index == null) {
			errors.add(new JSONError("viewBean.index", resources.getString("floor.erreur.index.obligatoire")));
		}

		if (id == null && imageFileName == null) {
			errors.add(new JSONError("viewBean.image", resources.getString("floor.erreur.image.obligatoire")));
		}
		if (imageFileName != null && !imageFileName.toLowerCase().endsWith(".png") && !imageFileName.toLowerCase().endsWith(".gif") && !imageFileName.toLowerCase().endsWith(".jpg") && !imageFileName.toLowerCase().endsWith(".swf")) {
			errors.add(new JSONError("viewBean.image", resources.getString("floor.erreur.image.format")));
		}
		
		int i = 0;
		for (FloorContentViewBean floorContentViewBean : floorContents) {
			
			if (StringUtil.isEmpty(getLabel())) {
				errors.add(new JSONError("viewBean.label", resources.getString("floorContent.erreur.name.obligatoire")));
			}
			else if(getLabel().length() > 2) {
				errors.add(new JSONError("viewBean.label", resources.getString("floor.erreur.label.tooLong")));
			}
			
			if (StringUtil.isEmpty(floorContentViewBean.getName())) {
				errors.add(new JSONError("viewBean.floorContents["+i+"].name", resources.getString("floorContent.erreur.name.obligatoire")));
			}
			
			
			i++;
		}

		return errors;
	}
	
}
