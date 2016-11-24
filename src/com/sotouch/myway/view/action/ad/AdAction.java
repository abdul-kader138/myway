package com.sotouch.myway.view.action.ad;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONUtil;
import org.hurryapp.quickstart.service.config.ConfigParameterService;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Ad;
import com.sotouch.myway.data.model.Event;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Playlist;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.data.model.Promotion;
import com.sotouch.myway.service.event.EventService;
import com.sotouch.myway.service.item.ItemService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.promotion.PromotionService;
import com.sotouch.myway.service.util.MyWayUtil;

/**
 * Class which manages the ads
 */
public class AdAction extends BaseCrudAction<AdViewBean> {

	@Resource (name="itemService")
	private ItemService itemService;

	@Resource (name="eventService")
	private EventService eventService;
	
	@Resource (name="promotionService")
	private PromotionService promotionService;
	
	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	@Resource(name="languageService")
	private LanguageService languageService;
	
	@Resource(name="licenseService")
	private LicenseService licenseService;
	
	@RemoteProperty(jsonAdapter="properties:id,name")
	private List items;
	
	@RemoteProperty(jsonAdapter="properties:id,name")
	private List events;
	
	@RemoteProperty(jsonAdapter="properties:id,name")
	private List promotions;
	
	@RemoteProperty(jsonAdapter="properties:id,key")
	private List<License> licenses;
	
	@Override
	protected void doInit() throws Exception {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		items = itemService.findByProject(projectId);
		events = eventService.findByProject(projectId);
		promotions = promotionService.findByProject(projectId);
		
		this.licenses = licenseService.findByProject(projectId);
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		AdViewBean adViewBean = (AdViewBean) viewBean;
		criteriaMap.put("name", adViewBean.getName());
		criteriaMap.put("playlist.id", adViewBean.getPlaylistId());
	}

	@Override
	protected String getDefaultSort() {
		return "index";
	}

