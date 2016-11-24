Ext.ns('HurryApp');

Ext.BLANK_IMAGE_URL = '../ext-3.1/resources/images/default/s.gif'; 

//-----------------------------------------------------------------------------
// Ajax exception handler
//-----------------------------------------------------------------------------
Ext.Ajax.on('requestcomplete', function(conn, response, options) {
	HurryApp.Utils.checkResponseErrors(response);
});

Ext.Ajax.on('requestexception', function(conn, response) {
	HurryApp.Utils.checkResponseErrors(response);
});

Ext.Ajax.on('loadexception', function (proxy, options, response, error) {
	HurryApp.Utils.checkResponseErrors(response);
});

String.prototype.ellipse = function(maxLength){
    if(this.length > maxLength){
        return this.substr(0, maxLength-3) + '...';
    }
    return this;
};
String.prototype.endsWith = function(str) {
	return (this.match(str+"$")==str)
}

//-----------------------------------------------------------------------------
// Utils
//-----------------------------------------------------------------------------

Ext.Ajax.timeout=900000;


HurryApp.Utils = function() {
	return {
		/**
		 * url: String
		 * params: Object 
		 * calback(Object jsonResponse): function
		 */
		sendAjaxRequest: function(url, params, callback, maskId, failure) {
			var mask;
			if (maskId != -1) {
				mask = new Ext.LoadMask(maskId ? maskId : Ext.getBody(), {});
				mask.show();
			}
			
			Ext.Ajax.request({
				url: url,
				success: function(response) {
					if (mask) {
						mask.hide();
					}
	
					var jsonResponse = null;
					try {
						jsonResponse = Ext.util.JSON.decode(response.responseText);
					}
					catch (e) {
					}
					
					if (jsonResponse != null) {
						if (response.status == 200 && jsonResponse.status == 200) { // OK response
							// Affichage des messages
							HurryApp.MessageUtils.showJsonResponse(jsonResponse);
	
							// Appel de la methode de retour
							if (callback) {
								callback.call({}, jsonResponse);
							}
						}
						else {
							HurryApp.Utils.checkResponseErrors(response);
						}
					}
					else {
						if (callback) {
							callback.call({}, response);
						}
					}
				},
				failure: function (response, options) {
					if (mask) {
						mask.hide();
					}
					
					if(failure) {
						failure(response, options);
					}
                },
				params: params
			});
		},
		
		/**
		 * Check and display response errors 
		 */
		checkResponseErrors: function(response) {
			var jsonResponse = null;
			try {
				jsonResponse = Ext.util.JSON.decode(response.responseText);
			}
			catch (e) {
			}

			if (jsonResponse != null) {
				if (jsonResponse.status && jsonResponse.status != 200) { // Error
					if (jsonResponse.status == 408) { // Expired
						window.location = myAppContext + '/login/init?logo='+logoUrl;
						//HurryApp.MessageUtils.showError('Votre session a expir&#233;...','Session non valide');
						//Ext.DomHelper.overwrite(Ext.getBody(), response.responseText);
					}
					else if (jsonResponse.status == 1001) { // Action non autoris� en d�mo
						HurryApp.MessageUtils.showWarning(COMMON_ERROR_DEMO_ACTIONNOTALLOWED);
					}
					else {
						var errorMsg = COMMON_ERROR_CONTACTADMIN;
						if (jsonResponse.errors.length != 0) {
							errorMsg += ' :<br/>';
							for (var i = 0; i < jsonResponse.errors.length; i++) {
								errorMsg += '<br/>' + jsonResponse.errors[i].msg;
							}
						}
						HurryApp.MessageUtils.showError(errorMsg);
						HurryApp.error(); // Erreur JS volontaire pour stoper le traitement
					}
				}
			}
			else if (response.status && response.status != 0 && response.status != 200) {
				if (response.status == 408) { // Expired
					window.location = myAppContext+'/login/init?logo='+logoUrl;
					//HurryApp.MessageUtils.showAlert('Votre session a expir&#233;...','Session non valide');
					//Ext.DomHelper.overwrite(Ext.getBody(), response.responseText);
				}
				else if (response.status == 404) {
					HurryApp.MessageUtils.showError('Page introuvable', 'Requ&#234;te invalide');
				}
				else {
					HurryApp.MessageUtils.showError("Unexpected exception");
				}
			}
			else {
				//HurryApp.MessageUtils.showAlert('Le serveur ne r�pond pas', 'Requ&#234;te invalide');
			}		

			/*
			if (jsonResponse != null) {
				if (response.status && response.status != 0 && response.status != 200) {
					if (response.status == 408) { // Expired
						window.location = myAppContext+'/login/init?logo='+logoUrl;
						//HurryApp.MessageUtils.showAlert('Votre session a expir&#233;...','Session non valide');
						//Ext.DomHelper.overwrite(Ext.getBody(), response.responseText);
					}
					else if (response.status == 404) {
						HurryApp.MessageUtils.showAlert('Le serveur ne trouve pas la page', 'Requ&#234;te invalide');
					}
					else {
						var errorMsg = 'Une erreur technique s\'est produite. Veuillez contacter votre administrateur';
						if (jsonResponse.errors && jsonResponse.errors.length != 0) {
							errorMsg += ' :<br/>';
							for (var i = 0; i < jsonResponse.errors.length; i++) {
								errorMsg += '<br/>'+jsonResponse.errors[i].msg;
							}
						}
						HurryApp.MessageUtils.showError(errorMsg);
					}
				}
				else if (jsonResponse) {
					if (jsonResponse.status && jsonResponse.status != 200) { // Error
						if (jsonResponse.status == 408) { // Expired
							window.location = myAppContext + '/login/init?logo='+logoUrl;
						//HurryApp.MessageUtils.showError('Votre session a expir&#233;...','Session non valide');
						//Ext.DomHelper.overwrite(Ext.getBody(), response.responseText);
						}
						else {
							var errorMsg = 'Une erreur s\'est produite. Veuillez contacter votre administrateur';
							if (jsonResponse.errors.length != 0) {
								errorMsg += ' :<br/>';
								for (var i = 0; i < jsonResponse.errors.length; i++) {
									errorMsg += '<br/>' + jsonResponse.errors[i].msg;
								}
							}
							HurryApp.MessageUtils.showError(errorMsg);
						}
					}
				}
				else {
					//HurryApp.MessageUtils.showAlert('Le serveur ne r�pond pas', 'Requ&#234;te invalide');
				}		
			}
			*/
		},


		/**
		 * Return an object containing url parameters. E.g. {param1:'value1', param2;'value2'}
		 */
		getUrlParams: function() {
			return Ext.urlDecode(window.location.search.substring(1));
		},
		
		/**
		 * Return an timestamp string
		 */
		getTimestamp: function() {
			return (new Date()).format('ymdHis');
		},
		
		/**
		 * Convert an array of objects to an array of strings matching a propertie 
		 */
		toArrayOfString: function(objectArray, propertie) {
			var arrayOfString = [];
			if (objectArray) {
				var j = 0;
				for (var i = 0; i < objectArray.length; i++) {
					if (objectArray[i][propertie]) {
						arrayOfString[j++] = objectArray[i][propertie];
					}
				}
			}
			return arrayOfString;
		},
		
		/**
		 * Convert an array of string to a string 
		 */
		arrayToString: function(array) {
			var str = '';
			for (var i=0; i<array.length; i++) {
				if (str != '') {
					str += ';';
				}
				str += Ext.encode(array[i]);
			};
			return str;
		},
		
		/**
		 * Convert an array of objects to an array of strings matching a propertie 
		 */
		toViewBean: function(dataBean) {
			var viewBean = {};
			for (prop in dataBean) {
				viewBean['viewBean.'+prop] = dataBean[prop];
			}
			return viewBean;
		},
		
		/**
		 * Convert an array of objects to an array of strings matching a propertie 
		 */
		toDataBean: function(viewBean) {
			var dataBean = {};
			for (prop in viewBean) {
				var shortProp = prop.substring(9, prop.length); // suppr 'viewBean.'
				dataBean[shortProp] = viewBean[prop];
			}
			return dataBean;
		},
		
		/**
		 * Convert an array of Ext.data.Record to on array of simple json objects
		 * @param {Object} records
		 */
		toJsonDatas: function(records) {
			var datas = new Array();
			for (var i=0; i<records.length; i++) {
				datas[i] = records[i].data;
			}
			return datas;	
		},
		
		/**
		 * Return the index of the item with the given id 
		 */
		getItemIndex: function(objectArray, itemId) {
			var index = 0;
			for (var i = 0; i < objectArray.length; i++) {
				if (objectArray[i].id == itemId) {
					index = i;
				}
			}
			return index;
		},
		
		/**
		 * Extend ids of fields with a suffix 
		 */
		extendFieldIds: function(fields, suffix) {
			for (var i = 0; i < fields.length; i++) {
				if (fields[i].id) {
					fields[i].id = fields[i].id + suffix;
				}
				
				if (fields[i].items) {
					HurryApp.Utils.extendFieldIds(fields[i].items, suffix);
				}
			}
		},

		/**
		 * Return a flash movie
		 */		
		getMovie: function(movieName) {
		if (navigator.appName.indexOf("Microsoft") != -1) {
				return window[movieName];
			} else {
				return document[movieName];
			}
		},
		
		/**
		 * Stop event propagation 
		 */
		stopEventPropagation: function(event) {
			if (event.stopPropagation) {
				event.stopPropagation();
			} 
			else {
				event.cancelBubble = true;
			} 
		},
		
        copyToClipboard: function(text){
            if (window.clipboardData) {
                window.clipboardData.setData('text', text);
            }
            else {
                var clipboarddiv = document.getElementById('divclipboardswf');
                if (clipboarddiv == null) {
                    clipboarddiv = document.createElement('div');
                    clipboarddiv.setAttribute("name", "divclipboardswf");
                    clipboarddiv.setAttribute("id", "divclipboardswf");
                    document.body.appendChild(clipboarddiv);
                }
                clipboarddiv.innerHTML = '<embed src="' + myAppContext + '/flash-movies/clipboard.swf"' +
										 ' FlashVars="clipboard=' + encodeURIComponent(text) + '"' +
										 ' width="0" height="0" type="application/x-shockwave-flash"></embed>';
            }
            return false;
        }		
	};
}();

