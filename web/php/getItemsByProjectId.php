<?php
ini_set("memory_limit", "-1");
set_time_limit(0);
$host = 'localhost';
$user = 'root';
$pass = 'root';
$database = 'myway';
date_default_timezone_set('America/Chicago');
error_log(date("Y-m-d H:i:s") . " Start");
try {
	$db = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $pass);
	//$file = "C:/Users/david.paul/Downloads/Wayfinder_Export-all(11)/0fde5d36-c099-4d03-a37e-32012eb23890/project.xml";
	$projectId = !empty($argv[1]) ? $argv[1] : 91;
	$flag = !empty($argv[2]) ? true : false;
	if ($flag) {
		$sql = "SELECT DISTINCT ITE_ID, ITE_NAME, ITT_ID FROM item left join map_item on ITE_ID = itemId  WHERE PRO_ID = $projectId AND ite_id NOT IN (SELECT itemId FROM map_item WHERE projectId = $projectId);";
	} else {
		$sql = "SELECT DISTINCT ITE_ID, ITE_NAME, ITT_ID FROM item left join map_item on ITE_ID = itemId  WHERE PRO_ID = $projectId";
	}
	
	foreach ($db->query($sql) as $row) {
		$type = "";
		switch ($row['ITT_ID']) {
			case '1': $type = "Location"; break;
			case '2': $type = "Access"; break;
			case '3': $type = "F.U.T.O."; break;
			case '4': $type = "Webview"; break;
			case '7': $type = "Terminal"; break;
			default: $type = "Unknown"; break;
				
		}
		echo "<tr style='cursor:pointer;' onclick='showPath(\"" . $row['ITE_ID'] . "\"); return false;'><td>" . $row['ITE_ID'] . "</td>"
			. "<td>" .$row['ITE_NAME'] . "</td>"
			. "<td>" . $type . "</td></tr>\r\n";
	}
} catch (Exception $ex) {
	error_log(print_r($ex, true));
}

?>