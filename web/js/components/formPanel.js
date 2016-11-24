Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Form panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.FormPanel
 * @extends Ext.form.FormPanel
 * Config options :
 *   successCallback: Function
 *   failureCallback: Function
 *   otherButtons: Array of objects (button configs)
 */
HurryApp.FormPanel = Ext.extend(Ext.form.FormPanel, {
    // superclass properties
	frame: true,
    id: 'form-panel',
    autoHeight: true,
	buttonAlign: 'right',
    defaultType: 'textfield',
	fileUpload: false,
    defaults: {
        msgTarget: 'side',
        allowBlank: true,
        anchor: '90%' 
    },

    // class properties
    // ...
    
    initComponent: function() {
    	var formPanel = this;
    	var statusbar = new Ext.ux.StatusBar({
		    defaultText: '',
			id: 'statusbar-'+formPanel.id,
		    plugins: new Ext.ux.ValidationStatus({form:this.initialConfig.id ? this.initialConfig.id : this.id})
		});
		
		if (this.initialConfig.fieldAnchor) {
			this.defaults.anchor = this.initialConfig.fieldAnchor;
		}
		
        // Apply properties
        Ext.apply(this, {
		    bbar: statusbar,
			buttons: []
        }); // eo apply 

        var submitFormFn = function(form, action) {
			if (action.response) {
				var jsonResponse = Ext.util.JSON.decode(action.response.responseText);
				if ((action.response.status == 200 && jsonResponse.status == 200) || !action.response.status) { // OK response
					
					if (jsonResponse.errors.length == 0) {
						if (formPanel.initialConfig.successCallback) {
						    formPanel.initialConfig.successCallback.call({}, jsonResponse);
						}
					}
					else {
						if (formPanel.initialConfig.failureCallback) {
						    formPanel.initialConfig.failureCallback.call({}, jsonResponse);
						}
					}

					HurryApp.MessageUtils.showJsonResponse(jsonResponse, formPanel.id);
					if (formPanel.initialConfig.finalCallback) {
					    formPanel.initialConfig.finalCallback.call({}, jsonResponse);
					}
				} else {
                	HurryApp.Utils.checkResponseErrors(action.response);
					//Ext.MessageBox.alert('Reponse du serveur invalide : ' + action.response.statusText + '\nErreur : ' + action.response.status);
				}
			}
        };
        
        var submitConfig = {
			waitMsg: COMMON_MESSAGE_PROGRESS,
			failure: submitFormFn,
			success: submitFormFn
		};
        
		this.addButton({
			id: 'okButton-'+formPanel.initialConfig.id,
			xtype: 'button',
			text: 'OK',
			handler: function() {
				if (window.userGroupId != GROUPE_SUPER_ADMIN && window.projectKey && window.projectKey.startsWith('trial') && formPanel.initialConfig.checkDemo != false) {
					HurryApp.MessageUtils.showWarning(COMMON_ERROR_DEMO_ACTIONNOTALLOWED);
				}
				else {
					if (formPanel.initialConfig.beforeSubmit) {
						formPanel.initialConfig.beforeSubmit.call(formPanel);
					}
					if (formPanel.initialConfig.url) {
						statusbar.showBusy(COMMON_MESSAGE_PROGRESS);
						formPanel.form.submit(submitConfig);
					}
					else {
						if (formPanel.initialConfig.successCallback) {
						    formPanel.initialConfig.successCallback.call(formPanel);
						}
					}
				}
			}
		});
        
        // add other buttons
        if (this.initialConfig.otherButtons) {
			for (var i = 0; i < this.initialConfig.otherButtons.length; i++) {
				this.addButton(this.initialConfig.otherButtons[i]);
			}
		}
    
        // call parent 
        HurryApp.FormPanel.superclass.initComponent.apply(this, arguments);
        
    	this.on('afterrender', function() {
    		// Detect 'Enter' pressed
            window.keyMapEnter = new Ext.KeyMap(this.initialConfig.id ? this.initialConfig.id : this.id,{
            	key: Ext.EventObject.ENTER,
            	fn: function(keyCode, eventObject) {
            		Ext.getCmp('okButton-'+formPanel.initialConfig.id).handler.call();
            	},
            	scope: formPanel
            });

            // Set focus to first field
            setTimeout(function() {
	            for (var i = 0; i < formPanel.items.items.length; i++) {
	            	if (formPanel.items.items[i].xtype != 'hidden') {
	            		formPanel.items.items[i].focus();
	            		break;
	            	}
	            }
            }, 1000);
    	});
    } // eo function initComponent 

}); // eo extend 
