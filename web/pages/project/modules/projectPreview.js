ProjectPreview = function() {
	var PREVIEW_HEIGHT = 90;
	var editLabel = PROJECT_LABEL_EDIT_BUTTON;
	var tooltipEditButton = PROJECT_TOOLTIP_EDIT_BUTTON;
	var renewLabel = PROJECT_LABEL_RENEW_BUTTON;
	var tooltipRenewButton = PROJECT_TOOLTIP_RENEW_BUTTON;
	var selectProjectLabel = PROJECT_SELECT_PROJECT_LABEL;
	var urlRenew;
	var suggestRenew = PROJECT_SUGGEST_RENEW;
	PROJECT_DEFAULT_PREVIEW_PICTURE = myAppContext + '/' + PROJECT_DEFAULT_PREVIEW_PICTURE;
	
	//-----------------------------------------------------------------------------
	// Fields
	//-----------------------------------------------------------------------------
	var fields = {
		xtype: 'panel',
		border: false,
		layout: '',
		cls: 'ha-plain-panel',
		width: '90%',
		style: 'text-align: center; padding: 0;',
		items: [
			{
				xtype: 'toolbar',
				region: 'north',
				border: false,
				cls: 'x-panel-tbar',
				id: 'project-preview-toolbar',
				height: 27,
				items:[
					{
						text: editLabel,
						tooltip: tooltipEditButton,
						iconCls: 'tool-edit',
						handler: function () {
							window.location = urlEdit;
						}
					}/*,
					{
						text: renewLabel,
						id: 'renewProjectTitle',
						tooltip: tooltipRenewButton,
						iconCls: 'tool-cart',
						handler: function () {
							window.open(urlRenew);
						}
					}*/
				]
			},
			{	xtype:'fieldset',
				title: '',
				region: 'center',
				id: 'displayFieldset',
				width: '100%',
				border: false,
				cls: 'ha-plain-panel',
				style: 'padding-top: 10px',
				items: [
					{
						xtype:'fieldset',
						title: '',
						region: 'north',
						id: 'displayTitre',
						width: '90%',
						height: 50,
						autoHeight: false,
						border: false,
						cls: 'ha-plain-panel',
						style: 'margin: 0 0 0 20px; padding: 10px 0 0 0; text-align: left;',
						items: [
							{
								xtype: 'label',
								allowBlank: true,
								id: 'name',
								width: '100%',
								text: '',
								height: 10,
								name: 'viewBean.name',
								style: 'font-weight: bold; font-size: 16px; text-align: left;'
							}
						]
					},
					{
						xtype:'fieldset',
						title: '',
						region: 'center',
						id: 'displayFieldset',
						width: '100%',
						height: 180,
						autoHeight: false,
						border: false,
						layout: 'hbox',
						cls: 'ha-plain-panel',
						style: 'margin-top: 0; padding-top: 0; margin-bottom: 0;',
						items: [
							{
								xtype:'fieldset',
								title: '',
								id: 'displayFieldsetLeft',
								width: '30%',
								height: 205,
								autoHeight: false,
								border: false,
								cls: 'ha-plain-panel',
								style: 'margin-top: 0; padding-top: 0;',
								items: [
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_LICENSES,
										allowBlank: true,
										id: 'nbLicenses',
										name: 'viewBean.nbLicenses',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background:url(../images/icons/terminal-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									},
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_LANGUAGES,
										allowBlank: true,
										id: 'nbLanguages',
										name: 'viewBean.nbLanguages',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background:url(../images/icons/language-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									},
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_ITEMS,
										allowBlank: true,
										id: 'nbItems',
										name: 'viewBean.nbItems',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background:url(../images/icons/item-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									},
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_EVENTS,
										allowBlank: true,
										id: 'nbEvents',
										name: 'viewBean.nbEvents',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background:url(../images/icons/event-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									},
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_NEWSLETTEREMAIL,
										allowBlank: true,
										id: 'nbNewsletterEmails',
										name: 'viewBean.nbNewsletterEmails',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background:url(../images/icons/contact-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									},
									{
										xtype: 'textfield',
										fieldLabel: PROJECT_NB_PROMOTIONS,
										allowBlank: true,
										id: 'nbPromotions',
										name: 'viewBean.nbPromotions',
										readOnly: true,
										style: 'width: 27px;',
										labelStyle: 'width: 70px; height: 18px; background: url(../images/icons/promotion-icon.png) no-repeat 0 2px !important; padding-left: 20px;'
									}
								]
							},
							{
								xtype:'fieldset',
								title: '',
								id: 'displayFieldsetRight',
								width: '70%',
								height: 205,
								autoHeight: false,
								border: false,
								cls: 'ha-plain-panel',
								style: 'margin-top: 0; padding-top: 0;',
								items: [
									{
										xtype: 'panel',
										id: 'logoPreview',
										region: 'center',
										border: false,
										height: PREVIEW_HEIGHT,
										width: 335,
										html: '<img src="" height="'+PREVIEW_HEIGHT+'"/>',
										bodyCssClass: 'ha-plain-panel',
									}
								]
							}	
						]
					},
					{
						xtype:'fieldset',
						title: '',
						id: 'displayFieldsetBottom',
						width: '100%',
						height: 50,
						region: 'south',
						autoHeight: false,
						border: false,
						layout: 'hbox',
						cls: 'ha-plain-panel',
						style: 'margin-top: 0; padding-top: 0;',
						items: [
							{	xtype:'fieldset',
								title: '',
								id: 'displayFieldsetBottomLeft',
								width: 390,
								height: 50,
								autoHeight: false,
								border: false,
								cls: 'ha-plain-panel',
								style: 'margin-top: 0; padding-top: 0; margin-right: 0; padding-right: 0;',
								items: [
									{	
										xtype: 'textfield',
										fieldLabel: PROJECT_KEY,
										allowBlank: true,
										id: 'key',
										width: 245,
										style: 'padding: 0; margin: 0 0 0 17px',
										labelStyle: 'width: 70px; height: 16px; background:url(../images/icons/key-icon.png) no-repeat !important; padding-left: 20px;',
										name: 'viewBean.key',
										//width: 200,
										readOnly: true,
										listeners: {
											'focus' : function () { selectProjectKey(); }
										}
									}
								]
							},
							{	xtype:'fieldset',
								title: '',
								id: 'displayFieldsetBottomRight',
								width: 180,
								height: 50,
								autoHeight: false,
								border: false,
								cls: 'ha-plain-panel',
								style: 'margin: 0; padding: 0; text-align: left;',
								items: [
									{
										xtype: 'label',
										allowBlank: true,
										id: 'copyKeyLink',
										width: '100%',
										style: 'font-weight: bold;',
										listeners: {
										    render: function(c){
										      c.getEl().on('click', function(){
										    	  copyProjectKey();
										      }, c);
										   }
										}
									}
								]
							}
						]
					}
				]
			},
			{	xtype:'fieldset',
				title: '',
				id: 'displayFieldsetSelectProject',
				width: '100%',
				autoHeight: false,
				border: false,
				cls: 'ha-plain-panel',
				style: 'margin: 115px auto 0 auto;',
				items: [
					{
						xtype: 'label',
						text: selectProjectLabel,
						id: 'selectProjectLabel',
						width: '100%',
						height: '100%',
						style: 'text-align: center; color: #828282;'
					}
				]
			}
		]
	};
	
	var projectPreview = new Ext.form.FormPanel({
		region: 'center',
		width: '100%',
		bodyCssClass: 'ha-panel-body',
	    defaults: {
	        msgTarget: 'side',
	        anchor: '100%' 
	    },
		bodyStyle: "padding: 0;",
		labelWidth: 80,
		title: PROJECT_TITRE,
		items: fields,
		fileUpload: false
	});
	
	//-----------------------------------------------------------------------------
	// Load form
	//-----------------------------------------------------------------------------
	var load = function(projectId) {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/project/edit',
			{
				'viewBean.id': projectId
			},
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				projectPreview.form.setValues(datas);
				//Edit button
				urlEdit = myAppContext+'/project/init?projectId='+projectId;
				// Renew url
				urlRenew = datas.renewProjectUrl;
				// Image previews
				if(datas.previewLogoUrl != '') {
					projectPreview.items.itemAt(0).items.itemAt(1).items.itemAt(1).items.itemAt(1).items.itemAt(0).show();
					setTimeout(function () { setImagePreview('logoPreview', datas.previewLogoUrl); }, 10);
				} else {
					projectPreview.items.itemAt(0).items.itemAt(1).items.itemAt(1).items.itemAt(1).items.itemAt(0).hide();
				}
			},
			'projectPreview'
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
			// panel.body.dom.firstChild.src = PROJECT_DEFAULT_PREVIEW_PICTURE;
			panel.body.dom.firstChild.src = '';
			panel.setHeight(PREVIEW_HEIGHT);
		}
	}
	
	//-----------------------------------------------------------------------------
	// Main panel
	//-----------------------------------------------------------------------------
	var mainPanel = new Ext.Panel({
		id: 'projectPreview',
		region: 'center',
		width: '100%',
		autoHeight: false,
		collapsible: false,
		split: false,
		border: false,
		layout: 'border',
		items: [
			projectPreview
		]
	});

	return {
		getComponent: function() {
			return mainPanel;
		},
		getForm: function() {
			return projectPreview;
		},
		getLoadFunc: function() {
			return load;
		},
		hide: function() {
			projectPreview.items.itemAt(0).items.itemAt(1).hide();
		},
		show: function() {
			projectPreview.items.itemAt(0).items.itemAt(1).show();
		}
	}
}