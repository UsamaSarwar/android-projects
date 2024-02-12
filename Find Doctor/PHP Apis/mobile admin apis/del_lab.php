<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$lab_id = $_POST['lad_id'];

$sql = "DELETE FROM labs WHERE lab_id = '$lab_id'";
if(mysqli_query($con,$sql)){
		$json = 'Successfully Deleted';
		
		}
else{
	$json = 'Error...Try again!!';
	
	}
	echo json_encode($json);
	mysqli_close($con);


?>