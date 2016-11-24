Ext.ns('HurryApp');

/**
 * Load an "amChart"
 * @param chartType the chart type : 'amcolumn', 'amline', 'ampie', 'amradar', 'amxy', 'amstock'
 * @param width the chart width
 * @param height the chart height
 * @param urlSettings the url of the settings xml file
 * @param urlDatas the url of the datas xml file (or xml stream)
 * @param chartPanelId the id of the panel where the chart is rendered 
 */
HurryApp.AmChartLoader = function(
		chartType, width, height, backgroundColor,
		urlSettings, urlDatas, chartPanelId) {
	var so = new SWFObject(myAppContext+'/flash-charts/'+chartType+'/'+chartType+'/'+chartType+'.swf', chartPanelId, width, height, '8', backgroundColor);
	so.addVariable('path', myAppContext+'/flash-charts/'+chartType+'/'+chartType+'/');
	so.addVariable('settings_file', encodeURIComponent(urlSettings));
	//so.addVariable("chart_id", chartPanelId);	
	so.addVariable("preloader_color", "#999999");
	so.addParam("wmode", "opaque");

	return {
		load: function(params) {
			var url = urlDatas;
			if (params) {
				params['timestamp'] = HurryApp.Utils.getTimestamp();
				url += '?'+Ext.urlEncode(params);
			}
			so.addVariable('data_file', encodeURIComponent(url));
			so.write(chartPanelId);
		},
		
		getSWFObject: function() {
			return so;
		}
	}
}
