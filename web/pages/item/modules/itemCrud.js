ItemCrud = function() {
	var TAB_PANEL_WIDTH = 365;
	var INNER_PANEL_WIDTH = 402;
	var FIELD_WIDTH = 200;
	
	var ITEM_TYPE_ID_LOCATION = 1;
	var ITEM_TYPE_ID_ACCESS = 2;
	var ITEM_TYPE_ID_OTHER = 3;
	var ITEM_TYPE_ID_WEBVIEW = 4;
	var ITEM_TYPE_ID_TERMINAL = 7;
	
	var form;

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: ITEM_ICON,
			dataIndex: 'visual',
			sortable: false,
			align: 'left',
			width: 30,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				if (record.data.itemTypeId == ITEM_TYPE_ID_WEBVIEW) {
					return '<img src="'+myAppContext+'/images/icons/item-webview-icon.png" width="16" height="16"/>';
				}
				else {
					return '<img src="'+myAppContext+record.data.visual+'" width="16" height="16"/>';
				}
			}			
		}
		,{
			id: 'name',
			header: ITEM_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: ITEM_ITEMTYPE,
			dataIndex: 'itemType',
			sortable: true,
			align: 'left',
			width: FIELD_WIDTH
		}
		,{
			header: ITEM_CATEGORY,
			dataIndex: 'category',
			sortable: true,
			align: 'left',
			width: FIELD_WIDTH
		}
		,{
			header: ITEM_SUBCATEGORY,
			dataIndex: 'subCategory',
			sortable: true,
			align: 'left',
			width: FIELD_WIDTH
		},{
			header: ITEM_KEYWORDS,
			dataIndex: 'keywords',
			sortable: false,
			align: 'left',
			width: FIELD_WIDTH + 100
		},{
			dataIndex: 'itemTypeId',
			hidden: true
		}
		/*
		,{
			header: ITEM_ADDRESS,
			dataIndex: 'address',
			sortable: true,
			align: 'left'
		}
		,{
			header: ITEM_HOURBEGIN,
			dataIndex: 'hourBegin',
			sortable: true,
			align: 'center'
		}
		,{
			header: ITEM_HOUREND,
			dataIndex: 'hourEnd',
			sortable: true,
			align: 'center'
		}
		*/
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var commonTabItems = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'hidden',
			id: 'itemTypeId',
			name: 'viewBean.itemTypeId'
		}
		/*
		,{
			xtype: 'combo',
			fieldLabel: ITEM_ITEMTYPE,
			id: 'itemTypeId',
			name: 'viewBean.itemType',
			hiddenName: 'viewBean.itemTypeId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.JsonStore({
				fields: ['id', 'name'],
				data : itemTypes
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			listeners: {
				'select': function(combo) {
					if (combo.getValue() != 1) {
						Ext.getCmp('tabCommon-location').hide();
					}
					else {
						Ext.getCmp('tabCommon-location').show();
					}
				}
			}
		}
		*/
		,{
			xtype: 'combo',
			fieldLabel: ITEM_CATEGORY,
			id: 'categoryId',
			name: 'viewBean.category',
			hiddenName: 'viewBean.categoryId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.JsonStore({
				fields: ['id', 'name'],
				data : [{id: null, name: '-'}].concat(categories)
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			editable: false,
			listeners: {
				'select': function(combo, newValue, oldValue) {
					var subCatCombo = Ext.getCmp('subCategoryId');
					if (combo.getValue()) {
						subCatCombo.store.load({
							params: {'viewBean.categoryId': combo.getValue()},
							callback: function() {
								subCatCombo.store.insert(0, new Ext.data.Record({id: null, name: '-'}))
							}
						});
						//Ext.getCmp('subCategoryId').store.baseParams = {'viewBean.categoryId': combo.getValue()};
					}
					else {
						subCatCombo.store.removeAll();
					}
					subCatCombo.clearValue();
				}
			}
		}
		,{
			xtype: 'combo',
			fieldLabel: ITEM_SUBCATEGORY,
			id: 'subCategoryId',
			name: 'viewBean.subCategory',
			hiddenName: 'viewBean.subCategoryId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({url: myAppContext+'/subCategory/load'}),
				reader: new Ext.data.JsonReader({
					root: 'datas',
					totalProperty: 'count',
					id: 'id'
				},
				['id', 'name'])
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus: true,
			editable: false
		}
		/*,{
			xtype: 'combo',
			fieldLabel: ITEM_ZONE,
			id: 'zoneId',
			name: 'viewBean.zone',
			hiddenName: 'viewBean.zoneId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.JsonStore({
				fields: ['id', 'name'],
				data : [{id: null, name: '-'}].concat(zones)
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus: true,
			editable: false
		}*/
		,{
			xtype: 'panel',
			id: 'tabCommon-location',
			layout: 'form',
			width: INNER_PANEL_WIDTH,
			//height: 330,
			border: false,
			hidden: false,
			defaults: {width: FIELD_WIDTH},
			items: [
				{
					xtype: 'fileuploadfield',
					fieldLabel: ITEM_ICON,
					allowBlank: true,
					id: 'logo',
					name: 'viewBean.logo',
					buttonText: '',
					buttonCfg: {
						iconCls: 'tool-add-image'
					},
					fileType: 'image'
				}
				,{
					xtype: 'panel',
					border: false,
					html: ITEM_ICON_TOOLTIP,
					bodyCssClass: 'ha-plain-panel',
					cls: 'ha-field-help',
					width: 400,
					style: 'vertical-align: top; padding-left: 105px; padding-bottom: 5px; overflow: hidden;'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_ADDRESS,
					allowBlank: true,
					id: 'address',
					name: 'viewBean.address'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_PHONENUMBER,
					allowBlank: true,
					id: 'phoneNumber',
					name: 'viewBean.phoneNumber'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_EMAIL,
					allowBlank: true,
					id: 'email',
					name: 'viewBean.email',
					vtype: 'email'
				}
				,{
					xtype: 'timefield',
					//format: 'H:i',
					fieldLabel: ITEM_HOURBEGIN,
					allowBlank: true,
					id: 'hourBegin',
					name: 'viewBean.hourBegin',
					editable: false,
					value: ITEM_HOURBEGIN_INIT
				}
				,{
					xtype: 'timefield',
					//format: 'H:i',
					fieldLabel: ITEM_HOUREND,
					allowBlank: true,
					id: 'hourEnd',
					name: 'viewBean.hourEnd',
					editable: false,
					value: ITEM_HOUREND_INIT
				}
				,{
					xtype: 'hidden',
					id: 'photos',
					name: 'viewBean.photos'
				}
				,{
					xtype: 'hidden',
					id: 'photosDir',
					name: 'viewBean.photosDir'
				}
				,{
					xtype: 'imagegallery',
					id: 'photosGallery',
					formPanelId: 'form-panel-item',
					title: ITEM_PHOTOS,
					tooltip: ITEM_PHOTOS_TOOLTIP_MAX,
					height: 140,
					width: INNER_PANEL_WIDTH,
					imageWidth: 96,
					imageHeight: 74,
					multiSelect: true,
					urlDelete: myAppContext+'/imageGallery/delete'
				}
			]
		}
		,{
			xtype: 'panel',
			id: 'tabCommon-notLocation',
			layout: 'form',
			width: INNER_PANEL_WIDTH,
			//height: 330,
			border: false,
			hidden: true,
			defaults: {width: FIELD_WIDTH},
			items: [
				{
					xtype: 'iconcombo',
					fieldLabel: ITEM_ICON,
					id: 'iconId',
					name: 'viewBean.icon',
					hiddenName: 'viewBean.iconId',
					valueField: 'id',
					displayField: 'name',
					store: new Ext.data.JsonStore({
						fields: ['id', 'name', 'path']
					}),
					displayMethod: 'img',
					typeAhead: true,
					mode: 'local',
					triggerAction: 'all',
					selectOnFocus:true,
					editable: false
				}
			]
		}
		,{
			xtype: 'panel',
			id: 'tabCommon-access',
			layout: 'form',
			width: INNER_PANEL_WIDTH,
			//height: 330,
			border: false,
			hidden: true,
			defaults: {width: FIELD_WIDTH},
			items: [
				/*{
					xtype: 'radiogroup',
					id: 'type',
					columns: 2,
					fieldLabel: ITEM_ACCESS_TYPE,
					items: [
						{boxLabel: ITEM_ACCESS_TYPE_DOOR,  name: 'viewBean.type', inputValue: 1, checked: true}
						,{boxLabel: ITEM_ACCESS_TYPE_STAIRS, name: 'viewBean.type', inputValue: 2}
						,{boxLabel: ITEM_ACCESS_TYPE_ESCALATOR,  name: 'viewBean.type', inputValue: 3}
						,{boxLabel: ITEM_ACCESS_TYPE_ELEVATOR,  name: 'viewBean.type', inputValue: 4}
		            ]
				}
				,*/{
					xtype: 'checkbox',
					fieldLabel: ITEM_DISABLEDACCESS,
					id: 'disabledAccess',
					name: 'viewBean.disabledAccess',
					inputValue: true
				}
			]
		}
	];

	// Common tab
	var tabs = [{
		xtype: 'panel',
		title: ITEM_TAB_COMMON,
		id: 'tabCommon',
		layout: 'form',
		bodyStyle: 'padding:10px;',
		width: TAB_PANEL_WIDTH,
		//height: 360,
		defaults: {width: FIELD_WIDTH},
		items: commonTabItems
	}];

	// Language tabs
	for (var i=0; i<languages.length; i++) {
		tabs.push({
			xtype: 'panel',
			title: languages[i].name,
			id: 'tab'+languages[i].flag,
			layout: 'form',
			bodyStyle: 'padding:10px;',
			iconCls: 'ux-flag-'+languages[i].flag,
			width: TAB_PANEL_WIDTH,
			//height: 360,
			defaults: {width: FIELD_WIDTH},
			autoShow: true,
			items: [
			    {
					xtype: 'hidden',
					id: 'itemContents['+i+'].languageId',
					name: 'viewBean.itemContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'itemContents['+i+'].languageCode',
					name: 'viewBean.itemContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'itemContents['+i+'].id',
					name: 'viewBean.itemContents['+i+'].id'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_NAME,
					allowBlank: true,
					id: 'itemContents['+i+'].name',
					name: 'viewBean.itemContents['+i+'].name',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							itemCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'htmleditor',
					fieldLabel: ITEM_DESCRIPTION,
					allowBlank: true,
					height: (languages.length>1)?175:270, 
					id: 'itemContents['+i+'].description',
					name: 'viewBean.itemContents['+i+'].description',
					enableFont: false,
					enableFontSize: false,
					enableAlignments: false,
					i: i,
					listeners: { 'sync' :  function () {
							itemCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_OPENINGDAYS,
					allowBlank: true,
					id: 'itemContents['+i+'].openingDays',
					name: 'viewBean.itemContents['+i+'].openingDays',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							itemCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_KEYWORDS,
					allowBlank: true,
					id: 'itemContents['+i+'].keywords',
					name: 'viewBean.itemContents['+i+'].keywords',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							itemCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'textfield',
					value: ITEM_KEYWORDS_COMMAS,
					readOnly: true,
					id: 'itemContents['+i+'].keywordsTooltip',
					cls: 'ha-field-help',
					style: 'background-image:url(""); background-color:#f0f0f0; border-color:#F0F0F0; height:14px; padding-top:0px;'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ITEM_URL,
					allowBlank: true,
					id: 'itemContents['+i+'].url',
					name: 'viewBean.itemContents['+i+'].url'
				},
				{
					xtype: 'panel',
					id: 'separator-translate['+i+']',
					height: 1,
					width: '100%',
					border: false,
					style: 'margin-top: 10px; border-top: 1px solid #D0D0D0; border-bottom: 1px solid #ffffff;'
				}
				,{
					xtype: 'panel',
					id: 'help-translate['+i+']',
					border: false,
					html: '<div><span id="translateTooltipTitle-'+i+'" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
					style: 'margin: 10px auto 0 385px; font-weight: bold;',
					i: i,
					listeners: {'afterrender': function() {translateTooltip('translateTooltipTitle-'+this.i+'');}}
				}
				,{
					xtype: 'button',
					id: 'button-translate['+i+']',
					cls: 'button-translate',
					text: ITEM_TRANSLATE,
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
		id: 'tabPanelItem',
		//layout: 'border',
		plain: true,
		width: TAB_PANEL_WIDTH,
		height: 410,
		minTabWidth: 80,
		resizeTabs: true,
		enableTabScroll:true,
		autoScroll: true,
		animScroll: false,
		scrollDuration: .35,
		activeTab: 0,
		deferredRender: false,
		anchor: '100%',
		bufferResize: false,
		items: tabs
	};

	//-----------------------------------------------------------------------------
	// Menu buttons
	//-----------------------------------------------------------------------------
	var menuButtons = [];
	for (var i=0; i<itemTypes.length; i++) {
		if (itemTypes[i].id == 4) {
			menuButtons.push('|');
		}
		menuButtons.push({
			text: COMMON_ADD+' '+itemTypes[i].name,
			iconCls: 'item-'+itemTypes[i].id,
			itemTypeIndex: i,
			handler: function() {
				var itemTypeId = itemTypes[this.itemTypeIndex].id;
				
				// Check categories and icons
				if (itemTypeId == ITEM_TYPE_ID_LOCATION && categories.length == 0) {
					HurryApp.MessageUtils.showInfo(ITEM_MESSAGE_CREATE_CATEGORIES+' '+itemTypes[this.itemTypeIndex].name.toLowerCase());
				}
				else if ((itemTypeId == ITEM_TYPE_ID_ACCESS || itemTypeId == ITEM_TYPE_ID_OTHER || itemTypeId == ITEM_TYPE_ID_WEBVIEW)
						 && (categories.length == 0 || iconsCommon.length+iconsProject.length == 0)) {
					HurryApp.MessageUtils.showInfo(ITEM_MESSAGE_CREATE_CATEGORIES_ICONS+' '+itemTypes[this.itemTypeIndex].name.toLowerCase());
				}
				// Display form
				else {
					itemGridCRUD.createEntity();
					itemGridCRUD.formWindow.setTitle(itemTypes[this.itemTypeIndex].name);
					initFields(itemTypeId);
					
					var dir = (new Date()).format('U')+window.userId;
					Ext.getCmp('form-panel-item').getForm().setValues({
						'viewBean.itemTypeId': itemTypeId,
						'viewBean.photosDir': dir
					});
					
					var tabPanel = Ext.getCmp('tabPanelItem');
					tabPanel.animScroll = true;
					
					Ext.getCmp('photosGallery').dir = 'project-exports/'+window.projectKey+'/items/'+dir;
				}
			}
		});
	};

	menuButtons.push('|');
	menuButtons.push({
		id: 'cloneButton-playlist',
		text: ITEM_BUTTON_CLONE,
		tooltip: ITEM_BUTTON_CLONE_TOOLTIP,
		iconCls: 'tool-clone',
		handler: function() {
			var selectedRecord = itemGridCRUD.getSelectionModel().getSelections();
			if(selectedRecord && selectedRecord.length == 1) {
				HurryApp.Utils.sendAjaxRequest(
					myAppContext+'/item/copy',
					{
						"viewBean.id": selectedRecord[0].id, 
						"viewBean.photosDir" : (new Date()).format('U')+window.userId
					},
					function(jsonResponse) {
						itemGridCRUD.getStore().load();
						itemGridCRUD.editEntity(jsonResponse.datas[0].id);
					},
					"",
					function() {
						Ext.MessageBox.alert('', AJAX_FAILURE);
					}
				);
			}
			else {
				HurryApp.MessageUtils.showError("Please select one item");
			}
		}
	});
	menuButtons.push({
		text: COMMON_DELETE,
		tooltip: ITEM_TOOLTIP_DELETE,
		iconCls: 'tool-remove',
		handler: function() {
			itemGridCRUD.deleteEntity();
		}
	});

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: COMMON_SEARCH,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Util functions
	//-----------------------------------------------------------------------------
	// Init fields
	var initFields = function(itemTypeId) {
		if (itemTypeId == ITEM_TYPE_ID_LOCATION) { // Location
			Ext.getCmp('tabCommon-location').show();
			Ext.getCmp('tabCommon-notLocation').hide();
			Ext.getCmp('tabCommon-access').hide();

			for (var i = 0; i < languages.length; i++) {
				Ext.getCmp('itemContents['+i+'].url').el.dom.parentNode.parentNode.style.display = 'none';
			}
		}
		else if (itemTypeId == ITEM_TYPE_ID_ACCESS) { // Access
			Ext.getCmp('tabCommon-location').hide();
			Ext.getCmp('tabCommon-notLocation').show();
			Ext.getCmp('tabCommon-access').show();

			for (var i = 0; i < languages.length; i++) {
				Ext.getCmp('itemContents['+i+'].openingDays').el.dom.parentNode.parentNode.style.display = 'none';
				Ext.getCmp('itemContents['+i+'].url').el.dom.parentNode.parentNode.style.display = 'none';
			}
		}
		else if (itemTypeId == ITEM_TYPE_ID_OTHER) { // FUTO
			Ext.getCmp('tabCommon-location').hide();
			Ext.getCmp('tabCommon-notLocation').show();
			Ext.getCmp('tabCommon-access').hide();

			for (var i = 0; i < languages.length; i++) {
				Ext.getCmp('itemContents['+i+'].openingDays').el.dom.parentNode.parentNode.style.display = 'none';
				Ext.getCmp('itemContents['+i+'].url').el.dom.parentNode.parentNode.style.display = 'none';
			}
		}
		else if (itemTypeId == ITEM_TYPE_ID_WEBVIEW) { // Web view
			Ext.getCmp('tabCommon-location').hide();
			Ext.getCmp('tabCommon-notLocation').hide();
			Ext.getCmp('tabCommon-access').hide();

			for (var i = 0; i < languages.length; i++) {
				Ext.getCmp('itemContents['+i+'].description').el.dom.parentNode.parentNode.parentNode.style.display = 'none';
				Ext.getCmp('itemContents['+i+'].keywords').el.dom.parentNode.parentNode.style.display = 'none';
				Ext.getCmp('itemContents['+i+'].keywordsTooltip').el.dom.parentNode.parentNode.style.display = 'none';
				Ext.getCmp('itemContents['+i+'].openingDays').el.dom.parentNode.parentNode.style.display = 'none';
			}
		}
		
		// Icons
		var icons = [];
		for (var i=0; i<iconsCommon.length; i++) {
			if (iconsCommon[i].typeId == itemTypeId) {
				icons.push(iconsCommon[i]);
			}
		};
		Ext.getCmp('iconId').store.loadData(icons.concat(iconsProject));
	}

	// Init tabs
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelItem');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
		
		// Reinit sub-categories
		Ext.getCmp('subCategoryId').store.removeAll();
	}
	
	// Translation
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-item').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		var gridData = new Array();
		
		for(var i = 0; i < languages.length; ++i) {
			if(	languages[i].id === sourceLanguageId || ( params["viewBean.itemContents["+ i +"].description"] === "" && 
				params["viewBean.itemContents["+ i +"].keywords"] === "" &&
				params["viewBean.itemContents["+ i +"].name"] === "" &&
				params["viewBean.itemContents["+ i +"].openingDays"] === "" )) {
				continue;
			}
				
			gridData.push([languages[i].flag, languages[i].name, i]);
		}
		
		if(gridData.length === 0) {
			HurryApp.Utils.sendAjaxRequest(
				myAppContext+'/item/translate',
				params,
				function(jsonResponse) {
					var datas = jsonResponse.datas[0];
					Ext.getCmp('form-panel-item').getForm().setValues(datas);
				},'tabPanelItem',
				function() {
					Ext.MessageBox.alert('', AJAX_FAILURE);
				});
			return;
		}
		
		var checkBoxSelMod = new Ext.grid.CheckboxSelectionModel();
		var store = new Ext.data.ArrayStore({
	        fields: [
	           {name: 'visual'},
	           {name: 'name'},
	           {name: 'index'},
	        ]
	    });
		var grid = new Ext.grid.GridPanel({
			store : store,
			columns : [
			    checkBoxSelMod,
			    {
			    	menuDisabled:true,
					header: "",
					dataIndex: 'visual',
					sortable: false,
					align: 'left',
					width: 30,
					renderer: function(value, metaData, record, rowIndex, colIndex, store) {
						return "<span style='display:block;width:16px;height:11px;margin:3px' class='ux-flag-" + value + "'/>"
					}
				},{
					menuDisabled:true,
			        header : "",
			        sortable : false,
			        dataIndex : 'name'
				},{
					dataIndex: 'index',
					hidden: true
				}
			],
            sm:checkBoxSelMod,
            autoExpandColumn:2
		});
			
		var popup = new Ext.Window({
			modal: true,
	        title: '',
	        height: 300,
	        width: 450,
	        layout: 'fit',
	        tbar:
	        [
                {
                	style: 'display:block;margin:3px;',
                	xtype: "label",
                	text:  ITEM_LANGUAGE_OVERRIDE_TIP
                }	
	        ],
	        items: [grid],
			buttons: [
			    {
					text: 'OK',
					handler: function() {
						var selections = checkBoxSelMod.getSelections();
						for(var i = 0, n = selections.length; i < n; ++i) {
							var index = selections[i].data.index;
							params["viewBean.itemContents["+ index +"].description"] = "";
							params["viewBean.itemContents["+ index +"].keywords"] = "";
							params["viewBean.itemContents["+ index +"].name"] = "";
							params["viewBean.itemContents["+ index +"].openingDays"] = "";
						}
						HurryApp.Utils.sendAjaxRequest(
							myAppContext+'/item/translate',
							params,
							function(jsonResponse) {
								var datas = jsonResponse.datas[0];
								Ext.getCmp('form-panel-item').getForm().setValues(datas);
							},'tabPanelItem',
							function() {
								Ext.MessageBox.alert('', AJAX_FAILURE);
							});
						popup.close();
					}
				},{
					text: 'Cancel',
					handler: function() {
						popup.close();
					}
				}
			]
	    });
		store.loadData(gridData);
		popup.show();
		
//		HurryApp.Utils.sendAjaxRequest(
//			myAppContext+'/item/translate',
//			params,
//			function(jsonResponse) {
//				var datas = jsonResponse.datas[0];
//				Ext.getCmp('form-panel-item').getForm().setValues(datas);
//			},
//			'tabPanelItem'
//		);
	}

	//-----------------------------------------------------------------------------
	// Items CRUD
	//-----------------------------------------------------------------------------
	var itemGridCRUD = new HurryApp.GridCRUD({
		type: 'item',
		region: 'center',
		width: '100%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/item/load',
		urlSave: myAppContext+'/item/save',
		urlEdit: myAppContext+'/item/edit',
		urlDelete: myAppContext+'/item/delete',
		defaultParams: {'viewBean.projectId': window.projectId},
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			this.form = form;
			
			// Popup's title according to item type
			itemGridCRUD.formWindow.setTitle(datas.itemType);

			// Init fields
			initFields(datas.itemTypeId);

			if (datas.categoryId) {
				var subCatCombo = Ext.getCmp('subCategoryId');
				subCatCombo.store.load({
					params: {'viewBean.categoryId': datas.categoryId},
					callback: function() {
						// Populate form
						form.setValues(datas);
						subCatCombo.store.insert(0, new Ext.data.Record({id: null, name: '-'}))
					}
				});
			}
			else {
				// Populate form
				form.setValues(datas);
			}

			// Photos
			var photosGallery = Ext.getCmp('photosGallery');
			if (datas.photos) {
				photosGallery.imageDataView.store.loadData(datas.photos);
			}
			photosGallery.dir = 'project-exports/'+window.projectKey+'/items/'+datas.photosDir;
		},
		beforeSubmit: function(form, action) {
			// Set photo paths
			var photos = '';
			var items = Ext.getCmp('imagesDataViewphotosGallery').store.data.items;
			for (var i=0; i<items.length; i++) {
				if (photos != '') {
					photos += ';';
				}
				photos += items[i].data.file;
			};
			Ext.getCmp('photos').setValue(photos);
		},
		createFormCallback: initTabs,

		title:               ITEM_LISTE,
		formWindowTitle:     ITEM_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    ITEM_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: ITEM_TOOLTIP_DELETE,
		labelDeleteQuestion: ITEM_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   ITEM_MESSAGE_DELETE_ADVERT,
		
		createFields: [tabPanel],
		searchFields: [nameSearch],
		menuButtons: menuButtons,
		displayAddButton: false,
		displayDeleteButton: false,
		canDelete: true,
		
		formWindowWidth:    450,
		formWindowMinWidth: 392,
		formLabelWidth:     100,
		
		displayPagingBar: true,
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT
	});

	return {
		getComponent: function() {
			return itemGridCRUD;
		}
	}
}
