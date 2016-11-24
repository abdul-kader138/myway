PlaylistCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: PLAYLIST_ACTIVE,
			dataIndex: 'active',
			sortable: true,
			align: 'left',
			width: 40,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		      return '<div class="tool-'+record.data.active+'">&nbsp;</div>';
			}
		}
		,{
			id: 'name', 
			header: PLAYLIST_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: PLAYLIST_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'combo',
			fieldLabel: PLAYLIST_LICENSE,
			id: 'licenseId',
			name: 'viewBean.license',
			hiddenName: 'viewBean.licenseId',
			valueField: 'id',
			displayField: 'key',
			store: new Ext.data.JsonStore({
				fields: ['id', 'key'],
				data : [{id: null, name: '-'}].concat(licenses)
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus: true,
			editable: false
		}
		,{
			xtype: 'checkbox',
			fieldLabel: PLAYLIST_ACTIVE,
			id: 'active',
			name: 'viewBean.active',
			inputValue: true,
			checked: true
		}
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: PLAYLIST_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Playlists CRUD
	//-----------------------------------------------------------------------------
	var playlistGridCRUD = new HurryApp.GridCRUD({
		type: 'playlist',
		region: 'west',
		width: '30%',
		split: true,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/playlist/load',
		urlSave: myAppContext+'/playlist/save',
		urlEdit: myAppContext+'/playlist/edit',
		urlDelete: myAppContext+'/playlist/delete',
		defaultParams: {'viewBean.projectId': window.projectId},

		title:               PLAYLIST_LISTE,
		formWindowTitle:     PLAYLIST_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    PLAYLIST_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: PLAYLIST_TOOLTIP_DELETE,
		labelDeleteQuestion: PLAYLIST_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   PLAYLIST_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     50,
		
		displayPagingBar: true,
		
		beforeSubmit: function() {
			if (this.getForm().findField('id').getValue() == '') {
				this.getForm().setValues({
					'viewBean.index': playlistGridCRUD.store.getCount()
				});
			}
		},
		menuButtons: [
		    {
				id: 'cloneButton-playlist',
				text: PLAYLIST_BUTTON_CLONE,
				tooltip: PLAYLIST_BUTTON_CLONE_TOOLTIP,
				iconCls: 'tool-clone',
				handler: function() {
					var selectedRecord = playlistGridCRUD.getSelectionModel().getSelections();
					if(selectedRecord && selectedRecord.length == 1) {
						HurryApp.Utils.sendAjaxRequest(
							myAppContext+'/playlist/copy',
							{"viewBean.id": selectedRecord[0].id},
							function(jsonResponse) {
								playlistGridCRUD.getStore().load();
								playlistGridCRUD.editEntity(jsonResponse.datas[0].id);
							},
							"",
							function() {
								Ext.MessageBox.alert('', AJAX_FAILURE);
							}
						);
					}
					else {
						HurryApp.MessageUtils.showError("Please select one playlist");
					}
				}
			}
  			,{
  				id: 'upButton-playlist',
  				text: PLAYLIST_BUTTON_UP,
  				tooltip: PLAYLIST_BUTTON_UP_TOOLTIP,
  				iconCls: 'tool-up',
  				handler: function() {
  					var selectedRecord = playlistGridCRUD.getSelectionModel().getSelected();
  					if (selectedRecord) {
  						var selectedRecordIndex = playlistGridCRUD.store.indexOf(selectedRecord);
  						if (selectedRecordIndex > 0) {
  							playlistGridCRUD.store.remove(selectedRecord);
  							playlistGridCRUD.store.insert(selectedRecordIndex-1, selectedRecord);
  							playlistGridCRUD.getSelectionModel().selectRow(selectedRecordIndex-1);
  							for (var i=0; i<playlistGridCRUD.store.data.items.length; i++) {
  								playlistGridCRUD.store.data.items[i].data.index = i;
  							};
  							HurryApp.Utils.sendAjaxRequest(myAppContext+'/playlist/updateModified', {'selectedObjects' : playlistGridCRUD.getObjects()});
  						}
  					}
  				}
  			}		
  			,{
  				id: 'downButton-playlist',
  				text: PLAYLIST_BUTTON_DOWN,
  				tooltip: PLAYLIST_BUTTON_DOWN_TOOLTIP,
  				iconCls: 'tool-down',
  				handler: function() {
  					var selectedRecord = playlistGridCRUD.getSelectionModel().getSelected();
  					if (selectedRecord) {
  						var selectedRecordIndex = playlistGridCRUD.store.indexOf(selectedRecord);
  						if (selectedRecordIndex < playlistGridCRUD.store.getCount()-1) {
  							playlistGridCRUD.store.remove(selectedRecord);
  							playlistGridCRUD.store.insert(selectedRecordIndex+1, selectedRecord);
  							playlistGridCRUD.getSelectionModel().selectRow(selectedRecordIndex+1);
  							for (var i=0; i<playlistGridCRUD.store.data.items.length; i++) {
  								playlistGridCRUD.store.data.items[i].data.index = i;
  							};
  							HurryApp.Utils.sendAjaxRequest(myAppContext+'/playlist/updateModified', {'selectedObjects' : playlistGridCRUD.getObjects()});
  						}
  					}
  				}
  			}		
  		]
	});

	return {
		getComponent: function() {
			return playlistGridCRUD;
		}
	}
}
