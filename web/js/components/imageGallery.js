Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Pad panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.ImageGallery
 * @extends Ext.Panel
 * Config options :
 *   formPanelId: String
 *   dir: String
 *   imageWidth: Integer
 *   imageHeight: Integer
 */
HurryApp.ImageGallery = Ext.extend(Ext.Panel, {
	// superclass properties

	// class properties
	dir: '',
	fileUploadField: null,
	imageDataView: null,
	
	initComponent: function() {
		var imageGallery = this;
		this.dir = imageGallery.initialConfig.dir ? imageGallery.initialConfig.dir : '';
		
		// Add button = file upload field
		this.fileUploadField = new Ext.ux.form.FileUploadField({
			allowBlank: true,
			id: 'image'+imageGallery.initialConfig.id,
			name: 'image',
			buttonText: '',
			buttonOnly: true,
			buttonCfg: {
				iconCls: 'tool-add'
			},
			fileType: 'image',
			listeners: {'fileselected': function(field, value) {
				if (checkFileExtension('image'+imageGallery.initialConfig.id, 'image')) {
					var mask = new Ext.LoadMask(imageGallery.initialConfig.formPanelId ? imageGallery.initialConfig.formPanelId : Ext.getBody(), {});
					mask.show();
					
					var submitFormFn = function(form, action) {
						mask.hide();
						imageGallery.fileUploadField.reset();
						
						if (action.response) {
							var jsonResponse = Ext.util.JSON.decode(action.response.responseText);
							if (jsonResponse.status == 200) { // OK response
								if (jsonResponse.errors.length == 0) {
									var dataView = Ext.getCmp('imagesDataView'+imageGallery.initialConfig.id);
									dataView.store.loadData(HurryApp.Utils.toJsonDatas(dataView.store.data.items).concat({
										dir: imageGallery.dir,
										file: jsonResponse.datas[0]
									}));
									HurryApp.MessageUtils.showJsonResponse(jsonResponse, imageGallery.initialConfig.formPanelId);
								}
								else {
									var errorMsg = '';
									for (var i = 0; i < jsonResponse.errors.length; i++) {
										if (errorMsg != '') {
											errorMsg += '<br/>';
										}
										errorMsg += jsonResponse.errors[i].msg;
									}
									HurryApp.MessageUtils.showWarning(errorMsg);
								}
							} else {
			                	HurryApp.Utils.checkResponseErrors(action.response);
							}
						}
					}
					
					Ext.getCmp(imageGallery.initialConfig.formPanelId).getForm().submit({
						url: myAppContext+'/imageGallery/upload',
						params: {
							dir: imageGallery.dir
						},
						failure: submitFormFn,
						success: submitFormFn
					});
				}
			}}
		});
		
		// Delete button
		var deleteButton = new Ext.Button({
			iconCls: 'tool-remove',
			imageGallery: this,
			handler: function() {
				var dataView = Ext.getCmp('imagesDataView'+this.imageGallery.initialConfig.id);

				if (this.imageGallery.initialConfig.urlDelete) {
					HurryApp.Utils.sendAjaxRequest(
						this.imageGallery.initialConfig.urlDelete,
						{
							'dir': this.imageGallery.dir,
							'fileNames': HurryApp.Utils.arrayToString(HurryApp.Utils.toArrayOfString(HurryApp.Utils.toJsonDatas(dataView.getSelectedRecords()), 'file')).replace(/"/g, '')
						},
						function(jsonResponse) {
							dataView.store.remove(dataView.getSelectedRecords());
							dataView.store.loadData(HurryApp.Utils.toJsonDatas(dataView.store.data.items));
						},
						this.imageGallery.initialConfig.id					
					);
				}
				else {
					dataView.store.remove(dataView.getSelectedRecords());
					dataView.store.loadData(HurryApp.Utils.toJsonDatas(dataView.store.data.items));
				}
			}
		});
						
		// Image data view
		this.imageDataView = new Ext.DataView({
			id: 'imagesDataView'+imageGallery.initialConfig.id,
			cls: 'ha-images-view',
			tpl:'<tpl for=".">'+
		            '<div class="ha-images-view-item" id="">'+
				    '<div class=""><img src="'+myAppContext+'/{dir}/{file}" width="'+imageGallery.initialConfig.imageWidth+'" height="'+imageGallery.initialConfig.imageHeight+'"></div>'+
				    '</div>'+
		        '</tpl>'+
		        '<div class="x-clear"></div>',
			itemSelector: 'div.ha-images-view-item',
			overClass: 'ha-over',
			selectedClass: 'ha-selected',
			singleSelect: true,
			store: {
				xtype: 'jsonstore',
				autoLoad: false,
				fields: ['dir', 'file']
				//,data: [{dir: 'test', file: '1.png'}, {dir: 'test', file: '2.png'}, {dir: 'test', file: '3.png'}, {dir: 'test', file: '4.png'}, {dir: 'test', file: '1.png'}, {dir: 'test', file: '2.png'}, {dir: 'test', file: '3.png'}, {dir: 'test', file: '4.png'}, {dir: 'test', file: '1.png'}, {dir: 'test', file: '2.png'}, {dir: 'test', file: '3.png'}, {dir: 'test', file: '4.png'}]
			}
		});
		
		// Apply properties
		Ext.apply(this, {
			autoScroll: true,
			style: 'background-color: #FFFFFF;',
			tbar: {
				items: [
					this.fileUploadField,
					deleteButton,
					'-',
					{
						xtype: 'label',
						cls: 'ha-field-help',
						text: this.initialConfig.tooltip ? this.initialConfig.tooltip : ''
					}
					
				]
			},
			items: [
				this.imageDataView
			]
		}); // eo apply 
		
		// call parent 
		HurryApp.ImageGallery.superclass.initComponent.apply(this, arguments); 
	} // eo function initComponent 
}); // eo extend 

Ext.reg('imagegallery', HurryApp.ImageGallery);
