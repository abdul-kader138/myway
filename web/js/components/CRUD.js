Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// CRUD
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.CRUD
 * @extends Ext.Panel
 * Config options :
 * 
 *   urlLoad: String
 *   urlSave: String
 *   urlEdit: String
 *   urlDelete: String
 * 
 *   formWindowTitle: String
 *   labelCancelButton: String
 * 
 *   formJSONDataNames: Array of strings
 *   createFields: Array of objects (field configs)
 *   editFields: Array of objects (field configs)
 *   editButtons: Array of objects (button configs)
 *   formWindowWidth: Integer
 *   formWindowMinWidth: Integer
 *   formLabelWidth: Integer
 */
HurryApp.CRUD = Ext.extend(Ext.Panel, {
    // superclass properties
    frame: true,

    // class properties
    urlSave: null,
    urlEdit: null,
    urlDelete: null,
	defaultParams: {},
    
    formWindowTitle:     FORM_WINDOW_TITLE,
	labelCancelButton:   LABEL_CANCEL_BUTTON,
	labelDeleteQuestion: LABEL_DELETE_QUESTION,
	labelDeleteAdvert:   LABEL_DELETE_ADVERT,
    
    formWindow: null,
    formWindowWidth:    400,
    formWindowMinWidth: 400,
    formLabelWidth:     80,
    
	messageTemplate: null,

    initComponent: function() {
        var crud = this;
        // urls
        this.urlSave = this.initialConfig.urlSave;
        this.urlEdit = this.initialConfig.urlEdit;
        this.urlDelete = this.initialConfig.urlDelete;

        // labels
        if (this.initialConfig.formWindowTitle)     {this.formWindowTitle     = this.initialConfig.formWindowTitle;}
    	if (this.initialConfig.labelCancelButton)   {this.labelCancelButton   = this.initialConfig.labelCancelButton;}
    	if (this.initialConfig.labelDeleteQuestion) {this.labelDeleteQuestion = this.initialConfig.labelDeleteQuestion;}
    	if (this.initialConfig.labelDeleteAdvert)   {this.labelDeleteAdvert   = this.initialConfig.labelDeleteAdvert;}

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
			var fields = this.initialConfig.editFields;
			if (this.initialConfig.createFields.length > this.initialConfig.editFields.length) {
				fields = this.initialConfig.createFields;
			}
            this.initialConfig.formJSONDataNames = HurryApp.Utils.toArrayOfString(fields, 'id');
            this.initialConfig.formJSONDataNames = this.initialConfig.formJSONDataNames.concat(HurryApp.Utils.toArrayOfString(fields, 'hiddenId'));
        }

        // form window size
        if (this.initialConfig.formWindowWidth)    {this.formWindowWidth    = this.initialConfig.formWindowWidth;}
        if (this.initialConfig.formWindowMinWidth) {this.formWindowMinWidth = this.initialConfig.formWindowMinWidth;}
        if (this.initialConfig.formLabelWidth)     {this.formLabelWidth     = this.initialConfig.formLabelWidth;}

        // panel template
        if (this.initialConfig.panelTemplate) {
            this.messageTemplate = new Ext.XTemplate(this.initialConfig.panelTemplate);
        }

        // misc
        if (!this.initialConfig.editButtons) {this.initialConfig.editButtons = []};

        // Apply properties
        Ext.apply(this, {
        }); // eo apply 
        
        // call parent 
        HurryApp.CRUD.superclass.initComponent.apply(this, arguments); 

    }, // eo function initComponent 
    
    //-------------------------------------------------------------------------
    // Create form window
    //-------------------------------------------------------------------------
    createFormWindow: function(formType, panelId) {
        var crud = this;
        
        // cancel button
        var cancelButton = {
            text: this.labelCancelButton,
            handler: function(){
                crud.formWindow.close();
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
                successCallback: function(response) {
                    crud.formWindow.close();
					if (crud.initialConfig.successCallback) {
						crud.initialConfig.successCallback.call(crud, response);
					}
					
                    if (crud.messageTemplate) {
                    	crud.messageTemplate.overwrite(panelId, response);
                    }
                },
                otherButtons: buttons,
				fileUpload: this.initialConfig.fileUpload ? this.initialConfig.fileUpload : false,
				timeout: this.initialConfig.timeout ? this.initialConfig.timeout : 30
            }
        });
    },
    
    //-------------------------------------------------------------------------
    // Edit entity
    //-------------------------------------------------------------------------
    editEntity: function(selectedId, panelId) {
        this.createFormWindow('edit', panelId);
        this.formWindow.show(this);
    
        // load form and assign value to fields
        this.formWindow.formPanel.form.load({
            url: this.urlEdit+'?viewBean.id='+selectedId, 
            waitMsg: Ext.LoadMask.prototype.msg
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
    createEntity: function(panelId) {
        this.createFormWindow('create', panelId);
        this.formWindow.show(this);
        this.formWindow.formPanel.getForm().setValues(this.defaultParams);
    },
	
	//-------------------------------------------------------------------------
	// Delete entity
	//-------------------------------------------------------------------------
	deleteEntity: function(ids) {
		var crud = this;
		if(ids.length > 0) {
			Ext.MessageBox.confirm('Message', this.labelDeleteQuestion, 
				function(btn) {
					 if(btn == 'yes') {
						// delete request
						HurryApp.Utils.sendAjaxRequest(crud.urlDelete, {'selectedIds' : HurryApp.Utils.arrayToString(ids)}, function(jsonResponse) {
							if (crud.initialConfig.successCallback) {
								crud.initialConfig.successCallback.call(crud);
							}
						});
					}
				}
			);	
		}
		else {
			Ext.MessageBox.alert('Erreur', this.labelDeleteAdvert);
		}
	},

    //-------------------------------------------------------------------------
    // Set default params
    //-------------------------------------------------------------------------
	setDefaultParams: function(defaultParams) {
		this.defaultParams = defaultParams;
	}

}); // eo extend 
