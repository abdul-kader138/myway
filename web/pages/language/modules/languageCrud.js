LanguageCrud = function(template) {

	var langMap = [
		['dz', 'Arabic-Algeria', 'ux-flag-dz', 'ar-dz'],
		['bh', 'Arabic-Bahrain', 'ux-flag-bh', 'ar-bh'],
		['com', 'Arabic-Comoros', 'ux-flag-com', 'ar-com'],
		['dji', 'Arabic-Djibouti', 'ux-flag-dji', 'ar-dji'],
		['eg', 'Arabic-Egypt', 'ux-flag-eg', 'ar-eg'],
		['iq', 'Arabic-Iraq', 'ux-flag-iq', 'ar-iq'],
		['jo', 'Arabic-Jordan', 'ux-flag-jo', 'ar-jo'],
		['kw', 'Arabic-Kuwait', 'ux-flag-kw', 'ar-kw'],
		['lb', 'Arabic-Lebanon', 'ux-flag-lb', 'ar-lb'],
		['ly', 'Arabic-Libya', 'ux-flag-ly', 'ar-ly'],
		['mrt', 'Arabic-Mauritania', 'ux-flag-mrt', 'ar-mrt'],
		['ma', 'Arabic-Morocco', 'ux-flag-ma', 'ar-ma'],
		['om', 'Arabic-Oman', 'ux-flag-om', 'ar-om'],
		['ps', 'Arabic-Palestine', 'ux-flag-ps', 'ar-ps'],
		['qa', 'Arabic-Qatar', 'ux-flag-qa', 'ar-qa'],
		['sa', 'Arabic-Saudi Arabia', 'ux-flag-sa', 'ar-sa'],
		['so', 'Arabic-Somalia', 'ux-flag-so', 'ar-so'],
		['sud', 'Arabic-Sudan', 'ux-flag-sud', 'ar-sud'],
		['sy', 'Arabic-Syria', 'ux-flag-sy', 'ar-sy'],
		['tn', 'Arabic-Tunisia', 'ux-flag-tn', 'ar-tn'],
		['ae', 'Arabic-UAE', 'ux-flag-ae', 'ar-ae'],
		['eh', 'Arabic-Western Sahara', 'ux-flag-eh', 'ar-eh'],
		['ye', 'Arabic-Yemen', 'ux-flag-ye', 'ar-ye'],
		['cn', 'Chinese', 'ux-flag-cn', 'zh-cn'],
		['nl', 'Dutch-Netherlands', 'ux-flag-nl', 'nl-nl'],
		['gb', 'English-UK', 'ux-flag-gb', 'en-gb'], 
		['us', 'English-US', 'ux-flag-us', 'en-us'], 
		['fr', 'French-France', 'ux-flag-fr', 'fr-fr'], 
		['de', 'German-Germany', 'ux-flag-de', 'de-de'], 
		['it', 'Italian-Italy', 'ux-flag-it', 'it-it'], 
		['br', 'Portuguese-Brazil', 'ux-flag-br', 'pt-br'],
		['ru', 'Russian-Russia', 'ux-flag-ru', 'ru-ru'], 
		['es', 'Spanish-Spain', 'ux-flag-es', 'es-es'], 
		['tr', 'Turkish', 'ux-flag-tr', 'tr-tr']
	];
	
	var getCountriesByLang = function(lang) {
		var countries = [];
		if(lang == "") return countries;
		
		for(var i = 0, n = langMap.length; i < n; ++i) {
			if(langMap[i][3].indexOf(lang) < 0) continue;
			countries.push(langMap[i]);
		}
		return countries;
	};
	
	//-----------------------------------------------------------------------------
	// Column model
	//-----------------------------------------------------------------------------
	var columns = [
		{
			id: 'name',
			header: LANGUAGE_NAME,
			dataIndex: 'name',
			sortable: true,
			align: 'left'
		}
		,{
			header: LANGUAGE_DEFAULT,
			dataIndex: 'defaultLanguage',
			hidden: template ? true : false,
			sortable: false,
			align: 'center',
			width: 50,
			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
				return record.data.defaultLanguage == true ? '<b>x</b>' : '';
			}			
		}
		,{
			dataIndex: 'flag',
			hidden: true
		}
		,{
			dataIndex: 'code',
			hidden: true
		}
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
			xtype: 'combo',
			fieldLabel: LANGUAGE_TITRE,
			id: 'code',
			name: 'viewBean.code',
			hiddenName: 'viewBean.code',
			valueField: 'langCode',
			displayField: 'langName',
			store: new Ext.data.SimpleStore({
				fields: ['langCode', 'langName'],
				data: [
				['ar', 'Arabic'],
				['zh', 'Chinese'],
				['nl', 'Dutch'],
				['en', 'English'], 
				['fr', 'French'], 
				['de', 'German'], 
				['it', 'Italian'], 
				['pt', 'Portuguese'],
				['ru', 'Russian'], 
				['es', 'Spanish'], 
				['tr', 'Turkish']
				]
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			editable: false,
			listeners: {
				'select': function(combo, rex, index) {
					var langCode = combo.getValue();
					var countries = getCountriesByLang(langCode);
					if(countries.length > 0) {
						Ext.getCmp('flag').store.loadData(countries, false);
						Ext.getCmp('flag').setValue(countries[0][0]);
					}
				},
				'render': function() {
					var oldSetValue = this.setValue;
					this.setValue = function(val) {
						oldSetValue.call(this, val);
						var record = this.findRecord('langCode', val);
						this.fireEvent('select', this, [record]);
						this.setValue = oldSetValue;
					}
				},
			}
		}
		,{
			xtype: 'iconcombo',
			fieldLabel: LANGUAGE_COUNTRY,
			id: 'flag',
			name: 'viewBean.name',
			hiddenName: 'viewBean.flag',
			valueField: 'countryCode',
			displayField: 'countryName',
			iconClsField: 'countryFlag',
			store: new Ext.data.SimpleStore({
				fields: ['countryCode', 'countryName', 'countryFlag'],
				data: []
			}),
			typeAhead: true,
			mode: 'local',
			triggerAction: 'all',
			selectOnFocus:true,
			editable: false,
			listeners: {
				'select': function(combo, rex, index) {
					Ext.getCmp('name').setValue(combo.getRawValue());
				},
				'render': function() {
					var oldSetValue = this.setValue;
					this.setValue = function(val) {
						oldSetValue.call(this, val);
						var record = this.findRecord('langCode', val);
						this.fireEvent('select', this, [record]);
						this.setValue = oldSetValue;
					}
				},
			}
		}
		,{
			xtype: template ? 'hidden' : 'checkbox',
			fieldLabel: LANGUAGE_DEFAULT,
			id: 'defaultLanguage',
			name: 'viewBean.defaultLanguage',
			inputValue: true
		}
		,{
			xtype: 'hidden',
			id: 'name',
			name: 'viewBean.name'
		}
		,{
			xtype: 'hidden',
			id: 'index',
			name: 'viewBean.index'
		}		
		/*
		,{
			xtype: 'textfield',
			fieldLabel: LANGUAGE_FLAG,
			allowBlank: true,
			id: 'flag',
			name: 'viewBean.flag'
		}
		*/
	];

	//-----------------------------------------------------------------------------
	// Search fields
	//-----------------------------------------------------------------------------
	var nameSearch = {
		xtype: 'textfield',
		fieldLabel: LANGUAGE_NAME,
		id: 'nameSearch',
		name: 'viewBean.name'
	};
	
	//-----------------------------------------------------------------------------
	// Languages CRUD
	//-----------------------------------------------------------------------------
	var languageGridCRUD = new HurryApp.GridCRUD({
		type: 'language',
		region: 'center',
		width: '30%',
		autoHeight: false,

		columns: columns,
		autoExpandColumn: 'name',
	
		urlLoad: template ? myAppContext+'/language/loadTemplates' : myAppContext+'/language/load',
		urlSave: myAppContext+'/language/save',
		urlEdit: myAppContext+'/language/edit',
		urlDelete: myAppContext+'/language/delete',
		defaultParams: {'viewBean.projectId': template ? null : window.projectId},

		title:               LANGUAGE_LISTE,
		formWindowTitle:     LANGUAGE_TITRE,
		labelConfirmButton:  COMMON_OK,
		labelCancelButton:   COMMON_CANCEL,
		labelAddButton:      COMMON_ADD,
		tooltipAddButton:    LANGUAGE_TOOLTIP_ADD,
		labelDeleteButton:   COMMON_DELETE,
		tooltipDeleteButton: LANGUAGE_TOOLTIP_DELETE,
		labelDeleteQuestion: LANGUAGE_MESSAGE_DELETE_QUESTION,
		labelDeleteAdvert:   LANGUAGE_MESSAGE_DELETE_ADVERT,
		
		beforeSubmit: function() {
			if (this.getForm().findField('id').getValue() == '') {
				this.getForm().setValues({
					'viewBean.index': languageGridCRUD.store.getCount()
				});
			}
		},
		menuButtons: template ? null : [
			{
				id: 'upButton-language',
				text: LANGUAGE_BUTTON_UP,
				tooltip: LANGUAGE_BUTTON_UP_TOOLTIP,
				iconCls: 'tool-up',
				handler: function() {
					var selectedRecord = languageGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = languageGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex > 0) {
							languageGridCRUD.store.remove(selectedRecord);
							languageGridCRUD.store.insert(selectedRecordIndex-1, selectedRecord);
							languageGridCRUD.getSelectionModel().selectRow(selectedRecordIndex-1);
							for (var i=0; i<languageGridCRUD.store.data.items.length; i++) {
								languageGridCRUD.store.data.items[i].data.index = i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/language/updateModified', {'selectedObjects' : languageGridCRUD.getObjects()});
						}
					}
				}
			}		
			,{
				id: 'downButton-language',
				text: LANGUAGE_BUTTON_DOWN,
				tooltip: LANGUAGE_BUTTON_DOWN_TOOLTIP,
				iconCls: 'tool-down',
				handler: function() {
					var selectedRecord = languageGridCRUD.getSelectionModel().getSelected();
					if (selectedRecord) {
						var selectedRecordIndex = languageGridCRUD.store.indexOf(selectedRecord);
						if (selectedRecordIndex < languageGridCRUD.store.getCount()-1) {
							languageGridCRUD.store.remove(selectedRecord);
							languageGridCRUD.store.insert(selectedRecordIndex+1, selectedRecord);
							languageGridCRUD.getSelectionModel().selectRow(selectedRecordIndex+1);
							for (var i=0; i<languageGridCRUD.store.data.items.length; i++) {
								languageGridCRUD.store.data.items[i].data.index = i;
							};
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/language/updateModified', {'selectedObjects' : languageGridCRUD.getObjects()});
						}
					}
				}
			}
		],
		
		createFields: fields,
		//searchFields: [nameSearch],
		
		formWindowWidth:    380,
		formWindowMinWidth: 380,
		formLabelWidth:     80,
		
		displayPagingBar: false
	});

	return {
		getComponent: function() {
			return languageGridCRUD;
		}
	}
}

