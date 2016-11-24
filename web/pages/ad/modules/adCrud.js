AdCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: AD_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: AD_TYPE,
			dataIndex: 'type',
			hidden: true,
			sortable: false,
			align: 'left',
			width: 30,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		      return '<div class="tool-'+record.data.type+'" title="'+record.data.type+'">&nbsp;</div>';
			}
		}
		,{
			header: AD_SKIPAFTER,
			dataIndex: 'skipAfter',
			hidden: true,
			sortable: false,
			align: 'left',
			width: 150
		}
		,{
			header: AD_LINKEDTO,
			dataIndex: 'linkedTo',
			hidden: true,
			sortable: false,
			align: 'left',
			width: 150
		}
		/*,{
			header: AD_VIEW,
			sortable: false,
			align: 'left',
			width: 35,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		      return '<div class="tool-preview" onclick="previewAd('+record.data.id+', \''+record.data.file+'\', '+record.data.width+', '+record.data.height+');">&nbsp;</div>';
			}
		}*/
		,{
			dataIndex: 'file',
			hidden: true
		}
		,{
			dataIndex: 'width',
			hidden: true
		}
		,{
			dataIndex: 'height',
			hidden: true
		}
		,{
			dataIndex: 'bannerType',
			hidden: true
		}
		,{
			dataIndex: 'bannerSkipAfter',
			hidden: true
		}
		,{
			dataIndex: 'bannerLinkedTo',
			hidden: true
		}
		,{
			dataIndex: 'bannerFile',
			hidden: true
		}
		,{
			dataIndex: 'bannerWidth',
			hidden: true
		}
		,{
			dataIndex: 'bannerHeight',
			hidden: true
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'playlistId',
			name: 'viewBean.playlistId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: AD_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}
		,{
			xtype:'fieldset',
			title: AD_FULLSCREEN,
			id: 'fullscreenFieldset',
			checkboxToggle: true,
			collapsed: false,
			checkboxName: 'viewBean.fullscreen',
			width: 550,
			labelWidth: 80,
			autoHeight: true,
			style: 'margin-top:20px;',
			defaults: {
				anchor: '95%'
			},
			listeners: {
				'expand': function() {
					Ext.getCmp('linkToFullscreenAd').enable();
				},
				'collapse': function() {
					Ext.getCmp('linkToFullscreenAd').disable();
					if (Ext.getCmp('bannerLinkTo').getValue().getValue() == true) {
						Ext.getCmp('bannerLinkTo').reset();
					}
				}
			},
			items: [
				{
					xtype: 'fileuploadfield',
					fieldLabel: AD_FILE,
					allowBlank: true,
					id: 'file',
					name: 'viewBean.file',
					buttonText: '',
					buttonCfg: {
						iconCls: 'tool-dir-search'
					},
					fileType: 'mediaZip',
					listeners: {'fileselected': function(field, value) {
							updateFieldsFullscreenAd();
						}
					}
				}
				,{
					xtype: 'hidden',
					id: 'fileFileName',
					name: 'viewBean.fileFileName'
				}
				,{
					xtype: 'panel',
					id: 'fileSizePanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							text: AD_WIDTH+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'textfield',
							allowBlank: true,
							id: 'width',
							name: 'viewBean.width',
							value: '550',
							width: 100
						}
						,{
							xtype: 'label',
							text: AD_HEIGHT+':',
							style: 'margin-left:120px;padding-top:4px;',
							width: 183
						}
						,{
							xtype: 'textfield',
							fieldLabel: AD_HEIGHT,
							allowBlank: true,
							id: 'height',
							name: 'viewBean.height',
							value: '400',
							width: 100
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'skipAfterPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							text: AD_SKIPAFTER+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'label',
							text: AD_SKIPAFTER_NBSEC_1,
							style: 'padding-top:4px;',
							width: 25
						}
						,{
							xtype: 'textfield',
							allowBlank: true,
							id: 'skipAfterNbSec',
							name: 'viewBean.skipAfterNbSec',
							style: 'margin-left:5px;',
							width: 30
						}
						,{
							xtype: 'label',
							text: AD_SKIPAFTER_NBSEC_2,
							style: 'padding-top:4px;padding-left:10px;',
							width: 50
						}
						,{
							xtype: 'label',
							id: 'nbSecOrAsEvent',
							text: AD_SKIPAFTER_OR,
							style: 'padding-left:50px;padding-top:4px;',
							width: 110
						}
						,{
							xtype: 'checkbox',
							boxLabel: AD_SKIPAFTER_ASEVENT,
							inputValue: true,
							id: 'skipAfterAsEvent',
							name: 'viewBean.skipAfterAsEvent',
							width: 140,
							listeners: {'check': function(checkbox, checked) {
									if (checked) {
										Ext.getCmp('skipAfterNbSec').setDisabled(true);
									}
									else {
										Ext.getCmp('skipAfterNbSec').setDisabled(false);
									}
								}
							}
						}
						,{
							xtype: 'label',
							id: 'skipAfterAsEventHelp',
							html: '<span id="skipAfterAsEventTooltip" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>',
							width: 20,
							listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('skipAfterAsEventTooltip', AD_SKIPAFTER_ASEVENT_TOOLTIP);}}
						}
						,{
							xtype: 'checkbox',
							boxLabel: AD_SKIPAFTER_COMPLETE,
							inputValue: true,
							id: 'skipAfterComplete',
							name: 'viewBean.skipAfterComplete',
							width: 180,
							listeners: {'check': function(checkbox, checked) {
									if (checked) {
										Ext.getCmp('skipAfterNbSec').setDisabled(true);
									}
									else {
										Ext.getCmp('skipAfterNbSec').setDisabled(false);
									}
								}
							}
						}
					]
				}
				,{
					xtype: 'radiogroup',
					id: 'linkTo',
					name: 'viewBean.linkTo',
					fieldLabel: AD_LINKTO,
					columns: 5,
					width: 370,
					items: [
						{boxLabel: AD_NONE, name: 'viewBean.linkTo', inputValue: '-1', checked: true}
						,{boxLabel: AD_URL, name: 'viewBean.linkTo', inputValue: '1'}
						,{boxLabel: AD_ITEM,  name: 'viewBean.linkTo', inputValue: '2'}
						,{boxLabel: AD_EVENT, name: 'viewBean.linkTo', inputValue: '3'}
						,{boxLabel: AD_PROMOTION, name: 'viewBean.linkTo', inputValue: '4'}
		            ],
		            listeners: {'change': function() {
		            		updateFieldsFullscreenAd();
		            	}            	
		            }
				}		
				,{
					xtype: 'panel',
					id: 'urlPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_URL+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'textfield',
							fieldLabel: AD_URL,
							allowBlank: true,
							id: 'url',
							name: 'viewBean.url',
							width: 375
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'itemPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_ITEM+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							id: 'itemId',
							name: 'viewBean.item',
							hiddenName: 'viewBean.itemId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : items
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'eventPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_EVENT+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							id: 'eventId',
							name: 'viewBean.event',
							hiddenName: 'viewBean.eventId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : events
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'promotionPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_PROMOTION+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							fieldLabel: AD_PROMOTION,
							id: 'promotionId',
							name: 'viewBean.promotion',
							hiddenName: 'viewBean.promotionId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : promotions
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
			]
		}
		,{
			xtype:'fieldset',
			title: AD_BANNER,
			id: 'bannerFieldset',
			checkboxToggle: true,
			collapsed: false,
			checkboxName: 'viewBean.banner',
			width: 550,
			labelWidth: 80,
			autoHeight: true,
			style: 'margin-top:20px;',
			defaults: {
				anchor: '95%'
			},
			items: [
				{
					xtype: 'fileuploadfield',
					fieldLabel: AD_FILE,
					allowBlank: true,
					id: 'bannerFile',
					name: 'viewBean.bannerFile',
					buttonText: '',
					buttonCfg: {
						iconCls: 'tool-dir-search'
					},
					fileType: 'mediaZip',
					listeners: {'fileselected': function(field, value) {
							updateFieldsBannerAd();
						}
					}
				}
				,{
					xtype: 'hidden',
					id: 'bannerFileFileName',
					name: 'viewBean.bannerFileFileName'
				}
				,{
					xtype: 'panel',
					id: 'bannerFileSizePanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							text: AD_WIDTH+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'textfield',
							allowBlank: true,
							id: 'bannerWidth',
							name: 'viewBean.bannerWidth',
							value: '468',
							width: 100,
							readOnly: true,
							cls: 'ha-field-disabled'
						}
						,{
							xtype: 'label',
							text: AD_HEIGHT+':',
							style: 'margin-left:120px;padding-top:4px;',
							width: 183
						}
						,{
							xtype: 'textfield',
							fieldLabel: AD_HEIGHT,
							allowBlank: true,
							id: 'bannerHeight',
							name: 'viewBean.bannerHeight',
							value: '60',
							width: 100,
							readOnly: true,
							cls: 'ha-field-disabled'
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'bannerSkipAfterPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							text: AD_SKIPAFTER+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'label',
							text: AD_SKIPAFTER_NBSEC_1,
							style: 'padding-top:4px;',
							width: 25
						}
						,{
							xtype: 'textfield',
							allowBlank: true,
							id: 'bannerSkipAfterNbSec',
							name: 'viewBean.bannerSkipAfterNbSec',
							style: 'margin-left:5px;',
							width: 30
						}
						,{
							xtype: 'label',
							text: AD_SKIPAFTER_NBSEC_2,
							style: 'padding-top:4px;padding-left:10px;',
							width: 50
						}
						,{
							xtype: 'label',
							id: 'bannerNbSecOrAsEvent',
							text: AD_SKIPAFTER_OR,
							style: 'padding-left:50px;padding-top:4px;',
							width: 110
						}
						,{
							xtype: 'checkbox',
							boxLabel: AD_SKIPAFTER_ASEVENT,
							inputValue: true,
							id: 'bannerSkipAfterAsEvent',
							name: 'viewBean.bannerSkipAfterAsEvent',
							width: 140,
							listeners: {'check': function(checkbox, checked) {
									if (checked) {
										Ext.getCmp('bannerSkipAfterNbSec').setDisabled(true);
									}
									else {
										Ext.getCmp('bannerSkipAfterNbSec').setDisabled(false);
									}
								}
							}
						}
						,{
							xtype: 'label',
							id: 'bannerSkipAfterAsEventHelp',
							html: '<span id="bannerSkipAfterAsEventTooltip" class="tool-help">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>',
							width: 20,
							listeners: {'afterrender': function() {HurryApp.MessageUtils.showTooltip('bannerSkipAfterAsEventTooltip', AD_SKIPAFTER_ASEVENT_TOOLTIP);}}
						}
						,{
							xtype: 'checkbox',
							boxLabel: AD_SKIPAFTER_COMPLETE,
							inputValue: true,
							id: 'bannerSkipAfterComplete',
							name: 'viewBean.bannerSkipAfterComplete',
							width: 180,
							listeners: {'check': function(checkbox, checked) {
									if (checked) {
										Ext.getCmp('bannerSkipAfterNbSec').setDisabled(true);
									}
									else {
										Ext.getCmp('bannerSkipAfterNbSec').setDisabled(false);
									}
								}
							}
						}
					]
				}
				,{
					xtype: 'radiogroup',
					id: 'bannerLinkTo',
					name: 'viewBean.bannerLinkTo',
					fieldLabel: AD_LINKTO,
					columns: 3,
					width: 370,
					items: [
						{boxLabel: AD_NONE, name: 'viewBean.bannerLinkTo', inputValue: '-1', checked: true}
						,{boxLabel: AD_URL, name: 'viewBean.bannerLinkTo', inputValue: '1'}
						,{boxLabel: AD_ITEM,  name: 'viewBean.bannerLinkTo', inputValue: '2'}
						,{boxLabel: AD_EVENT, name: 'viewBean.bannerLinkTo', inputValue: '3'}
						,{boxLabel: AD_PROMOTION, name: 'viewBean.bannerLinkTo', inputValue: '4'}
						,{boxLabel: AD_FULLSCREENAD, name: 'viewBean.bannerLinkTo', inputValue: '5', id: 'linkToFullscreenAd'}
		            ],
		            listeners: {'change': function() {
		            		updateFieldsBannerAd();
		            	}            	
		            }
				}		
				,{
					xtype: 'panel',
					id: 'bannerUrlPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_URL+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'textfield',
							fieldLabel: AD_URL,
							allowBlank: true,
							id: 'bannerUrl',
							name: 'viewBean.bannerUrl',
							width: 375
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'bannerItemPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_ITEM+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							id: 'bannerItemId',
							name: 'viewBean.bannerItem',
							hiddenName: 'viewBean.bannerItemId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : items
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'bannerEventPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_EVENT+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							id: 'bannerEventId',
							name: 'viewBean.bannerEvent',
							hiddenName: 'viewBean.bannerEventId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : events
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
				,{
					xtype: 'panel',
					id: 'bannerPromotionPanel',
					layout: 'hbox',
					border: false,
					hidden: false,
					style: 'padding-bottom:5px;',
					items: [
						{
							xtype: 'label',
							//text: AD_PROMOTION+':',
							style: 'padding-top:4px;',
							width: 85
						}
						,{
							xtype: 'combo',
							fieldLabel: AD_PROMOTION,
							id: 'bannerPromotionId',
							name: 'viewBean.bannerPromotion',
							hiddenName: 'viewBean.bannerPromotionId',
							valueField: 'id',
							displayField: 'name',
							store: new Ext.data.JsonStore({
								fields: ['id', 'name'],
								data : promotions
							}),
							typeAhead: true,
							mode: 'local',
							triggerAction: 'all',
							selectOnFocus:true,
							width: 375,
							editable: false
						}
					]
				}
			]
		}
	];

	//-----------------------------------------------------------------------------
	// Display/hide dynamic fields for the "fullscreen ad"
	//-----------------------------------------------------------------------------
	var updateFieldsFullscreenAd = function() {
		// Fullscreen ad
		var fileName = Ext.getCmp('file').getValue();
		if (fileName != '') {
			fileName = fileName.toUpperCase();
			// IMAGE
			if(fileName.indexOf(MEDIA_SUFFIX_1, fileName.length - MEDIA_SUFFIX_1.length) !== -1 ||
					//fileName.indexOf(MEDIA_SUFFIX_2, fileName.length - MEDIA_SUFFIX_2.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_3, fileName.length - MEDIA_SUFFIX_3.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_4, fileName.length - MEDIA_SUFFIX_4.length) !== -1){
				Ext.getCmp('fileSizePanel').hide();
				Ext.getCmp('skipAfterPanel').show();
				Ext.getCmp('nbSecOrAsEvent').hide();
				Ext.getCmp('skipAfterAsEvent').hide();
				Ext.getCmp('skipAfterAsEventHelp').hide();
				Ext.getCmp('skipAfterComplete').hide();
			}
			// SWF or ZIP
			else if(fileName.indexOf(MEDIA_SUFFIX_8, fileName.length - MEDIA_SUFFIX_8.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_9, fileName.length - MEDIA_SUFFIX_9.length) !== -1){
				Ext.getCmp('fileSizePanel').show();
				Ext.getCmp('skipAfterPanel').show();
				Ext.getCmp('nbSecOrAsEvent').show();
				Ext.getCmp('skipAfterAsEvent').show();
				Ext.getCmp('skipAfterAsEventHelp').show();
				Ext.getCmp('skipAfterComplete').hide();
			}
			// MOVIE
			else if(fileName.indexOf(MEDIA_SUFFIX_5, fileName.length - MEDIA_SUFFIX_5.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_6, fileName.length - MEDIA_SUFFIX_6.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_7, fileName.length - MEDIA_SUFFIX_7.length) !== -1){
				Ext.getCmp('fileSizePanel').hide();
				Ext.getCmp('skipAfterPanel').show();
				Ext.getCmp('nbSecOrAsEvent').show();
				Ext.getCmp('skipAfterAsEvent').hide();
				Ext.getCmp('skipAfterAsEventHelp').hide();
				Ext.getCmp('skipAfterComplete').show();
			}
		}
		else {
			Ext.getCmp('fileSizePanel').hide();
			Ext.getCmp('skipAfterPanel').hide();
			Ext.getCmp('nbSecOrAsEvent').hide();
			Ext.getCmp('skipAfterAsEvent').hide();
			Ext.getCmp('skipAfterAsEventHelp').hide();
			Ext.getCmp('skipAfterComplete').hide();
		}
		Ext.getCmp('skipAfterPanel').doLayout();
		
		// Link to ...
		var linkTo = Ext.getCmp('linkTo').getValue();
		if (linkTo) {
			if (linkTo.inputValue == '-1') { // None
				Ext.getCmp('urlPanel').hide();
				Ext.getCmp('itemPanel').hide();
				Ext.getCmp('eventPanel').hide();
				Ext.getCmp('promotionPanel').hide();
			}
			else if (linkTo.inputValue == '1') { // URL
				Ext.getCmp('urlPanel').show();
				Ext.getCmp('itemPanel').hide();
				Ext.getCmp('eventPanel').hide();
				Ext.getCmp('promotionPanel').hide();
			}
			else if (linkTo.inputValue == '2') { // Item
				Ext.getCmp('urlPanel').hide();
				Ext.getCmp('itemPanel').show();
				Ext.getCmp('eventPanel').hide();
				Ext.getCmp('promotionPanel').hide();
			}
			else if (linkTo.inputValue == '3') { // Event
				Ext.getCmp('urlPanel').hide();
				Ext.getCmp('itemPanel').hide();
				Ext.getCmp('eventPanel').show();
				Ext.getCmp('promotionPanel').hide();
			}
			else if (linkTo.inputValue == '4') { // Promotion
				Ext.getCmp('urlPanel').hide();
				Ext.getCmp('itemPanel').hide();
				Ext.getCmp('eventPanel').hide();
				Ext.getCmp('promotionPanel').show();
			}
		}
	}


	//-----------------------------------------------------------------------------
	// Display/hide dynamic fields for the "banner ad"
	//-----------------------------------------------------------------------------
	var updateFieldsBannerAd = function() {
		var fileName = Ext.getCmp('bannerFile').getValue();
		if (fileName != '') {
			fileName = fileName.toUpperCase();
			// IMAGE
			if(fileName.indexOf(MEDIA_SUFFIX_1, fileName.length - MEDIA_SUFFIX_1.length) !== -1 ||
					//fileName.indexOf(MEDIA_SUFFIX_2, fileName.length - MEDIA_SUFFIX_2.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_3, fileName.length - MEDIA_SUFFIX_3.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_4, fileName.length - MEDIA_SUFFIX_4.length) !== -1){
				Ext.getCmp('bannerFileSizePanel').hide();
				Ext.getCmp('bannerSkipAfterPanel').show();
				Ext.getCmp('bannerNbSecOrAsEvent').hide();
				Ext.getCmp('bannerSkipAfterAsEvent').hide();
				Ext.getCmp('bannerSkipAfterAsEventHelp').hide();
				Ext.getCmp('bannerSkipAfterComplete').hide();
			}
			// SWF or ZIP
			else if(fileName.indexOf(MEDIA_SUFFIX_8, fileName.length - MEDIA_SUFFIX_8.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_9, fileName.length - MEDIA_SUFFIX_9.length) !== -1){
				Ext.getCmp('bannerFileSizePanel').show();
				Ext.getCmp('bannerWidth').setValue('468');
				Ext.getCmp('bannerHeight').setValue('60');
				Ext.getCmp('bannerSkipAfterPanel').show();
				Ext.getCmp('bannerNbSecOrAsEvent').show();
				Ext.getCmp('bannerSkipAfterAsEvent').show();
				Ext.getCmp('bannerSkipAfterAsEventHelp').show();
				Ext.getCmp('bannerSkipAfterComplete').hide();
			}
			// MOVIE
			else if(fileName.indexOf(MEDIA_SUFFIX_5, fileName.length - MEDIA_SUFFIX_5.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_6, fileName.length - MEDIA_SUFFIX_6.length) !== -1 ||
					fileName.indexOf(MEDIA_SUFFIX_7, fileName.length - MEDIA_SUFFIX_7.length) !== -1){
				Ext.getCmp('bannerFileSizePanel').hide();
				Ext.getCmp('bannerSkipAfterPanel').show();
				Ext.getCmp('bannerNbSecOrAsEvent').show();
				Ext.getCmp('bannerSkipAfterAsEvent').hide();
				Ext.getCmp('bannerSkipAfterAsEventHelp').hide();
				Ext.getCmp('bannerSkipAfterComplete').show();
			}
		}
		else {
			Ext.getCmp('bannerFileSizePanel').hide();
			Ext.getCmp('bannerSkipAfterPanel').hide();
			Ext.getCmp('bannerNbSecOrAsEvent').hide();
			Ext.getCmp('bannerSkipAfterAsEvent').hide();
			Ext.getCmp('bannerSkipAfterAsEventHelp').hide();
			Ext.getCmp('bannerSkipAfterComplete').hide();
		}
		Ext.getCmp('bannerSkipAfterPanel').doLayout();
		
		// Link to ...
		var linkTo = Ext.getCmp('bannerLinkTo').getValue();
		if (linkTo) {
			if (linkTo.inputValue == '-1') { // None
				Ext.getCmp('bannerUrlPanel').hide();
				Ext.getCmp('bannerItemPanel').hide();
				Ext.getCmp('bannerEventPanel').hide();
				Ext.getCmp('bannerPromotionPanel').hide();
			}
			else if (linkTo.inputValue == '1') { // URL
				Ext.getCmp('bannerUrlPanel').show();
				Ext.getCmp('bannerItemPanel').hide();
				Ext.getCmp('bannerEventPanel').hide();
				Ext.getCmp('bannerPromotionPanel').hide();
			}
			else if (linkTo.inputValue == '2') { // Item
				Ext.getCmp('bannerUrlPanel').hide();
				Ext.getCmp('bannerItemPanel').show();
				Ext.getCmp('bannerEventPanel').hide();
				Ext.getCmp('bannerPromotionPanel').hide();
			}
			else if (linkTo.inputValue == '3') { // Event
				Ext.getCmp('bannerUrlPanel').hide();
				Ext.getCmp('bannerItemPanel').hide();
				Ext.getCmp('bannerEventPanel').show();
				Ext.getCmp('bannerPromotionPanel').hide();
			}
			else if (linkTo.inputValue == '4') { // Promotion
				Ext.getCmp('bannerUrlPanel').hide();
				Ext.getCmp('bannerItemPanel').hide();
				Ext.getCmp('bannerEventPanel').hide();
				Ext.getCmp('bannerPromotionPanel').show();
			}
			if (linkTo.inputValue == '5') { // Fullscreen ad
				Ext.getCmp('bannerUrlPanel').hide();
				Ext.getCmp('bannerItemPanel').hide();
				Ext.getCmp('bannerEventPanel').hide();
				Ext.getCmp('bannerPromotionPanel').hide();
			}
		}
	}

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: AD_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Ads CRUD
	//-----------------------------------------------------------------------------
	var adGridCRUD = new HurryApp.GridCRUD({
		type: 'ad',
		region: 'center',
		width: '70%',
		autoHeight: false,
		
		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: myAppContext+'/ad/load',
		urlSave: myAppContext+'/ad/save',
		urlEdit: myAppContext+'/ad/edit',
		urlDelete: myAppContext+'/ad/delete',

		title:               AD_LISTE,
		formWindowTitle:     AD_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    AD_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: AD_TOOLTIP_DELETE,
		labelDeleteQuestion: AD_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   AD_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    600,
		formWindowMinWidth: 600,
		formLabelWidth:     50,
		fieldAnchor:        '90%',
		
		displayPagingBar: true,
		loadOnStartup: false,
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT,
		
		templateExpander:
			'<table cellspacing="0" cellpadding="0" width="100%">' +
			'<tpl if="type !== null"><tr height="25"><td width="15%">'+AD_FULLSCREEN+':</td><td width="5%"><div class="tool-{type}" style="width:20px;height:20px;" title="{type}">&nbsp;</div></td><td width="40%">'+AD_SKIPAFTER+':{skipAfter}</td><td width="35%">'+AD_LINKEDTO+': <tpl if="linkedTo !== null">{linkedTo}</tpl></td><td width="5%" align="right"><div class="tool-preview" style="width:16px;cursor:pointer;" onclick="previewAd({id}, \'fullscreen\', \'{file}\'<tpl if="width !== null && height !== null">, {width}, {height}</tpl>);" title="'+COMMON_PREVIEW+'">&nbsp;</div></td></tr></tpl>' +
			'<tpl if="bannerType !== null"><tr height="25"><td width="15%">'+AD_BANNER+':</td><td width="5%"><div class="tool-{bannerType}" style="width:20px;height:20px;" title="{bannerType}">&nbsp;</div></td><td width="40%">'+AD_SKIPAFTER+':{bannerSkipAfter}</td><td width="35%">'+AD_LINKEDTO+': <tpl if="bannerLinkedTo !== null">{bannerLinkedTo}</tpl></td><td width="5%" align="right"><div class="tool-preview" style="width:16px;cursor:pointer;" onclick="previewAd({id}, \'banner\', \'{bannerFile}\'<tpl if="bannerWidth !== null && bannerHeight !== null">, {bannerWidth}, {bannerHeight}</tpl>);" title="'+COMMON_PREVIEW+'">&nbsp;</div></td></tr></tpl>' +
			'</table>',
		
		//templateExpander:
		//	'<table cellspacing="0" cellpadding="0" width="100%">' +
		//	'<tpl if="type !== null"><tr height="25"><td width="15%">'+AD_FULLSCREEN+':</td><td width="5%"><div class="tool-{type}" style="width:20px;height:20px;" title="{type}">&nbsp;</div></td><td width="40%">'+AD_SKIPAFTER+':{skipAfter}</td><td width="35%">'+AD_LINKEDTO+': <tpl if="linkedTo !== null">{linkedTo}</tpl></td><td width="5%" align="right"><div class="tool-preview" style="width:16px;cursor:pointer;" onclick="previewAd({id}, \'fullscreen\', \'{file}\'<tpl if="width !== null && height !== null">, {width}, {height}</tpl>);" title="'+COMMON_PREVIEW+'">&nbsp;</div></td></tr></tpl>' +
		//	'<tpl if="bannerType !== null"><tr height="25"><td width="15%">'+AD_BANNER+':</td><td width="5%"><div class="tool-{bannerType}" style="width:20px;height:20px;" title="{bannerType}">&nbsp;</div></td><td width="40%">'+AD_SKIPAFTER+':{bannerSkipAfter}</td><td width="35%">'+AD_LINKEDTO+': <tpl if="bannerLinkedTo !== null">{bannerLinkedTo}</tpl></td><td width="5%" align="right"><div class="tool-preview" style="width:16px;cursor:pointer;" onclick="previewAd({id}, \'banner\', \'{bannerFile}\'<tpl if="bannerWidth !== null && bannerHeight !== null">, {bannerWidth}, {bannerHeight}</tpl>);" title="'+COMMON_PREVIEW+'">&nbsp;</div></td></tr></tpl>' +
		//	'</table>',
		
		createFormCallback: function(formType) {
			if (formType == 'create') {
				updateFieldsFullscreenAd();
				updateFieldsBannerAd();
				Ext.getCmp('fullscreenFieldset').collapse();
				Ext.getCmp('bannerFieldset').collapse();
			}
		},
		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			form.setValues(datas);
			updateFieldsFullscreenAd();
			updateFieldsBannerAd();
			if (datas.fullscreen == null) {
				Ext.getCmp('fullscreenFieldset').collapse();
			}
			if (datas.banner == null) {
				Ext.getCmp('bannerFieldset').collapse();
			}
		},
		beforeSubmit: function() {
			if (this.getForm().findField('id').getValue() == '') {
				this.getForm().setValues({
					'viewBean.index': adGridCRUD.store.getCount()
				});
			}
		},
		menuButtons: [
  			{
  				id: 'upButton-ad',
  				text: AD_BUTTON_UP,
  				tooltip: AD_BUTTON_UP_TOOLTIP,
  				iconCls: 'tool-up',
  				handler: function() {
  					var selectedRecord = adGridCRUD.getSelectionModel().getSelected();
  					if (selectedRecord) {
  						var selectedRecordIndex = adGridCRUD.store.indexOf(selectedRecord);
  						if (selectedRecordIndex > 0) {
  							adGridCRUD.store.remove(selectedRecord);
  							adGridCRUD.store.insert(selectedRecordIndex-1, selectedRecord);
  							adGridCRUD.getSelectionModel().selectRow(selectedRecordIndex-1);
  							for (var i=0; i<adGridCRUD.store.data.items.length; i++) {
  								adGridCRUD.store.data.items[i].data.index = i;
  							};
  							HurryApp.Utils.sendAjaxRequest(myAppContext+'/ad/updateIndexes', {'selectedObjects' : adGridCRUD.getObjects()});
  						}
  					}
  				}
  			}		
  			,{
  				id: 'downButton-ad',
  				text: AD_BUTTON_DOWN,
  				tooltip: AD_BUTTON_DOWN_TOOLTIP,
  				iconCls: 'tool-down',
  				handler: function() {
  					var selectedRecord = adGridCRUD.getSelectionModel().getSelected();
  					if (selectedRecord) {
  						var selectedRecordIndex = adGridCRUD.store.indexOf(selectedRecord);
  						if (selectedRecordIndex < adGridCRUD.store.getCount()-1) {
  							adGridCRUD.store.remove(selectedRecord);
  							adGridCRUD.store.insert(selectedRecordIndex+1, selectedRecord);
  							adGridCRUD.getSelectionModel().selectRow(selectedRecordIndex+1);
  							for (var i=0; i<adGridCRUD.store.data.items.length; i++) {
  								adGridCRUD.store.data.items[i].data.index = i;
  							};
  							HurryApp.Utils.sendAjaxRequest(myAppContext+'/ad/updateIndexes', {'selectedObjects' : adGridCRUD.getObjects()});
  						}
  					}
  				}
  			}		
  		]
	});

	return {
		getComponent: function() {
			return adGridCRUD;
		}
	}
}

