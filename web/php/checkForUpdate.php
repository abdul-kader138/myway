<?php
date_default_timezone_set("UTC");
define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASS", "rmg2013sotouch");
define("DB_NAME", "myway");
define("DB_PORT", "3306");

$softwarePath = "uploads/";

if( isset( $_GET[ 'id' ] ) && isset( $_GET[ 'version' ] ) )
{
	$link = mysql_connect(DB_HOST . ":" . DB_PORT, DB_USER, DB_PASS);
	
	//or die("Can't connect MySql server!");
	mysql_select_db(DB_NAME) or die("Database is not available!");

	$strSql = "SELECT prm_libelle, prm_value FROM config_parameter WHERE prm_libelle = 'externalFilesPath' or  prm_libelle = 'softwarePath'";
	$result = mysql_query($strSql);
	while($row = mysql_fetch_object($result)){
	  if($row->prm_libelle == "externalFilesPath"){
		$externalFilesPath = $row->prm_value;
	  }else if($row->prm_libelle == "softwarePath"){
		$softwarePath = $row->prm_value;
	  }
	}
	
	$baseUrl = "http://ivs.rmgnetworks.com" . $softwarePath;
	$softwareFilesPath = $externalFilesPath . $softwarePath;
	if(!file_exists($softwareFilesPath)){
		die("Can't find the software path");
	}
	
	$vendor = isset($_GET['vendor']) ? $_GET['vendor'] : '';
	$version = $_GET['version'];
	
	$sql = "SELECT sof_version, sof_vendor, sof_installpack FROM software where sof_vendor = '$vendor' ORDER BY sof_modify_time DESC LIMIT 1";
	$result = mysql_query($sql);
	if($result){
	  $row = mysql_fetch_object($result);
	  if($row && checkVersion($version, $row->sof_version) && file_exists($softwareFilesPath . $row->sof_installpack)){
		echo "update---" .trim($row->sof_version). "---$baseUrl" . $row->sof_installpack;
		exit();
	  }
	  echo "noupdate";
	  exit();
	}
}
echo "noupdate";
exit();

function checkVersion($oldVersion, $newVersion){
	if(empty($oldVersion) || empty($newVersion)){
		return false;
	}
	$arrOldVersion = explode('.', $oldVersion);
	$arrNewVersion = explode('.', $newVersion);
	
	$bUpdate = false;
	if(sizeof($arrNewVersion) == sizeof($arrNewVersion)){
		$bUpldate = true;
		foreach($arrNewVersion as $key=> $nNewVersion){
		   if($nNewVersion > $arrOldVersion[$key]){
			 $bUpdate = true;
		   }
		}
	}
	return $bUpdate;
}
?>
