<?php
set_time_limit(0);
date_default_timezone_set("UTC");
if( isset( $_GET[ "key_id" ] ) )
{
	$key_id = $_GET[ "key_id" ];

	$configFile = "../project-exports/" . $key_id . "/Wayfinder_Export-" . date( "Y-m-d" ) . ".mwp";
	if ( file_exists( $configFile ) ) unlink( $configFile );

	$struct_filepath = array();
	$struct_filesize = array();
	
	if( isset( $_POST['struct'] ) )
	{
		$struct_xml = @simplexml_load_string( $_POST['struct'] );
		
		if( $struct_xml && $struct_xml->children() )
		{
			$i = 0;
			foreach ($struct_xml as $file)
			{
				$struct_filepath[ $i ] = urldecode($file['path']);
				$struct_filesize[ urldecode($file['path']) ] = urldecode($file['filesize']) ;
				$i++;
			}
		}
	}
	
	$zip = new ZipFolder( $configFile, "../project-exports/" . $key_id . "/", $struct_filepath, $struct_filesize );
	download( $configFile );
}
else
{
	echo "key_id not set";
}

class ZipFolder 
{
    protected $zip;
    protected $root;
    protected $ignored_paths;
    protected $ignored_filesizes;
    
    function __construct($file, $folder, $ignored_paths = null, $ignored_filesizes = null) 
	{
        $this->zip = new ZipArchive();
        if( $ignored_paths && is_array( $ignored_paths ) ) 			$this->ignored_paths = $ignored_paths;
        if( $ignored_filesizes && is_array( $ignored_filesizes  ) ) $this->ignored_filesizes = $ignored_filesizes;
		
        if ($this->zip->open($file, ZIPARCHIVE::CREATE)!==TRUE) {
            throw new Exception("cannot open <$file>\n");
        }
		
        $folder = substr($folder, -1) == '/' ? substr($folder, 0, strlen($folder)-1) : $folder;
        if(strstr($folder, '/')) {
            $this->root = substr($folder, 0, strrpos($folder, '/')+1);
            $folder = substr($folder, strrpos($folder, '/')+1);
        }
		
        $this->zip($folder);
		
		//------------------ ICONS --------------------
		
		$icons_folder = "../icons";
		
		$icons_folder = substr($icons_folder, -1) == '/' ? substr($icons_folder, 0, strlen($icons_folder) - 1) : $icons_folder;
		if(strstr($icons_folder, '/')) {
            $this->root = substr($icons_folder, 0, strrpos($icons_folder, '/')+1);
            $icons_folder = substr($icons_folder, strrpos($icons_folder, '/')+1);
        }
		
		$this->zip($icons_folder);
		
        $this->zip->close();
    }
    
    function zip($folder, $parent=null) {
        $full_path = $this->root.$parent.$folder;
        $zip_path = $parent.$folder;
        $this->zip->addEmptyDir($zip_path);
        $dir = new DirectoryIterator($full_path);
		
        foreach($dir as $file) 
		{
            if($file != "." && $file != "..") 
			{
                $filename = $file->getFilename();
			
				if($file->isDir()) 
				{
					$this->zip($filename, $zip_path.'/');
				}
				else 
				{
					$search = str_replace( $_GET[ "key_id" ] . "/", "", $zip_path.'/'.$filename );
					$skip = false;
					
					if( $this->ignored_paths && in_array( $search, $this->ignored_paths ) )
					{
						if( $this->ignored_filesizes[$search] == filesize( $full_path.'/'.$filename ) ) 
							$skip = true;
					}
					
					if( strpos( $filename, ".mwp" ) ) $skip = true;
					
					if ( !$skip || $filename == "project.xml" )
					{
						$this->zip->addFile($full_path.'/'.$filename, $zip_path.'/'.$filename);
					}
				}
            }
        }
    }
}


function download( $file_name )
{
	ini_set("memory_limit", "300M");
	if (ini_get('zlib.output_compression')) ini_set('zlib.output_compression', 'Off');
	
	header('Pragma: public');
	header('Expires: 0');
	header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
	header('Cache-Control: private',false);
	header('Content-Type: application/zip');
	header('Content-Disposition: attachment; filename="'.basename($file_name).'"');
	header('Content-Transfer-Encoding: binary');
	header('Content-Length: '.filesize($file_name));
	readfile($file_name);
	exit();
}

?>