	@Override
	public String save() throws Exception {
		try {
			super.execute();
			
			if (jsonResponse.getErrors().size() < 1) {
				String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+session.get(Constants.SESSION_KEY_PROJECT_KEY);
				// Fullscreen media upload
				AdViewBean adViewBean = (AdViewBean) viewBean;
				if (adViewBean.getFile() != null) {
					String dir = uploadPath+"/"+Constants.DIR_ADS+"/"+adViewBean.getId()+"/"+Constants.DIR_ADS_FULLSCREEN;
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+adViewBean.getFileFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(adViewBean.getFile()), uploadedFile);

					// Resize image
					Ad ad = (Ad) entityService.findById(adViewBean.getId());
					if (MyWayUtil.isImage(adViewBean.getFileFileName())) {
						filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_AD_WIDTH, Constants.IMAGE_SIZE_AD_HEIGHT, null);
						String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
						ad.setFile(newFileName);
					}
					else {
						ad.setFile(adViewBean.getFileFileName());
					}
					entityService.saveOrUpdate(ad);
				}

				// Fullscreen media upload
				if (adViewBean.getBannerFile() != null) {
					String dir = uploadPath+"/"+Constants.DIR_ADS+"/"+adViewBean.getId()+"/"+Constants.DIR_ADS_BANNER;
					FileUtils.deleteDirectory(new File(dir));
					String filePath = dir+"/"+adViewBean.getBannerFileFileName();
					File uploadedFile = new File(filePath);
					FileUtils.copyFile(new File(adViewBean.getBannerFile()), uploadedFile);

					// Resize image
					Ad ad = (Ad) entityService.findById(adViewBean.getId());
					if (MyWayUtil.isImage(adViewBean.getFileFileName())) {
						filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_AD_WIDTH, Constants.IMAGE_SIZE_AD_HEIGHT, null);
						String newFileName = filePath.substring(filePath.lastIndexOf('/')+1);
						ad.setBannerFile(newFileName);
					}
					else {
						ad.setBannerFile(adViewBean.getBannerFileFileName());
					}
					entityService.saveOrUpdate(ad);
				}
			}
		}
		catch (Exception e) {
			this.checkSaveException(e);
		}
		return SUCCESS_JSON;
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		AdViewBean adViewBean = (AdViewBean) viewBean;
		Ad ad = (Ad) dataBean;
		ad.setName(adViewBean.getName());
		ad.setIndex(adViewBean.getIndex());
		if (adViewBean.getPlaylistId() != null) {
			ad.setPlaylist(new Playlist(Integer.valueOf(adViewBean.getPlaylistId())));
		}

		ad.setFullscreen(StringUtil.isEmpty(adViewBean.getFullscreen()) ? Boolean.FALSE : Boolean.TRUE);
		ad.setSkipAfterNbSec(NumberUtil.toInteger(adViewBean.getSkipAfterNbSec()));
		ad.setSkipAfterAsEvent(adViewBean.getSkipAfterAsEvent());
		ad.setSkipAfterComplete(adViewBean.getSkipAfterComplete());
		ad.setWidth(NumberUtil.toInteger(adViewBean.getWidth()));
		ad.setHeight(NumberUtil.toInteger(adViewBean.getHeight()));
		ad.setLinkTo(NumberUtil.toInteger(adViewBean.getLinkTo())); if (ad.getLinkTo() == null) ad.setLinkTo(-1);
		ad.setUrl(adViewBean.getUrl());
		if (adViewBean.getFileFileName() != null && adViewBean.getFile() != null) {
			if (MyWayUtil.isImage(adViewBean.getFileFileName()) && ImageResizer.getWeight(adViewBean.getFile()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
				jsonResponse.addError(new JSONError("viewBean.file", getText("common.error.image.maxSize")));
			}
			else {
				ad.setFile(adViewBean.getFileFileName());
			}
		}
		if (adViewBean.getItemId() != null) {
			ad.setItem(new Item(Integer.valueOf(adViewBean.getItemId())));
		}
		if (adViewBean.getEventId() != null) {
			ad.setEvent(new Event(Integer.valueOf(adViewBean.getEventId())));
		}
		if (adViewBean.getPromotionId() != null) {
			ad.setPromotion(new Promotion(Integer.valueOf(adViewBean.getPromotionId())));
		}

		ad.setBanner(StringUtil.isEmpty(adViewBean.getBanner()) ? Boolean.FALSE : Boolean.TRUE);
		ad.setBannerSkipAfterNbSec(NumberUtil.toInteger(adViewBean.getBannerSkipAfterNbSec()));
		ad.setBannerSkipAfterAsEvent(adViewBean.getBannerSkipAfterAsEvent());
		ad.setBannerSkipAfterComplete(adViewBean.getBannerSkipAfterComplete());
		ad.setBannerWidth(NumberUtil.toInteger(adViewBean.getBannerWidth()));
		ad.setBannerHeight(NumberUtil.toInteger(adViewBean.getBannerHeight()));
		ad.setBannerLinkTo(NumberUtil.toInteger(adViewBean.getBannerLinkTo())); if (ad.getBannerLinkTo() == null) ad.setBannerLinkTo(-1);
		ad.setBannerUrl(adViewBean.getBannerUrl());
		if (adViewBean.getBannerFileFileName() != null && adViewBean.getBannerFile() != null) {
			if (MyWayUtil.isImage(adViewBean.getBannerFileFileName()) && ImageResizer.getWeight(adViewBean.getBannerFile()) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
				jsonResponse.addError(new JSONError("viewBean.bannerFile", getText("common.error.image.maxSize")));
			}
			else {
				ad.setBannerFile(adViewBean.getBannerFileFileName());
			}
		}
		if (adViewBean.getBannerItemId() != null) {
			ad.setBannerItem(new Item(Integer.valueOf(adViewBean.getBannerItemId())));
		}
		if (adViewBean.getBannerEventId() != null) {
			ad.setBannerEvent(new Event(Integer.valueOf(adViewBean.getBannerEventId())));
		}
		if (adViewBean.getBannerPromotionId() != null) {
			ad.setBannerPromotion(new Promotion(Integer.valueOf(adViewBean.getBannerPromotionId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Ad ad = (Ad) dataBean;
		AdViewBean adViewBean = (AdViewBean) viewBean;
		adViewBean.setName(ad.getName());
		adViewBean.setIndex(ad.getIndex());
		adViewBean.setPlaylistId(ad.getPlaylist() != null ? ad.getPlaylist().getId() : null);
		adViewBean.setLinkTo("-1");
		adViewBean.setBannerLinkTo("-1");

		//---------------------------------------------------------------------
		// Full screen ad
		//---------------------------------------------------------------------
		if (ad.getFullscreen() != null & ad.getFullscreen()) {
			adViewBean.setFullscreen(ad.getFullscreen() ? "on" : "");
			adViewBean.setFile(ad.getFile());
			adViewBean.setFileFileName(ad.getFile());
			adViewBean.setSkipAfterNbSec(NumberUtil.toString(ad.getSkipAfterNbSec()));
			adViewBean.setSkipAfterAsEvent(ad.getSkipAfterAsEvent());
			adViewBean.setSkipAfterComplete(ad.getSkipAfterComplete());
			adViewBean.setWidth(NumberUtil.toString(ad.getWidth()));
			adViewBean.setHeight(NumberUtil.toString(ad.getHeight()));
			adViewBean.setLinkTo(NumberUtil.toString(ad.getLinkTo()));
			adViewBean.setItem(ad.getItem() != null ? ad.getItem().getName() : "");
			adViewBean.setItemId(ad.getItem() != null ? ad.getItem().getId() : null);
			adViewBean.setEvent(ad.getEvent() != null ? ad.getEvent().getName() : "");
			adViewBean.setEventId(ad.getEvent() != null ? ad.getEvent().getId() : null);
			adViewBean.setPromotion(ad.getPromotion() != null ? ad.getPromotion().getName() : "");
			adViewBean.setPromotionId(ad.getPromotion() != null ? ad.getPromotion().getId() : null);
			adViewBean.setUrl(ad.getUrl());

			if (ad.getLinkTo() != null) {
				switch (ad.getLinkTo()) {
				case 1:
					adViewBean.setLinkedTo(StringUtil.truncate(ad.getUrl(), 25));
					break;
				case 2:
					adViewBean.setLinkedTo(StringUtil.truncate(ad.getItem().getName(), 25));
					break;
				case 3:
					adViewBean.setLinkedTo(StringUtil.truncate(ad.getEvent().getName(), 25));
					break;
				case 4:
					adViewBean.setLinkedTo(StringUtil.truncate(ad.getPromotion().getName(), 25));
					break;
				default:
					break;
				}
			}

			if (!StringUtil.isEmpty(ad.getFile())) {
				String fileExtension = ad.getFile().substring(ad.getFile().lastIndexOf('.')+1).toUpperCase();
				if ("MOV".equals(fileExtension) || "FLV".equals(fileExtension) || "MP4".equals(fileExtension)) {
					adViewBean.setSkipAfter(ad.getSkipAfterComplete() != null && ad.getSkipAfterComplete() ? getText("ad.skipAfter.complete") : getText("ad.skipAfter.nbSec.1")+" "+ ad.getSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setType("film");
				}
				else if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
					adViewBean.setSkipAfter(ad.getSkipAfterAsEvent() != null && ad.getSkipAfterAsEvent() ? getText("ad.skipAfter.asEvent") : getText("ad.skipAfter.nbSec.1")+" "+ ad.getSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setType("swf");
				}
				else {
					adViewBean.setSkipAfter(getText("ad.skipAfter.nbSec.1")+" "+ad.getSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setType("image");
				}
			}
		}

		//---------------------------------------------------------------------
		// Banner ad
		//---------------------------------------------------------------------
		if (ad.getBanner() != null & ad.getBanner()) {
			adViewBean.setBanner(ad.getBanner() ? "on" : "");
			adViewBean.setBannerFile(ad.getBannerFile());
			adViewBean.setBannerFileFileName(ad.getBannerFile());
			adViewBean.setBannerSkipAfterNbSec(NumberUtil.toString(ad.getBannerSkipAfterNbSec()));
			adViewBean.setBannerSkipAfterAsEvent(ad.getBannerSkipAfterAsEvent());
			adViewBean.setBannerSkipAfterComplete(ad.getBannerSkipAfterComplete());
			adViewBean.setBannerWidth(NumberUtil.toString(ad.getBannerWidth()));
			adViewBean.setBannerHeight(NumberUtil.toString(ad.getBannerHeight()));
			adViewBean.setBannerLinkTo(NumberUtil.toString(ad.getBannerLinkTo()));
			adViewBean.setBannerItem(ad.getBannerItem() != null ? ad.getBannerItem().getName() : "");
			adViewBean.setBannerItemId(ad.getBannerItem() != null ? ad.getBannerItem().getId() : null);
			adViewBean.setBannerEvent(ad.getBannerEvent() != null ? ad.getBannerEvent().getName() : "");
			adViewBean.setBannerEventId(ad.getBannerEvent() != null ? ad.getBannerEvent().getId() : null);
			adViewBean.setBannerPromotion(ad.getBannerPromotion() != null ? ad.getBannerPromotion().getName() : "");
			adViewBean.setBannerPromotionId(ad.getBannerPromotion() != null ? ad.getBannerPromotion().getId() : null);
			adViewBean.setBannerUrl(ad.getBannerUrl());
	
			if (ad.getBannerLinkTo() != null) {
				switch (ad.getBannerLinkTo()) {
				case 1:
					adViewBean.setBannerLinkedTo(StringUtil.truncate(ad.getBannerUrl(), 25));
					break;
				case 2:
					adViewBean.setBannerLinkedTo(StringUtil.truncate(ad.getBannerItem().getName(), 25));
					break;
				case 3:
					adViewBean.setBannerLinkedTo(StringUtil.truncate(ad.getBannerEvent().getName(), 25));
					break;
				case 4:
					adViewBean.setBannerLinkedTo(StringUtil.truncate(ad.getBannerPromotion().getName(), 25));
					break;
				default:
					break;
				}
			}
	
			if (!StringUtil.isEmpty(ad.getBannerFile())) {
				String fileExtension = ad.getBannerFile().substring(ad.getBannerFile().lastIndexOf('.')+1).toUpperCase();
				if ("MOV".equals(fileExtension) || "FLV".equals(fileExtension) || "MP4".equals(fileExtension)) {
					adViewBean.setBannerSkipAfter(ad.getBannerSkipAfterComplete() != null && ad.getBannerSkipAfterComplete() ? getText("ad.skipAfter.complete") : getText("ad.skipAfter.nbSec.1")+" "+ ad.getBannerSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setBannerType("film");
				}
				else if ("SWF".equals(fileExtension) || "ZIP".equals(fileExtension)) {
					adViewBean.setBannerSkipAfter(ad.getBannerSkipAfterAsEvent() != null && ad.getBannerSkipAfterAsEvent() ? getText("ad.skipAfter.asEvent") : getText("ad.skipAfter.nbSec.1")+" "+ ad.getBannerSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setBannerType("swf");
				}
				else {
					adViewBean.setBannerSkipAfter(getText("ad.skipAfter.nbSec.1")+" "+ad.getBannerSkipAfterNbSec()+" "+getText("ad.skipAfter.nbSec.2"));
					adViewBean.setBannerType("image");
				}
			}
		}
	}
	
	
	//=========================================================================
	// SPECIFIC METHODS 
	//=========================================================================
	/**
	 * Update indexes
	 */
	public String updateIndexes() throws Exception {
		if (!StringUtil.isEmpty(this.getSelectedObjects())) {
			String[] jsonObjects = this.getSelectedObjects().split(";");
			for (String jsonObject : jsonObjects) {
				// Coversion JSON --> viewBean
				viewBean = (BaseViewBean) JSONUtil.toObject(jsonObject, viewBeanClass);
				// Indexe update
				Ad ad = (Ad) entityService.findById(viewBean.getId());
				ad.setIndex(((AdViewBean) viewBean).getIndex());
				entityService.saveOrUpdate(ad);
			}
		}
		return SUCCESS_JSON;
	}

	public String getBuyUrl() {
		Project project = getProject();
		if (project.getAdsDateExpire() != null && project.getAdsDateExpire().after(new Date())) {
			return null;
		}
		else {
			String buyAdsFeatureUrl = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_BUY_ADS_FEATURE_URL);
			return buyAdsFeatureUrl;
		}
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================

	public void setItems(List items) {
		this.items = items;
	}
	public List getItems() {
		return items;
	}
	
	public void setEvents(List events) {
		this.events = events;
	}
	public List getEvents() {
		return events;
	}

	public void setPromotions(List promotions) {
		this.promotions = promotions;
	}
	public List getPromotions() {
		return promotions;
	}
	
	
	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}
	
	public List<License> getLicenses() {
		Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
		for(License license : licenses) {
			if(license.getLicenseContents().size() == 0) continue;
			
			for(LicenseContent licenseContent : license.getLicenseContents()) {
				if(licenseContent.getLanguage().getCode().equals(defaultLanguage.getCode())) {
					license.setKey(licenseContent.getName());
				}
			}
		}
		return licenses;
	}
}
