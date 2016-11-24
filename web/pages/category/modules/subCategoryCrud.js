SubCategoryCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: SUBCATEGORY_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		/*,{
			header: SUBCATEGORY_COLOR,
			dataIndex: 'color',
			sortable: false,
			align: 'left',
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return '<div style="width:16px;height:16px;background-color:#'+record.data.color+'"/>';
			}			
		}*/
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var commonTabFields = [
		{
			xtype: 'hidden',
			id: 'categoryId',
			name: 'viewBean.categoryId'
		}
		,{
			xtype: 'colorfield',
			fieldLabel: SUBCATEGORY_COLOR,
			allowBlank: true,
			id: 'color',
			name: 'viewBean.color'
		}
	];

	var tabs = [/*{
		xtype: 'panel',
		title: SUBCATEGORY_TAB_COMMON,
		id: 'tabCommon',
		layout: 'form',
		bodyStyle: 'padding:10px;',
		width: 365,
		//height: 350,
		defaults: {width: 220},
		items: commonTabFields
	}*/];

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
				i == 0 ? {
					xtype: 'hidden',
					id: 'categoryId',
					name: 'viewBean.categoryId'
				} : {
					xtype: 'hidden',
					id: 'mock',
					name: 'mock'
				}
				,{
					xtype: 'hidden',
					id: 'subCategoryContents['+i+'].languageId',
					name: 'viewBean.subCategoryContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'subCategoryContents['+i+'].languageCode',
					name: 'viewBean.subCategoryContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'subCategoryContents['+i+'].id',
					name: 'viewBean.subCategoryContents['+i+'].id'
				}
				,{
					xtype: 'textfield',
					fieldLabel: SUBCATEGORY_NAME,
					allowBlank: true,
					id: 'subCategoryContents['+i+'].name',
					name: 'viewBean.subCategoryContents['+i+'].name',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							subCategoryCheckFields(this.i);
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
					html: '<div><span id="translateTooltipTitle-sub-'+i+'" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
					style: 'margin: 10px auto 0 320px; font-weight: bold;',
					i: i,
					listeners: {'afterrender': function() {translateTooltip('translateTooltipTitle-sub-'+this.i+'');}}
				}
				,{
					xtype: 'button',
					id: 'button-translate['+i+']',
					cls: 'button-translate',
					text: SUBCATEGORY_TRANSLATE,
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
		id: 'tabPanelSubCategory',
		//layout: 'border',
		plain: true,
		width: 365,
		height: 160,
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
		id: 'nameSearch2',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Util functions
	//-----------------------------------------------------------------------------
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelSubCategory');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-subCategory').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/subCategory/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-subCategory').getForm().setValues(datas);
			},
			'tabPanelSubCategory'
		);
	}

	//-----------------------------------------------------------------------------
	// SubCategories CRUD
	//-----------------------------------------------------------------------------
	var subCategoryGridCRUD = new HurryApp.GridCRUD({
		id: 'subCategoryGridCRUD',
		type: 'category',
		idExtension: 'subCategory',
		region: 'east',
		width: '50%',
		split: true,
		autoHeight: false,
		enableSingleSelect: true,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/subCategory/load',
		urlSave: myAppContext+'/subCategory/save',
		urlEdit: myAppContext+'/subCategory/edit',
		urlDelete: myAppContext+'/subCategory/delete',
		loadOnStartup: false,
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
		},
		createFormCallback: initTabs,
		
		// Transform a category to a sub-category
		fnBeforeTransferItems: function(data) {
			var categoryId = data.selections[0].data.id;
			var destCategoryId = subCategoryGridCRUD.defaultParams['viewBean.categoryId'];
			
			if (destCategoryId) {
				if (destCategoryId != categoryId) {
					HurryApp.Utils.sendAjaxRequest(
						myAppContext+'/subCategory/transformCategoryToSubCategory',
						{
							'viewBean.id': categoryId,
							'destCategoryId': destCategoryId
						},
						function() {
							var categoryGridCRUD = Ext.getCmp('categoryGridCRUD');
							categoryGridCRUD.getSelectionModel().selectRow(HurryApp.Utils.getItemIndex(HurryApp.Utils.toJsonDatas(categoryGridCRUD.store.data.items), destCategoryId));
							subCategoryGridCRUD.loadDataStore();
						}
					);
				}
				else {
					HurryApp.MessageUtils.showInfo(CATEGORY_MESSAGE_TRANSFORM_ITSELF);
					return false;				
				}
			}
			else {
				HurryApp.MessageUtils.showInfo(CATEGORY_MESSAGE_TRANSFORM);
				return false;				
			}
		},

		title:               SUBCATEGORY_LISTE,
		formWindowTitle:     SUBCATEGORY_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    SUBCATEGORY_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: SUBCATEGORY_TOOLTIP_DELETE,
		labelDeleteQuestion: SUBCATEGORY_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   SUBCATEGORY_MESSAGE_DELETE_ADVERT,
		
		createFields: [tabPanel],
		searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return subCategoryGridCRUD;
		}
	}
}
