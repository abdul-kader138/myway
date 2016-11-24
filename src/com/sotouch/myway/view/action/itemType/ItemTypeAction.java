package com.sotouch.myway.view.action.itemType;

import java.util.Map;

import org.hurryapp.fwk.view.action.BaseCrudAction;
import org.hurryapp.fwk.view.action.BaseViewBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.springframework.dao.DataIntegrityViolationException;

import com.sotouch.myway.data.model.ItemType;

/**
 * Class which manages the item types
 */
public class ItemTypeAction extends BaseCrudAction<ItemTypeViewBean> {

	@Override
	protected void doInit() throws Exception {
	}
	
	@Override
	protected void populateSearchCriteria(Map<String, Object> criteriaMap) {
		ItemTypeViewBean itemTypeViewBean = (ItemTypeViewBean) viewBean;
		criteriaMap.put("name", itemTypeViewBean.getName());
	}

	@Override
	protected String getDefaultSort() {
		return "name";
	}

	@Override
	protected void populateDataBean(Object dataBean, BaseViewBean viewBean) throws Exception {
		ItemTypeViewBean itemTypeViewBean = (ItemTypeViewBean) viewBean;
		ItemType itemType = (ItemType) dataBean;
		itemType.setName(itemTypeViewBean.getName());
		itemType.setIcon(itemTypeViewBean.getIcon());
	}

	@Override
	protected void populateViewBean(BaseViewBean viewBean, Object dataBean) throws Exception {
		ItemType itemType = (ItemType) dataBean;
		ItemTypeViewBean itemTypeViewBean = (ItemTypeViewBean) viewBean;
		itemTypeViewBean.setName(itemType.getName());
		itemTypeViewBean.setIcon(itemType.getIcon());
	}
	
	@Override
	protected void checkDeleteException(Exception e) throws Exception {
		if (e instanceof DataIntegrityViolationException) {
			jsonResponse.addError(new JSONError(null, resources.getString("itemType.erreur.delete")));
		}
		else {
			throw e;
		}
	}
	
	@Override
	protected void checkSaveException(Exception e) throws Exception {
		// If the type already exists
		if (e instanceof DataIntegrityViolationException) {
			// Error message
			jsonResponse.addError(new JSONError("viewBean.name", resources.getString("itemType.erreur.already.exists")));
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

}
