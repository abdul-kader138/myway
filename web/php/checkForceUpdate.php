<?php

if( isset( $_GET[ "key_id" ] ) && isset( $_GET[ "date" ] ) )
{
	$key_id = $_GET[ "key_id" ];
	$date 	= $_GET[ "date" ];
	
	$projectFile = "../project-exports/" . $key_id . "/project.xml";
	
	if ( file_exists( $projectFile ) )
	{
		$xml = simplexml_load_string( file_get_contents($projectFile) );
		$force 			= $xml->{"force_update"};
		$project_date 	= $xml->{"creation_date"};
		
		if( $force == "true" && $project_date != $date )
		{
			echo "true";
		}
		else
		{
			echo "false";
		}
	}
	else
	{
		echo "false";
	}
}

?>