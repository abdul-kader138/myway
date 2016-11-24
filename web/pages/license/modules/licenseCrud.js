LicenseCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			header: LICENSE_ICON,
			dataIndex: 'visual',
			sortable: false,
			align: 'left',
			width: 30,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return '<img src="'+myAppContext + "/" + record.data.visual+'" width="16" height="16"/>';
			}
		}
		,{
			header: LICENSE_KEY,
			dataIndex: 'key',
			sortable: true,
			align: 'left',
			width: 200
		}
		,{
			id: 'description',
			header: LICENSE_DESCRIPTION,
			dataIndex: 'description',
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
					id: 'licenseContents['+i+'].languageId',
					name: 'viewBean.licenseContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'licenseContents['+i+'].languageCode',
					name: 'viewBean.licenseContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'licenseContents['+i+'].id',
					name: 'viewBean.licenseContents['+i+'].id'
				}
				,{
		  			xtype: 'textfield',
		  			fieldLabel: LICENSE_KEY,
		  			allowBlank: true,
		  			id: 'licenseContents['+i+'].name',
					name: 'viewBean.licenseContents['+i+'].name',
		  		}
		  		,{
		  			xtype: 'textfield',
		  			fieldLabel: LICENSE_DESCRIPTION,
		  			allowBlank: true,
		  			id: 'licenseContents['+i+'].description',
					name: 'viewBean.licenseContents['+i+'].description',
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
		id: 'tabPanelLicense',
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
	
	var id = {
		xtype: 'textfield',
		fieldLabel: "ID",
		allowBlank: true,
		id: 'idBis',
		readOnly: true,
	};
	
	var fields = [
  		{
  			xtype: 'hidden',
  			id: 'projectId',
  			name: 'viewBean.projectId'
  		}
  		,tabPanel
  		,{
  			xtype: 'hidden',
  			id: 'key',
  			name: 'viewBean.key'
  		}
  		,{
  			xtype: 'hidden',
  			id: 'description',
  			name: 'viewBean.description'
  		},
  		{
  			xtype: 'radiogroup',
  			id: 'orientation',
  			columns: 2,
  			fieldLabel: LICENSE_ORIENTATION,
  			items: [
  				{boxLabel: LICENSE_ORIENTATION_NORTH,  name: 'viewBean.orientation', inputValue: 'north', checked: true}
  				,{boxLabel: LICENSE_ORIENTATION_SOUTH,  name: 'viewBean.orientation', inputValue: 'south'}
  				,{boxLabel: LICENSE_ORIENTATION_EAST, name: 'viewBean.orientation', inputValue: 'east'}
  				,{boxLabel: LICENSE_ORIENTATION_WEST,  name: 'viewBean.orientation', inputValue: 'west'}
              ]
  		}
  		,{
			xtype: 'fileuploadfield',
			fieldLabel: LICENSE_ICON,
			allowBlank: true,
			id: 'logo',
			name: 'viewBean.logo',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			fileType: 'image'
		}
  		/*
  		,{
  			xtype: 'combo',
  			fieldLabel: LICENSE_ITEM,
  			id: 'itemId',
  			name: 'viewBean.item',
  			hiddenName: 'viewBean.itemId',
  			valueField: 'id',
  			displayField: 'libelle',
  			store: new Ext.data.JsonStore({
  				fields: ['id', 'libelle'],
  				data : items
  			}),
  			typeAhead: true,
  			mode: 'local',
  			triggerAction: 'all',
  			selectOnFocus:true
  		}
  		*/
  	];
	
	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var keySearch = {
		xtype: 'textfield',
		fieldLabel: LICENSE_KEY,
		id: 'keySearch',
		name: 'viewBean.key'
	};
	
	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelLicense');
		tabPanel.setAutoScroll(false);
		for (var i = 0; i < languages.length; i++) {
			tabPanel.setActiveTab(i+1);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	
	var translate = function(sourceLanguageId, tabIndex) {
		var params = Ext.getCmp('form-panel-license').getForm().getValues();
		params['sourceLanguageId'] = sourceLanguageId;
		params['tabIndex'] = tabIndex;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/license/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				Ext.getCmp('form-panel-license').getForm().setValues(datas);
			},
			'tabPanelLicense'
		);
	};
	
	//-----------------------------------------------------------------------------
	// Licenses CRUD
	//-----------------------------------------------------------------------------
	var licenseGridCRUD = new HurryApp.GridCRUD({
		type: 'license',
		region: 'center',
		id: 'licenseGridCRUD',
		split: true,
		width: '50%',
		height: 210,
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'description',
	
		urlLoad: myAppContext+'/license/load',
		urlSave: myAppContext+'/license/save',
		urlEdit: myAppContext+'/license/edit',
		urlDelete: myAppContext+'/license/delete',
		defaultParams: {'viewBean.projectId': window.projectId},
		loadOnStartup: false,
		//enableEditForm: false,
		//displayEditButton: false,
		//fnDblClick: function() {},
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
			
			//if (window.userGroupId != GROUPE_SUPER_ADMIN) {
			//	Ext.getCmp('key').setReadOnly(true);
			//}
			//if (window.userGroupId == GROUPE_USER) {
			//	Ext.getCmp('description').setReadOnly(true);
			//	Ext.getCmp('okButton-form-panel-license').disable();
			//}
			hasPageChanged = false;
		},
		createFormCallback: initTabs,
		deleteEntityYesCallback: function() {
			Ext.getCmp('keyBis').disable();
			Ext.getCmp('descriptionBis').disable();
			Ext.getCmp('orientationBis').disable();
			Ext.getCmp('save-button').disable();
			Ext.getCmp('keyBis').setValue("");
			Ext.getCmp('descriptionBis').setValue("");
			Ext.getCmp('orientationBis').setValue(null);
			hasPageChanged = false;
		},

		title:               LICENSE_LISTE,
		formWindowTitle:     LICENSE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    LICENSE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: LICENSE_TOOLTIP_DELETE,
		labelDeleteQuestion: LICENSE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   LICENSE_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		editFields: [id].concat(fields),
		
		searchFields: window.userGroupId == GROUPE_SUPER_ADMIN ? [keySearch] : null,
		displayToolBar: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		//displayToolBar: window.userGroupId == GROUPE_USER ? false : true,
		displayDeleteButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		
		formWindowWidth:    400,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT,
		
		//displayPagingBar: false

	});

	return {
		getComponent: function() {
			return licenseGridCRUD;
		}
	}
}