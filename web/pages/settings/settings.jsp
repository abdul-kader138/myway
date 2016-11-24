<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/custom-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/settings/modules/settingsPanel.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// Panel settings
			var settingsPanel = new SettingsPanel();

			// Main panel
			mainPanel.add(settingsPanel.getComponent());
			
			// Check modifications
			save = settingsPanel.getSaveFunc();
			document.getElementById("publishButton").setAttribute("onclick","checkSaveBeforePublish();");
			
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
