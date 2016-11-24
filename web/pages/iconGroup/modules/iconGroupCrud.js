IconGroupCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: ICONGROUP_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		},
		{
			id: 'type',
			dataIndex: 'type',
			hidden: true
		}
		
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: ICONGROUP_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: ICONGROUP_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// IconGroups CRUD
	//-----------------------------------------------------------------------------
	var iconGroupGridCRUD = new HurryApp.GridCRUD({
		type: 'iconGroup',
		region: 'center',
		width: '20%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/iconGroup/load',
		urlSave: myAppContext+'/iconGroup/save',
		urlEdit: myAppContext+'/iconGroup/edit',
		urlDelete: myAppContext+'/iconGroup/delete',
		defaultParams: {'viewBean.projectId': window.projectId},

		title:               ICONGROUP_LISTE,
		formWindowTitle:     ICONGROUP_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    ICONGROUP_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: ICONGROUP_TOOLTIP_DELETE,
		labelDeleteQuestion: ICONGROUP_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   ICONGROUP_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		fnDblClick: window.userGroupId != GROUPE_SUPER_ADMIN ? Ext.emptyFn : null,
		
		displayPagingBar: true,
		displayAddButton: false,
		displayEditButton: window.userGroupId != GROUPE_SUPER_ADMIN ? false : true,
		displayDeleteButton: false
	});

	return {
		getComponent: function() {
			return iconGroupGridCRUD;
		}
	}
}
