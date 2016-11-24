FloorCrud = function() {
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: FLOOR_NAME,
			dataIndex: 'name',
			sortable: true,
			sortable: false,
			align: 'left'
		}
		,{
			header: FLOOR_LABEL,
			dataIndex: 'label',
			sortable: true,
			sortable: false,
			align: 'left'
		}
		,{
			header: FLOOR_IMAGE,
			dataIndex: 'image',
			sortable: false,
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
					id: 'floorContents['+i+'].languageId',
					name: 'viewBean.floorContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'floorContents['+i+'].languageCode',
					name: 'viewBean.floorContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'floorContents['+i+'].id',
					name: 'viewBean.floorContents['+i+'].id'
				}
				,{
		  			xtype: 'textfield',
		  			fieldLabel: FLOOR_NAME,
		  			allowBlank: true,
		  			id: 'floorContents['+i+'].name',
					name: 'viewBean.floorContents['+i+'].name',
		  		}
				/*,{
		  			xtype: 'textfield',
		  			fieldLabel: FLOOR_LABEL,
		  			allowBlank: true,
		  			id: 'floorContents['+i+'].label',
					name: 'viewBean.floorContents['+i+'].label',
		  		}*/
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
		id: 'tabPanelFloor',
		style: 'margin-bottom: 10px;',
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
			id: 'buildingId',
			name: 'viewBean.buildingId'
		}
		,{
			xtype: 'hidden',
			id: 'name',
			name: 'viewBean.name'
		}
		/*,{
			xtype: 'hidden',
			id: 'label',
			name: 'viewBean.label',
			maxLength: 2
		}*/
		,tabPanel
		,{
			xtype: 'textfield',
			fieldLabel: FLOOR_LABEL,
			id: 'label',
			name: 'viewBean.label',
			allowBlank: false,
			maxLength: 2
		}
		,{
			xtype: 'fileuploadfield',
			fieldLabel: FLOOR_IMAGE,
			allowBlank: true,
			id: 'image',
			name: 'viewBean.image',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			previewAction: function() {
				if (checkFileExtension('image', 'image-swf')) {
					var mask = new Ext.LoadMask('form-panel-floor', {});
					mask.show();
					
					var submitFormFn = function(form, action) {
						mask.hide();
						if (action.response) {
							var jsonResponse = Ext.util.JSON.decode(action.response.responseText);
							if (jsonResponse.status == 200) { // OK response
								$.fancybox( {href : myAppContext+jsonResponse.datas[0]} );
							} else {
			                	HurryApp.Utils.checkResponseErrors(action.response);
							}
						}
					}
					
					Ext.getCmp('form-panel-floor').getForm().submit({
						url: myAppContext+'/floor/preview',
						params: {},
						failure: submitFormFn,
						success: submitFormFn
					});
				}
			},
			fileType: 'image-swf'
		}
		,{
			xtype: 'panel',
			border: false,
			html: FLOOR_IMAGE_TOOLTIP,
			bodyCssClass: 'ha-plain-panel',
			cls: 'ha-field-help',
			style: 'vertical-align: top; padding-left: 55px; overflow: hidden;'
		}
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}
	];
	
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelFloor');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}


	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-floor').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/floor/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-floor').getForm().setValues(datas);
			},
			'tabPanelFloor'
		);
	};

	//-----------------------------------------------------------------------------
	// Floors CRUD
	//-----------------------------------------------------------------------------
	var floorGridCRUD = new HurryApp.GridCRUD({
		type: 'floor',
		region: 'center',
		split: true,
		width: '30%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/floor/load',
		urlSave: myAppContext+'/floor/save',
		urlEdit: myAppContext+'/floor/edit',
		urlDelete: myAppContext+'/floor/delete',
		loadOnStartup: false,

		title:               FLOOR_LISTE,
		formWindowTitle:     FLOOR_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    FLOOR_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: FLOOR_TOOLTIP_DELETE,
		labelDeleteQuestion: FLOOR_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   FLOOR_MESSAGE_DELETE_ADVERT,
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
			
			hasPageChanged = false;
		},
		createFormCallback: initTabs,
		beforeSubmit: function() {
			if (this.getForm().findField('id').getValue() == '') {
				this.getForm().setValues({
					'viewBean.index': floorGridCRUD.store.getCount()
				});
			}
		},
		menuButtons: [
			{
				id: 'upButton-floor',
				text: FLOOR_BUTTON_UP,
				tooltip: FLOOR_BUTTON_UP_TOOLTIP,
				iconCls: 'tool-up',
				handler: function() {
					var selectedRecord = floorGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = floorGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex > 0) {
							floorGridCRUD.store.remove(selectedRecord);
							floorGridCRUD.store.insert(selectedRecordIndex-1, selectedRecord);
							floorGridCRUD.getSelectionModel().selectRow(selectedRecordIndex-1);
							for (var i=0, n=floorGridCRUD.store.data.items.length; i<n; i++) {
								floorGridCRUD.store.data.items[i].data.index = n - 1 - i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/floor/updateModified', {'selectedObjects' : floorGridCRUD.getObjects()});
						}
					}
				}
			}		
			,{
				id: 'downButton-floor',
				text: FLOOR_BUTTON_DOWN,
				tooltip: FLOOR_BUTTON_DOWN_TOOLTIP,
				iconCls: 'tool-down',
				handler: function() {
					var selectedRecord = floorGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = floorGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex < floorGridCRUD.store.getCount()-1) {
							floorGridCRUD.store.remove(selectedRecord);
							floorGridCRUD.store.insert(selectedRecordIndex+1, selectedRecord);
							floorGridCRUD.getSelectionModel().selectRow(selectedRecordIndex+1);
							for (var i=0, n=floorGridCRUD.store.data.items.length; i<n; i++) {
								floorGridCRUD.store.data.items[i].data.index = n - 1 - i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/floor/updateModified', {'selectedObjects' : floorGridCRUD.getObjects()});
						}
					}
				}
			}		
		],
		
		createFields: fields,
		
		formWindowWidth:    400,
		formWindowMinWidth: 380,
		formLabelWidth:     50,
		
		displayPagingBar: false,
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT
	});

	return {
		getComponent: function() {
			return floorGridCRUD;
		}
	}
}
