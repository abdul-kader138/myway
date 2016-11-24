package com.sotouch.myway.service.ad;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.AdDAO;
import com.sotouch.myway.data.model.Ad;


public class AdService extends BaseCrudService {

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	public AdService() {
	}
	
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Ad ad = (Ad) entityDao.findById(id);
		super.deleteById(id);
		
		// Suppression du r�pertoire
		String itemDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+ad.getPlaylist().getProject().getKey()+"/"+Constants.DIR_ADS+"/"+ad.getId();
		FileUtils.deleteDirectory(new File(itemDir));
	}
	
	public List<Ad> findByPlaylist(Integer playlistId) {
		return ((AdDAO)this.entityDao).findByPlaylist(playlistId);
	}

	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
}