var previewAd = function(adId, type, file, width, height) {
	var options = 'width=800,height=560';
	var previewUrl = myAppContext+'/project-exports/'+window.projectKey+'/ads/'+adId+'/'+type+'/'+file;//+'?t='+HurryApp.Utils.getTimestamp();
	
	var fileUpperCase = file.toUpperCase();
	// If SWF
	if (fileUpperCase.indexOf(MEDIA_SUFFIX_8, fileUpperCase.length - MEDIA_SUFFIX_8.length) !== -1) {
		options = 'width='+(width+20)+',height='+(height+20);
		//previewUrl = myAppContext+'/pages/ad/modules/swf-preview.jsp?file='+previewUrl+'&width='+width+'&height='+height;
		$.fancybox( {href : previewUrl, type : "swf", 'width' : width, 'height' : height} );
	}
	// If MOVIE
	else if (fileUpperCase.indexOf(MEDIA_SUFFIX_5, fileUpperCase.length - MEDIA_SUFFIX_5.length) !== -1 ||
		fileUpperCase.indexOf(MEDIA_SUFFIX_6, fileUpperCase.length - MEDIA_SUFFIX_6.length) !== -1 ||
		fileUpperCase.indexOf(MEDIA_SUFFIX_7, fileUpperCase.length - MEDIA_SUFFIX_7.length) !== -1) {
		//previewUrl = myAppContext+'/pages/ad/modules/movie-preview.jsp?file='+previewUrl;
		$.fancybox( {href : myAppContext+"/pages/ad/modules/player.swf",
			maxWidth    : 780,
	        maxHeight   : 542
		} );
		jwplayer('fancybox-mov').setup({
    			flashplayer: myAppContext+"/pages/ad/modules/player.swf",
    			file: previewUrl,
    			width:"780",
    			height:"542"
    	} );	
	} else {
		$.fancybox( {href : previewUrl, title : 'Ad preview'} );
	}

	//window.open(previewUrl, 'preview', (options != null ? options+',' : '')+'resizable=yes,scrollbars=yes,toolbar=no,status=no,menubar=no');
}