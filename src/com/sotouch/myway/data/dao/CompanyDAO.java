package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.Company;

public class CompanyDAO extends BaseDAO<Company> {

	public CompanyDAO() {
		super();
	}

	@Override
	public List<Company> findAll() {
		return super.findByNamedQuery("select.company.enabled");
	}
	
	public Company findByIdStr(String idStr) {
		return (Company) super.findUniqueResultByNamedQuery("select.company.by.idStr", new Object[]{idStr});
	}
}
