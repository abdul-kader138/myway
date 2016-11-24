UserSelect = function() {
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'nom', 
			header: UTILISATEUR_NOM, 
			width: 90, 
			dataIndex: 'nom',
			sortable: true
		}
		,{
			id: 'prenom', 
			header: UTILISATEUR_PRENOM, 
			width: 90, 
			dataIndex: 'prenom',
			sortable: true
		}
		,{
			id: 'groupe', 
			header: UTILISATEUR_GROUPE, 
			width: 150,
			dataIndex: 'groupe',
			sortable: false
		}
		/*,{
			id: 'login', 
			header: UTILISATEUR_LOGIN, 
			width: 150, 
			dataIndex: 'login',
			sortable: true
		}*/
	];
	
	//-----------------------------------------------------------------------------
	// Search field
	//-----------------------------------------------------------------------------
	var nomSearch = {
		xtype: 'textfield',
		fieldLabel: COMMON_SEARCH,
		id: 'nomSearch',
		name: 'viewBean.nom'
	};

	//-----------------------------------------------------------------------------
	// Users select grid
	//-----------------------------------------------------------------------------
	var userGridSelect = new HurryApp.GridSelect({
		type: 'utilisateur',
		region: 'south',
		width: '100%',
		height: 190,
		autoHeight: false,
		collapsible: false,
		split: true,
		columns: columns,
		autoExpandColumn: 'groupe',
		hideHeaders: false,
		urlLoad: myAppContext+'/utilisateur/load',
		//urlLoad: myAppContext+'/utilisateur/loadByProject',
		urlSearch: myAppContext+'/utilisateur/loadByCompanyNotInProject',
		urlSave: myAppContext+'/project/saveUsers',
		loadOnStartup: false,
		collectionName: 'viewBean.userIds',
		title: UTILISATEUR_LISTE,
		searchFields: [nomSearch],
		formWindowHeight: 400,
		displayToolBar: window.userGroupId != GROUPE_USER ? true : false
	});

	return {
		getComponent: function() {
			return userGridSelect;
		}
	}
}