package com.sotouch.myway.service.building;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Floor;


public class FloorService extends BaseCrudService {

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	public FloorService() {
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Floor floor = (Floor) entityDao.findById(id);
		super.deleteById(id);
		
		// Suppression du répertoire
		String floorDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+floor.getBuilding().getProject().getKey()+"/"+Constants.DIR_FLOORS+"/"+floor.getId();
		FileUtils.deleteDirectory(new File(floorDir));
	}

	//=========================================================================
	// METHODES METIER
	//=========================================================================

}
