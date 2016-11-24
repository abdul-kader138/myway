<?php
ini_set("memory_limit", "-1");
set_time_limit(0);
$host = 'localhost';
$user = 'root';
$pass = 'root';
$database = 'myway';
date_default_timezone_set('America/Chicago');
echo date("Y-m-d H:i:s") . " Start \n";
try {
	$db = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $pass);
	//$file = "C:/Users/david.paul/Downloads/Wayfinder_Export-all(11)/0fde5d36-c099-4d03-a37e-32012eb23890/project.xml";
	$itemId = !empty($argv[1]) ? $argv[1] : 91;
	$file = !empty($argv[2]) ? $argv[2] : 'projectTest3.xml';
	$sql = "SELECT * FROM map_item where itemId = $itemId";
	foreach ($db->query($sql) as $row) {
	}
} catch (Exception $ex) {
	
}

?>