package com.sotouch.myway.view.action.company;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hurryapp.fwk.annotation.RemoteProperty;
import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.groupe.GroupeService;
import org.hurryapp.quickstart.view.AccessUtil;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Company;

/**
 * Class which manages the companies
 */
public class CompanyAction extends BaseCrudAction<CompanyViewBean> {

	@Resource (name="groupeService")
	protected GroupeService groupeService;

	@RemoteProperty(jsonAdapter="properties:id,libelle")
	private List groupes;

	@Override
	public void doInit() throws Exception {
		groupes = groupeService.findAll();
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) throws Exception {
		CompanyViewBean companyViewBean = (CompanyViewBean) viewBean;
		criteriaMap.put("name", companyViewBean.getName());
		criteriaMap.put("enabled", Boolean.TRUE);

		Utilisateur user = AccessUtil.getUtilisateur(session);
		if (AccessUtil.canAccessGroup(user, Constants.GROUPE_SUPER_ADMIN)) {
		}
		else if (AccessUtil.canAccessGroup(user, Constants.GROUPE_ADMIN)) {
			criteriaMap.put("id", user.getCompany().getId());
		}
		else {
			criteriaMap.put("id", -1);
		}
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		CompanyViewBean companyViewBean = (CompanyViewBean) viewBean;
		Company company = (Company) dataBean;
		company.setName(companyViewBean.getName());
		company.setAddress(companyViewBean.getAddress());
		company.setCity(companyViewBean.getCity());
		company.setCountry(companyViewBean.getCountry());
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		Company company = (Company) dataBean;
		CompanyViewBean companyViewBean = (CompanyViewBean) viewBean;
		companyViewBean.setIdStr(company.getIdStr());
		companyViewBean.setName(company.getName());
		companyViewBean.setAddress(company.getAddress());
		companyViewBean.setCity(company.getCity());
		companyViewBean.setCountry(company.getCountry());
	}

	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("company.erreur.delete")));
		}
		else {
			throw e;
		}
	}

	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public List getGroupes() {
		return groupes;
	}

	public void setGroupes(List groupes) {
		this.groupes = groupes;
	}
}
