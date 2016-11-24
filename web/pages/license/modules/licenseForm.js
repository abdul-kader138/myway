LicenseForm = function() {

	var itemId;
	var callback = function(){};
	
	//-----------------------------------------------------------------------------
	// Save buton
	//-----------------------------------------------------------------------------
	var saveButton = {
		xtype: 'panel',
		region: 'south',
		split: true,
		layout: 'hbox',
		layoutConfig: {
			pack: 'center'
	    },
		height: 70,
		border: false,
		bodyCssClass: 'ha-panel-body',
		style: "padding-top: 15px;",
		items: [
			new Ext.Button({
				text: SETTINGS_SAVE,
				id: 'save-button',
				scale: 'large',
				iconCls: 'tool-save',
				width: 150,
				handler: function() {
					save();
				}
			})
		]
	};
	
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
		  			id: 'categoryContents['+i+'].nameBis',
					name: 'viewBean.categoryContents['+i+'].nameBis',
		  		}
		  		,{
		  			xtype: 'textfield',
		  			fieldLabel: LICENSE_DESCRIPTION,
		  			allowBlank: true,
		  			id: 'categoryContents['+i+'].descriptionBis',
					name: 'viewBean.categoryContents['+i+'].descriptionBis',
		  		},
		  		/*,{
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
				},*/
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
					style: 'margin: 10px auto 0 520px; font-weight: bold;',
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
	
	var fields = {
			xtype:'fieldset',
			title: '',
			region: 'center',
			id: 'displayFieldset',
			width: '100%',
			border: false,
			cls: 'ha-plain-panel',
			items: [
				{
					xtype: 'hidden',
					id: 'idBis',
					name: 'viewBean.idbis'
				}
				,{
					xtype: 'hidden',
					id: 'projectIdBis',
					name: 'viewBean.projectIdBis'
				}
				/*,{
					xtype: 'hidden',
					id: 'key',
					name: 'viewBean.key'
				}
				,{
					xtype: 'hidden',
					id: 'description',
					name: 'viewBean.description'
				}*/
				,{
					xtype: 'hidden',
					id: 'parentId',
					name: 'viewBean.parentId'
				}
				,tabPanel
				/*,{
					xtype: 'textfield',
					fieldLabel: LICENSE_KEY,
					allowBlank: true,
					id: 'keyBis',
					name: 'viewBean.keyBis',
					width: '98%',
					listeners: { 'change': function() {
							hasPageChanged = true;
						}
					}
				}
				,{
					xtype: 'textarea',
					fieldLabel: LICENSE_DESCRIPTION,
					allowBlank: true,
					height: 150,
					width: '98%',
					id: 'descriptionBis',
					name: 'viewBean.descriptionBis',
					listeners: { 'change': function() {
						hasPageChanged = true;
						}
					}
				}*/,
				{
					xtype: 'radiogroup',
					id: 'orientationBis',
					columns: 4,
					fieldLabel: LICENSE_ORIENTATION,
					width: '60%',
					items: [
						{boxLabel: LICENSE_ORIENTATION_NORTH, id: LICENSE_ORIENTATION_NORTH,  name: 'viewBean.orientationBis', inputValue: 'north', checked: true}
						,{boxLabel: LICENSE_ORIENTATION_SOUTH, id: LICENSE_ORIENTATION_SOUTH,  name: 'viewBean.orientationBis', inputValue: 'south'}
						,{boxLabel: LICENSE_ORIENTATION_EAST, id: LICENSE_ORIENTATION_EAST, name: 'viewBean.orientationBis', inputValue: 'east'}
						,{boxLabel: LICENSE_ORIENTATION_WEST, id: LICENSE_ORIENTATION_WEST,  name: 'viewBean.orientationBis', inputValue: 'west'}
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
				,saveButton
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
		]
	};
	
	var licensePanel = new Ext.form.FormPanel({
		region: 'center',
		width: '100%',
		bodyCssClass: 'ha-panel-body',
	    defaults: {
	        msgTarget: 'side',
	        anchor: '100%' 
	    },
		labelWidth: 80,
		title: LICENSE_TITRE,
		items: fields,
		fileUpload: false
	});
	
	//-----------------------------------------------------------------------------
	// Load form
	//-----------------------------------------------------------------------------
	var load = function(itemId, callback) {
		this.itemId = itemId;
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/license/edit',
			{
				'viewBean.id': itemId
			},
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				licensePanel.form.setValues(datas);
				addListeners();
				hasPageChanged = false;
				if(callback) callback();
			},
			'licenseEditPanel'
		);
	}
	
	//-----------------------------------------------------------------------------
	// Save data
	//-----------------------------------------------------------------------------
	var save = function() {
		var params = licensePanel.form.getValues();
		params['viewBean.id'] = this.itemId;
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/license/save',
			params,
			function(jsonResponse) {
				callback();
			},
			'licenseEditPanel'
		);
		hasPageChanged = false;
	}
	
	//-----------------------------------------------------------------------------
	// Add listeners on checkboxes and radios
	//-----------------------------------------------------------------------------
	var addListeners = function() {
		Ext.getCmp(LICENSE_ORIENTATION_NORTH).addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp(LICENSE_ORIENTATION_SOUTH).addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp(LICENSE_ORIENTATION_EAST).addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp(LICENSE_ORIENTATION_WEST).addListener('check', function() { hasPageChanged = true; });
	}

	//-----------------------------------------------------------------------------
	// Main panel
	//-----------------------------------------------------------------------------
	var mainPanel = new Ext.Panel({
		id: 'licenseEditPanel',
		region: 'east',
		width: '50%',
		split: true,
		border: false,
		layout: 'border',
		items: [
			licensePanel
		]
	});

	return {
		getComponent: function() {
			return mainPanel;
		},
		getSaveFunc: function() {
			return save;
		},
		getLoadFunc: function() {
			return load;
		},
		setCallbackFunc: function(func) {
			callback = func;
		}
		
	}
}
