<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>


<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/translate-tooltip.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/promotion/modules/promotionCrud.js"></script>

		<script type="text/javascript">
		
		var promotionCheckFields = function (id) {
			var empty = true;
			
			if(Ext.getCmp('promotionContents['+id+'].name').getValue() != '') empty = false;
			if(Ext.getCmp('promotionContents['+id+'].description').getValue() != '') empty = false;
			if(Ext.getCmp('promotionContents['+id+'].keywords').getValue() != '') empty = false;
			
			if(empty) Ext.getCmp('button-translate['+id+']').disable(); 
			else Ext.getCmp('button-translate['+id+']').enable();
		}
		
		function translateTooltip(id) {
			return (new Ext.ToolTip({
				cls: 'translate-tooltip',
				target: id,
				html: PROMOTION_TRANSLATE_TOOLTIP,
				anchor: "left",
				anchorOffset: 18,
				autoHide: true,
				autoShow: true,
				dismissDelay: 0
			}));
		}
		
		Ext.onReady(function(){
			// CRUD promotions
			var promotionCrud = new PromotionCrud();

			// Main panel
			mainPanel.add(promotionCrud.getComponent());
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
