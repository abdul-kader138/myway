Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
//DropZone
//-----------------------------------------------------------------------------
HurryApp.GridDropZone = function(grid, config, fnBeforeDrop, fnAfterDrop) { 
	this.grid = grid;

	if (fnBeforeDrop) {
		this.fnBeforeDrop = fnBeforeDrop;
	}

	if (fnAfterDrop) {
		this.fnAfterDrop = fnAfterDrop;
	}
	HurryApp.GridDropZone.superclass.constructor.call(this, grid.view.scroller.dom, config); 
}; 

Ext.extend(HurryApp.GridDropZone, Ext.dd.DropZone, { 
	onContainerOver:function(dd, e, data) { 
		return (dd.grid !== this.grid && dd.grid.type == this.grid.type) ? this.dropAllowed : this.dropNotAllowed; 
	}, // eo function onContainerOver 

	onContainerDrop:function(dd, e, data) { 
		if(dd.grid !== this.grid && dd.grid.type == this.grid.type) { 
			// call function before drop
			var process = true;
			if (this.fnBeforeDrop) {
				process = this.fnBeforeDrop.call(this.grid, data);
			}
		
			if (process == true || process == undefined) {
				// ajout des elements si ils ne sont deja present
				var selectedRows = data.selections;
				for (var iRow = 0; iRow < selectedRows.length; iRow++) {
					selectedRecord = selectedRows[iRow];
					if (!this.grid.elementExist(selectedRecord)) {
						this.grid.store.add(selectedRecord);
					}
				}
				//this.grid.store.add(data.selections);
				
				// suppression des elements
				Ext.each(data.selections, function(r){
					dd.grid.store.remove(r);
				});
				
				this.grid.onRecordsDrop(dd.grid, data.selections);
				
				// call function after drop
				if (this.fnAfterDrop) {
					this.fnAfterDrop.call(this.grid, data);
				}
			}
			return true; 
		} 
		else { 
			return false; 
		} 
	} // eo function onContainerDrop 
}); 

//-----------------------------------------------------------------------------
//RowExpander
//-----------------------------------------------------------------------------
HurryApp.RowExpander = function(config){
	Ext.apply(this, config);

	this.addEvents({
		beforeexpand : true,
		expand: true,
		beforecollapse: true,
		collapse: true
	});

	HurryApp.RowExpander.superclass.constructor.call(this);

	if(this.tpl){
		if(typeof this.tpl == 'string'){
			this.tpl = new Ext.XTemplate(this.tpl);
		}
		this.tpl.compile();
	}

	this.state = {};
	this.bodyContent = {};
};

Ext.extend(HurryApp.RowExpander, Ext.util.Observable, {
	header: "",
	width: 20,
	sortable: false,
	fixed:true,
	dataIndex: '',
	id: 'expander',
	lazyRender : false,
	enableCaching: false,

	getRowClass : function(record, rowIndex, p, ds){
		p.cols = p.cols-1;
		var content = this.bodyContent[record.id];
		if(!content && !this.lazyRender){
			content = this.getBodyContent(record, rowIndex);
		}
		if(content){
			p.body = content;
		}
		return this.state[record.id] ? 'x-grid3-row-expanded' : 'x-grid3-row-collapsed';
	},

	init : function(grid){
		this.grid = grid;
	
		var view = grid.getView();
		view.getRowClass = this.getRowClass.createDelegate(this);
	
		view.enableRowBody = true;
	
		grid.on('render', function(){
			view.mainBody.on('mousedown', this.onMouseDown, this);
		}, this);
	},
	
	getBodyContent : function(record, index){
		if(!this.enableCaching){
			return this.tpl.apply(record.data);
		}
		var content = this.bodyContent[record.id];
		if(!content){
			content = this.tpl.apply(record.data);
			this.bodyContent[record.id] = content;
		}
		return content;
	},
	
	onMouseDown : function(e, t){
		if(t.className == 'x-grid3-row-expander'){
			e.stopEvent();
			var row = e.getTarget('.x-grid3-row');
			this.toggleRow(row);
		}
	},
	
	renderer : function(v, p, record){
		p.cellAttr = 'rowspan="2"';
		return '<div class="x-grid3-row-expander">&#160;</div>';
	},
	
	beforeExpand : function(record, body, rowIndex){
		if(this.fireEvent('beforexpand', this, record, body, rowIndex) !== false){
			if(this.tpl && this.lazyRender){
				body.innerHTML = this.getBodyContent(record, rowIndex);
			}
			return true;
		}
		else{
		   return false;
		}
	},
	
	toggleRow : function(row){
		if(typeof row == 'number'){
			row = this.grid.view.getRow(row);
		}
		this[Ext.fly(row).hasClass('x-grid3-row-collapsed') ? 'expandRow' : 'collapseRow'](row);
	},
	
	expandRow : function(row){
		if(typeof row == 'number'){
			row = this.grid.view.getRow(row);
		}
		var record = this.grid.store.getAt(row.rowIndex);
		var body = Ext.DomQuery.selectNode('tr:nth(2) div.x-grid3-row-body', row);
		if(this.beforeExpand(record, body, row.rowIndex)){
			this.state[record.id] = true;
			Ext.fly(row).replaceClass('x-grid3-row-collapsed', 'x-grid3-row-expanded');
			this.fireEvent('expand', this, record, body, row.rowIndex);
		}
	},
	
	collapseRow : function(row){
		if(typeof row == 'number'){
		   row = this.grid.view.getRow(row);
		}
		var record = this.grid.store.getAt(row.rowIndex);
		var body = Ext.fly(row).child('tr:nth(1) div.x-grid3-row-body', true);
		if(this.fireEvent('beforcollapse', this, record, body, row.rowIndex) !== false){
			this.state[record.id] = false;
			Ext.fly(row).replaceClass('x-grid3-row-expanded', 'x-grid3-row-collapsed');
			this.fireEvent('collapse', this, record, body, row.rowIndex);
		}
	}
});

