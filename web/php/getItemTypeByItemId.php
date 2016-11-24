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
	$itemId = $argv[2];
	if (empty($project_id) || empty($itemId)) {
		error_log("no ids");
		die(" ");
	}
	$types = $db->query("select * from item i left join item_type it on i.itt_id = it.itt_id where ite_id = $itemId");
	$type = "";
	foreach($types as $t) {
		$type = strtolower($t['ITT_NAME']);
	}
	if ($type == "facil.util.trans.other") {
		$type = "futos";
	}
	echo $type;
	
} catch (Exception $ex) {
		print_r($ex);
}

