MenuCrud = function() {

	//-----------------------------------------------------------------------------
	// Form field configs
	//-----------------------------------------------------------------------------
	var libelle = {
		xtype: 'textfield',
		fieldLabel: MENU_LIBELLE,
		id: 'libelle',
		name: 'viewBean.libelle'
	};
	
	var url = {
		xtype: 'textfield',
		fieldLabel: MENU_URL,
		id: 'url',
		name: 'viewBean.url'
	};
	
	//-----------------------------------------------------------------------------
	// Menus CRUD
	//-----------------------------------------------------------------------------
	var menuTreeCRUD = new HurryApp.TreeCRUD({
		type: 'menu',
		region: 'center',
		width: '70%',
		autoHeight: false,

		//dataUrl: 'nodes.txt',
		urlLoad: myAppContext+'/menu/load',
		urlSave: myAppContext+'/menu/save',
		urlEdit: myAppContext+'/menu/edit',
		urlDelete: myAppContext+'/menu/delete',
		urlMove: myAppContext+'/menu/move',

		title:               MENU_LISTE,
		formWindowTitle:     MENU_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    MENU_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: MENU_TOOLTIP_DELETE,
		labelDeleteQuestion: MENU_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   MENU_MESSAGE_DELETE_ADVERT,
		
		createFields: [libelle, url],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80
	});

	return {
		getComponent: function() {
			return menuTreeCRUD;
		}
	}
}