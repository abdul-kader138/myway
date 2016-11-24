package com.sotouch.myway.service.event;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.dao.EventDAO;
import com.sotouch.myway.data.model.Event;


public class EventService extends BaseCrudService {

	@Resource (name="configParameterDao")
	private ConfigParameterDAO configParameterDao;

	public EventService() {
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Event event = (Event) entityDao.findById(id);
		super.deleteById(id);
		
		// Suppression du répertoire
		String eventDir = (String) configParameterDao.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH)+"/"+Constants.DIR_PROJECTS+"/"+event.getProject().getKey()+"/"+Constants.DIR_EVENTS+"/"+event.getId();
		FileUtils.deleteDirectory(new File(eventDir));
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<Event> findByProject(Integer projectId) {
		return ((EventDAO) entityDao).findByProject(projectId);
	}
	
	public Long countByProject(Integer projectId) {
		return ((EventDAO) entityDao).countByProject(projectId);
	}
}
