<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/language/modules/languageCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/language/modules/wordingCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			/*
			// Wording parameters
			var wordingCrud = new WordingCrud(true);

			// Tab panel config domains
			var languageTabPanel = new LanguageTabPanel(wordingCrud.getComponent());

			// Main panel
			mainPanel.add(languageTabPanel.getComponent());
			*/
			// CRUD languages
			var languageCrud = new LanguageCrud(true);

			// CRUD wordings
			var wordingCrud = new WordingCrud();

			// Main panel
			mainPanel.add(languageCrud.getComponent());
			mainPanel.add(wordingCrud.getComponent());

			// Refresh the wordings linked to the selected language
			languageCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				wordingCrud.getComponent().clearSearchFields();
				wordingCrud.getComponent().setDefaultParams({'viewBean.languageId': grid.store.getAt(rowIndex).data.id});
				wordingCrud.getComponent().loadDataStore();
				if (window.userGroupId == GROUPE_SUPER_ADMIN) {
					Ext.getCmp('addButton-wording').enable();
					Ext.getCmp('deleteButton-wording').enable();
				}
			});

			wordingCrud.getComponent().subscribe('language.load', function(store, record, options) {
				if (languageCrud.getComponent().getSelectionModel().getCount() < 1) {
					wordingCrud.getComponent().store.removeAll();
					if (window.userGroupId == GROUPE_SUPER_ADMIN) {
						Ext.getCmp('addButton-wording').disable();
						Ext.getCmp('deleteButton-wording').disable();
					}
				}
			});

			// Reset button:  reset the wordings of all projects
			var resetButton = {
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
						text: LANGUAGE_BUTTON_RESET,
						scale: 'large',
						iconCls: '',
						width: 150,
						x: '42%',
						handler: function() {
							Ext.MessageBox.confirm(COMMON_MESSAGE_CONFIRM, LANGUAGE_MESSAGE_RESET, 
								function(btn) {
									if(btn == 'yes') {
										HurryApp.Utils.sendAjaxRequest(myAppContext+'/language/resetProjectsLaguages', {}, function(jsonResponse) {});
									}
								}
							);	
						}
					})
				]
			}; 
			mainPanel.add(resetButton);
			
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
