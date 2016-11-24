package com.sotouch.myway.view.action.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.service.util.MyWayUtil;

public class AdViewBean extends BaseViewBean {
	
	private String name;
	private Integer index;
	private Integer playlistId;

	private String fullscreen;
	private String file;
	private String fileContentType;
	private String fileFileName;
	private String skipAfter;
	private String skipAfterNbSec;
	private Boolean skipAfterAsEvent;
	private Boolean skipAfterComplete;
	private String width;
	private String height;
	private String linkTo;
	private String item;
	private Integer itemId;
	private String event;
	private Integer eventId;
	private String promotion;
	private Integer promotionId;
	private String url;
	private String linkedTo;
	private String type;

	private String banner;
	private String bannerFile;
	private String bannerFileContentType;
	private String bannerFileFileName;
	private String bannerSkipAfter;
	private String bannerSkipAfterNbSec;
	private Boolean bannerSkipAfterAsEvent;
	private Boolean bannerSkipAfterComplete;
	private String bannerWidth;
	private String bannerHeight;
	private String bannerLinkTo;
	private String bannerItem;
	private Integer bannerItemId;
	private String bannerEvent;
	private Integer bannerEventId;
	private String bannerPromotion;
	private Integer bannerPromotionId;
	private String bannerUrl;
	private String bannerLinkedTo;
	private String bannerType;

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

	public Integer getPlaylistId() {
		return this.playlistId;
	}
	public void setPlaylistId(Integer playlistId) {
		this.playlistId = playlistId;
	}

	public String getFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(String fullscreen) {
		this.fullscreen = fullscreen;
	}

