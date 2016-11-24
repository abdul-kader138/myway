ZoneCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: ZONE_ICON,
			dataIndex: 'visual',
			sortable: false,
			align: 'left',
			width: 30,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return '<img src="'+myAppContext+record.data.visual+'" width="16" height="16"/>';
			}			
		}
		,{
			id: 'name',
			header: ZONE_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: ZONE_COLOR,
			dataIndex: 'color',
			sortable: false,
			align: 'left',
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return '<div style="width:16px;height:16px;background-color:#'+record.data.color+';border:solid 1px #929292;"/>';
			}			
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------

	var commonTabItems = 
	[
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
	    ,{
	    	width: 230,
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
			width: 230,
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
  		,{
  			width: 230,
			xtype: 'colorfield',
			fieldLabel: ZONE_COLOR,
			allowBlank: true,
			id: 'color',
			name: 'viewBean.color'
		}
  		,{
  			width: 230,
			xtype: 'numberfield',
			fieldLabel: ZONE_TRANSPARENCY,
			allowBlank: true,
			maxValue: 100,
			minValue: 10,
			maxText: ZONE_TRANSPARENCY_MAX,
			minText: ZONE_TRANSPARENCY_MIN,
			emptyText: '40',
			id: 'transparency',
			name: 'viewBean.transparency'
		}
  		,{
			xtype: 'checkbox',
			fieldLabel: ZONE_ITEMSINZONE_CHECKBOX_FIELD_LABEL,
			boxLabel: ZONE_ITEMSINZONE_CHECKBOX_BOX_LABEL,
			inputValue: true,
			id: 'itemsInZone',
			name: 'viewBean.itemsInZone',
			//listeners: {'check': function(checkbox, checked) {
			//	}
			//}
		}
  		,{
  			width: 230,
			xtype: 'fileuploadfield',
			fieldLabel: ZONE_ICON,
			allowBlank: true,
			id: 'image',
			name: 'viewBean.image',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			fileType: 'image'
		}
  		/*,{
			xtype: 'label',
			text: 'Items:',
			
		}
  		,{
  			xtype: 'panel',
  			layout: 'hbox',
  			style: 'margin-top:10px',
			border: false,
			width: 445,
			height: 380,
			items: 
			[
			 	{
			 		id: 'leftItemSelectPanel',
			 		xtype: 'grid',
			 		style: 'background:white;',
			 		width: 170,
			 		height: 190,
			 		hideHeaders: true,
			 		store : new Ext.data.SimpleStore({
			 			autoLoad :false,
			 			url:myAppContext + '/item/query',
			 			fields:[{name: 'id', mapping:'id'},{name: 'visual', mapping:'visual'}, {name: 'name', mapping:'name'}],
			 			root: 'datas'
			 		}),
			 		autoExpandColumn: 'name',
			 		columns: 
			 			[
			 		         {
								dataIndex: 'visual',
								sortable: false,
								align: 'left',
								width: 30,
								renderer: function(value, metaData, record, rowIndex, colIndex, store) {
									return '<img src="'+myAppContext+record.data.visual+'" width="16" height="16"/>';
								}
							},
			 		        {
			 		        	id: 'name',
			 		            header: 'name',
			 		            sortable: true,
			 		            dataIndex: 'name'
			 		         },
			 		         
			 	        ]
			 	}
			 	,{
					xtype: 'panel',
					layout: {
				        type: 'vbox',       // Arrange child items vertically
				        align: 'center',    // Each takes up full width
				    },
					width: "65",
					height: 190,
					border: false,
					items: [
					    {
					    	xtype: "button",
					    	text: "Add",
							scale: 'small',
							width: 50,
							style: "margin-top:50px;",
							handler: function() {
								var selected = Ext.getCmp('leftItemSelectPanel').getSelectionModel().getSelections();
								Ext.getCmp('leftItemSelectPanel').getStore().remove(selected);
								Ext.getCmp('rightItemSelectPanel').getStore().add(selected);
								Ext.getCmp('rightItemSelectPanel').getStore().sort('name', 'ASC');
							},
					    }
						,{
					    	xtype: "button",
					    	text: "Remove",
							scale: 'small',
							width: 50,
							style: "margin-top:100px;",
							handler: function() {
								var selected = Ext.getCmp('rightItemSelectPanel').getSelectionModel().getSelections();
								Ext.getCmp('rightItemSelectPanel').getStore().remove(selected);
								Ext.getCmp('leftItemSelectPanel').getStore().add(selected);
								Ext.getCmp('leftItemSelectPanel').getStore().sort('name', 'ASC');
							},
					    }
					]
				},
				{
			 		id: 'rightItemSelectPanel',
			 		xtype: 'grid',
			 		style: 'background:white;',
			 		width: 170,
			 		height: 190,
			 		hideHeaders: true,
			 		store : new Ext.data.SimpleStore({
			 			autoLoad :false,
			 			url:myAppContext + '/item/query',
			 			fields:[{name: 'id', mapping:'id'}, {name: 'visual', mapping:'visual'}, {name: 'name', mapping:'name'}],
			 			root: 'datas'
			 		}),
			 		autoExpandColumn: 'name',
			 		columns: 
			 			[
			 		         {
								dataIndex: 'visual',
								sortable: false,
								align: 'left',
								width: 30,
								renderer: function(value, metaData, record, rowIndex, colIndex, store) {
									return '<img src="'+myAppContext+record.data.visual+'" width="16" height="16"/>';
								}
			 		         },
			 		         {
			 		        	 id: 'name',
			 		        	 header: 'name',
			 		        	 sortable: true,
			 		        	 dataIndex: 'name'
			 		         },
			 		         
			 	        ]
			 	}
			]
  		}*/
  		];
	
	var tabs = [{
		xtype: 'panel',
		title: ITEM_TAB_COMMON,
		id: 'tabCommon',
		layout: 'form',
		bodyStyle: 'padding:10px;',
		width: 445,
		//height: 360,
		defaults: {width: 297},
		items: commonTabItems
	}];

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
					id: 'zoneContents['+i+'].languageId',
					name: 'viewBean.zoneContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'zoneContents['+i+'].languageCode',
					name: 'viewBean.zoneContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'zoneContents['+i+'].id',
					name: 'viewBean.zoneContents['+i+'].id'
				}
				,{
					xtype: 'textfield',
					fieldLabel: ZONE_NAME,
					allowBlank: true,
					id: 'zoneContents['+i+'].name',
					name: 'viewBean.zoneContents['+i+'].name',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							zoneCheckFields(this.i);
						}
					}
				},
				{
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
		id: 'tabPanelZone',
		style: 'margin-bottom:10px;',
		plain: true,
		width: 445,
		height: 280,
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
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelZone');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-zone').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/zone/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-zone').getForm().setValues(datas);
			},
			'tabPanelZone'
		);
	}

	var selectedItem = [];
	//-----------------------------------------------------------------------------
	// Categories CRUD
	//-----------------------------------------------------------------------------
	var zoneGridCRUD = new HurryApp.GridCRUD({
		id: 'zoneGridCRUD',
		type: 'zone',
		idExtension: 'zone',
		region: 'east',
		width: '30%',
		autoHeight: false,
		enableSingleSelect: true,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/zone/load',
		urlSave: myAppContext+'/zone/save',
		urlEdit: myAppContext+'/zone/edit',
		urlDelete: myAppContext+'/zone/delete',
		loadOnStartup: false,
		
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
			
			//Ext.getCmp('leftItemSelectPanel').getStore().load();
			//Ext.getCmp('rightItemSelectPanel').getStore().load({params:{'viewbean.zoneId': datas.id}});
		},
		createFormCallback: function() {
			initTabs();
			//Ext.getCmp('leftItemSelectPanel').getStore().load({params:{viewbean.zoneId:null}});
			//Ext.getCmp('leftItemSelectPanel').getStore().load();
			
		},

		beforeSubmit: function() {
			var form = this.getForm();
			var i;
			for(i = 0; i < selectedItem.length; ++i) {
				selectedItem[i].remove();
			}
			
			i = 0;
			selectedItem = [];
			//Ext.getCmp('rightItemSelectPanel').getStore().each(function(record) {
			//	//var temp = {};
			//	//temp["viewBean.items["+i+"].id"] = record.data.id;
			//	/*form.add({
			//		  xtype: 'hidden',
			//		  name: "viewBean.items["+i+"].id",
			//		  value: record.data.id
			//		});*/
			//	
			//	selectedItem.push(form.el.createChild({tag:'input', type:'hidden', name:"viewBean.items["+i+"].id", value: record.data.id}));
			//	++i;
			//});
		},
		
		title:               ZONE_LISTE,
		formWindowTitle:     ZONE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    ZONE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: ZONE_TOOLTIP_DELETE,
		labelDeleteQuestion: ZONE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   ZONE_MESSAGE_DELETE_ADVERT,
		
		createFields: [tabPanel],
//		searchFields: [nameSearch],
		
		formWindowWidth:    450,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT,
		displayPagingBar: false
	});

	return {
		getComponent: function() {
			return zoneGridCRUD;
		}
	}
}
