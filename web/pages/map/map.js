//----------------
// Map editor
//----------------
var publishMode;
var publishWindow;
var publishProgressWindow;
var nextUrl;
var mapSaved = true;
var hasPageChanged = false;
var save;

// Project publication popup
var displayPublishWindow = function() {
	publishWindow = new Ext.Window({
		id: 'publishWindow',
		title: COMMON_PUBLISH,
		width: 315,
		height: 250,
		animTarget: this,
		border: false,
		modal: true,
		layout: 'vbox',
		items: [
			// Publish only button
			{
				xtype: 'button',
				text: PROJECT_PUBLISH_ONLY,
				width: 300,
				height: 80,
				handler: function() {
					if (window.userGroupId != GROUPE_SUPER_ADMIN && window.projectKey.indexOf('trial') == 0) {
						HurryApp.MessageUtils.showWarning(PROJECT_PUBLISH_DEMO);
					}
					else {
						publishMode = 'online';
						publishProject();
					}
				},
				style: 'padding-left: 5px;padding-top: 10px;',
				cls: 'publish-button',
				iconCls: '',
				scale: 'large',
				iconAlign: 'left'
			}
			// Publish and download button
			,{
				xtype: 'button',
				text: PROJECT_PUBLISH_DOWNLOAD,
				width: 300,
				height: 80,
				handler: function() {
					if (window.userGroupId != GROUPE_SUPER_ADMIN && window.projectKey.indexOf('trial') == 0) {
						HurryApp.MessageUtils.showWarning(PROJECT_PUBLISH_DEMO);
					}
					else {
						publishMode = 'archive';
						publishProject();
					}
				},
				style: 'padding-left: 5px;padding-top: 10px;',
				cls: 'publish-button',
				iconCls: '',
				scale: 'large',
				iconAlign: 'left'
			}
			// Publish and update button
			,{
				xtype: 'button',
				text: PROJECT_PUBLISH_UPDATE,
				width: 300,
				height: 80,
				handler: function() {
					if (window.userGroupId != GROUPE_SUPER_ADMIN && window.projectKey.indexOf('trial') == 0) {
						HurryApp.MessageUtils.showWarning(PROJECT_PUBLISH_DEMO);
					}
					else {
						publishMode = 'forceUpdate';
						publishProject();
					}
				},
				style: 'padding-left: 5px;padding-top: 10px;',
				cls: 'publish-button',
				iconCls: '',
				scale: 'large',
				iconAlign: 'left'
			}
		]
	});
	publishWindow.show();
}

// Project publication
var publishProject = function() {
	// Close popup
	if (publishWindow) {
		publishWindow.close();
	}

	// Progress bar
	window.publishProgressWindow = new HurryApp.ProgressWindow();
	window.publishProgressWindow.show();

	var mapEditor = HurryApp.Utils.getMovie('mapEditor');
	if (mapEditor && mapEditor.saveMapConfig) {
		// Save editor
		mapEditor.saveMapConfig();
	}
	else {
		// Generate paths
		generatePaths();
	}
}

// Generate paths
var generatePaths = function() {
	var pathsGenerator = HurryApp.Utils.getMovie('pathsGenerator');
	var paths = '';
	if (pathsGenerator) {
		pathsGenerator.publishProject();
	}
}

// Callback after save (called by editor.swf) 
var mapConfigSavedHandler = function(result) {
	if (result == false) {
		HurryApp.MessageUtils.showWarning(MAP_ERREUR_SAVE);
	}
	else {
		window.mapSaved = true;

		// Generate paths
		generatePaths();
	}
}

// Callback after save and navigation (called by editor.swf) (after click on "yes" button of saveWindow) 
var mapConfigSavedOnTabChangeHandler = function() {
	if (window.nextUrl) {
		// Redirect to the clicked page 
		window.location = window.nextUrl;
	}
}

// Callback after save (without path generation) (called by editor.swf) 
var mapSavedHandler = function() {
	window.mapSaved = true;
}

// Callback after path generation (called by publisher.swf)
var pathsGeneratedHandler = function(content, errors) {
	// Close progress bar
	if (window.publishProgressWindow) {
		window.publishProgressWindow.close();
	}

	// Errors	
	if (errors.length > 0) {
		var errorMsg = '';
		for (var i = 0; i < errors.length; i++) {
			if (errorMsg != '') errorMsg += '<br/>';
			errorMsg += errors[i];
		}
		HurryApp.MessageUtils.showWarning(errorMsg);
	}

	// Publication
	HurryApp.Utils.sendAjaxRequest(
		myAppContext+'/map/publishProject',
		{
			project_id: window.projectId,
			publish_mode: publishMode,
			paths: content
		},
		function() {
			// Download archive
			if (publishMode == 'archive') {
				window.location = myAppContext + "/update/getProjectZip?key=" + window.projectKey;//'http://'+IP_SERVER+'/php/getProjectZip.php?key_id='+window.projectKey;
			}

			if (window.nextUrl) {
				// Redirect to clicked page 
				window.location = window.nextUrl;
			}
			
			// Refresh page (for reinit from publisher.swf)
			//window.location.reload();
		},
		-1
	);
}

// Callback which indicate that the map has been updated (called by editor.swf)
var mapModifiedHandler = function() {
	window.mapSaved = false;
}

// Callback which update the progress bar (called by publisher.swf)
var publishProgressHandler = function(value) {
	window.publishProgressWindow.updateProgress(value/100);
}

