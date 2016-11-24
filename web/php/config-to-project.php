<?php
echo "hello";

	ini_set("memory_limit", "-1");
error_reporting(E_ALL);
ini_set('display_errors', 1);
	set_time_limit(0);
	$host = 'localhost';
	$user = 'root';
	$pass = 'rmg2013sotouch';
	$database = 'myway';
	date_default_timezone_set('America/Chicago');
	echo date("Y-m-d H:i:s") . " Start\r\n";	
	$mtime = microtime();
	$time = time();

	try { 
		$db = new PDO("mysql:host=$host;dbname=$database;charset=utf8", $user, $pass);
		$project_id = $argv[1];
		if (empty($project_id)) {
			die("no project id");
		}

		$sql = "select * from project where pro_id = $project_id";
		$projects = $db->query($sql);
		$sql = "select * from license where pro_id = $project_id";
		$terminals = $db->query($sql);

		$mapItems = "<map_items>";
		$accesses = array();
		$access_hold = $db->query("select * from item i left join item_type it on i.itt_id = it.itt_id where i.itt_id = 2 and pro_id = $project_id");
		foreach($access_hold as $access) {
			$accesses[] = $access['ITE_ID'];
		}

		foreach($projects as $project) {
			$str = $project['PRO_MAP_CONFIG'];	
			$xml = simplexml_load_string($str);
			$items = $xml->xpath("//planable[@type='item']");
			
			echo count($items) . " number of items\n";

			$paths = array();
			$allFloors = array();
			$str = "<map_items>";
			
file_put_contents('map_items_test.xml', $str, FILE_APPEND | LOCK_EX);

			foreach($items as $item) { 

			   $destFloorId = $item->xpath('parent::*');
			   $destFloorId = $destFloorId[0]; 
			   $destFloorId = $destFloorId->xpath('parent::*'); 
			   $destFloorId = (string)($destFloorId[0]['id']);
			   $id = $item['id'];
			   $itemId = $item->item['id'];
			   $types = $db->query("select * from item i left join item_type it on i.itt_id = it.itt_id where ite_id = $itemId");
			   $type = "";
			   foreach($types as $t) {
				$type = strtolower($t['ITT_NAME']);
			   }
		           if ($type == "facil.util.trans.other") {
				$type = "futos";
			   }

		     	   $x = $item->position['x'];
		     	   $y = $item->position['y'];

			   $str = "<point type=\"$type\" id=\"$id\" itemId=\"$itemId\"><position>$x,$y</position><paths>";
			   foreach($terminals as $terminal) {  print_r($terminal);
				$term = $xml->xpath("//planable[@type='item']/item[@id='" . $terminal['LIC_ID'] . "']");
				print_r($term);
				if (empty($term)) {
					continue;
				}
				$term = $term[0]->xpath( 'parent::*' );
				$term = $term[0];
				$floorId = $term->xpath( 'parent::*' );
				$floorId = $floorId[0]->xpath('parent::*');
				$floorId = (string)$floorId[0]['id']; 
				$str .= "<terminal id=\"" . $term['id'] . "\" ><path type=\"shortest\">";
				echo date("Y-m-d H:i:s") . " floor ids $floorId $destFloorId\n\n";  
				$lasts = array();
				$floors = array();
				$node = $term;
				$distance = 0;
				$cnt = 1;
				
				while (!in_array($floorId,$floors) && !in_array(((string)$node['id']), $lasts) && !empty($floorId) && !empty($node)) {
				  $nodeId = (string)$node['id'];
				  echo date("Y-m-d H:i:s") . " floor=$floorId $cnt row $nodeId\n";
				  $cnt++;
				  if ($floorId == $destFloorId) {
					$path = findPath($db, $paths, $allFloors, $accesses, $node, $floorId, $destFloorId, $floors, $xml, $lasts, $distance, "shortest", $id);
					$paths[$nodeId] = $path;
					$str .= $path['path'];
					echo "better have found!\n";
					break;
				  } else if (array_key_exists($nodeId, $paths)) {
					echo "using already $nodeId\n"; 
					$path = $paths[$nodeId];
				  } else {
					$path = findPath($db, $paths, $allFloors, $accesses, $node, $floorId, $destFloorId, $floors, $xml, $lasts, $distance, "shortest", 0);
				  }
				  echo "\n" . date("Y-m-d H:i:s") . " floor=$floorId $cnt row $nodeId DONE\n";
				  $lasts[] = $nodeId;
				  $floors[] = $floorId;
				  if (!in_array($floorId, $allFloors)) {
				    $allFloors[] = $floorId;
				  }
				  $paths[$nodeId] = $path;
				  $str .= $path['path'];
			          $node = $xml->xpath("//floor[@id='" . $floorId . "']/planables/planable[@id='" . $path['end'] . "']");
				  $node = $node[0];
				  $node = $xml->xpath("//floor[@id='" . $path['floor'] . "']/planables/planable[@type='item']/item[@id='" . $node->item['id'] . "']");
				  $node = $node[0]->xpath( 'parent::*' );
				  $node = $node[0];
				  $floorId = $path['floor'];
			          $distance = $path['distance'];
   				  echo "myPath\n";
				  print_r($path);
				  echo "next floor $floorId\n";
				  print_r($node);
				  echo date("Y-m-d H:i:s") . " current string=$str\n";
				} 
				$str .=  "</path></terminal>";
			   echo $str . "=strAfterTerminal\n"; 
			   }
			   $str .= "</paths></point>\r\n";
			   echo $str . "=strAfterPoint\n";
			file_put_contents('map_items_test.xml', $str, FILE_APPEND | LOCK_EX);
			}
			$str = "</map_items>";
			file_put_contents('map_items_test.xml', $str, FILE_APPEND | LOCK_EX); 
		} 

	} catch (Exception $ex) {
		print_r($ex);
	}
	$mtime2 = microtime();
	$time2 = time();
	echo $time2 - $time . " number of seconds to run \n";
	echo $mtime2 - $mtime . " number of microseconds to run \n"; 


