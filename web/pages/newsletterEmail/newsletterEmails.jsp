<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/newsletterEmail/modules/newsletterEmailCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/newsletterEmail/modules/newsletterEmailPanel.js"></script>
		<script type="text/javascript">
		Ext.onReady(function(){
			// CRUD newsletterEmails
			var newsletterEmailCrud = new NewsletterEmailCrud();
			var newsletterEmailPanel = new NewsletterEmailPanel();
			// Main panel
			mainPanel.add(newsletterEmailCrud.getComponent());
			mainPanel.add(newsletterEmailPanel.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
