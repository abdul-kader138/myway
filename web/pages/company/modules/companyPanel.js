CompanyPanel = function() {

	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var createFields = [
		{
			xtype: 'textfield',
			fieldLabel: COMPANY_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_ADDRESS,
			allowBlank: true,
			id: 'address',
			name: 'viewBean.address'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_CITY,
			allowBlank: true,
			id: 'city',
			name: 'viewBean.city'
		}
		,{
			xtype: 'textfield',
			fieldLabel: COMPANY_COUNTRY,
			allowBlank: true,
			id: 'country',
			name: 'viewBean.country'
		}
	];
	
	var editFields = [
		{
			xtype: 'textfield',
			fieldLabel: COMPANY_IDSTR,
			allowBlank: true,
			id: 'idStr',
			name: 'viewBean.idStr',
			readOnly: true,
			cls: 'ha-field-disabled'
		}
	].concat(createFields);

	//-----------------------------------------------------------------------------
	// Companys CRUD
	//-----------------------------------------------------------------------------
	var companyCRUD = new HurryApp.CRUD({
		id: 'companyPanel',
		type: 'company',
		region: 'north',
		width: '100%',
		split: true,
		autoHeight: false,
		height: 35,
		layout: 'hbox',
		//frame: false,

		urlSave: myAppContext+'/company/save',
		urlEdit: myAppContext+'/company/edit',
		urlDelete: myAppContext+'/company/delete',

		//title:               COMPANY_TITRE,
		formWindowTitle:     COMPANY_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelDeleteQuestion: COMPANY_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   COMPANY_MESSAGE_DELETE_ADVERT,
		
		createFields: createFields,
		createFields: editFields,
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		listeners: {
			'render': function() {
				HurryApp.Utils.sendAjaxRequest(myAppContext+'/company/edit', {'viewBean.id' : window.userCompanyId}, function(jsonResponse) {
					var datas = jsonResponse.datas[0];
					Ext.getCmp('companyName').setValue(datas.name);
					Ext.getCmp('companyIdStr').setValue(datas.idStr);
				}, 'companyPanel');
			}
		},
		successCallback: function(response) {
			Ext.getCmp('companyName').setValue(response.datas[0].name);
		},
		
		items: [
			{
				xtype: 'label',
				text: COMPANY_NAME_FULL+':',
				style: 'padding-top:4px;'
			}
			,{
				xtype: 'textfield',
				id: 'companyName',
				readOnly: true,
				cls: 'ha-field-disabled',
				style: 'margin-left:10px;',
				width: 200
			}
			,{
				xtype: 'label',
				text: COMPANY_IDSTR_FULL+':',
				style: 'padding-top:4px;margin-left:20px;'
			}
			,{
				xtype: 'textfield',
				id: 'companyIdStr',
				readOnly: true,
				cls: 'ha-field-disabled',
				style: 'margin-left:30px;',
				width: 100
			}
			,{
				xtype: 'button',
				text: COMMON_EDIT,
				iconCls: 'tool-edit2',
				style: 'margin-left:40px;',
				handler: function() {
					companyCRUD.editEntity(window.userCompanyId);
				}
			}
		]
	});

	return {
		getComponent: function() {
			return companyCRUD;
		}
	}
}
