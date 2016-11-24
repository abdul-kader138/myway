Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Grid CRUD
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.GridCRUD
 * @extends HurryApp.Grid
 * Config options :
 * 
 *   urlLoad: String
 *   urlSave: String
 *   urlEdit: String
 *   urlDelete: String
 *   urlUpdateModified: String
 *   urlDblClick: String
 *   postOnDblClick: Array of strings
 *   loadParams: Object
 *   loadFormCallback: Function
 *   deleteEntityYesCallback: Function
 *   selectGridCallback: Function
 * 
 *   formWindowTitle: String
 *   labelCancelButton: String
 *   labelAddButton: String
 *   tooltipAddButton: String
 *   labelDeleteButton: String
 *   tooltipDeleteButton: String
 *   labelDeleteQuestion: String
 *   labelDeleteAdvert: String
 * 
 *   formJSONDataNames: Array of strings
 *   gridJSONDataNames: Array of strings
 *   searchFields: Array of objects (field configs)
 *   createFields: Array of objects (field configs)
 *   editFields: Array of objects (field configs)
 *   editButtons: Array of objects (button configs)
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 * 
 *   menuButtons: Array of objects (button configs)
 *   displayMenuBar: Boolean
 *   displayAddButton: Boolean
 *   displayDeleteButton: Boolean
 *   displayEditButton: Boolean
 *   loadOnStartup: Boolean
 *   getRowClass: Function
 *
 *   dbSync: Boolean
 *   autoId: Boolean
 */
