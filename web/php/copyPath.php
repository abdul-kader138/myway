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
	$item_id = $argv[2];
	if (empty($project_id) || empty($item_id)) {
		die("");
	}
	$sql = "select * from project where pro_id = $project_id";
	$projects = $db->query($sql);
	foreach($projects as $project) {
		$str = $project['PRO_MAP_CONFIG'];
		$xml = simplexml_load_string($str);
		$items = $xml->xpath("//planable[@type='item']/item[@id='" . $item_id . "']");
		if (is_array($items) && count($items)) {
			$item = $items[0];
		} else {
			die("");
		}
		$str = "";
		$plannable = $item->xpath( 'parent::*' )[0];
		$planId = (string)$plannable['id'];
		$item = $item->xpath( 'parent::*' )[0]->xpath( 'parent::*' )[0]->xpath( 'parent::*' )[0];
		$floorId = (string)$item['id'];
		$sql = "select id, paths from map_item left join item on itemId = ite_id where paths like '%</floor><floor id=\"$floorId\" %';";
		$mapItems = $db->query($sql);
		$index = 0;
		$closest = 0;
		foreach ($mapItems as $row) {
			if (($row['id'] == $planId || (abs($planId - $closest['id']) > abs($row['id'] - $planId))) && stristr($row['paths'], 'end="' . $row['id'])!== false) {
				$closest = $row;
			}
			$index++;
		}
		$path = $closest['paths'];
		$anId = $closest['id'];
		$path = str_replace($anId, $planId, $path);		
		echo $path;
	}
} catch (Exception $ex) {
		print_r($ex);
}
	