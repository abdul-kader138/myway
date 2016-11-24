DataSourceCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: DATASOURCE_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			id: 'status',
			header: DATASOURCE_STATUS,
			dataIndex: 'status',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		/*{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: DATASOURCE_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}*/
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	/*var nameSearch = {
		xtype: 'textfield',
		fieldLabel: BUILDING_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};*/
	
	//-----------------------------------------------------------------------------
	// Buildings CRUD
	//-----------------------------------------------------------------------------
	var dataSourceGridCRUD = new HurryApp.GridCRUD({
		type: 'dataSource',
		region: 'center',
		width: '100%',
		height: "100%",
		autoHeight: true,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/dataSource/load',
		urlSave: myAppContext+'/dataSource/save',
		urlEdit: myAppContext+'/dataSource/edit',
		//urlDelete: myAppContext+'/building/delete',
		defaultParams: {'viewBean.name': ""},

		displayMenuBar: false,
		header: false,
		
		beforeSubmit: function() {
			//if (this.getForm().findField('id').getValue() == '') {
			//	this.getForm().setValues({
			//		'viewBean.index': buildingGridCRUD.store.getCount()
			//	});
			//}
		},
		
		createFields: fields,
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: false
	});

	return {
		getComponent: function() {
			return dataSourceGridCRUD;
		}
	}
}
