<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/config/modules/configDomainTabPanel.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/quickstart/config/modules/configParameterCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD config parameters
			var configParameterCrud = new ConfigParameterCrud();

			// Tab panel config domains
			var configDomainTabPanel = new ConfigDomainTabPanel(configParameterCrud.getComponent());

			// Main panel
			mainPanel.add(configDomainTabPanel.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