// Return distance, end_planable_id, path, current_floor, error
function findPath($db, $oldPaths, $allFloors, $accesses, $start, $floor, $floorDest, $floors, $xml, $last=array(), $distance=0, $type="shortest", $end=0) {
				$str = "<floor id=\"" . $floor . "\" start=\"" . $start['id'] . "\" end=\"";
				$starts = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable/start[@id='" . $start['id'] . "']");
				$ends = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable/end[@id='" . $start['id'] . "']");
				$error = null;
				$path = "";
				$paths = array();
				foreach ($starts as $point) {
				  $last = array();
				  $last[] = (string)$start['id'];
				  $plan = $point->xpath('parent::*');
				  $plan = $plan[0];
				  $endId = (string)$plan->end['id'];
				if ($endId == $end) {
				   return array(array('distance'=>$distance, 'path'=> $str.$endId."\"></floor>", 'end'=>((string)$start['id']), 'floor'=>$floor));
				}

			          $check = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable[@id='" . $endId . "']");
				  if (is_array($check)) {
				    $check = $check[0];
				  } else if (empty($check)) {
				    echo "empty $endId \n";
				    continue;
				  }
				  
				  $type = (string)$check['type'];
			     	   $x = $check->position['x'];
			     	   $y = $check->position['y'];
					echo date("Y-m-d H:i:s") . " find check starts $endId $type ";
				  if ($type == 'point') {
				  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
				  } else {
					  $itemId = $check->item['id'];
					   if (in_array($itemId, $accesses)) {
						$access = $xml->xpath("//planable[@type='item']/item[@id='" . $itemId . "']");
						$found = false;
						$possibles = array();
						foreach($access as $a) {
							$aFloorId = $a->xpath( 'parent::*' );
							$aFloorId = $aFloorId[0]->xpath( 'parent::*' );
							$aFloorId = $aFloorId[0]->xpath('parent::*');
							$aFloorId = (string)$aFloorId[0]['id'];
							if (!empty($aFloorId) && $floor != $aFloorId) {
								$found = true;
				   			   	$paths[] = array(array('distance'=>$distance, 'path'=> "$x,$y", 'end'=>$endId, 'floor'=>$aFloorId));
							}
						}
						if (!$found) {
						  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
						} 
					   } else {
					  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
					   }

				  }
				}
				foreach ($ends as $point) {
				  $plan = $point->xpath('parent::*');
				  $plan = $plan[0];
				  $startId = (string)$plan->start['id'];
			          $check = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable[@id='" . $startId . "']");
				  if (is_array($check)) {
				    $check = $check[0];
				  } else if (empty($check)) {
				    echo "empty $endId \n";
				    continue;
				  }
				  
				  $type = (string)$check['type'];
			     	   $x = $check->position['x'];
			     	   $y = $check->position['y'];
			     	   
	     	   echo date("Y-m-d H:i:s") . " find check ends $startId $type ";

				  if ($type == 'point') {
				  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
				  } else {
					  $itemId = $check->item['id'];
					   if (in_array($itemId, $accesses)) {
						$access = $xml->xpath("//planable[@type='item']/item[@id='" . $itemId . "']");
						$found = false;
						$possibles = array();
						foreach($access as $a) {
							$aFloorId = $a->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath('parent::*');
							$aFloorId =(string)$aFloorId[0]['id'];
							if (!empty($aFloorId) && $floor != $aFloorId) {
								$found = true;
				   			   	$path[] = array(array('distance'=>$distance, 'path'=> "$x,$y", 'end'=>$endId, 'floor'=>$aFloorId));
							}
						}
						if (!$found) {
						  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
						} 
					   } else {
					  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
					   }

					   $itemId = $check->item['id'];
					   $types = $db->query("select * from item i left join item_type it on i.itt_id = it.itt_id where ite_id = $itemId");
			   		   $type = "";
			   	           foreach($types as $t) {
						$type = strtolower($t['ITT_NAME']);
			   		   }
		           		   if ($type == "facil.util.trans.other") {
						$type = "futos";
			   		   }
					   echo "find path type startid $itemId $type\n";
					   if ($type == "access") {
		   			   	$paths[] = array(array('distance'=>$distance, 'path'=> "$x,$y", 'end'=>$startId));
					   } else {
					  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, "$x,$y,", $end);
					   }
				  }
				}
				$shortestIndex = 0;
				$index = 0;
				$myPath = $str.$paths[0][0]['end']."\">" . substr($paths[0][0]['path'], 0, strlen($paths[0][0]['path'])-1) . "</floor>";
				$myDistance = $paths[0][0]['distance'];
				$myFloor = $paths[0][0]['floor'];
				$myEnd = $paths[0][0]['end'];
				$found = false;
				foreach ($paths as $aPath) {
				 if (is_array($aPath)) {
				$shortestIndex = 0;
				$index = 0;
				   if ($found) {
					break;
				   }
				   foreach ($aPath as $p) {
					echo "checking myFloor " . $p['floor'] . " end=" . $p['end'] . " $myFloor=my ";
					$p['path'] = substr($p['path'], 0, strlen($p['path'])-1);
					if (in_array($p['floor'], $floors)) {
						echo "already been to floor ";
						continue;
					}
				     if ($p['end'] == $end) {
						$found = true;
				        	echo "foundit ";
						$myDistance = $p['distance'];
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
						break;
				     } else if ($p['floor'] != $myFloor) {
					   echo " not equal ";
					   if ($myFloor == $floorDest) {
						echo "already found floor ";
						continue;
					   } else if ((array_key_exists($p['end'], $oldPaths) 
						&& !array_key_exists($myEnd, $oldPaths))
						|| (in_array($p['floor'], $allFloors) && !in_array($myFloor, $allFloors))) {
						echo $p['floor'] . " " . $p['end'] . " key exists or in all floors";
						$myDistance = $p['distance'];
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
					   } else if (array_key_exists($p['end'], $oldPaths) 
							&& array_key_exists($myEnd, $oldPaths)
							&& $p['distance'] < $myDistance) {
						$myDistance = $p['distance'];
						echo $p['floor'] . " " . $p['end'] . " key exists in both ";
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
					   } else if (checkFloor($p['floor'],$oldPaths) && !checkFloor($myFloor, $oldPaths)) {
						echo "go to floor we been before ";
						$myDistance = $p['distance'];
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
					   } else if ( abs($floorDest - $p['floor']) < abs($floorDest - $myFloor)) {
						echo $p['floor'] . " " . $p['end'] . " doesn't key exists ";
						$myDistance = $p['distance'];
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
				   	   } else {
						echo " do nothing ";
				           }

				     } else if ( $p['distance'] < $myDistance) {
						echo " shorter distance ";
						$myDistance = $p['distance'];
						$myPath = $str.$p['end']."\">" . $p['path'] . "</floor>";
						$myFloor = $p['floor'];
						$myEnd = $p['end'];
				     }
				     $index++;
				   }
				 }
				}
				return array('distance' => $myDistance, 'path'=>$myPath, 'floor'=>$myFloor, 'end'=>$myEnd);
}

