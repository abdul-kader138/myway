GroupeCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'libelle', 
			dataIndex: 'libelle',
			header: GROUPE_LIBELLE, 
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
		fieldLabel: GROUPE_LIBELLE,
		id: 'libelle',
		name: 'viewBean.libelle'
	};
	
	var ressourceGridSelect = {
		xtype: 'gridSelectField',
		type: 'ressource',
		name: 'viewBean.ressourceIds',
		height: 115,
		columns: [{id: 'libelle', dataIndex: 'libelle', header: RESSOURCE_LIBELLE, sortable: true}],
		autoExpandColumn: 'libelle',
		urlLoad: myAppContext+'/ressource/loadByGroupe',
		urlSearch: myAppContext+'/ressource/load',
		title: RESSOURCE_LISTE,
		searchFields: [libelle]
	};
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var libelleSearch = {
		xtype: 'textfield',
		fieldLabel: GROUPE_LIBELLE,
		id: 'libelleRessourceSearch',
		name: 'viewBean.libelle'
	};
	
	//-----------------------------------------------------------------------------
	// Groupes CRUD
	//-----------------------------------------------------------------------------
	var groupeGridCRUD = new HurryApp.GridCRUD({
		type: 'groupe',
		region: 'center',
		width: '70%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'libelle',
	
		urlLoad: myAppContext+'/groupe/load',
		urlSave: myAppContext+'/groupe/save',
		urlEdit: myAppContext+'/groupe/edit',
		urlDelete: myAppContext+'/groupe/delete',
		urlUpdateModified: myAppContext+'/groupe/updateModified',
		
		title:               GROUPE_LISTE,
		formWindowTitle:     GROUPE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    GROUPE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: GROUPE_TOOLTIP_DELETE,
		labelDeleteQuestion: GROUPE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   GROUPE_MESSAGE_DELETE_ADVERT,
		
		createFields: [libelle, ressourceGridSelect],
		searchFields: [libelleSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return groupeGridCRUD;
		}
	}
}