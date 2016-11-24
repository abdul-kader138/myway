package com.sotouch.myway.data.model;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Ad extends BaseDataBean {

	private String name;
	private Integer index;
	private Playlist playlist;

	private Boolean fullscreen;
	private String file;
	private Integer skipAfterNbSec;
	private Boolean skipAfterAsEvent;
	private Boolean skipAfterComplete;
	private Integer width = 550;
	private Integer height = 400;
	private Integer linkTo;
	private Item item;
	private Event event;
	private Promotion promotion;
	private String url;

	private Boolean banner;
	private String bannerFile;
	private Integer bannerSkipAfterNbSec;
	private Boolean bannerSkipAfterAsEvent;
	private Boolean bannerSkipAfterComplete;
	private Integer bannerWidth = 468;
	private Integer bannerHeight = 60;
	private Integer bannerLinkTo;
	private Item bannerItem;
	private Event bannerEvent;
	private Promotion bannerPromotion;
	private String bannerUrl;

	public Ad() {
		super();
	}

	public Ad(Integer id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return this.index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Playlist getPlaylist() {
		return this.playlist;
	}
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Boolean getFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(Boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public String getFile() {
		return this.file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public Integer getSkipAfterNbSec() {
		return this.skipAfterNbSec;
	}
	public void setSkipAfterNbSec(Integer skipAfterNbSec) {
		this.skipAfterNbSec = skipAfterNbSec;
	}

	public Boolean getSkipAfterAsEvent() {
		return this.skipAfterAsEvent;
	}
	public void setSkipAfterAsEvent(Boolean skipAfterAsEvent) {
		this.skipAfterAsEvent = skipAfterAsEvent;
	}

	public Boolean getSkipAfterComplete() {
		return skipAfterComplete;
	}
	public void setSkipAfterComplete(Boolean skipAfterComplete) {
		this.skipAfterComplete = skipAfterComplete;
	}

	public Integer getWidth() {
		return this.width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getLinkTo() {
		return this.linkTo;
	}
	public void setLinkTo(Integer linkTo) {
		this.linkTo = linkTo;
	}

	public Item getItem() {
		return this.item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	public Event getEvent() {
		return this.event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

	public Promotion getPromotion() {
		return this.promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getBanner() {
		return banner;
	}
	public void setBanner(Boolean banner) {
		this.banner = banner;
	}

	public String getBannerFile() {
		return bannerFile;
	}
	public void setBannerFile(String bannerFile) {
		this.bannerFile = bannerFile;
	}

	public Integer getBannerSkipAfterNbSec() {
		return bannerSkipAfterNbSec;
	}
	public void setBannerSkipAfterNbSec(Integer bannerSkipAfterNbSec) {
		this.bannerSkipAfterNbSec = bannerSkipAfterNbSec;
	}

	public Boolean getBannerSkipAfterAsEvent() {
		return bannerSkipAfterAsEvent;
	}
	public void setBannerSkipAfterAsEvent(Boolean bannerSkipAfterAsEvent) {
		this.bannerSkipAfterAsEvent = bannerSkipAfterAsEvent;
	}

	public Boolean getBannerSkipAfterComplete() {
		return bannerSkipAfterComplete;
	}
	public void setBannerSkipAfterComplete(Boolean bannerSkipAfterComplete) {
		this.bannerSkipAfterComplete = bannerSkipAfterComplete;
	}

	public Integer getBannerWidth() {
		return bannerWidth;
	}
	public void setBannerWidth(Integer bannerWidth) {
		this.bannerWidth = bannerWidth;
	}

	public Integer getBannerHeight() {
		return bannerHeight;
	}
	public void setBannerHeight(Integer bannerHeight) {
		this.bannerHeight = bannerHeight;
	}

	public Integer getBannerLinkTo() {
		return bannerLinkTo;
	}
	public void setBannerLinkTo(Integer bannerLinkTo) {
		this.bannerLinkTo = bannerLinkTo;
	}

	public Item getBannerItem() {
		return bannerItem;
	}
	public void setBannerItem(Item bannerItem) {
		this.bannerItem = bannerItem;
	}

	public Event getBannerEvent() {
		return bannerEvent;
	}
	public void setBannerEvent(Event bannerEvent) {
		this.bannerEvent = bannerEvent;
	}

	public Promotion getBannerPromotion() {
		return bannerPromotion;
	}
	public void setBannerPromotion(Promotion bannerPromotion) {
		this.bannerPromotion = bannerPromotion;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
}
