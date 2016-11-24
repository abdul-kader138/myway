Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Grid Select
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.GridSelect
 * @extends HurryApp.Grid
 * Config options :
 * 
 *   urlSearch: String
 *   urlLoad: String
 *   loadParams: Object
 *   urlSave: String
 *   saveParamName: String
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
 *   gridJSONDataNames: Array of strings
 *   gridWindowJSONDataNames: Array of strings
 *   gridWindowColumns: Array of objects
 *   searchFields: Array of objects (field configs)
 *   
 *   formWindowHeight: Integer
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 * 
 *   displayMenuBar: Boolean
 *   loadOnStartup: Boolean
 *   getRowClass: Function
 *   hideHeaders: Boolean
 */
HurryApp.GridSelect = Ext.extend(HurryApp.Grid, {
    // superclass properties
	frame: false,
	hideHeaders: true,
	loadOnStartup: false,

    // class properties
	parentId: '',
	gridWindow: null,

	formWindowTitle:     FORM_WINDOW_TITLE,
	labelCancelButton:   LABEL_CLOSE_BUTTON,
	labelAddButton:      LABEL_ADD_BUTTON,
	tooltipAddButton:    TOOLTIP_ADD_BUTTON,
	labelDeleteButton:   LABEL_DELETE_BUTTON,
	tooltipDeleteButton: TOOLTIP_DELETE_BUTTON,
	labelDeleteQuestion: LABEL_DELETE_QUESTION,
	labelDeleteAdvert:   LABEL_DELETE_ADVERT,

	formWindow: null,
	formWindowHeight:   200,
	formWindowWidth:    400,
	formWindowMinWidth: 400,
	formLabelWidth:     80,

	displayMenuBar: true,
	
    initComponent: function() {
		var gridSelect = this;

		// labels
    	if (this.initialConfig.formWindowTitle)     {this.formWindowTitle     = this.initialConfig.formWindowTitle;} else {this.formWindowTitle = this.initialConfig.title;}
    	if (this.initialConfig.labelCancelButton)   {this.labelCancelButton   = this.initialConfig.labelCancelButton;}
    	if (this.initialConfig.labelAddButton)      {this.labelAddButton      = this.initialConfig.labelAddButton;}
    	if (this.initialConfig.tooltipAddButton)    {this.tooltipAddButton    = this.initialConfig.tooltipAddButton;}
    	if (this.initialConfig.labelDeleteButton)   {this.labelDeleteButton   = this.initialConfig.labelDeleteButton;}
    	if (this.initialConfig.tooltipDeleteButton) {this.tooltipDeleteButton = this.initialConfig.tooltipDeleteButton;}
    	if (this.initialConfig.labelDeleteQuestion) {this.labelDeleteQuestion = this.initialConfig.labelDeleteQuestion;}
    	if (this.initialConfig.labelDeleteAdvert)   {this.labelDeleteAdvert   = this.initialConfig.labelDeleteAdvert;}
    	
		// Columns
		var idColumn = {id: 'id', dataIndex: 'id', hidden: true, header: COMMON_ID};
		this.initialConfig.columns = [idColumn].concat(this.initialConfig.columns);

    	// JSON data names
		if (this.initialConfig.gridJSONDataNames) {
			this.initialConfig.gridJSONDataNames = ['id'].concat(this.initialConfig.gridJSONDataNames);
		}
		else {
			this.initialConfig.gridJSONDataNames = HurryApp.Utils.toArrayOfString(this.initialConfig.columns, 'dataIndex');
		}

		// Misc
    	if (!this.initialConfig.gridWindowJSONDataNames) {this.initialConfig.gridWindowJSONDataNames = this.initialConfig.gridJSONDataNames;}
    	if (!this.initialConfig.gridWindowColumns) {this.initialConfig.gridWindowColumns = this.initialConfig.columns;}
		if (this.initialConfig.displayMenuBar == false) {this.displayMenuBar = false};

		// form window size
		if (this.initialConfig.formWindowHeight)   {this.formWindowHeight   = this.initialConfig.formWindowHeight;}
		if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
		if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
		if (this.initialConfig.formLabelWidth)     {this.formLabelWidth     = this.initialConfig.formLabelWidth;}
		
		// menu bar
		var menubarElements = new Array();
		var nbMenubarElelements = 0;
		
		menubarElements[nbMenubarElelements++] = {
			id: 'addButton-'+(this.initialConfig.idExtension ? this.initialConfig.idExtension : this.initialConfig.type),
			text: this.labelAddButton,
			tooltip: this.tooltipAddButton,
			iconCls: 'tool-add',
			handler: function(){
				gridSelect.selectEntity();
			}
		}

		menubarElements[nbMenubarElelements++] = {
			id: 'deleteButton-'+(this.initialConfig.idExtension ? this.initialConfig.idExtension : this.initialConfig.type),
			text: this.labelDeleteButton,
			tooltip: this.tooltipDeleteButton,
			iconCls: 'tool-remove',
			handler: function(){
				gridSelect.fnAfterTransferItems = saveElements;
				gridSelect.transferSelectedItems();
			}
		}

		//menubarElements = menubarElements.concat(this.getMenuBarSearchFields(true));

		var menubar = new Ext.Toolbar(menubarElements);

		if (this.displayMenuBar) {
			this.gridMenubar = menubar;
		}
		
        // Apply properties
        Ext.apply(this, {
			tbar: this.gridMenubar
        }); // eo apply 
        
        // call parent 
        HurryApp.GridSelect.superclass.initComponent.apply(this, arguments); 

		if (this.initialConfig.urlSave) {
			// Save elements
			var saveElements = function() {
				HurryApp.Utils.sendAjaxRequest(
					gridSelect.initialConfig.urlSave+'?viewBean.id='+gridSelect.parentId+'&'+gridSelect.initialConfig.collectionName+'='+gridSelect.getIds(),
					{},
					function() {
						if (gridSelect.formWindow) {
							gridSelect.formWindow.close();
						}
					}
				);
			}
			this.initialConfig.fnAfterTransferItems = saveElements;
			//this.store.on('add', saveElements);
			//this.store.on('remove', saveElements);
		}
    }, // eo function initComponent 
    
	//-------------------------------------------------------------------------
	// Create form window
	//-------------------------------------------------------------------------
	selectEntity: function() {
		var gridSelect = this;
		
		// cancel button
		/*
		var cancelButton = {
			text: this.labelCancelButton,
			handler: function(){
				gridSelect.formWindow.close();
			}
		}
		var buttons = [cancelButton];
		*/
		
		// add button
		var addButton = {
			text: this.labelAddButton,
			handler: function(){
				gridSelect.gridWindow.transferSelectedItems();
			}
		}
		var buttons = [addButton];
		
		// grid window
		this.gridWindow = new HurryApp.Grid({
			columns: this.initialConfig.gridWindowColumns,
			type: this.initialConfig.type,
			width: this.formWindowWidth-25,
			height: this.formWindowHeight,
			hideHeaders: this.hideHeaders,
			autoExpandColumn: this.initialConfig.autoExpandColumn,
			urlLoad: this.initialConfig.urlSearch,
			searchFields: this.initialConfig.searchFields,
			defaultParams: this.defaultParamsGridWindow ? this.defaultParamsGridWindow : this.defaultParams,
			gridJSONDataNames: this.initialConfig.gridWindowJSONDataNames,
			fnAfterTransferItems: this.initialConfig.fnAfterTransferItems,
			loadOnStartup: false
	    });
		this.gridWindow.on('rowdblclick', this.gridWindow.transferSelectedItems);
	    this.gridWindow.setLinkedGrid(gridSelect);
	    gridSelect.setLinkedGrid(this.gridWindow);

    	this.formWindow = new Ext.Window({
			title: this.formWindowTitle,
			width: this.formWindowWidth,
			minWidth: this.formWindowMinWidth,
			height: this.formWindowHeight,
			animTarget: this,
			layout: 'fit',
			plain: true,
			bodyStyle: 'padding:5px;',
			buttonAlign: 'right',
			closeAction: 'close',
			animCollapse: true,
			modal: false,
			items: [this.gridWindow],
			buttons: buttons
		});
		this.formWindow.show(this);
		this.gridWindow.loadDataStore();
	}, // eo function initComponent 
	
	//-------------------------------------------------------------------------
	// Set parent id and refresh
	//-------------------------------------------------------------------------
	refresh: function(parentId) {
		this.parentId = parentId;
		this.setLoadParams({'viewBean.parentId': parentId});
		this.loadDataStore();
	},
	
	//-------------------------------------------------------------------------
	// Set defaultParams for gridWindow
	//-------------------------------------------------------------------------
	setDefaultParamsGridWindow: function(defaultParamsGridWindow) {
		this.defaultParamsGridWindow = defaultParamsGridWindow;
	}
	
}); // eo extend 

Ext.reg('gridSelect', HurryApp.GridSelect);