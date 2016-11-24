SettingsPanel = function() {
	var PREVIEW_HEIGHT = 70;
	
	
	//-----------------------------------------------------------------------------
	// Load form
	//-----------------------------------------------------------------------------
	var load = function() {
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/settings/edit',
			{
				'viewBean.projectId': window.projectId
			},
			function(jsonResponse) {
				var datas = jsonResponse.datas[0];
				settingsPanel.form.setValues(datas);
				updateFields();
				addListeners();
			},
			'settingsEditPanel'
		);
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
				title: SETTINGS_DISPLAY,
				id: 'displayFieldset',
				width: 280,
				height: 465,
				autoHeight: false,
				style: '',
				layout: 'hbox',
				cls: 'ha-plain-panel',
				items: [
					{
						xtype:'fieldset',
						title: '',
						id: 'displaySubFieldset1',
						width: 325,
						height: 430,
						autoHeight: false,
						autoWidth: false,
						style: 'border-top:none; border-left:none; border-bottom:none; padding: 0 0 0 10px;',
						cls: 'ha-plain-panel',
						items: [
							{
								xtype: 'hidden',
								id: 'settingId',
								name: 'viewBean.settingId'
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_CLOCK+' <span id="clockTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('clockTitle', SETTINGS_CLOCK_TOOLTIP);}}
							}
							/*,{
								xtype: 'label',
								text: SETTINGS_CLOCK_TOOLTIP,
								style: ''
							}*/
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								//style: 'padding-bottom:10px;',
								items: [
									{
										xtype: 'radiogroup',
										id: 'clock',
										columns: 2,
										items: [
											{boxLabel: SETTINGS_CLOCK_12,  name: 'viewBean.clock', inputValue: '12', checked: true, id: 'clock-12'}
											,{boxLabel: SETTINGS_CLOCK_24, name: 'viewBean.clock', inputValue: '24', id: 'clock-24'}
							            ],
										width: 300
									}		
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_INACTIVITYDELAY+' <span id="inactivityDelayTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('inactivityDelayTitle', SETTINGS_INACTIVITYDELAY_TOOLTIP);}}
							}
							/*,{
								xtype: 'label',
								text: SETTINGS_INACTIVITYDELAY_TOOLTIP,
								style: ''
							}*/
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'numberfield',
										allowBlank: true,
										allowDecimals : false,
										id: 'inactivityDelay',
										name: 'viewBean.inactivityDelay',
										width: 50,
										listeners: { 'change': function() {
												hasPageChanged = true;
											}
										}
									}
									,{
										xtype: 'label',
										text: SETTINGS_INACTIVITYDELAY_SEC,
										style: 'padding-top:4px; padding-left:5px'
									}
								]
							}
							/*,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_ITEMSINZONE+' <span id="itemsInZoneTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('itemsInZoneTitle', SETTINGS_ITEMSINZONE_TOOLTIP);}}
							}*/
							/*,{
								xtype: 'label',
								text: SETTINGS_ITEMSINZONE_TOOLTIP,
								style: ''
							}*/
							/*,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_ITEMSINZONE_CHECKBOX,
										inputValue: true,
										id: 'itemsInZone',
										name: 'viewBean.itemsInZone',
										//listeners: {'check': function(checkbox, checked) {
										//	}
										//}
									}
								]
							}*/
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_FORCEITEMSIZES+' <span id="forceItemSizesTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('forceItemSizesTitle', SETTINGS_FORCEITEMSIZES_TOOLTIP);}}
							}
							/*,{
								xtype: 'label',
								text: SETTINGS_FORCEITEMSIZES_TOOLTIP,
								style: ''
							}*/
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_FORCEITEMSIZES_CHECKBOX,
										inputValue: true,
										id: 'forceItemSizes',
										name: 'viewBean.forceItemSizes',
										listeners: {'check': function(checkbox, checked) {
												if (checked) {
													Ext.getCmp('locationsWidth').enable(); Ext.getCmp('locationsWidth').setValue('60');
													Ext.getCmp('locationsHeight').enable(); Ext.getCmp('locationsHeight').setValue('60');
													Ext.getCmp('otherItemsWidth').enable(); Ext.getCmp('otherItemsWidth').setValue('40');
													Ext.getCmp('otherItemsHeight').enable(); Ext.getCmp('otherItemsHeight').setValue('40');
												}
												else {
													Ext.getCmp('locationsWidth').disable(); Ext.getCmp('locationsWidth').setValue('60');
													Ext.getCmp('locationsHeight').disable(); Ext.getCmp('locationsHeight').setValue('60');
													Ext.getCmp('otherItemsWidth').disable(); Ext.getCmp('otherItemsWidth').setValue('40');
													Ext.getCmp('otherItemsHeight').disable(); Ext.getCmp('otherItemsHeight').setValue('40');
												}
											}
										}
									}
								]
							}
							,{
								xtype: 'panel',
								id: 'locationsSizePanel',
								layout: 'hbox',
								border: false,
								hidden: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'label',
										text: SETTINGS_LOCATIONS_SIZE+':',
										style: 'padding-top:4px;',
										width: 100
									}
									,{
										xtype: 'numberfield',
										fieldLabel: SETTINGS_LOCATIONS_WIDTH,
										allowBlank: true,
										allowDecimals : false,
										id: 'locationsWidth',
										name: 'viewBean.locationsWidth',
										width: 50,
										listeners: { 'change': function() {
											hasPageChanged = true;
											}
										}
									}
									,{
										xtype: 'label',
										text: 'x',
										style: 'padding-top:4px;padding-left:5px;padding-right:10px;',
										width: 5
									}
									,{
										xtype: 'numberfield',
										fieldLabel: SETTINGS_LOCATIONS_HEIGHT,
										allowBlank: true,
										allowDecimals : false,
										id: 'locationsHeight',
										name: 'viewBean.locationsHeight',
										width: 50,
										listeners: { 'change': function() {
											hasPageChanged = true;
											}
										}
									}
								]
							}
							,{
								xtype: 'panel',
								id: 'otherItemsSizePanel',
								layout: 'hbox',
								border: false,
								hidden: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:10px;',
								items: [
									{
										xtype: 'label',
										text: SETTINGS_OTHERITEMS_SIZE+':',
										style: 'padding-top:4px;',
										width: 100
									}
									,{
										xtype: 'numberfield',
										fieldLabel: SETTINGS_OTHERITEMS_WIDTH,
										allowBlank: true,
										allowDecimals : false,
										id: 'otherItemsWidth',
										name: 'viewBean.otherItemsWidth',
										width: 50,
										listeners: { 'change': function() {
											hasPageChanged = true;
											}
										}
									}
									,{
										xtype: 'label',
										text: 'x',
										style: 'padding-top:4px;padding-left:5px;padding-right:10px;',
										width: 5
									}
									,{
										xtype: 'numberfield',
										fieldLabel: SETTINGS_OTHERITEMS_HEIGHT,
										allowBlank: true,
										allowDecimals : false,
										id: 'otherItemsHeight',
										name: 'viewBean.otherItemsHeight',
										width: 50,
										listeners: { 'change': function() {
											hasPageChanged = true;
											}
										}
									}
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_ITEMSKEEPRATIO+' <span id="itemsKeepRatioTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('itemsKeepRatioTitle', SETTINGS_ITEMSKEEPRATIO_TOOLTIP);}}
							}
							/*,{
								xtype: 'label',
								text: SETTINGS_ITEMSKEEPRATIO_TOOLTIP,
								style: ''
							}*/
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_ITEMSKEEPRATIO_CHECKBOX,
										inputValue: true,
										id: 'itemsKeepRatio',
										name: 'viewBean.itemsKeepRatio'
									}
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_BANNERPOSITION+' <span id="bannerPositionTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('bannerPositionTitle', SETTINGS_BANNERPOSITION_TOOLTIP);}}
							}
							/*,{
								xtype: 'label',
								text: SETTINGS_BANNERPOSITION_TOOLTIP,
								style: ''
							}*/
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								//style: 'padding-bottom:25px;',
								items: [
									{
										xtype: 'radiogroup',
										id: 'bannerPosition',
										columns: 2,
										items: [
											{boxLabel: SETTINGS_BANNERPOSITION_TOP,  name: 'viewBean.bannerPosition', inputValue: 'top', checked: true, id: 'bannerPosition-top'}
											,{boxLabel: SETTINGS_BANNERPOSITION_BOTTOM, name: 'viewBean.bannerPosition', inputValue: 'bottom', id: 'bannerPosition-bottom'}
							            ],
										width: 300
									}		
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_DISPLAYWHOLEMAP+' <span id="displayWholeMapTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('displayWholeMapTitle', SETTINGS_DISPLAYWHOLEMAP_TOOLTIP);}}
							}
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:5px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_DISPLAYWHOLEMAP_CHECKBOX,
										inputValue: true,
										id: 'displayWholeMap',
										name: 'viewBean.displayWholeMap'
									}
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_ALLOWWEBPAGENAVIGATION+' <span id="allowWebPageNavigationTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('allowWebPageNavigationTitle', SETTINGS_ALLOWWEBPAGENAVIGATION_TOOLTIP);}}
							}
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:0px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_ALLOWWEBPAGENAVIGATION_CHECKBOX,
										inputValue: true,
										id: 'allowWebPageNavigation',
										name: 'viewBean.allowWebPageNavigation'
									}
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_SHOWNAMEINSTEADOFICONS+' <span id="showNameInsteadofIconsTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('showNameInsteadofIconsTitle', SETTINGS_SHOWNAMEINSTEADOFICONS_TOOLTIP);}}
							}
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:0px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_SHOWNAMEINSTEADOFICONS_CHECKBOX,
										inputValue: true,
										id: 'showNameInsteadofIcon',
										name: 'viewBean.showNameInsteadofIcon'
									}
								]
							}
						]
					}
					/*,{
						xtype:'fieldset',
						title: '',
						id: 'displaySubFieldset2',
						width: 325,
						height: 190,
						autoHeight: false,
						style: 'border: none; margin: 0 0 0 10px; padding: 0 0 0 10px;',
						cls: 'ha-plain-panel',
						items: [
							{
								xtype: 'hidden',
								id: 'id',
								name: 'viewBean.id'
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_DISPLAYWHOLEMAP+' <span id="displayWholeMapTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('displayWholeMapTitle', SETTINGS_DISPLAYWHOLEMAP_TOOLTIP);}}
							}
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:25px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_DISPLAYWHOLEMAP_CHECKBOX,
										inputValue: true,
										id: 'displayWholeMap',
										name: 'viewBean.displayWholeMap'
									}
								]
							}
							,{
								xtype: 'panel',
								border: false,
								html: '<div>'+SETTINGS_ALLOWWEBPAGENAVIGATION+' <span id="allowWebPageNavigationTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
								style: 'font-weight: bold;',
								listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('allowWebPageNavigationTitle', SETTINGS_ALLOWWEBPAGENAVIGATION_TOOLTIP);}}
							}
							,{
								xtype: 'panel',
								layout: 'hbox',
								border: false,
								cls: 'ha-plain-panel',
								style: 'padding-bottom:0px;',
								items: [
									{
										xtype: 'checkbox',
										boxLabel: SETTINGS_ALLOWWEBPAGENAVIGATION_CHECKBOX,
										inputValue: true,
										id: 'allowWebPageNavigation',
										name: 'viewBean.allowWebPageNavigation'
									}
								]
							}
						]
					}*/
				]
			}
			,{
				xtype: 'fieldset',
				title: SETTINGS_DATA,
				id: 'dataFieldset',
				width: 400,
				height: 465,
				autoHeight: false,
				style: 'margin-left:20px;',
				defaults: {
					anchor: '95%'
				},
				items: [
					{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_AUTOUPDATEPROJECT+' <span id="autoUpdateProjectTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('autoUpdateProjectTitle', SETTINGS_AUTOUPDATEPROJECT_TOOLTIP);}}
					}
					/*,{
						xtype: 'label',
						text: SETTINGS_AUTOUPDATEPROJECT_TOOLTIP,
						style: ''
					}*/
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;',
						items: [
							{
								xtype: 'checkbox',
								boxLabel: SETTINGS_AUTOUPDATEPROJECT_CHECKBOX,
								inputValue: true,
								id: 'autoUpdateProject',
								name: 'viewBean.autoUpdateProject',
								listeners: {'check': function(checkbox, checked) {
										if (checked) {
											Ext.getCmp('hourUpdateProject').enable(); Ext.getCmp('hourUpdateProject').setValue('23:00 AM');
										}
										else {
											Ext.getCmp('hourUpdateProject').disable(); Ext.getCmp('hourUpdateProject').setValue('23:00 AM');
										}
									}
								}
							}
							,{
								xtype: 'label',
								width: 10
							}
							,{
								xtype: 'timefield',
								allowBlank: true,
								editable: false,
								id: 'hourUpdateProject',
								name: 'viewBean.hourUpdateProject',
								width: 80,
								increment: 60,
								listeners: { 'change': function() {
									hasPageChanged = true;
									}
								},
							}
						]
					}
					,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_AUTOUPDATESOFTWARE+' <span id="autoUpdateSoftwareTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('autoUpdateSoftwareTitle', SETTINGS_AUTOUPDATESOFTWARE_TOOLTIP);}}
					}
					/*,{
						xtype: 'label',
						text: SETTINGS_AUTOUPDATEPROJECT_TOOLTIP,
						style: ''
					}*/
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;',
						items: [
							{
								xtype: 'checkbox',
								boxLabel: SETTINGS_AUTOUPDATESOFTWARE_CHECKBOX,
								inputValue: true,
								id: 'autoUpdateSoftware',
								name: 'viewBean.autoUpdateSoftware',
								listeners: {'check': function(checkbox, checked) {
										if (checked) {
											Ext.getCmp('hourUpdateSoftware').enable(); 
											Ext.getCmp('hourUpdateSoftware').setValue('23:00 AM');
											
											Ext.getCmp('updateTryTimes').enable(); 
											Ext.getCmp('updateTryTimes').setValue('3');
											
										}
										else {
											Ext.getCmp('hourUpdateSoftware').disable(); 
											//Ext.getCmp('hourUpdateSoftware').setValue('23:00 AM');
											
											Ext.getCmp('updateTryTimes').disable(); 
										}
									}
								}
							}
							,{
								xtype: 'label',
								width: 10
							}
							,{
								xtype: 'timefield',
								allowBlank: true,
								editable: false,
								id: 'hourUpdateSoftware',
								name: 'viewBean.hourUpdateSoftware',
								width: 80,
								increment: 60,
								listeners: { 'change': function() {
									hasPageChanged = true;
									}
								},
							}
						]
					}
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						hidden: false,
						cls: 'ha-plain-panel',
						style: 'padding-left: 10px;margin-bottom:5px;',
						items: [
							{
								xtype: 'label',
								style: 'margin-top: 3px;',
								text:SETTINGS_UPDATESOFTWARE_TRYTIMES+": "
							}
							,{
								xtype: 'numberfield',
								style: 'margin-left: 10px;',
								editable: true,
								id: 'updateTryTimes',
								name: 'viewBean.updateTryTimes',
								width: 80,
							}
						]
					}
					,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_AUTOUPLOADCONTACTS+' <span id="autoUploadContactsTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('autoUploadContactsTitle', SETTINGS_AUTOUPLOADCONTACTS_TOOLTIP);}}
					}
					/*,{
						xtype: 'label',
						text: SETTINGS_AUTOUPLOADCONTACTS_TOOLTIP,
						style: ''
					}*/
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;',
						items: [
							{
								xtype: 'checkbox',
								boxLabel: SETTINGS_AUTOUPLOADCONTACTS_CHECKBOX,
								inputValue: true,
								id: 'autoUploadContacts',
								name: 'viewBean.autoUploadContacts',
								listeners: {'check': function(checkbox, checked) {
										if (checked) {
											Ext.getCmp('hourUploadContacts').enable(); Ext.getCmp('hourUploadContacts').setValue('12:00 AM');
										}
										else {
											Ext.getCmp('hourUploadContacts').disable(); Ext.getCmp('hourUploadContacts').setValue('12:00 AM');
										}
									}
								}
							}
							,{
								xtype: 'label',
								width: 10
							}
							,{
								xtype: 'timefield',
								allowBlank: true,
								editable: false,
								id: 'hourUploadContacts',
								name: 'viewBean.hourUploadContacts',
								width: 80,
								increment: 60,
								listeners: { 'change': function() {
									hasPageChanged = true;
									}
								}
							}
						]
					}
					
					/*,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_MIXPANELKEY+' <span id="mixPanelKeyTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('mixPanelKeyTitle', SETTINGS_MIXPANELKEY_TOOLTIP);}}
					}
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:45px; padding-left: 10px;',
						items: [
							{
								xtype: 'textfield',
								id: 'mixPanelKey',
								name: 'viewBean.mixPanelKey',
								width: 440
							}
						]
					}*/

					,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_SENDLISTDATAEMAIL+' <span id="sendListDataEmailTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('sendListDataEmailTitle', SETTINGS_SENDLISTDATAEMAIL_TOOLTIP);}}
					}
					/*,{
						xtype: 'label',
						text: SETTINGS_AUTOUPDATEPROJECT_TOOLTIP,
						style: ''
					}*/
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;padding-top:5px',
						items: [
							{
								xtype: 'checkbox',
								//boxLabel: SETTINGS_AUTOUPDATEPROJECT_CHECKBOX,
								inputValue: true,
								id: 'sendContactsEmail',
								name: 'viewBean.sendContactsEmail',
								style: 'margin-top:5px',
								listeners: {'check': function(checkbox, checked) {
									if (checked) {
										Ext.getCmp('emailSendContacts').enable(); //Ext.getCmp('listDataEmail').setValue('12:00 AM');
									}
									else {
										Ext.getCmp('emailSendContacts').disable(); 
										Ext.getCmp('emailSendContacts').setValue('');
									}
								}}
							}
							,{
								xtype: 'label',
								width: 5
							}
							,{
								xtype: 'textfield',
								allowBlank: true,
								editable: true,
								id: 'emailSendContacts',
								name: 'viewBean.emailSendContacts',
								width: 330,
							}
						]
					}
					,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_IVSESSERVERIP+' <span id="ivsESServerIPTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('ivsESServerIPTitle', SETTINGS_IVSESSERVERIP_TOOLTIP);}}
					}
					/*,{
						xtype: 'label',
						text: SETTINGS_AUTOUPDATEPROJECT_TOOLTIP,
						style: ''
					}*/
					,{
						xtype: 'panel',
						layout: 'hbox',
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;padding-top:5px',
						items: [
							{
								xtype: 'textfield',
								allowBlank: true,
								editable: true,
								id: 'ivsESServerIP',
								name: 'viewBean.ivsESServerIP',
								width: 200,
								style: 'margin-top:5px',
							}
							,{
								xtype: 'label',
								width: 20
							}
							,
							new Ext.Button({
								text: SETTINGS_FINDDATASOURCE_LABEL,
								scale: 'medium',
								//iconCls: 'tool-save',
								width: 125,
								handler: function() {
									//save();
								},
								//listeners: {
								//	'afterrender': function() {
								//		load();
								//	}
								//}
							})
						]
					}
					,{
						xtype: 'panel',
						border: false,
						html: '<div>'+SETTINGS_AVALIABLEDATASOURCE+' <span id="avaliableDataSourceTitle" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div>',
						style: 'font-weight: bold; padding-left: 10px;',
						listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('avaliableDataSourceTitle', SETTINGS_AVALIABLEDATASOURCE_TOOLTIP);}}
					}
					,{
						xtype: 'panel',
						layout:'border',
						height: 180,
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;padding-top:5px',
						items: [
							new DataSourceCrud().getComponent(),
						]
					}
					,{
						xtype: 'panel',
						layout: {
					        type: 'vbox',       // Arrange child items vertically
					        align: 'center',    // Each takes up full width
					    },
						height: 180,
						border: false,
						cls: 'ha-plain-panel',
						style: 'padding-bottom:5px; padding-left: 10px;padding-top:5px',
						items: [
							new Ext.Button({
								text: SETTINGS_IMPORTDATASOURCE_LABEL,
								scale: 'large',
								//iconCls: 'tool-save',
								width: 80,
								handler: function() {
									//save();
								},
								listeners: {
									'afterrender': function() {
										load();
									}
								}
							})
						]
					}
				]
			}
		]
	};

	var settingsPanel = new Ext.form.FormPanel({
		region: 'center',
		width: '100%',
		bodyCssClass: 'ha-panel-body',
	    defaults: {
	        msgTarget: 'side',
	        anchor: '100%' 
	    },
		labelWidth: 80,
		title: SETTINGS_TITRE,
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
		layoutConfig: {
			pack: 'center'
	    },
	    width: 0,
		height: 0,
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
		var params = settingsPanel.form.getValues();
		
		HurryApp.Utils.sendAjaxRequest(
			myAppContext+'/settings/save',
			params,
			function(jsonResponse) {
			},
			'settingsEditPanel'
		);
		hasPageChanged = false;
	}

	//-----------------------------------------------------------------------------
	// Show/hide dynamic fields
	//-----------------------------------------------------------------------------
	var updateFields = function() {
		if (Ext.getCmp('forceItemSizes').getValue()) {
			Ext.getCmp('locationsWidth').enable();
			Ext.getCmp('locationsHeight').enable();
			Ext.getCmp('otherItemsWidth').enable();
			Ext.getCmp('otherItemsHeight').enable();
		}
		else {
			Ext.getCmp('locationsWidth').disable();
			Ext.getCmp('locationsHeight').disable();
			Ext.getCmp('otherItemsWidth').disable();
			Ext.getCmp('otherItemsHeight').disable();
		}

		if (Ext.getCmp('autoUploadContacts').getValue()) {
			Ext.getCmp('hourUploadContacts').enable();
		}
		else {
			Ext.getCmp('hourUploadContacts').disable();
		}

		if (Ext.getCmp('autoUpdateProject').getValue()) {
			Ext.getCmp('hourUpdateProject').enable();
		}
		else {
			Ext.getCmp('hourUpdateProject').disable();
		}
		
		if (Ext.getCmp('sendContactsEmail').getValue()) {
			Ext.getCmp('emailSendContacts').enable();
		}
		else {
			Ext.getCmp('emailSendContacts').disable();
		}
		
		if (Ext.getCmp('autoUpdateSoftware').getValue()) {
			Ext.getCmp('hourUpdateSoftware').enable();
			Ext.getCmp('updateTryTimes').enable();
			
		}
		else {
			Ext.getCmp('hourUpdateSoftware').disable();
			Ext.getCmp('updateTryTimes').disable();
		}
		
	}
	
	//-----------------------------------------------------------------------------
	// Add listeners on checkboxes and radios
	//-----------------------------------------------------------------------------
	var addListeners = function() {
		Ext.getCmp('forceItemSizes').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('itemsKeepRatio').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('displayWholeMap').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('allowWebPageNavigation').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('autoUploadContacts').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('autoUpdateProject').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('clock-12').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('clock-24').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('bannerPosition-top').addListener('check', function() { hasPageChanged = true; });
		Ext.getCmp('bannerPosition-bottom').addListener('check', function() { hasPageChanged = true; });
	}

	//-----------------------------------------------------------------------------
	// Main panel
	//-----------------------------------------------------------------------------
	var mainPanel = new Ext.Panel({
		id: 'settingsEditPanel',
		region: 'east',
		width: '60%',
		height: '100%',
		border: false,
		layout: 'border',
		
		items: [
			settingsPanel,
			//saveButton
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
