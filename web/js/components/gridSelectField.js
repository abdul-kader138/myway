Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Form panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.GridSelectField
 * @extends Ext.form.FieldSet
 * Config options :
 *   formPanelConfig: Object (form panel config options)
 */
HurryApp.GridSelectField = Ext.extend(Ext.form.FieldSet, {
    // superclass properties
	autoHeight: true,
	collapsed: false,
	defaults: {
		anchor: '100%'
	},

    // class properties
	gridSelect: null,
	selectedIds: null,

    initComponent: function() {
    	var gridSelectField = this;
        // Hidden field
        this.selectedIds = new Ext.form.Hidden({id: this.initialConfig.id, name: this.initialConfig.name});
        
        // Select grid
        this.initialConfig.id = null;
        this.initialConfig.header = false;
        this.initialConfig.frame = false;
        this.initialConfig.fnAfterTransferItems = function() {
    		gridSelectField.selectedIds.setValue(gridSelectField.gridSelect.getIds());
	    };
        this.gridSelect = new HurryApp.GridSelect(this.initialConfig);

        // Apply properties
        Ext.apply(this, {
			items: [this.gridSelect, this.selectedIds]
        }); // eo apply 
        
        // call parent 
        HurryApp.GridSelectField.superclass.initComponent.apply(this, arguments); 
    } // eo function initComponent
}); // eo extend 

Ext.reg('gridSelectField', HurryApp.GridSelectField);