<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/iconGroup/modules/iconGroupCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/itemType/modules/iconCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD iconGroups
			var iconGroupCrud = new IconGroupCrud();

			// CRUD icons
			var iconCrud = new IconCrud('/project-exports/'+window.projectKey);

			// Main panel
			mainPanel.add(iconGroupCrud.getComponent());
			mainPanel.add(iconCrud.getComponent());

			// Refresh the icons linked to the selected icon group
			iconGroupCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				iconCrud.getComponent().setDefaultParams({'viewBean.iconGroupId': grid.store.getAt(rowIndex).data.id});
				if(grid.store.getAt(rowIndex).data.type == '1') {
					iconCrud.setCustomFields();
				}
				else {
					iconCrud.restoreFields();
				}
				iconCrud.loadIconGallery();
				iconCrud.getComponent().getTopToolbar().enable();
			});
			iconCrud.getComponent().subscribe('iconGroup.load', function(store, record, options) {
				if (iconGroupCrud.getComponent().getSelectionModel().getCount() < 1) {
					iconCrud.clearIconGallery();
					iconCrud.getComponent().getTopToolbar().disable();
				}
			});
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