//-----------------------------------------------------------------------------
// Messages utils
//-----------------------------------------------------------------------------
HurryApp.MessageUtils = function() {

	var messageTemplate = new Ext.XTemplate(
			'<table cellpadding="0" cellspacing="0" width="100%">',
			'<tr><td class="corner-top-left"></td><td class="top"></td><td class="corner-top-right"></td></tr>',
			'<tr><td colspan="3" class="center">',
			'<tpl for="errors">',
			'<p class="error-message">{msg}</p>',
			'</tpl>',
			'<tpl if="message!=null && message != \'\'">',
			'<p class="success-message">{message}</p>',
			'</tpl>',
			'</td></tr>',
			'<tr><td class="corner-bottom-left"></td><td class="bottom"></td><td class="corner-bottom-right"></td></tr>',
			'</table>');
	
	//var messagePopupTemplate = new Ext.XTemplate(
	//		'<tpl for="errors">',
	//		'<p class="error-message-popup">{msg}</p>',
	//		'</tpl>',
	//		'<tpl if="message!=null && message != \'\'">',
	//		'<p class="success-message-popup">{message}</p>',
	//		'</tpl>');

	return {
		showError: function (msg, title) {
			title = title || COMMON_MESSAGE_ERROR;
			Ext.Msg.show({
				title:title,
				msg:msg,
				modal:true,
				icon:Ext.Msg.ERROR,
				buttons:Ext.Msg.OK
			});
		},
		showWarning: function(msg, title, callback) {
			title = title || COMMON_MESSAGE_WARNING;
			Ext.Msg.show({
				title:title,
				msg:msg,
				modal:true,
				icon:Ext.Msg.WARNING,
				buttons:Ext.Msg.OK,
				fn:callback ? callback : Ext.emptyFn
			});
		},
		showInfo: function(msg, title) {
			title = title || COMMON_MESSAGE_INFO;
			Ext.Msg.show({
				title:title,
				msg:msg,
				modal:true,
				icon:Ext.Msg.INFO,
				buttons:Ext.Msg.OK
			});
		},
		showAlert: function(msg, title) {
			title = title || COMMON_MESSAGE_INFO;
			Ext.Msg.alert(title, msg);
		},
		showConfirm: function(msg, title, callback) {
			title = title || COMMON_MESSAGE_CONFIRM;
			Ext.Msg.confirm(title, msg, callback);
		},
		showTooltip: function(id, text) {
			new Ext.ToolTip({
				target: id,
				html: text,
				dismissDelay: 0,
				showDelay: 150,
				hideDelay: 0,
				anchor: "left",
				anchorOffset: 18,
			});
		},
		showJsonResponse: function(jsonResponse, formPanelId) {
			// Affichage des messages
			if (jsonResponse.message != null || jsonResponse.errors.length != 0) {
				if (!formPanelId) {
					formPanelId = 'form-panel';
				}
				var formPanel = Ext.getCmp(formPanelId);
				
				if (formPanel) {
					formPanel.getForm().markInvalid(jsonResponse.errors);
					//messagePopupTemplate.overwrite('messages-panel-popup', jsonResponse);
					//document.getElementById('messages-panel-popup').className = '';
				}
				else {
					if (!Ext.get('messages-panel')) {
						if (jsonResponse.errors.length != 0) {
							var errorMsg = '';
							if (jsonResponse.errors.length != 0) {
								for (var i = 0; i < jsonResponse.errors.length; i++) {
									if (errorMsg != '') {
										errorMsg += '<br/>';
									}
									errorMsg += jsonResponse.errors[i].msg;
								}
							}
							HurryApp.MessageUtils.showError(errorMsg);

							/*							
							var messageWindow = new Ext.Window({
								title: 'Informations',
								width: 400,
								html: '<div id=\'messages-panel\'/>',
								modal: true
							});
							messageWindow.show();
	
							messageTemplate.overwrite('messages-panel', jsonResponse);
							*/
						}
					}
					else {
						messageTemplate.overwrite('messages-panel', jsonResponse);
						Ext.get('messages-panel').slideIn('t', {easing: 'easeOut', duration: 1.0});
						//setTimeout(function() {
						//	Ext.get('messages-panel').slideOut('t', {easing: 'easeOut', duration: 0.5, remove: false, useDisplay: true});
						//}, 2000);
					}
				}
			}
			// Redirection
			else if (jsonResponse.redirectURL != null){
				window.location = myAppContext+jsonResponse.redirectURL; // redirection vers l'url
			}
		},
		cleanMessages: function() {
			Ext.get('messages-panel').slideOut('t', {easing: 'easeOut', duration: 1.5, remove: false, useDisplay: true});
			//var messagesPopup = document.getElementById('messages-panel-popup');
			//if(messagesPopup != null){
			//	messagesPopup.innerHTML = '';
			//}
		}
	};
}();

