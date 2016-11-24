<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<%@ page import="com.sotouch.myway.Constants"%>
<%@ page import="org.hurryapp.quickstart.service.config.ConfigParameterService"%>

<% String buyLicenseUrl = (String) session.getAttribute("buyLicenseUrl"); %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/license/modules/licenseCrud.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/pages/license/modules/licenseForm.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/pages/license/modules/deviceSelect.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/pages/license/modules/softwareUpdateCrud.js"></script>

		<script type="text/javascript">
		
		function translateTooltip(id) {
			return (new Ext.ToolTip({
				cls: 'translate-tooltip',
				target: id,
				html: CATEGORY_TRANSLATE_TOOLTIP,
				anchor: "left",
				anchorOffset: 18,
				autoHide: true,
				autoShow: true,
				dismissDelay: 0
			}));
		}
		
		Ext.onReady(function(){
			
			// Url buy license
			buyLicenseUrl = '<%= buyLicenseUrl %>';
			
			// Crud licenses
			var licenseCrud = new LicenseCrud();
			
			// Panel licenses
            var licenseForm = new LicenseForm();

            var deviceSelect = new DeviceSelect();
			
			var selectedGrid = null;
			
			var softwareUpdateCrud = new SoftwareUpdateCrud();
			
			// Callback après sauvegarde
			var refresh = function(){
				if(selectedGrid) selectedGrid.refresh(Ext.getCmp('parentId').getValue());
			}
			licenseForm.setCallbackFunc(refresh);

			// Main panel
			mainPanel.add(licenseCrud.getComponent());
			if (window.userGroupId == GROUPE_SUPER_ADMIN || window.userGroupId == GROUPE_ADMIN) {
				mainPanel.add(softwareUpdateCrud.getComponent());
			}
			
            //mainPanel.add(licenseForm.getComponent());

/*            var rightPanel = new Ext.Panel({
				region: 'east',
				split: true,
				border: false,
				width: '50%',
				layout: 'border',
				style: 'padding: 0;',
				items: [
					licenseForm.getComponent(),
					deviceSelect.getComponent()
				]
            });
            mainPanel.add(rightPanel);
            licenseCrud.getComponent().linkGridSelect(deviceSelect.getComponent());*/

			//Ext.getCmp('addButton-license').enable();
			//Ext.getCmp('deleteButton-license').enable();
			if (window.userGroupId != GROUPE_SUPER_ADMIN){
				//load();
				Ext.getCmp('addButton-license').setHandler(function () { window.open(buyLicenseUrl); });
				Ext.getCmp('addButton-license').setText(LICENSE_LABEL_BUY);
			}
			
			licenseCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				softwareUpdateCrud.getComponent().clearSearchFields();
				softwareUpdateCrud.getComponent().setDefaultParams({'viewBean.licenseId': grid.store.getAt(rowIndex).data.id});
				softwareUpdateCrud.getComponent().loadDataStore();
			});
			
			licenseCrud.getComponent().loadDataStore();
			// Refresh the licenses linked to the project
			//licenseCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				
			//	var func = function() {
			//		selectedGrid = grid;
			//		var licenseRow = grid.store.getAt(rowIndex);
					// License
			//		var load = licenseForm.getLoadFunc();
					//Ext.getCmp('keyBis').disable();
					//Ext.getCmp('descriptionBis').disable();
			//		Ext.getCmp('tabPanelLicense').disable();
			//		Ext.getCmp('orientationBis').disable();
			//		Ext.getCmp('save-button').disable();
			//		Ext.getCmp('addButton-device').disable();
			//		Ext.getCmp('deleteButton-device').disable();
			//		load(licenseRow.data.id, function() {
						//Ext.getCmp('keyBis').enable();
						//Ext.getCmp('descriptionBis').enable();
			//			Ext.getCmp('tabPanelLicense').enable();
			//			Ext.getCmp('orientationBis').enable();
			//			Ext.getCmp('save-button').enable();
			//			Ext.getCmp('addButton-device').enable();
			//			Ext.getCmp('deleteButton-device').enable();
			//		});
			//	}
			//	
			//	if(hasPageChanged) displaySaveLicensesWindow("", func);
			//	else func();
				
			//});
			
			//licenseCrud.getComponent().on('render', function(grid, rowIndex, event) {
			//	//Ext.getCmp('keyBis').disable();
			//	//Ext.getCmp('descriptionBis').disable();
			//	Ext.getCmp('tabPanelLicense').disable();
			//	Ext.getCmp('orientationBis').disable();
			//	Ext.getCmp('save-button').disable();
			//	Ext.getCmp('addButton-device').disable();
			//	Ext.getCmp('deleteButton-device').disable();
			//});
			
			// Check modifications
			save = licenseForm.getSaveFunc();
			document.getElementById("publishButton").setAttribute("onclick","checkSaveBeforePublish();");
			
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
