package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Ad;

public class AdDAO extends BaseDAO<Ad> {

	public AdDAO() {
		super();
	}
	
	public List<Ad> findByPlaylist(Integer playlistId) {
		return super.findByNamedQuery("select.ad.by.playlist", new Object[]{playlistId});
	}
}
