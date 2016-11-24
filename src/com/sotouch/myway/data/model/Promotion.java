package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Promotion extends BaseDataBean {

	private String name;
	private Date start;
	private Date end;
	private String image;
	private Project project;
	private Item item;
	private List<PromotionContent> promotionContents = new ArrayList<PromotionContent>();

	public Promotion() {
		super();
	}

	public Promotion(Integer id) {
		super(id);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return this.start;
	}
	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return this.end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Item getItem() {
		return this.item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	public List<PromotionContent> getPromotionContents() {
		return promotionContents;
	}
	public void setPromotionContents(List<PromotionContent> promotionContents) {
		this.promotionContents = promotionContents;
	}
}
