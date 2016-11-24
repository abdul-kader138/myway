<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/ad/modules/playlistCrud.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/ad/modules/adCrud.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/swfobject.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/pages/ad/modules/jwplayer.js"></script>

		<script type="text/javascript">
		Ext.onReady(function(){
			// Buy panel
			var buyUrl = '<s:property value="buyUrl"/>';
			if (buyUrl != '') {
				var buyIntroductionPanel = new Ext.Panel({
					region: 'center',
					layout: 'vbox',
					layoutConfig: {
						pack: 'center',
						align: 'center'
				    },
					border: false,
					cls: 'ha-plain-panel-lite',
					items: [
						{
							xtype: 'panel',
							border: false,
							html: AD_BUY_TOOLTIP
						}
						,{
							xtype: 'button',
							text: AD_BUTTON_BUY,
							scale: 'large',
							width: 150,
							style: 'margin-top:20px;',
							handler: function() {
								window.open(buyUrl);
							}
						}
					]
				});
				mainPanel.add(buyIntroductionPanel);

				/*
				var buyButtonPanel = new Ext.Panel({
					region: 'south',
					split: true,
					layout: 'hbox',
					layoutConfig: {
						pack: 'center'
				    },
					height: 40,
					border: false,
					cls: 'ha-plain-panel-lite',
					items: [
					]
				});
				mainPanel.add(buyButtonPanel);
				*/
			}
			// Main panel
			else {
				// CRUD playlists
				var playlistCrud = new PlaylistCrud();

				// CRUD ads
				var adCrud = new AdCrud();

				// Main panel
				mainPanel.add(playlistCrud.getComponent());
				mainPanel.add(adCrud.getComponent());

				// Refresh ads linked to the selected playlist
				playlistCrud.getComponent().on('rowclick', function(grid, rowIndex, event) {
					adCrud.getComponent().clearSearchFields();
					adCrud.getComponent().setDefaultParams({'viewBean.playlistId': grid.store.getAt(rowIndex).data.id});
					adCrud.getComponent().loadDataStore();
					Ext.getCmp('addButton-ad').enable();
					Ext.getCmp('deleteButton-ad').enable();
				});

				adCrud.getComponent().subscribe('playlist.load', function(store, record, options) {
					if (playlistCrud.getComponent().getSelectionModel().getCount() < 1) {
						adCrud.getComponent().store.removeAll();
						Ext.getCmp('addButton-ad').disable();
						Ext.getCmp('deleteButton-ad').disable();
					}
				});
			}
		});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
