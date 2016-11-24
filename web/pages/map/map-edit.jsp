<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/tld/hurryapp.tld" prefix="h" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="layout-app-project">
	<tiles:putAttribute name="header" type="string">
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<!-- Latest compiled and minified CSS -->

		<script type="text/javascript">
		// Map Path editor
		$(document).ready(function () {
			$("#main-panel").html($("#mapEdit").html());
			$("#mapEdit").html("");
			
            $("#itemsTable").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
		function showPath(id) {
		    $.ajax({
		         type: "POST",
		         url: myAppContext+'/map/getMapItem?itemId='+id,
		         data: {'itemId':id},
		         dataType: 'text',
		         cache: false,
		         success: function(response) {
					$("#myPathDialog").html(response);
					$("#myPathDialog").dialog({
					    modal: true,
					    draggable: false,
					    resizable: false,
					    position: ['center', 'top'],
					    show: 'blind',
					    hide: 'blind',
					    width: 400,
					    dialogClass: 'ui-dialog-osx',
					    buttons: {
					        "Cancel": function() {
					            $(this).dialog("close");
					        }
					    }
					});
		         },
		         error: function(xhr) {
					alert('Error');
		         }
		     });
		}
		function savePath(id) {
		    $.ajax({
		         type: "POST",
		         url: myAppContext+'/map/getMapItem?itemId='+id,
		         data: {'itemId':id},
		         cache: false,
		         success: function(response) {
					alert(id);
					alert(response);
		         },
		         error: function(xhr) {
					alert('Error');
		         }
		     });
		}
		function cancelPath(id) {
			
		}
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="body" type="string">
		<div id="mapEdit">
		<div id="ext-comp-1002" class=" x-panel x-panel-noborder" >
		<div style="padding:5px;margin:5px;font-size:larger;font-weight:bold;">
			<h1>Click A Row To Select An Item</h1>
		</div>
		<div>
                <table id="itemsTable" class="display">
                    <thead>
                        <tr>
                            <th>Item Id</th>
                            <th>Item Name</th>
                            <th>Item Type</th>
                        </tr>
                    </thead>
                    <tbody>
						${items}	
                    </tbody>
                </table>
        </div> 
		<div style="padding:5px;margin:5px;">
    		<button onclick="showPath(0);">Add New Item</button>
    	</div>
    	<div id="myPathDialog" style="display:none;"></div>
    	</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
