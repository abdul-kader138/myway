package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Playlist;

public class PlaylistDAO extends BaseDAO<Playlist> {

	public PlaylistDAO() {
		super();
	}
	
	public List<Playlist> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.playlist.by.project", new Object[]{projectId});
	}
}
