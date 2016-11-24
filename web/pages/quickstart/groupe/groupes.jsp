<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/groupe/modules/groupeCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/groupe/modules/ressourceSelect.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD groupes
			var groupeCrud = new GroupeCrud();

			// Listes des ressources associées au groupe sélectionné
			var ressourceSelect = new RessourceSelect();
			
			// Main panel
			mainPanel.add(groupeCrud.getComponent());
			mainPanel.add(ressourceSelect.getComponent());

			// Raffraichissement de la liste des ressources associées au groupe sélectionné
			groupeCrud.getComponent().linkGridSelect(ressourceSelect.getComponent());
		});
		</script>
	</tiles:putAttribute>

	<!--
	<tiles:putAttribute name="corpsPage" type="string">
		<table>
			<tr>
				<td><div id="grid-crud-groupe-div"></div></td>
				<td><div id="grid-select-ressource-div"></div></td>
			</tr>
		</table>
	</tiles:putAttribute>
	-->
</tiles:insertDefinition>
