ConfigParameterCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'libelle', 
			dataIndex: 'libelle',
			header: CONFIG_PARAMETER_LIBELLE,
			sortable: true,
			editor: new Ext.form.TextField({
				allowBlank: false
			})
		},
		{
			id: 'valeur', 
			dataIndex: 'valeur',
			header: CONFIG_PARAMETER_VALEUR, 
			sortable: true,
			width: 500,
			editor: new Ext.form.TextField({
				allowBlank: true
			})
		},
		{
			id: 'type', 
			dataIndex: 'type',
			header: CONFIG_PARAMETER_TYPE, 
			sortable: true,
			editor: new Ext.form.ComboBox({
				store: ['String', 'Integer', 'Float'],
				editable: false,
				triggerAction: 'all'
			})
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var domainId = {
		xtype: 'hidden',
		id: 'domainId',
		name: 'viewBean.domainId'
	};
	
	var libelle = {
		xtype: 'textfield',
		fieldLabel: CONFIG_PARAMETER_LIBELLE,
		id: 'libelle',
		name: 'viewBean.libelle'
	};
	
	var valeur = {
		xtype: 'textfield',
		fieldLabel: CONFIG_PARAMETER_VALEUR,
		id: 'valeur',
		name: 'viewBean.valeur'
	};
	
	var type = {
		xtype: 'combo',
		fieldLabel: CONFIG_PARAMETER_TYPE,
		id: 'type',
		name: 'viewBean.type',
		store: ['String', 'Integer', 'Float'],
		editable: false,
		triggerAction: 'all'
	};

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var libelleSearch = {
		xtype: 'textfield',
		fieldLabel: CONFIG_DOMAIN_LIBELLE,
		id: 'libelleSearch',
		name: 'viewBean.libelle'
	};
	
	//-----------------------------------------------------------------------------
	// Config parameters CRUD
	//-----------------------------------------------------------------------------
	var parameterGridCRUD = new HurryApp.GridCRUD({
		renderTo: Ext.getBody(),
		type: 'configParameter',
		region: 'center',
		height: DEFAULT_GRID_HEIGHT - 65,
		autoHeight: false,
		border: false,
		frame: false,

		columns: columns,
		autoExpandColumn: 'libelle',
	
		loadOnStartup: false,
		urlLoad: myAppContext+'/configParameter/load',
		urlSave: myAppContext+'/configParameter/save',
		urlEdit: myAppContext+'/configParameter/edit',
		urlDelete: myAppContext+'/configParameter/delete',
		urlUpdateModified: myAppContext+'/configParameter/updateModified',

		formWindowTitle:     CONFIG_PARAMETER_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    CONFIG_PARAMETER_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: CONFIG_PARAMETER_TOOLTIP_DELETE,
		labelDeleteQuestion: CONFIG_PARAMETER_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   CONFIG_PARAMETER_MESSAGE_DELETE_ADVERT,

		createFields: [domainId, libelle, valeur, type],
		searchFields: [libelleSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     50,
		
		displayPagingBar: true
	});
	
	return {
		getComponent: function() {
			return parameterGridCRUD;
		}
	}
}