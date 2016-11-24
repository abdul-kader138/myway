// Create user extensions namespace (Ext.ux)
Ext.namespace('Ext.ux.form');
 
/**
  * Ext.ux.form.IconCombo Extension Class
  *
  * @author  Jozef Sakalos
  * @version 1.0
  *
  * @class Ext.ux.form.IconCombo
  * @extends Ext.form.ComboBox
  * @constructor
  * @param {Object} config Configuration options
  */
Ext.ux.form.IconCombo = function(config) {
 
    // call parent constructor
    Ext.ux.form.IconCombo.superclass.constructor.call(this, config);
 
 	if (config.displayMethod && config.displayMethod == 'img') { // displayMethod = 'img' ou 'css'
 		this.displayMethod = 'img';
		this.tpl = config.tpl ||
			'<tpl for="."><div class="x-combo-list-item"><img src="'+myAppContext+'{path}" width="16" height="16">{name}</div></tpl>';
	}
	else {
 		this.displayMethod = 'css';
	    this.tpl = config.tpl ||
	          '<tpl for="."><div class="x-combo-list-item x-icon-combo-item {'+ this.iconClsField+'}">{'+this.displayField+'}</div></tpl>';
	}
 
    this.on({
        render:{scope:this, fn:function() {
            this.wrap.applyStyles({position:'relative'});
            this.el.addClass('x-icon-combo-input');
			/*
            var wrap = this.el.up('div.x-form-field-wrap');
            this.flag = Ext.DomHelper.append(wrap, {
                tag: 'div', style:'position:absolute'
            });
            this.img = Ext.DomHelper.append(this.flag, {
                tag: 'img', style:'position:absolute', width:'16', height:'16'
            });
            */
        }}
    });
} // end of Ext.ux.form.IconCombo constructor
 
// extend
Ext.extend(Ext.ux.form.IconCombo, Ext.form.ComboBox, {
 
    setIconCls: function() {
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if(rec) {
            this.flag.className = 'x-icon-combo-icon ' + rec.get(this.iconClsField);
        }
    },
 
    setIconImg: function() {
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if(rec) {
            this.img.src = myAppContext+rec.get('path');
			//Ext.util.CSS.createStyleSheet('.itemSelectedIcon {background-image:url('+myAppContext+rec.get('path')+') !important;}', 'itemSelectedIcon');
            this.flag.className = 'x-icon-combo-icon itemSelectedIcon';
        }
    },

    setValue: function(value) {
		if (!this.flag) {
			// Initialisation de la zone d'affichage de l'image
            var wrap = this.el.up('div.x-form-field-wrap');
            this.flag = Ext.DomHelper.append(wrap, {
                tag: 'div', style:'position:absolute'
            });
            this.img = Ext.DomHelper.append(this.flag, {
                tag: 'img', style:'position:absolute', width:'16', height:'16'
            });
		}
		
        Ext.ux.form.IconCombo.superclass.setValue.call(this, value);
		if (this.displayMethod == 'css') {
	        this.setIconCls();
		}
		else {
	        this.setIconImg();
		}
    }
 
}); // end of extend
 
Ext.reg('iconcombo', Ext.ux.form.IconCombo);

// end of file
