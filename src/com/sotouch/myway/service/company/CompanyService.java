package com.sotouch.myway.service.company;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.fwk.util.StringUtil;

import com.sotouch.myway.data.dao.CompanyDAO;
import com.sotouch.myway.data.model.Company;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.project.ProjectService;


public class CompanyService extends BaseCrudService {

	@Resource (name="projectService")
	private ProjectService projectService;

	public CompanyService() {
	}
	
	@Override
	public Object saveOrUpdate(Object entity) throws Exception {
		Company company = (Company) entity;
		if (StringUtil.isEmpty(company.getIdStr())) {
			company.setIdStr(getRandomIdStr(company.getName()));
		}
		return super.saveOrUpdate(company);
	}

	@Override
	public void delete(Object entity) throws Exception {
		Company company = (Company) entity;

		// Suppresion de la company ==> flag enabled = false
		//super.deleteById(id);
		company.setEnabled(false);
		entityDao.saveOrUpdate(company);
		
		// Désactivation des projets
		List<Project> projects = projectService.findByCompany(company.getId());
		for (Project project : projects) {
			projectService.delete(project);
		}
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		Company company = (Company) entityDao.findById(id);
		this.delete(company);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public Company findByIdStr(String idStr) {
		return ((CompanyDAO) entityDao).findByIdStr(idStr);
	}	
	
	public String getRandomIdStr(String name) {
		name = name.replaceAll(" ", "");
		name = name.length() >= 5 ? name.substring(0, 5) : name;
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String randomStr = "";
		for (int i = 0; i < 5; i++) {
			int character = (int)(Math.random()*36);
			randomStr += alphabet.substring(character, character+1);
		}
		return name.toUpperCase() + "-" + randomStr;
	}
}
