LicenseCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: LICENSE_KEY,
			dataIndex: 'key',
			sortable: true,
			align: 'left',
			width: 200
		}
		,{
			id: 'description',
			header: LICENSE_DESCRIPTION,
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
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: LICENSE_KEY,
			allowBlank: true,
			id: 'key',
			name: 'viewBean.key'
		}
		,{
			xtype: 'textfield',
			fieldLabel: LICENSE_DESCRIPTION,
			allowBlank: true,
			id: 'description',
			name: 'viewBean.description'
		},
		{
			xtype: 'radiogroup',
			id: 'orientation',
			columns: 2,
			fieldLabel: LICENSE_ORIENTATION,
			items: [
				{boxLabel: LICENSE_ORIENTATION_NORTH,  name: 'viewBean.orientation', inputValue: 'north', checked: true}
				,{boxLabel: LICENSE_ORIENTATION_SOUTH,  name: 'viewBean.orientation', inputValue: 'south'}
				,{boxLabel: LICENSE_ORIENTATION_EAST, name: 'viewBean.orientation', inputValue: 'east'}
				,{boxLabel: LICENSE_ORIENTATION_WEST,  name: 'viewBean.orientation', inputValue: 'west'}
            ]
		}		
		/*
		,{
			xtype: 'combo',
			fieldLabel: LICENSE_ITEM,
			id: 'itemId',
			name: 'viewBean.item',
			hiddenName: 'viewBean.itemId',
			valueField: 'id',
			displayField: 'libelle',
			store: new Ext.data.JsonStore({
				fields: ['id', 'libelle'],
				data : items
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true
		}
		*/
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var keySearch = {
		xtype: 'textfield',
		fieldLabel: LICENSE_KEY,
		id: 'keySearch',
		name: 'viewBean.key'
	};
	
	//-----------------------------------------------------------------------------
	// Licenses CRUD
	//-----------------------------------------------------------------------------
	var licenseGridCRUD = new HurryApp.GridCRUD({
		type: 'license',
		region: 'north',
		split: true,
		width: '100%',
		height: 210,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'description',
	
		urlLoad: myAppContext+'/license/load',
		urlSave: myAppContext+'/license/save',
		urlEdit: myAppContext+'/license/edit',
		urlDelete: myAppContext+'/license/delete',
		loadOnStartup: false,
		loadFormCallback: function(form, action) {
			//var datas = Ext.decode(action.response.responseText).datas[0];
			//form.setValues(datas);
			
			//if (window.userGroupId != GROUPE_SUPER_ADMIN) {
				//Ext.getCmp('key').setReadOnly(true);
			//}
			//if (window.userGroupId == GROUPE_USER) {
				//Ext.getCmp('description').setReadOnly(true);
				//Ext.getCmp('okButton-form-panel-license').disable();
			//}
		},

		title:               LICENSE_LISTE,
		formWindowTitle:     LICENSE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    LICENSE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: LICENSE_TOOLTIP_DELETE,
		labelDeleteQuestion: LICENSE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   LICENSE_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		searchFields: window.userGroupId == GROUPE_SUPER_ADMIN ? [keySearch] : null,
		displayToolBar: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: false
	});

	return {
		getComponent: function() {
			return licenseGridCRUD;
		}
	}
}
