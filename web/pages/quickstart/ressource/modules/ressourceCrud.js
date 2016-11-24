RessourceCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'libelle', 
			dataIndex: 'libelle',
			header: RESSOURCE_LIBELLE, 
			sortable: true,
			editor: new Ext.form.TextField({
				allowBlank: false
			})
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var libelle = {
		xtype: 'textfield',
		fieldLabel: RESSOURCE_LIBELLE,
		id: 'libelle',
		name: 'viewBean.libelle'
	};
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var libelleSearch = {
		xtype: 'textfield',
		fieldLabel: RESSOURCE_LIBELLE,
		id: 'libelleSearch',
		name: 'viewBean.libelle'
	};
	
	//-----------------------------------------------------------------------------
	// Ressources CRUD
	//-----------------------------------------------------------------------------
	var ressourceGridCRUD = new HurryApp.GridCRUD({
		type: 'ressource',
		region: 'center',
		width: '70%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'libelle',
	
		urlLoad: myAppContext+'/ressource/load',
		urlSave: myAppContext+'/ressource/save',
		urlEdit: myAppContext+'/ressource/edit',
		urlDelete: myAppContext+'/ressource/delete',
		urlUpdateModified: myAppContext+'/ressource/updateModified',
		
		title:               RESSOURCE_LISTE,
		formWindowTitle:     RESSOURCE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    RESSOURCE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: RESSOURCE_TOOLTIP_DELETE,
		labelDeleteQuestion: RESSOURCE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   RESSOURCE_MESSAGE_DELETE_ADVERT,
		
		createFields: [libelle],
		searchFields: [libelleSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return ressourceGridCRUD;
		}
	}
}