function recursion($db, $accesses, $floor, $start, $xml, $last, $distance, $path, $end) {

				if (((string)$start['id']) == $end) {
				   return array(array('distance'=>$distance, 'path'=> $path, 'end'=>((string)$start['id']), 'floor'=>$floor));
				}
				$starts = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable/start[@id='" . $start['id'] . "']");
				$ends = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable/end[@id='" . $start['id'] . "']");
				$last[] = (string)$start['id'];
$paths = array();

				foreach ($starts as $point) { 
				  $plan = $point->xpath('parent::*');
				   $plan = $plan[0];
				
				  $endId = (string)$plan->end['id'];
				  if (in_array($endId, $last)) {
					continue;
				  } 
				if ($endId == $end) {
				   $paths[] = array(array('distance'=>$distance, 'path'=> $path, 'end'=>$endId, 'floor'=>$floor));
				} 

			          $check = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable[@id='" . $endId . "']");
				  if (is_array($check)) {
				    $check = $check[0];
				  } else if (empty($check)) {
				    echo "empty $endId \n";
				    continue;
				  }
				  
				  $type = (string)$check['type'];
			     	   $x = $check->position['x'];
			     	   $y = $check->position['y'];
				  if ($type == 'point') {
				  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
				  } else {
					  $itemId = $check->item['id'];
					   if (in_array($itemId, $accesses)) {
						$access = $xml->xpath("//planable[@type='item']/item[@id='" . $itemId . "']");
						$found = false;
						$possibles = array();
						foreach($access as $a) {
							$aFloorId = $a->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath('parent::*');
							$aFloorId =(string)$aFloorId[0]['id'];

							if (!empty($aFloorId) && $floor != $aFloorId) {
								$found = true;
				   			   	$paths[] = array(array('distance'=>$distance, 'path'=> $path, 'end'=>$endId, 'floor'=>$aFloorId));
							}
						}
						if (!$found) {
						  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
						} 

					   } else {
					  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
					   }
				  } 
				}
				foreach ($ends as $point) {
				  $plan = $point->xpath('parent::*');
				  $plan = $plan[0];
				  $startId = (string)$plan->start['id'];
				  if (in_array($startId, $last)) {
					continue;
				  }
				if ($startId == $end) {
				   $paths[] = array(array('distance'=>$distance, 'path'=> $path, 'end'=>$startId, 'floor'=>$floor));
				   break;
				}
			          $check = $xml->xpath("//floor[@id='" . $floor . "']/planables/planable[@id='" . $startId . "']");
				  if (is_array($check)) {
				    $check = $check[0];
				  } else if (empty($check)) {
				    echo "empty $endId \n";
				    continue;
				  }
				  
				  $type = (string)$check['type'];
			     	   $x = $check->position['x'];
			     	   $y = $check->position['y'];
				  if ($type == 'point') {
				  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
				  } else {
					  $itemId = $check->item['id'];
					   if (in_array($itemId, $accesses)) {
						$access = $xml->xpath("//planable[@type='item']/item[@id='" . $itemId . "']");
						$found = false;
						$possibles = array();
						foreach($access as $a) {
							$aFloorId = $a->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath( 'parent::*' );
							$aFloorId =$aFloorId[0]->xpath('parent::*');
							$aFloorId =(string)$aFloorId[0]['id'];
							if (!empty($aFloorId) && $floor != $aFloorId) {
								$found = true;
				   			   	$paths[] = array(array('distance'=>$distance, 'path'=> $path, 'end'=>$startId, 'floor'=>$aFloorId));
							}
						}
						if (!$found) {
						  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
						}
					   } else {
					  	$paths[] = recursion($db, $accesses, $floor, $check, $xml, $last, $distance+10, $path."$x,$y,", $end);
					   }
				  } 

				} 
				$shortestIndex = 0;
				$index = 0;
				$e = 0;
				$myDistance = $distance;
				$myPath = $path;
				$valids = array();
				foreach ($paths as $aPath) {
				  if (is_array($aPath)) {
					foreach ($aPath as $p) {
					  if (!empty($p['end'])) {
					    $valids[] = $p;
					  } 
					}
				  } else {
					echo "not array $aPath\n";
				  }
				}
				return count($valids) ? $valids : array(array());

} 

function checkFloor($floor, $oldPaths) {
  foreach ($oldPaths as $o) {
	if ($floor == $o['floor']) {
		echo " $floor is true ";
		return true;
	}
  }
  echo " $floor is false ";
  return false;
}


?>
