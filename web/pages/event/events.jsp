<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/translate-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/event/modules/eventCrud.js"></script>

		<script type="text/javascript">
		
		var eventCheckFields = function (id) {
			var empty = true;
			
			if(Ext.getCmp('eventContents['+id+'].name').getValue() != '') empty = false;
			if(Ext.getCmp('eventContents['+id+'].description').getValue() != '') empty = false;
			if(Ext.getCmp('eventContents['+id+'].keywords').getValue() != '') empty = false;
			
			if(empty) Ext.getCmp('button-translate['+id+']').disable(); 
			else Ext.getCmp('button-translate['+id+']').enable();
		}
		
		function translateTooltip(id) {
			return (new Ext.ToolTip({
				cls: 'translate-tooltip',
				target: id,
				html: EVENT_TRANSLATE_TOOLTIP,
				anchor: "left",
				anchorOffset: 18,
				autoHide: true,
				autoShow: true,
				dismissDelay: 0
			}));
		}
		
		Ext.onReady(function(){
			// CRUD events
			var eventCrud = new EventCrud();

			// Main panel
			mainPanel.add(eventCrud.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
