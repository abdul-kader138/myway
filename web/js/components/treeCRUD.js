Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Tree CRUD
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.TreeCRUD
 * @extends Ext.tree.TreePanel
 * Config options :
 * 
 *   urlLoad: String
 *   urlSave: String
 *   urlEdit: String
 *   urlDelete: String
 *   urlMove: String
 *   loadParams: Object
 *   defaultParams: Object
 *   loadFormCallback: Function
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
 *   createFields: Array of objects (field configs)
 *   editFields: Array of objects (field configs)
 *   editButtons: Array of objects (button configs)
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 * 
 *   displayMenuBar: Boolean
 *   getRowClass: Function
 *   
 *   autoId: Boolean
 */
HurryApp.TreeCRUD = Ext.extend(Ext.tree.TreePanel, {
    // superclass properties
	frame: false,
	useArrows:true,
	autoScroll:true,
	animate:true,
	enableDD:true,
	containerScroll: true,
	root: {
		nodeType: 'async',
		text: 'Root',
		draggable:false,
		id:'1'
	},
	rootVisible: false,

    // class properties
	type: 'entity',
	urlSave: null,
	urlEdit: null,
	urlDelete: null,
	defaultParams: {},
	
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
	autoId: true,

    initComponent: function() {
		var treeCrud = this;
		if (this.initialConfig.type) {this.type = this.initialConfig.type;}

		// urls
		this.urlSave = this.initialConfig.urlSave;
		this.urlEdit = this.initialConfig.urlEdit;
		this.urlDelete = this.initialConfig.urlDelete;

		// loader
		var loader = new Ext.tree.TreeLoader({dataUrl: this.initialConfig.urlLoad})
		
		// default params
		if (this.initialConfig.defaultParams) {
			this.defaultParams = this.initialConfig.defaultParams;
			for (prop in this.defaultParams) {
				loader.baseParams[prop] = this.defaultParams[prop];
			}
		}
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

		if (!this.initialConfig.editFields) {
			this.initialConfig.editFields = this.initialConfig.createFields;
		}

		if (this.autoId) {
			// fields
			var idField = {xtype: 'hidden', id: 'id', name: 'viewBean.id'};
			var parentIdField = {xtype: 'hidden', id: 'parentId', name: 'viewBean.parentId'};
			if (this.initialConfig.createFields) {this.initialConfig.createFields = this.initialConfig.createFields.concat([idField, parentIdField]);}
			if (this.initialConfig.editFields) {this.initialConfig.editFields = this.initialConfig.editFields.concat([idField, parentIdField]);}
		}
		
    	// JSON data names
		if (this.initialConfig.formJSONDataNames) {
			if (this.autoId) {
				this.initialConfig.formJSONDataNames = ['parentId', 'id'].concat(this.initialConfig.formJSONDataNames);
			}
		}
		else {
			var fields = this.initialConfig.editFields;
			if (this.initialConfig.createFields && this.initialConfig.createFields.length > this.initialConfig.editFields.length) {
				fields = this.initialConfig.createFields;
			}
			this.initialConfig.formJSONDataNames = HurryApp.Utils.toArrayOfString(fields, 'id');
			this.initialConfig.formJSONDataNames = this.initialConfig.formJSONDataNames.concat(HurryApp.Utils.toArrayOfString(fields, 'hiddenId'));
		}
		
		// form window size
		if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
		if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
		if (this.initialConfig.formLabelWidth)     {this.formLabelWidth     = this.initialConfig.formLabelWidth;}

		// misc
		if (!this.initialConfig.editButtons) {this.initialConfig.editButtons = []};
		if (this.initialConfig.displayMenuBar == false) {this.displayMenuBar = false};

		// menu bar
		var menubarElements = [{
			text: this.labelAddButton,
			tooltip: this.tooltipAddButton,
			iconCls: 'tool-add',
			handler: function() {
				treeCrud.createEntity();
			}
		},'-',{
			text: this.labelDeleteButton,
			tooltip: this.tooltipDeleteButton,
			iconCls: 'tool-remove',
			handler: function() {
				treeCrud.deleteEntity();
			}
		}];

		var menubar = new Ext.Toolbar(menubarElements);
		
		var gridMenubar;
		if (this.displayMenuBar) {
			gridMenubar = menubar;
		}
		
		this.on('dblclick', function(node, event) {
			var selectedId = node.id;
			this.editEntity(selectedId);
		});

		this.on('click', function(node, event) {
			node.expand();
		});
		
		if (this.initialConfig.urlMove) {
			this.on('movenode', function(tree, node, oldParent, newParent, index) {
				HurryApp.Utils.sendAjaxRequest(treeCrud.initialConfig.urlMove, {'node': node.id, 'nodeIndex': index, 'oldParent': oldParent.id, 'newParent': newParent.id});
			});
		}
        
		// Apply properties
        Ext.apply(this, {
			tbar: gridMenubar,
			loader: loader
			//selModel: new Ext.tree.MultiSelectionModel()
        }); // eo apply 
        
        // call parent 
        HurryApp.TreeCRUD.superclass.initComponent.apply(this, arguments);

        // publish specific load event
        this.on('load', function(node) {
        	this.publish(this.type+'.load');
        }, this);
	
    }, // eo function initComponent 
	
	//-------------------------------------------------------------------------
	// Create form window
	//-------------------------------------------------------------------------
	createFormWindow: function(formType) {
		var treeCrud = this;
		
		// cancel button
		var cancelButton = {
			text: this.labelCancelButton,
			handler: function(){
				treeCrud.formWindow.close();
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
					var parentNode;
					treeCrud.formWindow.close();
					if (formType == 'create') {
						parentNode = treeCrud.getSelectionModel().getSelectedNode();
						if (!parentNode) {
							parentNode = treeCrud.root;
						}
					}
					else{
						parentNode = treeCrud.getSelectionModel().getSelectedNode().parentNode;
					}
					
					treeCrud.getLoader().load(parentNode);
					if (!parentNode.isExpanded()) {
						parentNode.expand();
					}
                },
                otherButtons: buttons
			}
		});
	},
	
	//-------------------------------------------------------------------------
	// Delete entity
	//-------------------------------------------------------------------------
	deleteEntity: function() {
		var treeCrud = this;
		var selectedNode = this.getSelectionModel().getSelectedNode();
		var parentNode = selectedNode.parentNode;
		if(selectedNode) {
			Ext.MessageBox.confirm(COMMON_MESSAGE_CONFIRM, this.labelDeleteQuestion, 
				function(btn) {
					 if(btn == 'yes') {
						var idsParam = selectedNode.id;
	
						// delete request
						HurryApp.Utils.sendAjaxRequest(treeCrud.urlDelete, {'selectedIds' : idsParam}, function(jsonResponse) {
							treeCrud.getLoader().load(parentNode);
							if (!parentNode.isExpanded()) {
								parentNode.expand();
							}
						});
					}
				}
			);	
		}
		else {
			Ext.MessageBox.alert(COMMON_MESSAGE_ERROR, this.labelDeleteAdvert);
		}
	},
	
	//-------------------------------------------------------------------------
	// Edit entity
	//-------------------------------------------------------------------------
	editEntity: function(selectedId) {
		this.createFormWindow('edit');
		this.formWindow.show(this);
	
		var loadFormCallback = function() {};
		if (this.initialConfig.loadFormCallback) {loadFormCallback = this.initialConfig.loadFormCallback;}
	
		// load form and assign value to fields
		this.formWindow.formPanel.form.load({
			url: this.urlEdit+'?viewBean.id='+selectedId, 
			waitMsg: Ext.LoadMask.prototype.msg,
			success: loadFormCallback
		});	
		
		// load gridSelect fields
		var selects = this.formWindow.formPanel.findByType('gridSelect');
		if (selects != null) {
			for (var i = 0; i < selects.length; i++) {
				selects[i].loadDataStore({'viewBean.parentId': selectedId});
				selects[i].store.on('load', selects[i].fnAfterTransferItems);
			}
		}
	},
	
	//-------------------------------------------------------------------------
	// Create entity
	//-------------------------------------------------------------------------
	createEntity: function() {
		this.createFormWindow('create');
		this.formWindow.show(this);
		var selectedNode = this.getSelectionModel().getSelectedNode();
		if (!selectedNode) {
			selectedNode = this.root;
		}
		this.formWindow.formPanel.getForm().setValues({'parentId': selectedNode.id});
		this.formWindow.formPanel.getForm().setValues(this.defaultParams);
	},
	
	//-------------------------------------------------------------------------
	// Link gridSelect to the the CRUD
	//-------------------------------------------------------------------------
	linkGridSelect: function(gridSelect) {
		this.on('click', function(node, event) {
			gridSelect.refresh(node.id);
		});
		gridSelect.subscribe(this.type+'.load', function(node) {
			gridSelect.loadDataStore();
		});
	},

	//-------------------------------------------------------------------------
	// Set default parameters always sended to the load request. 
	//-------------------------------------------------------------------------
	setDefaultParams: function(defaultParams) {
		this.defaultParams = defaultParams;
	},

	//-------------------------------------------------------------------------
	// Set parameters sended to the load request. 
	//-------------------------------------------------------------------------
	setLoadParams: function(params) {
		this.getLoader().baseParams = params;
	},

	//-------------------------------------------------------------------------
	// Load the grid with data from load request.
	//-------------------------------------------------------------------------
	loadDataStore: function(params) {
		if (params) {
			for (prop in params) {
				this.getLoader().baseParams[prop] = params[prop];
			}
		}
		if (this.defaultParams) {
			for (prop in this.defaultParams) {
				this.getLoader().baseParams[prop] = this.defaultParams[prop];
			}
		}
		this.getLoader().load(this.root);
	}
}); // eo extend 
