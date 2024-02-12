<?php


$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

ini_set('upload_max_filesize', '10M');
ini_set('post_max_size', '10M');
ini_set('max_input_time', 300);
ini_set('max_execution_time', 300);

 $file = '';
 $response = "";
 
		if($_FILES["file"]["name"] != ""){
			$path = "images/".$_FILES["file"]["name"];
			if(move_uploaded_file($_FILES["file"]["tmp_name"],$path)){
				$response = "success upload";
			}
			else{
				$response = "Failed upload";
			}
		}else{
			$response = "Failed upload and require a file";
			}

//$target_dir = "images/";
//$target_file_name = $target_dir.basename($_FILES['file']['name']);
//$response = "";
//
//if(isset($_FILES['file'])){
//	if(move_uploaded_file($_FILES['file']['name'],$target_file_name)){
//		$response = "success upload";
//		}
//	else{
//		$response = "failed upload";
//			}
//}
//else{
//	$response = "failed upload and require file to upload";
//	}
	
	echo json_encode($response);

?>