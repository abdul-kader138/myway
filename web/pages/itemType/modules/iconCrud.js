IconCrud = function(iconsPath) {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: ICON_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'itemTypeId',
			name: 'viewBean.itemTypeId'
		}
		,{
			xtype: 'hidden',
			id: 'iconGroupId',
			name: 'viewBean.iconGroupId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: ICON_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'fileuploadfield',
			fieldLabel: ICON_ICON,
			allowBlank: true,
			id: 'icon',
			name: 'viewBean.icon',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			fileType: 'image'
		}
		,{
			xtype: 'panel',
			border: false,
			html: ICON_ICON_TOOLTIP,
			bodyCssClass: 'ha-plain-panel',
			cls: 'ha-field-help',
			style: 'vertical-align: top; padding-left: 55px; overflow: hidden;'
		}
	];
	
	var customFields = [
	      		{
	      			xtype: 'hidden',
	      			id: 'itemTypeId',
	      			name: 'viewBean.itemTypeId'
	      		}
	      		,{
	      			xtype: 'hidden',
	      			id: 'iconGroupId',
	      			name: 'viewBean.iconGroupId'
	      		}
	      		,{
	    			xtype: 'combo',
	    			fieldLabel: ICON_NAME,
	    			id: 'name',
	    			name: 'viewBean.name',
	    			hiddenName: 'viewBean.name',
	    			valueField: 'iconName',
	    			displayField: 'iconName',
	    			store: new Ext.data.SimpleStore({
	    				fields: ['iconName'],
	    				data: [
	    				['disable-on'],
	    				['disable-off']
	    				]
	    			}),
	    			typeAhead: true,
	    			mode: 'local',
	    			selectOnFocus:true,
	    			editable: false,
	    		} ,{
	      			xtype: 'fileuploadfield',
	      			fieldLabel: ICON_ICON,
	      			allowBlank: true,
	      			id: 'icon',
	      			name: 'viewBean.icon',
	      			buttonText: '',
	      			buttonCfg: {
	      				iconCls: 'tool-add-image'
	      			},
	      			fileType: 'image'
	      		}
	      		,{
	      			xtype: 'panel',
	      			border: false,
	      			html: ICON_ICON_TOOLTIP,
	      			bodyCssClass: 'ha-plain-panel',
	      			cls: 'ha-field-help',
	      			style: 'vertical-align: top; padding-left: 55px; overflow: hidden;'
	      		}
	      	];	

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: ICON_NAME,
		id: 'nameSearch2',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Util functions
	//-----------------------------------------------------------------------------
	var loadIconGallery = function() {
		var mask = new Ext.LoadMask('iconsPanel', {});
		mask.show();
		iconDataView.store.load({
			params: iconGallery.defaultParams,
			callback: function() {mask.hide();}
		});
	}

	var formatData = function(data){
    	data.name = data.name.ellipse(12);
    	return data;
    }

	//-----------------------------------------------------------------------------
	// Icon gallery
	//-----------------------------------------------------------------------------
	var iconDataView = new Ext.DataView({
		id: 'iconsDataView',
		cls: 'ha-images-view',
		tpl:'<tpl for=".">'+
	            '<div class="ha-images-view-item" style="width:70px;height:50px;text-align:center;" id="" ondblclick="iconGallery.editEntity({id});">'+
			    '<div class=""><img src="'+myAppContext+iconsPath+'/icons/{subFolder}/{icon}?t={timestamp}" width="35" height="35"></div>'+
			    '<span class="">{name}</span></div>'+
	        '</tpl>'+
	        '<div class="x-clear"></div>',
		itemSelector: 'div.ha-images-view-item',
		overClass: 'ha-over',
		selectedClass: 'ha-selected',
		singleSelect: true,
		multiSelect: true,
		autoHeight: true,
		store: {
			xtype: 'jsonstore',
			autoLoad: false,
			root: 'datas',
			fields: ['id', 'name', 'icon', 'subFolder', 'timestamp'],
			url: myAppContext+'/icon/load'
		},
		prepareData: formatData.createDelegate(this)
	});
	
	//-----------------------------------------------------------------------------
	// Toolbar items
	//-----------------------------------------------------------------------------
	var toolbarItems = [
		{
			id: 'addButton-icon',
			text: COMMON_ADD,
			tooltip: ICON_TOOLTIP_ADD,
			iconCls: 'tool-add',
			handler: function() {
				iconGallery.createEntity();
			}
		}
		,{
			id: 'deleteButton-icon',
			text: COMMON_DELETE,
			tooltip: ICON_TOOLTIP_DELETE,
			iconCls: 'tool-remove',
			handler: function() {
				iconGallery.deleteEntity(HurryApp.Utils.toArrayOfString(HurryApp.Utils.toJsonDatas(iconDataView.getSelectedRecords()), 'id'));
			}
		}
	];
	
	if (iconsPath != '') {
		toolbarItems.push('->');
		toolbarItems.push({
			xtype: 'button',
			text: ICON_BUTTON_RESTOREDEFAULT,
			iconCls: 'tool-db-refresh',
			handler: function() {
				// Synchronisation des ic�nes avec ceux du type d'item associ�
				HurryApp.Utils.sendAjaxRequest(
					myAppContext+'/icon/restoreDefaultIcons',
					{
						'viewBean.iconGroupId': iconGallery.defaultParams['viewBean.iconGroupId']
					},
					function() {
						loadIconGallery();
					},
					'iconsPanel'
				);
			}
		});
	}
	
	//-----------------------------------------------------------------------------
	// CRUD
	//-----------------------------------------------------------------------------
	iconGallery = new HurryApp.CRUD({
		id: 'iconsPanel',
		type: 'icon',
		region: 'east',
		width: '80%',
		split: true,
		autoHeight: false,
		border: true,
		frame: false,
		layout: 'fit',

		urlSave: myAppContext+'/icon/save',
		urlEdit: myAppContext+'/icon/edit',
		urlDelete: myAppContext+'/icon/delete',
		successCallback: function() {
			loadIconGallery();
		},

		title:               ICON_LISTE,
		formWindowTitle:     ICON_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelDeleteQuestion: ICON_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   ICON_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     50,
		
		fileUpload: true,
		timeout: UPLOAD_TIMEOUT,

		tbar: {
			items: toolbarItems
		},
		items: [
			{
				xtype: 'panel',
				frame: true,
				border: true,
				autoScroll: true,
				bodyStyle: 'background-color: #FFFFFF;',
				items: [iconDataView]
			}
		]
	});
	
	return {
		getComponent: function() {
			return iconGallery;
		},
		
		loadIconGallery: function() {
			loadIconGallery();
		},
		
		clearIconGallery: function() {
			iconDataView.store.loadData({
				datas: []
			});
		},
		
		setCustomFields: function() {
			iconGallery.initialConfig.createFields = customFields;
			iconGallery.initialConfig.editFields = customFields;
		},
		
		restoreFields: function() {
			iconGallery.initialConfig.createFields = fields;
			iconGallery.initialConfig.editFields = fields;
		}
	}
}
