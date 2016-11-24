<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/company/modules/companyCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/company/modules/companyPanel.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/company/modules/utilisateurCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){

			// CRUD utilisateurs
			var utilisateurCrud = new UtilisateurCrud();

			// Main panel
			mainPanel.add(utilisateurCrud.getComponent());

			if (window.userGroupId == GROUPE_SUPER_ADMIN) {
				// CRUD companies
				var companyCrud = new CompanyCrud();
				mainPanel.add(companyCrud.getComponent());

				// Refresh users linked to the selected company
				companyCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
					utilisateurCrud.getComponent().clearSearchFields();
					utilisateurCrud.getComponent().setDefaultParams({'viewBean.companyId': grid.store.getAt(rowIndex).data.id});
					utilisateurCrud.getComponent().loadDataStore();
					Ext.getCmp('addButton-utilisateur').enable();
					Ext.getCmp('deleteButton-utilisateur').enable();
				});

				utilisateurCrud.getComponent().subscribe('company.load', function(store, record, options) {
					if (companyCrud.getComponent().getSelectionModel().getCount() < 1) {
						utilisateurCrud.getComponent().store.removeAll();
						Ext.getCmp('addButton-utilisateur').disable();
						Ext.getCmp('deleteButton-utilisateur').disable();
					}
				});
			}
			else {
				// CRUD companies
				var companyCrud = new CompanyPanel();
				mainPanel.add(companyCrud.getComponent());
			}
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
