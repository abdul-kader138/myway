package com.sotouch.myway.service.promotion;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.PromotionDAO;
import com.sotouch.myway.data.model.Promotion;


public class PromotionService extends BaseCrudService {

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	public PromotionService() {
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Promotion promotion = (Promotion) entityDao.findById(id);
		super.deleteById(id);
		
		// Suppression du répertoire
		String eventDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+promotion.getProject().getKey()+"/"+Constants.DIR_PROMOTIONS+"/"+promotion.getId();
		FileUtils.deleteDirectory(new File(eventDir));
	}

	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<Promotion> findByProject(Integer projectId) {
		return ((PromotionDAO) entityDao).findByProject(projectId);
	}
	
	public Long countByProject(Integer projectId) {
		return ((PromotionDAO) entityDao).countByProject(projectId);
	}
}
