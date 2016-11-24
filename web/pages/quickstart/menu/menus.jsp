<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/menu/modules/menuCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/menu/modules/groupeSelect.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD menu
			var menuCrud = new MenuCrud();

			// Listes des groupes associés au menu sélectionné
			var groupeSelect = new GroupeSelect();
			
			// Main panel
			mainPanel.add(menuCrud.getComponent());
			mainPanel.add(groupeSelect.getComponent());

			// Raffraichissement de la liste des groupes associées au menu sélectionné
			menuCrud.getComponent().linkGridSelect(groupeSelect.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
