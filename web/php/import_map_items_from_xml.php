<?php

	ini_set("memory_limit", "-1");
	set_time_limit(0);
	$host = 'localhost';
	$user = 'root';
	$pass = 'root';
	$database = 'myway';
	date_default_timezone_set('America/Chicago');
	echo date("Y-m-d H:i:s") . " Start\r\n";	
	try {
		$db = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $pass);
		$db->exec("create table if not exists map_item (id int auto_increment, type varchar(100), itemId varchar(100), position varchar(100), shape text, containitems text, zonebelongto text, paths mediumtext, projectId int,primary key(id));");
		$sql = " create table floor_path (id int auto_increment, primary key(id), terminal_id int, point_id int, floor_id int, next_floor int, start_id int, end_id int, path text); ";
		$sql = "create table terminal_path (id int auto_increment, primary key(id), terminal_id int, point_id int, path text);";
		//$file = "C:/Users/david.paul/Downloads/Wayfinder_Export-all(11)/0fde5d36-c099-4d03-a37e-32012eb23890/project.xml";
		$file = !empty($argv[1]) ? $argv[1] : "projectTest.xml";
        $handle = fopen($file, "r") or die("Failed to open file");
        $str='';
        $size = 8192;
        while(!feof($handle))
        	$str.=fread($handle,$size);
        fclose($handle);
        $pos1 = stripos($str, "<map_items>");
        $mapItems =  substr($str, $pos1, stripos($str, "</map_items>")-($pos1-strlen("</map_items>")));
        $xml = simplexml_load_string($str) or die("Error: Cannot create object:" . print_r(libxml_get_errors(), true));
        $cnt = 0;
        $points = array();
        foreach($xml->{'map_items'}->point as $item) {
        	$row = recursion($item);
        	$prep = array();
        	$values = array();
        	foreach($row as $k => $v ) {
        		$prep[] = '?';
        		$values[] = $v;
        	}
        	$sql = "INSERT INTO map_item ( " . implode(", ",array_keys($row)) . ") VALUES (" . implode(', ',array_values($prep)) . ")";
        	$sth = $db->prepare($sql);
        	if (!$sth) {
        		echo "\r\nPDO::errorInfo():\r\n$sql\r\n" . print_r($db->errorInfo(), true) . " row=" . print_r($row, true);
        	}
        	
        	$res = $sth->execute($values);
        	if(!$res) {
        		echo "\r\nSQL error:$sql\r\n" . print_r($db->errorInfo(), true);
        		foreach ($values as $k=>$v) {
        			echo "   " . strlen($v) . "\r\n";
        		}
        	}
        	$cnt++;
        }
        echo "$cnt Records added\r\n";
		echo date("Y-m-d H:i:s") . " End1\r\n";	
	} catch (Exception $ex) {
		echo "An error occured: " . $ex->getMessage();
	}
    exit;
    

function recursion($node, $space = '', $project_id = '9') {
	$row = array();
	foreach($node->attributes() as $a => $b) {
		$row[$a] = (string)$b;
	}
	if (!empty($node->children())) {
		foreach ($node->children() as $child) {
			$row[$child->getName()] = $child->asXml();
		}
	}
	if (!isset($row['projectId'])) {
		$row['projectId'] = $project_id;
	}
	return $row;
}
    /*
     * mysql> create table map_item (id int auto_increment, type varchar(100), itemId varchar(100), position varchar(100), shape text, containitems text, zonebelongto text, paths mediumtext, projectId int,primary key(id));
     */
?>
