Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Form panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.FormWindow
 * @extends Ext.Window
 * Config options :
 *   formPanelConfig: Object (form panel config options)
 */
HurryApp.FormWindow = Ext.extend(Ext.Window, {
    // superclass properties
	autoHeight: true,
	layout: 'fit',
	plain: true,
	bodyStyle: 'padding:5px;',
	buttonAlign: 'right',
	closeAction: 'close',
	animCollapse: true,
	modal: true,
	resizable: false,
	shadow: false,

    // class properties
	formPanel: null,

    initComponent: function() {
        // form panel
        this.formPanel = new HurryApp.FormPanel(this.initialConfig.formPanelConfig);

        // Apply properties
        Ext.apply(this, {
        	items: [this.formPanel]
        }); // eo apply 
        
        // call parent 
        HurryApp.FormWindow.superclass.initComponent.apply(this, arguments); 
    } // eo function initComponent 
}); // eo extend 
