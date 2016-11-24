Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Trigger field with grid
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.TriggerFieldGrid
 * @extends Ext.form.TriggerField
 * Config options :
 * 
 *   urlSearch: String
 * 
 *   formWindowTitle: String
 *   labelCancelButton: String
 * 
 *   gridWindowJSONDataNames: Array of strings
 *   gridWindowColumns: Array of objects
 *   searchFields: Array of objects (field configs)
 *   
 *   formWindowHeight: Integer
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 * 
 *   hideHeaders: Boolean
 */
HurryApp.TriggerFieldGrid = Ext.extend(Ext.form.TriggerField,  {
    // class properties
	hideHeaders: true,
	gridWindow: null,

	formWindow: null,
	formWindowHeight:   200,
	formWindowWidth:    400,
	formWindowMinWidth: 400,
	formWindowTitle:    FORM_WINDOW_TITLE,
	labelCancelButton:  LABEL_CLOSE_BUTTON,

    initComponent: function() {
		var triggerField = this;

		// labels
    	if (this.initialConfig.formWindowTitle)     {this.formWindowTitle     = this.initialConfig.formWindowTitle;} else {this.formWindowTitle = this.initialConfig.title;}
    	if (this.initialConfig.labelCancelButton)   {this.labelCancelButton   = this.initialConfig.labelCancelButton;}
    	
		// Misc
    	if (!this.initialConfig.gridWindowJSONDataNames) {this.initialConfig.gridWindowJSONDataNames = this.initialConfig.gridJSONDataNames;}
    	if (!this.initialConfig.gridWindowColumns)       {this.initialConfig.gridWindowColumns = this.initialConfig.columns;}

		// form window size
		if (this.initialConfig.formWindowHeight)   {this.formWindowHeight   = this.initialConfig.formWindowHeight;}
		if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
		if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
		
        // Apply properties
        Ext.apply(this, {}); 
        
        // call parent 
        HurryApp.TriggerFieldGrid.superclass.initComponent.apply(this, arguments); 
    }, 

	getValue : function(){
		return HurryApp.TriggerFieldGrid.superclass.getValue.call(this) || "";
	},

	// Set the current value of our text field.
	setValue : function(text){
		HurryApp.TriggerFieldGrid.superclass.setValue.call(this, text);
	},

	// Trigger button clicked, create and show chooser dialog.
	onTriggerClick: function() {
		var triggerField = this;

		if(this.disabled){
			return;
		}
		
		// cancel button
		var cancelButton = {
			text: this.labelCancelButton,
			handler: function(){
				triggerField.formWindow.close();
			}
		}
		var buttons = [cancelButton];
		
		// search fields
		/*
		var gridMenubar;
		var menubarElements = HurryApp.Grid.prototype.getMenuBarSearchFields(false, this.initialConfig.searchFields);
		if (menubarElements.length > 0) {
			gridMenubar = new Ext.Toolbar(menubarElements);
		}
		*/

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
			defaultParams: this.initialConfig.defaultParams,
			gridJSONDataNames: this.initialConfig.gridWindowJSONDataNames,
			fnBeforeTransferItems: this.initialConfig.fnBeforeTransferItems,
			fnAfterTransferItems: function() {
				triggerField.formWindow.close();
			}
	    });

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
	}, 
	
	//-------------------------------------------------------------------------
	// Search
	//-------------------------------------------------------------------------
	search: function() {
		this.gridWindow.search();
	}
});

Ext.reg('triggerfieldgrid', HurryApp.TriggerFieldGrid);
