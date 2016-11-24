Ext.namespace('Ext.ux');

Ext.ux.TreeCombo = Ext.extend(Ext.form.ComboBox, {
	initList: function() {
		if (!this.list) {
			this.list = new Ext.tree.TreePanel({
				root: {
					nodeType: 'async',
					text: 'Root',
					draggable: false,
					id: '1'
				},
				rootVisible: false,
				loader: new Ext.tree.TreeLoader({dataUrl: this.initialConfig.urlLoad, baseParams: this.initialConfig.baseParams}),
				floating: true,
				autoHeight: true,
				listeners: {
					click: this.onNodeClick,
					scope: this
				},
				alignTo: function(el, pos) {
					this.setPagePosition(this.el.getAlignToXY(el, pos));
				}
			});
		}
	},

	expand: function() {
		if (!this.list.rendered) {
			this.list.render(document.body);
			this.list.setWidth(this.el.getWidth());
			this.innerList = this.list.body;
			this.list.hide();
		}
		this.el.focus();
		// fixed : force tree to collapse on combo collapse event
		this.on('collapse', function(combo) {
			combo.list.collapseAll();
		});
		Ext.ux.TreeCombo.superclass.expand.apply(this, arguments);
		if (this.initialConfig.expandAll == true) {
			this.list.expandAll();
		}
	},

	doQuery: function(q, forceAll) {
		this.expand();
	},

	collapseIf : function(e){
		if(!e.within(this.wrap) && !e.within(this.list.el)){
			this.collapse();
		}
	},

	onNodeClick: function(node, e) {
		this.setRawValue(node.attributes.text);
		if (this.hiddenField) {
			this.hiddenField.value = node.id;
		}
		this.fireEvent('select');
		this.collapse();
	}
});
Ext.reg('ux-treecombo', Ext.ux.TreeCombo);

// Patch permettant de setter la valeur du champ caché du TreeCombo
Ext.form.BasicForm.prototype.setValues = function(values){
    if(Ext.isArray(values)){ 
        for(var i = 0, len = values.length; i < len; i++){
            var v = values[i];
            var f = this.findField(v.id);
            if(f){
                f.setValue(v.value);
                if(this.trackResetOnLoad){
                    f.originalValue = f.getValue();
                }
            }
        }
    }else{ 
        var field, id;
        for(id in values){
            if(!Ext.isFunction(values[id])){
                if(field = this.findField(id)){
                    field.setValue(values[id]);
                    if(this.trackResetOnLoad){
                        field.originalValue = field.getValue();
                    }
                }
                else{
                	field = Ext.get(id);
                	if(field && field.dom && field.dom.tagName == "INPUT"){
                		field.dom.value = values[id]
                	}
                }
            }
        }
    }
    return this;
}
