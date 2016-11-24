<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/item/modules/itemCrud.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// Map panel
			var mapPanel = new Ext.Panel({
				region: 'center',
				border: false,
				layout: 'fit',
				items: [
					{
						xtype: 'textarea',
						id: 'result'
					}
				]
			});

			// Map panel
			var buttonsPanel = new Ext.Panel({
				region: 'south',
				split: true,
				height: 52,
				layout: 'hbox',
				layoutConfig: {align: 'middle', pack: 'center'}, 
				bodyCssClass: 'ha-panel-body',
				style: 'text-align:center;',
				items: [
					{
						xtype: 'button',
						text: 'Init',
						width: 200,
						height: 40,
						handler: function() {
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/map/initEditor', {'projectId' : window.projectId}, function(response) {
								Ext.getCmp('result').setValue(response.responseText);
							});
						}
					},
					{
						xtype: 'label',
						width: 20
					},
					{
						xtype: 'button',
						text: 'Save',
						width: 200,
						height: 40,
						handler: function() {
							HurryApp.Utils.sendAjaxRequest(myAppContext+'/map/saveEditor', {'projectId' : window.projectId, xml: Ext.getCmp('result').getValue()}, function(response) {
								Ext.getCmp('result').setValue(response.responseText);
							});
						}
					}
				]
			});

			// Main panel
			mainPanel.add(mapPanel);
			mainPanel.add(buttonsPanel);
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
