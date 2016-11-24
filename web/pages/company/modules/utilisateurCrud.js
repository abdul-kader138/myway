UtilisateurCrud = function() {
	// Evict super admin group if the user is not a super admin
	var groupesSecured = [];
	if (window.userGroupId != GROUPE_SUPER_ADMIN) {
		for (var i=0; i<groupes.length; i++) {
			if (groupes[i].id != GROUPE_SUPER_ADMIN) {
				//alert(groupes[i].id);
				groupesSecured.push(groupes[i]);
			}
		};
	}
	else {
		groupesSecured = groupes;
	}

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		/*{
			id: 'id',
			dataIndex: 'id',
			hidden: true
		}
		,*/{
			id: 'nom', 
			header: UTILISATEUR_NOM, 
			dataIndex: 'nom',
            align: 'left',
			sortable: true
		}
		,{
			header: UTILISATEUR_PRENOM, 
			width: 120, 
			dataIndex: 'prenom',
            align: 'left',
			sortable: true
		}
		,{
			id: 'login', 
			header: UTILISATEUR_LOGIN, 
			width: 120, 
			dataIndex: 'login',
            align: 'left',
			sortable: true
		}
		,{
			id: 'groupe', 
			header: UTILISATEUR_GROUPE, 
			width: 120, 
			dataIndex: 'groupe',
            align: 'left',
			sortable: false
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form field configs
	//-----------------------------------------------------------------------------
	var socId = {
		xtype: 'hidden',
		id: 'companyId',
		name: 'viewBean.companyId'
	};
	   
	var login = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_LOGIN,
		id: 'login',
		name: 'viewBean.login'
	};
	
	var groupe = {
		xtype: 'combo',
		fieldLabel: UTILISATEUR_GROUPE,
		id: 'groupeId',
		name: 'viewBean.groupe',
		hiddenName: 'viewBean.groupeId',
		valueField: 'id',
		displayField: 'libelle',
		store: new Ext.data.JsonStore({
			fields: ['id', 'libelle'],
			data : groupesSecured
		}),
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		selectOnFocus:true,
		editable: false
	};

	var separator = {
		xtype: 'panel',
		border: false,
		height: 15
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

	var email = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_EMAIL,
		id: 'email',
		name: 'viewBean.email'
		//vtype: 'email'
	};
	
	var phone = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_PHONE,
		id: 'phone',
		name: 'viewBean.phone'
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
			anchor: '90%'
		},
		items: [password, passwordConfirmed]
	};

	/*
	var groupeGridSelect = {
		xtype: 'gridSelectField',
		type: 'groupe',
		name: 'viewBean.groupeIds',
		height: 100,
		columns: [{id: 'libelle', dataIndex: 'libelle', header: GROUPE_LIBELLE, sortable: true}],
		autoExpandColumn: 'libelle',
		urlLoad: myAppContext+'/groupe/loadByUtilisateur',
		urlSearch: myAppContext+'/groupe/load',
		title: GROUPE_LISTE
	};
	*/
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var lastNameSearch = {
		xtype: 'textfield',
		fieldLabel: UTILISATEUR_NOM,
		id: 'nomSearch',
		name: 'viewBean.nom'
	};
	
	//-----------------------------------------------------------------------------
	// Create grid CRUD
	//-----------------------------------------------------------------------------
	var utilisateurGridCRUD = new HurryApp.GridCRUD({
		type: 'utilisateur',
		region: 'center',
		width: '60%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'nom',
	
		urlLoad: myAppContext+'/utilisateur/load',
		urlSave: myAppContext+'/utilisateur/save',
		urlEdit: myAppContext+'/utilisateur/edit',
		urlDelete: myAppContext+'/utilisateur/delete',
		defaultParams: {'viewBean.companyId': window.userCompanyId},
		loadOnStartup: window.userGroupId != GROUPE_SUPER_ADMIN ? true : false,
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			if (datas.groupeId == GROUPE_SUPER_ADMIN && window.userGroupId != GROUPE_SUPER_ADMIN) {
				Ext.getCmp('okButton-form-panel-utilisateur').disable();
				Ext.getCmp('groupeId').setRawValue(datas.groupe);
			}
		},
		
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
		
		createFields: [socId, login, password, passwordConfirmed, groupe, separator, nom, prenom, email, phone, changePassword],
		editFields:   [socId, login, groupe, separator, nom, prenom, email, phone, passwordFieldSet],
		searchFields: [lastNameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     90,

		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return utilisateurGridCRUD;
		}
	}
}
