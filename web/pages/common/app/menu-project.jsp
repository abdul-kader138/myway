<%@ page language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
	/*
	Ext.onReady(function(){
	    var menuBar = new Ext.Toolbar({
	        renderTo: 'menu-panel',
	        items: ['->']
	    });

	    if (window.userGroupId != GROUPE_USER) {
		    menuBar.add(new Ext.Toolbar.Button({
		        text: MENU_COMPANIES,
		        id: 'menu-company',
		        listeners: {
		            'mouseover': function(){
		                this.showMenu()
		            }
		        },
		        handler: function(){
		            window.location = myAppContext + '/company/init'
		        }
		    }));
	    }
	    
	    menuBar.add(new Ext.Toolbar.Button({
	        text: MENU_PROJECTS,
	        id: 'menu-project',
	        listeners: {
	            'mouseover': function(){
	                this.showMenu()
	            }
	        },
	        handler: function(){
	            window.location = myAppContext + '/project/init'
	        },
	        menu: {
	            subMenuAlign: 'tl-tr?',
	            items: [{
	                text: MENU_BUILDINGS,
	                handler: function(){
	                    window.location = myAppContext + '/building/init'
	                }
	            }, {
	                text: MENU_CATEGORIES,
	                handler: function(){
	                    window.location = myAppContext + '/category/init'
	                }
	            }, {
	                text: MENU_ICONS,
	                handler: function(){
	                    window.location = myAppContext + '/iconGroup/init'
	                }
	            }, {
	                text: MENU_ITEMS,
	                handler: function(){
	                    window.location = myAppContext + '/item/init'
	                }
	            }, {
	                text: MENU_MAP,
	                handler: function(){
	                    window.location = myAppContext + '/map/init'
	                }
	            }, {
	                text: MENU_PROMOTIONS,
	                handler: function(){
	                    window.location = myAppContext + '/promotion/init'
	                }
	            }, {
	                text: MENU_EVENTS,
	                handler: function(){
	                    window.location = myAppContext + '/event/init'
	                }
	            }, {
	                text: MENU_LANGUAGES,
	                handler: function(){
	                    window.location = myAppContext + '/language/init'
	                }
	            }, {
	                text: MENU_NEWSLETTER,
	                handler: function(){
	                    window.location = myAppContext + '/newsletter/init'
	                }
	            }]
	        }
	    }));

	    menuBar.add(new Ext.Toolbar.Button({
	        text: 'FWK',
	        id: 'menu-',
	        listeners: {
	            'mouseover': function(){
	                this.showMenu()
	            }
	        },
	        menu: {
	            subMenuAlign: 'tl-tr?',
	            items: [{
	                text: 'Groups',
	                handler: function(){
	                    window.location = myAppContext + '/groupe/init'
	                }
	            }, {
	                text: 'Resources',
	                handler: function(){
	                    window.location = myAppContext + '/ressource/init'
	                }
	            }, {
	                text: 'Menus',
	                handler: function(){
	                    window.location = myAppContext + '/menu/init'
	                }
	            }]
	        }
	    }));
	    menuBar.doLayout();
	});
    */
</script>

<div id="menu-panel-html">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-project" onclick="goTo(myAppContext+'/project/init?projectId=<%=session.getAttribute(com.sotouch.myway.Constants.SESSION_KEY_PROJECT_ID)%>');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.project" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-license" onclick="goTo(myAppContext+'/license/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.licenses" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-language" onclick="goTo(myAppContext+'/language/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.languages" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-building" onclick="goTo(myAppContext+'/building/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.buildings" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-category" onclick="goTo(myAppContext+'/category/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.categories" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-iconGroup" onclick="goTo(myAppContext+'/iconGroup/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.icons" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-item" onclick="goTo(myAppContext+'/item/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.items" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-map" onclick="goTo(myAppContext+'/map/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.map" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-promotion" onclick="goTo(myAppContext+'/promotion/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.promotions" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-event" onclick="goTo(myAppContext+'/event/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.events" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-newsletterEmail" onclick="goTo(myAppContext+'/newsletter/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.newsletter" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-ad" onclick="goTo(myAppContext+'/ad/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.ads" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
			<td class="ha-item" valign="bottom">
				<table border="0" cellpadding="0" cellspacing="0" id="submenu-theme" onclick="goTo(myAppContext+'/theme/init');">
					<tr>
						<td class="ha-item-l"></td>
						<td class="ha-item-c"><s:text name="menu.theme" /></td>
						<td class="ha-item-r"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
