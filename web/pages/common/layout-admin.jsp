<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ page import="org.hurryapp.quickstart.Constants"%>
<%@ page import="org.hurryapp.quickstart.data.model.Utilisateur"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%
	String contextPath = request.getContextPath();
	String logoUrl = (String) session.getAttribute("logoUrl");
	Utilisateur user = (Utilisateur) session.getAttribute(Constants.SESSION_KEY_USER);
%>

<html>
<head>
	<title><s:text name="application" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/ext-3.1/resources/css/ext-all-notheme.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/ext-3.1/resources/css/xtheme-gray-green.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/ext-ux/Statusbar-3.1/css/statusbar.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/ext-ux/LockingGridPanel/columnLock.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/ext-ux/Fileuploadfield/fileuploadfield.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/ext-ux/ColorField/colorfield.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/js/ext-ux/IconCombo/iconcombo.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/styles/global-gray.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/styles/pad-panel.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/styles/image-gallery.css"/>

 	<script type="text/javascript" src="<%=contextPath%>/ext-3.1/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/ext-3.1/ext-all-debug.js"></script>
	<!--<script type="text/javascript" src="<%=contextPath%>/ext-3.1/src/locale/ext-lang-fr.js"></script>-->
	<script type="text/javascript" src="<%=contextPath%>/js/global-messages_<s:property value='locale'/>.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/TabPanel.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/Broadcast.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/Statusbar-3.1/StatusBar.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/Statusbar-3.1/ValidationStatus.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/LockingGridPanel/columnLock.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/ColorCombo.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/RadioGroup-patch.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/TreeCombo.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/SumGroupingView.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/Fileuploadfield/FileUploadField.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/ColorField/ColorField.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/ext-ux/IconCombo/InconCombo.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/yui-container-core.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/flash-charts/amline/amline/swfobject.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/lang-fr.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/config.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/common.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/myWay.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/imageLoader.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/amChartLoader.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/component-messages_<s:property value='locale'/>.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/ddgrid.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/formPanel.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/formWindow.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/progressWindow.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/CRUD.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/treeCRUD.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/gridCRUD.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/gridSelect.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/gridSelectField.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/tabPanelCRUD.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/triggerFieldGrid.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/carousel.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/padPanel.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/components/imageGallery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/map/map.js"></script>

	<script type="text/javascript">
		<%=request.getAttribute("jsVariables")%>
	</script>

	<script type="text/javascript">
		var myAppContext = '<%=contextPath%>';
		var logoUrl = '<%=logoUrl%>';

		<% if (user != null) { %>
		window.userId = '<%=user.getId()%>';
		window.userGroupId = '<%=user.getGroupe().getId()%>';
		window.userCompanyId = <%=user.getCompany().getId()%>;
		window.projectId = <%=session.getAttribute(com.sotouch.myway.Constants.SESSION_KEY_PROJECT_ID)%>;
		<% }%>

		var mainPanel;
		
		Ext.onReady(function(){
			Ext.QuickTips.init();

			mainPanel = new Ext.Panel({
				layout:'border',
				width:DEFAULT_GRID_WIDTH,
				height: DEFAULT_GRID_HEIGHT,
				border: false
			});
		});
	</script>

	<tiles:insertAttribute name="header" ignore="true" />
	
	<script type="text/javascript">
		function selectMenu() {
			//alert('<%=""+request.getServletPath()%>');
			var path = '<%=""+request.getServletPath()%>'; // commence avec /pages/ --> 7 caracteres
			path = path.substring(7);
			path = path.substring(0, path.indexOf('/'));
			var selectedMenu = Ext.get('menu-'+path);
			if (selectedMenu) {
				selectedMenu.addClass('ha-selected');
			}
		}
		
		Ext.onReady(function(){
			if (mainPanel.findBy(function(){return true;}).length > 0) {
				mainPanel.render('main-panel');
			}

			// Select menu
			setTimeout("selectMenu()", 500);
		});
	</script>
</head>

<body onmousemove="">
	<table class="ha-app" align="center" border="0" cellpadding="0" cellspacing="0" width="1200px">
		<!-- TOP -->
		<tr>
			<td colspan="3" class="ha-top">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="ha-top-logo" onclick="window.location=myAppContext+'/project/init'"><img src="<%=logoUrl%>" width="157" height="91"/></td>
						<td class="ha-top-right" valign="top">
							<% if (session.getAttribute(Constants.SESSION_KEY_USER) != null) { %>
							<div class="ha-top-menu">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="ha-left"></td>
										<td class="ha-center" valign="top">
											<!-- TILES menu -->
											<tiles:insertAttribute name="menu" />
										</td>
										<!-- Welcome/logout bar -->
										<td class="ha-welcome-bar" nowrap="nowrap">
											<div style="float: left;"><s:text name="common.welcome"/> <%=user.getFullNameTruncated(25)%></div>
											<div class="ha-top-logout" onclick="goTo('${pageContext.request.contextPath}/login/logout');" title="<s:text name="common.logout"/>" style="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
										</td>
										<td class="ha-right">
										</td>
									</tr>
								</table>
							</div>
							<% } %>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<!-- CENTER -->
		<tr>
			<td class="ha-center-tl"></td>
			<td class="ha-center-t"></td>
			<td class="ha-center-tr"></td>
		</tr>
		<tr>
			<td class="ha-center-l"></td>
			<td class="ha-center" align="center" width="1180px">
				<div id="main-panel">
				</div>
				<!-- TILES center -->
				<tiles:insertAttribute name="body" />
			</td>
			<td class="ha-center-r"></td>
		</tr>
		<tr>
			<td class="ha-center-bl"></td>
			<td class="ha-center-b"></td>
			<td class="ha-center-br"></td>
		</tr>
	</table>
</body>
</html>
