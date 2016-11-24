Ext.namespace('Ext.ux');

Ext.ux.SumGroupingView = Ext.extend(Ext.grid.GroupingView, {
    
    initTemplates : function(){
		this. groupTextTpl = '{text} - {sum} '+COMMON_CURRENCY;
	    Ext.grid.GroupingView.superclass.initTemplates.call(this);
	    this.state = {};
	
	    var sm = this.grid.getSelectionModel();
	    sm.on(sm.selectRow ? 'beforerowselect' : 'beforecellselect',
	            this.onBeforeRowSelect, this);
	
	    if(!this.startGroup){
	        this.startGroup = new Ext.XTemplate(
	            '<div id="{groupId}" class="x-grid-group {cls}">',
	                '<div id="{groupId}-hd" class="x-grid-group-hd" style="{style}"><div class="x-grid-group-title">', this.groupTextTpl ,'</div></div>',
	                '<div id="{groupId}-bd" class="x-grid-group-body">'
	        );
	    }
	    this.startGroup.compile();
	    if(!this.endGroup){
	        this.endGroup = '</div></div>';
	    }
	
	    this.endGroup = '</div></div>';
	},
	
    doRender : function(cs, rs, ds, startRow, colCount, stripe){
	    if(rs.length < 1){
	        return '';
	    }
	    var groupField = this.getGroupField(),
	        colIndex = this.cm.findColumnIndex(groupField),
	        g;
	
	    var sumFields = this.grid.initialConfig.sumFields;

	    this.enableGrouping = (this.enableGrouping === false) ? false : !!groupField;
	
	    if(!this.enableGrouping || this.isUpdating){
	        return Ext.grid.GroupingView.superclass.doRender.apply(
	                this, arguments);
	    }
	    var gstyle = 'width:' + this.getTotalWidth() + ';',
	        cfg = this.cm.config[colIndex],
	        groupRenderer = cfg.groupRenderer || cfg.renderer,
	        prefix = this.showGroupName ? (cfg.groupName || cfg.header)+': ' : '',
	        groups = [],
	        curGroup, i, len, gid;
	        
	    var sum = 0;
	
	    for(i = 0, len = rs.length; i < len; i++){
    	    sum = 0;
	        var rowIndex = startRow + i,
	            r = rs[i],
	            gvalue = r.data[groupField];
	        
	        if (sumFields && sumFields.length) {
	    	    for(var j = 0, lenSumFields = sumFields.length; j < lenSumFields; j++){
	    	    	var val = parseFloat(r.data[sumFields[j]].trim().replace(',', '.'));
	    	    	if (!isNaN(val)) {
		    	    	sum = sum + val;
	    	    	}
	    	    }
	        }
	
	        g = this.getGroup(gvalue, r, groupRenderer, rowIndex, colIndex, ds);
	        if(!curGroup || curGroup.group != g){
	            gid = this.constructId(gvalue, groupField, colIndex);
	            // if state is defined use it, however state is in terms of expanded
	            // so negate it, otherwise use the default.
	            this.state[gid] = !(Ext.isDefined(this.state[gid]) ? !this.state[gid] : this.startCollapsed);
	            curGroup = {
	                group: g,
	                gvalue: gvalue,
	                text: prefix + g,
	                groupId: gid,
	                startRow: rowIndex,
	                rs: [r],
	                cls: this.state[gid] ? '' : 'x-grid-group-collapsed',
	                style: gstyle,
	                sum: sum
	            };
	            groups.push(curGroup);
	        }else{
	            curGroup.rs.push(r);
	            curGroup.sum = curGroup.sum + sum;
	        }
	        r._groupId = gid;
	    }
	
	    var buf = [];
	    for(i = 0, len = groups.length; i < len; i++){
	        g = groups[i];
	        this.doGroupStart(buf, g, cs, ds, colCount);
	        buf[buf.length] = Ext.grid.GroupingView.superclass.doRender.call(
	                this, cs, g.rs, ds, g.startRow, colCount, stripe);
	
	        this.doGroupEnd(buf, g, cs, ds, colCount);
	    }
	    return buf.join('');
	}
});

