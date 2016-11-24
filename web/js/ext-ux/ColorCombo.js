Ext.namespace('Ext.ux');

/**
 */
Ext.ux.ColorCombo = function(config){
    Ext.ux.ColorCombo.superclass.constructor.call(this, config);
    
    this.tpl = config.tpl ||
	    '<tpl for=".">'+
		'<div class="x-combo-list-item">'+
		'<table bgcolor="#{'+this.colorField+'}"  width="95%" height="20px" style="margin: 2px;"><tr><td>'+
		'</td></tr></table>'+
		'</div>'+
		'</tpl>';
    
    this.on({
        render: {
            scope: this,
            fn: function(){
                this.el.addClass('x-color-combo-input');
            }
        }
    });
}

// extend
Ext.extend(Ext.ux.ColorCombo, Ext.form.ComboBox, {

    setColor: function(){
        var rec = this.store.query(this.valueField, this.getValue()).itemAt(0);
        if (rec) {
            this.el.dom.style.backgroundColor = '#'+rec.get(this.colorField);
            this.el.dom.style.color = '#'+rec.get(this.colorField);
        }
    },
    
    setValue: function(value){
        Ext.ux.ColorCombo.superclass.setValue.call(this, value);
        this.setColor();
    }
    
});

Ext.reg('ux-colorcombo', Ext.ux.ColorCombo);
