PromotionCrud = function() {
	var TAB_PANEL_WIDTH = 365;
	var INNER_PANEL_WIDTH = 402;
	var FIELD_WIDTH = 297;

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: PROMOTION_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: PROMOTION_ITEM,
			dataIndex: 'item',
			sortable: true,
			align: 'left',
			width: 200
		}
		,{
			header: PROMOTION_START,
			dataIndex: 'start',
			sortable: true,
			align: 'center',
			width: 200
		}
		,{
			header: PROMOTION_END,
			dataIndex: 'end',
			sortable: true,
			align: 'center',
			width: 200
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var commonTabPromotions = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'combo',
			fieldLabel: PROMOTION_ITEM,
			id: 'itemId',
			name: 'viewBean.item',
			hiddenName: 'viewBean.itemId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.JsonStore({
				fields: ['id', 'name'],
				data : items
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus: true,
			editable: false
		}
		,{
			layout: 'hbox',
			width: 330,
			border: false,
			style: 'padding-bottom:5px;',
			items: [
				{
					xtype: 'label',
					text: PROMOTION_START+':',
					width: 105,
					style: 'padding-top:2px;'
				}
				,{
					xtype: 'datefield',
					allowBlank: true,
					id: 'start',
					name: 'viewBean.start',
					width: 100
				}
				,{
					xtype: 'label',
					style: 'padding-left:20px;'
				}
				,{
					xtype: 'timefield',
					//format: 'H:i',
					allowBlank: true,
					id: 'hourStart',
					name: 'viewBean.hourStart',
					width: 100,
					editable: false
				}
			]
		}
		,{
			layout: 'hbox',
			width: 330,
			border: false,
			style: 'padding-bottom:5px;',
			items: [
				{
					xtype: 'label',
					text: PROMOTION_END+':',
					width: 105,
					style: 'padding-top:2px;'
				}
				,{
					xtype: 'datefield',
					allowBlank: true,
					id: 'end',
					name: 'viewBean.end',
					width: 100
				}
				,{
					xtype: 'label',
					style: 'padding-left:20px;'
				}
				,{
					xtype: 'timefield',
					//format: 'H:i',
					allowBlank: true,
					id: 'hourEnd',
					name: 'viewBean.hourEnd',
					width: 100,
					editable: false
				}
			]
		}
		,{
			xtype: 'fileuploadfield',
			fieldLabel: PROMOTION_IMAGE,
			allowBlank: true,
			id: 'image',
			name: 'viewBean.image',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			fileType: 'image'
		}
		/*
		,{
			xtype: 'datefield',
			fieldLabel: PROMOTION_START,
			allowBlank: true,
			id: 'start',
			name: 'viewBean.start'
		}
		,{
			xtype: 'timefield',
			//format: 'H:i',
			fieldLabel: PROMOTION_HOURSTART,
			allowBlank: true,
			id: 'hourStart',
			name: 'viewBean.hourStart'
		}
		,{
			xtype: 'datefield',
			fieldLabel: PROMOTION_END,
			allowBlank: true,
			id: 'end',
			name: 'viewBean.end'
		}
		,{
			xtype: 'timefield',
			//format: 'H:i',
			fieldLabel: PROMOTION_HOUREND,
			allowBlank: true,
			id: 'hourEnd',
			name: 'viewBean.hourEnd'
		}
		*/
	];
	
	var tabs = [{
		xtype: 'panel',
		title: PROMOTION_TAB_COMMON,
		id: 'tabCommon',
		layout: 'form',
		bodyStyle: 'padding:10px;',
		width: TAB_PANEL_WIDTH,
		//height: 350,
		defaults: {width: FIELD_WIDTH},
		items: commonTabPromotions
	}];

	// Tab languages
	for (var i=0; i<languages.length; i++) {
		tabs.push({
			xtype: 'panel',
			title: languages[i].name,
			id: 'tab'+languages[i].flag,
			layout: 'form',
			bodyStyle: 'padding:10px;',
			iconCls: 'ux-flag-'+languages[i].flag,
			width: TAB_PANEL_WIDTH,
			//height: 350,
			defaults: {width: FIELD_WIDTH},
			autoShow: true,
			items: [
				{
					xtype: 'hidden',
					id: 'promotionContents['+i+'].languageId',
					name: 'viewBean.promotionContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'promotionContents['+i+'].languageCode',
					name: 'viewBean.promotionContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'promotionContents['+i+'].id',
					name: 'viewBean.promotionContents['+i+'].id'
				}
				,{
					xtype: 'textfield',
					fieldLabel: PROMOTION_NAME,
					allowBlank: true,
					id: 'promotionContents['+i+'].name',
					name: 'viewBean.promotionContents['+i+'].name',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
							promotionCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'textarea',
					fieldLabel: PROMOTION_DESCRIPTION,
					allowBlank: true,
					height: (languages.length>1)?190:280, 
					id: 'promotionContents['+i+'].description',
					name: 'viewBean.promotionContents['+i+'].description',
					enableKeyEvents: true,
					i: i,
					listeners: {
						'focus': function() {
							window.keyMapEnter.disable();					
						},
						'blur': function() {
							window.keyMapEnter.enable();					
						},
						'keyup' : function () {
							promotionCheckFields(this.i);
						}
					}
				}
				,{
					xtype: 'textfield',
					fieldLabel: PROMOTION_KEYWORDS,
					allowBlank: true,
					id: 'promotionContents['+i+'].keywords',
					name: 'viewBean.promotionContents['+i+'].keywords',
					enableKeyEvents: true,
					i: i,
					listeners: { 'keyup' :  function () {
						promotionCheckFields(this.i);
					}
				}
				}
				,{
					xtype: 'textfield',
					value: ITEM_KEYWORDS_COMMAS,
					readOnly: true,
					style: 'background-image:url(""); background-color:#f0f0f0; border-color:#F0F0F0; height:14px; padding-top:0px;'
				},
				{
					xtype: 'panel',
					height: 1,
					width: '100%',
					border: false,
					style: 'margin-top: 10px; border-top: 1px solid #D0D0D0; border-bottom: 1px solid #ffffff;'
				}
				,{
					xtype: 'panel',
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
					text: PROMOTION_TRANSLATE,
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
		id: 'tabPanelPromotion',
		//layout: 'border',
		plain: true,
		width: TAB_PANEL_WIDTH,
		height: 400,
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
		var tabPanel = Ext.getCmp('tabPanelPromotion');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-promotion').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/promotion/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-promotion').getForm().setValues(datas);
			},
			'tabPanelPromotion'
		);
	}

	//-----------------------------------------------------------------------------
	// Promotions CRUD
	//-----------------------------------------------------------------------------
	var promotionGridCRUD = new HurryApp.GridCRUD({
		type: 'promotion',
		region: 'center',
		width: '100%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/promotion/load',
		urlSave: myAppContext+'/promotion/save',
		urlEdit: myAppContext+'/promotion/edit',
		urlDelete: myAppContext+'/promotion/delete',
		defaultParams: {'viewBean.projectId': window.projectId},
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
		},
		createFormCallback: initTabs,

		title:               PROMOTION_LISTE,
		formWindowTitle:     PROMOTION_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    PROMOTION_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: PROMOTION_TOOLTIP_DELETE,
		labelDeleteQuestion: PROMOTION_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   PROMOTION_MESSAGE_DELETE_ADVERT,
		
		createFields: [tabPanel],
		searchFields: [nameSearch],
		
		formWindowWidth:    450,
		formWindowMinWidth: 380,
		formLabelWidth:     100,
		
		displayPagingBar: true,
		fileUpload: true
	});

	return {
		getComponent: function() {
			return promotionGridCRUD;
		}
	}
}
