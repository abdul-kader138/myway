<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%
	String contextPath = request.getContextPath();
%>

<html>
<head>
 	<script type="text/javascript" src="<%=contextPath%>/ext-3.1/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/ext-3.1/ext-all-debug.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/common.js"></script>
	<script type="text/javascript" src="jwplayer.js"></script>
	
	<script type="text/javascript">
		var myAppContext = '<%=contextPath%>';
	</script>
</head>

<body>
	<div id="mediaplayer"></div> 
	
	<script type="text/javascript" src="jwplayer.js"></script> 
	<script type="text/javascript"> 
		jwplayer("mediaplayer").setup({
			flashplayer: "player.swf",
			file: HurryApp.Utils.getUrlParams().file,
			image: "preview.jpg",
			width:"780",
			height:"542"
		});
	</script> 
</body>
</html>