HurryApp.GridCRUD = Ext.extend(HurryApp.Grid, {
    // superclass properties
	frame: false,

    // class properties
	urlSave: null,
	urlEdit: null,
	urlDelete: null,
	urlUpdateModified: null,
	
	formWindowTitle:     FORM_WINDOW_TITLE,
	labelCancelButton:   LABEL_CANCEL_BUTTON,
	labelAddButton:      LABEL_ADD_BUTTON,
	tooltipAddButton:    TOOLTIP_ADD_BUTTON,
	labelDeleteButton:   LABEL_DELETE_BUTTON,
	tooltipDeleteButton: TOOLTIP_DELETE_BUTTON,
	labelDeleteQuestion: LABEL_DELETE_QUESTION,
	labelDeleteAdvert:   LABEL_DELETE_ADVERT,
	
	formWindow: null,
	formWindowWidth:    400,
	formWindowMinWidth: 400,
	formLabelWidth:     80,
	
	displayMenuBar: true,
	displayAddButton: true,
	displayDeleteButton: true,
	displayEditButton: true,
	dbSync: true,
	autoId: true,

    initComponent: function() {
		var gridCrud = this;
    	// urls
		this.urlSave = this.initialConfig.urlSave;
		this.urlEdit = this.initialConfig.urlEdit;
		this.urlDelete = this.initialConfig.urlDelete;
		this.urlUpdateModified = this.initialConfig.urlUpdateModified;

    	// labels
    	if (this.initialConfig.formWindowTitle)     {this.formWindowTitle     = this.initialConfig.formWindowTitle;}
    	if (this.initialConfig.labelCancelButton)   {this.labelCancelButton   = this.initialConfig.labelCancelButton;}
    	if (this.initialConfig.labelAddButton)      {this.labelAddButton      = this.initialConfig.labelAddButton;}
    	if (this.initialConfig.tooltipAddButton)    {this.tooltipAddButton    = this.initialConfig.tooltipAddButton;}
    	if (this.initialConfig.labelDeleteButton)   {this.labelDeleteButton   = this.initialConfig.labelDeleteButton;}
    	if (this.initialConfig.tooltipDeleteButton) {this.tooltipDeleteButton = this.initialConfig.tooltipDeleteButton;}
    	if (this.initialConfig.labelDeleteQuestion) {this.labelDeleteQuestion = this.initialConfig.labelDeleteQuestion;}
    	if (this.initialConfig.labelDeleteAdvert)   {this.labelDeleteAdvert   = this.initialConfig.labelDeleteAdvert;}

		if (this.initialConfig.autoId == false) {this.autoId = false;}
		if (this.initialConfig.dbSync == false) {this.dbSync = false;}

		if (!this.initialConfig.editFields) {
			this.initialConfig.editFields = this.initialConfig.createFields;
		}

		var idColumn = {id: 'id', dataIndex: 'id', hidden: true, header: COMMON_ID};
		this.initialConfig.columns = this.initialConfig.columns.concat([idColumn]);
		
		if (this.autoId) {
			// fields
			var idField = {xtype: 'hidden', fieldLabel: 'Id', id: 'id', name: 'viewBean.id'};
			this.initialConfig.createFields = this.initialConfig.createFields.concat([idField]);
			this.initialConfig.editFields = this.initialConfig.editFields.concat([idField]);
		}
		
    	// JSON data names
		if (this.initialConfig.formJSONDataNames) {
			if (this.autoId) {
				this.initialConfig.formJSONDataNames = ['id'].concat(this.initialConfig.formJSONDataNames);
			}
		}
		else {
			var fields = this.initialConfig.editFields;
			if (this.initialConfig.createFields.length > this.initialConfig.editFields.length) {
				fields = this.initialConfig.createFields;
			}
			this.initialConfig.formJSONDataNames = HurryApp.Utils.toArrayOfString(fields, 'id');
			this.initialConfig.formJSONDataNames = this.initialConfig.formJSONDataNames.concat(HurryApp.Utils.toArrayOfString(fields, 'hiddenId'));
		}

		if (this.initialConfig.gridJSONDataNames) {
			if (this.autoId) {
				this.initialConfig.gridJSONDataNames = ['id'].concat(this.initialConfig.gridJSONDataNames);
			}
		}
		else {
			if (this.autoId) {
				this.initialConfig.gridJSONDataNames = ['id'].concat(HurryApp.Utils.toArrayOfString(this.initialConfig.columns, 'dataIndex'));
			}
		}

		// form window size
		if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
		if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
		if (this.initialConfig.formLabelWidth)     {this.formLabelWidth     = this.initialConfig.formLabelWidth;}
	
		// misc
		if (!this.initialConfig.editButtons) {this.initialConfig.editButtons = []};
		if (this.initialConfig.displayMenuBar == false) {this.displayMenuBar = false};
		if (this.initialConfig.displayAddButton == false) {this.displayAddButton = false};
		if (this.initialConfig.displayDeleteButton == false) {this.displayDeleteButton = false};
		if (this.initialConfig.displayEditButton == false) {this.displayEditButton = false};
			
		// menu bar
		var menubarElements = new Array();
		var nbMenubarElelements = 0;
		
		if (this.displayAddButton) {
			if (menubarElements.length > 0) {
				menubarElements[nbMenubarElelements++] = '-';
			}
			
			menubarElements[nbMenubarElelements++] = {
				id: 'addButton-'+(this.initialConfig.idExtension ? this.initialConfig.idExtension : this.initialConfig.type),
				text: this.labelAddButton,
				tooltip: this.tooltipAddButton,
				iconCls: 'tool-add',
				handler: function() {
					gridCrud.createEntity();
				}
			};
		}
		
		if (this.displayDeleteButton) {
			if (menubarElements.length > 0) {
				menubarElements[nbMenubarElelements++] = '-';
			}
			
			menubarElements[nbMenubarElelements++] = {
				id: 'deleteButton-'+(this.initialConfig.idExtension ? this.initialConfig.idExtension : this.initialConfig.type),
				text: this.labelDeleteButton,
				tooltip: this.tooltipDeleteButton,
				iconCls: 'tool-remove',
				handler: function() {
					gridCrud.deleteEntity();
				}
			};
		}
		
		if (this.urlUpdateModified) {
			if (menubarElements.length > 0) {
				menubarElements[nbMenubarElelements++] = '-';
			}
			
			menubarElements[nbMenubarElelements++] = {
				text: LABEL_UPDATE_ALL_BUTTON,
				tooltip: TOOLTIP_UPDATE_ALL_BUTTON,
				iconCls: 'tool-edit',
				handler: function() {
					gridCrud.updateEntities();
				}
			};
		}
		
		if (this.initialConfig.menuButtons) {
			for (var i = 0; i < this.initialConfig.menuButtons.length; i++) {
				if (menubarElements.length > 0) {
					menubarElements[nbMenubarElelements++] = '-';
				}
				
				menubarElements[nbMenubarElelements++] = this.initialConfig.menuButtons[i];
			}
		}
		
		// search fields
		menubarElements = menubarElements.concat(this.getMenuBarSearchFields(true));

		var menubar = new Ext.Toolbar(menubarElements);
		
		if (this.displayMenuBar) {
			this.gridMenubar = menubar;
		}
		
		// Row edit icon
		if (this.initialConfig.columns[this.initialConfig.columns.length-1].dataIndex != null) {
			if (this.displayEditButton) {
				this.initialConfig.columns[this.initialConfig.columns.length] = {
					width: 16,
					fixed: true,
					dataIndex: null,
					sortable: false,
					menuDisabled: true,
					//locked: true,
					css: 'background-image:url(../images/icons/tool-edit2.png) !important; background-repeat: no-repeat; cursor: pointer;'
				}
			}
			
			//if (this.initialConfig.urlDblClick) {
			//	this.initialConfig.columns[this.initialConfig.columns.length] = {
			//		width: 16,
			//		fixed: true,
			//		dataIndex: null,
			//		sortable: false,
			//		menuDisabled: true,
					//locked: true,
			//		css: 'background-image:url(../images/icons/tool-goTo2.png) !important; background-repeat: no-repeat; cursor: pointer;'
			//	}
			//}
		}
		this.on('cellclick', function(gridPanel, rowIndex, columnIndex, event) {
			var indexColumEdit = this.displayEditButton ? this.initialConfig.columns.length-1 : -1;
			var indexColumGoTo = -1;
			if (this.initialConfig.urlDblClick) {
				indexColumEdit = indexColumEdit-1;
				indexColumGoTo = this.initialConfig.columns.length-1;
			}
			
			if (this.initialConfig.templateExpander) {
				indexColumEdit = indexColumEdit+1;
				indexColumGoTo = this.initialConfig.columns.length+1;
			}

			if (columnIndex == indexColumEdit) {
				var selectedId = this.store.data.items[rowIndex].data.id;
				this.editEntity(selectedId, rowIndex);
			}
			else if (columnIndex == indexColumGoTo) {
				var record = gridPanel.getStore().getAt(rowIndex);
				var urlDblClick = this.initialConfig.urlDblClick+'?'+this.initialConfig.type+'Id='+record.get('id');
				if (this.initialConfig.postOnDblClick) {
					for (var i = 0; i < this.initialConfig.postOnDblClick.length; i++) {
						urlDblClick += '&viewBean.'+this.initialConfig.postOnDblClick[i]+'='+record.get(this.initialConfig.postOnDblClick[i]);
					}
				}
				window.location = urlDblClick;
			}
		});
		
		// Action on double click
		if (this.initialConfig.urlDblClick) {
			this.initialConfig.fnDblClick = function(gridPanel, rowIndex, e) {
				var record = gridPanel.getStore().getAt(rowIndex);
				var urlDblClick = this.initialConfig.urlDblClick+'?'+this.initialConfig.type+'Id='+record.get('id');
				if (this.initialConfig.postOnDblClick) {
					for (var i = 0; i < this.initialConfig.postOnDblClick.length; i++) {
						urlDblClick += '&viewBean.'+this.initialConfig.postOnDblClick[i]+'='+record.get(this.initialConfig.postOnDblClick[i]);
					}
				}
				window.location = urlDblClick;
	        };
		}
		else {
			if (!this.initialConfig.fnDblClick) {
		        this.initialConfig.fnDblClick = function(gridPanel, rowIndex, e) {
					var selectedId = this.store.data.items[rowIndex].data.id;  
					this.editEntity(selectedId, rowIndex);
		        };
			}
		}
        
        // Apply properties
        Ext.apply(this, {
			tbar: this.gridMenubar
			//bbar: myPagingToolbar
        }); // eo apply 
        
        // call parent 
        HurryApp.GridCRUD.superclass.initComponent.apply(this, arguments); 

	}, // eo function initComponent 
	
	onRender:function() { 
		HurryApp.Grid.superclass.onRender.apply(this, arguments); 
		var base = this;
		var deleteKeyPressed = function() {
			if(base.displayDeleteButton || base.initialConfig.canDelete) base.deleteEntity();
		};
		new Ext.KeyMap(this.el, {
            key: Ext.EventObject.DELETE,
            handler: deleteKeyPressed,
            scope : this
        });
	}, // eo function onRender 
	
	//-------------------------------------------------------------------------
	// Create form window
	//-------------------------------------------------------------------------
	createFormWindow: function(formType, rowIndex) {
		var gridCrud = this;
		
		// cancel button
		var cancelButton = {
			text: this.labelCancelButton,
			handler: function(){
				gridCrud.formWindow.close();
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
		
		var formId = 'form-panel';
		if (this.initialConfig.idExtension || this.initialConfig.type) {
			formId +='-'+(this.initialConfig.idExtension ? this.initialConfig.idExtension : this.initialConfig.type)
		}

		// window
		this.formWindow = new HurryApp.FormWindow({
			title: this.formWindowTitle,
			width: this.formWindowWidth,
			minWidth: this.formWindowMinWidth,
			animTarget: this,
			formPanelConfig: {
				id: formId,
				frame: false,
				baseCls: 'x-plain',
				labelWidth: this.formLabelWidth,
				fieldAnchor: this.initialConfig.fieldAnchor,
				url: this.urlSave,
				reader: new Ext.data.JsonReader({
						root: 'datas'
					}, this.initialConfig.formJSONDataNames
				),
				items: fields,
				beforeSubmit: this.initialConfig.beforeSubmit,
                successCallback: function(response) {
					if (gridCrud.dbSync) {
						// sync with DB
						gridCrud.formWindow.close();
						gridCrud.loadDataStore();
						gridCrud.publish(gridCrud.type+'.update');
							
						if (gridCrud.initialConfig.saveEntityCallback) {
							gridCrud.initialConfig.saveEntityCallback.call(gridCrud, formType, response.datas[0]);								
						}
					}
					else {
						if (gridCrud.formWindow.formPanel.getForm().isValid()) {
							// update grid
							var data = HurryApp.Utils.toDataBean(gridCrud.formWindow.formPanel.getForm().getValues());
							if (rowIndex || rowIndex == 0) {
								gridCrud.store.removeAt(rowIndex);
								gridCrud.store.insert(rowIndex, [new Ext.data.Record(data)]);
							}
							else {
								gridCrud.store.insert(0, [new Ext.data.Record(data)]);
							}
							gridCrud.formWindow.close();
						}
					}
                },
                finalCallback: function(response) {
                	if(gridCrud.initialConfig.saveFinalCallback) {
                		gridCrud.initialConfig.saveFinalCallback.call(gridCrud, formType, response);
                	}
                },
                otherButtons: buttons,
				fileUpload: this.initialConfig.fileUpload ? this.initialConfig.fileUpload : false,
				timeout: this.initialConfig.timeout ? this.initialConfig.timeout : 30
			}
		});

		this.formWindow.show(this);

		if (this.initialConfig.createFormCallback) {
			this.initialConfig.createFormCallback.call(gridCrud, formType);
		}
	},
	
	//-------------------------------------------------------------------------
	// Delete entity
	//-------------------------------------------------------------------------
	deleteEntity: function() {
		var gridCrud = this;
		var sel = this.getSelectionModel().getSelections();
		var deleteEntityYesCallback = function() {};
		if (this.initialConfig.deleteEntityYesCallback) {deleteEntityYesCallback = this.initialConfig.deleteEntityYesCallback;} // callback
		if(sel.length > 0) {
			Ext.MessageBox.confirm(COMMON_MESSAGE_CONFIRM, this.labelDeleteQuestion, 
				function(btn) {
					 if(btn == 'yes') {
						// sync with DB
						if (gridCrud.dbSync) {
							var idsParam = gridCrud.getSelectedIds();
		
							// delete request
							HurryApp.Utils.sendAjaxRequest(gridCrud.urlDelete, {'selectedIds' : idsParam}, function(jsonResponse) {
								gridCrud.loadDataStore();
								gridCrud.publish(gridCrud.type+'.update');
							});
						}
						// update grid
						else {
							gridCrud.store.remove(gridCrud.getSelectionModel().getSelections());
						}
						deleteEntityYesCallback.call();
					}
				}
			);	
		}
		else {
			Ext.MessageBox.alert(COMMON_MESSAGE_ERROR, this.labelDeleteAdvert);
		}
	},
	
	//-------------------------------------------------------------------------
	// Update modified entities
	//-------------------------------------------------------------------------
	updateEntities: function() {
		if (this.dbSync) {
			var gridCrud = this;
			if(this.store.getModifiedRecords().length > 0) {
				var modifiedObjects = gridCrud.getModifiedObjects();
	
				// update request
				HurryApp.Utils.sendAjaxRequest(gridCrud.urlUpdateModified, {'selectedObjects' : modifiedObjects}, function(jsonResponse) {
					gridCrud.loadDataStore();
					gridCrud.publish(gridCrud.type+'.updateModified');
				});
			}
		}
	},
	
	//-------------------------------------------------------------------------
	// Edit entity
	//-------------------------------------------------------------------------
	editEntity: function(selectedId, rowIndex) {
		var gridCrud = this;
		this.createFormWindow('edit', rowIndex);
		
		var loadFormCallback = function() {};
		if (this.initialConfig.loadFormCallback) {loadFormCallback = this.initialConfig.loadFormCallback;}
	
		// load form and assign value to fields
		if (this.dbSync) {
			this.formWindow.formPanel.form.load({
				url: this.urlEdit+'?viewBean.id='+selectedId, 
				waitMsg: Ext.LoadMask.prototype.msg,
				success: function(form, action){
					loadFormCallback.call({}, form, action);
					
					// load gridSelect fields
					var selects = gridCrud.formWindow.formPanel.findByType('gridSelect');
					if (selects != null) {
						for (var i = 0; i < selects.length; i++) {
							selects[i].loadDataStore({'viewBean.parentId': selectedId});
							selects[i].store.on('load', selects[i].fnAfterTransferItems);
						}
					}
				
					// load gridCrud fields
					var cruds = gridCrud.formWindow.formPanel.findByType('gridCrud');
					if (cruds != null) {
						for (var i = 0; i < cruds.length; i++) {
							cruds[i].loadDataStore({'viewBean.parentId': selectedId});
							cruds[i].store.on('load', cruds[i].fnAfterTransferItems);
						}
					}
				}
			});	
		}
		else {
			// load data from grid (not DB)
			var data = this.store.data.items[rowIndex].data;
			this.formWindow.formPanel.getForm().setValues(HurryApp.Utils.toViewBean(this.store.data.items[rowIndex].data));		
			loadFormCallback.call();	
		}
	},
	
	//-------------------------------------------------------------------------
	// Create entity
	//-------------------------------------------------------------------------
	createEntity: function() {
		this.createFormWindow('create');
		this.formWindow.formPanel.getForm().setValues(this.defaultParams);	
	},
	
	//-------------------------------------------------------------------------
	// Link gridSelect to the the CRUD
	//-------------------------------------------------------------------------
	linkGridSelect: function(gridSelect) {
		this.on('rowclick', function(grid, rowIndex, event) {
			gridSelect.refresh(grid.store.data.items[rowIndex].data.id);
		});
		//this.store.on('load', function(store, record, options) {
		//	gridSelect.loadDataStore();
		//});
		gridSelect.subscribe(this.type+'.load', function(store, record, options) {
			gridSelect.loadDataStore();
		});
	}, 
	
	//-------------------------------------------------------------------------
	// Set parent id and refresh
	//-------------------------------------------------------------------------
	refresh: function(parentId) {
		this.parentId = parentId;
		this.setDefaultParams({'viewBean.parentId': parentId, 'viewBean.projectId': window.projectId});
		this.loadDataStore();
	}
}); // eo extend 

Ext.reg('gridCrud', HurryApp.GridCRUD);