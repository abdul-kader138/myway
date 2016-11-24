<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/itemType/modules/itemTypeCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/itemType/modules/iconCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD itemTypes
			var itemTypeCrud = new ItemTypeCrud();

			// CRUD icons
			var iconCrud = new IconCrud('');

			// Main panel
			mainPanel.add(itemTypeCrud.getComponent());
			mainPanel.add(iconCrud.getComponent());

			// Refresh the icons linked to the selected item type
			itemTypeCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				iconCrud.getComponent().setDefaultParams({'viewBean.itemTypeId': grid.store.getAt(rowIndex).data.id});
				iconCrud.loadIconGallery();
				iconCrud.getComponent().getTopToolbar().enable();
			});
			iconCrud.getComponent().subscribe('itemType.load', function(store, record, options) {
				if (itemTypeCrud.getComponent().getSelectionModel().getCount() < 1) {
					iconCrud.clearIconGallery();
					iconCrud.getComponent().getTopToolbar().disable();
				}
			});
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
