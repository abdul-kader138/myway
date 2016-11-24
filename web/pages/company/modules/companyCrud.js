CompanyCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: COMPANY_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: COMPANY_IDSTR,
			dataIndex: 'idStr',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var createFields = [
		{
			xtype: 'textfield',
			fieldLabel: COMPANY_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_ADDRESS,
			allowBlank: true,
			id: 'address',
			name: 'viewBean.address'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_CITY,
			allowBlank: true,
			id: 'city',
			name: 'viewBean.city'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_COUNTRY,
			allowBlank: true,
			id: 'country',
			name: 'viewBean.country'
		}
	];
	
	var editFields = [
		{
			xtype: 'textfield',
			fieldLabel: COMPANY_IDSTR,
			allowBlank: true,
			id: 'idStr',
			name: 'viewBean.idStr',
			readOnly: true,
			cls: 'ha-field-disabled'
		}
	].concat(createFields);

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: COMPANY_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Companys CRUD
	//-----------------------------------------------------------------------------
	var companyGridCRUD = new HurryApp.GridCRUD({
		type: 'company',
		region: 'west',
		width: '40%',
		split: true,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/company/load',
		urlSave: myAppContext+'/company/save',
		urlEdit: myAppContext+'/company/edit',
		urlDelete: myAppContext+'/company/delete',

		title:               COMPANY_LISTE,
		formWindowTitle:     COMPANY_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    COMPANY_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: COMPANY_TOOLTIP_DELETE,
		labelDeleteQuestion: COMPANY_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   COMPANY_MESSAGE_DELETE_ADVERT,
		
		createFields: createFields,
		editFields: editFields,
		searchFields: window.userGroupId == GROUPE_SUPER_ADMIN ? [nameSearch] : null,
		displayAddButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		displayDeleteButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		displayToolBar: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return companyGridCRUD;
		}
	}
}
