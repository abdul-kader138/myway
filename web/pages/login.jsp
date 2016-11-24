<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>

<tiles:insertDefinition name="layout-app-login">
    <tiles:putAttribute name="header" type="string">
        <script type="text/javascript">
        Ext.onReady(function(){
            Ext.QuickTips.init();

            // form panel
            var formPanel = new HurryApp.FormPanel({
                title: '<s:text name="login.titre"/>',
                renderTo: 'login-form-div',
                width: 300,
                minWidth: 300,
                labelWidth: 45,
                url: myAppContext+'/login/execute',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '<s:text name="utilisateur.login"/>',
                    id: 'login',
                    name: 'viewBean.login'
                },{
                    xtype: 'textfield',
                    fieldLabel: '<s:text name="utilisateur.password"/>',
                    inputType: 'password',
                    id: 'password',
                    name: 'viewBean.password'
                },{
        			xtype: 'panel',
        			border: false,
        			html: '<a href="#" onclick="passwordForgotten();" style="color:#5f6061;">'+LOGIN_PASSWORD_FORGOTTEN+'</a>',
        			bodyCssClass: 'ha-plain-panel',
        			cls: 'ha-login-password-forgotten',
        			style: 'vertical-align: top; padding-left: 50px; overflow: hidden;'
        		}],
                successCallback: function(jsonResponse) {
        			var user = jsonResponse.datas[0];
                    if (user.groupeId == GROUPE_SUPER_ADMIN) {
                        window.location = myAppContext+'/project/init';
                    }
                    else {
                        window.location = myAppContext+'/project/init';
                    }                        
                },
                /*failureCallback: function(jsonResponse) {
                	var error = jsonResponse.errors[0];
                    if (error.id == "certificate") {
                        window.location = myAppContext+'/login/certificate';
                    }
                },*/
                checkDemo: false
            });
        });

		var passwordForgotten = function() {
			var login = Ext.getCmp('login').getValue();
			if (login != '') {
				HurryApp.Utils.sendAjaxRequest(
					myAppContext+'/login/passwordForgotten',
					{'viewBean.login': login},
					function(jsonResponse) {
						var email = jsonResponse.datas[0];
						if (email != '') {
							HurryApp.MessageUtils.showInfo(LOGIN_PASSWORD_FORGOTTEN_MESSAGE_CONFIRM + ' ' + jsonResponse.datas[0]);
						}
						else {
							HurryApp.MessageUtils.showInfo(LOGIN_PASSWORD_FORGOTTEN_LOGINNOTEXISTS + ' ' + jsonResponse.datas[0]);
						}
					}
				);
			}
			else {
				HurryApp.MessageUtils.showInfo(LOGIN_PASSWORD_FORGOTTEN_LOGINEMPTY);
			}
        }
        </script>
    </tiles:putAttribute>

    <tiles:putAttribute name="body" type="string">
        <div id="login-form-div" align="center"></div>
    </tiles:putAttribute>
</tiles:insertDefinition>
