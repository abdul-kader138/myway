package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.ItemType;

public class ItemTypeDAO extends BaseDAO<ItemType> {

	public ItemTypeDAO() {
		super();
	}
	
	@Override
	public List<ItemType> findAll() {
		return super.findByNamedQuery("select.itemType.except.terminal", new Object[]{Constants.ITEM_TYPE_TERMINAL});
	}
	
	public List<ItemType> findAllWithTerminal() {
		return super.findAll();
	}

	public ItemType findByName(String name) {
		return (ItemType) super.findUniqueResultByNamedQuery("select.itemType.by.name", new Object[]{name});
	}
	
}
