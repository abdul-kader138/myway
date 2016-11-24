<?php



if( isset( $_GET[ 'value' ] ) )
{
	$path = "../project-exports/" . $_GET[ 'value' ];
	
	if( is_dir( $path ) )
	{
		header("HTTP/1.0 200");
	}
	else
	{
		header("HTTP/1.0 403");
	}
}

exit(0);

?>