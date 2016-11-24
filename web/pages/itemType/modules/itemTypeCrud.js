ItemTypeCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: ITEMTYPE_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		/*
		,{
			header: ITEMTYPE_ICON,
			dataIndex: 'icon',
			sortable: false,
			align: 'left'
		}
		*/
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'textfield',
			fieldLabel: ITEMTYPE_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		/*
		,{
			xtype: 'textfield',
			fieldLabel: ITEMTYPE_ICON,
			allowBlank: true,
			id: 'icon',
			name: 'viewBean.icon'
		}
		*/
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: ITEMTYPE_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// ItemTypes CRUD
	//-----------------------------------------------------------------------------
	var itemTypeGridCRUD = new HurryApp.GridCRUD({
		type: 'itemType',
		region: 'center',
		width: '20%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/itemType/load',
		urlSave: myAppContext+'/itemType/save',
		urlEdit: myAppContext+'/itemType/edit',
		urlDelete: myAppContext+'/itemType/delete',

		title:               ITEMTYPE_LISTE,
		formWindowTitle:     ITEMTYPE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    ITEMTYPE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: ITEMTYPE_TOOLTIP_DELETE,
		labelDeleteQuestion: ITEMTYPE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   ITEMTYPE_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return itemTypeGridCRUD;
		}
	}
}
