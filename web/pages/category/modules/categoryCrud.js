CategoryCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: CATEGORY_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: CATEGORY_COLOR,
			dataIndex: 'color',
			sortable: false,
			align: 'left',
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return '<div style="width:16px;height:16px;background-color:#'+record.data.color+';border:solid 1px #929292;"/>';
				//return '<div style="width:16px;height:16px;background-color:#'+record.data.color+';border: 1px solid #000000!important;"/>';
			}			
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var commonTabFields = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'colorfield',
			fieldLabel: CATEGORY_COLOR,
			allowBlank: true,
			id: 'color',
			name: 'viewBean.color'
		}
	];

	var tabs = [{
		xtype: 'panel',
		title: CATEGORY_TAB_COMMON,
		id: 'tabCommon',
		layout: 'form',
		bodyStyle: 'padding:10px;',
		width: 365,
		//height: 350,
		defaults: {width: 220},
		items: commonTabFields
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
					id: 'categoryContents['+i+'].languageId',
					name: 'viewBean.categoryContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'categoryContents['+i+'].languageCode',
					name: 'viewBean.categoryContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'categoryContents['+i+'].id',
					name: 'viewBean.categoryContents['+i+'].id'
				}
				,{
					xtype: 'textfield',
					fieldLabel: CATEGORY_NAME,
					allowBlank: true,
					id: 'categoryContents['+i+'].name',
					name: 'viewBean.categoryContents['+i+'].name',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							categoryCheckFields(this.i);
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
		id: 'tabPanelCategory',
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
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Util functions
	//-----------------------------------------------------------------------------
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelCategory');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-category').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/category/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-category').getForm().setValues(datas);
			},
			'tabPanelCategory'
		);
	}

	//-----------------------------------------------------------------------------
	// Categories CRUD
	//-----------------------------------------------------------------------------
	var categoryGridCRUD = new HurryApp.GridCRUD({
		id: 'categoryGridCRUD',
		type: 'category',
		idExtension: 'category',
		region: 'center',
		width: '50%',
		autoHeight: false,
		enableSingleSelect: true,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/category/load',
		urlSave: myAppContext+'/category/save',
		urlEdit: myAppContext+'/category/edit',
		urlDelete: myAppContext+'/category/delete',
		defaultParams: {'viewBean.projectId': window.projectId},
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
		},
		createFormCallback: initTabs,
			
		// Transform a sub-category to a category
		fnBeforeTransferItems: function(data) {
			HurryApp.Utils.sendAjaxRequest(
				myAppContext+'/category/transformSubCategoryToCategory',
				{
					'viewBean.id': data.selections[0].data.id
				},
				function() {
					categoryGridCRUD.loadDataStore();
				}
			);
		},

		title:               CATEGORY_LISTE,
		formWindowTitle:     CATEGORY_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    CATEGORY_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: CATEGORY_TOOLTIP_DELETE,
		labelDeleteQuestion: CATEGORY_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   CATEGORY_MESSAGE_DELETE_ADVERT,
		
		createFields: [tabPanel],
		searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return categoryGridCRUD;
		}
	}
}
