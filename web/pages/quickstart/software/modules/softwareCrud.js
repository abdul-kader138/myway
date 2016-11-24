SoftwareCrud = function() {

    var download = function (value) {
    	var tpldownload = '<a style="text-decoration:underline" href="' + myAppContext + '/uploads/{0}" >Download</a>';
        return String.format(tpldownload, value);
    }
    
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'version', 
			dataIndex: 'version',
			header: SOFTWARE_VERSION, 
			sortable: true,
		},
        {
			id: 'vendor', 
			dataIndex: 'vendor',
			header: SOFTWARE_VENDOR, 
			sortable: true,
		},
		{
			id: 'uploadTime', 
			dataIndex: 'uploadTime',
			header: SOFTWARE_UPLOAD_TIME, 
			sortable: true,
			width: 150,
			align: 'center',
		},
		{
			id: 'modifyTime', 
			dataIndex: 'modifyTime',
			header: SOFTWARE_MODIFY_TIME,
			sortable: true,
			width: 150,
			align: 'center',
		},
		{
			id: 'uploadBy', 
			dataIndex: 'userName',
			header: SOFTWARE_UPLOAD_BY,
			sortable: true,
		},
		{
			header: SOFTWARE_DOWNLOAD,
			dataIndex: 'installPack',
			sortable: false,
			align: 'center',
			renderer: download
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	
	var id = {
		xtype: 'hidden',
		id: 'id',
		name: 'viewBean.id'
	};
	
	var version = {
		xtype: 'textfield',
		fieldLabel: SOFTWARE_VERSION,
		id: 'version',
		name: 'viewBean.version',
		allowBlank: false,
        blankText: SOFTWARE_ERREUR_VERSION_OBLIGATOIRE
	};
	
    var vendor = {
		xtype: 'textfield',
		fieldLabel: SOFTWARE_VENDOR,
		id: 'vendor',
		name: 'viewBean.vendor',
		allowBlank: true,
	};

    var softwareUpload = {
		xtype: 'fileuploadfield',
		fieldLabel: SOFTWARE_FILE,
		allowBlank: true,
		blankText: SOFTWARE_ERREUR_INSTALLPACK_OBLIGATOIRE,
		id: 'installPack',
		name: 'viewBean.installPack',
		buttonText: '',
		buttonCfg: {
			iconCls: 'tool-dir-search'
		},
	};
    
    var versionForEdit = {
		xtype: 'textfield',
		readOnly:true,
		fieldLabel: SOFTWARE_VERSION,
		id: 'version',
		name: 'viewBean.version',
	};
    
    var softwareForEdit = {
		xtype: 'textfield',
		readOnly:true,
		fieldLabel: SOFTWARE_FILE,
		id: 'installPack',
		name: 'viewBean.installPack'
    }

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var versionSearch = {
		xtype: 'textfield',
		fieldLabel: SOFTWARE_VERSION,
		id: 'versionSearch',
		name: 'viewBean.version'
	};
	
	//-----------------------------------------------------------------------------
	// Ressources CRUD
	//-----------------------------------------------------------------------------
	var softwareGridCRUD = new HurryApp.GridCRUD({
		type: 'software',
		region: 'center',	
		width: '70%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'vendor',
	
		urlLoad: myAppContext+'/software/load',
		urlSave: myAppContext+'/software/save',
		urlEdit: myAppContext+'/software/edit',
		urlDelete: myAppContext+'/software/delete',
		
		title:               SOFTWARE_LISTE,
		formWindowTitle:     SOFTWARE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    SOFTWARE_TOOLTIP_ADD,
		
		createFields: [id, version, vendor, softwareUpload],
		editFields: [id, versionForEdit, vendor, softwareForEdit],
		searchFields: [versionSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT,
		
		displayPagingBar: true,
		/*saveFinalCallback: function(formType, jsonResponse) {
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
		}*/
	});

	return {
		getComponent: function() {
			return softwareGridCRUD;
		}
	}
}
