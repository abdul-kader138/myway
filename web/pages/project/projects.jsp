<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-admin">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/renew-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/projectCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/userSelect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/project/modules/projectPreview.js"></script>

		<script type="text/javascript">
		
		// This script sets OSName variable as follows:
		// "Windows"    for all versions of Windows
		// "MacOS"      for all versions of Macintosh OS
		// "Linux"      for all versions of Linux
		// "UNIX"       for all other UNIX flavors 
		// "Unknown OS" indicates failure to detect the OS
		var OSName="Unknown OS";
		if (navigator.appVersion.indexOf("Win")!=-1) OSName="Windows";
		if (navigator.appVersion.indexOf("Mac")!=-1) OSName="MacOS";
		if (navigator.appVersion.indexOf("X11")!=-1) OSName="UNIX";
		if (navigator.appVersion.indexOf("Linux")!=-1) OSName="Linux";
		
		function copyProjectKey() {
			if (Ext.isIE) {
				var range = document.body.createTextRange();
	 	        //range.moveToElementText(document.getElementById('key'));
				//range.select();
				fnSelect('key');
				//range.execCommand('copy');
				document.execCommand('copy');
				changeCopyText('Key copied into clipboard...', '#000000', 5000);
			}
		}
		
		function selectProjectKey() {
			fnSelect("key");
			if (!Ext.isIE) changeCopyText('Ctrl+C to copy.', '#000000', 5000);
			if (OSName == 'MacOS') changeCopyText('CMD+C to copy.', '#000000', 5000);
		}
		
		function changeCopyText(text, color, duration) {
			tmpdisplay = document.getElementById('copyKeyLink').style.display;
			tmpcol = document.getElementById('copyKeyLink').style.color;
			tmpval = document.getElementById('copyKeyLink').innerHTML;
			document.getElementById('copyKeyLink').style.display = "inline";
			document.getElementById('copyKeyLink').style.color = color;
			document.getElementById('copyKeyLink').innerHTML = text;
			window.setTimeout(function() {
				document.getElementById('copyKeyLink').style.display = tmpdisplay;
				document.getElementById('copyKeyLink').style.color = tmpcol;
				document.getElementById('copyKeyLink').innerHTML = tmpval;
		    }, duration);
		}
		
		function fnSelect(objId) {
			fnDeSelect();
			setTimeout( function () {document.getElementById(objId).select();}, 10);
			//if (document.selection) {
			//var range = document.body.createTextRange();
	 	    //    range.moveToElementText(document.getElementById(objId));
			//range.select();
			//}
			//else if (window.getSelection) {
			//var range = document.createRange();
			//range.selectNode(document.getElementById(objId));
			//window.getSelection().addRange(range);
			//}
		}
			
		function fnDeSelect() {
			if (document.selection) document.selection.empty(); 
			else if (window.getSelection)
	                window.getSelection().removeAllRanges();
		}
		
		function getDate(strDate){	
			// Format yyyy-mm-dd
		    year = strDate.substring(0,4);
			month = strDate.substring(5,7);
			day = strDate.substring(8,10);
			d = new Date();
			d.setDate(day);
			d.setMonth(month-1);
			d.setFullYear(year); 
			return d;  
		}
		   
		function compareDates(date1, date2){
			//Retourne:
			//   0 si date_1 = date_2
		  	//   1 si date_1 > date_2
			//  -1 si date_1 < date_2	 
		    diff = date1.getTime() - date2.getTime();
		    return (diff==0?diff:diff/Math.abs(diff));
		}
		
		function daysInterval(date1, date2) {
		    var ONE_DAY = 1000 * 60 * 60 * 24

		    var date1_ms = date1.getTime()
		    var date2_ms = date2.getTime()

		    var difference_ms = Math.abs(date1_ms - date2_ms)
		    return Math.round(difference_ms/ONE_DAY)
		}
		
		var rowDate = null;
		function renewTooltip(id, text) {
			var date = new Date();
			return (new Ext.ToolTip({
				cls: 'renew-tooltip',
				target: id,
				html: text,
				anchor: "left",
				anchorOffset: 18,
				autoHide: false,
				autoShow: false,
				listeners: {
				  show: function(tt) {
					(daysInterval(date,rowDate) == 0) ? remainingDays = 'today' : remainingDays = 'in ' + daysInterval(date,rowDate) + ' days'
				    tt.body.update(text.replace('[X]',remainingDays));
				  }
				}
			}));
		}
		
		Ext.onReady(function(){
			// CRUD projects
			var projectCrud = new ProjectCrud();

			// Users select
			var userSelect = new UserSelect();
			
			// Project preview
			var projectPreview = new ProjectPreview();
			var load = projectPreview.getLoadFunc();

			// Main panel
			mainPanel.add(projectCrud.getComponent());
			//if (window.userGroupId != GROUPE_USER) {
				var rightPanel = new Ext.Panel({
					region: 'east',
					split: true,
					border: false,
					width: '50%',
					layout: 'border',
					style: 'padding: 0;',
					items: [
						projectPreview.getComponent(),
						userSelect.getComponent()
					]
				});
				mainPanel.add(rightPanel);
			//}
			
			// Disable default preview
			Ext.getCmp('project-preview-toolbar').items.itemAt(0).hide();
			//Ext.getCmp('project-preview-toolbar').items.itemAt(1).hide();
			projectPreview.getComponent().disable();
			projectPreview.hide();

			// Instantiate tooltips
			var rtipr = renewTooltip('renewProjectTitle', PROJECT_REQUIRE_RENEW);
			var rtips = renewTooltip('renewProjectTitle', PROJECT_SUGGEST_RENEW);
			
			document.onclick = function(){rtipr.hide(); rtips.hide();}; // On fait disparaitre une tool tip en cliquant n'importe où sur l'ecran
			
			// Refresh the usres and the licenses linked to the selected project
			projectCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				Ext.getCmp('project-preview-toolbar').items.itemAt(0).show();
				//Ext.getCmp('project-preview-toolbar').items.itemAt(1).show();
				
				setTimeout( function () {
					if(Ext.isIE){
						document.getElementById('copyKeyLink').innerHTML = PROJECT_KEY_COPY_LABEL;
						document.getElementById('copyKeyLink').style.textDecoration = 'underline';
					}
				}, 20);
				
				projectPreview.getComponent().disable();
				
				var projectRow = grid.store.getAt(rowIndex);
				
				// Preview
				load(projectRow.data.id);
				projectPreview.getForm().items.itemAt(0).items.itemAt(1).items.itemAt(0).items.itemAt(0).setText(projectRow.data.name);
				
				date = new Date();
				rowDate = getDate(projectRow.data.dateExpire);
				var timeout;
				
				if(compareDates(rowDate, date) < 0) { // if the project is expired
					Ext.getCmp('project-preview-toolbar').items.itemAt(0).disable();
					rtips.hide();
					rtips.disable();
					rtipr = renewTooltip('renewProjectTitle', PROJECT_REQUIRE_RENEW);
					rtipr.enable();
					clearTimeout(timeout);
					timeout = setTimeout(function () { rtipr.show(); rtips.hide(); rtips.disable();}, 2000);		
				}
				else { // else...
					
					Ext.getCmp('project-preview-toolbar').items.itemAt(0).enable();
					date.setMonth(date.getMonth()+1);
					if(compareDates(rowDate, date) < 0){ // if the project expires soon
						
						date.setMonth(date.getMonth()-1);
						rtips.disable();
						rtipr.hide();
						rtipr.disable();
						rtips.enable();
						clearTimeout(timeout);
						timeout = setTimeout(function () { rtips.show(); rtipr.hide(); rtipr.disable(); setTimeout(function () { rtips.hide(); }, 10000);}, 2000);	
					}else{ // else...
							clearTimeout(timeout);
							rtipr.hide();
							rtipr.disable();
							rtips.hide();
							rtips.disable();
					}
				}
				// Users
				userSelect.getComponent().parentId = projectRow.data.id;
				userSelect.getComponent().setDefaultParams({'viewBean.parentId': projectRow.data.id, 'projectId': projectRow.data.id});
				userSelect.getComponent().setDefaultParamsGridWindow({'viewBean.companyId': projectRow.data.companyId, 'projectId': projectRow.data.id});
				userSelect.getComponent().loadDataStore();
				Ext.getCmp('addButton-utilisateur').enable();
				Ext.getCmp('deleteButton-utilisateur').enable();
				projectPreview.getComponent().enable();
				projectPreview.show();
				// Retreive the project key
				window.projectKey = projectRow.data.key;
			});
			userSelect.getComponent().subscribe('project.load', function(store, record, options) {
				if (projectCrud.getComponent().getSelectionModel().getCount() < 1) {
					// Users
					userSelect.getComponent().store.removeAll();
					Ext.getCmp('addButton-utilisateur').disable();
					Ext.getCmp('deleteButton-utilisateur').disable();
				}
			});
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
