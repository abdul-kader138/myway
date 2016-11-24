package com.sotouch.myway.view.action.ad;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.util.NumberUtil;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Ad;
import com.sotouch.myway.data.model.Language;
import com.sotouch.myway.data.model.License;
import com.sotouch.myway.data.model.LicenseContent;
import com.sotouch.myway.data.model.Playlist;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.ad.AdService;
import com.sotouch.myway.service.language.LanguageService;
import com.sotouch.myway.service.license.LicenseService;
import com.sotouch.myway.service.translation.TranslationService;
import com.sotouch.myway.view.action.item.ItemJSONAdapter;

/**
 * Class which manages the ad playlists
 */
public class PlaylistAction extends BaseCrudAction<PlaylistViewBean> {

	@Resource(name="languageService")
	private LanguageService languageService;
	
	@Resource(name="adService")
	private AdService adService;
	
	@Override
	protected void doInit() throws Exception {
		
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		PlaylistViewBean playlistViewBean = (PlaylistViewBean) viewBean;
		criteriaMap.put("name", playlistViewBean.getName());
		criteriaMap.put("project.id", playlistViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "index";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		PlaylistViewBean playlistViewBean = (PlaylistViewBean) viewBean;
		Playlist playlist = (Playlist) dataBean;
		playlist.setName(playlistViewBean.getName());
		playlist.setActive(playlistViewBean.getActive());
		playlist.setIndex(NumberUtil.toInteger(playlistViewBean.getIndex()));
		if (playlistViewBean.getProjectId() != null) {
			playlist.setProject(new Project(Integer.valueOf(playlistViewBean.getProjectId())));
		}
		
		if (playlistViewBean.getLicenseId() != null) {
			playlist.setLicense(new License(Integer.valueOf(playlistViewBean.getLicenseId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Playlist playlist = (Playlist) dataBean;
		PlaylistViewBean playlistViewBean = (PlaylistViewBean) viewBean;
		playlistViewBean.setName(playlist.getName());
		playlistViewBean.setActive(playlist.getActive());
		playlistViewBean.setIndex(NumberUtil.toString(playlist.getIndex()));
		playlistViewBean.setProjectId(playlist.getProject() != null ? playlist.getProject().getId() : null);
		
		if(playlist.getLicense() != null && playlist.getLicense().getLicenseContents().size() > 0) {
			Language defaultLanguage = this.languageService.findDefaultByProject((Integer)this.session.get("SESSION_PROJECT_ID"));
			for(LicenseContent licenseContent : playlist.getLicense().getLicenseContents()) {
				if(!licenseContent.getLanguage().getCode().equals(defaultLanguage.getCode())) continue;
				
				playlistViewBean.setLicense(licenseContent.getName());
			}
		}
		else {
			playlistViewBean.setLicense(playlist.getLicense() != null ? playlist.getLicense().getKey() : "");
		}
		
		playlistViewBean.setLicenseId(playlist.getLicense() != null ? playlist.getLicense().getId() : null);
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("playlist.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================
	
	public String copy() throws Exception {
		Integer id = this.viewBean.getId();
		Playlist playlist = null;
		if(id == null || (playlist = (Playlist)this.entityService.findById(id)) == null) {
			jsonResponse.addError(new JSONError(null, "playlist does not exists:" + id));
		}
		else {
			playlist.setId(null);
			playlist.setName("Copy Of " + playlist.getName());
			playlist = (Playlist)this.entityService.saveOrUpdate(playlist);
			
			List<Ad> ads = adService.findByPlaylist(id);
			if(ads != null && ads.size() > 0) {
				for(Ad ad : ads) {
					ad.setId(null);
					ad.setPlaylist(new Playlist(playlist.getId()));
					adService.saveOrUpdate(ad);
				}
			}
			
			this.jsonResponse = new JSONResponse(toViewBean(playlist));
		}
		return SUCCESS_JSON;
	}
	

	//=========================================================================
	// ACCESSORS
	//=========================================================================
}
