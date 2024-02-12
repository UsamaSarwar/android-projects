<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}


$target_dir = "images/";
$target_file_name = $target_dir . basename($_FILES["file"]["name"]);
$response = "";

if(isset($_FILES["file"])){
	if(move_uploaded_file($_FILES["file"]["name"],$target_file_name)){
		$response = "success upload";
		}
	else{
		$response = "failed upload";
			}
}
else{
	$response = "failed upload and require file to upload";
	}
	
	echo json_encode($response);

?>