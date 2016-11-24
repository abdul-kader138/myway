<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/building/modules/buildingCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/building/modules/floorCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/building/modules/zoneCrud.js"></script>

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
			// CRUD buildings
			var buildingCrud = new BuildingCrud();

			// CRUD floors
			var floorCrud = new FloorCrud();
			var zoneCrud = new ZoneCrud();

			// Main panel
			mainPanel.add(buildingCrud.getComponent());
			mainPanel.add(floorCrud.getComponent());
			mainPanel.add(zoneCrud.getComponent());

			// Refresh floors linked to the selected building
			buildingCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				floorCrud.getComponent().setDefaultParams({'viewBean.buildingId': grid.store.getAt(rowIndex).data.id});
				floorCrud.getComponent().loadDataStore();
				Ext.getCmp('addButton-floor').enable();
				Ext.getCmp('deleteButton-floor').enable();
				Ext.getCmp('upButton-floor').enable();
				Ext.getCmp('downButton-floor').enable();
				
				zoneCrud.getComponent().setDefaultParams({'viewBean.buildingId': grid.store.getAt(rowIndex).data.id});
				zoneCrud.getComponent().loadDataStore();
				Ext.getCmp('addButton-zone').enable();
				Ext.getCmp('deleteButton-zone').enable();
			});
			
			/*floorCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				zoneCrud.getComponent().setDefaultParams({'viewBean.floorId': grid.store.getAt(rowIndex).data.id});
				zoneCrud.getComponent().loadDataStore();
				Ext.getCmp('addButton-zone').enable();
				Ext.getCmp('deleteButton-zone').enable();
			});*/
			
			floorCrud.getComponent().subscribe('building.load', function(store, record, options) {
				if (buildingCrud.getComponent().getSelectionModel().getCount() < 1) {
					floorCrud.getComponent().store.removeAll();
					Ext.getCmp('addButton-floor').disable();
					Ext.getCmp('deleteButton-floor').disable();
					Ext.getCmp('upButton-floor').disable();
					Ext.getCmp('downButton-floor').disable();
				}
			});
			
			zoneCrud.getComponent().subscribe('building.load', function(store, record, options) {
				if (buildingCrud.getComponent().getSelectionModel().getCount() < 1) {
					zoneCrud.getComponent().store.removeAll();
					Ext.getCmp('addButton-zone').disable();
					Ext.getCmp('deleteButton-zone').disable();
				}
			});
			
			/*zoneCrud.getComponent().subscribe('floor.load', function(store, record, options) {
				if (floorCrud.getComponent().getSelectionModel().getCount() < 1) {
					zoneCrud.getComponent().store.removeAll();
					Ext.getCmp('addButton-zone').disable();
					Ext.getCmp('deleteButton-zone').disable();
				}
			});*/

			// Raffraichissement des listes en fonction du projet sélectionné
			//buildingCrud.getComponent().subscribe('project.select', function(projectId) {
			//	buildingCrud.getComponent().setDefaultParams({'viewBean.projectId': projectId});
			//	buildingCrud.getComponent().loadDataStore();
			//});
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
