WordingCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: WORDING_VALUE,
			dataIndex: 'value',
			sortable: true,
			align: 'left',
			width: 200
		}
		,{
			id: 'description',
			header: WORDING_DESCRIPTION,
			dataIndex: 'description',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'languageId',
			name: 'viewBean.languageId'
		}
		,window.userGroupId != GROUPE_SUPER_ADMIN ? {
			xtype: 'hidden',
			id: 'name',
			name: 'viewBean.name',
		} : {
			xtype: 'textfield',
			fieldLabel: WORDING_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name',
			readOnly: window.userGroupId == GROUPE_SUPER_ADMIN ? false : true,
			cls: window.userGroupId == GROUPE_SUPER_ADMIN ? '' : 'ha-field-disabled'
		}
		,{
			xtype: 'textarea',
			fieldLabel: WORDING_DESCRIPTION,
			allowBlank: true,
			id: 'description',
			name: 'viewBean.description',
			readOnly: window.userGroupId == GROUPE_SUPER_ADMIN ? false : true,
			cls: window.userGroupId == GROUPE_SUPER_ADMIN ? '' : 'ha-field-disabled',
			listeners: {
				'focus': function() {
					window.keyMapEnter.disable();					
				},
				'blur': function() {
					window.keyMapEnter.enable();					
				}
			}
		}
		,{
			xtype: 'textfield',
			fieldLabel: WORDING_VALUE,
			allowBlank: true,
			id: 'value',
			name: 'viewBean.value'
		}
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: WORDING_NAME,
		id: 'nameSearch2',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Wordings CRUD
	//-----------------------------------------------------------------------------
	var wordingGridCRUD = new HurryApp.GridCRUD({
		type: 'wording',
		region: 'east',
		width: '70%',
		split: true,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'description',
	
		urlLoad: myAppContext+'/wording/load',
		urlSave: myAppContext+'/wording/save',
		urlEdit: myAppContext+'/wording/edit',
		urlDelete: myAppContext+'/wording/delete',

		title:               WORDING_LISTE,
		formWindowTitle:     WORDING_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    WORDING_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: WORDING_TOOLTIP_DELETE,
		labelDeleteQuestion: WORDING_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   WORDING_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: false,
		displayAddButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		displayDeleteButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		loadOnStartup: false
	});

	return {
		getComponent: function() {
			return wordingGridCRUD;
		}
	}
}