// Redirect to the clicked page
var goTo = function(url) {
	var mapEditor = HurryApp.Utils.getMovie('mapEditor');
	var projectEditor = document.getElementById("key");
	var settingsEditor = document.getElementById("clock");
	var licensesEditor = document.getElementById("keyBis");
	if (mapEditor && mapEditor.saveMapConfig && window.mapSaved != true) {
		displaySaveMapWindow(url)
	}else if(projectEditor && hasPageChanged){
		displaySaveProjectWindow(url);
	}else if(settingsEditor && hasPageChanged){
		displaySaveSettingsWindow(url);
	}else if(licensesEditor && hasPageChanged){
		displaySaveLicensesWindow(url);
	} else {
		window.location = url;
	}
}

// Check updates before publish
var checkSaveBeforePublish = function() {
	var projectEditor = document.getElementById("key");
	var settingsEditor = document.getElementById("clock");
	var settingsEditor = document.getElementById("clock");
	var licensesEditor = document.getElementById("keyBis");
	if(projectEditor && hasPageChanged){
		displaySaveProjectWindow('');
	}else if(settingsEditor && hasPageChanged){
		displaySaveSettingsWindow('');
	}else if(licensesEditor && hasPageChanged){
		displaySaveLicensesWindow('');
	}else{
		displayPublishWindow();
	}
}

// Display the save map window
var displaySaveMapWindow = function(url) {
	var saveWindow = new Ext.Window({
		id: 'saveWindow',
		title: MAP_MESSAGE_ONUNLOAD_SAVE,
		width: 300,
		animTarget: this,
		border: false,
		modal: true,
		cls: 'ha-plain-window',	
		bodyStyle: 'padding:5px;',
		html: MAP_MESSAGE_ONUNLOAD_SAVE_QUESTION,
		buttons: [
			{
				xtype: 'button',
				text: MAP_BOUTON_SAVE_YES,
				cls: 'ha-default-button',
				handler: function() {
					var mapEditor = HurryApp.Utils.getMovie('mapEditor');
					// Save editor
					mapEditor.saveMapConfig(false);
					saveWindow.close();
				}
			}
			,{
				xtype: 'button',
				text: MAP_BOUTON_SAVE_NO,
				handler: function() {
					window.location = url;
				}
			}
			,{
				xtype: 'button',
				text: MAP_BOUTON_SAVE_CANCEL,
				handler: function() {
					saveWindow.close();
				}
			}
		]
	});
	saveWindow.show();
}

// Display the save project window
var displaySaveProjectWindow = function(url) {
	var saveProjectWindow = new Ext.Window({
		id: 'saveProjectWindow',
		title: PROJECT_MESSAGE_ONUNLOAD_SAVE,
		width: 300,
		animTarget: this,
		border: false,
		modal: true,
		cls: 'ha-plain-window',	
		bodyStyle: 'padding:5px;',
		html: PROJECT_MESSAGE_ONUNLOAD_SAVE_QUESTION,
		buttons: [
			{
				xtype: 'button',
				text: PROJECT_BOUTON_SAVE_YES,
				cls: 'ha-default-button',
				handler: function() {
					// Save changes
					save();
					saveProjectWindow.close();
					if(url != "") window.location = url;
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: PROJECT_BOUTON_SAVE_NO,
				handler: function() {
					saveProjectWindow.close();
					if(url != "") window.location = url;
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: PROJECT_BOUTON_SAVE_CANCEL,
				handler: function() {
					saveProjectWindow.close();
				}
			}
		]
	});
	saveProjectWindow.show();
}

// Display the save settings window
var displaySaveSettingsWindow = function(url) {
	var saveSettingsWindow = new Ext.Window({
		id: 'saveSettingsWindow',
		title: SETTINGS_MESSAGE_ONUNLOAD_SAVE,
		width: 300,
		animTarget: this,
		border: false,
		modal: true,
		cls: 'ha-plain-window',	
		bodyStyle: 'padding:5px;',
		html: SETTINGS_MESSAGE_ONUNLOAD_SAVE_QUESTION,
		buttons: [
			{
				xtype: 'button',
				text: SETTINGS_BOUTON_SAVE_YES,
				cls: 'ha-default-button',
				handler: function() {
					// Save changes
					save();
					saveSettingsWindow.close();
					if(url != "") setTimeout(function() {window.location = url;}, 200); // FIX-ME: compatibilit� Safari
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: SETTINGS_BOUTON_SAVE_NO,
				handler: function() {
					saveSettingsWindow.close();
					if(url != "") window.location = url;
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: SETTINGS_BOUTON_SAVE_CANCEL,
				handler: function() {
					saveSettingsWindow.close();
				}
			}
		]
	});
	saveSettingsWindow.show();
}

// Display the save licenses window
var displaySaveLicensesWindow = function(url, callback) {
	var saveLicensesWindow = new Ext.Window({
		id: 'saveLicensesWindow',
		title: LICENSES_MESSAGE_ONUNLOAD_SAVE,
		width: 300,
		animTarget: this,
		border: false,
		modal: true,
		cls: 'ha-plain-window',	
		bodyStyle: 'padding:5px;',
		html: LICENSES_MESSAGE_ONUNLOAD_SAVE_QUESTION,
		buttons: [
			{
				xtype: 'button',
				text: LICENSES_BOUTON_SAVE_YES,
				cls: 'ha-default-button',
				handler: function() {
					// Save changes
					save();
					saveLicensesWindow.close();
					if(url != "") setTimeout(function() {window.location = url;}, 200); // FIX-ME: compatibilit� Safari
					else if(callback) callback();
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: LICENSES_BOUTON_SAVE_NO,
				handler: function() {
					saveLicensesWindow.close();
					if(url != "") window.location = url;
					else if(callback) callback();
					else displayPublishWindow();
				}
			}
			,{
				xtype: 'button',
				text: LICENSES_BOUTON_SAVE_CANCEL,
				handler: function() {
					saveLicensesWindow.close();
				}
			}
		]
	});
	saveLicensesWindow.show();
}
