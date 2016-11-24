DeviceCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name', 
			dataIndex: 'name',
			header: DEVICE_NAME, 
			sortable: true,
			editor: new Ext.form.TextField({
				allowBlank: false
			}),
		},
        {
			id: 'key', 
			dataIndex: 'key',
			header: DEVICE_KEY, 
			sortable: true,
			width: 700
//			editor: new Ext.form.TextField({
//				allowBlank: false
//			})
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var name = {
		xtype: 'textfield',
		fieldLabel: DEVICE_NAME,
		id: 'name',
		name: 'viewBean.name',
		allowBlank: false,
        blankText: DEVICE_ERREUR_NAME_OBLIGATOIRE
	};
	
    var key = {
		xtype: 'textfield',
		fieldLabel: DEVICE_KEY,
		id: 'key',
		name: 'viewBean.key',
		allowBlank: false,
	    blankText: DEVICE_ERREUR_KEY_OBLIGATOIRE
	};

    var keyForEdit = {
		xtype: 'textfield',
		readOnly:true,
		fieldLabel: DEVICE_KEY,
		id: 'key',
		name: 'viewBean.key'
	};


	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: DEVICE_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Ressources CRUD
	//-----------------------------------------------------------------------------
	var deviceGridCRUD = new HurryApp.GridCRUD({
		type: 'device',
		region: 'center',	
		width: '70%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',

	    displayDeleteButton: false,
	
		urlLoad: myAppContext+'/device/load',
		urlSave: myAppContext+'/device/save',
		urlEdit: myAppContext+'/device/edit',
		urlUpdateModified: myAppContext+'/device/updateModified',
		
		title:               DEVICE_LISTE,
		formWindowTitle:     DEVICE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    DEVICE_TOOLTIP_ADD,
		
		createFields: [name, key],
        editFields: [name, keyForEdit],
		searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true,
		saveFinalCallback: function(formType, jsonResponse) {
			this.formWindow.close();
			if (jsonResponse.errors.length != 0) {
				var errorMsg = '';
				if (jsonResponse.errors.length != 0) {
					for (var i = 0; i < jsonResponse.errors.length; i++) {
						if (errorMsg != '') {
							errorMsg += '<br/>';
						}
						errorMsg += jsonResponse.errors[i].msg;
					}
				}
				HurryApp.MessageUtils.showError(errorMsg);
			}
		}
	});

	return {
		getComponent: function() {
			return deviceGridCRUD;
		}
	}
}
