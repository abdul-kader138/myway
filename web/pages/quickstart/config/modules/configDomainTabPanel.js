ConfigDomainTabPanel = function(gridPanel) {

	//-----------------------------------------------------------------------------
	// Form field configs
	//-----------------------------------------------------------------------------
	var libelle = {
		xtype: 'textfield',
		fieldLabel: CONFIG_DOMAIN_LIBELLE,
		id: 'libelle',
		name: 'viewBean.libelle'
	};
	
	//-----------------------------------------------------------------------------
	// Tab Pannel
	//-----------------------------------------------------------------------------
	var configTabPanel = new HurryApp.TabPanelCRUD({
		datas: domains,
		gridPanel: gridPanel,

		region: 'center',
		urlLoad: myAppContext+'/config/load',
		urlSave: myAppContext+'/config/save',
		urlEdit: myAppContext+'/config/edit',
		urlDelete: myAppContext+'/config/delete',
		
		title:               CONFIG_DOMAIN_TITRE,                
		formWindowTitle:     CONFIG_DOMAIN_TITRE,                
		labelConfirmButton:  COMMON_OK,                          
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,                         
		tooltipAddButton:    CONFIG_DOMAIN_TOOLTIP_ADD,          
		labelUpdateButton:   COMMON_UPDATE,                      
		labelDeleteButton:   COMMON_DELETE,                      
		labelDeleteQuestion: CONFIG_DOMAIN_MESSAGE_DELETE_QUESTION,
		
		createFields: [libelle],
		tabLabel: 'libelle',
		gridDefaultLoadParam: 'domainId',
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     50
	});
	
	return {
		getComponent: function() {
			return configTabPanel;
		}
	}
}