package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;

public class MapItem extends BaseDataBean {
	
	private String type;
	private String itemId;
	private String position;
	private String shape;
	private String containitems;
	private String zonebelongto;
	private String paths;
	private Project project;

	public MapItem() {
		super();
		this.position = "";
		this.shape = "";
		this.containitems = "";
		this.zonebelongto = "";
		this.paths = "";
	}

	public MapItem(Integer id) {
		super(id);
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String x) {
		this.type = x;
	}

	public String getItemId() {
		return this.itemId;
	}
	public void setItemId(String x) {
		this.itemId = x;
	}

	public String getPosition() {
		return this.position;
	}
	public void setPosition(String x) {
		this.position = x;
	}

	public String getShape() {
		return this.shape;
	}
	public void setShape(String x) {
		this.shape = x;
	}

	public String getContainitems() {
		return this.containitems;
	}
	public void setContainitems(String x) {
		this.containitems = x;
	}

	public String getZonebelongto() {
		return this.zonebelongto;
	}
	public void setZonebelongto(String x) {
		this.zonebelongto = x;
	}

	public String getPaths() {
		return this.paths;
	}
	public void setPaths(String x) {
		this.paths = x;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project x) {
		this.project = x;
	}

	public String toXml() {
		String xml = null;
		xml = "<point type=\""+ type + "\" id=\""+ id+"\" itemId=\""+itemId+"\">"
		+ ((position == null) ? "": position)
		+ ((shape == null || shape.isEmpty()) ? "": shape)
		+ ((containitems == null || containitems.isEmpty()) ? "": containitems)
		+ ((zonebelongto == null || zonebelongto.isEmpty()) ? "": zonebelongto)
		+ ((paths == null || paths.isEmpty()) ? "": paths)
		+ "</point>";
		return xml;
	}
	
	public String toForm() {
		String form = "";
		form = "<div><form method='post'><input type='hidden' name='itemId' value='" + this.itemId + "'/><input type='hidden' name='id' value='" + this.id + "'/>";
		form += "<table><tr><td>Position:</td><td><input value='" + ((position == null) ? "": position) + "' /></td></tr>"
				+"<tr><td>Shape</td><td><input type='text' value='" + ((shape == null || shape.isEmpty()) ? "": shape) + "' /></td></tr>"
				+"<tr><td>Contain Items</td><td><input type='text' value='" + ((containitems == null || containitems.isEmpty()) ? "": containitems) + "' /></td></tr>"
				+"<tr><td>Zone Belong To</td><td><input type='text' value='" + ((zonebelongto == null || zonebelongto.isEmpty()) ? "": zonebelongto) + "' /></td></tr>"
				+"<tr><td>Paths</td><td><textarea value='" + ((paths == null || paths.isEmpty()) ? "": paths) + "' /></td></tr>"
				+"</table></form></div>";
		return form;
	}

}