//-----------------------------------------------------------------------------
//Grid using DropZone and RowExpander
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Form panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.Grid
 * @extends Ext.grid.EditorGridPanel
 * Class for creating a grid with drag and drop, pagination, row group and row expander capabilities.
 * 
 * <br><br>Usage:
 * <pre><code>    
var catsGrid = new HurryApp.Grid({
    type: 'cat',
    renderTo: 'cats-div',
    columns: [{id: 'name', dataIndex: 'name', header: 'Name', sortable: true}],
    width: 400,
    height: 300,
    urlLoad: '/myApp/cat/load',
    displayPagingBar: true
});
    
var selectedCatsGrid = new HurryApp.Grid({
    type: 'cat',
    renderTo: 'selected-cats-div',
    columns: [{id: 'name', dataIndex: 'name', header: 'Name', sortable: true}],
    width: 400,
    height: 300,
    urlLoad: '/myApp/cat/load'
});
    
catsGrid.setLinkedGrid(selectedCatsGrid);
</code></pre>
 * In this sample you can drag items form a grid and drop them to another.
 * 
 * @constructor
 * @param {Object} config The config object
 */
HurryApp.Grid = Ext.extend(Ext.grid.LockingEditorGridPanel, {
	/**
	 * @cfg {String} urlLoad The url from which to load datas. This url must return an object like
	 * {
	 *   datas: [{attribut1: 'valeur1', attribut2: 'valeur1'}, {attribut1: 'valeur2', attribut2: 'valeur2'}, {attribut1: 'valeur3', attribut2: 'valeur3'}],
	 *   count: 3
	 * }
	 */
	/**
	 * @cfg {Object} loadParams An object containing properties which are used as parameters to the load request (see {@link HurryApp.Grid#urlLoad urlLoad}).
	 */
	/**
	 * @cfg {Object} defaultParams An object containing properties which are used as parameters to the load request (see {@link HurryApp.Grid#urlLoad urlLoad}).
	 */
	/**
	 * @cfg {Boolean} loadOnStartup If true datas are loaded after render using loadUrl (default to true).
	 * If false datas will be loaded on demand using loadDataStore function.
	 */
	/**
	 * @cfg {Array} gridJSONDataNames (optional) The names of data to store in the data store. If not defined, data indexes (property 'dataIndex') of columns are used.
	 */
	/**
	 * @cfg {Function} getRowClass (optional) See {@link Ext.grid.GridView#getRowClass Ext.grid.GridView#getRowClass}.
	 */
	/**
	 * @cfg {String} templateExpander (optional) Template used to display datas for each row through an expander. For templates see {@link Ext.XTemplate Ext.XTemplate}.
	 */
	/**
	 * @cfg {String} groupField (optional) The name of the column used to group rows.
	 */
	/**
	 * @cfg {Function} fnClick (optional) Function called on a row click.
	 */
	/**
	 * @cfg {Function} fnDblClick (optional) Function called on a row double click.
	 */
	/**
	 * @cfg {Function} fnBeforeTransferItems (optional) Function called before items have been transfered to another grid (see {@link HurryApp.Grid#transferSelectedItems transferSelectedItems}).
	 */
	/**
	 * @cfg {Function} fnBeforeTransferItems (optional) Function called after items have been transfered to another grid (see {@link HurryApp.Grid#transferSelectedItems transferSelectedItems}).
	 */
	/**
	 * @cfg {String} type (optional) The type of objects contained in the grid. Used for drag and drop capabilities: you can drag and drop items between grids with same type.
	 * Also used to publish a specific event when datas are loaded: eg. if type is 'cat' the event 'cat.load' will be sended when data are loaded.
	 */
	/**
	 * @cfg {Boolean} displayPagingBar If true a paging bar is displayed (default to false) and parameters 'paginationBean.start' and and 'paginationBean.limit' are sended to each load request.
	 */
	/**
	 * @cfg {Boolean} displayToolBar If true a toolbar is displayed (default to false).
	 */
	/**
	 * @cfg {Integer} pageSize Number of items in a page (default to DEFAULT_PAGE_SIZE defined in "config.js").
	 */
	/**
	 * @cfg {Boolean} lockColumns If true columns with 'locked' propertie will be locked.
	 */
	
	// superclass properties
	autoScroll: true,
	autoHeight: true,
	layout: 'fit',
	enableDragDrop: true,
	clicksToEdit: 1,
	loadMask: {msg:'Chargement ...'},

	// class properties
	type: 'entity',
	defaultParams: {},
	fnBeforeTransferItems: Ext.emptyFn,
	fnAfterTransferItems: Ext.emptyFn,
	enableSingleSelect: false,
	loadOnStartup: true,
	displayPagingBar: false,
	displayToolBar: true,
	pageSize: DEFAULT_PAGE_SIZE,
	gridMenubar: null,

	initComponent:function() {
		if (this.initialConfig.loadOnStartup == false) {this.loadOnStartup = false;}
		if (this.initialConfig.height) {this.autoHeight = false;}
		if (this.initialConfig.type) {this.type = this.initialConfig.type;}
		if (this.initialConfig.displayPagingBar) {this.displayPagingBar = this.initialConfig.displayPagingBar;}
		if (this.initialConfig.displayToolBar) {this.displayToolBar = this.initialConfig.displayToolBar;}
		if (this.initialConfig.pageSize) {this.pageSize = this.initialConfig.pageSize;}
		if (this.initialConfig.defaultParams) {this.defaultParams = this.initialConfig.defaultParams;}

		// Proxy
		var proxy = new Ext.data.HttpProxy({
				url: this.initialConfig.urlLoad
			}
		);
		
    	// JSON data names
		if (!this.initialConfig.gridJSONDataNames) {
			this.initialConfig.gridJSONDataNames = HurryApp.Utils.toArrayOfString(this.initialConfig.columns, 'dataIndex');
		}

		// Reader
		var reader = new Ext.data.JsonReader({
				root: 'datas',
				totalProperty: 'count',
				id: 'id'
			},
			this.initialConfig.gridJSONDataNames
		);

		// Expander
		var columnsExpander = this.initialConfig.columns;
		var expander = {init:Ext.emptyFn};

		if (this.initialConfig.templateExpander) {
			expander = new HurryApp.RowExpander({
				tpl : new Ext.XTemplate(this.initialConfig.templateExpander)
			});
			var tabExpander = new Array(expander);
			columnsExpander = tabExpander.concat(this.initialConfig.columns);
		}

		// Store
		var store;

		// View
		var view;
		
		// Grouping grid
		if (this.initialConfig.groupField && this.initialConfig.groupField != '') {
			store = new Ext.data.GroupingStore({
				proxy: proxy,
				reader: reader,
				sortInfo: {field:this.initialConfig.groupField, direction:'ASC'},
				groupField:this.initialConfig.groupField
				
			});
			if (this.initialConfig.sumFields) {
				view = new Ext.ux.SumGroupingView({
					forceFit:false,
					getRowClass:this.initialConfig.getRowClass,
					groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "El&eacute;ments" : "El&eacute;ment"]})',
					hideGroupedColumn:true,
					showGroupName:false
				});
			}
			else {
				view = new Ext.grid.GroupingView({
					forceFit:false,
					getRowClass:this.initialConfig.getRowClass,
					groupTextTpl: '{text}',
					hideGroupedColumn:true,
					showGroupName:false
				});
			}
		}
		// Simple grid
		else {
			store = new Ext.data.Store({
				proxy: proxy,
				reader: reader,
				remoteSort: true				
			});
			// Locked columns
			if (this.initialConfig.lockColumns == true) {
				view = new Ext.grid.LockingGridView({
					forceFit:false,
					getRowClass:this.initialConfig.getRowClass
				})
			}
			// Not locked columns
			else {
				view = new Ext.grid.GridView({
					forceFit:false,
					getRowClass:this.initialConfig.getRowClass
				})
			}
		}
		
		// Pagging
		var pagingBar = null;
		if (this.displayPagingBar == true) {
		    pagingBar = new Ext.PagingToolbar({
		        pageSize: this.pageSize,
		        paramNames: {start: 'paginationBean.start', limit: 'paginationBean.limit'},
		        store: store,
		        displayInfo: true,
		        displayMsg: 'Elements {0} - {1} of {2}',
		        emptyMsg: "No element"
		    });
		}
    
		// menu bar with search fields
		if (this.gridMenubar == null) {
			var menubarElements = new Array();
			var nbMenubarElelements = 0;
			if (this.initialConfig.menuButtons) {
				for (var i = 0; i < this.initialConfig.menuButtons.length; i++) {
					if (menubarElements.length > 0) {
						menubarElements[nbMenubarElelements++] = '-';
					}
					
					menubarElements[nbMenubarElelements++] = this.initialConfig.menuButtons[i];
				}
			}
			menubarElements = menubarElements.concat(this.getMenuBarSearchFields(true));
			if (menubarElements.length > 0) {
				this.gridMenubar = new Ext.Toolbar(menubarElements);
			}
		}
		
		// Apply properties
		Ext.apply(this, { 
			sm: new Ext.grid.RowSelectionModel({singleSelect:this.enableSingleSelect}),
			columns: columnsExpander,
			store: store,
			view: view,
			type: this.type,
			plugins: expander,
			tbar: this.displayToolBar == true ? this.gridMenubar : null,
	        bbar: pagingBar
		}); // eo apply 
	
		// call parent 
		HurryApp.Grid.superclass.initComponent.apply(this, arguments); 
	
		// specific init
		this.getColumnModel().defaultSortable = true;
	
		if (this.initialConfig.fnClick) {
		   this.on('cellclick', this.initialConfig.fnClick);
		}
	
		if (this.initialConfig.fnDblClick) {
		   this.on('rowdblclick', this.initialConfig.fnDblClick);
		}
		else {
		   //this.on('rowdblclick', this.transferSelectedItems);
		}
	
		if (this.initialConfig.fnBeforeTransferItems) {
		   this.fnBeforeTransferItems = this.initialConfig.fnBeforeTransferItems;
		}
	
		if (this.initialConfig.fnAfterTransferItems) {
		   this.fnAfterTransferItems = this.initialConfig.fnAfterTransferItems;
		}
		
		// publish specific load event
		this.store.on('load', function(store, record, options) {
			this.publish(this.type+'.load');
		}, this);

		if (this.initialConfig.loadParams) {
			this.store.baseParams = this.initialConfig.loadParams
		};
	
		// display
		//if (this.initialConfig.gridDivId) {
		//   this.render(this.initialConfig.gridDivId);
		//}

		// load datastore
		if (this.loadOnStartup == true) {
			this.loadDataStore();
		}
	}, // eo function initComponent 

	onRender:function() { 
		HurryApp.Grid.superclass.onRender.apply(this, arguments); 
		this.dz = new HurryApp.GridDropZone(this, {ddGroup:this.ddGroup || 'GridDD'}, this.initialConfig.fnBeforeTransferItems, this.initialConfig.fnAfterTransferItems); 
	}, // eo function onRender 
	
	onRecordsDrop:Ext.emptyFn,

	/** 
	 * Set default parameters always sended to the load request. 
	 * @param {Object} defaultParams An object containing properties which are always used as parameters to the load request 
	 */ 
	setDefaultParams: function(defaultParams) {
		this.defaultParams = defaultParams;
	},

	/** 
	 * Set parameters sended to the load request. 
	 * @param {Object} params An object containing properties which are used as parameters to the load request 
	 */ 
	setLoadParams: function(params) {
		this.store.baseParams = params;
	},

	/** 
	 * Load the grid with data from load request (see {@link HurryApp.Grid#urlLoad urlLoad}). 
	 * @param {Object} params (optional) An object containing properties which are used as parameters to the load request 
	 */ 
	loadDataStore: function(params) {
		if (params) {
			for (prop in params) {
				this.store.baseParams[prop] = params[prop];
			}
		}
		if (this.defaultParams) {
			for (prop in this.defaultParams) {
				this.store.baseParams[prop] = this.defaultParams[prop];
			}
		}
		if (this.displayPagingBar) {
			var pagingBar = this.getBottomToolbar();
			var activePage = Math.ceil((pagingBar.cursor + pagingBar.pageSize) / pagingBar.pageSize)-1;
			this.store.load({params:{'paginationBean.start':activePage * this.pageSize, 'paginationBean.limit':this.pageSize}});
		}
		else {
			this.store.load();
		}
	},
	
	/** 
	 * Set the grid linked to this one. Used for item transfer capabilities 
	 * (see {@link HurryApp.Grid#transferSelectedItems transferSelectedItems}).
	 * @param {Ext.grid.GridPanel} linkedGrid The grid to link to this one 
	 */ 
	setLinkedGrid: function(linkedGrid) {
		this.linkedGrid = linkedGrid;
	},
	
	/** 
	 * Transfer selected items to the linked grid. If linked grid is not defined, remove items 
	 * (see {@link HurryApp.Grid#setLinkedGrid setLinkedGrid}). 
	 */ 
	transferSelectedItems: function(gridPanel, rowIndex, e) {
		if (!gridPanel) {
			gridPanel = this;
		}
		
		var process = true;
		if (gridPanel.fnBeforeTransferItems) {
			process = gridPanel.fnBeforeTransferItems.call(gridPanel, rowIndex);
		}
	
		if (process == true || process == undefined) {
			var selectedRows = gridPanel.getSelectionModel().getSelections();
		
			if (gridPanel.linkedGrid) {
				for(var iRow=0;iRow<selectedRows.length;iRow++) {
					selectedRecord=selectedRows[iRow];
					if(!gridPanel.linkedGrid.elementExist(selectedRecord)) {
						if (gridPanel.linkedGrid.store) {
							gridPanel.linkedGrid.store.add(selectedRecord);
						}
					}
				}		
			}
		
			Ext.each(selectedRows, function(item) { 
				gridPanel.store.remove(item);
			});
		
			if (gridPanel.fnAfterTransferItems) {
			   gridPanel.fnAfterTransferItems.call(gridPanel, rowIndex);
			}
		}
	},
	
	/** 
	 * Transfer all items to the linked grid. If linked grid is not defined, remove items 
	 * (see {@link HurryApp.Grid#setLinkedGrid setLinkedGrid}). 
	 */ 
	transferAllItems: function(gridPanel) {
		gridPanel.getSelectionModel().selectAll();
		this.transferSelectedItems(gridPanel);
	},

	//Indique si l'element existe dans le tableau
	elementExist: function(record) {
		var exist = false;
		if (this.store) {
			var records = this.store.data.items;
			for (var i = 0; i < records.length; i++) {
				if (record.data.id == records[i].data.id) {
					exist = true;
					break;
				}
			}
		}
		return exist;
	},
	
	/**
	 * Return all objects as a string.
	 * @return json objects as strings separated by ";"
	 */
	getObjects: function() {
		var items = this.store.data.items;
		var jsonString = '';
		if (items.length != 0) {
			for (i=0; i<items.length; i++) {
				var record = items[i];
				if (i == 0) {
					jsonString += Ext.util.JSON.encode(record.data);
				}
				else {  
					jsonString += ';' + Ext.util.JSON.encode(record.data)
				}
			}
		}
		return jsonString;
	},
	
	/**
	 * Return modified objects as a string.
	 * @return json objects as strings separated by ";"
	 */
	getModifiedObjects: function() {
		var items = this.store.getModifiedRecords();
		var jsonString = '';
		if (items.length != 0) {
			for (i=0; i<items.length; i++) {
				var record = items[i];
				if (i == 0) {
					jsonString += Ext.util.JSON.encode(record.data);
				}
				else {  
					jsonString += ';' + Ext.util.JSON.encode(record.data)
				}
			}
		}
		return jsonString;
	},
	
	/**
	 * Return new objects as a string.
	 * @return json objects as strings separated by ";"
	 */
	getNewObjects: function() {
		var items = this.store.data.items;
		var jsonString = '';
		if (items.length != 0) {
			for (i=0; i<items.length; i++) {
				var record = items[i];
				if (!record.data.id) {
					if (i == 0) {
						jsonString += Ext.util.JSON.encode(record.data);
					}
					else {  
						jsonString += ';' + Ext.util.JSON.encode(record.data)
					}
				}
			}
		}
		return jsonString;
	},
	
	/**
	 * Return ids (propertiy 'id') of all objects as a string.
	 * @return ids (integers) separated by ";"
	 */
	getIds: function() {
		var items = this.store.data.items;
		var ids = '';
		for (i=0; i<items.length; i++) {
			var record = items[i];
			var id = record.get('id');
			if(i == 0) {
				ids = id;
			}
			else {
				ids += ';' + id;
			}
		}
		return ids;
	},
	
	/**
	 * Return ids (propertiy 'id') of modified objects as a string.
	 * @return ids (integers) separated by ";"
	 */
	getSelectedIds: function(propertie) {
		var propertieId = propertie ? propertie : 'id';
		var items = this.getSelectionModel().getSelections();
		var ids = '';
		for (i=0; i<items.length; i++) {
			var record = items[i];
			var id = record.get(propertieId);
			if(i == 0) {
				ids = id;
			}
			else {
				ids+= ';' + id;
			}
		}
		return ids;
	}, 
	
	//------------------------------------------------------------------------
	// Search
	//-------------------------------------------------------------------------
	search: function() {
		// load datas by criteria
		var params = this.getSearchParams();
		if (this.displayPagingBar == true) {
			var pagingBar = this.getBottomToolbar();
			pagingBar.cursor = 0;
		}
		this.loadDataStore(params);
	},
	
	//------------------------------------------------------------------------
	// Return search parameters
	//-------------------------------------------------------------------------
	getSearchParams: function() {
		var searchFields = this.initialConfig.searchFields;
		var params = {};
		for (var i = 0; i < searchFields.length; i++) {
			var tool;
			if (searchFields[i].xtype == 'combo' || searchFields[i].xtype == 'ux-treecombo') {
				tool = Ext.get(searchFields[i].hiddenId).dom;
			}
			else {
				tool = Ext.get(searchFields[i].id).dom;
			}
			params[tool.name]= tool.value;
		}
		return params;
	},
	
	//------------------------------------------------------------------------
	// Return search fields for menu bar
	//-------------------------------------------------------------------------
	getMenuBarSearchFields: function(alignRight) {
		var menubarElements = new Array();
		var nbMenubarElelements = 0;
		var grid = this;

		if (this.initialConfig.searchFields) {
			var searchFields = this.initialConfig.searchFields;
			if (alignRight) {
				menubarElements[nbMenubarElelements++] = '->';
			}
			for (var i = 0; i < searchFields.length; i++) {
				if (searchFields[i].xtype != 'hidden') {
					menubarElements[nbMenubarElelements++] = '-';
					menubarElements[nbMenubarElelements++] = searchFields[i].fieldLabel+':';
				}
				searchFields[i].enableKeyEvents = true;
				searchFields[i].listeners = {'keypress': function(field, event) {
					if (event.getKey() == Ext.EventObject.ENTER) {
						grid.search();
					}
				}};
				menubarElements[nbMenubarElelements++] = searchFields[i];
			}
			menubarElements[nbMenubarElelements++] = {
				xtype: 'tbbutton',
				iconCls: 'tool-refresh',
				handler: function() {
					grid.search();
				}
			};
		}
		
		return menubarElements;
	},
	
	//------------------------------------------------------------------------
	// Clear search fields
	//-------------------------------------------------------------------------
	clearSearchFields: function() {
		this.store.baseParams = {};
		var searchFields = this.initialConfig.searchFields;
		if (searchFields != null) {
			for (var i = 0; i < searchFields.length; i++) {
				var tool;
				if (searchFields[i].xtype == 'combo' || searchFields[i].xtype == 'ux-treecombo') {
					tool = Ext.get(searchFields[i].hiddenId).dom;
				}
				else {
					tool = Ext.get(searchFields[i].id).dom;
				}
				tool.value = '';
			}
		}
	}
}); // eo extend 
//eof
