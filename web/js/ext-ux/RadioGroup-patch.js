Ext.override(Ext.form.RadioGroup, {
//	getName : function() {
//		return this.items.first().getName();
//	},
//
//	getValue : function() {
//		var v;
//
//		this.items.each( function(item) {
//			v = item.getRawValue();
//			return !item.getValue();
//		});
//
//		return v;
//	},
//
	setValue : function(v) {
		this.items.each( function(item) {
			item.setValue(item.inputValue == v);
		});
	}
});