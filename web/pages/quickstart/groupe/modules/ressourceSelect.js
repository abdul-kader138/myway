RessourceSelect = function() {
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'libelle', 
			dataIndex: 'libelle',
			header: RESSOURCE_LIBELLE, 
			sortable: true
		}
	];
	
	//-----------------------------------------------------------------------------
	// Search field
	//-----------------------------------------------------------------------------
	var libelleSearch = {
		xtype: 'textfield',
		fieldLabel: RESSOURCE_LIBELLE,
		id: 'libelleSearch',
		name: 'viewBean.libelle'
	};

	//-----------------------------------------------------------------------------
	// Ressources select grid
	//-----------------------------------------------------------------------------
	var ressourceGridSelect = new HurryApp.GridSelect({
		type: 'ressource',
		region: 'east',
		width: '30%',
		autoHeight: false,
		collapsible: true,
		split: true,
		columns: columns,
		autoExpandColumn: 'libelle',
		urlLoad: myAppContext+'/ressource/loadByGroupe',
		urlSearch: myAppContext+'/ressource/load',
		urlSave: myAppContext+'/groupe/saveRessources',
		collectionName: 'viewBean.ressourceIds',
		title: RESSOURCE_LISTE,
		searchFields: [libelleSearch]
	});

	return {
		getComponent: function() {
			return ressourceGridSelect;
		}
	}
}