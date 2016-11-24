package com.sotouch.myway.service.zone;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.CategoryDAO;
import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.dao.ZoneDAO;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Zone;


public class ZoneService extends BaseCrudService {

	@Resource (name="itemDao")
	private ItemDAO itemDao;

	public ZoneService() {
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		// Suppression des sous-categories
		List<Item> items = itemDao.findByZone(id);
		for (Item item : items) {
			item.setZone(null);
			itemDao.saveOrUpdate(item);
		}
		entityDao.getHibernateTemplate().flush();
		
		// Suppression de la cat�gorie
		super.deleteById(id);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	public List<Zone> findByProject(Integer projectId) {
		return ((ZoneDAO) entityDao).findByProject(projectId);
	}
}
