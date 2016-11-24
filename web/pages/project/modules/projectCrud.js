ProjectCrud = function() {
	var base = this;
	base.checkStatus = -1;
	window.onCheckboxClick = function(event) {
		base.checkStatus = event.target.checked ? 1 : 0;
		//event.preventDefault();
		//event.stopPropagation();
	};
	var tplchecked = '<input type="checkbox" {0} onclick="onCheckboxClick(event)">';
    var IsChecked = function (value) {
    	var ifCheck = (value == "" || new Date(value).valueOf() < new Date().valueOf()) ? '' : 'checked';
        return String.format(tplchecked, ifCheck);
    }
    
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: PROJECT_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left',
			width: 280
		}
		,{
			header: PROJECT_COMPANY,
			dataIndex: 'company',
			sortable: false,
			align: 'left',
			width: 200
		}
		,{
			header: PROJECT_DATE_EXPIRE,
			dataIndex: 'dateExpire',
			sortable: false,
			align: 'left'
		}
		/*,{
			header: PROJECT_ADS_DATE_EXPIRE,
			dataIndex: 'adsDateExpire',
			sortable: false,
			align: 'center',
			renderer: IsChecked
		}*/
		,{
			header: "Company ID",
			dataIndex: 'companyId',
			hidden: true
		}
		,{
			header: "Key",
			dataIndex: 'key',
			hidden: true
		}
	];
	
	if(window.userGroupId == 1) {
		columns.push({header: PROJECT_ADS_DATE_EXPIRE,dataIndex: 'adsDateExpire',sortable: false,align: 'center',renderer: IsChecked});
	}
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'textfield',
			fieldLabel: PROJECT_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'textfield',
			fieldLabel: PROJECT_ORDERNUMBER,
			allowBlank: true,
			id: 'orderNumber',
			name: 'viewBean.orderNumber'
		}
		,{
			xtype: 'textfield',
			fieldLabel: PROJECT_SALESFORCELINK,
			allowBlank: true,
			id: 'salesforceLink',
			name: 'viewBean.salesforceLink'
		}
		/*,{
			xtype: 'fileuploadfield',
			fieldLabel: PROJECT_LOGO,
			allowBlank: true,
			id: 'logo',
			name: 'viewBean.logo',
			buttonText: '',
			buttonCfg: {
				iconCls: 'tool-add-image'
			},
			fileType: 'image'
		}*/
	];

	var key = {
		xtype: 'textfield',
		fieldLabel: PROJECT_KEY_FULL,
		allowBlank: true,
		id: 'key',
		name: 'viewBean.key',
		readOnly: true
	};
	
	if (window.userGroupId == GROUPE_SUPER_ADMIN) {
		fields.push({
			xtype: 'combo',
			fieldLabel: PROJECT_COMPANY,
			id: 'companyId',
			name: 'viewBean.company',
			hiddenName: 'viewBean.companyId',
			valueField: 'id',
			displayField: 'name',
			store: new Ext.data.JsonStore({
				fields: ['id', 'name'],
				data : companys
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus: true,
			editable: false
		});
	}
	else {
		fields.push({
			xtype: 'hidden',
			id: 'companyId',
			name: 'viewBean.companyId',
			value: window.userCompanyId
		});
	}
	
	fields.push({
		fieldLabel: PROJECT_START,
		xtype: 'datefield',
		allowBlank: true,
		id: 'dateStart',
		name: 'viewBean.dateStart',
	});
	
	fields.push({
		fieldLabel: PROJECT_END,
		xtype: 'datefield',
		allowBlank: true,
		id: 'dateExpire',
		name: 'viewBean.dateExpire',
	});
	
	/*fields.push({
		layout: 'hbox',
		width: 330,
		border: false,
		//style: 'padding-bottom:5px;',
		items: [
			{
				xtype: 'label',
				text: PROJECT_START+':',
				width: 105,
				style: 'padding-top:2px;'
			}
			,{
				xtype: 'datefield',
				allowBlank: true,
				id: 'dateStart',
				name: 'viewBean.dateStart',
				width: 100
			}
		]
	});
	
	fields.push({
		layout: 'hbox',
		width: 330,
		border: false,
		//style: 'padding-bottom:5px;',
		items: [
			{
				xtype: 'label',
				text: PROJECT_END+':',
				width: 105,
				style: 'padding-top:2px;'
			}
			,{
				xtype: 'datefield',
				allowBlank: true,
				id: 'dateExpire',
				name: 'viewBean.dateExpire',
				width: 100
			}
		]
	});*/

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: PROJECT_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	var companySearch = {
		xtype: 'combo',
		fieldLabel: COMMON_SEARCH,
		id: 'companySearch',
		hiddenId: 'companyIdSearch',
		name: 'viewBean.company',
		hiddenName: 'viewBean.companyId',
		valueField: 'id',
		displayField: 'name',
		store: new Ext.data.JsonStore({
			fields: ['id', 'name'],
			data : [{id: '-1', name: COMMON_ALL}].concat(companys)
		}),
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		selectOnFocus:true,
		listeners:{'select': function() {projectGridCRUD.search();}}
	}
	
	//-----------------------------------------------------------------------------
	// Projects CRUD
	//-----------------------------------------------------------------------------
	var projectGridCRUD = new HurryApp.GridCRUD({
		type: 'project',
		region: 'center',
		width: '50%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
		loadOnStartup: true,
		enableEditForm: false,
		displayEditButton: false,
	
		urlLoad: myAppContext+'/project/load',
		urlSave: myAppContext+'/project/save',
		urlEdit: myAppContext+'/project/edit',
		urlDelete: myAppContext+'/project/delete',
		urlDblClick: myAppContext+'/project/init',

		title:               PROJECT_LISTE,
		formWindowTitle:     PROJECT_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    PROJECT_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: PROJECT_TOOLTIP_DELETE,
		labelDeleteQuestion: PROJECT_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   PROJECT_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		editFields: [key].concat(fields),
		searchFields: window.userGroupId == GROUPE_SUPER_ADMIN ? [nameSearch] : null,
		displayAddButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		displayDeleteButton: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		displayToolBar: window.userGroupId == GROUPE_SUPER_ADMIN ? true : false,
		
		formWindowWidth:    390,
		formWindowMinWidth: 390,
		formLabelWidth:     100,
		
		displayPagingBar: true,
		
		fnClick: function(gridPanel, rowIndex, columnIndex, event) {
			if(columnIndex == columns.length - 1 && base.checkStatus != -1) {
				var projectRow = gridPanel.store.getAt(rowIndex);
				HurryApp.Utils.sendAjaxRequest(myAppContext+'/project/save', {
					'viewBean.id' : projectRow.data.id,
					'viewBean.name' : projectRow.data.name,
					'viewBean.companyId' : projectRow.data.companyId,
					'viewBean.adsDateExpire' : (base.checkStatus == 1) ? '2030-01-01' : '1970-01-01'
				}, 
				function(jsonResponse) {
				});
				base.checkStatus = -1;
			}
		}
	});

	return {
		getComponent: function() {
			return projectGridCRUD;
		}
	}
}
