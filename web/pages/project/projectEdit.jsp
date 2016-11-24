<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/translate-tooltip.css"/>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/dataSourceCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/settingsPanel.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/projectPanel.js"></script>

		<script type="text/javascript">
		
		/*
		function translateTooltip(id) {
			return (new Ext.ToolTip({
				cls: 'translate-tooltip',
				target: id,
				html: PROJECT_TRANSLATE_TOOLTIP,
				anchor: "left",
				anchorOffset: 18,
				width: 390,
				autoHide: true,
				autoShow: true,
				dismissDelay: 0
			}));
		}
		*/
		
		Ext.onReady(function(){
			// Panel project
			var projectPanel = new ProjectPanel();
			
			// Main panel
			mainPanel.add(projectPanel.getComponent());
			
			// Check modifications
			save = projectPanel.getSaveFunc();
			document.getElementById("publishButton").setAttribute("onclick","checkSaveBeforePublish();");
		});	
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
