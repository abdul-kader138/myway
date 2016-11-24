<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-login">
    <tiles:putAttribute name="header" type="string">
        <script type="text/javascript">
        Ext.onReady(function(){
            var msgDiv = document.getElementById("login-form-div");
            msgDiv.innerHTML = "<span style='font-size:35px;color:white'><s:property escape='false' value='uniqueKey'/></span>";
        })
        </script>
    </tiles:putAttribute>

    <tiles:putAttribute name="body" type="string">
        <div id="login-form-div" align="center"></div>
    </tiles:putAttribute>
</tiles:insertDefinition>
