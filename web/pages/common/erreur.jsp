<%@ page language="java" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="body" type="string">
		<div align="center">
			<div class="error-message" title="<s:property value="#request.stackTrace" escape="false"/>">
				<s:text name="erreur.unexpected"/>
			</div>
			<br/><br/>
			<div>
				<a href="javascript:history.back();"><s:text name="erreur.retour" /></a>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
