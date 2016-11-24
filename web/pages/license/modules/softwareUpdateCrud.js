SoftwareUpdateCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'logType',
			header: SOFTWAREUPDATE_LOGTYPE,
			dataIndex: 'logType',
			sortable: true,
			align: 'left',
			width: 100
		}
		,{
			id: 'pcName',
			header: SOFTWAREUPDATE_PCNAME,
			dataIndex: 'pcName',
			sortable: true,
			align: 'left',
			width: 100
		}
		,{
			id: 'ip',
			header: SOFTWAREUPDATE_IP,
			dataIndex: 'ip',
			sortable: true,
			align: 'left',
			width: 80
		}
		,{
			id: 'oldVersion',
			header: SOFTWAREUPDATE_OLDVERSION,
			dataIndex: 'oldVersion',
			sortable: true,
			align: 'left',
			width: 80
		}
		,{
			id: 'newVersion',
			header: SOFTWAREUPDATE_NEWVERSION,
			dataIndex: 'newVersion',
			sortable: true,
			align: 'left',
			width: 80
		}
		,{
			id: 'updateTime',
			header: SOFTWAREUPDATE_TIME,
			dataIndex: 'updateTime',
			sortable: true,
			align: 'left',
			width: 100
		}
		,{
			id: 'description',
			header: SOFTWAREUPDATE_DESCRIPTION,
			dataIndex: 'description',
			sortable: true,
			align: 'left',
			width: 100
		}
	];
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var search = {
		xtype: 'textfield',
		fieldLabel: SOFTWAREUPDATE_VERSION,
		id: 'versionSearch',
		name: 'viewBean.search'
	};
	
	//-----------------------------------------------------------------------------
	// software update CRUD
	//-----------------------------------------------------------------------------
	var softwareUpdateGridCRUD = new HurryApp.GridCRUD({
		type: 'softwareUpdate',
		region: 'east',
		id: 'softwareUpdateGridCRUD',
		split: true,
		width: '65%',
		height: 210,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'description',
	
		urlLoad: myAppContext+'/softwareUpdate/load',
		loadOnStartup: false,
		enableEditForm: false,
		displayEditButton: false,
		displayAddButton: false,
		fnDblClick: function() {},

		title: SOFTWAREUPDATE_TITLE,
		
		searchFields: [search],
		createFields: [],
		displayToolBar: true,
		displayDeleteButton: false,
		
		formWindowWidth:    400,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		//displayPagingBar: false
	});

	return {
		getComponent: function() {
			return softwareUpdateGridCRUD;
		}
	}
}