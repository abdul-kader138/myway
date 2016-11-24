<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/translate-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/category/modules/categoryCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/category/modules/subCategoryCrud.js"></script>

		<script type="text/javascript">
		
		var categoryCheckFields = function (id) {
			var empty = true;
			
			if(Ext.getCmp('categoryContents['+id+'].name').getValue() != '') empty = false;
			
			if(empty) Ext.getCmp('button-translate['+id+']').disable(); 
			else Ext.getCmp('button-translate['+id+']').enable();
		}
		
		var subCategoryCheckFields = function (id) {
			var empty = true;
			
			if(Ext.getCmp('subCategoryContents['+id+'].name').getValue() != '') empty = false;
			
			if(empty) Ext.getCmp('button-translate['+id+']').disable(); 
			else Ext.getCmp('button-translate['+id+']').enable();
		}
		
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
			// CRUD categories
			var categoryCrud = new CategoryCrud();

			// CRUD subCategories
			var subCategoryCrud = new SubCategoryCrud();

			// Main panel
			mainPanel.add(categoryCrud.getComponent());
			mainPanel.add(subCategoryCrud.getComponent());

			// Refresh sub-categories linked to the selected category
			categoryCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
				subCategoryCrud.getComponent().clearSearchFields();
				subCategoryCrud.getComponent().setDefaultParams({'viewBean.categoryId': grid.store.getAt(rowIndex).data.id});
				subCategoryCrud.getComponent().loadDataStore();
				Ext.getCmp('addButton-subCategory').enable();
				Ext.getCmp('deleteButton-subCategory').enable();
			});
			subCategoryCrud.getComponent().subscribe('category.load', function(store, record, options) {
				if (categoryCrud.getComponent().getSelectionModel().getCount() < 1) {
					subCategoryCrud.getComponent().store.removeAll();
					Ext.getCmp('addButton-subCategory').disable();
					Ext.getCmp('deleteButton-subCategory').disable();
				}
			});
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
