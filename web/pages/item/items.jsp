<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/translate-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/item/modules/itemCrud.js"></script>

		<script type="text/javascript">
		
		var itemCheckFields = function (id) {
			var empty = true;
			
			if(Ext.getCmp('itemContents['+id+'].name').getValue() != '') empty = false;
			if(!Ext.isIE){ if(Ext.getCmp('itemContents['+id+'].description').getRawValue() != ('<br>')) empty = false; }
			else { if(Ext.getCmp('itemContents['+id+'].description').getRawValue() != ("<p>&nbsp;</p>")) empty = false; }
			if(Ext.getCmp('itemContents['+id+'].openingDays').getValue() != '') empty = false;
			if(Ext.getCmp('itemContents['+id+'].keywords').getValue() != '') empty = false;
			
			if(empty) Ext.getCmp('button-translate['+id+']').disable(); 
			else Ext.getCmp('button-translate['+id+']').enable();
		}
		
		// Translate tooltip
		var translateTooltip = function (id) {
			return (new Ext.ToolTip({
				cls: 'translate-tooltip',
				target: id,
				html: ITEM_TRANSLATE_TOOLTIP,
				anchor: "left",
				anchorOffset: 18,
				autoHide: true,
				autoShow: true,
				dismissDelay: 0
			}));
		}
		
		Ext.onReady(function(){
			// CRUD items
			var itemCrud = new ItemCrud();

			// Main panel
			mainPanel.add(itemCrud.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
