ThemePanel = function() {
	
	
	//-----------------------------------------------------------------------------
	// Upload a file
	//-----------------------------------------------------------------------------
	var uploadFile = function(formPanel, imagePanelId, urlIndex) {
		var mask = new Ext.LoadMask(formPanel.id, {});
		mask.show();
		
		var submitFormFn = function(form, action){
			mask.hide();
			if (action.response) {
				var jsonResponse = Ext.util.JSON.decode(action.response.responseText);
				if (jsonResponse.status == 200) { // OK response
					var urls = jsonResponse.datas;
					//setImagePreview(imagePanelId, urls[urlIndex]);
					//Ext.get(imageId).dom.src = myAppContext + '/' + urls[urlIndex];
					HurryApp.MessageUtils.showJsonResponse(jsonResponse);
				}
				else {
					HurryApp.Utils.checkResponseErrors(action.response);
				}
			}
		}
		
		formPanel.getForm().submit({
			url: myAppContext + '/theme/uploadFiles',
			failure: submitFormFn,
			success: submitFormFn
		});
	}
	
	//-----------------------------------------------------------------------------
	// Delete a file
	//-----------------------------------------------------------------------------
	var deleteFile = function(fileType, panelId) {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/theme/deleteFile',
			{
				'viewBean.id': window.projectId,
				fileType: fileType
			},
			function(jsonResponse) {
				//setImagePreview(panelId, '');
			},
			'themeEditPanel'
		);
	}
	
	//-----------------------------------------------------------------------------
	// Load form
	//-----------------------------------------------------------------------------
	var load = function() {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/theme/edit',
			{
				'viewBean.projectId': window.projectId
			},
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				themePanel.form.setValues(datas);
				updateFields();
				addListeners();
			},
			'themeEditPanel'
		);
	}
	
	var previewImage = function(file, type) {
		if(file == undefined || file == "") return;
		var previewUrl = myAppContext+'/project-exports/'+window.projectKey+'/theme/'+type+'/'+file;
		$.fancybox( {href : previewUrl, title : 'Image preview'} );
	}
	
	//-----------------------------------------------------------------------------
	// Settings panel
	//-----------------------------------------------------------------------------
	var fields = {
		xtype: 'panel',
		border: false,
		layout: 'hbox',
		cls: 'ha-plain-panel',
		items: [
			{
				xtype:'fieldset',
				title: THEME_LANDSCAPE,
				id: 'landscapeFieldset',
				width: 400,
				height: 455,
				autoHeight: false,
				style: '',
				cls: 'ha-plain-panel',
				defaults: {
		            anchor: '100%',
		        },
				items: [
					{
						xtype: 'hidden',
						id: 'id',
						name: 'viewBean.id'
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Left Panel",
						allowBlank: true,
						id: 'leftPanel',
						name: 'viewBean.leftPanel',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('leftPanel', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('leftPanel', '');
						},
						previewAction: function() {
							var url = $("#leftPanel").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "leftPanel");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Background",
						allowBlank: true,
						id: 'landscapeBackground',
						name: 'viewBean.landscapeBackground',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('landscapeBackground', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('landscapeBackground', '');
						},
						previewAction: function() {
							var url = $("#landscapeBackground").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "landscapeBackground");
						}
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_LIGHT_FONT_COLOR,
						allowBlank: true,
						id: 'landscapeLightFontColor',
						name: 'viewBean.landscapeLightFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_TEXT_FONT_COLOR,
						allowBlank: true,
						id: 'landscapeTextFontColor',
						name: 'viewBean.landscapeTextFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_DARK_FONT_COLOR,
						allowBlank: true,
						id: 'landscapeDarkFontColor',
						name: 'viewBean.landscapeDarkFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_PROMOTION_HIGHLIGHT_COLOR,
						allowBlank: true,
						id: 'landscapePromotionHighlightColor',
						name: 'viewBean.landscapePromotionHighlightColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_CATEGORY_DEFAULT_COLOR,
						allowBlank: true,
						id: 'landscapeCategoryDefaultColor',
						name: 'viewBean.landscapeCategoryDefaultColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_CATEGORY_SELECTED_COLOR,
						allowBlank: true,
						id: 'landscapeCategorySelectedColor',
						name: 'viewBean.landscapeCategorySelectedColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_HEADER_COLOR,
						allowBlank: true,
						id: 'landscapeNavigationHeaderColor',
						name: 'viewBean.landscapeNavigationHeaderColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_HEADER_FONT_COLOR,
						allowBlank: true,
						id: 'landscapeNavigationHeaderFontColor',
						name: 'viewBean.landscapeNavigationHeaderFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_BACKGROUND_COLOR,
						allowBlank: true,
						id: 'landscapeNavigationBackgroundColor',
						name: 'viewBean.landscapeNavigationBackgroundColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_SELECTED_BACKGROUND_COLOR,
						allowBlank: true,
						id: 'landscapeNavigationSelectedBackgroundColor',
						name: 'viewBean.landscapeNavigationSelectedBackgroundColor'
					}
				]
			}
			,{
				xtype: 'fieldset',
				title: THEME_PORTRAIT,
				id: 'portraitFieldset',
				width: 400,
				height: 455,
				autoHeight: false,
				style: 'margin-left:10px;',
				defaults: {
		            anchor: '100%',
		        },
				items: [
			        {
						xtype: 'fileuploadfield',
						fieldLabel: "Top Panel",
						allowBlank: true,
						id: 'topPanel',
						name: 'viewBean.topPanel',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('topPanel', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('topPanel', '');
						},
						previewAction: function() {
							var url = $("#topPanel").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "topPanel");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Background",
						allowBlank: true,
						id: 'portraitBackground',
						name: 'viewBean.portraitBackground',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('portraitBackground', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('portraitBackground', '');
						},
						previewAction: function() {
							var url = $("#portraitBackground").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "portraitBackground");
						}
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_LIGHT_FONT_COLOR,
						allowBlank: true,
						id: 'portraitLightFontColor',
						name: 'viewBean.portraitLightFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_TEXT_FONT_COLOR,
						allowBlank: true,
						id: 'portraitTextFontColor',
						name: 'viewBean.portraitTextFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_DARK_FONT_COLOR,
						allowBlank: true,
						id: 'portraitDarkFontColor',
						name: 'viewBean.portraitDarkFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_PROMOTION_HIGHLIGHT_COLOR,
						allowBlank: true,
						id: 'portraitPromotionHighlightColor',
						name: 'viewBean.portraitPromotionHighlightColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_CATEGORY_DEFAULT_COLOR,
						allowBlank: true,
						id: 'portraitCategoryDefaultColor',
						name: 'viewBean.portraitCategoryDefaultColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_CATEGORY_SELECTED_COLOR,
						allowBlank: true,
						id: 'portraitCategorySelectedColor',
						name: 'viewBean.portraitCategorySelectedColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_HEADER_COLOR,
						allowBlank: true,
						id: 'portraitNavigationHeaderColor',
						name: 'viewBean.portraitNavigationHeaderColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_HEADER_FONT_COLOR,
						allowBlank: true,
						id: 'portraitNavigationHeaderFontColor',
						name: 'viewBean.portraitNavigationHeaderFontColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_BACKGROUND_COLOR,
						allowBlank: true,
						id: 'portraitNavigationBackgroundColor',
						name: 'viewBean.portraitNavigationBackgroundColor'
					}
					,{
						xtype: 'colorfield',
						fieldLabel: THEME_NAVIGATION_SELECTED_BACKGROUND_COLOR,
						allowBlank: true,
						id: 'portraitNavigationSelectedBackgroundColor',
						name: 'viewBean.portraitNavigationSelectedBackgroundColor'
					}
				]
			}
			,{
				xtype: 'fieldset',
				title: THEME_ITEM_WINDOW,
				id: 'itemWindowFieldset',
				width: 350,
				height: 455,
				autoHeight: false,
				style: 'margin-left:20px;',
				defaults: {
		            anchor: '100%',
		        },
				items: [
			        {
						xtype: 'fileuploadfield',
						fieldLabel: "Header Background",
						allowBlank: true,
						id: 'iwHeaderBackground',
						name: 'viewBean.iwHeaderBackground',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwHeaderBackground', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwHeaderBackground', '');
						},
						previewAction: function() {
							var url = $("#iwHeaderBackground").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwHeaderBackground");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Touch Wall",
						allowBlank: true,
						id: 'iwTouchWall',
						name: 'viewBean.iwTouchWall',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwTouchWall', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwTouchWall', '');
						},
						previewAction: function() {
							var url = $("#iwTouchWall").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwTouchWall");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Main Background",
						allowBlank: true,
						id: 'iwMainBackground',
						name: 'viewBean.iwMainBackground',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwMainBackground', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwMainBackground', '');
						},
						previewAction: function() {
							var url = $("#iwMainBackground").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwMainBackground");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Footer Background",
						allowBlank: true,
						id: 'iwFooterBackground',
						name: 'viewBean.iwFooterBackground',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwFooterBackground', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwFooterBackground', '');
						},
						previewAction: function() {
							var url = $("#iwFooterBackground").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwFooterBackground");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Button",
						allowBlank: true,
						id: 'iwButton',
						name: 'viewBean.iwButton',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwButton', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwButton', '');
						},
						previewAction: function() {
							var url = $("#iwButton").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwButton");
						}
					}
					,{
						xtype: 'fileuploadfield',
						fieldLabel: "Selected Button",
						allowBlank: true,
						id: 'iwSelectedButton',
						name: 'viewBean.iwSelectedButton',
						buttonText: '',
						buttonCfg: {
							iconCls: 'tool-add-image'
						},
						fileType: 'image',
						listeners: {'fileselected': function(field, value) {
							if (checkFileExtension('iwSelectedButton', 'image')) {
								uploadFile(themePanel, '', 1);
							}
						}},
						clearAction: function() {
							deleteFile('iwSelectedButton', '');
						},
						previewAction: function() {
							var url = $("#iwSelectedButton").val();
							previewImage(url.substring(url.lastIndexOf('\\') + 1), "iwSelectedButton");
						}
					}
				]
			}
		]
	};

	var themePanel = new Ext.form.FormPanel({
		region: 'center',
		width: '100%',
		autoHeight: false,
		bodyCssClass: 'ha-panel-body',
	    defaults: {
	        msgTarget: 'side',
	        anchor: '100%' 
	    },
		labelWidth: 80,
		title: THEME_TITRE,
		items: fields,
		fileUpload: true
	});
	

	//-----------------------------------------------------------------------------
	// Save buton
	//-----------------------------------------------------------------------------
	var saveButton = {
		xtype: 'panel',
		region: 'south',
		split: true,
		layout: 'hbox',
		height: 40,
		layoutConfig: {
			pack: 'center'
	    },
		border: false,
		cls: 'ha-plain-panel-lite',
		items: [
			new Ext.Button({
				text: SETTINGS_SAVE,
				scale: 'large',
				iconCls: 'tool-save',
				width: 150,
				handler: function() {
					save();
				},
				listeners: {
					'afterrender': function() {
						load();
					}
				}
			})
		]
	}; 
	
	

	//-----------------------------------------------------------------------------
	// Save data
	//-----------------------------------------------------------------------------
	var save = function() {
		var params = themePanel.form.getValues();
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/theme/save',
			params,
			function(jsonResponse) {
			},
			'themeEditPanel'
		);
		hasPageChanged = false;
	}

	//-----------------------------------------------------------------------------
	// Show/hide dynamic fields
	//-----------------------------------------------------------------------------
	var updateFields = function() {
		
	}
	
	//-----------------------------------------------------------------------------
	// Add listeners on checkboxes and radios
	//-----------------------------------------------------------------------------
	var addListeners = function() {
		
	}

	//-----------------------------------------------------------------------------
	// Main panel
	//-----------------------------------------------------------------------------
	var mainPanel = new Ext.Panel({
		id: 'themeEditPanel',
		region: 'center',
		width: '60%',
		height: '100%',
		border: false,
		layout: 'border',
		items: [
			themePanel,
			saveButton
		]
	});

	return {
		getComponent: function() {
			return mainPanel;
		},
		getSaveFunc: function() {
			return save;
		}
	}
}
