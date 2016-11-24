GroupeSelect = function() {
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'libelle', 
			dataIndex: 'libelle',
			header: GROUPE_LIBELLE,
			sortable: true
		}
	];
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var libelleSearch = {
		xtype: 'textfield',
		fieldLabel: GROUPE_LIBELLE,
		id: 'libelleGroupeSearch',
		name: 'viewBean.libelle'
	};

	//-----------------------------------------------------------------------------
	// Groupes select grid
	//-----------------------------------------------------------------------------
	var groupeGridSelect = new HurryApp.GridSelect({
		type: 'groupe',
		region: 'east',
		width: '30%',
		autoHeight: false,
		collapsible: true,
		split: true,
		columns: columns,
		autoExpandColumn: 'libelle',
		urlLoad: myAppContext+'/groupe/loadByRessource',
		urlSearch: myAppContext+'/groupe/load',
		urlSave: myAppContext+'/menu/saveGroupes',
		collectionName: 'viewBean.groupeIds',
		title: GROUPE_LISTE,
		searchFields: [libelleSearch]
	});

	return {
		getComponent: function() {
			return groupeGridSelect;
		}
	}
}