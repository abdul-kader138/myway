package com.sotouch.myway.service.ad;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;
import com.sotouch.myway.data.dao.AdDAO;
import com.sotouch.myway.data.model.Ad;
import com.sotouch.myway.data.model.Playlist;


public class PlaylistService extends BaseCrudService {

	@Resource (name="adDao")
	private AdDAO adDao;
	
	public PlaylistService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	
	@Override
	public void deleteById(Integer id) throws Exception {
		List<Ad> ads = adDao.findByPlaylist(id);
		for(Ad ad : ads) {
			adDao.delete(ad);
		}
		
		Playlist playlist = (Playlist) entityDao.findById(id);
		super.delete(playlist);
	}
	
}
