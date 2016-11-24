Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// TabPanel CRUD
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.TabPanelCRUD
 * @extends Ext.Panel
 * Config options :
 * 
 *   datas: Array of objects {libelle: 'libelle', id: 1}
 *   gridPanel: Ext.grid.GridPanel
 *   
 *   urlLoad: String
 *   urlSave: String
 *   urlEdit: String
 *   urlDelete: String
 *   loadParams: Object
 * 
 *   formWindowTitle: String
 *   labelCancelButton: String
 *   labelAddButton: String
 *   tooltipAddButton: String
 *   labelUpdateButton: String
 *   labelDeleteButton: String
 *   labelDeleteQuestion: String
 * 
 *   formJSONDataNames: Array of strings
 *   createFields: Array of objects (field configs)
 *   editFields: Array of objects (field configs)
 *   editButtons: Array of objects (button configs)
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 *
 *   tabLabel: String
 *   gridDefaultLoadParam: String (default gridPanel load parameter name)
 */
HurryApp.TabPanelCRUD = Ext.extend(Ext.Panel, {
	// superclass properties
	frame: true,

	// class properties
	tabPanelCrud: null,
	
	urlSave: null,
	urlEdit: null,
	urlDelete: null,
	
	formWindowTitle:     FORM_WINDOW_TITLE,
	labelCancelButton:   LABEL_CANCEL_BUTTON,
	labelAddButton:      LABEL_ADD_BUTTON,
	tooltipAddButton:    TOOLTIP_ADD_BUTTON,
	labelUpdateButton:   LABEL_UPDATE_BUTTON,
	tooltipUpdateButton: TOOLTIP_UPDATE_BUTTON_UNIQUE,
	labelDeleteButton:   LABEL_DELETE_BUTTON,
	tooltipDeleteButton: TOOLTIP_DELETE_BUTTON_UNIQUE,
	labelDeleteQuestion: LABEL_DELETE_QUESTION_UNIQUE,
	
	formWindow: null,
	formWindowWidth:    400,
	formWindowMinWidth: 400,
	formLabelWidth:     80,
	
	initComponent: function() {
		var mainPanel = this;

		// urls
		this.urlLoad = this.initialConfig.urlLoad;
		this.urlSave = this.initialConfig.urlSave;
		this.urlEdit = this.initialConfig.urlEdit;
		this.urlDelete = this.initialConfig.urlDelete;

		// labels
		if (this.initialConfig.formWindowTitle)     {this.formWindowTitle     = this.initialConfig.formWindowTitle;}
		if (this.initialConfig.labelCancelButton)   {this.labelCancelButton   = this.initialConfig.labelCancelButton;}
		if (this.initialConfig.labelAddButton)      {this.labelAddButton      = this.initialConfig.labelAddButton;}
		if (this.initialConfig.tooltipAddButton)    {this.tooltipAddButton    = this.initialConfig.tooltipAddButton;}
		if (this.initialConfig.labelUpdateButton)   {this.labelAddButton      = this.initialConfig.labelAddButton;}
		if (this.initialConfig.labelDeleteButton)   {this.labelDeleteButton   = this.initialConfig.labelDeleteButton;}
		if (this.initialConfig.labelDeleteQuestion) {this.labelDeleteQuestion = this.initialConfig.labelDeleteQuestion;}

		// fields
		if (!this.initialConfig.editFields) {
			this.initialConfig.editFields = this.initialConfig.createFields;
		}
		var idField = {xtype: 'hidden', fieldLabel: 'Id', id: 'id', name: 'viewBean.id'};
		this.initialConfig.createFields = this.initialConfig.createFields.concat([idField]);
		this.initialConfig.editFields = this.initialConfig.editFields.concat([idField]);
		
    	// JSON data names
		if (this.initialConfig.formJSONDataNames) {
			this.initialConfig.formJSONDataNames = ['id'].concat(this.initialConfig.formJSONDataNames);
		}
		else {
			this.initialConfig.formJSONDataNames = HurryApp.Utils.toArrayOfString(this.initialConfig.editFields, 'id');
		}

		// form window size
		if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
		if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
		if (this.initialConfig.formLabelWidth)     {this.formLabelWidth     = this.initialConfig.formLabelWidth;}
		
		// misc
		if (!this.initialConfig.editButtons) {this.initialConfig.editButtons = []};
	
		//-----------------------------------------------------------------------------
		// Tab Pannel
		//-----------------------------------------------------------------------------
		this.tabPanelCrud = new Ext.ux.TabPanel({
			autoHeight: false,
			resizeTabs: false,
			//minTabWidth: 80,
			plain: true,
			enableTabScroll: true,
			deferredRender: true,
			tabPosition: 'top',
			defaults:{autoScroll: false}
		});
		
		this.tabPanelCrud.gridPanel = this.initialConfig.gridPanel;
	
		//-----------------------------------------------------------------------------
		// Tabs
		//-----------------------------------------------------------------------------
		for (var i = 0 ; i < this.initialConfig.datas.length ; i++) {
			var labelValue = this.initialConfig.datas[i];
			this.addTab(labelValue[this.initialConfig.tabLabel], labelValue.id);
		}
		
		// Add-tab
		this.addAddTab();
		
		if (this.initialConfig.datas.length > 0) {
			this.tabPanelCrud.setActiveTab(this.initialConfig.datas[0].id);
		}
		else {
			//this.tabPanelCrud.gridPanel.hide();
			//this.tabPanelCrud.setActiveTab('add-tab');
		}

		this.tabPanelCrud.on('beforetabchange', function(panel, newTab, currentTab) {
			if (newTab.id == 'add-tab') {
				mainPanel.createEntity();
				return false;
			}
		});

		// Tab menu
		this.tabPanelCrud.on('contextmenu', function(tabPanel, tab, e) {
			mainPanel.tabPanelCrud.setActiveTab(tab);
			if (!mainPanel.menu) {
				mainPanel.menu = new Ext.menu.Menu([{
					id: 'edit-tab-menu',
					text: mainPanel.labelUpdateButton,
					tooltip: mainPanel.tooltipUpdateButton,
					iconCls: 'tool-edit',
					handler : function(){
						mainPanel.editEntity(mainPanel.tabPanelCrud.getActiveTab().id);
					}
				},{
					id: 'close-tab-menu',
					text: mainPanel.labelDeleteButton,
					tooltip: mainPanel.tooltipDeleteButton,
					iconCls: 'tool-remove',
					handler : function(){
						mainPanel.deleteEntity(mainPanel.tabPanelCrud.getActiveTab().id);
					}
				}]);
			}
			mainPanel.menu.showAt(e.getPoint());
		});

		// Apply properties
		Ext.apply(this, {
			items: [this.tabPanelCrud]
		}); // eo apply 
		
		// call parent 
		HurryApp.TabPanelCRUD.superclass.initComponent.apply(this, arguments); 
	}, // eo function initComponent 
	
	//-------------------------------------------------------------------------
	// Create form window
	//-------------------------------------------------------------------------
	createFormWindow: function(formType) {
		var mainPanel = this;
		
		// cancel button
		var cancelButton = {
			text: this.labelCancelButton,
			handler: function(){
				mainPanel.formWindow.close();
			}
		}
		var buttons = [cancelButton];

		// pick fields and buttons
		if (formType == 'create') {
			fields = this.initialConfig.createFields;
		}
		else{
			fields = this.initialConfig.editFields;
			buttons = buttons.concat(this.initialConfig.editButtons);
		}

		// window
		this.formWindow = new HurryApp.FormWindow({
			title: this.formWindowTitle,
			width: this.formWindowWidth,
			minWidth: this.formWindowMinWidth,
			animTarget: this,
			formPanelConfig: {
				frame: false,
				baseCls: 'x-plain',
				labelWidth: this.formLabelWidth,
				url: this.urlSave,
				reader: new Ext.data.JsonReader({
						root: 'datas'
					}, this.initialConfig.formJSONDataNames
				),
				items: fields,
				successCallback: function() {
					mainPanel.formWindow.close();
					if (formType == 'create') {
						HurryApp.Utils.sendAjaxRequest(mainPanel.urlLoad, null, function(jsonResponse) {
							// Delete add-tab
							mainPanel.tabPanelCrud.remove('add-tab');
							
							// Add new tab
							//for (var i = 0 ; i < jsonResponse.datas.length ; i++) {
							//	var labelValue = jsonResponse.datas[i];
							//	mainPanel.addTab(labelValue[mainPanel.initialConfig.tabLabel], labelValue.id);
							//}
							var lastTab = jsonResponse.datas[jsonResponse.datas.length-1];
							mainPanel.addTab(lastTab[mainPanel.initialConfig.tabLabel], lastTab.id);
							mainPanel.tabPanelCrud.setActiveTab(lastTab.id);

							// Add add-tab
							mainPanel.addAddTab();
						});
					}
					else {
						// Update tab title
						mainPanel.tabPanelCrud.getActiveTab().setTitle(mainPanel.formWindow.formPanel.getForm().findField(mainPanel.initialConfig.tabLabel).getValue());
					}
				},
				otherButtons: buttons
			}
		});
	},
	
	//-------------------------------------------------------------------------
	// Delete entity
	//-------------------------------------------------------------------------
	deleteEntity: function(selectedTabId) {
		var mainPanel = this;
		if(selectedTabId) {
			Ext.MessageBox.confirm(COMMON_MESSAGE_CONFIRM, this.labelDeleteQuestion, 
				function(btn) {
					 if(btn == 'yes') {
						var idsParam = selectedTabId;
	
						// delete request
						HurryApp.Utils.sendAjaxRequest(mainPanel.urlDelete, {'selectedIds' : idsParam}, function(jsonResponse) {
							// delete tab
							mainPanel.tabPanelCrud.remove(selectedTabId);
						});
					}
				}
			);
		}
	},
	
	//-------------------------------------------------------------------------
	// Edit entity
	//-------------------------------------------------------------------------
	editEntity: function(selectedTabId) {
		this.createFormWindow('edit');
		this.formWindow.show(this.tabPanelCrud);
	
		// load form and assign value to fields
		this.formWindow.formPanel.form.load({
			url: this.urlEdit+'?viewBean.id='+selectedTabId, 
			waitMsg:'Chargement ...'
		});	
		
		// load gridSelect fields
		var selects = this.formWindow.formPanel.findByType('gridSelect');
		if (selects != null) {
			for (var i = 0; i < selects.length; i++) {
				selects[i].loadDataStore({'viewBean.parentId': selectedTabId});
				selects[i].store.on('load', selects[i].fnAfterTransferItems);
			}
		}
	},
	
	//-------------------------------------------------------------------------
	// Create entity
	//-------------------------------------------------------------------------
	createEntity: function() {
		this.createFormWindow('create');
		this.formWindow.show(this.tabPanelCrud);
		this.formWindow.formPanel.getForm().setValues(this.defaultParams);
	},
	
	//-------------------------------------------------------------------------
	// Add tab
	//-------------------------------------------------------------------------
	addTab: function(label, value) {
		var mainPanel = this;
		mainPanel.tabPanelCrud.add({
			title: label,
			id: ''+value,
			listeners: {
				activate: function(panel) {
					//mainPanel.tabPanelCrud.gridPanel.show();
					var gridDefaultLoadParam = 'viewBean.'+mainPanel.gridDefaultLoadParam;
					var params = {};
					params[gridDefaultLoadParam] = this.id;
					mainPanel.tabPanelCrud.gridPanel.setDefaultParams(params);
					mainPanel.tabPanelCrud.gridPanel.setLoadParams({});
					mainPanel.tabPanelCrud.gridPanel.loadDataStore();
				}
			},
			contentEl: mainPanel.tabPanelCrud.gridPanel.id
		});
	},
	
	//-------------------------------------------------------------------------
	// Add add-tab
	//-------------------------------------------------------------------------
	addAddTab: function() {
		this.tabPanelCrud.add({
			title: this.labelAddButton,
			tabTip: this.tooltipAddButton,
			iconCls: 'tool-add',
			id: 'add-tab',
			contentEl: this.tabPanelCrud.gridPanel.id
		});
	}
}); // eo extend 
