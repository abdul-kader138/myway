DeviceSelect = function() {
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name', 
			dataIndex: 'name',
			header: DEVICE_NAME, 
            width: 150, 
			sortable: true
		},
        {
			id: 'key', 
			dataIndex: 'key',
			header: DEVICE_KEY,
            width: 180,
			sortable: true
		}
	];
	
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
	// Groupes select grid
	//-----------------------------------------------------------------------------
	var deviceGridSelect = new HurryApp.GridSelect({
		type: 'device',
		region: 'south',
		width: '30%',
		height: 190,
		autoHeight: false,
		collapsible: true,
		split: true,
		columns: columns,
		autoExpandColumn: 'name',
		hideHeaders: false,
		urlLoad: myAppContext+'/device/loadByLicense',
		urlSearch: myAppContext+'/device/loadByNoLicense',
		urlSave: myAppContext+'/license/saveDevices',
		collectionName: 'viewBean.deviceIds',
		title: DEVICE_LISTE,
		searchFields: [nameSearch]
	});

	return {
		getComponent: function() {
			return deviceGridSelect;
		}
	}
}
