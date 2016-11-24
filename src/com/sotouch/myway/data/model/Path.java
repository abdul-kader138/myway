package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Path extends BaseDataBean {

	private String fastest;
	private String easiest;
	private ItemMap itemMapStart;
	private ItemMap itemMapEnd;

	public Path() {
		super();
	}

	public Path(Integer id) {
		super(id);
	}

	public String getFastest() {
		return this.fastest;
	}
	public void setFastest(String fastest) {
		this.fastest = fastest;
	}

	public String getEasiest() {
		return this.easiest;
	}
	public void setEasiest(String easiest) {
		this.easiest = easiest;
	}

	public ItemMap getItemMapStart() {
		return this.itemMapStart;
	}
	public void setItemMapStart(ItemMap itemMapStart) {
		this.itemMapStart = itemMapStart;
	}

	public ItemMap getItemMapEnd() {
		return this.itemMapEnd;
	}
	public void setItemMapEnd(ItemMap itemMapEnd) {
		this.itemMapEnd = itemMapEnd;
	}

}
