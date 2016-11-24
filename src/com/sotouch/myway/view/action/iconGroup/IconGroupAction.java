package com.sotouch.myway.view.action.iconGroup;

import java.util.Map;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.data.model.IconGroup;
import com.sotouch.myway.data.model.Project;

/**
 * Class which manages the icon groups
 */
public class IconGroupAction extends BaseCrudAction<IconGroupViewBean> {

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		IconGroupViewBean iconGroupViewBean = (IconGroupViewBean) viewBean;
		criteriaMap.put("name", iconGroupViewBean.getName());
		criteriaMap.put("project.id", iconGroupViewBean.getProjectId());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		IconGroupViewBean iconGroupViewBean = (IconGroupViewBean) viewBean;
		IconGroup iconGroup = (IconGroup) dataBean;
		iconGroup.setName(iconGroupViewBean.getName());
		iconGroup.setIcon(iconGroupViewBean.getIcon());
		iconGroup.setType(iconGroupViewBean.getType());
		if (iconGroupViewBean.getProjectId() != null) {
			iconGroup.setProject(new Project(Integer.valueOf(iconGroupViewBean.getProjectId())));
		}
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		IconGroup iconGroup = (IconGroup) dataBean;
		IconGroupViewBean iconGroupViewBean = (IconGroupViewBean) viewBean;
		iconGroupViewBean.setName(iconGroup.getName());
		iconGroupViewBean.setIcon(iconGroup.getIcon());
		iconGroupViewBean.setType(iconGroup.getType());
		iconGroupViewBean.setProjectId(iconGroup.getProject() != null ? iconGroup.getProject().getId() : null);
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("iconGroup.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// If the group already exists
		if (e instanceof DataIntegrityViolationException) {
			// Error message
			jsonResponse.addError(new JSONError("viewBean.name", resources.getString("iconGroup.erreur.already.exists")));
		}
		else {
			throw e;
		}
	}
	
	//=========================================================================
	// METHODES SPECIFIQUES
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================
}
