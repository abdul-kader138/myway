/*!
 * Ext JS Library 3.1.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.form');

function checkFileExtension(fieldId, fileType) {
	var fileField = document.getElementById(fieldId);
	var str = fileField.value.toUpperCase();

	if (fileType == 'image') {
		if(!(str.indexOf(MEDIA_SUFFIX_1, str.length - MEDIA_SUFFIX_1.length) !== -1 ||
			//str.indexOf(MEDIA_SUFFIX_2, str.length - MEDIA_SUFFIX_2.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_3, str.length - MEDIA_SUFFIX_3.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_4, str.length - MEDIA_SUFFIX_4.length) !== -1)){
			HurryApp.MessageUtils.showWarning(COMMON_ERROR_FILEEXTENSION_IMAGE);
			fileField.value='';
			return false;
		}
	}
	else if (fileType == 'image-swf') {
		if(!(str.indexOf(MEDIA_SUFFIX_1, str.length - MEDIA_SUFFIX_1.length) !== -1 ||
			//str.indexOf(MEDIA_SUFFIX_2, str.length - MEDIA_SUFFIX_2.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_3, str.length - MEDIA_SUFFIX_3.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_4, str.length - MEDIA_SUFFIX_4.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_8, str.length - MEDIA_SUFFIX_8.length) !== -1)){
			HurryApp.MessageUtils.showWarning(COMMON_ERROR_FILEEXTENSION_IMAGE_SWF);
			fileField.value='';
			return false;
		}
	}
	else if (fileType == 'media') {
		if(!(str.indexOf(MEDIA_SUFFIX_1, str.length - MEDIA_SUFFIX_1.length) !== -1 ||
			//str.indexOf(MEDIA_SUFFIX_2, str.length - MEDIA_SUFFIX_2.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_3, str.length - MEDIA_SUFFIX_3.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_4, str.length - MEDIA_SUFFIX_4.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_5, str.length - MEDIA_SUFFIX_5.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_6, str.length - MEDIA_SUFFIX_6.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_7, str.length - MEDIA_SUFFIX_7.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_8, str.length - MEDIA_SUFFIX_8.length) !== -1)){
			HurryApp.MessageUtils.showWarning(COMMON_ERROR_FILEEXTENSION_MEDIA);
			fileField.value='';
			return false;
		}
	}
	else if (fileType == 'mediaZip') {
		if(!(str.indexOf(MEDIA_SUFFIX_1, str.length - MEDIA_SUFFIX_1.length) !== -1 ||
			//str.indexOf(MEDIA_SUFFIX_2, str.length - MEDIA_SUFFIX_2.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_3, str.length - MEDIA_SUFFIX_3.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_4, str.length - MEDIA_SUFFIX_4.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_5, str.length - MEDIA_SUFFIX_5.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_6, str.length - MEDIA_SUFFIX_6.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_7, str.length - MEDIA_SUFFIX_7.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_8, str.length - MEDIA_SUFFIX_8.length) !== -1 ||
			str.indexOf(MEDIA_SUFFIX_9, str.length - MEDIA_SUFFIX_9.length) !== -1)){
			HurryApp.MessageUtils.showWarning(COMMON_ERROR_FILEEXTENSION_MEDIA_ZIP);
			fileField.value='';
			return false;
		}
	}
	return true;
}
	
/**
 * @class Ext.ux.form.FileUploadField
 * @extends Ext.form.TextField
 * Creates a file upload field.
 * @xtype fileuploadfield
 */
