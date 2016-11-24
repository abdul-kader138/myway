Ext.namespace('HurryApp');

//-----------------------------------------------------------------------------
// Pad panel
//-----------------------------------------------------------------------------
/**
 * @class HurryApp.PadPanel
 * @extends Ext.Panel
 * Config options :
 *   url: String
 *   fields: Array
 *   itemTemplate: String
 *   columns: Integer
 *   rows: Integer
 *   onClickLeaf: Function
 */
HurryApp.PadPanel = Ext.extend(Ext.Panel, {
	// superclass properties

	// class properties
	store: null,
	jsonData: null,
	jsonDataSelected: null,
	carousel: null,
	dataView: null,
	displayNavBar: null,
	displayCarousel: null,
	nbCarouselItems: null,
	heightCarousel: null,
	widthCarouselNav: null,
	minWidthCarouselItem: null,
	buttonSize: null,
	heightPadItem: null,
	minWidthPadItem: null,
	heightNavBar: null,
	columns: null,
	rows: null,
	startItem: null,
	loadOnStartup: null,
	
	initComponent: function() {
		var padPanel = this;
		this.jsonDataSelected = new Array();
		this.displayNavBar = this.initialConfig.displayNavBar == true ? true : false;
		this.displayCarousel = this.initialConfig.displayCarousel == true ? true : false;
		this.levelUp = this.initialConfig.displayCarousel == true ? 2 : 1;
		//this.nbCarouselItems = this.initialConfig.nbCarouselItems ? this.initialConfig.nbCarouselItems : 6;
		this.heightCarousel = this.initialConfig.heightCarousel ? this.initialConfig.heightCarousel : 40;
		this.widthCarouselNav = this.initialConfig.widthCarouselNav ? this.initialConfig.widthCarouselNav : 40;
		this.minWidthCarouselItem = this.initialConfig.minWidthCarouselItem ? this.initialConfig.minWidthCarouselItem : 80;
		this.buttonSize = this.initialConfig.buttonSize ? this.initialConfig.buttonSize : 'large';
		this.heightPadItem = this.buttonSize == 'large' ? 75 : (this.buttonSize == 'medium' ? 39 : 20);
		this.minWidthPadItem = 130;
		this.heightNavBar = this.displayNavBar == true ? 40 : 0;
		//this.columns = this.initialConfig.columns ? this.initialConfig.columns : 6;
		//this.rows = this.initialConfig.rows ? this.initialConfig.rows : (this.initialConfig.height ? Math.floor((this.initialConfig.height - (this.heightNavBar+2) - (this.heightCarousel+2)) / (this.heightPadItem+2)) : 6);
		this.startItem = 0;
		this.loadOnStartup = this.initialConfig.loadOnStartup == false ? false : true;

		// Store
		this.store = new Ext.data.JsonStore({
			url: this.initialConfig.url,
			root: 'datas',
			autoLoad: false,
			fields: this.initialConfig.fields
		});
		
		// Template
		var tpl = this.getXTemplate();
		
		var itemSelector = 'div.ha-pad-panel-item-'+this.buttonSize;
		
		var cls = this.initialConfig.cls;
		if (!cls) {
			cls = 'ha-pad-panel';
		}
		
		var overClass = this.initialConfig.overClass;
		if (!overClass) {
			overClass = 'ha-over';
		}
		
		var selectedClass = this.initialConfig.selectedClass;
		if (!selectedClass) {
			selectedClass = 'ha-selected';
		}
		
		this.dataView = new Ext.DataView({
			cls: cls,
			tpl: tpl,
			itemSelector: itemSelector,
			overClass: overClass,
			selectedClass: selectedClass,
			singleSelect: true,
			store: new Ext.data.JsonStore({
				autoLoad: false,
				fields: this.initialConfig.fields
			}),
			listeners: {
			}
		});
		
		//this.on('afterrender', function(panel) {
		//	if (padPanel.loadOnStartup) {
		//		padPanel.createCarousel();
		//		padPanel.loadDatastore();
		//	}
		//});
		
		var items = [
			new Ext.Panel({
				html: this.getHtmlCarousel(),
				border: false,
				listeners: {'afterrender': function(panel) {
					if (padPanel.loadOnStartup) {
						if (padPanel.displayCarousel) {
							padPanel.createCarousel();
						}
						padPanel.loadDatastore();
					}
				}
			}}),
			this.dataView
		];
		
		if (this.displayNavBar) {
			items = items.concat(new Ext.Panel({
				html: this.getHtmlNavigationBar(),
				border: false
			}));
		}

		// Apply properties
		Ext.apply(this, {
			items: items
		}); // eo apply 
		
		// call parent 
		HurryApp.PadPanel.superclass.initComponent.apply(this, arguments); 

		// Refresh on resize
		var onResizeFn = function() {
			var i = 1;
			this.on('resize', function() {
				this.refresh();
				this.un('afterlayout', onResizeFn);
			});
		}
		this.on('afterlayout', onResizeFn);
	}, // eo function initComponent 
	
	getXTemplate: function() {
		return new Ext.XTemplate(
			'<table width="100%" cellspacing="1"><tbody>' +
			'<tpl for=".">' +
				'<tpl if="xindex % '+this.columns+' === 1">' +
					'<tr>' +
				'</tpl>' +
				'<td width="'+(100/this.columns)+'%"><div class="ha-pad-panel-item'+(this.buttonSize ? '-'+this.buttonSize : '')+' {color}" onclick="Ext.getCmp(\''+this.initialConfig.id+'\').onClick({id}, {leaf});">' +
					'<table cellspacing="0" border="0" width="100%" onMouseDown="HurryApp.ButtonHandler.buttonPressed(this);" onMouseUp="HurryApp.ButtonHandler.buttonReleased(this);" onMouseOut="HurryApp.ButtonHandler.buttonReleased(this);"><tbody><tr>' +
						'<td class="ha-left"/>' +
						'<td class="ha-center">' +
							this.initialConfig.itemTemplate +
						'</td>' +
						'<td class="ha-right"/>' +
					'</tr></tbody></table>' +
				'</div></td>' +
				'<tpl if="xindex % '+this.columns+' === '+this.columns+'">' +
					'</tr>' +
				'</tpl>' +
			'</tpl>' +
			'</tbody></table>');	
	},
	
	getHtmlNavigationBar: function() {
		var navBar =
			'<table width="100%"><tbody>' +
				'<tr>' +
				'<td width="33%"><div class="ha-pad-panel-page" onclick="Ext.getCmp(\''+this.initialConfig.id+'\').previousPage();">' +
				'<table id="pagePrevious'+this.initialConfig.id+'" cellspacing="0" border="0" width="100%" align="center" class="ha-disabled"><tbody><tr>' +
					'<td class="ha-left"/>' +
					'<td class="ha-center">' +
						'<table align="center"><tbody><tr><td class="ha-previous"/></tr></tbody></table>' +
					'</td>' +
					'<td class="ha-right"/>' +
				'</tr></tbody></table>' +
				'</div></td>' +
				'<td width="33%"><div class="ha-pad-panel-page" onclick="Ext.getCmp(\''+this.initialConfig.id+'\').upPage();">' +
				'<table id="navUp'+this.initialConfig.id+'" cellspacing="0" border="0" width="100%" class="ha-disabled"><tbody><tr>' +
					'<td class="ha-left"/>' +
					'<td class="ha-center">' +
						'<table align="center"><tbody><tr><td class="ha-up"/></tr></tbody></table>' +
					'</td>' +
					'<td class="ha-right"/>' +
				'</tr></tbody></table>' +
				'</div></td>' +
				'<td width="33%"><div class="ha-pad-panel-page" onclick="Ext.getCmp(\''+this.initialConfig.id+'\').nextPage();">' +
				'<table id="pageNext'+this.initialConfig.id+'" cellspacing="0" border="0" width="100%" class="ha-disabled"><tbody><tr>' +
					'<td class="ha-left"/>' +
					'<td class="ha-center">' +
						'<table align="center"><tbody><tr><td class="ha-next"/></tr></tbody></table>' +
					'</td>' +
					'<td class="ha-right"/>' +
				'</tr></tbody></table>' +
				'</div></td>' +
				'</tr>' +
			'</tbody></table>';
			
		return navBar;
	},
	
	getHtmlCarousel: function() {
		var navBar =
			'<div id="carousel-'+this.initialConfig.id+'" class="ha-pad-panel-carousel">' +
				'<table cellspacing="0" border="0" width="100%"><tbody><tr>' +
					'<td><div id="prev-arrow-'+this.initialConfig.id+'" class="ha-previous"></div></td>' +
					'<td>' +
						'<div class="carousel-clip-region">' +
							'<ul class="carousel-list">' +
							'</ul>' +
						'</div>' +
					'</td>' +
					'<td><div id="next-arrow-'+this.initialConfig.id+'" class="ha-next"></div></td>' +
				'</tr></tbody></table>' +
			'</div>';
		return navBar;
	},
	
	//-------------------------------------------------------------------------
	// Charge les données du panel
	//-------------------------------------------------------------------------
	loadDatastore: function() {
		var padPanel = this;
		if (this.initialConfig.url) {
			this.store.load({
				callback: function() {
					this.startItem = 0;
					padPanel.jsonData = HurryApp.Utils.toJsonDatas(padPanel.store.data.items);
					padPanel.refresh();
				}	
			});
		}
		else if (this.initialConfig.data) {
			this.startItem = 0;
			padPanel.jsonData = this.initialConfig.data;
			if (!this.displayCarousel) {
				this.jsonDataSelected.push(padPanel.jsonData);
			}
			padPanel.refresh();
		}
	},
	
	//-------------------------------------------------------------------------
	// Rafraichi le carousel et la dataView
	//-------------------------------------------------------------------------
	refresh: function() {
		this.nbCarouselItems = this.initialConfig.nbCarouselItems ? this.initialConfig.nbCarouselItems : Math.floor((this.getWidth() - this.widthCarouselNav*2) / this.minWidthCarouselItem);
		this.columns = this.initialConfig.columns ? this.initialConfig.columns : Math.floor(this.getWidth() / this.minWidthPadItem);
		this.rows = this.initialConfig.rows ? this.initialConfig.rows : (Math.floor((this.getHeight() - (this.heightNavBar+2) - (this.heightCarousel+2)) / (this.heightPadItem+1)));
		
		//this.setHeight(this.rows*72 + this.heightNavBar + 2 + this.heightCarousel);
		
		this.dataView.tpl = this.getXTemplate();
		if (this.carousel) {
			this.carousel.setProperty("numVisible", this.nbCarouselItems);
			this.refreshCarousel(this.jsonData);
		}
		
		if (this.jsonDataSelected.length > 0) {
			this.refreshDataView(this.jsonDataSelected[this.jsonDataSelected.length-1]);
		}
		else {
			this.refreshDataView([]);
		}
	},

	//-------------------------------------------------------------------------
	// Action réalisé lors d'un click sur un item
	//-------------------------------------------------------------------------
	onClick: function(nodeId, isLeaf) {
		var node = this.getNode(nodeId, isLeaf);
		if (this.initialConfig.onClickItem) {
			this.initialConfig.onClickItem.call(this, node);
		}
		else {
			if (!isLeaf) {
				this.startItem = 0;
				this.refreshDataView(node.children);
			}
			else {
				if (this.initialConfig.onClickLeaf) {
					this.initialConfig.onClickLeaf.call(this, node);
				}
			}
		}
	},
	
	//-------------------------------------------------------------------------
	// Action réalisé lors d'un click sur un élément du carousel
	//-------------------------------------------------------------------------
	onCarouselClick: function(nodeId, isLeaf) {
		if (!isLeaf) {
			this.jsonDataSelected = new Array();
			this.jsonDataSelected.push([]);
			this.onClick(nodeId, isLeaf);
		}
	},
	
	//-------------------------------------------------------------------------
	// Page précédente
	//-------------------------------------------------------------------------
	previousPage: function() {
		if (this.startItem > 0) {
			this.startItem -= this.columns*this.rows;
			this.refreshDataView(this.jsonDataSelected.pop());
		}
	},
	
	//-------------------------------------------------------------------------
	// Page suivante
	//-------------------------------------------------------------------------
	nextPage: function() {
		if (this.startItem + this.columns * this.rows < this.jsonDataSelected[this.jsonDataSelected.length - 1].length) {
			this.startItem += this.columns*this.rows;
			this.refreshDataView(this.jsonDataSelected.pop());
		}	
	},
	
	//-------------------------------------------------------------------------
	// Page supérieure
	//-------------------------------------------------------------------------
	upPage: function() {
		this.startItem = 0;
		if (this.jsonDataSelected.length > this.levelUp) {
			this.jsonDataSelected.pop();
			this.refreshDataView(this.jsonDataSelected.pop());
		}
	},
	
	//-------------------------------------------------------------------------
	// Raffraichi la vue avec les données
	//-------------------------------------------------------------------------
	refreshDataView: function(jsonData) {
		this.jsonDataSelected.push(jsonData);
		var newDatas = new Array();
		for (var i=0; i<jsonData.length; i++) {
			if (i >= this.startItem && i < this.startItem+this.columns*this.rows)
			newDatas[newDatas.length] = jsonData[i];
		};
		
		for (var i=newDatas.length; i<this.columns*this.rows; i++) {
			newDatas[newDatas.length] = {libelle: '&nbsp;'};
		};
		
		this.dataView.store.loadData(newDatas);
		
		this.refreshNavBar();
	},

	//-------------------------------------------------------------------------
	// Raffraichi la vue avec les données
	//-------------------------------------------------------------------------
	refreshNavBar: function() {
		var prevButton = Ext.get('pagePrevious'+this.initialConfig.id);
		if (prevButton != null) {
			if (this.startItem <= 0) {
				prevButton.addClass('ha-disabled');
			}
			else {
				prevButton.removeClass('ha-disabled');
			}
		}
		
		var nextButton = Ext.get('pageNext'+this.initialConfig.id);
		if (nextButton != null) {
			if (this.startItem+this.columns*this.rows >= this.jsonDataSelected[this.jsonDataSelected.length-1].length) {
				nextButton.addClass('ha-disabled');
			}
			else {
				nextButton.removeClass('ha-disabled');
			}
		}
		
		var upButton = Ext.get('navUp'+this.initialConfig.id);
		if (upButton != null) {
			if (this.jsonDataSelected.length < this.levelUp+1) {
				upButton.addClass('ha-disabled');
			}
			else {
				upButton.removeClass('ha-disabled');
			}
		}
	},

	//-------------------------------------------------------------------------
	// Renvoie les enfants d'un noeud
	//-------------------------------------------------------------------------
	getNode: function(nodeId, isLeaf) {
		var node;
		var stack = new Array();
		for (var i=0; i<this.jsonData.length; i++) {
			stack.push(this.jsonData[i]);
			while (stack.length > 0) {
				node = stack.pop();
				if (node.id == nodeId && node.leaf == isLeaf) {
					return node;
				}
				if (node.children) {
					for (var j=0; j<node.children.length; j++) {
						stack.push(node.children[j])
					}
				}
			}
		}
		return null;	
	},
	
	//-------------------------------------------------------------------------
	// Crée le carousel
	//-------------------------------------------------------------------------
	createCarousel: function() {
		this.carousel = new YAHOO.extension.Carousel('carousel-'+this.initialConfig.id, 
			{
				numVisible:      this.nbCarouselItems,
				animationSpeed:  0.2,
				animationMethod: YAHOO.util.Easing.easeBoth,
				scrollInc:       1,
				navMargin:       this.widthCarouselNav,
				loadOnStart:            false,
				//loadInitHandler:        panel.loadCarousel,
				prevButtonStateHandler: this.handlePrevButtonState,
				nextButtonStateHandler: this.handleNextButtonState,
				prevElement:            'prev-arrow-'+this.initialConfig.id,
				nextElement:            'next-arrow-'+this.initialConfig.id
			}
		);
	},

	//-------------------------------------------------------------------------
	// Raffraichi le carousel avec les données
	//-------------------------------------------------------------------------
	refreshCarousel: function(jsonData) {
		this.carousel.setProperty("size", jsonData.length);
		var itemWidth = Math.floor((this.getWidth() - 2*this.widthCarouselNav - this.nbCarouselItems*4 - 2) / this.nbCarouselItems);
		for (var i = 1; i < jsonData.length+1; i++) {
			this.carousel.addItem(i,
				'<div onclick="Ext.getCmp(\''+this.initialConfig.id+'\').onCarouselClick('+jsonData[i-1].id+', '+jsonData[i-1].leaf+');">' +
				'<table cellspacing="0" border="0" width="100%" onclick="Ext.getCmp(\''+this.initialConfig.id+'\').clearSelectionCarousel(); HurryApp.ButtonHandler.buttonSelected(this);"><tbody><tr>' +
					'<td class="ha-left"/><td class="ha-center">' + jsonData[i-1].libelle + '</td><td class="ha-right"/>' +
				'</tr></tbody></table>' +
				'</div>',
				null, itemWidth);
		}
	},
	
	//-------------------------------------------------------------------------
	// Raffraichi le carousel avec les données
	//-------------------------------------------------------------------------
	clearSelectionCarousel: function() {
		var selectedElements = document.getElementsByClassName('ha-selected-left');
		for (var i=0; i<selectedElements.length; i++) {
			selectedElements[i].className = 'ha-left';
		}		
		
		selectedElements = document.getElementsByClassName('ha-selected-center');
		for (var i=0; i<selectedElements.length; i++) {
			selectedElements[i].className = 'ha-center';
		}		

		selectedElements = document.getElementsByClassName('ha-selected-right');
		for (var i=0; i<selectedElements.length; i++) {
			selectedElements[i].className = 'ha-right';
		}		
	},
	
	//-------------------------------------------------------------------------
	// Gestion du bouton "prev"
	//-------------------------------------------------------------------------
	handlePrevButtonState: function(type, args) {
		var enabling = args[0];
		var leftImage = args[1];
		if(enabling) {
			leftImage.className = 'ha-previous';		
		} else {
			leftImage.className = 'ha-previous-disabled';		
		}
	},
	
	//-------------------------------------------------------------------------
	// Gestion du bouton "next"
	//-------------------------------------------------------------------------
	handleNextButtonState: function(type, args) {
		var enabling = args[0];
		var rightImage = args[1];
		if(enabling) {
			rightImage.className = 'ha-next';		
		} else {
			rightImage.className = 'ha-next-disabled';		
		}
	}
}); // eo extend 
