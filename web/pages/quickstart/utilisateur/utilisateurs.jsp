<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/utilisateur/modules/utilisateurCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/utilisateur/modules/groupeSelect.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD utilisateurs
			var utilisateurCrud = new UtilisateurCrud();

			// Listes des groupes associées à l'utilisateur sélectionné
			var groupeSelect = new GroupeSelect();

			// Main panel
			mainPanel.add(utilisateurCrud.getComponent());
			mainPanel.add(groupeSelect.getComponent());

			// Raffraichissement de la liste des groupes associées à l'utilisateur sélectionné
			utilisateurCrud.getComponent().linkGridSelect(groupeSelect.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