Ext.ux.form.FileUploadField = Ext.extend(Ext.form.TextField,  {
    /**
     * @cfg {String} buttonText The button text to display on the upload button (defaults to
     * 'Browse...').  Note that if you supply a value for {@link #buttonCfg}, the buttonCfg.text
     * value will be used instead if available.
     */
    buttonText: 'Browse...',
    /**
     * @cfg {Boolean} buttonOnly True to display the file upload field as a button with no visible
     * text field (defaults to false).  If true, all inherited TextField members will still be available.
     */
    buttonOnly: false,
    /**
     * @cfg {Number} buttonOffset The number of pixels of space reserved between the button and the text field
     * (defaults to 3).  Note that this only applies if {@link #buttonOnly} = false.
     */
    buttonOffset: 3,
    /**
     * @cfg {Object} buttonCfg A standard {@link Ext.Button} config object.
     */

    // private
    readOnly: true,

    /**
     * @hide
     * @method autoSize
     */
    autoSize: Ext.emptyFn,
	
	fileType: null,

    // private
    initComponent: function(){
        Ext.ux.form.FileUploadField.superclass.initComponent.call(this);

        this.addEvents(
            /**
             * @event fileselected
             * Fires when the underlying file input field's value has changed from the user
             * selecting a new file from the system file selection dialog.
             * @param {Ext.ux.form.FileUploadField} this
             * @param {String} value The file value returned by the underlying file input field
             */
            'fileselected'
        );
    },

    // private
    onRender : function(ct, position){
		var textField = this;
        Ext.ux.form.FileUploadField.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');
        this.createFileInput();

        var buttonsDiv = this.wrap.createChild({
            tag: 'div', style:'float:right;'
        });
		
		// Main button
        this.leftButtonDiv = buttonsDiv.createChild({
            tag: 'div', style:'float:left;'
        });
        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.leftButtonDiv,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : ''),
			tooltip: COMMON_SELECTFILE
        }));

		// Clear button
		if (this.clearAction) {
	        this.rightButtonDiv = buttonsDiv.createChild({
	            tag: 'div', style:'padding-left:2px; float:right;'
	        });
	        this.clearButton = new Ext.Button({
	            renderTo: this.rightButtonDiv,
	            cls: ' x-btn-icon',
				iconCls: 'tool-remove2',
				tooltip: COMMON_DELETE,
				handler: function(){
					textField.clearAction();
					textField.setValue('');
				}
	        });
		}

		// Preview button
		if (this.previewAction) {
	        this.rightButtonDiv = buttonsDiv.createChild({
	            tag: 'div', style:'padding-left:2px; float:right;'
	        });
	        this.previewButton = new Ext.Button({
	            renderTo: this.rightButtonDiv,
	            cls: ' x-btn-icon',
				iconCls: 'tool-preview',
				tooltip: COMMON_PREVIEW,
				handler: function(){
					textField.previewAction();
				}
	        });
		}

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    },
    
    bindListeners: function(){
        this.fileInput.on({
            scope: this,
            mouseenter: function() {
                this.button.addClass(['x-btn-over','x-btn-focus'])
            },
            mouseleave: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            mousedown: function(){
                this.button.addClass('x-btn-click')
            },
            mouseup: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            change: function(){
                var v = this.fileInput.dom.value;
                this.setValue(v);
                this.fireEvent('fileselected', this, v);    
            }
        }); 
    },
    
    createFileInput : function() {
        var fileInputDiv = this.wrap.createChild({
            cls: 'x-form-file',
            tag: 'div',
			style: (this.clearAction && this.previewAction) ? 'margin-right:50px;' : (this.clearAction || this.previewAction ? 'margin-right:25px;' : '')
        });
        this.fileInput = fileInputDiv.createChild({
            id: this.getFileInputId(),
            name: this.name||this.getId(),
            cls: 'x-form-file',
            tag: 'input',
            type: 'file',
            size: 1,
			onchange: 'checkFileExtension(\''+this.getFileInputId()+'\', \''+this.fileType+'\')'
        });
    },
    
    reset : function(){
        this.fileInput.remove();
        this.createFileInput();
        this.bindListeners();
        Ext.ux.form.FileUploadField.superclass.reset.call(this);
    },

    // private
    getFileInputId: function(){
        return this.id + '-file';
    },

    // private
    onResize : function(w, h){
        Ext.ux.form.FileUploadField.superclass.onResize.call(this, w, h);

        this.wrap.setWidth(w);

        if(!this.buttonOnly){
			var deltaWidth = this.button.getEl().getWidth();
			if (this.initialConfig.clearAction || this.initialConfig.previewAction) {
				if(this.initialConfig.clearAction && this.initialConfig.previewAction) {
					deltaWidth += this.button.getEl().getWidth()*2 + 2;
				}
				else {
					deltaWidth += this.button.getEl().getWidth() + 2;
				}
				
			}
            var w = this.wrap.getWidth() - deltaWidth - this.buttonOffset;
            this.el.setWidth(w);
        }
    },

    // private
    onDestroy: function(){
        Ext.ux.form.FileUploadField.superclass.onDestroy.call(this);
        Ext.destroy(this.fileInput, this.button, this.wrap);
    },
    
    onDisable: function(){
        Ext.ux.form.FileUploadField.superclass.onDisable.call(this);
        this.doDisable(true);
    },
    
    onEnable: function(){
        Ext.ux.form.FileUploadField.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    // private
    doDisable: function(disabled){
        this.fileInput.dom.disabled = disabled;
        this.button.setDisabled(disabled);
    },


    // private
    preFocus : Ext.emptyFn,

    // private
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});

Ext.reg('fileuploadfield', Ext.ux.form.FileUploadField);

// backwards compat
Ext.form.FileUploadField = Ext.ux.form.FileUploadField;
