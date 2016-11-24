Ext.ns('HurryApp');

/**
 * Load an image and mask a panel during loading
 * @param image the image to load (dom element)
 * @param url the url of the image
 * @param imagePanelId the id of the panel to mask during loading
 */
HurryApp.ImageLoader = function(image, url, imagePanelId) {
	var imageTmp;
	var imageLoadingMask = new Ext.LoadMask(Ext.get(imagePanelId));

	return {
		load: function(params) {
			imageLoadingMask.show();
			params['timestamp'] = HurryApp.Utils.getTimestamp();
			imageTmp = new Image();
			imageTmp.src = url+'?'+Ext.urlEncode(params);
			window['imageLoader'+imagePanelId] = this;
			this.checkImageLoading();
		},

		checkImageLoading: function() {
			if (imageTmp && imageTmp.complete != false ){	
				image.src = imageTmp.src;
				imageLoadingMask.hide();
				imageTmp = null;
			}
			else{
				setTimeout('imageLoader'+imagePanelId+'.checkImageLoading()', 500);
			}
		}
	}
}
