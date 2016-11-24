Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Progress window
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.ProgressWindow
 * @extends Ext.Window
 * Config options :
 */
HurryApp.ProgressWindow = Ext.extend(Ext.Window, {
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
	width: 300,
	title : Ext.LoadMask.prototype.msg,
	
    // class properties
	progressBar: null,

    initComponent: function() {
        // Progress bar
        this.progressBar = new Ext.ProgressBar();

        // Apply properties
        Ext.apply(this, {
        	items: [this.progressBar]
        }); // eo apply 
        
        // call parent 
        HurryApp.FormWindow.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent
    
	updateProgress: function(value) {
		this.progressBar.updateProgress(value, '', true);
	} 
}); // eo extend 
