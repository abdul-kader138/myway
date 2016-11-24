<?php
ini_set("memory_limit", "-1");
set_time_limit(0);
$host = 'localhost';
$user = 'root';
$pass = 'root';
$database = 'myway';
date_default_timezone_set('America/Chicago');
$mtime = microtime();
$time = time();
try {
	$db = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $pass);
	$project_id = $argv[1];
	$id = $argv[2];
	if (empty($project_id) || empty($id)) {
		error_log("no ids");
		die(" ");
	}
	$sql = "select * from project where pro_id = $project_id";
	$projects = $db->query($sql);
	foreach($projects as $project) {
		$str = $project['PRO_MAP_CONFIG'];
		$xml = simplexml_load_string($str);
		$items = $xml->xpath("//planable[@id='" . $id . "']");
		if (is_array($items) && count($items)) {
			$item = $items[0];
		} else {
			error_log("no items");
			die(" ");
		}
		$str = " ";
		$plannable = $item;
		$planId = (string)$plannable['id'];

		echo $planId;
	}
} catch (Exception $ex) {
		print_r($ex);
}
	