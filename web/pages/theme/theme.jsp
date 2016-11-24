<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
    <tiles:putAttribute name="header" type="string">
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/custom-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/theme/modules/themePanel.js"></script>
		
        <script type="text/javascript">
        Ext.onReady(function(){
            //var msgDiv = document.getElementById("login-form-div");
            //msgDiv.innerHTML = "<span style='font-size:35px;color:black'>Comming soon!</span>";
            
        	// Panel settings
			var themePanel = new ThemePanel();

			// Main panel
			mainPanel.add(themePanel.getComponent());
			
			// Check modifications
			save = themePanel.getSaveFunc();
			//document.getElementById("publishButton").setAttribute("onclick","checkSaveBeforePublish();");
        })
        </script>
    </tiles:putAttribute>
    <!--<tiles:putAttribute name="body" type="string">
        <div id="login-form-div" align="center"></div>
    </tiles:putAttribute>-->
</tiles:insertDefinition>