//-----------------------------------------------------------------------------
// Button handler
//-----------------------------------------------------------------------------
HurryApp.ButtonHandler = function() {
  return {
	buttonPressed: function(table) {
		table.rows[0].cells[0].className='ha-pressed-left';
		table.rows[0].cells[1].className='ha-pressed-center';
		table.rows[0].cells[2].className='ha-pressed-right';			
	},

	buttonReleased: function(table) {
		table.rows[0].cells[0].className='ha-left';
		table.rows[0].cells[1].className='ha-center';
		table.rows[0].cells[2].className='ha-right';			
	},

	buttonSelected: function(table) {
		table.rows[0].cells[0].className='ha-selected-left';
		table.rows[0].cells[1].className='ha-selected-center';
		table.rows[0].cells[2].className='ha-selected-right';			
	}
  };
}();

//-----------------------------------------------------------------------------
// Vignette handler
//-----------------------------------------------------------------------------
HurryApp.VignetteHandler = function() {
  return {
	mouseOver: function(table) {
		table.rows[0].cells[0].className='ha-over-top-left';
		table.rows[0].cells[1].className='ha-over-top';
		table.rows[0].cells[2].className='ha-over-top-right';			
		table.rows[1].cells[0].className='ha-over-left';
		if (table.rows[1].cells[1].className=='ha-center-full') {
			table.rows[1].cells[1].className='ha-over-center-full';
		}
		else {
			table.rows[1].cells[1].className='ha-over-center';
		}
		table.rows[1].cells[2].className='ha-over-right';			
		table.rows[2].cells[0].className='ha-over-bottom-left';
		table.rows[2].cells[1].className='ha-over-bottom';
		table.rows[2].cells[2].className='ha-over-bottom-right';			
	},

	mouseOut: function(table) {
		table.rows[0].cells[0].className='ha-top-left';
		table.rows[0].cells[1].className='ha-top';
		table.rows[0].cells[2].className='ha-top-right';			
		table.rows[1].cells[0].className='ha-left';
		if (table.rows[1].cells[1].className=='ha-over-center-full') {
			table.rows[1].cells[1].className='ha-center-full';
		}
		else {
			table.rows[1].cells[1].className='ha-center';
		}
		table.rows[1].cells[2].className='ha-right';			
		table.rows[2].cells[0].className='ha-bottom-left';
		table.rows[2].cells[1].className='ha-bottom';
		table.rows[2].cells[2].className='ha-bottom-right';			
	}
  };
}();


//-----------------------------------------------------------------------------
// Patch IE9
//-----------------------------------------------------------------------------
if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment) {
	Range.prototype.createContextualFragment = function(html) {
		var frag = document.createDocumentFragment(), 
		div = document.createElement("div");
		frag.appendChild(div);
		div.outerHTML = html;
		return frag;
	};
}

String.prototype.startsWith = function(str){
    return (this.match("^" + str) == str)
}