	public String getFile() {
		return this.file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public String getSkipAfter() {
		return skipAfter;
	}
	public void setSkipAfter(String skipAfter) {
		this.skipAfter = skipAfter;
	}

	public String getSkipAfterNbSec() {
		return this.skipAfterNbSec;
	}
	public void setSkipAfterNbSec(String skipAfterNbSec) {
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

	public String getWidth() {
		return this.width;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return this.height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	public String getLinkTo() {
		return this.linkTo;
	}
	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
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

	public String getEvent() {
		return this.event;
	}
	public void setEvent(String event) {
		this.event = event;
	}

	public Integer getEventId() {
		return this.eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getPromotion() {
		return this.promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public Integer getPromotionId() {
		return this.promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinkedTo() {
		return linkedTo;
	}
	public void setLinkedTo(String linkedTo) {
		this.linkedTo = linkedTo;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBannerFile() {
		return bannerFile;
	}
	public void setBannerFile(String bannerFile) {
		this.bannerFile = bannerFile;
	}
	
	public String getBannerFileContentType() {
		return bannerFileContentType;
	}
	public void setBannerFileContentType(String bannerFileContentType) {
		this.bannerFileContentType = bannerFileContentType;
	}
	
	public String getBannerFileFileName() {
		return bannerFileFileName;
	}
	public void setBannerFileFileName(String bannerFileFileName) {
		this.bannerFileFileName = bannerFileFileName;
	}
	
	public String getBannerSkipAfter() {
		return bannerSkipAfter;
	}
	public void setBannerSkipAfter(String bannerSkipAfter) {
		this.bannerSkipAfter = bannerSkipAfter;
	}
	
	public String getBannerSkipAfterNbSec() {
		return bannerSkipAfterNbSec;
	}
	public void setBannerSkipAfterNbSec(String bannerSkipAfterNbSec) {
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
	
	public String getBannerWidth() {
		return bannerWidth;
	}
	public void setBannerWidth(String bannerWidth) {
		this.bannerWidth = bannerWidth;
	}
	
	public String getBannerHeight() {
		return bannerHeight;
	}
	public void setBannerHeight(String bannerHeight) {
		this.bannerHeight = bannerHeight;
	}
	
	public String getBannerLinkTo() {
		return bannerLinkTo;
	}
	public void setBannerLinkTo(String bannerLinkTo) {
		this.bannerLinkTo = bannerLinkTo;
	}
	
	public String getBannerItem() {
		return bannerItem;
	}
	public void setBannerItem(String bannerItem) {
		this.bannerItem = bannerItem;
	}
	
	public Integer getBannerItemId() {
		return bannerItemId;
	}
	public void setBannerItemId(Integer bannerItemId) {
		this.bannerItemId = bannerItemId;
	}
	
	public String getBannerEvent() {
		return bannerEvent;
	}
	public void setBannerEvent(String bannerEvent) {
		this.bannerEvent = bannerEvent;
	}
	
	public Integer getBannerEventId() {
		return bannerEventId;
	}
	public void setBannerEventId(Integer bannerEventId) {
		this.bannerEventId = bannerEventId;
	}
	
	public String getBannerPromotion() {
		return bannerPromotion;
	}
	public void setBannerPromotion(String bannerPromotion) {
		this.bannerPromotion = bannerPromotion;
	}
	
	public Integer getBannerPromotionId() {
		return bannerPromotionId;
	}
	public void setBannerPromotionId(Integer bannerPromotionId) {
		this.bannerPromotionId = bannerPromotionId;
	}
	
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	
	public String getBannerLinkedTo() {
		return bannerLinkedTo;
	}
	public void setBannerLinkedTo(String bannerLinkedTo) {
		this.bannerLinkedTo = bannerLinkedTo;
	}
	
	public String getBannerType() {
		return bannerType;
	}
	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}
	
	
	public List<JSONError> validate(ResourceBundle resources){
		List<JSONError> errors = new ArrayList<JSONError>();
		
		if (StringUtil.isEmpty(name)) {
			errors.add(new JSONError("viewBean.name", resources.getString("ad.erreur.name.obligatoire")));
		}

		if (!StringUtil.isEmpty(fullscreen)) {
			if (StringUtil.isEmpty(fileFileName)) {
				errors.add(new JSONError("viewBean.file", resources.getString("ad.erreur.file.obligatoire")));
			}
			else {
				if (MyWayUtil.isImage(fileFileName)) {
					if (StringUtil.isEmpty(skipAfterNbSec)) {
						errors.add(new JSONError("viewBean.skipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.obligatoire")));
					}
				}
				else if (MyWayUtil.isFlash(fileFileName) || MyWayUtil.isZip(fileFileName)) {
					if (StringUtil.isEmpty(width)) {
						errors.add(new JSONError("viewBean.width", resources.getString("ad.erreur.width.obligatoire")));
					}
					if (StringUtil.isEmpty(height)) {
						errors.add(new JSONError("viewBean.height", resources.getString("ad.erreur.height.obligatoire")));
					}
					if (StringUtil.isEmpty(skipAfterNbSec) && skipAfterAsEvent == null) {
						errors.add(new JSONError("viewBean.skipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.asEvent.obligatoire")));
					}
				}	
				else if (MyWayUtil.isVideo(fileFileName)) {
					if (StringUtil.isEmpty(skipAfterNbSec) && skipAfterComplete == null) {
						errors.add(new JSONError("viewBean.skipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.asEvent.obligatoire")));
					}
				}
			}

			if (StringUtil.isEmpty(linkTo)) {
				errors.add(new JSONError("viewBean.linkTo", resources.getString("ad.erreur.linkTo.obligatoire")));
			}
			else {
				int linkToInt = NumberUtil.toInteger(linkTo);
				switch (linkToInt) {
				case 1:
					if (StringUtil.isEmpty(url)) {
						errors.add(new JSONError("viewBean.url", resources.getString("ad.erreur.url.obligatoire")));
					}
					break;
				case 2:
					if (itemId == null) {
						errors.add(new JSONError("viewBean.itemId", resources.getString("ad.erreur.item.obligatoire")));
					}
					break;
				case 3:
					if (eventId == null) {
						errors.add(new JSONError("viewBean.eventId", resources.getString("ad.erreur.event.obligatoire")));
					}
					break;
				case 4:
					if (promotionId == null) {
						errors.add(new JSONError("viewBean.promotionId", resources.getString("ad.erreur.promotion.obligatoire")));
					}
					break;
				default:
					break;
				}
			}
		}

		if (!StringUtil.isEmpty(banner)) {
			if (StringUtil.isEmpty(bannerFileFileName)) {
				errors.add(new JSONError("viewBean.bannerFile", resources.getString("ad.erreur.file.obligatoire")));
			}
			else {
				if (MyWayUtil.isImage(bannerFileFileName)) {
					if (StringUtil.isEmpty(bannerSkipAfterNbSec)) {
						errors.add(new JSONError("viewBean.bannerSkipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.obligatoire")));
					}
				}
				else if (MyWayUtil.isFlash(bannerFileFileName) || MyWayUtil.isZip(bannerFileFileName)) {
					if (StringUtil.isEmpty(bannerWidth)) {
						errors.add(new JSONError("viewBean.bannerWidth", resources.getString("ad.erreur.width.obligatoire")));
					}
					if (StringUtil.isEmpty(bannerHeight)) {
						errors.add(new JSONError("viewBean.bannerHeight", resources.getString("ad.erreur.height.obligatoire")));
					}
					if (StringUtil.isEmpty(bannerSkipAfterNbSec) && bannerSkipAfterAsEvent == null) {
						errors.add(new JSONError("viewBean.bannerSkipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.asEvent.obligatoire")));
					}
				}	
				else if (MyWayUtil.isVideo(bannerFileFileName)) {
					if (StringUtil.isEmpty(bannerSkipAfterNbSec) && bannerSkipAfterComplete == null) {
						errors.add(new JSONError("viewBean.bannerSkipAfterNbSec", resources.getString("ad.erreur.skipAfterNbSec.asEvent.obligatoire")));
					}
				}
			}

			if (StringUtil.isEmpty(bannerLinkTo)) {
				errors.add(new JSONError("viewBean.bannerLinkTo", resources.getString("ad.erreur.linkTo.obligatoire")));
			}
			else {
				int linkToInt = NumberUtil.toInteger(bannerLinkTo);
				switch (linkToInt) {
				case 1:
					if (StringUtil.isEmpty(bannerUrl)) {
						errors.add(new JSONError("viewBean.bannerUrl", resources.getString("ad.erreur.url.obligatoire")));
					}
					break;
				case 2:
					if (bannerItemId == null) {
						errors.add(new JSONError("viewBean.bannerItemId", resources.getString("ad.erreur.item.obligatoire")));
					}
					break;
				case 3:
					if (bannerEventId == null) {
						errors.add(new JSONError("viewBean.bannerEventId", resources.getString("ad.erreur.event.obligatoire")));
					}
					break;
				case 4:
					if (bannerPromotionId == null) {
						errors.add(new JSONError("viewBean.bannerPromotionId", resources.getString("ad.erreur.promotion.obligatoire")));
					}
					break;
				default:
					break;
				}
			}
		}
		
		return errors;
	}
	
}
