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
		$project_id = !empty($argv[1]) ? $argv[1] : 91;
		$file = !empty($argv[2]) ? $argv[2] : 'projectTest3.xml';
		$sql = "SELECT * FROM map_item where projectId = $project_id";
    	$doc=new DomDocument;
		try {
    		echo $sql . "\n";
    		$doc=new DomDocument;
    		$doc->Load($file);
    		echo date("Y-m-d H:i:s") . " done load \n";
    		
    		$root=$doc->documentElement;
    		$mapItems =$doc->createElement('map_items');
    		$cnt = 0;
    		foreach ($db->query($sql) as $row) {
    			echo "$cnt\n";
    			$fragment = $doc->createDocumentFragment();
    			$fragment->appendXML("<point type=\"". $row['type'] . "\" id=\"".$row['id']."\" itemId=\"".$row['itemId']."\">"
    					.(empty($row['position']) ? '': $row['position'])
    					.(empty($row['shape']) ? '': $row['shape'])
    					.(empty($row['containitems']) ? '': $row['containitems'])
    					.(empty($row['zonebelongto']) ? '': $row['zonebelongto'])
    					.(empty($row['paths']) ? '': $row['paths'])
    					."</point>\n");
    			$mapItems->appendChild($fragment);
    			$cnt++;
    		}
    		$root->appendChild($mapItems);
    		echo date("Y-m-d H:i:s") . " done append \n";
    		    		
    		$cnt = 0;
    	} catch (Exception $ex) {
    		print_r($ex);	
    	}
    	 
    	echo $doc->save($file);
    	echo date("Y-m-d H:i:s") . " done save \n";
    	 
	} catch (Exception $ex) {
		echo "An error occured: " . $ex->getMessage();
	}
    exit;
    
    
?>
