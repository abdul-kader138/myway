<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript">
		// Map editor
		swfobject.embedSWF(
			'../flash-movies/editor.swf',
			'mapEditor', '100%', '100%', '10.0.0',
			'../flash-movies/editor.swf',
			// flashVars
			{
				project_id: window.projectId,
				init_url: myAppContext+'/map/initEditor',
				save_url: myAppContext+'/map/saveEditor'
			},
			// params
			{
				menu: 'false',
				scale: 'noScale',
				allowFullscreen: 'true',
				allowScriptAccess: 'always',
				bgcolor: '#f0f0f0',
				wmode: 'opaque',
				allowFullScreenInteractive: 'true' 
			},
			// attributes
			{
				id: 'mapEditor'
			}
		);

		// Vérifie si la map est sauvegardée lors d'un refresh, d'un changement de page ou de la fermeture du navigateur
		/*
		window.ssonbeforeunload = function(event) {
			if (window.mapSaved != true) {
				if (confirm(MAP_MESSAGE_ONUNLOAD_SAVE_QUESTION)) {
					var mapEditor = HurryApp.Utils.getMovie('mapEditor');
					if (mapEditor) {
						// Sauvegarde de l'éditeur
						mapEditor.saveMapConfig();
					}
				}
				window.mapSaved = true;
			}
			return true;
		}
		*/
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body" type="string">
		<div id="mapEditor">
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
