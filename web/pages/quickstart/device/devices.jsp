<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/device/modules/deviceCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD ressources
			var deviceCrud = new DeviceCrud();
            mainPanel.add(deviceCrud.getComponent());
			// Main panel
//            var btn = new Ext.Button({
//                text:'hi device'
//            });
//            mainPanel.add(btn);
           
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
