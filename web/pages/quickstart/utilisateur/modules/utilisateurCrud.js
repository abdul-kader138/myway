UtilisateurCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		/*{
			id: 'id',
			dataIndex: 'id',
			hidden: true
		},*/{
			id: 'login', 
			header: UTILISATEUR_LOGIN, 
			dataIndex: 'login',
            align: 'left',
			sortable: true
		},{
			header: UTILISATEUR_NOM, 
			width: 200, 
			dataIndex: 'nom',
            align: 'left',
			sortable: true
		},{
			header: UTILISATEUR_PRENOM, 
			width: 200, 
			dataIndex: 'prenom',
            align: 'left',
			sortable: true
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form field configs
	//-----------------------------------------------------------------------------
	var socId = {
		xtype: 'hidden',
		id: 'societeId',
		name: 'viewBean.societeId'
	};
	   
	var login = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_LOGIN,
		id: 'login',
		name: 'viewBean.login'
	};
	   
	var nom = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_NOM,
		id: 'nom',
		name: 'viewBean.nom'
	};

	var prenom = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_PRENOM,
		id: 'prenom',
		name: 'viewBean.prenom'
	};
	
	var password = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_PASSWORD,
		inputType: 'password',
		id: 'password',
		name: 'viewBean.password'
	};
	   
	var passwordConfirmed = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_PASSWORD_CONFIRM,
		inputType: 'password',
		id: 'passwordConfirmed',
		name: 'viewBean.passwordConfirmed'
	};
	
	var changePassword = {
		xtype: 'hidden',
		id: 'changePassword',
		name: 'viewBean.changePassword',
		value: 'on' 
	};
	
	var passwordFieldSet = {
		xtype:'fieldset',
		title: UTILISATEUR_PASSWORD_CHANGE,
		checkboxToggle: true,
		checkboxName: 'viewBean.changePassword',
		width: 340,
		autoHeight: true,
		collapsed: true,
		defaults: {
			anchor: '95%'
		},
		items: [password, passwordConfirmed]
	};
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var loginSearch = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_LOGIN,
		id: 'loginSearch',
		name: 'viewBean.login'
	};
	
	//-----------------------------------------------------------------------------
	// Create grid CRUD
	//-----------------------------------------------------------------------------
	var utilisateurGridCRUD = new HurryApp.GridCRUD({
		type: 'utilisateur',
		region: 'center',
		width: '70%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'login',
	
		urlLoad: myAppContext+'/utilisateur/load',
		urlSave: myAppContext+'/utilisateur/save',
		urlEdit: myAppContext+'/utilisateur/edit',
		urlDelete: myAppContext+'/utilisateur/delete',
		defaultParams: {'viewBean.societeId': societeId},
		
		title:               UTILISATEUR_LISTE,
		formWindowTitle:     UTILISATEUR_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    UTILISATEUR_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: UTILISATEUR_TOOLTIP_DELETE,
		labelDeleteQuestion: UTILISATEUR_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   UTILISATEUR_MESSAGE_DELETE_ADVERT,
		
		createFields: [socId, login, password, passwordConfirmed, nom, prenom, changePassword],
		editFields:   [socId, login, nom, prenom, passwordFieldSet],
		searchFields: [loginSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,

		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return utilisateurGridCRUD;
		}
	}
}
