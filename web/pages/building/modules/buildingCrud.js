BuildingCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: BUILDING_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	
	var tabs = [];

	// Tab languages
	for (var i=0; i<languages.length; i++) {
		var top = (languages.length>1)?10:500;
		tabs.push({
			xtype: 'panel',
			title: languages[i].name,
			id: 'tab'+languages[i].flag,
			layout: 'form',
			bodyStyle: 'padding:10px;',
			iconCls: 'ux-flag-'+languages[i].flag,
			width: 365,
			//height: 350,
			defaults: {width: 220},
			autoShow: true,
			items: [
				{
					xtype: 'hidden',
					id: 'buildingContents['+i+'].languageId',
					name: 'viewBean.buildingContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'buildingContents['+i+'].languageCode',
					name: 'viewBean.buildingContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'buildingContents['+i+'].id',
					name: 'viewBean.buildingContents['+i+'].id'
				}
				,{
		  			xtype: 'textfield',
		  			fieldLabel: BUILDING_NAME,
		  			allowBlank: true,
		  			id: 'buildingContents['+i+'].name',
					name: 'viewBean.buildingContents['+i+'].name',
		  		}
		  		,{
					xtype: 'panel',
					height: 1,
					width: '100%',
					border: false,
					style: 'margin-top: '+top+'px; border-top: 1px solid #D0D0D0; border-bottom: 1px solid #ffffff;'
				}
				,{
					xtype: 'panel',
					border: false,
					html: '<div><span id="translateTooltipTitle-'+i+'" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
					style: 'margin: 10px auto 0 320px; font-weight: bold;',
					i: i,
					listeners: {'afterrender': function() {translateTooltip('translateTooltipTitle-'+this.i+'');}}
				}
				,{
					xtype: 'button',
					id: 'button-translate['+i+']',
					cls: 'button-translate',
					text: CATEGORY_TRANSLATE,
					forceLayout: true,
					scale: 'large',
					allowBlank: true,
					style: 'margin: auto;',
					width: 200,
					langId: languages[i].id,
					tabIndex: i,
					handler: function() {translate(this.langId, this.tabIndex);},
					iconCls: 'ux-flag-'+languages[i].flag,
					preventBodyReset: true
				}
			]
		});
	};
	
	var tabPanel = {
		xtype: 'tabpanel',
		id: 'tabPanelBuilding',
		//layout: 'border',
		plain: true,
		width: 365,
		height: 180,
		minTabWidth: 80,
		resizeTabs: true,
		enableTabScroll:true,
		autoScroll: true,
		animScroll: false,
		scrollDuration: .35,
		activeTab: 0,
		deferredRender: false,
		anchor: '100%',
		items: tabs
	};
	
	
	var fields = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'hidden',
			id: 'name',
			name: 'viewBean.name'
		}
		,tabPanel
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}
	];
	
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelBuilding');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}


	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-building').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/building/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-building').getForm().setValues(datas);
			},
			'tabPanelBuilding'
		);
	};
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: BUILDING_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Buildings CRUD
	//-----------------------------------------------------------------------------
	var buildingGridCRUD = new HurryApp.GridCRUD({
		type: 'building',
		region: 'west',
		width: '40%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/building/load',
		urlSave: myAppContext+'/building/save',
		urlEdit: myAppContext+'/building/edit',
		urlDelete: myAppContext+'/building/delete',
		defaultParams: {'viewBean.projectId': window.projectId},

		title:               BUILDING_LISTE,
		formWindowTitle:     BUILDING_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    BUILDING_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: BUILDING_TOOLTIP_DELETE,
		labelDeleteQuestion: BUILDING_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   BUILDING_MESSAGE_DELETE_ADVERT,
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
			
			hasPageChanged = false;
		},
		createFormCallback: initTabs,
		beforeSubmit: function() {
			if (this.getForm().findField('id').getValue() == '') {
				this.getForm().setValues({
					'viewBean.index': buildingGridCRUD.store.getCount()
				});
			}
		},
		menuButtons: [
			{
				id: 'upButton-building',
				text: BUILDING_BUTTON_UP,
				tooltip: BUILDING_BUTTON_UP_TOOLTIP,
				iconCls: 'tool-up',
				handler: function() {
					var selectedRecord = buildingGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = buildingGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex > 0) {
							buildingGridCRUD.store.remove(selectedRecord);
							buildingGridCRUD.store.insert(selectedRecordIndex-1, selectedRecord);
							buildingGridCRUD.getSelectionModel().selectRow(selectedRecordIndex-1);
							for (var i=0; i<buildingGridCRUD.store.data.items.length; i++) {
								buildingGridCRUD.store.data.items[i].data.index = i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/building/updateModified', {'selectedObjects' : buildingGridCRUD.getObjects()});
						}
					}
				}
			}		
			,{
				id: 'downButton-building',
				text: BUILDING_BUTTON_DOWN,
				tooltip: BUILDING_BUTTON_DOWN_TOOLTIP,
				iconCls: 'tool-down',
				handler: function() {
					var selectedRecord = buildingGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = buildingGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex < buildingGridCRUD.store.getCount()-1) {
							buildingGridCRUD.store.remove(selectedRecord);
							buildingGridCRUD.store.insert(selectedRecordIndex+1, selectedRecord);
							buildingGridCRUD.getSelectionModel().selectRow(selectedRecordIndex+1);
							for (var i=0; i<buildingGridCRUD.store.data.items.length; i++) {
								buildingGridCRUD.store.data.items[i].data.index = i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/building/updateModified', {'selectedObjects' : buildingGridCRUD.getObjects()});
						}
					}
				}
			}		
		],
		
		createFields: fields,
		searchFields: [nameSearch],
		
		formWindowWidth:    400,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: false
	});

	return {
		getComponent: function() {
			return buildingGridCRUD;
		}
	}
}
