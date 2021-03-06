package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class PromotionContent extends BaseDataBean {

	private String name;
	private String description;
	private String keywords;
	private Integer index;
	private Promotion promotion;
	private Language language;

	public PromotionContent() {
		super();
	}

	public PromotionContent(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getKeywords() {
		return this.keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Language getLanguage() {
		return this.language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

}
