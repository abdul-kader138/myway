<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/ressource/modules/ressourceCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/ressource/modules/groupeSelect.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD ressources
			var ressourceCrud = new RessourceCrud();

			// Listes des ressources associées au groupe sélectionné
			var groupeSelect = new GroupeSelect();
			
			// Main panel
			mainPanel.add(ressourceCrud.getComponent());
			mainPanel.add(groupeSelect.getComponent());

			// Raffraichissement de la liste des groupes associées à la ressource sélectionnée
			ressourceCrud.getComponent().linkGridSelect(groupeSelect.getComponent());

			// Main panel
			mainPanel.add(ressourceCrud.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
