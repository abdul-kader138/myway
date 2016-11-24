NewsletterEmailCrud = function() {

	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'email',
			header: NEWSLETTEREMAIL_EMAIL,
			dataIndex: 'email',
			sortable: true,
			align: 'left',
			width: 150
		}
		,{
			header: NEWSLETTEREMAIL_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left',
			width: 100
		}
		,{
			id: 'firstName',
			header: NEWSLETTEREMAIL_FIRSTNAME,
			dataIndex: 'firstName',
			sortable: true,
			align: 'left',
			width: 100
		}
		,{
			id: 'licenseName',
			header: NEWSLETTEREMAIL_LICENSENAME,
			dataIndex: 'licenseName',
			sortable: true,
			align: 'left'
		}
		/*
		,{
			header: NEWSLETTEREMAIL_CITY,
			dataIndex: 'city',
			sortable: true,
			align: 'left'
		}
		,{
			header: NEWSLETTEREMAIL_GENDER,
			dataIndex: 'genderId',
			sortable: true,
			align: 'center'
		}
		*/
	];
	
	//-----------------------------------------------------------------------------
	// Form fields
	//-----------------------------------------------------------------------------
	var fields = [
		{
			xtype: 'hidden',
			id: 'projectId',
			name: 'viewBean.projectId'
		}
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_EMAIL,
			allowBlank: true,
			id: 'email',
			name: 'viewBean.email',
			vtype: 'email'
		}
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_NAME,
			allowBlank: true,
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_FIRSTNAME,
			allowBlank: true,
			id: 'firstName',
			name: 'viewBean.firstName'
		}
		,{
			xtype: 'combo',
			fieldLabel: ITEM_CATEGORY,
			id: 'licenseId',
			name: 'viewBean.LicenseName',
			hiddenName: 'viewBean.LicenseId',
			valueField: 'id',
			displayField: 'key',
			store: new Ext.data.JsonStore({
				fields: ['id', 'key'],
				data : [{id: null, key: '-'}].concat(licenses)
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			editable: false,
		}
		/*
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_CITY,
			allowBlank: true,
			id: 'city',
			name: 'viewBean.city'
		}
		,{
			xtype: 'combo',
			fieldLabel: NEWSLETTEREMAIL_GENDER,
			id: 'genderId',
			name: 'viewBean.gender',
			hiddenName: 'viewBean.genderId',
			valueField: 'id',
			displayField: 'value',
			store: new Ext.data.JsonStore({
				fields: ['id', 'value'],
				data : [{id: 'F', value: COMMON_FEMALE}, {id: 'M', value: COMMON_MALE}]
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			editable: false
		}
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_CATEGORIES,
			allowBlank: true,
			id: 'categories',
			name: 'viewBean.categories'
		}
		,{
			xtype: 'textfield',
			fieldLabel: NEWSLETTEREMAIL_ITEMS,
			allowBlank: true,
			id: 'items',
			name: 'viewBean.items'
		}
		*/
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var emailSearch = {
		xtype: 'textfield',
		fieldLabel: NEWSLETTEREMAIL_EMAIL,
		id: 'emailSearch',
		name: 'viewBean.email'
	};
	
	//-----------------------------------------------------------------------------
	// NewsletterEmails CRUD
	//-----------------------------------------------------------------------------
	var newsletterEmailGridCRUD = new HurryApp.GridCRUD({
		type: 'newsletterEmail',
		region: 'center',
		width: '100%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'firstName',
	
		urlLoad: myAppContext+'/newsletter/load',
		urlSave: myAppContext+'/newsletter/save',
		urlEdit: myAppContext+'/newsletter/edit',
		urlDelete: myAppContext+'/newsletter/delete',
		defaultParams: {'viewBean.projectId': window.projectId},

		loadFormCallback: function(form, action) {
			var datas = Ext.decode(action.response.responseText).datas[0];
			this.form = form;
			
			var licenseCombo = Ext.getCmp('licenseId');
			licenseCombo.setValue(datas.licenseId);
		},
		
		title:               NEWSLETTEREMAIL_LISTE,
		formWindowTitle:     NEWSLETTEREMAIL_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    NEWSLETTEREMAIL_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: NEWSLETTEREMAIL_TOOLTIP_DELETE,
		labelDeleteQuestion: NEWSLETTEREMAIL_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   NEWSLETTEREMAIL_MESSAGE_DELETE_ADVERT,
		
		createFields: fields,
		searchFields: [emailSearch],
		menuButtons: [
			{
				text: COMMON_EXPORT,
				tooltip: NEWSLETTEREMAIL_TOOLTIP_EXPORT,
				iconCls: 'tool-excel',
				handler: function() {
					window.location = myAppContext+'/newsletter/export';
				}
			}
		],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: true
	});

	return {
		getComponent: function() {
			return newsletterEmailGridCRUD;
		}
	}
}
