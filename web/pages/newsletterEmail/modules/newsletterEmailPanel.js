NewsletterEmailPanel = function() {
	var PREVIEW_HEIGHT = 70;
	
	//-----------------------------------------------------------------------------
	// Newsletter panel
	//-----------------------------------------------------------------------------
	var TAB_PANEL_WIDTH = 300;
	var tabsNewsletter = [];

	// Tab languages
	for (var i=0; i<languages.length; i++) {
		tabsNewsletter.push({
			xtype: 'panel',
			title: languages[i].name,
			id: 'tab'+languages[i].flag,
			layout: 'form',
			bodyCssClass: 'ha-panel-body',
			iconCls: 'ux-flag-'+languages[i].flag,
			width: TAB_PANEL_WIDTH,
			defaults: {
	        	anchor: '100%'
			},
			autoShow: true,
			items: [
				{
					xtype: 'hidden',
					id: 'projectContents['+i+'].languageId',
					name: 'viewBean.projectContents['+i+'].languageId',
					value: languages[i].id
				}
				,{
					xtype: 'hidden',
					id: 'projectContents['+i+'].languageCode',
					name: 'viewBean.projectContents['+i+'].languageCode',
					value: languages[i].flag
				}
				,{
					xtype: 'hidden',
					id: 'projectContents['+i+'].id',
					name: 'viewBean.projectContents['+i+'].id'
				}
				,{
					xtype: 'textarea',
					fieldLabel: PROJECT_NEWSLETTER_TERMS,
					allowBlank: true,
					height: 230,
					id: 'projectContents['+i+'].newsletterTerms',
					name: 'viewBean.projectContents['+i+'].newsletterTerms',
					listeners: { 'change': function() {
							hasPageChanged = true;
						}
					}
				}
				/*,{
					xtype: 'panel',
					layout: 'form',
					border: false,
					cls: 'ha-plain-panel',
					items: [
						{
							xtype: 'panel',
							height: 1,
							width: '73%',
							border: false,
							style: 'margin: 10px auto 0 auto; border-top: 1px solid #D0D0D0; border-bottom: 1px solid #ffffff;'
						}
						,{
							xtype: 'panel',
							border: false,
							html: '<div><span id="translateTooltipTitle-'+i+'" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
							style: 'margin: 10px auto 0 580px;',
							i: i,
							listeners: {'afterrender': function() {translateTooltip('translateTooltipTitle-'+this.i+'');}}
						}
						,{
							xtype: 'button',
							id: 'button-translate['+i+']',
							cls: 'button-translate',
							text: ITEM_TRANSLATE,
							scale: 'large',
							width: '50%',
							allowBlank: true,
							style: 'margin: auto;',
							langId: languages[i].id,
							tabIndex: i,
							handler: function() {translate(this.langId, this.tabIndex);},
							iconCls: 'ux-flag-'+languages[i].flag
						}
					]
				}*/
			]
		});
	};
	
	var tabPanelNewsletter = {
		xtype: 'tabpanel',
		id: 'tabPanelNewsletter',
		//layout: 'border',
		plain: true,
		width: TAB_PANEL_WIDTH,
		height: 265,
		minTabWidth: 80,
		resizeTabs: true,
		enableTabScroll:true,
		autoScroll: true,
		animScroll: false,
		scrollDuration: .35,
		activeTab: 0,
		deferredRender: false,
		anchor: '100%',
		items: tabsNewsletter
	};

	var initTabs = function() {
		var tabPanel = Ext.getCmp('tabPanelNewsletter');
		tabPanel.setAutoScroll(false);
		for (var i = 1; i < languages.length; i++) {
			tabPanel.setActiveTab(i);
		}
		tabPanel.setActiveTab(0);
		tabPanel.animScroll = true;
	}
	/*
	var translate = function(sourceLanguageId, tabIndex) {
		var params = newsletterPanel.form.getValues();
		params['sourceLanguageId'] = sourceLanguageId; 
		params['tabIndex'] = tabIndex;
		params['viewBean.id'] = window.projectId;
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/project/translate',
			params,
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				newsletterPanel.form.setValues(datas);
			},
			'newsletterPanel'
		);
	}
	*/
	var newsletterPanel = new Ext.form.FormPanel({
		region: 'center',
		id: 'newsletterPanel',
		split: true,
		width: '60%',
		split: true,
		bodyCssClass: 'ha-panel-body',
	    defaults: {
	        msgTarget: 'side',
	        anchor: '100%' 
	    },
		labelWidth: 60,
		title: PROJECT_NEWSLETTER_TITRE,
		items: [
			{
				xtype: 'fileuploadfield',
				fieldLabel: PROJECT_NEWSLETTER_LOGO,
				allowBlank: true,
				id: 'newsletterLogo',
				name: 'viewBean.newsletterLogo',
				buttonText: '',
				buttonCfg: {
					iconCls: 'tool-add-image'
				},
				listeners: {'fileselected': function(field, value) {
					if (checkFileExtension('newsletterLogo', 'image')) {
						uploadFile(newsletterPanel, 'newsletterLogoPreview', 1);
					}
				}},
				clearAction: function() {
					deleteFile('newsletterLogo', 'newsletterLogoPreview');
				}
			}
			,{
				xtype: 'panel',
				id: 'newsletterLogoPreview',
				border: false,
				height: 0,
				html: '<img src="" height="'+PREVIEW_HEIGHT+'"/>',
				bodyCssClass: 'ha-plain-panel',
				style: 'padding-left: 65px;'
			}
			,{
				xtype: 'panel',
				border: false,
				html: PROJECT_NEWSLETTER_LOGO_TOOLTIP,
				height: 70,
				bodyCssClass: 'ha-plain-panel',
				cls: 'ha-field-help',
				style: 'vertical-align: top; padding-left: 65px; overflow: hidden;'
			}
			,tabPanelNewsletter
			,{
				xtype: 'hidden',
				id: 'id',
				name: 'viewBean.id'
			}
			,{
				xtype: 'hidden',
				id: 'companyId',
				name: 'viewBean.companyId',
				value: window.userCompanyId,
			}
			,{
				xtype: 'hidden',
				id: 'key',
				name: 'viewBean.key',
			}
			,{
				xtype: 'hidden',
				id: 'name',
				name: 'viewBean.name',
			}
			/*,{
				xtype: 'hidden',
				id: 'logo',
				name: 'viewBean.logo',
			}*/
			/*,{
				xtype: 'hidden',
				id: 'creditsImage',
				name: 'viewBean.creditsImage',
			}*/
		],
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT
	});


	//-----------------------------------------------------------------------------
	// Save buton
	//-----------------------------------------------------------------------------
	var saveButton = {
		xtype: 'panel',
		region: 'south',
		split: true,
		layout: 'hbox',
		layoutConfig: {
			pack: 'center'
	    },
		height: 40,
		border: false,
		cls: 'ha-plain-panel-lite',
		items: [
			new Ext.Button({
				text: PROJECT_SAVE,
				scale: 'large',
				iconCls: 'tool-save',
				width: 150,
				x: '42%',
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
	// Load form
	//-----------------------------------------------------------------------------
	var load = function() {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/project/edit',
			{
				'viewBean.id': window.projectId
			},
			function(jsonResponse) {
				initTabs();
				var datas = jsonResponse.datas[0];
				//projectPanel.form.setValues(datas);
				newsletterPanel.form.setValues(datas);
				//creditsPanel.form.setValues(datas);
				Ext.getCmp('companyId').setValue(datas.companyId);
				
				// Image previews
				//setImagePreview('logoPreview', datas.logoUrl);				
				setImagePreview('newsletterLogoPreview', datas.newsletterLogoUrl);				
				//setImagePreview('creditsImagePreview', datas.creditsImageUrl);				
			},
			'newsletterEditPanel'
		);
	}


	//var settingsPanel = new SettingsPanel();
	
	//-----------------------------------------------------------------------------
	// Save data
	//-----------------------------------------------------------------------------
	var save = function() {
		/*var params = projectPanel.form.getValues();
		var paramsTmp = newsletterPanel.form.getValues();
		for (key in paramsTmp) {
			params[key] = paramsTmp[key];
		}*/
		var params = newsletterPanel.form.getValues();

		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/project/save',
			params,
			function(jsonResponse) {
			},
			'newsletterEditPanel'
		);
		//hasPageChanged = false;
		
		//settingsPanel.getSaveFunc()();
	}

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
					setImagePreview(imagePanelId, urls[urlIndex]);
					//Ext.get(imageId).dom.src = myAppContext + '/' + urls[urlIndex];
					HurryApp.MessageUtils.showJsonResponse(jsonResponse);
				}
				else {
					HurryApp.Utils.checkResponseErrors(action.response);
				}
			}
		}
		
		formPanel.getForm().submit({
			url: myAppContext + '/project/uploadFiles',
			failure: submitFormFn,
			success: submitFormFn
		});
	}
	
	//-----------------------------------------------------------------------------
	// Delete a file
	//-----------------------------------------------------------------------------
	var deleteFile = function(fileType, panelId) {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/project/deleteFile',
			{
				'viewBean.id': window.projectId,
				fileType: fileType
			},
			function(jsonResponse) {
				setImagePreview(panelId, '');
			},
			'newsletterEditPanel'
		);
	}
	
	//-----------------------------------------------------------------------------
	// Set image preview
	//-----------------------------------------------------------------------------
	var setImagePreview = function(panelId, url) {
		var panel = Ext.getCmp(panelId);
		if (url && url != '') {
			panel.setHeight(PREVIEW_HEIGHT);
			panel.body.dom.firstChild.src = myAppContext + '/' + url + '?t=' + HurryApp.Utils.getTimestamp();
		}
		else {
			panel.setHeight(0);
		}
	}
	
	//-----------------------------------------------------------------------------
	// Main panel
	//-----------------------------------------------------------------------------


	var mainPanel = new Ext.Panel({
		id: 'newsletterEditPanel',
		region: 'east',
		width: '55%',
		border: false,
		layout: 'border',
		
		items: [
			//centerPanel,
			//settingsPanel.getComponent(),
			newsletterPanel,
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
